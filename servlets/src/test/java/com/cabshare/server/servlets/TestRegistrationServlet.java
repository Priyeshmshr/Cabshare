package com.cabshare.server.servlets;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestRegistrationServlet {
	private RegistrationServlet servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse  response;
	
	@Before
	public void setup(){
		servlet = new RegistrationServlet();
		request= new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}
	
	@Test
	public void ValidateRegistrationDetails() throws ServletException, IOException{
		request.setParameter("username", "priyeshmshr@gmail.com");
		request.setParameter("password", "priyesh");
		request.setParameter("fullname", "Priyesh Mishra");
		request.setParameter("gender", "male");
		request.setParameter("contact_no", "8553711804");
		servlet.doPost(request, response);
		assertEquals("text/html",response.getContentType());
		assertEquals("Registration successful",response.getContentAsString().toString());
	}
}
