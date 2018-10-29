package com.apap.tugas1.controller;

import java.util.List;
import java.util.Optional;
import java.sql.Date;
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
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();
		List<JabatanPegawaiModel> listJabatanPegawai = pegawai.getListJabatanPegawai();

		model.addAttribute("title", "Detail Pegawai");
		model.addAttribute("gaji", pegawaiService.hitungGajiByNIP(nip));
		model.addAttribute("listJabatan", listJabatanPegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		return "lihat-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setTanggalLahir(new Date(0));
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<JabatanPegawaiModel>();
		pegawai.setListJabatanPegawai(listJabatanPegawai);
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		List<InstansiModel> listInstansi = listProvinsi.get(0).getListProvinsiInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}


	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"tambahJabatanLain"})
	public String tambahJabatan(@ModelAttribute PegawaiModel pegawaiBaru, Model model) {
		PegawaiModel pegawai = pegawaiBaru;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListProvinsiInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String tambahJabatanBaru(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		String nip = pegawaiService.buatNIP(pegawai.getInstansi(), pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatan = pegawai.getListJabatanPegawai();
		
		pegawai.setListJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		
		pegawaiService.tambahPegawai(pegawai);
		
		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.tambahJabatan(listJabatan.get(i));
		}
		
		
		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);
		return "sukses-tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = pegawai.getInstansi().getProvinsi().getListProvinsiInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"tambahJabatanLain"})
	public String ubahJabatan(@ModelAttribute PegawaiModel pegawaiSetelahUbah, Model model) {
		PegawaiModel pegawai = pegawaiSetelahUbah;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListProvinsiInstansi();

		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String ubahJabatanBaru(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel pegawaiSebelumUbah = pegawaiService.getPegawaiByNip(pegawai.getNip());
		
		pegawaiService.ubahPegawai(pegawai, pegawaiSebelumUbah);
		
		String nip = pegawaiSebelumUbah.getNip();
		
		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);
		return "sukses-ubah-pegawai";
	}

	@RequestMapping(value="/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String PegawaiTertuaDanTermuda(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(idInstansi);
		
		List<PegawaiModel> listPegawai =  pegawaiService.getPegawaiByInstansiOrderByTanggalLahirAsc(instansi);
		
		if (!listPegawai.isEmpty()) {
			int banyakPegawai = listPegawai.size();
			
			PegawaiModel pegawai_termuda = listPegawai.get(banyakPegawai-1);
			PegawaiModel pegawai_tertua = listPegawai.get(0);

			
			model.addAttribute("gajiPegawaiTermuda", pegawaiService.hitungGajiByNIP(pegawai_termuda.getNip()));
			model.addAttribute("gajiPegawaiTertua", pegawaiService.hitungGajiByNIP(pegawai_tertua.getNip()));
			
			model.addAttribute("pegawaiTermuda", pegawai_termuda);
			model.addAttribute("pegawaiTertua", pegawai_tertua);
			
		}

		model.addAttribute("title", "Detail Pegawai");
		return "termuda-tertua";
	}
	
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai(Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListProvinsiInstansi();
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Cari Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);

		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params= {"search"})
	public String cariPegawaiBaru(@RequestParam(value="IdProvinsi", required=false) Optional<Long> IdProvinsi, 
			@RequestParam(value="IdInstansi", required=false) Optional<Long> IdInstansi,
			@RequestParam(value="IdJabatan", required=false) Optional<Long> IdJabatan, Model model) {
		
	
		
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		if (IdProvinsi.isPresent()) {
			if (IdInstansi.isPresent()) {
				if (IdJabatan.isPresent()) {
					InstansiModel instansi = instansiService.getInstansiById(IdInstansi.get());
					JabatanModel jabatan = jabatanService.getJabatanById(IdJabatan.get());
					
					listPegawai = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
					
				}
				else {
					InstansiModel instansi = instansiService.getInstansiById(IdInstansi.get());
					listPegawai = pegawaiService.getPegawaiByInstansi(instansi);
				}
				
			}
			else {
				if (IdJabatan.isPresent()) {
					ProvinsiModel provinsi = provinsiService.getProvinsiById(IdProvinsi.get());
					JabatanModel jabatan = jabatanService.getJabatanById(IdJabatan.get());
					
					listPegawai = pegawaiService.getPegawaiByProvinsiAndJabatan(provinsi, jabatan);

					
				}
				else {
					List<InstansiModel> listInstansi = provinsiService.getProvinsiById(IdProvinsi.get()).getListProvinsiInstansi();
					for (int i = 0; i < listInstansi.size(); i++) {
						List<PegawaiModel> listPegawaiBaru = listInstansi.get(i).getListPegawai();
						listPegawai.addAll(listPegawaiBaru);
					}
				}
			}
		}
		else {
			if (IdJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.getJabatanById(IdJabatan.get());
				for (int i = 0; i< jabatan.getListJabatanPegawai().size(); i++) {
					listPegawai.add(jabatan.getListJabatanPegawai().get(i).getPegawai());
				}
				
			}
		}
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListProvinsiInstansi();
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Cari Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listPegawai", listPegawai);
		return "cari-pegawai";
	}
	
}