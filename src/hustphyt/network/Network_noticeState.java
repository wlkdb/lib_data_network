package hustphyt.network;

import hustphyt.db.DB_newNotice;
import hustphyt.db.DB_noticeState;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;

import java.io.IOException;

public class Network_noticeState extends NetworkAb{
	public Info_noticeUsers noticeUsers;
	public static String key="noticeState";

	public Network_noticeState(Info_noticeUsers noticeUsers)
	{
		this.noticeUsers=noticeUsers;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_noticeState.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+noticeUsers.getFormatString(1);
		output.writeUTF(message);
		output.flush();
		
		if (noticeUsers.state.equals(Info_noticeUsers.getState(3)))
		{
			output.writeUTF(noticeUsers.refuseInfo);
			output.flush();
		}
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		if (noticeUsers.state.equals(Info_noticeUsers.getState(3)))
			noticeUsers.refuseInfo=input.readUTF()+" ";
		
		db=new DB_noticeState(noticeUsers);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

}
