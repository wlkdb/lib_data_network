package hustphyt.network;

import hustphyt.db.DB_joinGroup;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;

public class Network_joinGroup extends NetworkAb{
	public Info_user user;
	public static String key="joinGroup";

	public Network_joinGroup(Info_user user)
	{
		this.user=user;
	}
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_joinGroup.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub

		String message=key+Info.sep1+user.getFormatString(5);
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_joinGroup(user);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

}
