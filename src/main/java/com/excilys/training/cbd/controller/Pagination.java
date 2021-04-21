package com.excilys.training.cbd.controller;

import java.util.ArrayList;

import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;
import com.excilys.training.cbd.model.Page;

public class Pagination {

	public int limitByPage;
	public int numberOfPages = 0;
	public ArrayList<Page> pages = new ArrayList<Page>();

	public Pagination(int limitByPage, ArrayList<ComputerDTO> computersDTO) {
		this.limitByPage = limitByPage;
		System.out.println("nombre de computers : " + computersDTO.size());
		System.out.println("limites par pages : " + limitByPage);
		System.out.println("maxi : " + (int) Math.ceil((float) computersDTO.size() / limitByPage));
		this.numberOfPages = (int) Math.ceil((float) computersDTO.size() / limitByPage);
		for (int i = 0, j = 1; i < computersDTO.size(); i = i + limitByPage, j++) {
			if (numberOfPages - 1 == pages.size()) {
				pages.add(new Page(j, computersDTO.subList(i, computersDTO.size())));
			} else {
				pages.add(new Page(j, computersDTO.subList(i, i + limitByPage)));
			}
		}

	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public Page nextPage(int currentPage) {
		return this.pages.get(currentPage--);
	}

	public Page precedentPage(int currentPage) {
		return this.pages.get(currentPage++);
	}

	public Page firstPage() {
		return this.pages.get(0);
	}

	public Page lastPage() {
		return this.pages.get(pages.size() - 1);
	}

	public Page getPageIndex(int index) {
		return this.pages.get(index);
	}

}
