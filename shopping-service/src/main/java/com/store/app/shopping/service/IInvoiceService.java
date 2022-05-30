package com.store.app.shopping.service;

import java.util.List;

import com.store.app.shopping.entity.Invoice;

public interface IInvoiceService {

	public List<Invoice> findInvoiceAll();
	
	public Invoice createInvoice(Invoice invoice);
	public Invoice updateInvoice(Invoice invoice);
	public Invoice deleteInvoice(Invoice invoice);
	
	public Invoice getInvoice(Long id);
}
