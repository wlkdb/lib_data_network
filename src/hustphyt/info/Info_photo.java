package hustphyt.info;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Map;

public class Info_photo {
	public int photoId, userId, noticeId, index ,counts;
	public String userName, details;
	public byte[] photo;
	
	public Info_photo()
	{	
	}
	
	public Info_photo(int noticeId, int index)
	{
		this.noticeId=noticeId;
		this.index=index;
	}
	
	public Info_photo(int noticeId, int userId, String details, byte[] photo)
	{
		this.noticeId=noticeId;
		this.userId=userId;
		this.details=details;
		this.photo=photo;
	}
	
	public Info_photo(int formatType,String[] strArr)
	{
		if (formatType==0)
		{
			this.noticeId=Integer.parseInt(strArr[1]);
			this.userId=Integer.parseInt(strArr[2]);
			this.details=strArr[3];
		} else if (formatType==1)
		{
			this.userId=Integer.parseInt(strArr[0]);
			this.userName=strArr[1];
			this.details=strArr[2];
			this.counts=Integer.parseInt(strArr[3]);
		}
	}
	
	public Info_photo (ResultSet rs) throws SQLException
	{
		this.userId=rs.getInt(Info.str_userId);
		this.details=rs.getString(Info.str_details);
		this.photo=rs.getBytes(Info.str_photo);
	}
	
	public String getFormatString(int formatType)
	{
		if (formatType==0)
		{
			return noticeId+Info.sep1+userId+Info.sep1+details;
		} else if (formatType==1)
		{
			return userId+Info.sep1+userName+Info.sep1+details+Info.sep1+counts;
		} 
		return "";
	}

	public PreparedStatement getCreatStatement(Connection con) throws SQLException
	{
		String sql = "CREATE TABLE np"+noticeId+" (id int Primary key auto_increment, userId int not null, details varchar(255) not null, photo BLOB not null);";
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql);  
	    return preStmt; 
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
		String sql = "";
	    PreparedStatement preStmt = null;
		sql="insert into np"+noticeId+"(userId,details,photo) values(?,?,?)";    
		preStmt=con.prepareStatement(sql);  
		preStmt.setInt(1, userId);
		preStmt.setString(2, details);
		InputStream is = new ByteArrayInputStream(photo);  
    	try {
			preStmt.setBinaryStream(3, is, is.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return preStmt;
	}
	
	public ResultSet getRsFromIndex(Connection con) throws SQLException
	{
		String sql = "select * from np"+noticeId+" u limit "+index+",1";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		
		return rs;
		
	}
	
	public ResultSet getRsAll(Connection con) throws SQLException
	{
		String sql = "select * from np"+noticeId+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}

	public ResultSet getCountRsAll(Connection con) throws SQLException
	{
		String  sql = "select count(*) from np"+noticeId+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
}
