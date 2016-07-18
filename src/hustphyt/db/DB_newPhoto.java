package hustphyt.db;

import hustphyt.info.Info_photo;

import java.sql.SQLException;

public class DB_newPhoto extends DBAb{
	public Info_photo photo;
	final public static String[] str_results=new String[]{"添加记录失败","添加记录成功！"};

	public DB_newPhoto(Info_photo photo)
	{
		this.photo=photo;
	}
	
	@Override
	protected int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		preStmt=photo.getInsertStatement(con);
		if (preStmt.executeUpdate()==0)
			return 0;
		
		return 1;
	}

}
