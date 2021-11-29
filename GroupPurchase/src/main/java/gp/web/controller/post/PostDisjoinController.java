package gp.web.controller.post;

import gp.web.entity.Post;
import gp.web.service.JoinService;
import gp.web.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/post/disjoin")
public class PostDisjoinController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postNum = Integer.parseInt(req.getParameter("postNum"));

        PostService service = new PostService();
        JoinService joinService = new JoinService();
        HttpSession session = req.getSession();
        Post post = service.getPost(postNum);

        if (post.getNumOfParticipants()==post.getLimitOfParticipants()) {
            post.setState("not full");
        }
        post.setNumOfParticipants(post.getNumOfParticipants()-1);

        service.updatePost(post);
        joinService.deleteJoin(postNum, (String)session.getAttribute("loginNum"));

        resp.sendRedirect("./detail?postNum=" + String.valueOf(postNum));
    }
}
