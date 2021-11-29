package gp.web.controller.post;

import gp.web.entity.Post;
import gp.web.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/post/list")
public class PostListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostService service = new PostService();

        // @@ temporary
        req.getSession().setAttribute("loginNum", "201701");
        req.getSession().setAttribute("isLogin", true);

        //list?f=title&q=query
        String page_ = req.getParameter("p");
        String field_ = req.getParameter("f");
        String query_ = req.getParameter("q");


        int page = 1;
        if (page_ != null && !page_.equals(""))
            page = Integer.parseInt(page_);

        String field = "title";
        if (field_ != null && !field_.equals(""))
            field = field_;




        String query = "";
        if (query_ != null && !query_.equals(""))
            query = query_;

        List<Post> list = service.getPostList(field, query, page);
        int count = service.getPostCount(field, query);

        req.setAttribute("list", list);
        req.setAttribute("count", count);
        req.getRequestDispatcher("/WEB-INF/view/post/list.jsp").forward(req,resp);

    }
}
