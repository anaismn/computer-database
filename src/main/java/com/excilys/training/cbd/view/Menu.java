package com.excilys.training.cbd.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.training.cbd.controller.CollectComputerInfo;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.service.ServiceCompany;
import com.excilys.training.cbd.service.ServiceComputer;

public class Menu {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void menuPrincipal() {
		System.out.println("Choisissez l'action que vous voulez effectuée parmi la liste ci-desous : \n");
		for (ChoiceMenu choice : ChoiceMenu.values()) {
			System.out.println(choice);
		}
	}
	
	public static void choisirAction(int action) throws ParseException, SQLException {
		switch(ChoiceMenu.values()[action]) {
		case EXIT:
			System.out.println("See you again");
			System.exit(0);
			break;
		case LIST_COMPANIES:
			System.out.println("List of All the companies");
			ArrayList<Company> companies = ServiceCompany.getAllCompanies();
			System.out.println("piellll");
			companies.forEach((company) -> System.out.println(company)) ;
			break;
		case LIST_COMPUTERS:
			System.out.println("List of All the computers");
			ArrayList<Computer> computers = ServiceComputer.getAllComputers();
			computers.forEach((computer) -> System.out.println( " - id : "+ computer.getID() + " = Name : "+computer.getName())) ;
			break;
		case ONE_COMPUTER:
			System.out.println("Give the name of the computer, you're searching for");
			String nameSearched = sc.nextLine() ;
			Computer computer = ServiceComputer.getOneComputer(nameSearched);
			System.out.println(computer) ;
			break;
		case CREATE_COMPUTER:
			System.out.println("Add a new computer to the database");
			CollectComputerInfo.setNewComputerInfo();
			break;
		case UPDATE_COMPUTER:
			System.out.println("Give the name of the computer, you'll update ");
			String name = sc.nextLine() ;  
			System.out.println("Tell me what you want modify. There is : ");
			CollectComputerInfo.modifications(name);
			break;
		case DELETE_COMPUTER:
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
	
}
