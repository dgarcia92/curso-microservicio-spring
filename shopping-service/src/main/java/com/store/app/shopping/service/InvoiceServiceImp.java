package com.store.app.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.app.shopping.entity.Invoice;
import com.store.app.shopping.repository.InvoiceRepository;


@Service
public class InvoiceServiceImp implements IInvoiceService {

	
	@Autowired
	private InvoiceRepository repository;
	
	@Override
	public List<Invoice> findInvoiceAll() {
		return repository.findAll();
	}

	@Override
	public Invoice createInvoice(Invoice invoice) {
		
		Invoice invoiceDB = repository.findByNumberInvoice( invoice.getNumberInvoice());
		
		if(invoiceDB != null)
		{
			return invoiceDB;
		}
		
		invoice.setState("CREATED");
		return repository.save(invoice);
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) {
		
		Invoice invoiceDB = getInvoice( invoice.getId());
		
		if(invoiceDB == null)
		{
			return null;
		}
		
		invoiceDB.setCustomerId(invoice.getCustomerId());
		invoiceDB.setDescription(invoice.getDescription());
		invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
		invoiceDB.getItems().clear();
		invoiceDB.setItems(invoice.getItems());
		return repository.save(invoiceDB);
	}

	@Override
	public Invoice deleteInvoice(Invoice invoice) {
		Invoice invoiceDB = getInvoice( invoice.getId());
		
		if(invoiceDB == null)
		{
			return null;
		}
		
		invoiceDB.setState("DELETED");
		return repository.save(invoice);
	}

	@Override
	public Invoice getInvoice(Long id) {
		return repository.findById(id).orElse(null);
	}

}
