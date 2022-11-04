package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Brand;
import model.FileUpload;
import model.PagingQuery;
import model.ProductType;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import context.AuthAdminContext;
import dao.BrandDAO;
import dao.FileUploadDAO;
import dao.ProductTypeDAO;

/**
 * Servlet implementation class BrandController
 */
@WebServlet("/admin/brand")
public class BrandController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrandController() {
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
		String page = "/admin/brands-list.jsp";
		
		try {
			List<ProductType> productTypes = ProductTypeDAO.getAllByActived();
			request.setAttribute("productTypes", productTypes);
			
			if (action == null) {
				PagingQuery<Brand> paging = new PagingQuery<Brand>(request, response);
				
				List<Brand> data = BrandDAO.search(paging);
				paging.setData(data);
				paging.setTotalRecords(BrandDAO.getTotalRecords(paging));
				paging.caltotalPage();
				request.setAttribute("paging", paging);
				
				request.getRequestDispatcher(page).forward(request, response);
				return;
			} else if (action.equals("detail")) {
				page = "/admin/brand-detail.jsp";
				String id = request.getParameter("id");
				
				if (id != null) {
					Brand detail = BrandDAO.findById(Integer.parseInt(id));
					
					request.setAttribute("detail", detail);
				}
			} else if (action.equals("getBrandAjax")) {
				printBrandSelectHTML(request, response);
				return;
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
		String page = "/admin/brand-detail.jsp";
		
		if (action == null) {
			request.getRequestDispatcher(page).forward(request, response);
			return;
		}
		
		if (action.equals("store")) {
			store(request, response);
			return;
		} else if (action.equals("delete")) {
			deleteById(request, response);
		} else if (action.equals("disable")) {
			disableById(request, response);
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
				Brand detail = new Brand(request, response);
				request.setAttribute("detail", detail);
				request.setAttribute("message", message);
				
				List<ProductType> productTypes = ProductTypeDAO.getAllByActived();
				request.setAttribute("productTypes", productTypes);
				
				request.getRequestDispatcher("/admin/brand-detail.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect(getServletContext().getContextPath() + "/admin/brand");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String validate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String code = request.getParameter("code");
			String name = request.getParameter("name");
			String productType = request.getParameter("productType");
			String message = null;
			
			if (name == null || name.length() == 0) message = "Yêu cầu tên nhãn hiệu.";
			else if (productType == null || productType.length() == 0)  message = "Yêu cầu loại sản phẩm.";
			
			if (id == null || id.length() == 0 || Integer.parseInt(id) <= 0) {
				if (code == null || code.length() == 0) message = "Yêu cầu mã nhãn hiệu.";
			}
			
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
	}
	
	// For AJAX
	private void deleteById(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			PrintWriter out = response.getWriter();
			
			if (id == null || !BrandDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = BrandDAO.delete(Integer.parseInt(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// For AJAX
	private void disableById(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			PrintWriter out = response.getWriter();
			
			if (id == null || !BrandDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = BrandDAO.disable(Integer.parseInt(id));
				if (res < 1) {
					System.out.println("Có lỗi xảy ra ở phía máy chủ.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String strProductType = request.getParameter("productType");
		Integer productType = strProductType == null || strProductType.length() == 0 ? null : Integer.parseInt(strProductType);
		boolean active = request.getParameter("active") == null ? false : true;
		
		if (BrandDAO.checkExists(code)) {
			return "Mã " + "'" + code + "' đã tồn tại.";
		}
		
		Brand item = new Brand(null, code, name, active, productType, null, new Date(), null);
		
		int id = BrandDAO.create(item);
		
		if (id < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		return null;
	}
	
	private String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String strProductType = request.getParameter("productType");
		Integer productType = strProductType == null || strProductType.length() == 0 ? null : Integer.parseInt(strProductType);
		boolean active = request.getParameter("active") == null ? false : true;
		
		if (BrandDAO.checkExists(id) == false) {
			return "ID " + "'" + id + "' không tồn tại.";
		}
		
		Brand item = new Brand(id, code, name, active, productType, null, null, new Date());
		
		int row = BrandDAO.update(item);
		
		if (row < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		return null;
	}
	
	private List<Brand> getByProductTypeId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String productTypeId = request.getParameter("productTypeId");
			return BrandDAO.getByProductTypeId(Integer.parseInt(productTypeId));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	private void printBrandSelectHTML(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			List<Brand> list = getByProductTypeId(request, response);
			String html = "<option value=''></option>";
			for(Brand item : list) {
				html +="<option value='" + item.getId() + "'>" + item.getName() + "</option>";
			}
			out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
