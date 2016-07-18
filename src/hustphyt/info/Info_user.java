package hustphyt.info;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Info_user {
	public String phoneNumber, name, details, password, refuseInfo, message, groupName, permission;
	public int id, groupId, state, oldState;
	public int[] stateCount;
	public long lastLogin;
	public boolean isExpand;
	
	public Info_user(int id)
	{
		this.id=id;
	}
	
	public Info_user(String phoneNumber)
	{
		this.phoneNumber=phoneNumber;
	}
	
	public Info_user(int id, int state)
	{
		this.id=id;
		this.state=state;
	}
	
	public Info_user(int type, String[] strArray)
	{
		if (type==1)
		{
			phoneNumber=strArray[1];
			password=strArray[2];
			name=strArray[3];
			details=strArray[4];
		} else if (type==2)
		{
			phoneNumber=strArray[1];
			password=strArray[2];
		} else if (type==3)
		{
			id=Integer.parseInt(strArray[1]);
		} else if (type==4)
		{
			id=Integer.parseInt(strArray[0]);
			phoneNumber=strArray[1];
			name=strArray[2];
			details=strArray[3];
		} else if (type==5)
		{
			id=Integer.parseInt(strArray[1]);
			groupId=Integer.parseInt(strArray[2]);
			groupName=strArray[3];
		} else if (type==6)
		{
			id=Integer.parseInt(strArray[0]);
			name=strArray[1];
			phoneNumber=strArray[2];
			details=strArray[3];
			permission=strArray[4];
		} else if (type==7)
		{
			id=Integer.parseInt(strArray[0]);
			name=strArray[1];
			stateCount=new int[3];
			for (int i=0;i<3;i++)
				stateCount[i]=Integer.parseInt(strArray[2+i]);
		} else if (type==8)
		{
			name=strArray[0];
			phoneNumber=strArray[1];
		} else if (type==9)
		{
			id=Integer.parseInt(strArray[1]);
			message=strArray[2];
		} else if (type==10)
		{
			id=Integer.parseInt(strArray[1]);
			groupId=Integer.parseInt(strArray[2]);
			permission=strArray[3];
		}
	}

	public String getFormatString(int type)
	{
		if (type==1)
			return phoneNumber+Info.sep1+password+Info.sep1+name+Info.sep1+details;
		else if (type==2)
			return phoneNumber+Info.sep1+password;
		else if (type==3)
			return id+"";
		else if (type==4)
			return id+Info.sep1+phoneNumber+Info.sep1+name+Info.sep1+details;
		else if (type==5)
			return id+Info.sep1+groupId+Info.sep1+groupName;
		else if (type==6)
			return id+Info.sep1+name+Info.sep1+phoneNumber+Info.sep1+details+Info.sep1+permission+Info.sep2;
		else if (type==7)
			return id+Info.sep1+name+Info.sep1+stateCount[0]+Info.sep1+stateCount[1]+Info.sep1+stateCount[2]+Info.sep2;
		else if (type==8)
			return name+Info.sep1+phoneNumber+Info.sep2;
		else if (type==9)
			return id+Info.sep1+message;
		else if (type==10)
			return id+Info.sep1+groupId+Info.sep1+permission;
		return "";
	}
	
	public void encryptPassword()
	{
		if (password!=null)
			password=MD5(password);
	}

	public static String MD5(String str) {
		MessageDigest md5 =null;

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		 
		char[] charArray = str.toCharArray();
		byte[] byteArray =new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue =new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) &0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		
		//return encryptmd5(hexValue.toString());
		return hexValue.toString();	
	}
		 
		// 可逆的加密算法
	public static String encryptmd5(String str) {
		char[] a = str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^'l');
		}
		String s = new String(a);
		return s;
	}
	
	public void getInfoFromRs(ResultSet rs) throws SQLException
	{
		id=rs.getInt(Info.str_id);
		password=rs.getString(Info.str_password);
		name=rs.getString(Info.str_name);
		phoneNumber=rs.getString(Info.str_phoneNumber);
		details=rs.getString(Info.str_details);
		lastLogin=rs.getLong(Info.str_lastLogin);
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
    	String sql="insert into _user(phoneNumber,password,name,details) values(?,?,?,?)";    
		System.out.println(sql);
    	PreparedStatement preStmt =con.prepareStatement(sql);  
    	Info_user user=Info.user;
        preStmt.setString(1,phoneNumber);
        preStmt.setString(2,password);
        preStmt.setString(3,name);
        preStmt.setString(4,details);
        return preStmt;
	}
	
	public PreparedStatement getFeedbackInsertStatement(Connection con) throws SQLException
	{
    	String sql="insert into _feedback(userId,message) values(?,?)";    
		System.out.println(sql);
    	PreparedStatement preStmt =con.prepareStatement(sql);  
        preStmt.setInt(1,id);
        preStmt.setString(2,message);
        return preStmt;
	}
	
	
	public ResultSet getRsFromPhoneNumber(Connection con) throws SQLException
	{
		String sql = "select * from _user u where u.phoneNumber = '"+ phoneNumber+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getRsFromId(Connection con) throws SQLException
	{
		String sql = "select * from _user u where u.id = '"+ id+"'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}

	public PreparedStatement getUpdateStatement(Connection con) throws SQLException
	{
		String sql = "update _user set "+Info.str_lastLogin+"="+System.currentTimeMillis()+" where "+Info.str_id+"="+id;
		System.out.println(sql);
		PreparedStatement statement = con.prepareStatement(sql);
        return statement;
	}
}