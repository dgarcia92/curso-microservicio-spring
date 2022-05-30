package com.store.app.shopping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.app.shopping.controller.ErrorMessage;
import com.store.app.shopping.entity.Invoice;
import com.store.app.shopping.service.InvoiceServiceImp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/invoices")
public class InvoiceController {

	
	@Autowired
	private InvoiceServiceImp service;
	
	
	// --------- Retreive All Invoices ----------------
	
	@GetMapping
	public ResponseEntity<List<Invoice>> listAllInvoices(){
		
		List<Invoice> invoices = service.findInvoiceAll();
		
		if(invoices.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(invoices);	
	}
	
	
	// --------- Retreive Single Invoices ----------------
	
	@GetMapping("/{id}")
	public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Long id){
		
		log.info("Fetching Invoice whidth id {}", id);
		
		Invoice invoice = service.getInvoice(id);
		
		if(null == invoice)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(invoice);
	}
	
	
	// ---------  Create Invoice ----------------
	
	@PostMapping()
	public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result)
	{
		log.info("Creating Invoice: {}", invoice);
	
		if(result.hasErrors())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
		}
		
		Invoice invoiceDb = service.createInvoice(invoice);
		return ResponseEntity.ok(invoiceDb);
	}
	
	
	// ---------  Update Invoice ----------------
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateInvoice(@PathVariable("id") Long id, @RequestBody Invoice invoice)
	{
		
		log.info("updating Invoice: {}", invoice);
		
		invoice.setId(id);
		Invoice currentInvoice = service.updateInvoice(invoice);
		
		if( currentInvoice == null)
		{
			log.error("Unable to update. Invoice whith id {} not found.", invoice);
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(currentInvoice);
	}
	
	
	// ---------  Delete Invoice ----------------
	@DeleteMapping("/{id}")
	public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") long id)
	{
		log.info("Fetching & Deleting Invoice Whith id {}", id);
		
		
		Invoice invoice = service.getInvoice(id);
		
		if(null == invoice)
		{
			log.error("Unable to update. Invoice whith id {} not found.", invoice);
			return ResponseEntity.notFound().build();
		}
		
		invoice = service.deleteInvoice(invoice);
		return ResponseEntity.ok(invoice);
		
	}
	
	
	private String formatMessage(BindingResult result)
	{
		List<Map<String, String>> errors = result.getFieldErrors().stream().map( err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		
		
		ErrorMessage errorMessage = ErrorMessage.builder().code("01").menssages(errors).build();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		
		try
		{
			jsonString = mapper.writeValueAsString(errorMessage);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
		return jsonString;
	}
	
}
