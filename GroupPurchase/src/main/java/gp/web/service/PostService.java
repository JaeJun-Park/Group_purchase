package gp.web.service;

import gp.web.entity.Post;


import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PostService {
	
	private Connection con;
	
	private void connectWithDB()
	{
		try {
			InitialContext initCtx = new InitialContext();
			Context envContext = (Context) initCtx.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/GroupPurchase");
			con = ds.getConnection();
			initCtx.close(); //?
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

    public List<Post> getPostList() {
        return getPostList("title", "", 1);
    }

    public List<Post> getPostList(int page) {
        return getPostList("title", "", page);
    }

    public List<Post> getPostList(String field, String query, int page) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT * FROM ( " +
                "    SELECT ROWNUM NUM, P.* " +
                "    FROM (SELECT * FROM POST WHERE " + field + " LIKE ? ORDER BY DATE_ DESC)   P " +
                "    ) " +
                "WHERE NUM BETWEEN ? AND ?";

        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + query + "%");
            st.setInt(2, 1 + (page - 1) * 15);
            st.setInt(3, page * 15);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int postNum = rs.getInt(2);
                String studentNum = rs.getString(3);
                String title = rs.getString(4);
                String productInfo = rs.getString(5);
                Timestamp date = rs.getTimestamp(6);
                String state = rs.getString(7);
                int numOfParticipants = rs.getInt(8);
                int limitOfParticipants = rs.getInt(9);


                Post post = new Post(postNum, title, date, studentNum, numOfParticipants, limitOfParticipants, productInfo, state);
                list.add(post);
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getPostCount() {
        return getPostCount("title", "");
    }

    public int getPostCount(String field, String query) {
        int count = 0;
        List<Post> list = new ArrayList<>();
        String sql = "SELECT COUNT(POSTNUM) COUNT FROM ( " +
                "    SELECT ROWNUM NUM, P.* " +
                "    FROM (SELECT * FROM POST WHERE " + field + " LIKE ? ORDER BY DATE_ DESC)   P " +
                "    ) ";

        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + query + "%");
            ResultSet rs = st.executeQuery();

            if (rs.next())
                count = rs.getInt(1);

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public Post getPost(int postNum) {
        String sql = "SELECT * FROM POST WHERE POSTNUM=?";
        Post post = null;
        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            rs.next();

            String title = rs.getString(3);
            Timestamp date = rs.getTimestamp(5);
            String studentNum = rs.getString(2);
            int numOfParticipants = rs.getInt(7);
            int limitOfParticipants = rs.getInt(8);
            String productInfo = rs.getString(4);
            String state = rs.getString(6);

            post = new Post(postNum, title, date, studentNum, numOfParticipants, limitOfParticipants, productInfo, state);
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public Post getNextPost(int postNum) {
        String sql = "SELECT * FROM POST WHERE POSTNUM = ( " +
                "    SELECT POSTNUM FROM POST " +
                "    WHERE date_ > (select date_ from post where POSTNUM = ?) " +
                "    and rownum = 1 " +
                "    )";
        Post post = null;
        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            rs.next();

            String title = rs.getString(3);
            Timestamp date = rs.getTimestamp(5);
            String studentNum = rs.getString(2);
            int numOfParticipants = rs.getInt(7);
            int limitOfParticipants = rs.getInt(8);
            String productInfo = rs.getString(4);
            String state = rs.getString(6);

            post = new Post(postNum, title, date, studentNum, numOfParticipants, limitOfParticipants, productInfo, state);
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public Post getPrevPost(int postNum) {
        String sql = "SELECT * FROM POST WHERE POSTNUM = ( " +
                "    select * from (SELECT POSTNUM FROM POST " +
                "    WHERE DATE_ < (SELECT DATE_ FROM POST WHERE POSTNUM = ?) " +
                "    ORDER BY date_ desc) where ROWNUM = 1 " +
                "    ) ";
        Post post = null;
        try {
        	connectWithDB();connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            rs.next();


            String title = rs.getString(3);
            Timestamp date = rs.getTimestamp(5);
            String studentNum = rs.getString(2);
            int numOfParticipants = rs.getInt(7);
            int limitOfParticipants = rs.getInt(8);
            String productInfo = rs.getString(4);
            String state = rs.getString(6);

            post = new Post(postNum, title, date, studentNum, numOfParticipants, limitOfParticipants, productInfo, state);
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public int insertPost(Post post) {
        int result = 0;
        String sql = "INSERT into post values(?, ?, ?, ?, TO_DATE(?, 'yyyymmddhh24mi'), ? ,? ,?)";
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String strTime = timestampToString(time);
        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, post.getPostNum());
            st.setString(2, post.getStudentNum());
            st.setString(3, post.getTitle());
            st.setString(4, post.getProductInfo());
            st.setString(5, strTime);
            st.setString(6, post.getState());
            st.setInt(7, post.getNumOfParticipants());
            st.setInt(8, post.getLimitOfParticipants());

            result = st.executeUpdate();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deletePost(int postNum) {
        boolean result = false;
        String sql = "DELETE FROM POST WHERE POSTNUM = ?";

        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            result = rs.next();

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean updatePost(Post post) {
        boolean result = false;
        String sql = "UPDATE POST SET TITLE=?, PRODUCTINFO=?, NUMOFPARTICIPANTS=?, STATE=? WHERE POSTNUM=?";

        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, post.getTitle());
            st.setString(2, post.getProductInfo());
            st.setInt(3, post.getNumOfParticipants());
            st.setString(4, post.getState());
            st.setInt(5, post.getPostNum());
            ResultSet rs = st.executeQuery();

            result = rs.next();

            rs.close();
            st.close();
            con.close();
        } catch ( SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getLastPostNum() {
        int lastPostNum = 0;
        String sql = "SELECT POSTNUM as pno FROM ( " +
                "    SELECT ROWNUM NUM, P.* " +
                "    FROM (SELECT * FROM POST ORDER BY DATE_ DESC) P " +
                "    ) where num = 1";
        try {
        	connectWithDB();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                lastPostNum = rs.getInt(1); // this doesnt work
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastPostNum;
    }
    public List<Post> getMyPostList(String studentNum, int page) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT * FROM ( " +
                "    SELECT ROWNUM NUM, P.* " +
                "    FROM (SELECT * FROM POST WHERE studentnum LIKE ? ORDER BY DATE_ DESC)   P " +
                "    ) " +
                "WHERE NUM BETWEEN ? AND ?";

        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, studentNum);
            st.setInt(2, 1 + (page - 1) * 15);
            st.setInt(3, page * 15);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String title = rs.getString(4);
                Timestamp date = rs.getTimestamp(6);
                int numOfParticipants = rs.getInt(8);
                int limitOfParticipants = rs.getInt(9);
                String productInfo = rs.getString(5);
                String state = rs.getString(7);
                int postNum = rs.getInt(2);

                Post post = new Post(postNum, title, date, studentNum, numOfParticipants, limitOfParticipants, productInfo, state);
                list.add(post);
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
	public String timestampToString(Timestamp time)
	{
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		String res = fm.format(time);
		
		return res;
	}
}
