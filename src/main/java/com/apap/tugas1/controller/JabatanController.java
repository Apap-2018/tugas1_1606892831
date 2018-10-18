package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;


@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/view", method= RequestMethod.GET)
	private String lihatPegawai(@RequestParam("id") long id, Model model) {
		model.addAttribute("title", "Detail jabatan");
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		return "lihat-jabatan";
	}
	
	@RequestMapping(value="/jabatan/viewAll", method= RequestMethod.GET)
	private String viewAll(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		model.addAttribute("title", "View Pilot");
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		List<FlightModel> pilotFlight = pilot.getPilotFlight();
		model.addAttribute("pilotFlight", pilotFlight);
		return "view-pilot";
		
	}
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		model.addAttribute("title", "Tambah Jabatan");
		model.addAttribute("jabatan", new JabatanModel());
		return "tambah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.tambahJabatan(jabatan);
		return "sukses-tambah-pegawai";
	}
	
	@RequestMapping(value="/jabatan/ubah/", method = RequestMethod.GET)
	private String updatePilot(@RequestParam(value="id") long id, Model model) {
		model.addAttribute("title", "Ubah Jabatan");
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("jabatanBaru", new JabatanModel());
		return "ubah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatanBaru) {
		jabatanService.ubahJabatan(jabatanBaru);
		return "sukses-ubah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus/{id}", method = RequestMethod.GET)
	private String hapusJabatan(@PathVariable(value="id") String id) {
		JabatanModel pilot = jabatanService.getJabatanDetailById(Long.parseLong(id));
		jabatanService.hapusJabatan(pilot);
		return "sukses-hapus-jabatan";
	}
	
}
