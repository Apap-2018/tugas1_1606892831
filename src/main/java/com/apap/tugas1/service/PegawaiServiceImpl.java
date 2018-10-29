package com.apap.tugas1.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.JabatanPegawaiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void tambahPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public int hitungGajiByNIP(String nip) {
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanPegawaiModel> listJabatan = pegawai.getListJabatanPegawai();
		int banyakJabatan = listJabatan.size();
		double gajiPokok = 0;
		
		for (int i = 0; i < banyakJabatan; i++) {
			double gajiJabatan = listJabatan.get(i).getJabatan().getGajiPokok();
			if (gajiPokok < gajiJabatan) {
				gajiPokok = gajiJabatan;
			}
		}
		
		double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		
		gajiPokok += (presentaseTunjangan*gajiPokok)/100;
		
		return (int) gajiPokok;
	}
	

	@Override
	public void ubahPegawai(PegawaiModel pegawaiSetelahUbah, PegawaiModel pegawaiSebelumUbah) {
		pegawaiSebelumUbah.setInstansi(pegawaiSetelahUbah.getInstansi());
		pegawaiSebelumUbah.setNama(pegawaiSetelahUbah.getNama());
		pegawaiSebelumUbah.setNip(pegawaiSetelahUbah.getNip());
		pegawaiSebelumUbah.setTahunMasuk(pegawaiSetelahUbah.getTahunMasuk());
		pegawaiSebelumUbah.setTanggalLahir(pegawaiSetelahUbah.getTanggalLahir());
		pegawaiSebelumUbah.setTempatLahir(pegawaiSetelahUbah.getTempatLahir());
		
		int jumlahList = pegawaiSebelumUbah.getListJabatanPegawai().size();
		for (int i = 0; i< jumlahList; i++) {
			pegawaiSebelumUbah.getListJabatanPegawai().get(i).setJabatan(pegawaiSetelahUbah.getListJabatanPegawai().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < pegawaiSetelahUbah.getListJabatanPegawai().size(); i++) {
			pegawaiSetelahUbah.getListJabatanPegawai().get(i).setPegawai(pegawaiSebelumUbah);
			jabatanPegawaiDb.save(pegawaiSetelahUbah.getListJabatanPegawai().get(i));
		}
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiOrderByTanggalLahirAsc(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		List<PegawaiModel> hasil = new ArrayList<PegawaiModel>();
		List<PegawaiModel> listPegawaiInstansi = pegawaiDb.findByInstansi(instansi);
		
		for (int i = 0; i < listPegawaiInstansi.size(); i++) {
			int sizeJ = listPegawaiInstansi.get(i).getListJabatanPegawai().size();
			for (int j = 0; j < sizeJ; j++ ) {
				if (listPegawaiInstansi.get(i).getListJabatanPegawai().get(j).getJabatan().getId() == jabatan.getId()) {
					hasil.add(listPegawaiInstansi.get(i));
				}
			}
		}
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> findAll() {
		return pegawaiDb.findAll();
	}


	@Override
	public String buatNIP(InstansiModel instansi, PegawaiModel pegawai) {
		
		String nip = "";
		nip += instansi.getId();

		Date tanggalLahir = pegawai.getTanggalLahir();
		String[] tglLahir = (""+tanggalLahir).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		
		nip += pegawai.getTahunMasuk();
		
		List<PegawaiModel> listPegawai = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getInstansi());
		
		int banyakPegawai = listPegawai.size();
		
		if (banyakPegawai >= 10) {
			nip += banyakPegawai;
		}
		else {
			nip += "0" + (banyakPegawai+1);
		}
		return nip;
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsiAndJabatan(ProvinsiModel provinsi, JabatanModel jabatan) {
		List<PegawaiModel> hasil = new ArrayList<PegawaiModel>();
		List<JabatanPegawaiModel> listPegawaiJabatan = jabatanDb.findById(jabatan.getId()).get().getListJabatanPegawai();
		
		for (int i = 0; i < listPegawaiJabatan.size(); i++) {
			if (listPegawaiJabatan.get(i).getPegawai().getInstansi().getProvinsi().getId() == provinsi.getId()) {
				hasil.add(listPegawaiJabatan.get(i).getPegawai());
			}
			
		}
		
		return hasil;
	}

}
