package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_group;
import hustphyt.info.Info_groupNotices;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_photo;
import hustphyt.info.Info_user;
import hustphyt.info.Info_userGroups;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_refresh extends DBAb{

	final public static String[] str_results=new String[]{"此用户ID不存在","刷新成功！","读取数据库错误"};

	public DB_refresh(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		groups=new ArrayList<Info_group>();
		notices=new ArrayList<Info_notice>();
		
		rs=user.getRsFromId(con);
		if (!rs.next())
			return 0;	//此用户ID不存在
		
		user.phoneNumber=rs.getString(Info.str_phoneNumber);
		user.password=rs.getString(Info.str_password);
		user.name=rs.getString(Info.str_name);
		user.details=rs.getString(Info.str_details);
		
		Info_userGroups userGroups=new Info_userGroups(user.id);
		rs=userGroups.getRsAll(con);
		
		while (rs.next())
		{
			Info_group group=new Info_group(rs.getInt(Info.str_groupId));
			ResultSet rs2=group.getRsFromId(con);
			if (!rs2.next()) return 2;
			group.getInfoFromRs(rs2);
			
			Info_user user2=new Info_user(group.creator);
			rs2=user2.getRsFromId(con);
			if (!rs2.next()) return 2;
			group.creatorName=rs2.getString(Info.str_name);
			
			Info_groupUsers groupUsers=new Info_groupUsers(group.id);
			groupUsers.userId=user.id;
			rs2=groupUsers.getRsFromUserId(con);
			if (!rs2.next()) return 2;
			group.permission=rs2.getString(Info.str_permission);
			
			int groupNum=0;
			rs2 = groupUsers.getCountRsAll(con);
			if (rs2.next())
				groupNum=rs2.getInt(1)-1;
			
			Info_groupNotices groupNotices=new Info_groupNotices(group.id);
			groupNotices.state=Info_groupNotices.getState(0);	//"active"
			rs2=groupNotices.getRsFromState(con);
			
			while (rs2.next())
			{
				Info_notice notice=new Info_notice(rs2.getInt(Info.str_noticeId));
				ResultSet rs3=notice.getRsFromId(con);
				if (!rs3.next()) return 2;
				notice.getInfoFromRs(rs3);
				notice.groupName=group.name;
				
				Info_user user_Creator=new Info_user(notice.creator);
				rs3=user_Creator.getRsFromId(con);
				if (!rs3.next()) return 2;
				notice.creatorName=rs3.getString(Info.str_name);
				
				Info_comment comment=new Info_comment();
				comment.noticeId=notice.id;
				try {
					rs3=comment.getCountRsAll(con);
					if (rs3.next())
						notice.commentsNum=rs3.getInt(1);
				} catch (Exception e) {
					// TODO: handle exception
					notice.commentsNum=0;
				}
				
				Info_photo photo=new Info_photo(notice.id, 0);
				try {
					rs3=photo.getCountRsAll(con);
					if (rs3.next())
						notice.photosNum=rs3.getInt(1);
				} catch (Exception e) {
					// TODO: handle exception
					notice.photosNum=0;
				}
				
				Info_noticeUsers noticeUsers=new Info_noticeUsers(notice.id);
				noticeUsers.userId=user.id;
				rs3=noticeUsers.getRsFromUserId(con);
				if (rs3.next())
					notice.userState=rs3.getString(Info.str_state);
				else 
					notice.userState=Info_noticeUsers.getState(0);	//"nocheck"
				
				if ((notice.creator==user.id)||(notice.type==2)||notice.type>=10)
				{
					if ((notice.type==0)||(notice.type==2)||notice.type>=10)
					{
						noticeUsers.state=Info_noticeUsers.getState(1);	//"checked"
						rs3 = noticeUsers.getCountRsFromState(con);
						if (rs3.next())
							notice.checkedNum=rs3.getInt(1);
					}
					if ((notice.type==1)||(notice.type==2)||notice.type>=10)
					{
						noticeUsers.state=Info_noticeUsers.getState(2);	//"join"
						rs3 = noticeUsers.getCountRsFromState(con);
						if (rs3.next())
							notice.joinNum=rs3.getInt(1);
					}
					if (notice.type==1)
					{
						noticeUsers.state=Info_noticeUsers.getState(3);	//"refuse"
						rs3 = noticeUsers.getCountRsFromState(con);
						if (rs3.next())
							notice.refuseNum=rs3.getInt(1);
					}
					if (notice.type==0)
						notice.nocheckNum=groupNum-notice.checkedNum;
					else if (notice.type==1)
						notice.nocheckNum=groupNum-notice.joinNum-notice.refuseNum;
					else if (notice.type==2||notice.type>=10)
						notice.nocheckNum=groupNum-notice.joinNum-notice.checkedNum;
				}
				if (notice.type==2)
				{
					noticeUsers.state=Info_noticeUsers.getState(2);	//"join"
					rs3 = noticeUsers.getRsFromState(con);
					StringBuffer joinNames=new StringBuffer();
					int index=0;
					while (rs3.next())
					{
						if (index>0)
							joinNames.append("，");
						index++;
						Info_user user_join=new Info_user(rs3.getInt(Info.str_userId));
						ResultSet rs4 = user_join.getRsFromId(con);
						if (rs4.next())
							joinNames.append(rs4.getString(Info.str_name));
					}
					notice.joinNames=joinNames.toString();
				}
				notices.add(notice);
			}
			groups.add(group);
		}
	    return 1;
	}
}
