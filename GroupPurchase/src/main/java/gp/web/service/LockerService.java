package gp.web.service;
import gp.web.entity.Locker;

import java.sql.*;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LockerService {
	
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
    public void allocateLcoker(int postNum) {
        Random rand = new Random();
        String pwd = String.valueOf(rand.nextInt(10000));

        String sql = "update locker set postnum=?, password=?, isallocated=? " +
                "where LOCKERNUM = ( " +
                "    select LOCKERNUM from (select * from locker where ISALLOCATED='F') where rownum=1 " +
                "    );";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            st.setString(2, pwd);
            st.setString(3, "T");

            st.executeUpdate();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void collectLocker(int lockerNum) {
        Random rand = new Random();
        String pwd = String.valueOf(rand.nextInt(10000));

        String sql = "update locker set isallocated='F' where LOCKERNUM = ?";


        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, lockerNum);

            st.executeUpdate();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Locker getLockerInfo(int postNum) {
        String sql = "SELECT * from locker where postnum=?";
        Locker locker = null;

        try {
        	connectWithDB();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, postNum);
            ResultSet rs = st.executeQuery();

            if(rs.next())
            {
		        int lockerNum = rs.getInt(1);
		        String password = rs.getString(3);
		        String isAllocated = rs.getString(4);
		        
		        locker = new Locker(lockerNum, postNum, password, isAllocated);
            }
            

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locker;

    }
}
