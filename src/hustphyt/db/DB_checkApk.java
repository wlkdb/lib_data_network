package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_user;

import java.sql.SQLException;

public class DB_checkApk extends DBAb{
	final public static String[] str_results=new String[]{"����ʧ��","���ҳɹ���"};
	public String url;
	public int version;
	
	public DB_checkApk( )
	{
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		rs=Info.getRs(con);
		if (!rs.next())
			return 0;	//�˵绰���벻����
		
		version=rs.getInt("version");
		url=rs.getString("url");
		
	    return 1;
	}
	
}
