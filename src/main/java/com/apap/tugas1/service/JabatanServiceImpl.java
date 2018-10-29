package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void tambahJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public void hapusJabatan(JabatanModel jabatan) {
		jabatanDb.delete(jabatan);
	}

	@Override
	public JabatanModel getJabatanById(long id) {
		return jabatanDb.findById(id).get();
	}

	@Override
	public List<JabatanModel> findAll() {
		return jabatanDb.findAll();
	}

}
