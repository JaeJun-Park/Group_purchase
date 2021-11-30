package gp.web.controller.post;

import gp.web.entity.Post;
import gp.web.entity.TakeLog;
import gp.web.service.JoinService;
import gp.web.service.LockerService;
import gp.web.service.PostService;
import gp.web.service.TakeLogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/post/state")
public class PostStateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postNum = Integer.parseInt(req.getParameter("postNum"));
        String toState = req.getParameter("toState");
        String take = req.getParameter("take");

        PostService service = new PostService();
        JoinService joinService = new JoinService();
        TakeLogService takeLogService = new TakeLogService();
        LockerService lockerService = new LockerService();
        HttpSession session = req.getSession();
        Post post = service.getPost(postNum);

        if (toState != null && toState.equals("ordering")) { 
            post.setState(toState); // toState = ordering
            //<-- ------ locker allocation ------ --->
            lockerService.allocateLcoker(postNum);

        }

        if (take != null && take.equals("T")) { 
            takeLogService.setTakeLog(postNum, (String) session.getAttribute("loginNum"));
            
            List<TakeLog> takeLogList = takeLogService.getTakeLogList(postNum);
            toState = "ordering"; // 거래 완료를 의미
            int num = post.getNumOfParticipants();
            int count = 0;
            for (TakeLog takeLog : takeLogList) {
                count++;
                if (takeLog.isGet().equals("F")) 
                    break;
                if (takeLog.isGet().equals("T") && count == num) // 아직 물품 수령 못한 맴버있음
                    toState = "done";
            }
            post.setState(toState);
            if (post.getState().equals("done")) { 
                lockerService.collectLocker(lockerService.getLockerInfo(postNum).getLockerNum()); 
            }
        }
        service.updatePost(post);

        resp.sendRedirect("./detail?postNum=" + String.valueOf(postNum));
    }
}
