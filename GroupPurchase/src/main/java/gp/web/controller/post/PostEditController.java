package gp.web.controller.post;

import gp.web.entity.Post;
import gp.web.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/post/edit")
public class PostEditController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postNum = Integer.parseInt(req.getParameter("postNum"));

        PostService service = new PostService();
        Post post = service.getPost(postNum);

        req.setAttribute("post", post);
        req.getSession().setAttribute("postNum", post.getPostNum());
        req.getRequestDispatcher("/WEB-INF/view/post/edit.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = req.getParameter("title");
        String productInfo = req.getParameter("productInfo");
        String limitOfParticipants_ = req.getParameter("limitOfParticipants");
        int postNum = (Integer) req.getSession().getAttribute("postNum");
        //PrintWriter out = resp.getWriter();
        PostService service =new PostService();

        Post post = new Post();

        String prevState = service.getPost(postNum).getState();
        int prevNumOfParticipants = service.getPost(postNum).getNumOfParticipants();

        post.setPostNum(postNum);
        post.setStudentNum((String) req.getSession().getAttribute("loginNum"));
        post.setTitle(title);
        post.setProductInfo(productInfo);
        post.setState(prevState);
        post.setNumOfParticipants(prevNumOfParticipants);
        post.setLimitOfParticipants(Integer.parseInt(limitOfParticipants_));
        // need to change possst-service methods

        service.updatePost(post);

        resp.sendRedirect("list");

    }
}
