package gp.web.entity;

import java.util.Date;

public class TakeLog {
    int postNum;
    String studentNum;
    Date time;
    String get;

    public TakeLog() {

    }

    public TakeLog(int postNum, String studentNum, Date time, String get) {
        this.postNum = postNum;
        this.studentNum = studentNum;
        this.time = time;
        this.get = get;
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

    public String isGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    @Override
    public String toString() {
        return "TakeLog{" +
                "postNum=" + postNum +
                ", studentNum='" + studentNum + '\'' +
                ", time=" + time +
                ", get=" + get +
                '}';
    }
}
