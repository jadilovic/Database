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

import database.Person;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/PageServlet")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageServlet() {
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
		request.setAttribute("message", "");
		
		if(action == null) {
			request.getRequestDispatcher("/pageIndex.jsp").forward(request, response);
		} else if(action.equals("login")){
			request.getRequestDispatcher("/pageLogin.jsp").forward(request, response);
		} else if(action.equals("create")){
			request.getRequestDispatcher("/pageCreate.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		
		
		if(action.equals("dologin")){
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				out.println("No connection established");
				e.printStackTrace();
			}
			
			Person person = new Person(conn);
			
			try {
				if(person.login(email, password)){
					request.setAttribute("email", email);
					request.getRequestDispatcher("pageSuccess.jsp").forward(request, response);
				} else {
					request.setAttribute("message", "Wrong email or password. Please try agian.");
					request.getRequestDispatcher("pageLogin.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
