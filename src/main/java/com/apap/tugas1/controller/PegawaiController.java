package com.apap.tugas1.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;


import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private InstansiService instansiService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	@Autowired
	private ProvinsiService provinsiService;

	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.findAll();
		List<InstansiModel> listInstansi = instansiService.findAll();
		
		model.addAttribute("title", "Home Page");
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String lihatPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();
		List<JabatanPegawaiModel> listJabatanPegawai = pegawai.getJabatanPegawaiList();

		model.addAttribute("title", "Detail Pegawai");
		model.addAttribute("gaji", pegawaiService.hitungGajiByNIP(nip));
		model.addAttribute("listJabatan", listJabatanPegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		return "lihat-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<JabatanPegawaiModel>();
		pegawai.setJabatanPegawaiList(listJabatanPegawai);

		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		List<InstansiModel> listInstansi = listProvinsi.get(0).getProvinsiInstansi();

		List<JabatanModel> listJabatan = jabatanService.findAll();


		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}


	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"tambahJabatanLain"})
	public String tambahJabatanLain(@ModelAttribute PegawaiModel pegawaiBaru, Model model) {
		PegawaiModel pegawai = pegawaiBaru;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getProvinsiInstansi();

		List<JabatanModel> listJabatan = jabatanService.findAll();
	
		
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		String nip = pegawaiService.buatNIP(pegawai.getInstansi(), pegawai);
		pegawai.setNip(nip);

		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();

		pegawaiService.tambahPegawai(pegawai);

		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.tambahJabatan(listJabatan.get(i));
		}

		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);

		return "sukses-tambah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method= RequestMethod.GET)
	private String ubahPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = pegawai.getInstansi().getProvinsi().getProvinsiInstansi();

		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"tambahJabatanLain"})
	public String ubahJabatan(@ModelAttribute PegawaiModel pegawaiBaru, Model model) {
		PegawaiModel pegawai = pegawaiBaru;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getProvinsiInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
	
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";
	}




	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawaiSetelahUbah, Model model) {
		
		String nip = pegawaiService.buatNIP(pegawaiSetelahUbah.getInstansi(), pegawaiSetelahUbah);
		pegawaiSetelahUbah.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatan = pegawaiSetelahUbah.getJabatanPegawaiList();
		
		pegawaiSetelahUbah.setJabatanPegawaiList(new ArrayList<JabatanPegawaiModel>());
		
	
		pegawaiService.tambahPegawai(pegawaiSetelahUbah);
	
		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawaiSetelahUbah);
			jabatanPegawaiService.tambahJabatan(listJabatan.get(i));
		}
		
		
		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);
		return "sukse-ubah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai(@RequestParam (value="idProvinsi")String idProvinsi, Model model){
		return null;
	}


	@RequestMapping(value="/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String lihatPegawaiTuaDanMuda(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
		InstansiModel instansi = instansiService.findById(idInstansi);
		
		List<PegawaiModel> listPegawai =  pegawaiService.findByInstansiOrderByTanggalLahirAsc(instansi);
		
		if (!listPegawai.isEmpty()) {
			int banyakPegawai = listPegawai.size();
			
			PegawaiModel pegawai_termuda = listPegawai.get(banyakPegawai-1);
			PegawaiModel pegawai_tertua = listPegawai.get(0);

			
			model.addAttribute("gajiPegawaiTeruda", pegawaiService.hitungGajiByNIP(pegawai_termuda.getNip()));
			model.addAttribute("gajiPegawaiTertua", pegawaiService.hitungGajiByNIP(pegawai_tertua.getNip()));
			
			model.addAttribute("pegawaiTermuda", pegawai_termuda);
			model.addAttribute("pegawaiTertua", pegawai_tertua);
			
		}

		model.addAttribute("title", "Detail Pegawai");
		return "termuda-tertua";
	}
	
}