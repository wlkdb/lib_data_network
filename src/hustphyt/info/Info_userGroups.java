package hustphyt.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Info_userGroups {
	public int id, groupId;
	
	public Info_userGroups(int id)
	{
		this.id=id;
	}
	
	public Info_userGroups(int id, int groupId)
	{
		this.id=id;
		this.groupId=groupId;
	}
	
	public PreparedStatement getCreatStatement(Connection con) throws SQLException
	{
		String sql = "CREATE TABLE ug"+id+" (groupId int not null, primary key (groupId))";
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql); 
		return preStmt;
	}
	
	public ResultSet getRsAll(Connection con) throws SQLException
	{
		String sql = "select * from ug"+id+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
		String sql="insert into ug"+id+"(groupId) values(?)";   
		System.out.println(sql); 
		PreparedStatement preStmt =con.prepareStatement(sql);  
	    preStmt.setInt(1,groupId);
	    return preStmt;
	}

	public PreparedStatement getDeleteStatement(Connection con) throws SQLException
	{
		String sql="delete from ug"+id+" where groupId = ?";   
		PreparedStatement preStmt =con.prepareStatement(sql);  
		preStmt.setInt(1, groupId);
		System.out.println(sql); 
	    return preStmt;
	}
}
