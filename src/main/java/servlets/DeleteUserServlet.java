package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 3819791547198600769L;
	Connection connection;

	public void init() {
		try {
			System.out.println("deleteServlet -> init()");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "mydb", "mydb");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("deleteServlet -> doPost()");
		String email = request.getParameter("email");
		try {
			Statement statement = connection.createStatement();
			PrintWriter writer = response.getWriter();
			int result = statement.executeUpdate("DELETE FROM user WHERE email='" + email + "'");
			if (result > 0) {
				writer.print("<h2>User deleted</h2>");
			} else {
				writer.print("<h2>User not found in database</h2>");
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			System.out.println("deleteServlet -> destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
