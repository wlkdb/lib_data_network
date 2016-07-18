package hustphyt.db;

import hustphyt.info.Info_user;

import java.sql.SQLException;

public class DB_feedback extends DBAb{
	final public static String[] str_results=new String[]{"反馈失败","反馈成功！"};

	public DB_feedback(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		preStmt=user.getFeedbackInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
	    return 1;
	}
}
