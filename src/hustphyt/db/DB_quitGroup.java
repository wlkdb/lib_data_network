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

public class DB_quitGroup extends DBAb{

	final public static String[] str_results=new String[]{"退出群组失败","退出群组成功！"};

	public DB_quitGroup(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Info_groupUsers groupUsers=new Info_groupUsers(user.groupId, user.id);
		preStmt=groupUsers.getDeleteStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		Info_userGroups userGroups=new Info_userGroups(user.id,user.groupId);
		preStmt=userGroups.getDeleteStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		return 1;
	}
	
}
