package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.Cart;
import dao.ProductDAO;

/**
 * Servlet implementation class CartController
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null) {
			
		}
		
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null) {
			if (action.equals("addToCart")) {
				addToCart(request, response);
				return;
			}
			else if (action.equals("updateToCart")) {
				updateToCart(request, response);
				return;
			}
		}
		
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response) {
		String strProductId = request.getParameter("productId");
		String strQuantity = request.getParameter("quantity");
		String errorMsg = null;
		
		int productId = strProductId == null || strProductId.length() == 0 ? 0 : Integer.parseInt(strProductId);
		int quantity = strQuantity == null || strQuantity.length() == 0 ? 0 : Integer.parseInt(strQuantity);
		
		try {
			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");

			if (cart == null) cart = new Cart();
			
			Product itemCart = ProductDAO.findById(productId);
			
			if (itemCart == null) {
				errorMsg = "Không tìm thấy sản phẩm";
			} else {
				itemCart.setQuantity(quantity);
				cart.addItem(itemCart);
				
				session.setMaxInactiveInterval(86400);
				session.setAttribute("cart", cart);
			}
			
			
			session.setAttribute("successMsg", errorMsg == null ? "“"+ itemCart.getName() +"” đã được thêm vào giỏ hàng." : null);
			session.setAttribute("old_quantity", quantity);
			response.sendRedirect(request.getContextPath() + "/product?action=detail&id=" + itemCart.getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void updateToCart(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<String> parameterNames = request.getParameterNames();
		
		try {
			HttpSession session = request.getSession();
			Cart cart = new Cart();
			
			while (parameterNames.hasMoreElements()) {
				 
	            String paramName = parameterNames.nextElement();
	 
	            String[] paramValues = request.getParameterValues(paramName);
	            if (paramName.contains("productId_")) {
	            	int productId = Integer.parseInt(paramName.substring(paramName.indexOf("_") + 1, paramName.length()));
	            	int quantity = Integer.parseInt(paramValues[0]);
	            	
	            	Product itemCart = ProductDAO.findById(productId);
	            	itemCart.setQuantity(quantity);
					cart.addItem(itemCart);
	            }
	        }
			
			session.setMaxInactiveInterval(86400);
			session.setAttribute("cart", cart);
			
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
