package com.excilys.training.cbd.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

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
			ServiceComputer.getAllComputers();
			break;
		case 3:
			System.out.println("Give the name of the computer, you're searching for");
			String nameSearched = sc.nextLine() ;
			System.out.println(ServiceComputer.getOneComputer(nameSearched));
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
			System.out.println("Saturday");  
		}
	}

	private static void setNewComputerInfo() throws ParseException, SQLException {
		System.out.print("Name of the computer ? ");
		String name = sc.nextLine();
		System.out.println("name : "+name);

		System.out.print("Date of its introduction ? ");
		String introduced = checkDate();
		System.out.print("Date when it discontinued? ");
		String discontinued = checkDate();

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

	private static String checkDate() {
		while(true) {
			System.out.println(" ( Please enter a date with this format yyyy-MM-dd )");
			String date = sc.nextLine();
			if(!"".equals(date)) {
				dateFormat.setLenient(false);
				try
				{
					 dateFormat.parse(date);
					 return date;
				}
				/* Date format is invalid */
				catch (ParseException e)
				{
					System.out.println(date+" is Invalid Date format");
				}
			}
		}
	}

	private static void modifications(String oldName) {
		System.out.println("1 - the name");
		System.out.println("2 - the date of introduction");
		System.out.println("3 - the date of discontinuation)");
		System.out.println("4 - the company it's from");

		System.out.println("Example : if you want to change the name and the company it's from \n "+
				"you will tape 14");

		Optional<Computer> oldComputer = ServiceComputer.getOneComputer(oldName);

		String newName = oldName;
		String introduced = "";
		String discontinued = "";
		Long companyID = null;

		String modificationsChoice = sc.nextLine();
		if (modificationsChoice.contains("1")) {
			System.out.println("What is the new name of "+oldName+" ? ");
			newName = sc.nextLine();
		}
		if (modificationsChoice.contains("2")) {
			System.out.print("Date of its introduction ? ");
			introduced = dateFormat.format(checkDate());
		}
		if (modificationsChoice.contains("3")) {
			System.out.print("Date when it was discontinued? ");
			discontinued = dateFormat.format(checkDate());
		}
		if (modificationsChoice.contains("4")) {
			System.out.println("What is the name of its company ? ");
			String companyName = sc.nextLine();
			companyID = ServiceCompany.getOneCompany(companyName).getID();
		}

		ServiceComputer.updateComputer(oldName,  oldComputer, newName, introduced, discontinued, companyID);
	}

}
