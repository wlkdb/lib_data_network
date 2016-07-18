package hustphyt.network;

import hustphyt.db.DB_joinInfo;
import hustphyt.db.DB_noticeState;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_updateJoinInfo extends NetworkAb{
	public Info_noticeUsers noticeUsers;
	public static String key="updateJoinInfo";
	
	public Network_updateJoinInfo(Info_noticeUsers noticeUsers)
	{
		this.noticeUsers=noticeUsers;
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_joinInfo.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		StringBuffer message=new StringBuffer(key+Info.sep1+noticeUsers.id);
		for (Info_user user : noticeUsers.users) {
			if (user.state!=user.oldState)
				message.append(Info.sep1+user.id+Info.sep1+user.state);
		}
		output.writeUTF(message.toString());
		output.flush();
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		for (Info_user user : noticeUsers.users) {
			noticeUsers.userId=user.id;
			noticeUsers.state=Info_noticeUsers.getState((user.state+2)%4);

			System.out.println(noticeUsers.state);
			DB_noticeState db=new DB_noticeState(noticeUsers);
			flag=db.operate();
		}
		output.writeInt(flag);
		output.flush();
	}

}
