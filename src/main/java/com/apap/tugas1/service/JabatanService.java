package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanDetailById(long id);
	void tambahJabatan(JabatanModel jabatan);
	void ubahJabatan(JabatanModel jabatan);
	void hapusJabatan(JabatanModel jabatan);
}
