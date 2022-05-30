package com.store.app.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Positive(message = "La cantidad debe ser mayor a cero")
	private Double quantity;
	private Double price;

	@Column(name = "product_id")
	private Long productId;
	
	@Transient
	private Double subTotal;

	/*
	 * @Transient
	 * private Product product;
	 * */
	
	public Double getSubtotal() {
		if( this.price > 0 && this.quantity > 0)
		{
			return this.price * this.quantity;
		}
		return (double)0;
	}
	
	public InvoiceItem(){
			this.quantity = (double) 0;
			this.price = (double) 0;
	}
	
}
