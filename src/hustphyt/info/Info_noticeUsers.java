package hustphyt.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Info_noticeUsers {
	public int id;
	public int userId;
	public String state;
	public String refuseInfo;
	public List<Info_user> users=null;
	private static String[] strs_state=new String[]{"nocheck","checked","join","refuse","super"};
	@SuppressWarnings("serial")
	private static Map<String, Integer> map_state=new HashMap<String, Integer>(){
		{
			put("join",0);
			put("refuse", 1);
			put("nocheck", 2);
			put("super", 3);
			put("check", 4);
		}
	};
	
	public Info_noticeUsers(int id)
	{
		this.id=id;
	}

	public Info_noticeUsers(int id, int userId)
	{
		this.id=id;
		this.userId=userId;
	}
	
	public Info_noticeUsers(String[] strArr)
	{
		id=Integer.parseInt(strArr[1]);
		users=new ArrayList<Info_user>();
		for (int i=2;i<strArr.length;i+=2)
			users.add(new Info_user(Integer.parseInt(strArr[i]),Integer.parseInt(strArr[i+1])));
	}
	
	public Info_noticeUsers(int type, String[] strArr)
	{
		if (type==1)
		{
			id=Integer.parseInt(strArr[1]);
			userId=Integer.parseInt(strArr[2]);
			state=strArr[3];
		}
	}
	
	public String getFormatString(int type)
	{
		if (type==1)
			return id+Info.sep1+userId+Info.sep1+state;
		return "";
	}
	
	public static String getState(int i)
	{
		return strs_state[i];
	}
	
	public static int getState(String str)
	{
		return map_state.get(str);
	}

	public PreparedStatement getCreatStatement(Connection con) throws SQLException
	{
		String sql = "CREATE TABLE nu"+id+" (userId int not null, state enum('"+strs_state[0]+"','"+strs_state[1]+"','"+strs_state[2]+"','"+
				strs_state[3]+"','"+strs_state[4]+"') not null default 'nocheck', refuseInfo varchar(255),primary key (userId));";
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql);  
	    return preStmt; 
	}
	
	public PreparedStatement getReplaceStatement(Connection con) throws SQLException
	{
		if (refuseInfo==null)
			refuseInfo="";
		String sql="replace into nu"+id+"(userId,state,refuseInfo) values(?,?,?)";
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql);  
        preStmt.setInt(1,userId);
        preStmt.setString(2, state);
        preStmt.setString(3, refuseInfo);
        return preStmt;
	}
	
	public ResultSet getRsFromUserId(Connection con) throws SQLException
	{
		String sql = "select * from nu"+id+" u where u.userId = '"+userId+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getRsFromState(Connection con) throws SQLException
	{
		String sql = "select * from nu"+id+" u where u.state = '"+state+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getCountRsFromState(Connection con) throws SQLException
	{
		String  sql = "select count(*) from nu"+id+" u where u.state = '"+state+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}

}
