package gp.web.controller.post;

import gp.web.entity.Join;
import gp.web.entity.Locker;
import gp.web.entity.Post;
import gp.web.service.JoinService;
import gp.web.service.LockerService;
import gp.web.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/post/detail")  // 이 녀석을 통해서 해당 detail.jsp가 실행되는 거임
public class PostDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PostService service = new PostService();
        JoinService joinService = new JoinService();
        LockerService lockerService = new LockerService();

        // <-- -----------------req from client--------------------- -->
        int postNum = Integer.parseInt(req.getParameter("postNum"));
        int currentNumOfParticipants = joinService.getCurrentNumOfParticipants(postNum);
        String joinPost = req.getParameter("joinPost");
        String outPost = req.getParameter("outPost");


        if (joinPost != null && !joinPost.equals(""))
            if (service.getPost(postNum).getNumOfParticipants() < service.getPost(postNum).getLimitOfParticipants()) {
                joinService.setJoin(postNum, (String) session.getAttribute("loginNum"));
            }

        Post post = service.getPost(postNum);
        List<Join> joinList = joinService.getJoinList(postNum);
        Locker locker = lockerService.getLockerInfo(postNum);

        req.setAttribute("post", post);
        req.setAttribute("joinList", joinList);
        req.setAttribute("locker", locker);
        //forwarding 작업했던 내용들 이어받아 처리하게끔
        req.getRequestDispatcher("/WEB-INF/view/post/detail.jsp").forward(req, resp);

    }
}


