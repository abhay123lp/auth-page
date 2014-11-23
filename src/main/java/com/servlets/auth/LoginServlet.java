package com.servlets.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.utils.JDBCUtils;
import com.utils.SecurityUtils;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -1356158742266398064L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

/*		String salt = getSalt();
		System.out.println(salt);
		String hashedPassword = hashPassword(password, salt);
		System.out.println(password);*/

		if (authorize(username, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", username);
			session.setMaxInactiveInterval(30 * 60);
			response.sendRedirect(request.getServletContext().getContextPath() + "/protected/welcome.jsp");
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/public/login.jsp");
			request.setAttribute("error", "font-size: small; color: red;");
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			rd.forward(request, response);
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/public/login.jsp");
		rd.forward(request, response);
	}

	private boolean authorize(String username, String password) {
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = JDBCUtils.getConnection();
			rs = connection
					.createStatement()
					.executeQuery(
							String.format(
									"select * from USERS where USERS_LOGIN='%s'",
									username, password));
			if (rs.next()) {
				String salt = rs.getString("USERS_SALT");
				String generatedPassword = SecurityUtils.hashPassword(password, salt);
				String storedPassword = rs.getString("USERS_PASSWORD");
				if (storedPassword.equals(generatedPassword))
				{
					return true;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
