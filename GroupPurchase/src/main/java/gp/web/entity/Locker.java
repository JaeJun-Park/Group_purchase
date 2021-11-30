package gp.web.entity;

public class Locker {
    int lockerNum;
    int postNum;
    String password;
    String isAllocated;

    public Locker() {

    }

    public Locker(int lockerNum, int postNum, String password, String isAllocated) {
        this.lockerNum = lockerNum;
        this.postNum = postNum;
        this.password = password;
        this.isAllocated = isAllocated;
    }

    public int getLockerNum() {
        return lockerNum;
    }

    public void setLockerNum(int lockerNum) {
        this.lockerNum = lockerNum;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAllocated() {
        return isAllocated;
    }

    public void setIsAllocated(String isAllocated) {
        this.isAllocated = isAllocated;
    }

    @Override
    public String toString() {
        return "Locker{" +
                "lockerNum=" + lockerNum +
                ", postNum=" + postNum +
                ", password='" + password + '\'' +
                ", isAllocated='" + isAllocated + '\'' +
                '}';
    }
}
