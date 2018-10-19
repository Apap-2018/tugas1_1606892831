package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanDetailById(long id);
	List<JabatanModel> findAll();
	void tambahJabatan(JabatanModel jabatan);
	void hapusJabatan(JabatanModel jabatan);
}
