package gp.web.servlet;

import java.io.IOException;

import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.service.ChatroomService;
import gp.web.entity.Message;

@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String studentNum = request.getParameter("studentNum");
		String roomNum = request.getParameter("roomNum");
		String listType = request.getParameter("listType");
		
		if(studentNum == null || studentNum.equals("") || roomNum == null || roomNum.equals("") ||  listType == null || listType.equals(""))
		{
			response.getWriter().write("");
		}
		else
		{
			try {
				response.getWriter().write(getTen(roomNum, listType)); 
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	public String getTen(String roomNum, String listType)
	{
		int rNum = Integer.parseInt(roomNum);
		int type = Integer.parseInt(listType);
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatroomService serv = new ChatroomService();
		ArrayList<Message> chatList = serv.getChatListByRoomNumRecent(rNum, type);
		
		if(chatList.size() == 0) return "";
		for(int i=0; i< chatList.size(); i++)
		{
			result.append("[{\"value\":\"" + chatList.get(i).getStudentNum() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getRoomNum() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getText() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getStrTime() + "\"}]");
			if(i!=chatList.size() -1) result.append(",");
			
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getMessageNum() + "\"}"); // 애매
		return result.toString();
	}
	
	public String getID(String roomNum, String messageNum)
	{
		int rNum = Integer.parseInt(roomNum);
		int mNum = Integer.parseInt(messageNum);
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatroomService serv = new ChatroomService(); 
		ArrayList<Message> chatList = serv.getChatListByRoomNum(rNum, mNum);
		
		if(chatList.size() == 0) return "";
		for(int i=0; i< chatList.size(); i++)
		{
			result.append("[{\"value\":\"" + chatList.get(i).getStudentNum() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getRoomNum() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getText() + "\"},");
			result.append("{\"value\":\"" + chatList.get(i).getStrTime() + "\"}]");
			if(i!=chatList.size() -1) result.append(",");
			
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getMessageNum() + "\"}"); // 애매
		return result.toString();
	}
}
