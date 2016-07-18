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

public class DB_newGroup extends DBAb{

	final public static String[] str_results=new String[]{"创建群组失败","创建群组成功","创建失败：群组名已存在！"};

	public DB_newGroup(Info_group group)
	{
		this.group=group;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		rs=group.getRsFromName(con);
		if (rs.next())
			return 2;
		
		preStmt=group.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		rs=group.getRsFromName(con);
		if (!rs.next())
			return 0;
		group.id=rs.getInt(Info.str_id);
		
		Info_userGroups userGroups=new Info_userGroups(group.creator, group.id);
		preStmt=userGroups.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		Info_groupUsers groupUsers=new Info_groupUsers(group.id);
		preStmt=groupUsers.getCreatStatement(con);
		preStmt.executeUpdate();
		
		groupUsers.userId=group.creator;
		groupUsers.permission=Info_groupUsers.getPermission(0);	//super
		preStmt=groupUsers.getInsertsStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;

		Info_groupNotices groupNotices=new Info_groupNotices(group.id);
		preStmt=groupNotices.getCreatStatement(con);
		preStmt.executeUpdate();
		
	    return 1;
	}
	
}
