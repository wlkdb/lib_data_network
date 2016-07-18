package hustphyt.network;

import hustphyt.db.DB_checkApk;
import hustphyt.db.DB_checkTime;
import hustphyt.db.DB_login;
import hustphyt.db.DB_register;
import hustphyt.info.Info;
import hustphyt.info.Info_user;

import java.io.IOException;

public class Network_checkApk extends NetworkAb{
	public static String key="checkApk";
	public String url;
	public int version;
	
	public Network_checkApk()
	{
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_login.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return Network_checkApk.key;
	}

	@Override
	public void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		if (flag==0)
			return;
		url=input.readUTF();
		version=input.readInt();
	}

	@Override
	public void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_checkApk();
		flag=db.operate();
		output.writeInt(flag);
		output.flush();
		if (flag==0)
			return;
		output.writeUTF(((DB_checkApk)db).url);
		output.flush();
		output.writeInt(((DB_checkApk)db).version);
		output.flush();
	}

}
