package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.FileUpload;
import model.PagingQuery;
import model.ProductType;
import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import context.AuthAdminContext;
import dao.FileUploadDAO;
import dao.ProductTypeDAO;
import dao.UserDAO;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/admin/user")
@MultipartConfig
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
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
		String page = "/admin/users-list.jsp";
		
		try {
			if (action == null) {
				PagingQuery<User> paging = new PagingQuery<User>(request, response);
				
				List<User> data = UserDAO.search(paging);
				paging.setData(data);
				paging.setTotalRecords(UserDAO.getTotalRecords(paging));
				paging.caltotalPage();
				request.setAttribute("paging", paging);
				
				request.getRequestDispatcher(page).forward(request, response);
				return;
			} else if (action.equals("detail")) {
				page = "/admin/user-detail.jsp";
				String id = request.getParameter("id");
				
				if (id != null) {
					User detail = UserDAO.findById(Integer.parseInt(id));
					FileUpload fileUpload = FileUploadDAO.read(Integer.parseInt(id), "User");
					if (fileUpload != null) {
						detail.setFileName(fileUpload.getName());
					}
					
					request.setAttribute("detail", detail);
				}
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
		String page = "/admin/user-detail.jsp";
		
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
				User detail = new User(request, response);
				request.setAttribute("detail", detail);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/admin/user-detail.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect(getServletContext().getContextPath() + "/admin/user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String validate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phone = request.getParameter("phone");
			String role = request.getParameter("role");
			String message = null;
			
			if (firstName == null || firstName.length() == 0) message = "Yêu cầu tên họ.";
			else if (lastName == null || lastName.length() == 0) message = "Yêu cầu tên.";
			else if (phone == null || phone.length() == 0) message = "Yêu cầu số điện thoại.";
			else if (role == null || role.length() == 0) message = "Yêu cầu vai trò.";
			
			if (id == null || id.length() == 0 || Integer.parseInt(id) <= 0) {
				if (email == null || email.length() == 0) message = "Yêu cầu email.";
				if (username == null || username.length() == 0) message = "Yêu cầu tên đăng nhập.";
				if (password == null || password.length() == 0) message = "Yêu cầu mật khẩu.";
				
			}
			
			if (password.length() > 0 && !password.equals(confirmPassword)) message = "Mật khẩu và mật khẩu xác nhận không khớp.";
			
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
			
			if (id == null || !UserDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = UserDAO.delete(Integer.parseInt(id));
				if (res < 1) {
					System.out.println("Có lỗi xảy ra ở phía máy chủ.");
				} else {
					FileUpload.delete(Integer.parseInt(id), "User");
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
			
			if (id == null || !UserDAO.checkExists(Integer.parseInt(id))) {
				System.out.println("ID không tồn tại");
			} else {
				int res = UserDAO.disable(Integer.parseInt(id));
				if (res < 1) {
					System.out.println("Có lỗi xảy ra ở phía máy chủ.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		if (UserDAO.checkExistsUsername(username)) {
			return "Tên đăng nhập " + "'" + username + "' đã tồn tại.";
		} else if (UserDAO.checkExistsEmail(email)) {
			return "Email " + "'" + email + "' đã tồn tại.";
		}
		
		User item = new User(request, response);
		
		int id = UserDAO.create(item);
		
		if (id < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		if (request.getPart("file").getSize() > 0) {
			FileUpload.create(getServletContext(),
					request.getPart("file"), 
					"admin\\assets\\images\\avatars\\", 
					id, "User");
		}
		
		return null;
	}
	
	private String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		
		if (UserDAO.checkExists(id) == false) {
			return "ID " + "'" + id + "' không tồn tại.";
		}
		
		int row = 0;
		User item = new User(request, response);
		
		if (password == null || password.length() == 0) {
			row = UserDAO.updateWithoutPassword(item);
		} else {
			row = UserDAO.update(item);
		}
		
		if (row < 1) {
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
		
		if (request.getPart("file").getSize() > 0) {
			System.out.println("Proccess upload..." + request.getPart("file").getSize());
			FileUpload.update(getServletContext(),
					request.getPart("file"), 
					"admin\\assets\\images\\avatars\\", 
					id, "User");
		}
		
		return null;
	}
	
	protected void uploadFile(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {

	    // Create path components to save the file
	    final String path = getServletContext().getInitParameter("LocalPath") + "admin\\assets\\images\\avatars";
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
