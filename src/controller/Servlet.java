package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.tomcat.jni.User;

import database.Account;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			InitialContext initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			ds = (DataSource) env.lookup("jdbc/webshop");
			
		} catch (NamingException e) {
			throw new ServletException();
		}	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String page = null;
		
		if(action == null){
			page = "/index.jsp";
		} else {
			page = "/login.jsp";
		}
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		
		if(action == null){
			System.out.println("unrecognized action");
			return;
		}
		
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServletException();
		}
		
		out.println("Connection established");
		
		if(action.equals("dologin")){
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			User user = new User();
			
			request.setAttribute("email", email);
			request.setAttribute("password", "");
			
			Account account = new Account(conn);
			
			if(account.login(email, password)){
				request.getRequestDispatcher("/loginsuccess.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "email address or password not recognized");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
			
			out.println("<html>");
			out.println("<p> Email is: " + email + "</p>");
			out.println("<p> Password is: " + password + "</p>");
			out.println("</html>");
		} else {
			out.println("unrecognized action");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
