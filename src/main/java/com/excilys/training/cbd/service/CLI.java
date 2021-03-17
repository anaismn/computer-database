package com.excilys.training.cbd.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.DAOException;

public class CLI {

	static Scanner sc;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws ParseException, SQLException {
		sc = new Scanner(System.in); 
		Boolean run = true;
		int action;

		while(run) {
			menu();
			action = Integer.parseInt(sc.nextLine()) ; 
			choisirAction(action);
			System.out.println("\n -------------------- \n");
		}
	}

	public static void menu() {
		System.out.println("Choisissez l'action que vous voulez effectuée parmi la liste ci-desous : ");
		System.out.println("1 - List companies");
		System.out.println("2 - List computers");
		System.out.println("3 - Show computer details (the detailed information of only one computer)");
		System.out.println("4 - Create a computer");
		System.out.println("5 - Update a computer");
		System.out.println("6 - Delete a computer");
		System.out.println("0 - To exit \n");
	}

	public static void choisirAction(int action) throws ParseException, SQLException {
		switch(action) {
		case 0:
			System.out.println("See you again");
			System.exit(0);
			break;
		case 1:
			System.out.println("List of All the companies");
			ArrayList<Company> companies = ServiceCompany.getAllCompanies();
			companies.forEach((company) -> System.out.println(company)) ;
			break;
		case 2:
			System.out.println("List of All the computers");
			ArrayList<Computer> computers = ServiceComputer.getAllComputers();
			computers.forEach((computer) -> System.out.println( " - id : "+ computer.getID() + " = Name : "+computer.getName())) ;
			break;
		case 3:
			System.out.println("Give the name of the computer, you're searching for");
			String nameSearched = sc.nextLine() ;
			Computer computer = ServiceComputer.getOneComputer(nameSearched);
			System.out.println(computer) ;
			break;
		case 4:
			System.out.println("Add a new computer to the database");
			setNewComputerInfo();
			break;
		case 5:
			System.out.println("Give the name of the computer, you'll update ");
			String name = sc.nextLine() ;  
			System.out.println("Tell me what you want modify. There is : ");
			modifications(name);
			break;
		case 6:
			System.out.println("Give the name of the computer, you want to delete");
			String delateName = sc.nextLine();
			System.out.println("Are you sure ? y/n");
			String valide = sc.nextLine();
			if(valide.equals("Y") || valide.equals("y")){
				ServiceComputer.deleteComputer(delateName);
			}else{
				System.out.println("Cancel ");
			}
			//Long deleteID = Long.parseLong(sc.nextLine()) ;

			break;
		default :
			System.out.println("Vueillez choisir parmi ce qui est proposé");  
		}
	}

	private static void setNewComputerInfo() throws ParseException, SQLException {
		System.out.print("Name of the computer ? ");
		String name = sc.nextLine();
		System.out.println("name : "+name);

		System.out.print("Date of its introduction ? ");
		LocalDate introduced = checkDate();
		System.out.print("Date when it discontinued? ");
		LocalDate discontinued = checkDate();

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

	private static LocalDate checkDate() {
		while(true) {
			System.out.println(" ( Please enter a date with this format yyyy-MM-dd )");
			String date = sc.nextLine();
			if(!"".equals(date)) {
				dateFormat.setLenient(false);
				return LocalDate.parse(date);
			}
		}
	}
	
	private static LocalDate checkDate(LocalDate oldDate) {
		while(true) {
			System.out.println(" ( Please enter a date with this format yyyy-MM-dd )");
			String date = sc.nextLine();
			if(!"".equals(date)) {
				dateFormat.setLenient(false);
				return LocalDate.parse(date);
			}else {
				return oldDate;
			}
		}
	}

	private static void modifications(String oldName) throws SQLException {
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
		introduced = checkDate(introduced);
		System.out.print("Date when it was discontinued? ");
		discontinued = checkDate(discontinued);
		
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
