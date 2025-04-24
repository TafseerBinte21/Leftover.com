package com.tafa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "marketplace", schema = "leftover")
public class MarketPlace implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_id_seq")
	@SequenceGenerator(name = "market_id_seq", sequenceName = "market_id_seq", allocationSize = 1)
	private Long id;
	
	private String name;
	
	private String description;
	
	private String price;
	
	@Column(name = "image", columnDefinition = "BYTEA") 
	private byte[] image;
	
	
	public MarketPlace () {
		
	}


	public MarketPlace(String name, String description, String price, byte[] image) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	
}
