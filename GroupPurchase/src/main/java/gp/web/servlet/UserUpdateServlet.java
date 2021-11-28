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
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			resp.sendRedirect("./update");
			return;
		}
		
		if(num.equals(session.getAttribute("studentNum")))
		{
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			resp.sendRedirect("./home");
			return;
		}
		
		if(!pw1.equals(pw2))
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "비밀번호가 서로 일치하지 않습니다.");
			resp.sendRedirect("./update");
			return;
		}
		int result = new StudentService().update(num, name, pw1);
		if(result == 1)
		{
			StudentService serv = new StudentService();
			Student stu = new Student();
			stu = serv.getStudent("studentNum", num);
			req.getSession().setAttribute("messageType", "성공 메시지");
			req.getSession().setAttribute("messageContent", "회원정보 수정에 성공했습니다.");
			req.getSession().setAttribute("loginNum", stu.getStudentNum());
			req.getSession().setAttribute("student", stu);
			resp.sendRedirect("./home");
			return;
		}
		else
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			resp.sendRedirect("./update");
		}
			
	}
}
