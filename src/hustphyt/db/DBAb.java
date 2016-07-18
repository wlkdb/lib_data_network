package hustphyt.db;

import hustphyt.info.Info_group;
import hustphyt.info.Info_groupNotices;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;
import hustphyt.info.Info_userGroups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DBAb {
	//驱动程序名
	private static String driver = "";
	//URL指向要访问的数据库名
	private static String URL = "";
	//MySql配置时的用户名密码
	private static String username = "";
	private static String password = "";
	protected Connection con=null;
	protected ResultSet rs;
	protected PreparedStatement preStmt;

	protected String[] str_results;
	
	public Info_user user;
	public Info_group group;
	public Info_notice notice;
	public List<Info_user> users;
	public List<Info_group> groups;
	public List<Info_notice> notices;
	
	DBAb()
	{
	}
	
	public static void setDB_Info(String driver,String URL,String username,String password)
	{
		DBAb.driver=driver;
		DBAb.URL=URL;
		DBAb.username=username;
		DBAb.password=password;
	}
	
	public int operate()
	{
		try {
			Class.forName(driver);
			System.out.println("connect db");
			con= DriverManager.getConnection(URL, username, password);
			if (con!=null)
				System.out.println("connect success!");
			else 
				return 0;
			int flag = operateAb();
			con.close();
			return flag;
			} catch (SQLException e) {
			e.printStackTrace();
			}
		 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	protected abstract int operateAb() throws ClassNotFoundException, SQLException;
	
}
