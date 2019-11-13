package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getParameter("action");
		String page = null;
		
		if(action == null){
			page = "/index.jsp";
		} else {
			page = "/login.jsp";
		}
		
		getServletContext().getRequestDispatcher(page).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		Connection conn = null;
		
		if(action.equals("logedin")){
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			out.println("<html>");
			out.println("<p> Email is: " + email + "</p>");
			out.println("<p> Password is: " + password + "</p>");
			out.println("</html>");
		} else {
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}

		
		
		
	}

}
