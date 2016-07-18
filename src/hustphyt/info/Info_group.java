package hustphyt.info;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Info_group {
	public int id;
	public String name;
	public int creator;
	public String creatorName;
	public String details;
	public String permission;
	public int iconId;
	public byte[] icon;
	
	public Info_group(int id)
	{
		this.id=id;
	}

	public Info_group()
	{
	}
	
	public Info_group(int type, String[] strArr)
	{
		if (type==1)
		{
			id=Integer.parseInt(strArr[0]);
			name=strArr[1];
			permission=strArr[2];
			creatorName=strArr[3];
			details=strArr[4];
		} else if (type==2)
		{
			creator=Integer.parseInt(strArr[1]);
			name=strArr[2];
			details=strArr[3];
			iconId=Integer.parseInt(strArr[4]);
		} else if (type==3)
		{
			id=Integer.parseInt(strArr[2]);
			name=strArr[3];
		} else if (type==4)
		{
			id=Integer.parseInt(strArr[1]);
		}
	}
	
	public String getFormatString(int type)
	{
		if (type==1)
			return id+Info.sep1+name+Info.sep1+permission+Info.sep1+creatorName+Info.sep1+details+Info.sep2;
		else if (type==2)
			return creator+Info.sep1+name+Info.sep1+details+Info.sep1+iconId;
		return "";
	}
	
	public void getInfoFromRs(ResultSet rs) throws SQLException
	{
		id=rs.getInt("id");
		name=rs.getString("name");
		creator=rs.getInt("creator");
		details=rs.getString("details");
		iconId=rs.getInt("iconId");
		if (iconId==-1)
			icon=rs.getBytes("icon");
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
    	String sql;
    	if (iconId!=-1)
    		sql="insert into _group(name,creator,details,iconId) values(?,?,?,?)";    
    	else
    		sql="insert into _group(name,creator,details,iconId,icon) values(?,?,?,?,?)";    
		System.out.println(sql);
	    PreparedStatement preStmt =con.prepareStatement(sql);  
        preStmt.setString(1,name);
        preStmt.setInt(2,creator);
        preStmt.setString(3,details);
        preStmt.setInt(4, iconId);
        if (iconId==-1)
        {
        	InputStream is = new ByteArrayInputStream(icon);  
        	try {
				preStmt.setBinaryStream(5, is, is.available());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return preStmt;
	}
	
	public ResultSet getRsFromName(Connection con) throws SQLException
	{
		String sql = "select * from _group u where u.name = '"+name+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getRsFromId(Connection con) throws SQLException
	{
		String sql = "select * from _group u where u.id = '"+id+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public static ResultSet getRsAll(Connection con) throws SQLException
	{
		String sql = "select * from _group u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
}
