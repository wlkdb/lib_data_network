package hustphyt.db;

import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_icon extends DBAb{
	final public static String[] str_results=new String[]{"��Ⱥ�鲻����","���ҳɹ�","Ⱥ��Ϊ��"};
	
	public DB_icon(Info_group group) {
		// TODO Auto-generated constructor stub
		this.group=group;
	}

	public DB_icon(Info_notice notice) {
		// TODO Auto-generated constructor stub
		this.notice=notice;
	}
	
	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		if (group!=null)
		{
			ResultSet rs=group.getRsFromId(con);
			if (!rs.next())
				return 0;
			group.icon=rs.getBytes("icon");
		} else if (notice!=null)
		{
			ResultSet rs=notice.getRsFromId(con);
			if (!rs.next())
				return 0;
			notice.icon=rs.getBytes("icon");
		} else
			return 2;
		return 1;
	}

}
