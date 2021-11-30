package gp.web.entity;

import java.util.Date;

public class Join {
    int postNum;
    String studentNum;
    Date time;

    public Join() {

    }

    public Join(int postNum, String studentNum, Date time) {
        this.postNum = postNum;
        this.studentNum = studentNum;
        this.time = time;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Join{" +
                "postNum=" + postNum +
                ", studentNum='" + studentNum + '\'' +
                ", time=" + time +
                '}';
    }
}
