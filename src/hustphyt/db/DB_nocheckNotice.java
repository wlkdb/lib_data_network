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

public class DB_nocheckNotice extends DBAb{

	final public static String[] str_results=new String[]{"获取未读notice失败","获取未读notice成功！"};

	public DB_nocheckNotice(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		notices=new ArrayList<Info_notice>();
		Info_userGroups userGroups=new Info_userGroups(user.id);
		rs=userGroups.getRsAll(con);
		
		while (rs.next())
		{
			Info_group group=new Info_group(rs.getInt(Info.str_groupId));
			ResultSet rs2=group.getRsFromId(con);
			if (!rs2.next()) return 2;
			group.name=rs2.getString(Info.str_name);
			
			Info_groupNotices groupNotices=new Info_groupNotices(rs.getInt("groupId"));
			groupNotices.state=Info_groupNotices.getState(0);	//"active"
			rs2=groupNotices.getRsFromState(con);
			
			while (rs2.next())
			{
				Info_notice notice=new Info_notice(rs2.getInt("noticeId"));
				ResultSet rs3=notice.getRsFromId(con);
				if (!rs3.next()) return 2;
				notice.getInfoFromRs(rs3);
				notice.groupName=group.name;
				
				Info_user user_Creator=new Info_user(notice.creator);
				rs3=user_Creator.getRsFromId(con);
				if (!rs3.next()) return 2;
				notice.creatorName=rs3.getString("name");
				
				Info_noticeUsers noticeUsers=new Info_noticeUsers(notice.id);
				noticeUsers.userId=user.id;
				rs3=noticeUsers.getRsFromUserId(con);
				if (rs3.next())
					notice.userState=rs3.getString("state");
				else 
					notice.userState=Info_noticeUsers.getState(0);	//"nocheck"
				
				if (notice.userState.equals(Info_noticeUsers.getState(0)))
					notices.add(notice);
			}
		}
	    return 1;
	}
	
}
