package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_comment;
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

public class DB_noticeComments extends DBAb{
	public int userNum[];
	final public static String[] str_results=new String[]{"获取评论信息失败","获取评论信息成功！"};

	public DB_noticeComments(Info_notice notice)
	{
		this.notice=notice;
	}
	
	@Override
	protected int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Info_comment comment=new Info_comment();
		comment.noticeId=notice.id;
		rs=comment.getRsAll(con);
		Info.comments=new ArrayList<Info_comment>();
		while (rs.next())
		{
			Info_comment comment2=new Info_comment(rs);
			Info_user user=new Info_user(comment2.userId);
			ResultSet rs2=user.getRsFromId(con);
			if (!rs2.next())
				return 0;
			comment2.userName=rs2.getString(Info.str_name);
			Info.comments.add(comment2);
		}
		
		return 1;
	}

}
