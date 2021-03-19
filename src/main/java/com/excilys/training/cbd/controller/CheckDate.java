package com.excilys.training.cbd.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

	public class CheckDate {
		
		static Scanner sc = new Scanner(System.in);
		static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		public static LocalDate checkDateFormat() {
			while(true) {
				System.out.println(" ( Please enter a date with this format yyyy-MM-dd )");
				String date = sc.nextLine();
				if(!"".equals(date)) {
					dateFormat.setLenient(false);
					return LocalDate.parse(date);
				}
			}
		}
		
		public static LocalDate checkDateIsNew(LocalDate oldDate) {
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
		
		public static Boolean checkDiscontinuedAfterIntroduced(LocalDate introduced, LocalDate discontinued) {
			if(discontinued.isBefore(introduced)) {
				return false;
			}else {
				return true;
			}
		}
		
}
