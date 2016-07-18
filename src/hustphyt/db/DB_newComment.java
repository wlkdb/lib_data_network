package hustphyt.db;

import hustphyt.info.Info_comment;
import hustphyt.info.Info_noticeUsers;

import java.sql.SQLException;

public class DB_newComment extends DBAb{
	public Info_comment comment;
	final public static String[] str_results=new String[]{"添加评论失败","添加评论成功！"};

	public DB_newComment(Info_comment comment)
	{
		this.comment=comment;
	}
	
	@Override
	protected int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		preStmt=comment.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		return 1;
	}

}
