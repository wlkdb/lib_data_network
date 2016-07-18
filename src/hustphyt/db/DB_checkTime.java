package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_groupNotices;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;
import hustphyt.info.Info_userGroups;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_checkTime extends DBAb{

	final public static String[] str_results=new String[]{"检查成功","检查失败"};

	public DB_checkTime()
	{
	}
	
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		long nowTime=System.currentTimeMillis();
		
		group=new Info_group();
		rs=Info_group.getRsAll(con);
		while (rs.next())
		{
			Info_groupNotices groupNotices=new Info_groupNotices(rs.getInt(Info.str_id));
			groupNotices.state=Info_groupNotices.getState(0);	//active
			ResultSet rs2=groupNotices.getRsFromState(con);
			while (rs2.next())
			{
				Info_notice notice=new Info_notice(rs2.getInt(Info.str_noticeId));
				ResultSet rs3=notice.getRsFromId(con);
				if (rs3.next())
					if (rs3.getLong(Info.str_time)<nowTime)
					{
						groupNotices.noticeId=notice.id;
						preStmt=groupNotices.getUpdateStatement(con);
						preStmt.executeUpdate();
					}
			}
		}
	    
	    return 1;
	}

}
