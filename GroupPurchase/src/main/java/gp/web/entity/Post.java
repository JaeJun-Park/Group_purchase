package gp.web.entity;

import java.sql.Timestamp;

public class Post{
    int postNum;
    String title;
    Timestamp date;
    String studentNum;
    int numOfParticipants;
    int limitOfParticipants;
    String productInfo;
    String state;

    public Post() {
    }

    public Post(int postNum, String title, Timestamp date, String studentNum, int numOfParticipants,
                int limitOfParticipants, String productInfo, String state) {
        this.postNum = postNum;
        this.title = title;
        this.date = date;
        this.studentNum = studentNum;
        this.numOfParticipants = numOfParticipants;
        this.limitOfParticipants = limitOfParticipants;
        this.productInfo = productInfo;
        this.state = state;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public int getNumOfParticipants() {
        return numOfParticipants;
    }

    public void setNumOfParticipants(int numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }

    public int getLimitOfParticipants() {
        return limitOfParticipants;
    }

    public void setLimitOfParticipants(int limitOfParticipants) {
        this.limitOfParticipants = limitOfParticipants;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postNum=" + postNum +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", studentNum='" + studentNum + '\'' +
                ", numOfParticipants='" + numOfParticipants + '\'' +
                ", limitOfParticipants='" + limitOfParticipants + '\'' +
                ", productInfo='" + productInfo + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
