package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DB_joinInfo extends DBAb{
	public int userNum[];
	final public static String[] str_results=new String[]{"获取报名信息失败","获取报名信息成功！"};

	public DB_joinInfo(Info_notice notice, Info_user user)
	{
		this.notice=notice;
		this.user=user;
	}
	
	@Override
	protected int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		users=new ArrayList<Info_user>();
		
		int index=0;
		userNum=new int[]{0,0,0};
		Set<Integer> set=new HashSet<Integer>(); 
		
		ResultSet rs,rs2;
		
		rs=notice.getRsFromId(con);
		if (!rs.next())
			return 0;
		notice.getInfoFromRs(rs);

		Info_noticeUsers noticeUsers2=new Info_noticeUsers(notice.id);
		Info_user user2;
		if ((notice.type==0)||(notice.type==2)||(notice.type>9))
		{
			noticeUsers2.state=Info_noticeUsers.getState(1);
			rs=noticeUsers2.getRsFromState(con);
			while (rs.next())
			{
				userNum[index]++;
				user2=new Info_user(rs.getInt(Info.str_userId));
				rs2=user2.getRsFromId(con);
				rs2.next();
				user2.name=rs2.getString(Info.str_name);
				users.add(user2);
				set.add(rs.getInt(Info.str_userId));
			}
			index++;
		}
		if ((notice.type==1)||(notice.type==2)||(notice.type>9))
		{
			noticeUsers2.state=Info_noticeUsers.getState(2);
			rs=noticeUsers2.getRsFromState(con);
			while (rs.next())
			{
				userNum[index]++;
				user2=new Info_user(rs.getInt(Info.str_userId));
				rs2=user2.getRsFromId(con);
				rs2.next();
				user2.name=rs2.getString(Info.str_name);
				users.add(user2);
				set.add(rs.getInt(Info.str_userId));
			}
			index++;
		}
		if (notice.type==1)
		{
			noticeUsers2.state=Info_noticeUsers.getState(3);
			rs=noticeUsers2.getRsFromState(con);
			while (rs.next())
			{
				userNum[index]++;
				user2=new Info_user(rs.getInt(Info.str_userId));
				rs2=user2.getRsFromId(con);
				rs2.next();
				user2.name=rs2.getString(Info.str_name);
				user2.refuseInfo=rs.getString(Info.str_refuseInfo);
				users.add(user2);
				set.add(rs.getInt(Info.str_userId));
			}
			index++;
		}
		Info_groupUsers groupUsers=new Info_groupUsers(notice.groupId);
		rs=groupUsers.getRsAll(con);
		while (rs.next())
		{
			if (rs.getInt(Info.str_userId)==notice.creator)
				continue;
			if (!set.contains(rs.getInt(Info.str_userId)))
			{
				userNum[index]++;
				user2=new Info_user(rs.getInt(Info.str_userId));
				rs2=user2.getRsFromId(con);
				rs2.next();
				user2.name=rs2.getString(Info.str_name);
				users.add(user2);
			}
		}	
		return 1;
	}
}
