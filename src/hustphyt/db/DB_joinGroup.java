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

public class DB_joinGroup extends DBAb{

	final public static String[] str_results=new String[]{"加入群组失败","加入群组成功！"};

	public DB_joinGroup(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		if (user.groupId<0)
		{
			group=new Info_group();
			group.name=user.groupName;
			rs=group.getRsFromName(con);
			if (!rs.next())
				return 0;
			user.groupId=rs.getInt(Info.str_id);
		}
		
		Info_groupUsers groupUsers=new Info_groupUsers(user.groupId, user.id);
		preStmt=groupUsers.getInsertsStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		Info_userGroups userGroups=new Info_userGroups(user.id,user.groupId);
		preStmt=userGroups.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		return 1;
	}
	
}
