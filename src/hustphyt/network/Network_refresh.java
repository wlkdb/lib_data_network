package hustphyt.network;

import hustphyt.db.DB_login;
import hustphyt.db.DB_refresh;
import hustphyt.db.DB_register;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Network_refresh extends NetworkAb{

	public Info_user user;
	public static String key="refresh";

	public Network_refresh(Info_user user)
	{
		this.user=user;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_refresh.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return Network_refresh.key;
	}

	@Override
	public void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+user.getFormatString(3);
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		if (flag!=1)
			return;
		
		String str=input.readUTF();
		String[] strArray = str.split(Info.sep1);
		Info.user=new Info_user(4,strArray);

		str=input.readUTF();
		strArray = str.split(Info.sep2);
		Info.groups=new ArrayList<Info_group>();
		if (!str.equals(""))
			for (int i=0;i<strArray.length;i++)
			{
				String[] strArray2= strArray[i].split(Info.sep1);
				Info_group group=new Info_group(1, strArray2);
				Info.groups.add(group);
			}
		
		str=input.readUTF();
		Info.notices=new ArrayList<Info_notice>();
		while (!str.equals(Info.str_endWrite))
		{
			strArray = str.split(Info.sep2);
			for (int i=0;i<strArray.length;i++)
			{
				String[] strArray2= strArray[i].split(Info.sep1);
				Info_notice notice=new Info_notice(1, strArray2);
				if (notice.time>=new Date().getTime())
					Info.notices.add(notice);
			}
			str=input.readUTF();
		}
	}

	@Override
	public void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub

		db=new DB_refresh(user);
		flag=db.operate();
		output.writeInt(flag);
		output.flush();
		
		output.writeUTF(db.user.getFormatString(4));
		output.flush();
		
		StringBuffer message=new StringBuffer();
		for (int i=0;i<db.groups.size();i++)
			message.append(db.groups.get(i).getFormatString(1));
		output.writeUTF(message.toString());
		output.flush();

		int index=0;
		while (index<db.notices.size())
		{
			message=new StringBuffer();
			while ((message.toString().getBytes("utf-8").length<1450)&&(index<db.notices.size()))
			{
				message.append(db.notices.get(index).getFormatString(1));
				index++;
			}
			System.out.println("发送活动信息 : "+index+" "+message);
			output.writeUTF(message.toString());
			output.flush();
		}
		output.writeUTF(Info.str_endWrite);
		output.flush();
	}
}
