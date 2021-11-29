package gp.web.service;

import gp.web.entity.Join;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JoinService {
	
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
	
    public int getCurrentNumOfParticipants(int postNum) {
        int currentNumOfParticipants = -1;
        String sql = "select count(*) as num from join where POSTNUM = ?";



        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            if (rs.next())
                currentNumOfParticipants = rs.getInt(1);

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentNumOfParticipants;
    }


    public List<Join> getJoinList(int postNum) {
        List<Join> list = new ArrayList<>();
        String sql = "select * from join where POSTNUM = ?";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String studentNum = rs.getString(2);
                Date time = rs.getDate(3);

                Join join = new Join(postNum, studentNum, time);
                list.add(join);
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Join> getJoinList(String studentNum) {
        List<Join> list = new ArrayList<>();
        String sql = "select * from join where STUDENTNUM = ?";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, studentNum);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int postNum = rs.getInt("postNum");
                Date time = rs.getDate("time");

                Join join = new Join(postNum, studentNum, time);
                list.add(join);
            }

            rs.close();
            st.close();
            con.close();
        } catch ( SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void setJoin(int postNum, String studentNum) {
        String sql = "insert into join values (?, ?, ?)";



        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, postNum);
            st.setString(2, studentNum);
            st.setDate(3, new Date(System.currentTimeMillis()));

            st.execute();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteJoin(int postNum, String studentNum) {
        String sql = "delete from join where postNum=? and studentnum=?";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, postNum);
            st.setString(2, studentNum);

            st.executeUpdate();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteJoin(int postNum) {
        String sql = "delete from join where postNum=?";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, postNum);

            st.executeUpdate();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}