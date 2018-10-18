package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinsiModel;
public interface ProvinsiService {

	ProvinsiModel findById(Long id);
	List<ProvinsiModel> findAll();
}
