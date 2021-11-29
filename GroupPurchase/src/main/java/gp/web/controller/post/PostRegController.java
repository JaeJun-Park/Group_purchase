package gp.web.controller.post;

import gp.web.entity.Post;
import gp.web.service.ChatroomService;
import gp.web.service.JoinService;
import gp.web.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/post/reg")
public class PostRegController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/view/post/reg.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String productInfo = req.getParameter("productInfo");
        String limitOfParticipants_ = req.getParameter("limitOfParticipants");
        HttpSession session = req.getSession();
        

        PrintWriter out = resp.getWriter();
        PostService service =new PostService();
        JoinService joinService = new JoinService();
        ChatroomService chatService = new ChatroomService();

        Post post = new Post();
        int lastPostNum = service.getLastPostNum();

        post.setPostNum(service.getLastPostNum() + 1);
        post.setStudentNum((String)session.getAttribute("loginNum"));
        post.setTitle(title);
        post.setProductInfo(productInfo);
        post.setState("not full");
        post.setNumOfParticipants(1);
        post.setLimitOfParticipants(Integer.parseInt(limitOfParticipants_));
        // need to change possst-service methods
        service.insertPost(post);
        joinService.setJoin(post.getPostNum(), post.getStudentNum());
        chatService.createChatRoom(post.getPostNum(), post.getPostNum());

        resp.sendRedirect("list");

    }
}
