package com.store.app.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.app.shopping.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
	
	public List<Invoice> findByCustomerId(Long id);
	public Invoice findByNumberInvoice(String numberInvoice);

}
