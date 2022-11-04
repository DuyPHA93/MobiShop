package controller.admin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Brand;
import model.FileUpload;
import model.PagingQuery;
import model.Product;
import model.ProductType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import context.AuthAdminContext;
import dao.BrandDAO;
import dao.FileUploadDAO;
import dao.ProductDAO;
import dao.ProductTypeDAO;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/admin/product")
@MultipartConfig
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
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
		String page = "/admin/products-list.jsp";
		
		try {
			List<ProductType> productTypes = ProductTypeDAO.getAllByActived();
			request.setAttribute("productTypes", productTypes);
			
			if (action == null) {
				PagingQuery<Product> paging = new PagingQuery<Product>(request, response);
				
				List<Product> data = ProductDAO.search(paging);
				paging.setData(data);
				paging.setTotalRecords(ProductDAO.getTotalRecords(paging));
				paging.caltotalPage();
				request.setAttribute("paging", paging);
				
				request.getRequestDispatcher(page).forward(request, response);
				return;
			} else if (action.equals("detail")) {
				page = "/admin/product-detail.jsp";
				String id = request.getParameter("id");
				List<Brand> brands = new ArrayList<>();
				
				if (id != null) {
					Product detail = ProductDAO.findById(Integer.parseInt(id));
					brands = BrandDAO.getByProductTypeId(detail.getProductTypeId());
//					FileUpload fileUpload = FileUploadDAO.read(Integer.parseInt(id), "Product");
//					if (fileUpload != null) {
//						detail.setFileName(fileUpload.getName());
//					}
					
					request.setAttribute("detail", detail);
				}
				
				request.setAttribute("brands", brands);
			} else {
				
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
		String page = "/admin/product-detail.jsp";
		
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
				Product detail = new Product(request, response);
				request.setAttribute("detail", detail);
				request.setAttribute("message", message);
				
				List<ProductType> productTypes = ProductTypeDAO.getAllByActived();
				request.setAttribute("productTypes", productTypes);
				
				List<Brand> brands = BrandDAO.getAllByActived();
				request.setAttribute("brands", brands);
				
				request.getRequestDispatcher("/admin/product-detail.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect(getServletContext().getContextPath() + "/admin/product");
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
			String description = request.getParameter("description");
			String price = request.getParameter("price");
			String productType = request.getParameter("productType");
			String brand = request.getParameter("brand");
			String quantity = request.getParameter("quantity");
			Part filePart = request.getPart("file");
			String message = null;
			
			if (name == null || name.length() == 0) message = "Yêu cầu tên sản phẩm.";
			else if (description == null || description.length() == 0) message = "Yêu cầu mô tả.";
			else if (price == null || price.length() == 0 || Double.parseDouble(price) <= 0) message = "Yêu cầu giá.";
			else if (productType == null || productType.length() == 0 || Integer.parseInt(productType) < 0) message = "Yêu cầu danh mục.";
			else if (quantity == null || quantity.length() == 0 || Integer.parseInt(quantity) < 0) message = "Yêu cầu số lượng trong kho.";
			
			if (id == null || id.length() == 0 || Integer.parseInt(id) <= 0) {
				if (code == null || code.length() == 0) message = "Yêu cầu mã sản phẩm.";
				else if (filePart.getSize() == 0) message = "Yêu cầu file ảnh.";
				
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
			
			if (id == null || !ProductDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = ProductDAO.delete(Integer.parseInt(id));
				if (res < 1) {
					System.out.println("Có lỗi xảy ra ở phía máy chủ.");
				} else {
					FileUpload.delete(Integer.parseInt(id), "Product");
				}
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
			
			if (id == null || !ProductDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = ProductDAO.disable(Integer.parseInt(id));
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
		
		if (ProductDAO.checkExists(code)) {
			return "Mã " + "'" + code + "' đã tồn tại.";
		}
		
		Product item = new Product(request, response);
		
		int id = ProductDAO.create(item);
		
		if (id < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		if (request.getPart("file").getSize() > 0) {
			FileUpload.create(getServletContext(),
					request.getPart("file"), 
					"assets\\images\\products\\", 
					id, "Product");
		}
		
		return null;
	}
	
	private String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		
		if (ProductDAO.checkExists(id) == false) {
			return "ID " + "'" + id + "' không tồn tại.";
		}
		
		Product item = new Product(request, response);
		
		int row = ProductDAO.update(item);
		
		if (row < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		if (request.getPart("file").getSize() > 0) {
			System.out.println("Proccess upload..." + request.getPart("file").getSize());
			FileUpload.update(getServletContext(),
					request.getPart("file"), 
					"assets\\images\\products\\", 
					id, "Product");
		}
		
		return null;
	}
	
	protected void uploadFile(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {

	    // Create path components to save the file
	    final String path = getServletContext().getInitParameter("LocalPath") + "assets\\images\\products";
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);

	    OutputStream out = null;
	    InputStream filecontent = null;

	    try {
	        out = new FileOutputStream(new File(path + File.separator
	                + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        System.out.println("New file " + fileName + " created at " + path);
	    } catch (FileNotFoundException fne) {
	    	System.out.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	    	System.out.println("ERROR: " + fne.getMessage());
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
	}

	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
}
