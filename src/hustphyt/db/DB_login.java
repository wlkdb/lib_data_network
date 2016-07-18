package hustphyt.db;

import hustphyt.info.Info_user;

import java.sql.SQLException;

public class DB_login extends DBAb{
	final public static String[] str_results=new String[]{"此号码不存在","登陆成功！","密码错误"};

	public DB_login(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		rs=user.getRsFromPhoneNumber(con);
		if (!rs.next())
			return 0;	//此电话号码不存在
		user.id=rs.getInt("id");
		
		String password=rs.getString("password");
		//System.out.println(password);
		//System.out.println(user.password);
		if (!password.equals(user.password))
			return 2;	//密码错误
		
		preStmt=user.getUpdateStatement(con);
		preStmt.executeUpdate();
		
	    return 1;
	}
	
}
