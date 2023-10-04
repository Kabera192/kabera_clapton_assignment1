package com.kabera;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		HttpSession session = req.getSession();
		UserDto signedUser = (UserDto)session.getAttribute("user");
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		if(req.getParameter("email").equals(signedUser.getUsername()) && req.getParameter("passwd").equals(signedUser.getPassword())) {
			res.sendRedirect("/StudentAuthApp/home.html");
		}else {
			out.println("<h1>Come on you have no account right?</h1>");
		}
	}

}
