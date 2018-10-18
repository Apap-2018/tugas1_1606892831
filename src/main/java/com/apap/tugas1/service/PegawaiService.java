package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	PegawaiModel getPegawaiDetailByIdInstansi(String id_instansi)
	;
	PegawaiModel getPegawaiDetailByIdJabatan(String id_jabatan)
	;
	void tambahPegawai(PegawaiModel pegawai);
	void ubahPegawai(PegawaiModel pegawai);
	
	int hitungGajiByNIP(String nip);
}
