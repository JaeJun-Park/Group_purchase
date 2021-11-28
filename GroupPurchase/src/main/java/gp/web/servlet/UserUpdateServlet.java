package gp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gp.web.entity.Student;
import gp.web.service.StudentService;

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		

		String num = req.getParameter("userNum");
		String id = req.getParameter("userID");
		String name = req.getParameter("userName");
		String pw1 = req.getParameter("userPassword1");
		String pw2 = req.getParameter("userPassword2");
		
		if(num== null || num.equals("") || id== null || id.equals("") || pw1== null || pw1.equals("") ||pw2== null || pw2.equals("") ||name== null || name.equals(""))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "��� ������ �Է��ϼ���.");
			resp.sendRedirect("./update");
			return;
		}
		
		if(num.equals(session.getAttribute("studentNum")))
		{
			session.setAttribute("messageType", "���� �޽���");
			session.setAttribute("messageContent", "������ �� �����ϴ�.");
			resp.sendRedirect("./home");
			return;
		}
		
		if(!pw1.equals(pw2))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "��й�ȣ�� ���� ��ġ���� �ʽ��ϴ�.");
			resp.sendRedirect("./update");
			return;
		}
		int result = new StudentService().update(num, name, pw1);
		if(result == 1)
		{
			StudentService serv = new StudentService();
			Student stu = new Student();
			stu = serv.getStudent("studentNum", num);
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "ȸ������ ������ �����߽��ϴ�.");
			req.getSession().setAttribute("loginNum", stu.getStudentNum());
			req.getSession().setAttribute("student", stu);
			resp.sendRedirect("./home");
			return;
		}
		else
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "�����ͺ��̽� ������ �߻��߽��ϴ�.");
			resp.sendRedirect("./update");
		}
			
	}
}
