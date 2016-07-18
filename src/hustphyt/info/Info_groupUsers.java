package hustphyt.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Info_groupUsers {
	public int id;
	public int userId;
	public String permission=null;
	public List<Info_user> users=null;
	private static String[] strs_permission=new String[]{"super","admin","member"};

	public Info_groupUsers(int id)
	{
		this.id=id;
	}
	
	public Info_groupUsers(int id, int userId)
	{
		this.id=id;
		this.userId=userId;
		this.permission=strs_permission[2];
	}
	
	public Info_groupUsers(String[] strArr)
	{
		this.id=Integer.parseInt(strArr[1]);
		this.users=new ArrayList<Info_user>();
		for (int i=2;i<strArr.length;i+=2)
		{
			Info_user user=new Info_user(strArr[i]);
			user.name=strArr[i+1];
			users.add(user);
		}
	}
	
	public String getFormatString()
	{
		return id+Info.sep1+userId;
	}
	
	public static String getPermission(int i)
	{
		return strs_permission[i];
	}
	
	public PreparedStatement getCreatStatement(Connection con) throws SQLException
	{
		String sql = "CREATE TABLE gu"+id+" (userId int not null, permission enum('"+strs_permission[0]+"','"+
				strs_permission[1]+"','"+strs_permission[2]+"') not null default 'member', primary key (userId));";
		PreparedStatement preStmt =con.prepareStatement(sql);  
	    return preStmt; 
	}
	
	public PreparedStatement getInsertsStatement(Connection con)throws SQLException
	{
		if (permission==null)
			permission="member";
		String sql="insert into gu"+id+"(userId,permission) values(?,?)";    
		PreparedStatement preStmt =con.prepareStatement(sql);  
        preStmt.setInt(1,userId);
        preStmt.setString(2, permission);
        return preStmt;
	}
	
	public PreparedStatement getDeleteStatement(Connection con)throws SQLException
	{
		String sql="delete from gu"+id+" where userId = ?";   
	    PreparedStatement preStmt = con.prepareStatement(sql);
	    preStmt.setInt(1, userId);
		System.out.println(sql);
        return preStmt;
	}
	
	public PreparedStatement getUpdateStatement(Connection con)throws SQLException
	{
		String sql = "update gu"+id+" set "+Info.str_permission+"='"+permission+"' where "+Info.str_userId+"="+userId;
		System.out.println(sql);
		PreparedStatement statement = con.prepareStatement(sql);
        return statement;
	}
	
	public ResultSet getRsFromUserId(Connection con) throws SQLException
	{
		String sql = "select * from gu"+id+" u where u.userId = '"+userId+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getRsAll(Connection con) throws SQLException
	{
		String sql = "select * from gu"+id+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getCountRsAll(Connection con) throws SQLException
	{
		String  sql = "select count(*) from gu"+id+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
}
