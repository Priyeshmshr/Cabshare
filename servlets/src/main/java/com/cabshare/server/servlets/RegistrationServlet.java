/*
 * Created by Priyesh Mishra on 25-Dec-2015
 */
package com.cabshare.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cabshare.server.dao.dao.UserAuthDAO;
import com.cabshare.server.entities.User;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final String username = "username";
    private final String password = "password";
    private final String fullName = "fullname";
    private final String gender = "gender";
    private final String contact = "contact_no";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String result = null;
		if(request !=null){
		    User user = new User();
		    user.setUsername(request.getParameter(username));
		    user.setPassword(request.getParameter(password));
		    user.setFullName(request.getParameter(fullName));
		    user.setGender(request.getParameter(gender));
		    user.setContact_no(request.getParameter(contact));
		    result = new UserAuthDAO().registration(user);
		}
		out.write(result);
		out.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
