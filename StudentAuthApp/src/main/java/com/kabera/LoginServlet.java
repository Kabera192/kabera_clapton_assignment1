package com.kabera;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String name = req.getParameter("email");
		String password = req.getParameter("passwd");
		
		
		if(!name.equals("kabera@gmail.com") || !password.equals("123kabera##")) {
			res.sendRedirect("/StudentAuthApp/forgotLogin.html");
			return;
		}else {
			res.sendRedirect("/StudentAuthApp/home.html");
			return;
		}
	}

}
