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

@WebServlet("/post/join")
public class PostJoinController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postNum = Integer.parseInt(req.getParameter("postNum"));

        PostService service = new PostService();
        JoinService joinService = new JoinService();
        ChatroomService chatService = new ChatroomService();
        HttpSession session = req.getSession();
        Post post = service.getPost(postNum);

        joinService.setJoin(postNum,(String)session.getAttribute("loginNum"));
        chatService.participateInChatRoom(postNum, (String)session.getAttribute("loginNum"));
        if (post.getNumOfParticipants()+1 == post.getLimitOfParticipants()) {
            post.setState("full");
        }
        post.setNumOfParticipants(post.getNumOfParticipants()+1);

        service.updatePost(post);

        resp.sendRedirect("./detail?roomNum=" + String.valueOf(postNum));
    }
}
