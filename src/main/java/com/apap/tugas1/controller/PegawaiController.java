package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home Page");
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method= RequestMethod.GET)
	private String lihatPegawai(@RequestParam("nip") String nip, Model model) {
		model.addAttribute("title", "Detail Pegawai");
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		List<JabatanModel> pegawaiJabatan = pegawai.getJabatan();
		model.addAttribute("pegawaiJabatan", pegawaiJabatan);
		return "lihat-pegawai";
		
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", new PegawaiModel());
		return "tambah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.tambahPegawai(pegawai);
		return "sukses-tambah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method= RequestMethod.GET)
	private String ubahPegawai(@RequestParam("nip") String nip, Model model) {
		model.addAttribute("title", "Ubah Pegawai");
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pegawaiSetelahUbah", new PegawaiModel());
		return "ubah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawaiSetelahUbah) {
		pegawaiService.ubahPegawai(pegawaiSetelahUbah);
		return "sukses-ubah-pegawai";
	}
	
	
	
}