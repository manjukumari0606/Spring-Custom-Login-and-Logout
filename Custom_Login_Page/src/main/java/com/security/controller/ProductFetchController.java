package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.security.model.Product;
import com.security.repository.ProductRepo;
import com.security.service.ProductService;

@Controller
public class ProductFetchController {
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/signin")
	public String login() {
		System.out.println("Sign in page coming.....");
		return "login";
	}
	
	@GetMapping("/userlogout")
	public String error() {
		System.out.println("logout page coming.....");
		return "logout";
	}
	
	@GetMapping("/data")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String showPage(Model model) {
		return findPaginated(1,"product_name", "asc", model);
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
		    @RequestParam("sortField") String sortField,
		    @RequestParam("sortDir") String sortDir,
		    Model model) {
		    int pageSize = 5;
		
		    Page < Product > page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
		    List < Product > listProduct = page.getContent();

		
		model.addAttribute("currentPage" , pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("product", listProduct);
	
		return "index";
		
	} 
}
