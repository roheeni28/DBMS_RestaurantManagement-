
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
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Add_Cutomer
 */
@WebServlet("/customer")
public class Add_Customer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	ResultSet rs;
	Statement st;
	PreparedStatement ps;
	String query;

	/**
	 * Default constructor. 
	 */
	public Add_Customer() {
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
			if (ps != null) {
				ps.close();
			}

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

		String input=request.getParameter("y");

		if(input != null && input.equals("Submit"))
		{
			String name=request.getParameter("name");
			String no=request.getParameter("no");

			if(no.length()!=10)
			{
				out.println("<h1>Please Enter 10 Digit Mobile Number</h1>");
			}
			else
			{
				query="insert into customer(name,mobile_no) values(?,?)";

				try {

					ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, name);
					ps.setString(2, no);

					int flag=ps.executeUpdate();

					ResultSet rs = ps.getGeneratedKeys();
					int autoIncValue = -1;

					if (rs.next()) {
						autoIncValue = rs.getInt(1);
					}


					HttpSession session=request.getSession();

					session.setAttribute("x", autoIncValue);
					if(flag>0&&autoIncValue > 0)
					{

						out.println("<body bgcolor=\"#9A616D\">");
						out.println("<h1>REGISTERED SUCCESSFULLY</h1>");
						out.println("<table>");

						out.println("<tr>");
						out.println("<td>Customer ID</td>");
						out.println("<td>"+autoIncValue+"</td>");
						out.println("</tr>");

						out.println("<tr>");
						out.println("<td>Customer Name</td>");
						out.println("<td>"+name+"</td>");
						out.println("</tr>");

						out.println("<tr>");
						out.println("<td>Mobile No</td>");
						out.println("<td>"+no+"</td>");
						out.println("</tr>");



						out.println("<body bgcolor=#9A616D>");
						out.println("<tr>");
						out.println("<td><a href=menu.html>ORDER ONLINE</a></td>");
						out.println("</tr>");

						out.println("<tr>");
						out.println("<td><a href=Reserve.html>RESERVE A TABLE</a></td>");
						out.println("</tr>");
						
						out.println("<tr>");
						out.println("<td><a href=Home.html>Home</a></td>");
						out.println("</tr>");

						out.println("</table>");

					}
					else
					{
						out.println("<body bgcolor=\"#9A616D\">");
						out.println("<h2>Registration Unsuccessful!!</h2>");
						out.println("<br><br>");
						out.println("<a href=Customer_Login.html>Login Again</a>");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					if (e.getErrorCode() == 1062) {
						// Duplicate entry error
						out.println("<body bgcolor=\"#9A616D\">");
						out.println("<h1>Insertion failed: Record already exists!</h1>");
						out.println("<br><br>");
						out.println("<a href=Home.html>Home Page</a>");
						
					} else {
						// Other SQL error
						e.printStackTrace();
					}

				}
			}
		}
	}
}
