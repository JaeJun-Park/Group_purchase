package gp.web.controller.post;

import gp.web.entity.Join;
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
import java.util.List;

@WebServlet("/post/mylist")
public class PostMyListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostService service = new PostService();
        HttpSession session = req.getSession();
        // @@ temporary

        //list?f=title&q=query
        String page_ = req.getParameter("p");

        int page = 1;
        if (page_ != null && !page_.equals(""))
            page = Integer.parseInt(page_);

        List<Post> list = service.getMyPostList((String)session.getAttribute("loginNum"), page);
        int count = service.getPostCount("studentNum", (String)session.getAttribute("loginNum"));

        req.setAttribute("list", list);
        req.setAttribute("count", count);
        req.getRequestDispatcher("/WEB-INF/view/post/mylist.jsp").forward(req, resp);

    }
}