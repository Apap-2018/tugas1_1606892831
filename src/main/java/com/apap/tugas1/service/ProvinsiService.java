package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinsiModel;
public interface ProvinsiService {

	ProvinsiModel getProvinsiById(Long id);
	List<ProvinsiModel> findAll();
}
