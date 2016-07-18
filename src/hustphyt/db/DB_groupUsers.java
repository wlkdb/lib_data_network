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

public class DB_groupUsers extends DBAb{

	final public static String[] str_results=new String[]{"��ȡȺ���û�ʧ��","��ȡȺ���û��ɹ�"};

	public DB_groupUsers(Info_group group)
	{
		this.group=group;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		users=new ArrayList<Info_user>();
		
		Info_groupUsers groupUsers=new Info_groupUsers(group.id);
		ResultSet rs=groupUsers.getRsAll(con);
		while (rs.next())
		{
			user=new Info_user(rs.getInt(Info.str_userId));
			user.permission=rs.getString(Info.str_permission);
			
			ResultSet rs2=user.getRsFromId(con);
			rs2.next();
			user.getInfoFromRs(rs2);
			users.add(user);
		}
		
	    return 1;
	}
	
}
