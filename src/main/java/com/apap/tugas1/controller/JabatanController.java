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
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJawaban);
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
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan_tambah, Model model) {
		jabatanService.tambahJabatan(jabatan_tambah);
		
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "sukses-tambah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah/", method = RequestMethod.GET)
	private String ubahJabatan(@RequestParam(value="id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		
		model.addAttribute("title", "Ubah Jabatan");
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("jabatanBaru", jabatan);
		return "ubah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatanBaru, Model model) {
		
		JabatanModel jabatan = jabatanService.getJabatanDetailById(jabatanBaru.getId());
		jabatan.setNama(jabatanBaru.getNama());
		jabatan.setDeskripsi(jabatanBaru.getDeskripsi());
		jabatan.setGaji_pokok(jabatanBaru.getGaji_pokok());
		
		model.addAttribute("title", "Sukses");
		model.addAttribute("jabatan", jabatan);
		return "sukses-ubah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus/", method = RequestMethod.GET)
	private String hapusJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
		jabatanService.hapusJabatan(jabatan);
		
		model.addAttribute("title", "Sukses");
		return "sukses-hapus-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewAll", method = RequestMethod.GET)
	public String lihatSemuaJabatan(Model model) {
		
		List<JabatanModel> listJabatan = jabatanService.findAll();

		
		model.addAttribute("title", "Viewall Jabatan");
		model.addAttribute("listJabatan", listJabatan);
		return "lihat-jabatan-semua";
}

}
