

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Existing_Customer
 */
@WebServlet("/existing_customer")
public class Existing_Customer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	String query;
	/**
	 * Default constructor. 
	 */
	public Existing_Customer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject","root","root");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {

			con.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();

		String input=request.getParameter("z");

		if(input.equals("Submit"))
		{
			int id=Integer.parseInt(request.getParameter("id"));
			String pass=request.getParameter("pass");

			if(pass.length()!=10)
			{
				out.println("<body bgcolor=\"#9A616D\">");
				out.println("<h1>Wrong Password!!</h1>");
				out.println("<br><br>");
				out.println("<h1><a href=customer.html>Login Again</a></h1>");
			}
			query="select * from customer where c_id=? and mobile_no=?";

			try {
				ps=con.prepareStatement(query);

				ps.setInt(1, id);
				ps.setString(2, pass);

				rs=ps.executeQuery();

				if(rs.next())
				{
					out.println("<body bgcolor=#9A616D>");
					out.println("<br><br>");
					out.println("<table>");
					out.println("<tr>");
					out.println("<td><a href=menu.html>ORDER ONLINE</a></td>");
					out.println("</tr>");
					out.println("<td><a href=Reserve.html>RESERVE A TABLE</a></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td><a href=Home.html>Go To Home Page</a></td>");
					out.println("</tr>");
					out.println("</table>");
				}
				else
				{
					out.println("<body bgcolor= #9A616D>");
					out.println("<br><br>");
					out.println("<h1>Wrong Password Or ID!!</h1>");
					out.println("<br><br>");
					out.println("<h1><a href=customer.html>Login Again</a></h1>");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
