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

import java.io.IOException;
import java.util.List;

import bean.Cart;
import dao.OrderDAO;
import dao.OrderLineDAO;
import dao.UserDAO;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		if (session.getAttribute("accountInfo") == null) {
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
			if (action.equals("detail")) {
				renderOrderDetailPage(request, response);
				return;
			}
		}
		
		renderMyOrderPage(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void renderMyOrderPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Account accountInfo = (Account) session.getAttribute("accountInfo");
			
			List<Order> orders = OrderDAO.findByUserId(accountInfo.getId());
			for(Order item : orders) {
				List<OrderLine> orderLines = OrderLineDAO.findByOrderNo(item.getOrderNo());
				item.setFileName(orderLines.get(0).getFileName());
			}
			
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("my-order.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void renderOrderDetailPage(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		
		try {
			Order detail = OrderDAO.findById(Integer.parseInt(id));
			List<OrderLine> products = OrderLineDAO.findByOrderNo(detail.getOrderNo());
			
			request.setAttribute("detail", detail);
			request.setAttribute("products", products);
			request.getRequestDispatcher("order-detail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
