package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_groupNotices;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_photo;
import hustphyt.info.Info_user;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_newNotice extends DBAb{
	final public static String[] str_results=new String[]{"创建notice失败","创建notice成功！"};
	
	public DB_newNotice(Info_notice notice)
	{
		this.notice=notice;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		preStmt=notice.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		rs=notice.getRsFromTime(con);
		
		if (!rs.last())
			return 0;
		
		notice.id=rs.getInt(Info.str_id);
		
		Info_noticeUsers noticeUsers=new Info_noticeUsers(notice.id);
		preStmt=noticeUsers.getCreatStatement(con);
		preStmt.executeUpdate();
		
		noticeUsers.userId=notice.creator;
		noticeUsers.state=Info_noticeUsers.getState(4);
		preStmt=noticeUsers.getReplaceStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		Info_comment comment=new Info_comment();
		comment.noticeId=notice.id;
		preStmt=comment.getCreatStatement(con);
		preStmt.executeUpdate();
		
		Info_photo photo=new Info_photo();
		photo.noticeId=notice.id;
		preStmt=photo.getCreatStatement(con);
		preStmt.executeUpdate();
		
		Info_groupNotices groupNotices=new Info_groupNotices(notice.groupId);
		groupNotices.noticeId=notice.id;
		preStmt=groupNotices.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		Info_groupUsers groupUsers=new Info_groupUsers(notice.groupId);
		rs=groupUsers.getRsAll(con);
		users=new ArrayList<Info_user>();
		
		long nowTime=System.currentTimeMillis();
		System.out.println(nowTime);
		while (rs.next())
		{
			Info_user user=new Info_user(rs.getInt(Info.str_userId));
			ResultSet rs2=user.getRsFromId(con);
			if (rs2.next())
			{
				try {
					user.getInfoFromRs(rs2);
					System.out.println(user.lastLogin);
					if (nowTime-user.lastLogin>7*24*60*60000)
						users.add(user);
				} catch (Exception e) {
					// TODO: handle exception
					users.add(user);
				}
			}
		}
		return 1;
	}
	
}
