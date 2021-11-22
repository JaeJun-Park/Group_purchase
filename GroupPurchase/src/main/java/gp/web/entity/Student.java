package gp.web.entity;

public class Student 
{
	private String studentNum;
	private String name;
	private String id;
	private String password;
	private float credibility;
	
	public Student() {

	}

	public Student(String studentNum, String name, String id, String password) {
		this.studentNum = studentNum;
		this.name = name;
		this.id = id;
		this.password = password;
		credibility = 0.0f;
	}
	
	public Student(String studentNum, String name, String id, String password, float credibility) {
		this.studentNum = studentNum;
		this.name = name;
		this.id = id;
		this.password = password;
		this.credibility = credibility;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getCredibility() {
		return credibility;
	}

	public void setCredibility(float credibility) {
		this.credibility = credibility;
	}

	@Override
	public String toString() {
		return "Student [studentNum=" + studentNum + ", name=" + name + ", id=" + id + ", password=" + password
				+ ", credibility=" + credibility + "]";
	}
	
}
