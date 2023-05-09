

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
 * Servlet implementation class ShoppingApplication
 */
@WebServlet("/shop")
public class ShoppingApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	int code;
	int qty;
	String query;
	/**
	 * Default constructor. 
	 */
	public ShoppingApplication() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library1662","root","root");

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

		String ip=request.getParameter("x");

		if(ip.equals("Add Item"))
		{	
			query="insert into my_shop values(?,?)";

			try {
				ps=con.prepareStatement(query);

				code=Integer.parseInt(request.getParameter("pcode"));
				qty=Integer.parseInt(request.getParameter("t1"));

				ps.setInt(1, code);
				ps.setInt(2, qty);
				int flag=ps.executeUpdate();

				if(flag==1)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<html>");
					out.println("<body bgcolor=green>");
					out.println("Record Inserted Successfully!!");
					out.println("<br><br>");
					out.println("<a href=Shopping.html>Want To Shop Again?</a>");
				}
				out.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(ip.equals("Remove Item"))
		{
			query="delete from my_shop where code=?";
			
			try {
				ps=con.prepareStatement(query);
				
				code=Integer.parseInt(request.getParameter("pcode"));
				ps.setInt(1, code);
				int flag=ps.executeUpdate();
				
				if(flag==1)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<html>");
					out.println("<body bgcolor=green>");
					out.println("Record Deleted Successfully!!");
					out.println("<br><br>");
					out.println("<a href=Shopping.html>Want To Shop Again?</a>");
				}
				out.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(ip.equals("Show Item"))
		{
			query="select * from my_shop";
			
			try {
				
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();
				
				out.println("<h1>Product Code     Product Quantity</h1>");
				
				while(rs.next())
				{
					out.println("<h1>"+rs.getInt(1)+"</h1>"+rs.getInt(2));
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


	}

}
