package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_user;
import hustphyt.info.Info_userGroups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB_inviteJoinGroup extends DBAb{

	final public static String[] str_results=new String[]{"邀请加入群组失败","邀请加入群组成功！"};

	public DB_inviteJoinGroup(Info_groupUsers groupUsers)
	{
		this.group=new Info_group(groupUsers.id);
		this.users=groupUsers.users;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Info_user> users2=new ArrayList<Info_user>();
		for (Info_user user : users) {
			rs=user.getRsFromPhoneNumber(con);
			if (rs.next())
			{
				users2.add(user);
				user.id=rs.getInt(Info.str_id);
				user.groupId=group.id;
				DBAb db=new DB_joinGroup(user);
				try {
					int flag=db.operate();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		for (Info_user user:users2)
			users.remove(user);
		return 1;
	}
}
