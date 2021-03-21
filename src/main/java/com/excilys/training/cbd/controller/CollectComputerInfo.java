package com.excilys.training.cbd.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Scanner;

import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.service.ServiceCompany;
import com.excilys.training.cbd.service.ServiceComputer;

public class CollectComputerInfo {

	static Scanner sc = new Scanner(System.in); 
	
	public static void setNewComputerInfo() throws ParseException, SQLException {
		
		System.out.print("Name of the computer ? ");
		String name = sc.nextLine();
		System.out.println("name : "+name);

		System.out.print("Date of its introduction ? ");
		LocalDate introduced = CheckDate.checkDateFormat(); 
		System.out.print("Date when it discontinued? ");
		LocalDate discontinued = CheckDate.checkDateFormat();

		//	    System.out.print("What is its company ID ? ");
		//	    Long companyID = Long.parseLong(sc.nextLine()) ;

		System.out.print("What is its company name? ");
		String companyName = sc.nextLine();
		Long companyID = ServiceCompany.getOneCompany(companyName).getID();

		Computer computer = new Computer.Builder(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany_id(companyID)
				.build();
	        System.out.println(computer);
		ServiceComputer.setNewComputer(computer);
	}

	public static void modifications(String oldName) throws SQLException {
		System.out.println("if you doesn't want to change an information");

		Computer oldComputer = ServiceComputer.getOneComputer(oldName);

		String newName = oldName;
		LocalDate introduced = oldComputer.getIntroduced();
		LocalDate discontinued = oldComputer.getDiscontinued();
		Long companyID = oldComputer.getCompanyID();
		
		System.out.println("What is the new name of "+oldName+" ? ");
		String modif = sc.nextLine();
		if(!"".equals(modif)) {
			newName = modif;
		}
		System.out.println("Date of its introduction ? ");
		introduced = CheckDate.checkDateIsNew(introduced);
		System.out.print("Date when it was discontinued? ");
		discontinued = CheckDate.checkDateIsNew(discontinued);
		
		System.out.println("What is the name of its company ? ");
		modif = sc.nextLine();
		if(!"".equals(modif)) {
			companyID = ServiceCompany.getOneCompany(modif).getID();
		}

		Computer updatedComputer = new Computer.Builder(newName)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany_id(companyID)
				.build();
		
		ServiceComputer.updateComputer(oldName,  updatedComputer);
	}
	
}
