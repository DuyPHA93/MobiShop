package model;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PagingQuery<T> {
	private int currentPage;
	private int totalPage;
	private int perPage;
	private int totalRecords;
	private String query;
	private String sort;
	private String order;
	private List<T> data;
	
	public PagingQuery(int currentPage, int totalPage, int perPage, String query, String sort, String order) {
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.perPage = perPage;
		this.query = query;
		this.sort = sort;
		this.order = order;
	}

	public PagingQuery(int currentPage, int totalPage, int perPage, String query) {
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.perPage = perPage;
		this.query = query;
	}
	
	public PagingQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.currentPage = request.getParameter("page") == null || request.getParameter("page") == "" ? 1 : Integer.parseInt(request.getParameter("page"));
		this.perPage = request.getParameter("perPage") == null || request.getParameter("perPage") == "" ? 10 : Integer.parseInt(request.getParameter("perPage"));
		this.query = request.getParameter("search") == null ? "" : request.getParameter("search");
	}
	
	public void caltotalPage() {
		int mode = this.totalRecords % this.perPage;
		this.totalPage = (this.totalRecords / (this.perPage));
		
		if (mode > 0) {
			this.totalPage++;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
