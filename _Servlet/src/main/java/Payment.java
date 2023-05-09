

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
 * Servlet implementation class Payment
 */
@WebServlet("/payment")
public class Payment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;

	/**
	 * Default constructor. 
	 */
	public Payment() {
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

		if(input.equals("PAY"))
		{

			int id=Integer.parseInt(request.getParameter("id"));
			String query="select c_id from customer where c_id="+id;

			try {
				ps=con.prepareStatement(query);

				rs=ps.executeQuery();

				if(rs.next()) {

					String phno=request.getParameter("no");
					String address=request.getParameter("address");
					String type=request.getParameter("type");

					if(phno.length()!=10)
					{
						out.println("<h1>Please enter 10 Digit number!</h1>");
						out.println("<a href=payment.html>ENTER MOBILE NO</a>");
					}

					else if(type.equals("ONLINE")&&address.trim().isEmpty())
					{
						out.println("<h1>PLEASE ENTER YOUR ADDRESS</h1>");
						out.println("<a href=payment.html>ENTER ADDRESS</a>");
					}

					else 
					{

						int order_id=0;

						String query1="select order_id from order_details order by order_id desc limit 1";

						try {

							ps=con.prepareStatement(query1);
							rs=ps.executeQuery();

							if(rs.next())
							{
								order_id=rs.getInt(1);
								order_id=order_id+1;

								String query2="update order_details set order_id=? where order_id=0";

								ps=con.prepareStatement(query2);
								ps.setInt(1,order_id);

								int flag=ps.executeUpdate();

								if(flag>0)
								{
									id=Integer.parseInt(request.getParameter("id"));
									//phno=request.getParameter("no");
									//address=request.getParameter("address");
									//type=request.getParameter("type");

									String query3="select sum(cost) from order_details where order_id="+order_id;

									ps=con.prepareStatement(query3);

									rs=ps.executeQuery();

									if(rs.next())
									{
										float total_cost=rs.getFloat("sum(cost)");

										String query4="insert into payments(order_id,c_id,total_cost,address,date,time,ph_no,type) values(?,?,?,?,now(),now(),?,?)";

										ps=con.prepareStatement(query4);
										ps.setInt(1, order_id);
										ps.setInt(2, id);
										ps.setFloat(3, total_cost);
										ps.setString(4, address);
										ps.setString(5, phno);
										ps.setString(6, type);

										int flag2=ps.executeUpdate();

										if(flag2>0)
										{
											String query5="update order_details set c_id=?,type=? where c_id is null";
											ps=con.prepareStatement(query5);
											ps.setInt(1, id);
											ps.setString(2, type);

											int flag1=ps.executeUpdate();
											if(flag1>0)
											{
												out.println("<body bgcolor=\"#9A616D\">");
												out.println("<h1>PAYMENT DONE SUCCESSFULLY</h1>");
												out.println("<br><br>");
												out.println("<h1>DO VISIT AGAIN</h1>");
												out.println("<br><br>");
												out.println("<a href=Home.html>GO TO HOME PAGE</a>");
											}

											else
											{
												out.println("<body bgcolor=\"#9A616D\">");
												out.println("<h1>PAYMENT UNSUCCESSFUL</h1>");
												out.println("<br><br>");
												out.println("<h1>Please Enter Correct Details</h1>");
												out.println("<br><br>");
												out.println("<a href=payment.html>Try Again!</a>");
											}
										}
										else
										{
											out.println("<body bgcolor=\"#9A616D\">");
											out.println("<h1>PAYMENT UNSUCCESSFUL</h1>");
											out.println("<br><br>");
											out.println("<h1>Please Enter Correct Details</h1>");
											out.println("<br><br>");
											out.println("<a href=payment.html>Try Again!</a>");
										}
									}
									else
									{
										out.println("<body bgcolor=\"#9A616D\">");
										out.println("<h1>PAYMENT UNSUCCESSFUL</h1>");
										out.println("<br><br>");
										out.println("<h1>Please Enter Correct Details</h1>");
										out.println("<br><br>");
										out.println("<a href=payment.html>Try Again!</a>");
									}
								}
								else
								{
									out.println("<body bgcolor=\"#9A616D\">");
									out.println("<h1>PAYMENT UNSUCCESSFUL</h1>");
									out.println("<br><br>");
									out.println("<h1>Please Enter Correct Details</h1>");
									out.println("<br><br>");
									out.println("<a href=payment.html>Try Again!</a>");
								}
							}
							else
							{
								out.println("<body bgcolor=\"#9A616D\">");
								out.println("<h1>PAYMENT UNSUCCESSFUL</h1>");
								out.println("<br><br>");
								out.println("<h1>Please Enter Correct Details</h1>");
								out.println("<br><br>");
								out.println("<a href=payment.html>Try Again!</a>");
							}
						}catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				
			}
		}

	}

}
