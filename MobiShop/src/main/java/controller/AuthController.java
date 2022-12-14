package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.FileUpload;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;

import dao.AccountDAO;
import dao.UserDAO;

/**
 * Servlet implementation class AuthController
 */
@WebServlet("/auth")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String userRemember = getUsernameRemember(request);
		
		if (action != null) {
			if (action.equals("logout")) {
				logout(request, response);
				return;
			}
			else if (action.equals("register")) {
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			} else if (action.equals("checkLoginAjax")) {
				checkLoginAjax(request, response);
				return;
			}
		}
		
		request.setAttribute("userRemember", userRemember);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action == null) {
			login(request, response);
		} else if (action.equals("register")) {
			register(request, response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			
			if (session != null) {
				session.invalidate();
				response.sendRedirect(request.getContextPath() + "/home");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		
		try {
			if (registerValidation(request, response) != null) return;
			
			User item = new User(request, response);
			item.setRoleId(2);
			item.setActive(true);
			
			int id = UserDAO.create(item);
			
			if (id < 1) {
				message = "C?? l???i x???y ra ??? ph??a m??y ch???.";
			}
			
			if (message == null) {
				response.sendRedirect(request.getContextPath() + "/auth");	
			} else {
				request.setAttribute("message", message);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String loginPage = "/login.jsp";
		
		try {
			// Validate
			loginValidation(request, response);
			
			Account account = null;
			
			// Check login
			account = AccountDAO.getByEmailAndPassword(username, password);
			if (account == null) {
				account = AccountDAO.getByUsernameAndPassword(username, password);
			}
			
			if (account != null) {
				// Set Session
				setSession(request, response, account);
				// Remember
				remember(request, response, username);
				
				response.sendRedirect(request.getContextPath() + "/home");
			} else {
				request.setAttribute("username", username);
				request.setAttribute("userRemember", getUsernameRemember(request));
				request.setAttribute("message", "Email ho???c m???t kh???u kh??ng ????ng.");
				request.getRequestDispatcher(loginPage).forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void checkLoginAjax(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();
			Account account = (Account) session.getAttribute("accountInfo");
			if (account == null) {
				out.println(false);
			} else {
				out.println(true);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void loginValidation(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String loginPage = "/login.jsp";
		String message = null;
		
		try {
			if (username.length() == 0) message = "Y??u c???u t??n t??i kho???n ho???c ?????a ch??? email.";
			if (password.length() == 0) message = "Y??u c???u m???t kh???u.";
			
			if (message != null) {
				request.setAttribute("message", message);
				request.setAttribute("email", username);
				request.setAttribute("userRemember", getUsernameRemember(request));
				request.getRequestDispatcher(loginPage).forward(request, response);
				return;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String registerValidation(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("re-password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String message = null;
		
		try {
			if (firstName == null || firstName.length() == 0) message = "Y??u c???u t??n h???.";
			else if (lastName == null || lastName.length() == 0) message = "Y??u c???u t??n.";
			else if (phone == null || phone.length() == 0) message = "Y??u c???u s??? ??i???n tho???i.";
			else if (email == null || email.length() == 0) message = "Y??u c???u email.";
			else if (username == null || username.length() == 0) message = "Y??u c???u t??n ????ng nh???p.";
			else if (password == null || password.length() == 0) message = "Y??u c???u m???t kh???u.";
			else if (password.length() > 0 && !password.equals(confirmPassword)) message = "M???t kh???u v?? m???t kh???u x??c nh???n kh??ng kh???p.";
			
			if (message == null) {
				if (UserDAO.checkExistsUsername(username)) {
					message = "T??n ????ng nh???p " + "'" + username + "' ???? t???n t???i.";
				} else if (UserDAO.checkExistsEmail(email)) {
					message = "Email " + "'" + email + "' ???? t???n t???i.";
				}
			}
			
			if (message != null) {
				request.setAttribute("message", message);
				request.setAttribute("email", email);
				request.setAttribute("username", username);
				request.setAttribute("firstName", firstName);
				request.setAttribute("lastName", lastName);
				request.setAttribute("phone", phone);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
			
			return message;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "C?? l???i x???y ra ??? ph??a m??y ch???.";
		}
	}
	
	private void setSession(HttpServletRequest request, HttpServletResponse response, Account account) {
		try {
			HttpSession session = request.getSession();
			// Expire in 30 minutes
			session.setMaxInactiveInterval(1800);
			session.setAttribute("accountInfo", account);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void remember(HttpServletRequest request, HttpServletResponse response, String email) {
		String remember = request.getParameter("remember");
		
		Cookie cookie = new Cookie("userRemember", email);
		
		if (remember != null) cookie.setMaxAge(86400); // Expired in 1 day
		else cookie.setMaxAge(0);
		
		response.addCookie(cookie);
	}
	
	private String getUsernameRemember(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("userRemember")) 
				return cookie.getValue();
		}
		
		return null;
	}
}
