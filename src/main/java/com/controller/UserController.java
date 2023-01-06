package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Service.Services;
import com.bean.User;
import com.dao.UserDao;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		
		if(action.equalsIgnoreCase("register")) 
		{
			boolean flag=UserDao.checkEmail(request.getParameter("email"));
			if(flag==false)
			{
				User u=new User();
				u.setFname(request.getParameter("fname"));
				u.setLname(request.getParameter("lname"));
				u.setEmail(request.getParameter("email"));
				u.setMobile(request.getParameter("mobile"));
				u.setAddress(request.getParameter("address"));
				u.setGender(request.getParameter("gender"));
				u.setPassword(request.getParameter("password"));
				u.setCpassword(request.getParameter("cpassword"));
				UserDao.insertuser(u);
				response.sendRedirect("login.jsp");
			}
			else
			{
				request.setAttribute("msg", "Email Already Registered");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("login"))
		{
			User u=UserDao.login(request.getParameter("email"),request.getParameter("password"));
			if(u!=null)
			{
				HttpSession session=request.getSession();
				session.setAttribute("u", u);
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "Email or Password is incorrect");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("change password"))
		{
			HttpSession session=request.getSession();
			User u=(User) session.getAttribute("u");
			if(u.getPassword().equals(request.getParameter("old_password")))
			{
				if(request.getParameter("new_password").equals(request.getParameter("cnew_password")))
				{
					UserDao.changePassword(u.getEmail(), request.getParameter("new_password"));	
					response.sendRedirect("logout.jsp");
				}
				else
				{
						request.setAttribute("msg", "New password and Confirm new password does not match");
						request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
				}
			}
			else
			{	
				request.setAttribute("msg", "old password does not matched");
				request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("send OTP"))
		{
			String email=request.getParameter("email");
			boolean flag=UserDao.checkEmail(email);
			if(flag==true)
			{
				int min=1000;
				int max=9999;
				int otp = (int)(Math.random()*(max-min+1)+min);
				Services.sendMail(email, otp);
				request.setAttribute("email", email);
				request.setAttribute("otp", otp);
				request.getRequestDispatcher("otp.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "Email not Registered");
				request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("verify otp"))
		{
			String email=request.getParameter("email");
			String otp=request.getParameter("otp");
			String uotp=request.getParameter("uotp");
		
			if(otp.equals(uotp))
			{
				request.setAttribute("email", email);
				request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
				
			}
			else
			{
				request.setAttribute("msg", "Invalid otp");
				request.setAttribute("email", email);
				request.setAttribute("otp", otp);
				request.getRequestDispatcher("otp.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("update password"))
		{
			String email=request.getParameter("email");
			String np=request.getParameter("new_password");
			String cnp=request.getParameter("cnew_password");
			
			if(np.equals("cnp"))
			{
				UserDao.changePassword(email, np);
				request.setAttribute("msg", "password updates succesfully");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "password and Confirm password does not matched");
				request.setAttribute("email" , email);
				request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
			}
		}
	}

}
