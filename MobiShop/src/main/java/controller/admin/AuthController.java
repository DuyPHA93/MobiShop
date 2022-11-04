package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;

import dao.AccountDAO;

/**
 * Servlet implementation class AuthController
 */
@WebServlet("/admin/auth")
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
		String emailRemeber = getEmailRemember(request);
		
		if (action != null) {
			if (action.equals("logout")) {
				logout(request, response);
				return;
			}
				
		}
		
		request.setAttribute("emailRemeber", emailRemeber);
		request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		// Process login
		if (action.equals("login")) 
			login(request, response);
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			
			if (session != null) {
				session.invalidate();
//				request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
				response.sendRedirect(request.getContextPath() + "/admin/auth");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String loginPage = "/admin/login.jsp";
		
		try {
			// Validate
			validation(request, response);
			
			// Check login
			Account account = AccountDAO.getByEmailAndPassword(email, password);
			if (account != null && account.isAdmin()) {
				// Set Session
				setSession(request, response, account);
				// Remember
				remember(request, response, email);
				
				response.sendRedirect(request.getContextPath() + "/admin");
			} else {
				request.setAttribute("email", email);
				request.setAttribute("emailRemeber", getEmailRemember(request));
				request.setAttribute("message", "Email hoặc mật khẩu không đúng.");
				request.getRequestDispatcher(loginPage).forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void validation(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String loginPage = "/admin/login.jsp";
		String message = null;
		
		try {
			if (email.length() == 0) message = "Yêu cầu email";
			if (password.length() == 0) message = "Yêu cầu mật khẩu";
			
			if (message != null) {
				request.setAttribute("message", message);
				request.setAttribute("email", email);
				request.setAttribute("emailRemeber", getEmailRemember(request));
				request.getRequestDispatcher(loginPage).forward(request, response);
				return;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void setSession(HttpServletRequest request, HttpServletResponse response, Account account) {
		try {
			HttpSession session = request.getSession();
			// Expire in 30 minutes
			session.setMaxInactiveInterval(1800);
			session.setAttribute("loginInfo", account);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void remember(HttpServletRequest request, HttpServletResponse response, String email) {
		String remember = request.getParameter("remember");
		
		Cookie cookie = new Cookie("emailRemember", email);
		
		if (remember != null) cookie.setMaxAge(86400); // Expired in 1 day
		else cookie.setMaxAge(0);
		
		response.addCookie(cookie);
	}
	
	private String getEmailRemember(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("emailRemember")) 
				return cookie.getValue();
		}
		
		return null;
	}
}
