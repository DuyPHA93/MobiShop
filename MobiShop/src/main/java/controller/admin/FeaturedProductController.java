package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Brand;
import model.FeaturedProduct;
import model.PagingQuery;
import model.Product;
import model.ProductType;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import context.AuthAdminContext;
import dao.BrandDAO;
import dao.FeaturedProductDAO;
import dao.ProductDAO;
import dao.ProductTypeDAO;

/**
 * Servlet implementation class FeaturedProductController
 */
@WebServlet("/admin/featuredProduct")
public class FeaturedProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeaturedProductController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!AuthAdminContext.isLogin(request, response)) return;
		
    	super.service(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String page = "/admin/featured-products-list.jsp";
		
		try {
			if (action == null) {
				PagingQuery<FeaturedProduct> paging = new PagingQuery<FeaturedProduct>(request, response);
				
				List<FeaturedProduct> data = FeaturedProductDAO.search(paging);
				paging.setData(data);
				paging.setTotalRecords(FeaturedProductDAO.getTotalRecords(paging));
				paging.caltotalPage();
				request.setAttribute("paging", paging);
				
				request.getRequestDispatcher(page).forward(request, response);
				return;
			} else if (action.equals("detail")) {
				page = "/admin/featured-product-detail.jsp";
				String id = request.getParameter("id");
				
				List<Product> products = ProductDAO.getAllByActived();
				request.setAttribute("products", products);
				
				if (id != null) {
					FeaturedProduct detail = FeaturedProductDAO.findById(Integer.parseInt(id));
					
					request.setAttribute("detail", detail);
				}
			}
			
			request.getRequestDispatcher(page).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String page = "/admin/featured-product-detail.jsp";
		
		if (action == null) {
			request.getRequestDispatcher(page).forward(request, response);
			return;
		}
		
		if (action.equals("store")) {
			store(request, response);
			return;
		} else if (action.equals("delete")) {
			deleteById(request, response);
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

	private void store(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String message = validate(request, response);
			
			if (message == null) {
				if (id == null || id.equals("") || Integer.parseInt(id) <= 0) {
					// Create
					message = create(request, response);
				} else {
					// Update
					message = update(request, response);
				}
			}
			
			if (message != null) {
				// Returns input data
				FeaturedProduct detail = new FeaturedProduct(request, response);
				request.setAttribute("detail", detail);
				request.setAttribute("message", message);
				
				List<Product> products = ProductDAO.getAllByActived();
				request.setAttribute("products", products);
				
				request.getRequestDispatcher("/admin/featured-product-detail.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect(getServletContext().getContextPath() + "/admin/featuredProduct");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String validate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String product = request.getParameter("product");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String message = null;
			
			if (endDate == null || endDate.length() == 0) message = "Yêu cầu ngày kết thúc.";
			else if (isDateAfter(startDate, endDate)) message = "Ngày kết thúc phải lớn hơn hoặc bằng với ngày bắt đầu.";
			
			if (id == null || id.length() == 0 || Integer.parseInt(id) <= 0) {
				if (product == null || product.length() == 0) message = "Yêu cầu sản phẩm.";
				else if (startDate == null || startDate.length() == 0) message = "Yêu cầu ngày bắt đầu.";
			}
			
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
	}
	
	private boolean isDateAfter(String strDate1, String strDate2) {
		try {
			Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(strDate1);
			Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(strDate2);
			
			return date1.after(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	// For AJAX
	private void deleteById(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			PrintWriter out = response.getWriter();
			
			if (id == null || !FeaturedProductDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = FeaturedProductDAO.delete(Integer.parseInt(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String product = request.getParameter("product");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if (FeaturedProductDAO.checkExists(Integer.parseInt(product), 
				new SimpleDateFormat("dd/MM/yyyy").parse(startDate), 
				new SimpleDateFormat("dd/MM/yyyy").parse(endDate), -1)) {
			return "Kỳ hạn từ ngày " + "'" + startDate + "' đến ngày '" + endDate + "' đang giao với kỳ hạn khác.";
		}
		
		FeaturedProduct item = new FeaturedProduct(request, response);
		
		int id = FeaturedProductDAO.create(item);
		
		if (id < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		return null;
	}
	
	private String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		String product = request.getParameter("product");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if (FeaturedProductDAO.checkExists(id) == false) {
			return "ID " + "'" + id + "' không tồn tại.";
		}
		
		if (FeaturedProductDAO.checkExists(Integer.parseInt(product), 
				new SimpleDateFormat("dd/MM/yyyy").parse(startDate), 
				new SimpleDateFormat("dd/MM/yyyy").parse(endDate), id)) {
			return "Kỳ hạn từ ngày " + "'" + startDate + "' đến ngày '" + endDate + "' đang giao với kỳ hạn khác.";
		}
		
		FeaturedProduct item = new FeaturedProduct(request, response);
		
		int row = FeaturedProductDAO.update(item);
		
		if (row < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		return null;
	}
}
