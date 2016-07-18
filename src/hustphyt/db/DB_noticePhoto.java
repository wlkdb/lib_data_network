package hustphyt.db;

import hustphyt.info.Info;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_photo;
import hustphyt.info.Info_user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DB_noticePhoto extends DBAb{
	public int userNum[];
	public Info_photo photo;
	final public static String[] str_results=new String[]{"获取记录失败","获取记录成功！"};

	public DB_noticePhoto(Info_photo photo)
	{
		this.photo=photo;
	}
	
	@Override
	protected int operateAb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		int noticeId=photo.noticeId;
		rs=photo.getRsFromIndex(con);
		if (!rs.next())
			return 0;
		photo=new Info_photo(rs);
		photo.noticeId=noticeId;
		
		rs=photo.getCountRsAll(con);
		if (!rs.next())
			return 0;
		photo.counts=rs.getInt(1);
		
		user=new Info_user(photo.userId);
		rs=user.getRsFromId(con);
		if (!rs.next())
			return 0;
		photo.userName=rs.getString(Info.str_name);
		
		return 1;
	}

}
