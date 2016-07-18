package hustphyt.info;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Info {
	public static String serverIP="";
	public static int serverPort=1;
	public static String sep1="/";
	public static String sep2="//";
	public static String sep3="-";
	public static String str_endWrite="endWrite";
	public static String str_id="id";
	public static String str_userId="userId";
	public static String str_name="name";
	public static String str_phoneNumber="phoneNumber";
	public static String str_password="password";
	public static String str_details="details";
	public static String str_creator="creator";
	public static String str_iconId="iconId";
	public static String str_icon="icon";
	public static String str_groupId="groupId";
	public static String str_type="type";
	public static String str_time="time";
	public static String str_lastTime="lastTime";
	public static String str_place="place";
	public static String str_title="title";
	public static String str_noticeId="noticeId";
	public static String str_state="state";
	public static String str_permission="permission";
	public static String str_refuseInfo="refuseInfo";
	public static String str_lastLogin="lastLogin";
	public static String str_message="message";
	public static String str_commentId="commentId";
	public static String str_timeString="timeString";
	public static String str_photo="photo";
	
	public static String[][] joinInfoTitles={{"已查看公告的组员有","未查看公告的组员有"},{"活动报名的组员有","活动请假的组员有","未查看活动的组员有"},
											 {"查看活动的组员有","活动报名的组员有","未查看活动的组员有"},{"参与组员","请假组员","缺勤组员"},
											 {"查看意向的组员有","对意向感兴趣的组员有","未查看意向的组员有"}}; 

	public static Info_user user;
	public static Info_group group;
	public static Info_notice notice;
	public static Info_photo photo;
	public static List<Info_user> users;
	public static List<Info_group> groups;
	public static List<Info_notice> notices;
	public static List<Info_notice> recNotices;
	public static List<Info_notice> nocNotices;
	public static List<Info_comment> comments;
	public static String[] joinInfos;
	
	public Info() {
		// TODO Auto-generated constructor stub

	} 
	
	public static void sortNotices()
	{
		Collections.sort(notices);
	}
	
	public static void sortNocNotices()
	{
		Collections.sort(nocNotices);
	}
	
	public static void sortRecNotices()
	{
		Collections.sort(recNotices);
	}
	
	public static int stateConvert(int state)
	{
		return (state+2)%4;
	}
	
	public static ResultSet getRs(Connection con) throws SQLException
	{
		String sql = "select * from _apk u";
		System.out.println(sql);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
}
