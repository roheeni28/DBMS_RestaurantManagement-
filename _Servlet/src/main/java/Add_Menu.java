import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Add_Menu
 */
@WebServlet("/add_menu")
public class Add_Menu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	String query;
	/**
	 * Default constructor. 
	 */
	public Add_Menu() {
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

		String input=request.getParameter("x");

		if(input.equals("ADD ITEM"))
		{
			int mid=Integer.parseInt(request.getParameter("id"));
			String name=request.getParameter("name");
			String type=request.getParameter("type");
			float cost=Float.parseFloat(request.getParameter("cost"));

			query="insert into menu values(?,?,?,?)";

			try {

				ps=con.prepareStatement(query);
				ps.setInt(1, mid);
				ps.setString(2, name);
				ps.setString(3, type);
				ps.setFloat(4, cost);

				int flag=ps.executeUpdate();

				if(flag>0)
				{
					out.println("<html>");
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h1>Record Added Successfully!!</h1>");
					out.println("<br><br>");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Add More Items</a>");
				}
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Insertion Unsuccessful!!</h2>");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Add Again</a>");
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				if (e.getErrorCode() == 1062) {
					// Duplicate entry error
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h1>Insertion failed: Record already exists!</h1>");
					out.println("<br><br>");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Add More Items</a>");
				} else {
					// Other SQL error
					e.printStackTrace();
				}
			}
		}
		else if(input.equals("VIEW MENU"))
		{
			query="select * from menu";

			try {
				ps=con.prepareStatement(query);
				rs=ps.executeQuery();

				out.println("<body bgcolor=\"#9A616D\">");
				out.println("<table border='1' align='center'>");
				out.println("<tr>");
				out.println("<th>Menu ID</th>");
				out.println("<th>Menu Name</th>");
				out.println("<th>Menu Type</th>");
				out.println("<th>Menu Cost</th>");

				out.println("</tr>");

				while(rs.next())
				{
					out.println("<tr>");
					out.println("<td>"+rs.getInt(1)+"</td>");
					out.println("<td>"+rs.getString(2)+"</td>");
					out.println("<td>"+rs.getString(3)+"</td>");
					out.println("<td>"+rs.getFloat(4)+"</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				
				out.println("<td><a href=add_menu.html>Back</a>");
				out.println("<br><br>");
				out.println("<td><a href=Home.html>Home Page</a>");
				out.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(input.equals("UPDATE MENU"))
		{
			int id=Integer.parseInt(request.getParameter("id"));
			String name=request.getParameter("name");
			String type=request.getParameter("type");
			float cost=Float.parseFloat(request.getParameter("cost"));

			query="update menu set Menu_Name='"+name+"',Menu_Type='"+type+"',Price="+cost+" where Menu_ID="+id;

			try {

				ps=con.prepareStatement(query);

				int flag=ps.executeUpdate();
				if(flag>0)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h1>Updated Successfully!!</h1>");

					out.println("<table>");

					out.println("<tr>");
					out.println("<td>Menu ID</td>");
					out.println("<td>"+id+"</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td>Menu Name</td>");
					out.println("<td>"+name+"</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td>Menu Type</td>");
					out.println("<td>"+type+"</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td>Menu Cost</td>");
					out.println("<td>"+cost+"</td>");
					out.println("</tr>");

					out.println("</table>");
					
					out.println("<br><br>");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Add More Items</a>");
				}
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Updation Unsuccessful!!");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Update Again?</a>");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		else if(input.equals("DELETE MENU ITEM"))
		{
			int id=Integer.parseInt(request.getParameter("id"));
			query="delete from menu where Menu_ID="+id;

			try {

				ps=con.prepareStatement(query);
				int flag=ps.executeUpdate();

				if(flag>0)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h1>Item Deleted Successfully!!");
					out.println("<br><br>");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Add More Items</a>");
				}
				
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Deletion Unsuccessful!!");
					out.println("<a href=Home.html>Home Page</a>");
					out.println("<br><br>");
					out.println("<a href=add_menu.html>Try Deleting Again?</a>");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
