package hustphyt.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Map;

public class Info_comment {
	public int noticeId, commentId, userId, groupId, type, likes;
	public Map<Long,Long> times;
	public String details, place, timeString, userName;
	
	public Info_comment()
	{	
	}
	
	public Info_comment(int noticeId, int userId, String details)
	{
		this.noticeId=noticeId;
		this.userId=userId;
		this.details=details;
	}
	
	public Info_comment(int noticeId, int userId, String timeString, String place, String details)
	{
		this.noticeId=noticeId;
		this.userId=userId;
		this.timeString=timeString;
		this.place=place;
		this.details=details;
	}
	
	public Info_comment(int formatType,String[] strArr)
	{
		if (formatType==0)
		{
			type=Integer.parseInt(strArr[1]);
			if (type==0)
			{
				noticeId=Integer.parseInt(strArr[2]);
				userId=Integer.parseInt(strArr[3]);
				details=strArr[4];
			} else if (type==1)
			{
				noticeId=Integer.parseInt(strArr[2]);
				userId=Integer.parseInt(strArr[3]);
				timeString=strArr[4];
				place=strArr[5];
				details=strArr[6];
			}
		} else if (formatType==1)
		{
			type=Integer.parseInt(strArr[0]);
			if (type==0)
			{
				noticeId=Integer.parseInt(strArr[1]);
				userId=Integer.parseInt(strArr[2]);
				userName=strArr[3];
				details=strArr[4];
			} else if (type==1)
			{
				noticeId=Integer.parseInt(strArr[1]);
				userId=Integer.parseInt(strArr[2]);
				userName=strArr[3];
				timeString=strArr[4];
				place=strArr[5];
				details=strArr[6];
			}
		}
	}
	
	public Info_comment (ResultSet rs) throws SQLException
	{
		type=rs.getInt(Info.str_type);
		if (type==0)
		{
			userId=rs.getInt(Info.str_userId);
			details=rs.getString(Info.str_details);
		} else if (type==1)
		{
			userId=rs.getInt(Info.str_userId);
			details=rs.getString(Info.str_details);
			timeString=rs.getString(Info.str_timeString);
			place=rs.getString(Info.str_place);
		}
	}
	
	public String getFormatString(int formatType)
	{
		if (formatType==0)
		{
			if (type==0)
				return type+Info.sep1+noticeId+Info.sep1+userId+Info.sep1+details;
			else if (type==1)
				return type+Info.sep1+noticeId+Info.sep1+userId+Info.sep1+timeString+Info.sep1+place+Info.sep1+details;
		} else if (formatType==1)
		{
			if (type==0)
				return type+Info.sep1+noticeId+Info.sep1+userId+Info.sep1+userName+Info.sep1+details+Info.sep2;
			else if (type==1)
				return type+Info.sep1+noticeId+Info.sep1+userId+Info.sep1+userName+Info.sep1+timeString+Info.sep1+place+Info.sep1+details+Info.sep2;
		} 
		return "";
	}
	
	public String getCommentString()
	{
		if (type==0)
			return details;
		else if (type==1)
		{
			StringBuffer buffer=new StringBuffer();
			if (!timeString.equals(" "))
			{
				buffer.append("可接受时间段： ");
				String[] strArr=timeString.split(Info.sep3);
				for (int i=0;i<strArr.length;i+=2)
				{
					buffer.append("\n");
					Info_notice notice=new Info_notice();
					notice.time=Long.parseLong(strArr[i]);
					buffer.append(notice.getFormatTime(0)+" —— ");
					notice.time=Long.parseLong(strArr[i+1]);
					buffer.append(notice.getFormatTime(1)+"    ");
				}
				buffer.append("\n");
			}
			if (!place.equals(" "))
			{
				buffer.append("可接受地点： "+place+"\n");
			}
			buffer.append(details);
			return buffer.toString();
		}
		return "";
	}

	public PreparedStatement getCreatStatement(Connection con) throws SQLException
	{
		String sql = "CREATE TABLE nc"+noticeId+" (id int Primary key auto_increment, type int not null, userId int, details varchar(255), timeString varchar(255), place varchar(20));";
		System.out.println(sql);
		PreparedStatement preStmt =con.prepareStatement(sql);  
	    return preStmt; 
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
		String sql = "";
	    PreparedStatement preStmt = null;
		if (type==0)
		{
			sql="insert into nc"+noticeId+"(type,userId,details) values(?,?,?)";    
			preStmt=con.prepareStatement(sql);  
			preStmt.setInt(1, type);
			preStmt.setInt(2, userId);
			preStmt.setString(3, details);
		} else if (type==1)
		{
			sql="insert into nc"+noticeId+"(type,userId,details,timeString,place) values(?,?,?,?,?)";    
			preStmt=con.prepareStatement(sql);  
			preStmt.setInt(1, type);
			preStmt.setInt(2, userId);
			preStmt.setString(3, details);
			preStmt.setString(4, timeString);
			preStmt.setString(5, place);
		}
		System.out.println(sql); 
        return preStmt;
	}
	
	public ResultSet getRsAll(Connection con) throws SQLException
	{
		String sql = "select * from nc"+noticeId+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getCountRsAll(Connection con) throws SQLException
	{
		String  sql = "select count(*) from nc"+noticeId+" u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
}
