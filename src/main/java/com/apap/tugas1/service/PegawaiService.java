package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiByNip(String nip);
	
	void tambahPegawai(PegawaiModel pegawai);
	
	int hitungGajiByNIP(String nip);

	String buatNIP(InstansiModel instansi, PegawaiModel pegawai);
	
	List<PegawaiModel> getPegawaiByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	
	void ubahPegawai(PegawaiModel pegawaiSetelahUbah, PegawaiModel pegawaiSebelumUbah);
	
	List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	
	List<PegawaiModel> findAll();

	List<PegawaiModel> getPegawaiByProvinsiAndJabatan(ProvinsiModel provinsi, JabatanModel jabatan);
}
