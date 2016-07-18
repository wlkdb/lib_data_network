package hustphyt.info;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

public class Info_notice implements Comparable<Info_notice>{
	public int id, groupId, type, creator, checkedNum, refuseNum, nocheckNum, joinNum, iconId, commentsNum, photosNum;
	public String title, place, details, groupName, creatorName, userState, joinNames;
	public long time, lastTime;
	public boolean isExpand, isRecord;
	public long clockAhead;
	public byte[] icon;

	public int compareTo(Info_notice notice2) {
		// TODO Auto-generated method stub
		if ((this.userState.equals(Info_noticeUsers.getState(0)))&&(!notice2.userState.equals(Info_noticeUsers.getState(0))))
			return -1;
		if ((notice2.userState.equals(Info_noticeUsers.getState(0)))&&(!this.userState.equals(Info_noticeUsers.getState(0))))
			return 1;
		if (this.time<notice2.time)
			return -1;
		else if (this.time==notice2.time)
			return 0;
		return 1;
	}
	
	public Info_notice() {
		// TODO Auto-generated constructor stub
	}
	
	public Info_notice(int id)
	{
		this.id=id;
	}
	
	public Info_notice(int formatType, String[] strArr)
	{
		if (formatType==1)
		{
			clockAhead=-1;
			groupName=strArr[0];
			type=Integer.parseInt(strArr[1]);
			title=strArr[2];
			time=Long.parseLong(strArr[3]);
			place=strArr[4];
			details=strArr[5];
			userState=strArr[6];
			id=Integer.parseInt(strArr[7]);
			groupId=Integer.parseInt(strArr[8]);
			int i=9;
			if ((userState.equals("super"))||(type==2)||type>=10)
			{
				nocheckNum=Integer.parseInt(strArr[9]);
				if (type==0)
					checkedNum=Integer.parseInt(strArr[10]);
				else if (type==1)
				{
					refuseNum=Integer.parseInt(strArr[10]);
					joinNum=Integer.parseInt(strArr[11]);
				}
				else if (type==2||type>=10)
				{
					checkedNum=Integer.parseInt(strArr[10]);
					joinNum=Integer.parseInt(strArr[11]);
				}
				if (type==0)
					i+=2;
				else
					i+=3;
			}
			if (type==2)
			{
				joinNames=strArr[i];
				i++;
			}
			creatorName=strArr[i++];
			if (i<strArr.length)
				commentsNum=Integer.parseInt(strArr[i++]);
			if (i<strArr.length)
				photosNum=Integer.parseInt(strArr[i++]);
			if (type>9)
				lastTime=Long.parseLong(strArr[i++]);
		} else if (formatType==2)
		{
			creator=Integer.parseInt(strArr[1]);
			groupId=Integer.parseInt(strArr[2]);
			type=Integer.parseInt(strArr[3]);
			title=strArr[4];
			time=Long.parseLong(strArr[5]);
			place=strArr[6];
			details=strArr[7];
			lastTime=Long.parseLong(strArr[8]);
		} else if (formatType==3)
		{
			clockAhead=-1;
			groupName=strArr[0];
			type=Integer.parseInt(strArr[1]);
			title=strArr[2];
			time=Long.parseLong(strArr[3]);
			place=strArr[4];
			details=strArr[5];
			userState=strArr[6];
			id=Integer.parseInt(strArr[7]);
			groupId=Integer.parseInt(strArr[8]);
			creatorName=strArr[9];
		} else if (formatType==4)
		{
			id=Integer.parseInt(strArr[2]);
		}
	}

	public String getFormatString(int formatType)
	{
		String s1=Info.sep1, s2=Info.sep2;
		if (formatType==1)
		{
			String string = groupName+s1+type+s1+title+s1+time+s1+place+s1+details+s1+userState+s1+id+s1+groupId;
			if ((userState.equals("super"))||(type==2)||type>=10)
				if (type==0)
					string=string+s1+nocheckNum+s1+checkedNum;
				else if (type==1)
					string=string+s1+nocheckNum+s1+refuseNum+s1+joinNum;
				else if (type==2||type>=10)
					string=string+s1+nocheckNum+s1+checkedNum+s1+joinNum;
			if (joinNames==null||joinNames.equals(""))
				joinNames=" ";
			if (type==2)
				string=string+s1+joinNames;
			string=string+s1+creatorName;
			string=string+s1+commentsNum;
			string=string+s1+photosNum;
			if (type>9)
				string=string+s1+lastTime;
			return string+s2;
		} else if (formatType==2)
		{
			return creator+Info.sep1+groupId+Info.sep1+type+Info.sep1+title+Info.sep1+time+Info.sep1+place+Info.sep1+details+Info.sep1+lastTime+Info.sep1;
		} else if (formatType==3)
			return groupName+s1+type+s1+title+s1+time+s1+place+s1+details+s1+userState+s1+id+s1+groupId+s1+creatorName+s2;
		return "";
	}

