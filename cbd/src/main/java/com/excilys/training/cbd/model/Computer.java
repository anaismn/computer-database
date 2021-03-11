package com.excilys.training.cbd.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Computer {

	String name;
	String introduced = null;
	String discontinued = null;
	Long company_id;
	
	
	public Computer() {}
	
	public Computer( String name, Date introduced, Date discontinued, Long company_id) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.name = name;
		this.company_id = company_id;
		if(introduced != null) {
			this.introduced = dateFormat.format(introduced);
		}
		
		if(discontinued != null) {
			this.discontinued = dateFormat.format(discontinued);
		}
	}
	
	public String toString() {
		return "name : "+name+
				" - introduced : "+introduced+
				" - discontinued : "+discontinued+
				" company id : "+company_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public Long getCompanyID() {
		return company_id;
	}
}
