package gp.web.controller.post;

import gp.web.service.JoinService;
import gp.web.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/post/delete")
public class PostDeleteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postNum = Integer.parseInt(req.getParameter("postNum"));

        PostService service = new PostService();
        JoinService joinService = new JoinService();
        joinService.deleteJoin(postNum);
        service.deletePost(postNum);


        resp.sendRedirect("list");
    }
}
