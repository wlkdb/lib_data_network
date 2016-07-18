package hustphyt.network;

import hustphyt.db.DB_newGroup;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;

import java.io.IOException;

public class Network_newGroup extends NetworkAb{
	public Info_group group;
	public static String key="newGroup";

	public Network_newGroup(Info_group group)
	{
		this.group=group;
	}
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_newGroup.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub

		String message=key+Info.sep1+group.getFormatString(2);
		output.writeUTF(message);
		output.flush();
		if (group.iconId==-1)
			sendBytes(group.icon);
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub

		if (group.iconId==-1)
			group.icon=reciveBytes();
		
		db=new DB_newGroup(group);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

}
