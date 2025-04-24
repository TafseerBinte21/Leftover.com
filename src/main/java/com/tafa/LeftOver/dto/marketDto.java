package com.tafa.LeftOver.dto;

import java.util.Arrays;

import javax.persistence.Column;

public class marketDto {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private String price;
	
	private byte[] image;
	
	public marketDto () {
		
	}


	public marketDto(String name, String description, String price, byte[] image) {
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

	@Override
	public String toString() {
		return "marketDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", image=" + Arrays.toString(image) + "]";
	}

	
}
