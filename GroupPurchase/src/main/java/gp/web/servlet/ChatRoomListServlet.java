package gp.web.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.entity.Message;
import gp.web.service.ChatroomService;

/**
 * Servlet implementation class CharRoomListServlet
 */
@WebServlet("/ChatRoomListServlet")
public class ChatRoomListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String studentNum = request.getParameter("studentNum");
		if(studentNum == null || studentNum.equals("")) {
			response.getWriter().write("0");
		}
		else
		{
			try {
				response.getWriter().write(getRoom(studentNum));
			}catch (Exception e)
			{
				response.getWriter().write("");
			}
		}
	}
	
	public String getRoom(String studentNum)
	{
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatroomService serv = new ChatroomService(); 
		ArrayList<Message> chatList = serv.getRoom(studentNum);
		if(chatList.size() == 0) return "";
		for(int i=0; i< chatList.size(); i++)
		{
			result.append("[{\"value\":\"" + chatList.get(i).getStudentNum() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getRoomNum() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getText() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getStrTime() + "\"}]");
			if(i!=chatList.size() -1) result.append(",");
			
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getMessageNum() + "\"}"); 
		return result.toString();
	}
}
