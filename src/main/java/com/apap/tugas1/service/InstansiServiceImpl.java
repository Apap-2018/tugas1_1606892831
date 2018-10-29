package com.apap.tugas1.service;


import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.model.InstansiModel;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{

	@Autowired
	private InstansiDb instansiDb;

	@Override
	public List<InstansiModel> findAll() {
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel getInstansiById(Long id) {
		return instansiDb.findById(id).get();
	}
}
