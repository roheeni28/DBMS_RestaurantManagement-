

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
 * Servlet implementation class Orders
 */
@WebServlet("/view_menu")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	String query;

	/**
	 * Default constructor. 
	 */
	public Orders() {
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

		String input=request.getParameter("c");

		if(input.equals("VIEW ORDERS"))
		{
			query="select * from order_details where order_id=0";
			
			try {

				ps=con.prepareStatement(query);
				rs=ps.executeQuery();

				out.println("<body bgcolor=\"#9A616D\">");
				out.println("<table border='1'>");
				out.println("<tr>");
				out.println("<th>Menu Name</th>");
				out.println("<th>Quantity</th>");
				out.println("<th>Cost</th>");
				out.println("</tr>");

				while(rs.next())
				{
					out.println("<tr>");
					out.println("<td>"+rs.getString(3)+"</td>");
					out.println("<td>"+rs.getInt(4)+"</td>");
					out.println("<td>"+rs.getFloat(5)+"</td>");
					out.println("</tr>");
				}
				
				String query1="select sum(cost) from order_details where order_id=0";
				
				ps=con.prepareStatement(query1);
				rs=ps.executeQuery();
				
				if(rs.next())
				{
					float total_cost=rs.getFloat("sum(cost)");
					out.println("<tr>Total Cost: </tr>");
					out.println("<tr>"+total_cost+"</tr>");
				}
				
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>No Orders Added To Cart Yet!!");
				}
				out.println("</table>");
				out.println("<tr>Orders Displayed Successfully!!</tr>");
				out.println("<br>");
				out.println("<tr><a href=menu.html>View Menu</a></tr>");
				out.println("<tr><a href=PaymentType.html>Proceed To Pay</a></tr>");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
