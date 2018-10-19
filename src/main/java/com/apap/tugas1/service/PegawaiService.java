package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	
	void tambahPegawai(PegawaiModel pegawai);
	
	int hitungGajiByNIP(String nip);

	String buatNIP(InstansiModel instansi, PegawaiModel pegawai);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	void ubahPegawai(PegawaiModel pegawaiSetelahUbah, PegawaiModel pegawaiSebelumUbah);
	
}
