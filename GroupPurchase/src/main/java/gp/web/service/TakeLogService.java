package gp.web.service;

import gp.web.entity.TakeLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TakeLogService {
	
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
	
    public List<TakeLog> getTakeLogList(int postNum) {
        List<TakeLog> list = new ArrayList<>();
        String sql = "select * from TAKE_LOG where POSTNUM = ?";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String studentNum = rs.getString(2);
                Date time = rs.getDate(3);
                String get = rs.getString(4);

                TakeLog takeLog = new TakeLog(postNum, studentNum, time,get);
                list.add(takeLog);
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void setTakeLog(int postNum, String studentNum) {
        String sql = "insert into join values (?, ?, ?,'T')";


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
}