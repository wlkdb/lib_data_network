package hustphyt.network;

import hustphyt.db.DB_feedback;
import hustphyt.db.DB_newNotice;
import hustphyt.db.DB_noticeState;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;

import java.io.IOException;

public class Network_feedback extends NetworkAb{
	public Info_user user;
	public static String key="feedback";

	public Network_feedback(Info_user user)
	{
		this.user=user;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_feedback.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+user.getFormatString(9);
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_feedback(user);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

}
