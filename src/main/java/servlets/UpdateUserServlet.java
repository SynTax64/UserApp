package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateServlet")
public class UpdateUserServlet extends HttpServlet {

	private static final long serialVersionUID = -855707995453903659L;
	Connection connection;

	public void init() {
		try {
			System.out.println("updateServlet -> init()");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "mydb", "mydb");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("updateServlet -> doPost()");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			Statement statement = connection.createStatement();
			int result = statement
					.executeUpdate("UPDATE user set password='" + password + "' WHERE email='" + email + "'");

			PrintWriter writer = response.getWriter();

			if (result > 0) {
				writer.print("<h1>Password Updated</h1>");

			} else {
				writer.print("<h1>Error updating the password</h1>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			System.out.println("updateServlet -> destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
