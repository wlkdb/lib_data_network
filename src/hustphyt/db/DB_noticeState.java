package hustphyt.db;

import hustphyt.info.Info_noticeUsers;

import java.sql.SQLException;

public class DB_noticeState extends DBAb{
	public Info_noticeUsers noticeUsers;
	final public static String[] str_results=new String[]{"���±���״̬ʧ��","���±���״̬�ɹ���"};

	public DB_noticeState(Info_noticeUsers noticeUsers)
	{
		this.noticeUsers=noticeUsers;
	}
	
	@Override
	protected int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		preStmt=noticeUsers.getReplaceStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		return 1;
	}

}
