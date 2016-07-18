package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_user;
import hustphyt.info.Info_userGroups;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_register extends DBAb{

	final public static String[] str_results=new String[]{"注册失败","注册成功！","此号码已经被注册"};

	public DB_register(Info_user user)
	{
		this.user=user;
	}
	
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		int i=0,id=0;  
		
		rs=user.getRsFromPhoneNumber(con);
		if (rs.next())
			return 2;	//此电话号码已经注册了
		
    	preStmt=user.getInsertStatement(con);
		if (preStmt.executeUpdate()==0) 
			return 0;	//用户数据插入数据库失败
		
		rs=user.getRsFromPhoneNumber(con);
		if (!rs.next())
			return 0;	//用户数据插入数据库失败

		Info_userGroups ug=new Info_userGroups(rs.getInt("id"));

		preStmt=ug.getCreatStatement(con); 
		
	    preStmt.executeUpdate();
	    
	    return 1;
	}

}
