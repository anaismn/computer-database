package com.excilys.training.model;

import java.util.ArrayList;
import java.util.List;

public class Page {

	private List<ComputerDTO> computerDTOs;
	public int pageNumber;

	public Page(int pageNumber, List<ComputerDTO> computerDTOs) {
		this.computerDTOs = computerDTOs;
		this.pageNumber = pageNumber;
	}

//	public Page(int j, List<ComputerDTO> subList) {
//		// TODO Auto-generated constructor stub
//	}

	public List<ComputerDTO> getcomputerDTOs() {
		return computerDTOs;
	}

	public void setcomputerDTOs(List<ComputerDTO> computerDTOs) {
		this.computerDTOs = computerDTOs;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toString() {
		String pageDisplay = "";
		for (ComputerDTO computerDTO : this.computerDTOs) {
			pageDisplay += " - Name : " + computerDTO.getName() + "\n";
		}
		return pageDisplay;
	}

}
