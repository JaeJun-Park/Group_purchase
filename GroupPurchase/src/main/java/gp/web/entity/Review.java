package gp.web.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Review 
{
	
	private int reviewNum;
	private String writerNum;
	private String evaluateeNum;
	private int postNum;
	private float rating;
	private String comment;
	private Timestamp time;
	
	public Review() {
	}
	public Review(int reviewNum, String writerNum, String evaluateeNum, int postNum, float rating, String comment,
			Timestamp time) {
		this.reviewNum = reviewNum;
		this.writerNum = writerNum;
		this.evaluateeNum = evaluateeNum;
		this.postNum = postNum;
		this.rating = rating;
		this.comment = comment;
		this.time = time;
	}
	public int getReviewNum() {
		return reviewNum;
	}
	public void setReviewNum(int reviewNum) {
		this.reviewNum = reviewNum;
	}
	public String getWriterNum() {
		return writerNum;
	}
	public void setWriterNum(String writerNum) {
		this.writerNum = writerNum;
	}
	public String getEvaluateeNum() {
		return evaluateeNum;
	}
	public void setEvaluateeNum(String evaluateeNum) {
		this.evaluateeNum = evaluateeNum;
	}
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getStrTime()
	{
		return timestampToString();
	}
	public String timestampToString()
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(time);
		
		return res;
	}
	@Override
	public String toString() {
		return "Review [reviewNum=" + reviewNum + ", writerNum=" + writerNum + ", evaluateeNum=" + evaluateeNum
				+ ", postNum=" + postNum + ", rating=" + rating + ", comment=" + comment + ", time=" + time + "]";
	}
	
}
