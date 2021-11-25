package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.entity.Student;
import gp.web.service.StudentService;

@WebServlet("/mypage")
public class MypageController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fieldT = req.getParameter("f");
		String valueT = req.getParameter("v");
		
		String field = "studentNum";
		if(fieldT != null && !fieldT.equals(""))
			field = fieldT;
		
		String value = "";
		if(valueT != null && !valueT.equals(""))
			value = valueT;
		
		
		StudentService serv = new StudentService();
		Student stu = new Student();
		
		stu = serv.getStudent(field, value);
		System.out.println(stu.getStudentNum());
		req.setAttribute("s", stu);
		
		req.getRequestDispatcher("/WEB-INF/view/student/mypage.jsp").forward(req, resp);
	}
}
