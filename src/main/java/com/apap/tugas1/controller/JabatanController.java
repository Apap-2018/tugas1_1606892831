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

import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	
	@Autowired 
	private JabatanService jabatanService;

	@RequestMapping(value="/jabatan/view", method= RequestMethod.GET)
	private String lihatJabatan(@RequestParam("idJabatan") Long idJawaban, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJawaban);
		model.addAttribute("title", "Detail Jabatan");
		model.addAttribute("jabatan", jabatan);
		
		return "lihat-jabatan";
	}

	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "tambah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatanBaru, Model model) {
		jabatanService.tambahJabatan(jabatanBaru);
		
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "sukses-tambah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String ubahJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
		
		model.addAttribute("title", "Ubah Jabatan");
		model.addAttribute("jabatan", jabatan);
		return "ubah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatanUbah, Model model) {
		
		JabatanModel jabatan = jabatanService.getJabatanById(jabatanUbah.getId());
		
		jabatan.setNama(jabatanUbah.getNama());
		jabatan.setDeskripsi(jabatanUbah.getDeskripsi());
		jabatan.setGajiPokok(jabatanUbah.getGajiPokok());
		
		model.addAttribute("title", "Ubah Jabatan");
		model.addAttribute("message", true);
		model.addAttribute("jabatan", jabatan);
		return "ubah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
		
		jabatanService.hapusJabatan(jabatan);
		
		model.addAttribute("title", "Hapus Jabatan");
		return "sukses-hapus-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String viewAllJabatan(Model model) {
		
		List<JabatanModel> listJabatan = jabatanService.findAll();

		
		model.addAttribute("title", "Viewall Jabatan");
		model.addAttribute("listJabatan", listJabatan);
		return "lihat-jabatan-semua";
	}
}


