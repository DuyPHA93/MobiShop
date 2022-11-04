package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Brand;
import model.Order;
import model.OrderLine;
import model.PagingQuery;
import model.Product;
import model.ProductType;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import context.AuthAdminContext;
import dao.BrandDAO;
import dao.OrderDAO;
import dao.OrderLineDAO;
import dao.ProductDAO;
import dao.ProductTypeDAO;
import dao.UserDAO;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/admin/order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Order detail = null;
	private User personOrder = null;
	private List<OrderLine> products = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!AuthAdminContext.isLogin(request, response))
			return;

		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String page = "/admin/orders-list.jsp";

		try {
			if (action == null) {
				PagingQuery<Order> paging = new PagingQuery<Order>(request, response);

				List<Order> data = OrderDAO.search(paging);
				paging.setData(data);
				paging.setTotalRecords(OrderDAO.getTotalRecords(paging));
				paging.caltotalPage();
				request.setAttribute("paging", paging);

				request.getRequestDispatcher(page).forward(request, response);
				return;
			} else if (action.equals("detail")) {
				page = "/admin/order-detail.jsp";
				String id = request.getParameter("id");

				if (id != null) {
					detail = OrderDAO.findById(Integer.parseInt(id));
					personOrder = UserDAO.findById(detail.getPersonOrderId());
					products = OrderLineDAO.findByOrderNo(detail.getOrderNo());

					request.setAttribute("detail", detail);
					request.setAttribute("personOrder", personOrder);
					request.setAttribute("products", products);
				}
			}

			request.getRequestDispatcher(page).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action != null) {
			if (action.equals("receiveOrder")) {
				receiveOrder(request, response);
				return;
			} else if (action.equals("deliveryOrder")) {
				deliveryOrder(request, response);
				return;
			} else if (action.equals("deliveredOrder")) {
				confirmDeliveredOrder(request, response);
				return;
			} else if (action.equals("completeOrder")) {
				completeOrder(request, response);
				return;
			} else if (action.equals("cancelOrder")) {
				cancelOrder(request, response);
				return;
			}
		}

		request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
	}

	private void receiveOrder(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String orderNo = request.getParameter("orderNo");
		Integer result = 1;

		try {
			Order order = OrderDAO.findById(Integer.parseInt(id));

			if (order == null || !order.getStatus().equals("dang_cho_nhan_hang")) {
				request.setAttribute("message", "Có gì đó không đúng.");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
				return;
			}

			result *= OrderDAO.updateReceivedOrderStatus(orderNo);
			result *= OrderLineDAO.updateReceivedOrderStatus(orderNo);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/order");
			} else {
				request.setAttribute("message", "Có lỗi xảy ra ở phía máy chủ");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deliveryOrder(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String orderNo = request.getParameter("orderNo");
		String warehousePickup = request.getParameter("warehousePickup");
		String shippingCode = request.getParameter("shippingCode");
		String transporter = request.getParameter("transporter");
		String totalWeight = request.getParameter("totalWeight");
		Integer result = 1;

		try {
			if (validateDeliveryOrder(request, response) != null) return;

			Order order = OrderDAO.findById(Integer.parseInt(id));

			if (order == null || !order.getStatus().equals("da_nhan_hang")) {
				request.setAttribute("message", "Có gì đó không đúng.");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
				return;
			}

			order.setWarehousePickup(warehousePickup);
			order.setShippingCode(shippingCode);
			order.setTransporter(transporter);
			order.setTotalWeight(Double.parseDouble(totalWeight));

			result *= OrderDAO.updateDeliveryOrderStatus(orderNo, order);
			result *= OrderLineDAO.updateDeliveryOrderStatus(orderNo);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/order");
			} else {
				request.setAttribute("message", "Có lỗi xảy ra ở phía máy chủ");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void confirmDeliveredOrder(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String orderNo = request.getParameter("orderNo");
		Integer result = 1;

		try {
			Order order = OrderDAO.findById(Integer.parseInt(id));

			if (order == null || !order.getStatus().equals("dang_giao_hang")) {
				request.setAttribute("message", "Có gì đó không đúng.");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
				return;
			}

			result *= OrderDAO.updateDeliveredOrderStatus(orderNo);
			result *= OrderLineDAO.updateDeliveredOrderStatus(orderNo);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/order");
			} else {
				request.setAttribute("message", "Có lỗi xảy ra ở phía máy chủ");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void completeOrder(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String orderNo = request.getParameter("orderNo");
		Integer result = 1;

		try {
			Order order = OrderDAO.findById(Integer.parseInt(id));

			if (order == null || !order.getStatus().equals("da_giao_hang")) {
				request.setAttribute("message", "Có gì đó không đúng.");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
				return;
			}

			result *= OrderDAO.updateCompleteOrderStatus(orderNo);
			result *= OrderLineDAO.updateCompleteOrderStatus(orderNo);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/order");
			} else {
				request.setAttribute("message", "Có lỗi xảy ra ở phía máy chủ");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String orderNo = request.getParameter("orderNo");
		String reasonCancelOrder = request.getParameter("reasonCancelOrder");
		Integer result = 1;

		try {
			if (validateCancelOrder(request, response) != null) return;

			Order order = OrderDAO.findById(Integer.parseInt(id));

			if (order == null || !order.getStatus().equals("dang_cho_nhan_hang")) {
				request.setAttribute("message", "Có gì đó không đúng.");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
				return;
			}

			result *= OrderDAO.updateCancelOrderStatus(orderNo, reasonCancelOrder);
			result *= OrderLineDAO.updateCancelOrderStatus(orderNo);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/order");
			} else {
				request.setAttribute("message", "Có lỗi xảy ra ở phía máy chủ");
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String validateDeliveryOrder(HttpServletRequest request, HttpServletResponse response) {
		String warehousePickup = request.getParameter("warehousePickup");
		String shippingCode = request.getParameter("shippingCode");
		String transporter = request.getParameter("transporter");
		String totalWeight = request.getParameter("totalWeight");
		String message = null;

		try {
			if (totalWeight == null || totalWeight.length() == 0) message = "Yêu cầu tổng khối lượng.";
			else if (transporter == null || transporter.length() == 0) message = "Yêu cầu nhà vận chuyển.";
			else if (shippingCode == null || shippingCode.length() == 0) message = "Yêu cầu mã vận chuyển.";
			else if (warehousePickup == null || warehousePickup.length() == 0) message = "Yêu cầu kho lấy hàng.";

			if (message != null) {
				request.setAttribute("message", message);
				request.setAttribute("detail", detail);
				request.setAttribute("personOrder", personOrder);
				request.setAttribute("products", products);
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}

			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return "Có gì đó không đúng.";
		}
	}

	public String validateCancelOrder(HttpServletRequest request, HttpServletResponse response) {
		String reasonCancelOrder = request.getParameter("reasonCancelOrder");
		String message = null;

		try {
			if (reasonCancelOrder == null || reasonCancelOrder.length() == 0) message = "Yêu cầu nhập lý do từ chối.";
			
			if (message != null) {
				request.setAttribute("message", message);
				request.setAttribute("detail", detail);
				request.setAttribute("personOrder", personOrder);
				request.setAttribute("products", products);
				request.getRequestDispatcher("/admin/order-detail.jsp").forward(request, response);
			}
			
			return message;

		} catch (Exception e) {
			e.printStackTrace();
			return "Có gì đó không đúng.";
		}
	}
}
