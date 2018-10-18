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
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
		
	}

	@Override
	public void ubahJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hapusJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JabatanModel getJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JabatanModel> findAll() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

}
