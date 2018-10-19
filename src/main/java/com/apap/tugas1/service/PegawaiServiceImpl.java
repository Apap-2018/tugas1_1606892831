package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;

import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.repository.JabatanPegawaiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void tambahPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public int hitungGajiByNIP(String nip) {
		// TODO Auto-generated method stub
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
		int banyakJabatan = listJabatan.size();
		double gajiPokok = 0;
		
		for (int i = 0; i < banyakJabatan; i++) {
			double gajiJabatan = listJabatan.get(i).getJabatan().getGaji_pokok();
			if (gajiPokok < gajiJabatan) {
				gajiPokok = gajiJabatan;
			}
		}
		
		double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentase_tunjangan();
		
		gajiPokok += (presentaseTunjangan*gajiPokok)/100;
		
		return (int) gajiPokok;
	}
	

	@Override
	public void ubahPegawai(PegawaiModel pegawaiSetelahUbah, PegawaiModel pegawaiSebelumUbah) {
		pegawaiSebelumUbah.setInstansi(pegawaiSetelahUbah.getInstansi());
		pegawaiSebelumUbah.setNama(pegawaiSetelahUbah.getNama());
		pegawaiSebelumUbah.setNip(pegawaiSetelahUbah.getNip());
		pegawaiSebelumUbah.setTahun_masuk(pegawaiSetelahUbah.getTahun_masuk());
		pegawaiSebelumUbah.setTanggal_lahir(pegawaiSetelahUbah.getTanggal_lahir());
		pegawaiSebelumUbah.setTempat_lahir(pegawaiSetelahUbah.getTempat_lahir());
		
		int jumlahList = pegawaiSebelumUbah.getJabatanPegawaiList().size();
		for (int i = 0; i< jumlahList; i++) {
			pegawaiSebelumUbah.getJabatanPegawaiList().get(i).setJabatan(pegawaiSetelahUbah.getJabatanPegawaiList().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < pegawaiSetelahUbah.getJabatanPegawaiList().size(); i++) {
			pegawaiSetelahUbah.getJabatanPegawaiList().get(i).setPegawai(pegawaiSebelumUbah);
			jabatanPegawaiDb.save(pegawaiSetelahUbah.getJabatanPegawaiList().get(i));
		}
	}

	@Override
	public String buatNIP(InstansiModel instansi, PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public String buatNIP(InstansiModel instansi, PegawaiModel pegawai) {
//		// TODO Auto-generated method stub
//		
//		String nip = "";
//		nip += instansi.getId();
//
//		Date tanggalLahir = pegawai.getTanggal_lahir();
//		String[] tglLahir = (""+tanggalLahir).split("-");
//		for (int i = tglLahir.length-1; i >= 0; i--) {
//			int ukuranTgl = tglLahir[i].length();
//			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
//		}
//		
//		nip += pegawai.getTahun_masuk();
//		
//		List<PegawaiModel> listPegawai = pegawaiDb.tanggal_lahirAndTahunMasukAndInstansi(pegawai.getTanggal_lahir(), pegawai.getTahun_masuk(), pegawai.getInstansi());
//		
//		int banyakPegawai = listPegawai.size();
//		
//		if (banyakPegawai >= 10) {
//			nip += banyakPegawai;
//		}
//		else {
//			nip += "0" + (banyakPegawai+1);
//		}
//		
//		
//		return nip;
//	}
//	@Override
//	public List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi) {
//		return pegawaiDb.byInstansiOrderByTanggalLahirAsc(instansi);
//	}

}
