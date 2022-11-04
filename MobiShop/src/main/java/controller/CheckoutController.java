package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Order;
import model.OrderLine;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.Cart;
import context.AuthAdminContext;
import dao.OrderDAO;
import dao.OrderLineDAO;

/**
 * Servlet implementation class CheckoutController
 */
@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	Cart cart = (Cart) session.getAttribute("cart");
		if (session.getAttribute("accountInfo") == null || cart == null || cart.getList().size() == 0) {
			response.sendRedirect(request.getContextPath() + "/auth");
			return;
		}
		
    	super.service(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null) {
			
		}
		
		request.getRequestDispatcher("/checkout.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkout(request, response);
	}

	private void checkout(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (checkoutValidation(request, response) != null) return;
			
			HttpSession session = request.getSession();
			Account accountInfo = (Account) session.getAttribute("accountInfo");
			Cart cart = (Cart) session.getAttribute("cart");
			Integer result = 1;
			
			String orderNo = Order.generateOrderNo();
			Integer totalQuantity = 0;
			Double totalPrice = 0D;
			String status = "dang_cho_nhan_hang";
			Date orderDate = new Date();
			
			for(Product product : cart.getList()) {
				OrderLine orderLine = new OrderLine(orderNo, status, product);
				result *= OrderLineDAO.create(orderLine);
				totalQuantity += orderLine.getProductQuantity();
				totalPrice += orderLine.getProductTotalPrice();
			}
			
			Order order = new Order(request, response);
			order.setOrderNo(orderNo);
			order.setOrderDate(orderDate);
			order.setPersonOrderId(accountInfo.getId());
			order.setStatus(status);
			order.setTotalQuantity(totalQuantity);
			order.setTotalPrice(totalPrice);
			
			result *= OrderDAO.create(order);
			
			if (result > 0) {
				request.setAttribute("orderNo", orderNo);
				request.setAttribute("orderDate", orderDate);
				request.getRequestDispatcher("order-success.jsp").forward(request, response);
				session.setAttribute("cart", new Cart());
			} else {
				PrintWriter out = response.getWriter();
				out.println("Có lỗi xảy ra ở phía máy chủ !");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String checkoutValidation(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String note = request.getParameter("note");
		String message = null;
		
		try {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
			Matcher matcher = pattern.matcher(email);
			
			if (email == null || email.length() == 0) message = "Yêu cầu email.";
			else if (!matcher.matches()) message = "Email không hợp lệ.";
			else if (phone == null || phone.length() == 0) message = "Yêu cầu số điện thoại.";
			else if (address == null || address.length() == 0) message = "Yêu cầu địa chỉ giao hàng.";
			else if (lastName == null || lastName.length() == 0) message = "Yêu cầu tên.";
			else if (firstName == null || firstName.length() == 0) message = "Yêu cầu yêu cầu họ tên.";
			
			if (message != null) {
				request.setAttribute("message", message);
				request.setAttribute("firstName", firstName);
				request.setAttribute("lastName", lastName);
				request.setAttribute("address", address);
				request.setAttribute("phone", phone);
				request.setAttribute("email", email);
				request.setAttribute("note", note);
				request.getRequestDispatcher("/checkout.jsp").forward(request, response);
			}
			
			return message;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "Có lỗi xảy ra ở phía máy chủ.";
		}
	}
}
