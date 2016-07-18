package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_groupNotices;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;
import hustphyt.info.Info_userGroups;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_groupCount extends DBAb{

	final public static String[] str_results=new String[]{"获取群组考勤失败","获取群组考勤成功"};
	int days;

	public DB_groupCount(Info_group group,int days)
	{
		this.group=group;
		this.days=days;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		long nowTime=System.currentTimeMillis();
		long millisecond=((long)days)*24*60*60000;
		
		Info_groupUsers groupUsers = new Info_groupUsers(group.id);
		rs=groupUsers.getRsAll(con);
		users=new ArrayList<Info_user>();
		while (rs.next())
		{
			Info_user user=new Info_user(rs.getInt(Info.str_userId));
			user.stateCount=new int[4];
			ResultSet rs2=user.getRsFromId(con);
			if (rs2.next())
			{
				user.name=rs2.getString(Info.str_name);
				users.add(user);
			}
		}
		
		Info_groupNotices groupNotices = new Info_groupNotices(group.id);
		groupNotices.state=Info_groupNotices.getState(1);
		rs=groupNotices.getRsFromState(con);
		while (rs.next())
		{
			notice=new Info_notice(rs.getInt(Info.str_noticeId));
			ResultSet rs2=notice.getRsFromId(con);
			rs2.next();
			notice.getInfoFromRs(rs2);
			System.out.println("millisecond = "+millisecond);
			if (days>0 && nowTime-notice.time>millisecond)
				continue;
			if (notice.type==1)
			{
				Info_noticeUsers noticeUsers=new Info_noticeUsers(notice.id);
				for (Info_user user : users) {
					noticeUsers.userId=user.id;
					ResultSet rs3=noticeUsers.getRsFromUserId(con);
					if (rs3.next())
						user.stateCount[Info_noticeUsers.getState((rs3.getString(Info.str_state)))]++;
					else 
						user.stateCount[2]++;
				}
			}
		}
		
	    return 1;
	}
	
}
