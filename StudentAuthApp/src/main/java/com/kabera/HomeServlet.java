package com.kabera;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String selectedLanguage = req.getParameter("language");
	    if (selectedLanguage == null) {
	        selectedLanguage = "en"; // Default to English
	    }
	    Locale userLocale = new Locale(selectedLanguage);
	    ResourceBundle bundle = ResourceBundle.getBundle("i18n.page", userLocale);
	    String title = bundle.getString("title");
	    String title2 = bundle.getString("title2");
	    String name = bundle.getString("name");
	    String pic = bundle.getString("picture");
	    String transcript = bundle.getString("transcript");
	    String change = bundle.getString("change");
	    String apply = bundle.getString("apply");
	    
	    // Set the content type and write the localized greeting to the response
	    res.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = res.getWriter();
	    out.println("<html><head><title>Student Home</title></head><body>");
	    out.println("<header><h2>" + title + "</h2>");
	    out.println("<h3>" + title2 + "</h3></header>");
	    out.println("<form action=studentHome>");
	    out.println("<label>"+ name +"</label>\n"
	    		+ "	   <input type=\"text\" name=\"stuName\"><br>");
	    out.println("<label>"+ pic + "</label>\n"
	    		+ "	   <input type=\"file\" name=\"stuName\"><br>");
	    out.println("<label>"+ transcript +"</label>\n"
	    		+ "	   <input type=\"file\" name=\"stuName\"><br>");
	    out.println("<button type=\"submit\">"+apply+"</button>\n"
	    		+ "   </form>");
	    out.println("<footer>\n"
	    		+ "	   <form action=\"studentHome\" method=\"post\">\n"
	    		+ "        <label for=\"language\">Select Language:</label>\n"
	    		+ "        <select id=\"language\" name=\"language\">\n"
	    		+ "            <option value=\"en\">English</option>\n"
	    		+ "            <option value=\"fr\">French</option>\n"
	    		+ "            <option value=\"es\">Spanish</option>\n"
	    		+ "        </select>\n"
	    		+ "        <input type=\"submit\" value=\"Change Language\">\n"
	    		+ "    	</form>\n"
	    		+ "   </footer>");
	    out.println("<a href=\"index.html\">"+change+"</a>");
	    out.println("</body></html>");
	}
    
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Part filePart = req.getPart("pic");
		Part docPart = req.getPart("transcript");
		
		String filename = filePart.getSubmittedFileName();
		String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		
		String docName = docPart.getSubmittedFileName();
		String docExtension = docName.substring(docName.lastIndexOf(".") + 1).toLowerCase();
		
		if((fileExtension.equals("jpg") || fileExtension.equals("png")) && docExtension.equals("pdf")) {
			sendEmail(req, res);
		}else {
			
		}
	}

	public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession ss = request.getSession();
        // Get the recipient's email address and confirmation code from the request parameters
        UserDto user = (UserDto) ss.getAttribute("user");
        String recipientEmail = user.getUsername();
        String confirmationCode = request.getParameter("confirmationCode");

        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP host
        properties.put("mail.smtp.port", "587"); // Gmail SMTP port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Replace with your actual Gmail email and password or App Password
        final String senderEmail = "ckabera6@gmail.com";
        final String senderPassword = "iirf tzcx tasj zcsy";

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Subject of the Email"); // Replace with your subject
            message.setText("Email sent successfully. Confirmation Code: " + confirmationCode); // Replace with your message

            // Send the message
            Transport.send(message);
            
            // Handle successful email sending (e.g., display a success message)
            response.getWriter().write("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle email sending failure (e.g., display an error message)
            response.getWriter().write("Failed to send email: " + e.getMessage());
        }
	}
}

