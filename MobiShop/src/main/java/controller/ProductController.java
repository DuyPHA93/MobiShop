package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Brand;
import model.PagingQuery;
import model.Product;
import model.ProductType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import dao.BrandDAO;
import dao.ProductDAO;
import dao.ProductTypeDAO;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/product")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action == null || action.equals("search") || action.equals("all")) {
			search(request, response);
		} else if (action.equals("brand")) {
			searchByCategory(request, response, true);
		} else if (action.equals("productType")) {
			searchByCategory(request, response, false);
		} else if (action.equals("detail")) {
			detail(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Product detail = null;
		
		try {
			PrintWriter out = response.getWriter();
			
			if (id == null || id.length() == 0) {
				out.println("ID không hợp lệ: ");
				return;
			}
			
			detail = ProductDAO.findById(Integer.parseInt(id));
			
			if (detail == null) {
				out.println("Không tìm thấy sản phẩm !");
				return;
			}
			
			request.setAttribute("detail", detail);
			request.getRequestDispatcher("/detail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void search(HttpServletRequest request, HttpServletResponse response) {
		String query = request.getParameter("query");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		String strCurrPage = request.getParameter("page");
		int currentPage = strCurrPage == null || strCurrPage.length() == 0 ? 1 : Integer.parseInt(strCurrPage);
		
		try {
			PagingQuery<Product> paging = new PagingQuery<Product>(currentPage, -1, 16, query, sort, order);
			List<Product> data = ProductDAO.searchProducts(paging);
			paging.setTotalRecords(data.size());
			paging.caltotalPage();
			
			int offset = paging.getPerPage() * (paging.getCurrentPage() - 1);
			int limit = paging.getPerPage();
			
			List<Product> dataPerPage = data.stream().skip(offset).limit(limit).collect(Collectors.toList());
			paging.setData(dataPerPage);
			
			request.setAttribute("paging", paging);
			request.getRequestDispatcher("/products.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void searchByCategory(HttpServletRequest request, HttpServletResponse response, boolean isBrand) {
		String strId = request.getParameter("id");
		int id = strId == null || strId.length() == 0 ? -1 : Integer.parseInt(strId);
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		String strCurrPage = request.getParameter("page");
		int currentPage = strCurrPage == null || strCurrPage.length() == 0 ? 1 : Integer.parseInt(strCurrPage);
		
		try {
			PagingQuery<Product> paging = new PagingQuery<Product>(currentPage, -1, 16, null, sort, order);
			List<Product> data = ProductDAO.searchByCategory(paging, isBrand, id);
			paging.setTotalRecords(data.size());
			paging.caltotalPage();
			
			int offset = paging.getPerPage() * (paging.getCurrentPage() - 1);
			int limit = paging.getPerPage();
			
			List<Product> dataPerPage = data.stream().skip(offset).limit(limit).collect(Collectors.toList());
			paging.setData(dataPerPage);
			
			if (isBrand) {
				Brand brand = BrandDAO.findById(id);
				request.setAttribute("productTypeId", brand.getProductTypeId());
				request.setAttribute("productTypeName", brand.getProductTypeName());
				request.setAttribute("brandName", brand.getName());
			} else {
				ProductType productType = ProductTypeDAO.findById(id);
				request.setAttribute("productTypeName", productType.getName());
			}
			
			request.setAttribute("paging", paging);
			request.getRequestDispatcher("/products.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
