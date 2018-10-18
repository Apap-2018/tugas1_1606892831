package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void tambahPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public void ubahPegawai(PegawaiModel pegawaiSetelahUbah) {
		PegawaiModel pegawaiSebelumUbah = this.getPegawaiDetailByNip(pegawaiSetelahUbah.getNip());
		pegawaiSebelumUbah.setNama(pegawaiSetelahUbah.getNama());
		pegawaiSebelumUbah.setTempat_lahir(pegawaiSetelahUbah.getTempat_lahir());
		pegawaiSebelumUbah.setTanggal_lahir(pegawaiSetelahUbah.getTanggal_lahir());
		pegawaiSebelumUbah.setTahun_masuk(pegawaiSetelahUbah.getTahun_masuk());
		
	}

	@Override
	public PegawaiModel getPegawaiDetailByIdInstansi(String id_instansi) {
		return pegawaiDb.save(id_instansi);
	}

	@Override
	public PegawaiModel getPegawaiDetailByIdJabatan(String id_jabatan) {
		// TODO Auto-generated method stub
		return null;
	}

}
