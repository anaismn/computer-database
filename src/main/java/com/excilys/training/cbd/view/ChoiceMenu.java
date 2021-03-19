package com.excilys.training.cbd.view;

public enum ChoiceMenu {
	EXIT(0, "To exit \n"),
	LIST_COMPANIES(1, "List companies"), 
	LIST_COMPUTERS(2, "List computers"), 
	ONE_COMPUTER(3, "Show computer details (the detailed information of only one computer)"), 
	CREATE_COMPUTER(4, "Create a computer"), 
	UPDATE_COMPUTER(5, "Update a computer"), 
	DELETE_COMPUTER(6, "Delete a computer");
	
	private int number;
	private String action;
	
	private ChoiceMenu(int number, String action) {
		this.number = number;
		this.action = action;
	}

	public int getNumber() {
		return number;
	}

	public String getAction() {
		return action;
	}
	
	public String toString() {
		return this.number + " - " + this.action ;
	}
}
