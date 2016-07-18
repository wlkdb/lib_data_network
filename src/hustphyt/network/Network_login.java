package hustphyt.network;

import hustphyt.db.DB_login;
import hustphyt.db.DB_register;
import hustphyt.info.Info;
import hustphyt.info.Info_user;

import java.io.IOException;

public class Network_login extends NetworkAb{
	public Info_user user;
	public static String key="login";
	
	public Network_login(Info_user user)
	{
		this.user=user;
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_login.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return Network_login.key;
	}

	@Override
	public void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		user.password=Info_user.encryptmd5(user.password);
		String message=key+Info.sep1+user.getFormatString(2);
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		
		int id=input.readInt();
		Info.user=new Info_user(id);
	}

	@Override
	public void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_login(user);
		flag=db.operate();
		output.writeInt(flag);
		output.flush();

		output.writeInt(db.user.id);
		output.flush();
	}

}
