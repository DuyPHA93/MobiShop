package model;

import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class User {
	private Integer id;
	private String email;
	private String username;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private String phone;
	private Integer roleId;
	private String roleName;
	private String fileName;
	private boolean active;
	private Date createdAt;
	private Date updatedAt;
	
	public User() {}

	public User(Integer id, String email, String username, String password, String confirmPassword, String firstName,
			String lastName, String phone, Integer roleId, String roleName, boolean active, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.roleId = roleId;
		this.roleName = roleName;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public User(HttpServletRequest request, HttpServletResponse response) {
		try {
			String strId = request.getParameter("id");
			this.id = strId == null || strId.equals("")  ? null : Integer.parseInt(strId);
			this.email = request.getParameter("email");
			this.username = request.getParameter("username");
			this.password = request.getParameter("password");
			this.firstName = request.getParameter("firstName");
			this.lastName = request.getParameter("lastName");
			this.phone = request.getParameter("phone");
			String strRole = request.getParameter("role");
			this.roleId = strRole == null || strRole.equals("") ? -1 : Integer.parseInt(strRole);
			this.active = request.getParameter("active") == null ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
