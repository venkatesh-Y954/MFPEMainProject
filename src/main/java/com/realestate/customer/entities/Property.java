package com.realestate.customer.entities;

import lombok.Data;

@Data
public class Property {

	private int id;
	private String propertyType;
	private String locality;
	private double budget;
}
