

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
 * Servlet implementation class Reservation
 */
@WebServlet("/reservation")
public class Reservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;


	/**
	 * Default constructor. 
	 */
	public Reservation() {
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

		if(input.equals("RESERVE"))
		{	
			int c_id=Integer.parseInt(request.getParameter("id"));
			int g_no=Integer.parseInt(request.getParameter("no"));
			int n_g_no=0;
			String date=request.getParameter("date");
			String time=request.getParameter("time");
			String query1="select * from customer where c_id="+c_id;

			try {

				ps=con.prepareStatement(query1);
				rs=ps.executeQuery();

				if(rs.next())
				{
					if(g_no<=2)
						n_g_no=2;

					else if(g_no>2&&g_no<=4)
						n_g_no=4;

					else if(g_no>4&&g_no<=10)
						n_g_no=10;

					// select table_no from table_info where date='2023-05-12'and a_time='19:00:00' and status='Available' and no_of_chairs=4 limit 1;
					String query2="select table_no from table_info where date='"+date+"'and a_time='"+time+"'and status='Available'and no_of_chairs="+n_g_no+" limit 1";
					ps=con.prepareStatement(query2);
					rs=ps.executeQuery();

					if(rs.next())
					{
						int table_no=rs.getInt("table_no");

						String query3="update table_info set status='Reserved',c_id="+c_id+" where table_no="+table_no+" and date='"+date+"'and a_time='"+time+"'";

						ps=con.prepareStatement(query3);
						int flag=ps.executeUpdate();

						if(flag==1)
						{
							String query4="insert into reservation values("+table_no+","+c_id+",'"+date+"','"+time+"',"+g_no+")";

							ps=con.prepareStatement(query4);
							int flag1=ps.executeUpdate();

							if(flag1==1)
							{
								out.println("<body bgcolor=\"#9A616D\">");
								out.println("<h1>TABLE RESERVED SUCCESSFULLY</h1>");
								out.println("<table>");

								out.println("<tr>");
								out.println("<td>Customer ID</td>");
								out.println("<td>"+c_id+"</td>");
								out.println("</tr>");

								out.println("<tr>");
								out.println("<td>Table No</td>");
								out.println("<td>"+table_no+"</td>");
								out.println("</tr>");

								out.println("<tr>");
								out.println("<td>Date</td>");
								out.println("<td>"+date+"</td");
								out.println("</tr>");

								out.println("<tr>");
								out.println("<td>Time</td>");
								out.println("<td>"+time+"</td");
								out.println("</tr>");
								
								out.println("<tr><a href=Home.html>Home Page</a>");

							}
							else
							{
								out.println("<body bgcolor=\"#9A616D\">");
								out.println("<h2>Reservation Unsuccessful!!");
								out.println("<br><br>");
								out.println("<h2><a href=Reserve.html>Try Again</a><h2>");
							}
						}
						else
						{
							out.println("<body bgcolor=\"#9A616D\">");
							out.println("<h2>Reservation Unsuccessful!!");
							out.println("<br><br>");
							out.println("<h2><a href=Reserve.html>Try Again</a><h2>");
						}
					}
					else
					{
						out.println("<body bgcolor=\"#9A616D\">");
						out.println("<h2>All Tables Are Reserved For This Slot</h2>");
						out.println("<br><br>");
						out.println("<h2>Try For Different Slot</h2>");
						out.println("<br><br>");
						out.println("<a href=Reserve.html>Try Again?</a>");
						out.println("<br><br>");
						out.println("<a href=Home.html>Home Page</a>");
					}

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
				} else {
					// Other SQL error
					e.printStackTrace();
				}

			}


		}

		else if(input.equals("DELETE"))
		{
			int c_id=Integer.parseInt(request.getParameter("id"));
			int g_no=Integer.parseInt(request.getParameter("no"));
			String date=request.getParameter("date");
			String time=request.getParameter("time");

			String query1="select * from customer where c_id="+c_id;

			try {

				ps=con.prepareStatement(query1);
				rs=ps.executeQuery();

				if(rs.next())
				{
					String query2="select table_no from reservation where date='"+date+"'and a_time='"+time+"'and c_id="+c_id;

					ps=con.prepareStatement(query2);
					rs=ps.executeQuery();

					if(rs.next())
					{
						int table_no=rs.getInt("table_no");
						String query3="delete from reservation where date='"+date+"'and a_time='"+time+"'and c_id="+c_id;

						ps=con.prepareStatement(query3);
						int flag=ps.executeUpdate();

						if(flag==1)
						{
							String query4="update table_info set status='Available',c_id=0 where date='"+date+"'and a_time='"+time+"'and table_no="+table_no;

							ps=con.prepareStatement(query4);
							int flag1=ps.executeUpdate();

							if(flag1==1)
							{
								out.println("<background-color: #9A616D>");
								out.println("<h1>Reservation Cancelled Successfully!!");
								out.println("<br><br>");
								out.println("<a href=Home.html>Home Page</a>");
							}
							else
							{
								out.println("<body bgcolor=\"#9A616D\">");
								out.println("<h2>Cancellation Unsuccessful!!");
								out.println("<br><br>");
								out.println("<h2><a href=Reserve.html>Try Again</a><h2>");
							}
						}
					}
					else
					{
						out.println("<background-color: pink>");
						out.println("<h2>Cancellation Unsuccessful!!");
						out.println("<br><br>");
						out.println("<h2><a href=Reserve.html>Try Again</a><h2>");
						out.println("<br><br>");
					}
				}
				else
				{
					out.println("<background-color: pink>");
					out.println("<h2>Cancellation Unsuccessful!!");
					out.println("<br><br>");
					out.println("<h2><a href=Reserve.html>Try Again</a><h2>");
					out.println("<br><br>");
				}

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if(input.equals("SEARCH FOR AVAILABLE SLOTS"))
		{

			String date=request.getParameter("date");
			String time=request.getParameter("time");
			String query1="select * from table_info where status='Available' and date='"+date+"'and a_time='"+time+"'";

			try {

				ps=con.prepareStatement(query1);
				rs=ps.executeQuery();

				out.println("<body bgcolor=\"#9A616D\">");
				out.println("<table border='1'>");
				out.println("<tr>");
				out.println("<th>Table Number</th>");
				out.println("<th>Date</th>");
				out.println("<th>Time</th>");
				out.println("<th>Status</th>");
				out.println("<th>No of Seats Available");
				out.println("</tr>");

				if(rs.next())
				{
					while(rs.next())
					{
						out.println("<body bgcolor=\"#9A616D\">");
						out.println("<tr>");
						out.println("<td>"+rs.getInt("table_no")+"</td>");
						out.println("<td>"+rs.getString("date")+"</td>");
						out.println("<td>"+rs.getString("a_time")+"</td>");
						out.println("<td>Available</td>");
						out.println("<td>"+rs.getInt("no_of_chairs")+"</td>");
						out.println("</tr>");
					}
					out.println("<a href=Reserve.html>RESERVE A TABLE</a>");
				}
				else
				{
					out.println("<body bgcolor=\"#9A616D\">");
					out.println("<h2>All Tables Are Reserved For This Slot</h2>");
					out.println("<br><br>");
					out.println("<h2>Try For Different Slot</h2>");
					out.println("<br><br>");
					out.println("<a href=Reserve.html>Try Again?</a>");
					out.println("<br><br>");
					out.println("<a href=Home.html>Home Page</a>");
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
