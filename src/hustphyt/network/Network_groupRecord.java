package hustphyt.network;

import hustphyt.db.DB_groupRecord;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_groupRecord extends NetworkAb{
	public Info_user user;
	public Info_group group;
	public static String key="groupRecord";

	public Network_groupRecord(Info_user user, Info_group group)
	{
		this.user=user;
		this.group=group;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_groupRecord.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub

		String message=key+Info.sep1+user.id+Info.sep1+group.id+Info.sep1+group.name;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		
		String str=input.readUTF();
		Info.recNotices=new ArrayList<Info_notice>();
		
		if (!str.equals(""))
		{
			String[] strArray=str.split(Info.sep2);
			for (String string:strArray)
			{
				String[] strArray2=string.split(Info.sep1);
				Info_notice notice=new Info_notice(1,strArray2);
				Info.recNotices.add(notice);
			}
		}
		
		//Info.sortRecNotices();

	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_groupRecord(user, group);
		flag=db.operate();
		output.writeInt(flag);
		output.flush();
		
		StringBuffer message=new StringBuffer();
		for (Info_notice notice:db.notices)
		{
			System.out.println(notice.getFormatString(1));
			message.append(notice.getFormatString(1));
		}
		output.writeUTF(message.toString());
		output.flush();
		
	}

}
