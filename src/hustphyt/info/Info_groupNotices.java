package hustphyt.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Info_groupNotices {
	public int id;
	public int noticeId;
	public String state;
	private static String[] strs_state=new String[]{"active","outdate","cancel"};

	public Info_groupNotices(int id)
	{
		this.id=id;
	}
	
	public static String getState(int state)
	{
		return strs_state[state];
	}
	
	public PreparedStatement getCreatStatement(Connection con) throws SQLException
	{
		String sql = "CREATE TABLE gn"+id+" (noticeId int not null, state enum('"+strs_state[0]+"','"+strs_state[1]+"','"+strs_state[2]+"') not null default 'active', primary key (noticeId));";
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql);  
	    return preStmt; 
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
		String sql="insert into gn"+id+"(noticeId) values(?)";    
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql);  
        preStmt.setInt(1,noticeId);
        return preStmt;
	}

	public PreparedStatement getUpdateStatement(Connection con) throws SQLException
	{
		String sql = "update gn"+id+" set state='outdate' where noticeId="+noticeId;
		System.out.println(sql);
		PreparedStatement statement = con.prepareStatement(sql);
        return statement;
	}
	
	public ResultSet getRsFromState(Connection con) throws SQLException
	{
		String sql = "select * from gn"+id+" u where u.state = '"+state+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
}
