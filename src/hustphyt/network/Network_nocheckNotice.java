package hustphyt.network;

import hustphyt.db.DB_nocheckNotice;
import hustphyt.db.DB_refresh;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_nocheckNotice extends NetworkAb{
	public Info_user user;
	public static String key="nocheckNotice";
	
	public Network_nocheckNotice(Info_user user)
	{
		this.user=user;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_nocheckNotice.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub

		String message=key+Info.sep1+user.id;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		Info.nocNotices=new ArrayList<Info_notice>();
		
		String str=input.readUTF();
		while (!str.equals(Info.str_endWrite))
		{
			strArray = str.split(Info.sep2);
			for (int i=0;i<strArray.length;i++)
			{
				String[] strArray2= strArray[i].split(Info.sep1);
				Info_notice notice=new Info_notice(3, strArray2);
				Info.nocNotices.add(notice);
			}
			str=input.readUTF();
		}
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub

		db=new DB_nocheckNotice(user);
		flag=db.operate();
		output.writeInt(flag);
		output.flush();
		
		int index=0;
		while (index<db.notices.size())
		{
			StringBuffer message=new StringBuffer();
			while ((message.toString().getBytes("utf-8").length<1450)&&(index<db.notices.size()))
			{
				message.append(db.notices.get(index).getFormatString(3));
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
