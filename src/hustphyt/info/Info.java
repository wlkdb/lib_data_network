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
	
	public static String[][] joinInfoTitles={{"�Ѳ鿴�������Ա��","δ�鿴�������Ա��"},{"���������Ա��","���ٵ���Ա��","δ�鿴�����Ա��"},
											 {"�鿴�����Ա��","���������Ա��","δ�鿴�����Ա��"},{"������Ա","�����Ա","ȱ����Ա"},
											 {"�鿴�������Ա��","���������Ȥ����Ա��","δ�鿴�������Ա��"}}; 

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
