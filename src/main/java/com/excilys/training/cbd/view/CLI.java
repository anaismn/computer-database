package com.excilys.training.cbd.view;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CLI {

	static Scanner sc;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws ParseException, SQLException {
		sc = new Scanner(System.in); 
		Boolean run = true;
		int action;

		while(run) {
			Menu.menuPrincipal();
			action = Integer.parseInt(sc.nextLine()) ; 
			Menu.choisirAction(action);
			System.out.println("\n -------------------- \n");
		}
	}
}
