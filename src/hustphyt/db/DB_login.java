package hustphyt.db;

import hustphyt.info.Info_user;

import java.sql.SQLException;

public class DB_login extends DBAb{
	final public static String[] str_results=new String[]{"�˺��벻����","��½�ɹ���","�������"};

	public DB_login(Info_user user)
	{
		this.user=user;
	}

	@Override
	public int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		rs=user.getRsFromPhoneNumber(con);
		if (!rs.next())
			return 0;	//�˵绰���벻����
		user.id=rs.getInt("id");
		
		String password=rs.getString("password");
		//System.out.println(password);
		//System.out.println(user.password);
		if (!password.equals(user.password))
			return 2;	//�������
		
		preStmt=user.getUpdateStatement(con);
		preStmt.executeUpdate();
		
	    return 1;
	}
	
}
