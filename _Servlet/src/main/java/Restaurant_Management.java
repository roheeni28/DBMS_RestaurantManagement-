

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
 * Servlet implementation class Restaurant_Management
 */
@WebServlet("/staff")
public class Restaurant_Management extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	String query;
	/**
	 * Default constructor. 
	 */
	public Restaurant_Management() {
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
		int flag=0;


		int id=Integer.parseInt(request.getParameter("id"));
		String pass=request.getParameter("pass");

		query="select * from staff where Staff_ID="+id+" and password='"+pass+"'";

		try {
			ps=con.prepareStatement(query);

			rs=ps.executeQuery(query);

			if(id==101)
			{
				if(rs.next())
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<html>");
					out.println("<body bgcolor=pink>");
					out.println("<h1>FOODIE MOODIE WELCOMES YOU</h1>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>VIEW MENU</a>");
					out.println("<br><br>");
					out.println("<br><br>");
				}
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<html>");
					out.println("<h1>Invalid ID or Password!!<h1>");
					out.println("<br><br>");
					out.println("<a href=Staff_Login.html>Login Again</a>");
				}

			}

			else
			{
				if(rs.next())
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<html>");
					out.println("<body bgcolor=pink>");
					out.println("<h1>FOODIE MOODIE WELCOMES YOU</h1>");
					out.println("<br><br>");
					out.println("<a href=menu.html>Take Orders</a>");
				}

				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<html>");
					out.println("<h1>Invalid ID or Password!!<h1>");
					out.println("<br><br>");
					out.println("<a href=Staff_Login.html>Login Again</a>");
				}
				out.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
