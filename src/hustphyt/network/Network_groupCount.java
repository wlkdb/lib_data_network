package hustphyt.network;

import hustphyt.db.DB_groupCount;
import hustphyt.db.DB_groupUsers;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_groupCount extends NetworkAb{
	public Info_group group;
	public static String key="groupCount";
	public int days;

	public Network_groupCount(Info_group group,int days)
	{
		this.group=group;
		this.days=days;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_groupCount.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub

		String message=key+Info.sep1+group.id+Info.sep1+days;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		
		String str=input.readUTF();
		String[] strArray=str.split(Info.sep2);
		Info.users=new ArrayList<Info_user>();
		for (String string:strArray)
		{
			String[] strArray2=string.split(Info.sep1);
			Info_user user=new Info_user(7,strArray2);
			Info.users.add(user);
		}
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_groupCount(group, days);
		flag=db.operate();
		output.writeInt(flag);
		output.flush();
		
		StringBuffer message=new StringBuffer();
		for (Info_user user : db.users) 
			message.append(user.getFormatString(7));
		output.writeUTF(message.toString());
		output.flush();
	}

}
