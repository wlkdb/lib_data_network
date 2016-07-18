package hustphyt.network;

import hustphyt.db.DB_newNotice;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_newNotice extends NetworkAb{
	public Info_notice notice;
	public static String key="newNotice";

	public Network_newNotice(Info_notice notice)
	{
		this.notice=notice;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_newNotice.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+notice.getFormatString(2);
		output.writeUTF(message);
		output.flush();
		
		if (notice.icon!=null)
		{
			output.writeInt(1);
			output.flush();
			sendBytes(notice.icon);
		}
		else
		{
			output.writeInt(-1);
			output.flush();
		}
		
		flag=input.readInt();
		Info.notice=new Info_notice();
		Info.notice.id=input.readInt();
		String str=input.readUTF();
		Info.users=new ArrayList<Info_user>();
		if (str.equals(""))
			return;
		String[] strArr=str.split(Info.sep2);
		for (String string : strArr) {
			String[] strArr2=string.split(Info.sep1);
			Info_user user=new Info_user(8, strArr2);
			Info.users.add(user);
		}
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		int t=input.readInt();
		if (t==1)
			notice.icon=reciveBytes();
		
		db=new DB_newNotice(notice);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
		System.out.println("flag="+flag);
		
		output.writeInt(notice.id);
		output.flush();
		System.out.println("noticeId="+notice.id);
		
		StringBuffer message=new StringBuffer();
		for (Info_user user : db.users) {
			message.append(user.getFormatString(8));
		}
		output.writeUTF(message.toString());
		output.flush();
	}

}
