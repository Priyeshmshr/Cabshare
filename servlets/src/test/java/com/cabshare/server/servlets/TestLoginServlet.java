/*
 * Created by Priyesh Mishra on 26-Dec-2015
 */

package com.cabshare.server.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestLoginServlet{

	private LoginServlet servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse  response;
	
	@Before
	public void setup(){
		servlet = new LoginServlet();
		request= new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}
	
	@Test
	public void ValidateLoginDetails() throws ServletException, IOException{
		request.setParameter("username", "prish");
		request.setParameter("password", "priyesh");
		servlet.doPost(request, response);
		assertEquals("text/html",response.getContentType());
		assertEquals("success",response.getContentAsString().toString());
	}
}