	public String getShorMessage()
	{
		return title+"("+groupName+")";
	}
	
	public int getDays()
	{
		Date nowDate=new Date();
		Date date=new Date(time);
		int days=(int)((time-System.currentTimeMillis())/(1000*60*60*24));
		if (time<System.currentTimeMillis())
			days--;
		int temp=(date.getHours()-nowDate.getHours())*3600+(date.getMinutes()-nowDate.getMinutes())*60+(date.getSeconds()-nowDate.getSeconds());
		if (temp<0)
			days++;
		return days;
	}
	
	public static String getWeek(Date date)
	{
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
	}
	
	public String getFormatTime(int flag)
	{
		Date date=new Date(time);
		Date nowDate=new Date();
		int days=getDays();
		String string="";
		if (date.getYear()==nowDate.getYear()+1)
			string="明年";
		else if (date.getYear()!=nowDate.getYear())
			string=date.getYear()+"年";
		if (days==0)
			string=string+"今天";
		else if (days==1)
			string=string+"明天";
		else {
			string=(date.getMonth()+1)+"月"+(date.getDate())+"日";
		}
		if (flag==0)
			string=string+getWeek(date);
		string=string+date.getHours()+":"+(date.getMinutes()<10?"0":"")+date.getMinutes();
		return string;
	}
	
	public String getJoinString()
	{
		if (type==0)
			return checkedNum+"人已查看，"+nocheckNum+"人未查看";
		else if (type==1)
			if (isRecord)
				return refuseNum+"人请假，"+joinNum+"人参加，"+nocheckNum+"人缺勤";
			else
				return refuseNum+"人请假，"+joinNum+"人报名，"+nocheckNum+"人未查看";
		else if (type==2)
			return joinNum+"人报名，"+checkedNum+"人已查看，"+nocheckNum+"人未查看";
		else if (type>9)
			return joinNum+"人感兴趣，"+checkedNum+"人已查看，"+nocheckNum+"人未查看";
		else
			return "";
	}
	
	public String getShortJoinString()
	{
		if (type==2)
			return joinNum+"人报名";
		else if (type>9)
			return joinNum+"人感兴趣";
		return "";
	}
	
	public String getSubitemString()
	{
		StringBuffer sb=new StringBuffer();
		if (photosNum>0)
			sb.append(photosNum+"张照片");
		if (commentsNum>0)
		{
			if (photosNum>0)
				sb.append("，");
			sb.append(commentsNum+"条评论");
		}
		return sb.toString();
	}

	public void getInfoFromRs(ResultSet rs) throws SQLException
	{
		id=rs.getInt("id");
		title=rs.getString("title");
		groupId=rs.getInt("groupId");
		details=rs.getString("details");
		place=rs.getString("place");
		type=rs.getInt("type");
		creator=rs.getInt("creator");
		time=rs.getLong("time");
		if (type>9)
			lastTime=rs.getLong(Info.str_lastTime);
	}
	
	public PreparedStatement getInsertStatement(Connection con) throws SQLException
	{
		String sql;
		if (icon==null)
			sql="insert into _notice(groupId,type,title,time,place,details,creator,lastTime) values(?,?,?,?,?,?,?,?)";    
		else
			sql="insert into _notice(groupId,type,title,time,place,details,creator,lastTime,icon) values(?,?,?,?,?,?,?,?,?)";    
		System.out.println(sql);
	    PreparedStatement preStmt =con.prepareStatement(sql);  
        preStmt.setInt(1,groupId);
        preStmt.setInt(2,type);
        preStmt.setString(3,title);
        preStmt.setLong(4,time);
        preStmt.setString(5,place);
        preStmt.setString(6,details);
        preStmt.setInt(7, creator);
        preStmt.setLong(8, lastTime);
        if (icon!=null)
        {
        	InputStream is = new ByteArrayInputStream(icon);  
        	try {
				preStmt.setBinaryStream(9, is, is.available());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return preStmt;
	}
	
	public ResultSet getRsFromTime(Connection con) throws SQLException
	{
		String sql = "select * from _notice u where u.time = '" + time + "'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet getRsFromId(Connection con) throws SQLException
	{
		String sql = "select * from _notice u where u.id = '" + id + "'";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
}
