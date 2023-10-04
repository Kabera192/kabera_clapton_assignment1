package com.kabera;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		UserDto user = new UserDto();
		
		user.setUsername(req.getParameter("email"));
		user.setPassword(req.getParameter("passwd"));
		
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		
		res.sendRedirect("/StudentAuthApp/index.html");
	}

}
