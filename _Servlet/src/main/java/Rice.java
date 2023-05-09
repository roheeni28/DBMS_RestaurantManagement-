

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
 * Servlet implementation class Rice
 */
@WebServlet("/rice")
public class Rice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	String query1;
	String query2;
	float cost;
	int qty;

    /**
     * Default constructor. 
     */
    public Rice() {
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

		String input=request.getParameter("b");

		if(input.equals("ADD ITEM"))
		{
			String name=request.getParameter("r_name");
			qty=Integer.parseInt(request.getParameter("quantity"));


			String query="select Price from menu where Menu_Name='"+name+"'";

			try {

				ps=con.prepareStatement(query);

				rs=ps.executeQuery();

				if(rs.next())
				{
					cost=rs.getFloat("Price");
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cost=cost*qty;

			query1="INSERT INTO order_details(Menu_Name,quantity,cost) values(?,?,?)";

			try {

				ps=con.prepareStatement(query1);
				ps.setString(1, name);
				ps.setInt(2, qty);
				ps.setFloat(3, cost);

				int flag=ps.executeUpdate();

				if(flag>0)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Order Added To Cart</h2>");
					out.println("<br><br>");
					out.println("<a href=menu.html>Add More Orders</a>");
				}

				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Order Not Placed!!</h2>");
					out.println("<br><br>");
					out.println("<a href=Rice.html>Order Again?</a>");
					out.println("<br><br>");
					out.println("<a href=menu.html>View Menu</a>");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

		}

		else if(input.equals("UPDATE ITEM"))
		{
			String name=request.getParameter("r_name");
			int qty=Integer.parseInt(request.getParameter("quantity"));

			String query="select Price from menu where Menu_Name='"+name+"'";

			try {

				ps=con.prepareStatement(query);

				rs=ps.executeQuery();

				if(rs.next())
				{
					cost=rs.getFloat("Price");
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cost=cost*qty;

			query2="update order_details set quantity=?,cost=? where Menu_Name=? and order_id=0";

			try {

				ps=con.prepareStatement(query2);
				ps.setInt(1, qty);
				ps.setFloat(2, cost);
				ps.setString(3, name);


				int flag=ps.executeUpdate();

				if(flag>0)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Order Successfully Updated!!</h2>");
					out.println("<br><br>");
					out.println("<a href=menu.html>View Menu</a>");
				}

				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Updation Unsuccessful!!<h2>");
					out.println("<br><br>");
					out.println("<a href=Rice.html>Update Again?</a>");
					out.println("<br><br>");
					out.println("<a href=menu.html>View Menu</a>");
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if(input.equals("DELETE ORDER"))
		{	
			String name=request.getParameter("r_name");
			String query4="delete from order_details where order_id=0 and Menu_Name='"+name+"'";

			try {
				ps=con.prepareStatement(query4);
				int flag=ps.executeUpdate();

				if(flag>0)
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Order Deleted Successfully!!</h2>");
					out.println("<br><br>");
					out.println("<td><a href=menu.html>View Menu</a>");
				}
				
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>Order Not Deleted!!<h2>");
					out.println("<br><br>");
					out.println("<td><a href=menu.html>View Menu</a>");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
