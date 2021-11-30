package gp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.service.StudentService;

public class UserRegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String num = req.getParameter("userNum");
		String id = req.getParameter("userID");
		String name = req.getParameter("userName");
		String pw1 = req.getParameter("userPassword1");
		String pw2 = req.getParameter("userPassword2");
		
		if(num== null || num.equals("") || id== null || id.equals("") || pw1== null || pw1.equals("") ||pw2== null || pw2.equals("") ||name== null || name.equals(""))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "��� ������ �Է��ϼ���.");
			resp.sendRedirect("./signup");
			return;
		}
		if(!pw1.equals(pw2))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "��й�ȣ�� ���� ��ġ���� �ʽ��ϴ�.");
			resp.sendRedirect("./signup");
			return;
		}
		int result = new StudentService().register(num, name, id, pw1);
		if(result == 1)
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "ȸ�����Կ� �����߽��ϴ�.");

			resp.sendRedirect("./home");
			return;
		}
		else
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "�̹� �����ϴ� ȸ���Դϴ�.");
			resp.sendRedirect("./signup");
		}
			
	}
}
