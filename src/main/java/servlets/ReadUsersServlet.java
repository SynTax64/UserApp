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

@WebServlet("/readServlet")
public class ReadUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 3819791547198600769L;
	Connection connection;

	public void init() {
		try {
			System.out.println("readServlet -> init()");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "mydb", "mydb");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("readServlet -> doPost()");

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM user");

			PrintWriter writer = response.getWriter();

			writer.print("<table>");
			writer.print("<tr>");
			writer.print("<th>");
			writer.println("First Name");
			writer.print("</th>");
			writer.print("<th>");
			writer.println("Last Name");
			writer.print("</th>");
			writer.print("<th>");
			writer.println("Email");
			writer.print("</th>");
			writer.print("</tr>");
			while (result.next()) {
				writer.println("<tr>");
				writer.println("<td>");
				writer.print(result.getString("firstName"));
				writer.println("<td>");
				writer.print(result.getString("lastName"));
				writer.println("</td>");
				writer.println("<td>");
				writer.print(result.getString("email"));
				writer.println("</td>");
				writer.println("</tr>");
			}
			writer.print("</table>");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			System.out.println("readServlet -> destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
