package hustphyt.network;

import hustphyt.db.DB_newComment;
import hustphyt.db.DB_newNotice;
import hustphyt.db.DB_newPhoto;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_photo;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_newPhoto extends NetworkAb{
	public Info_photo photo;
	public static String key="newPhoto";

	public Network_newPhoto(Info_photo photo)
	{
		this.photo=photo;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_newPhoto.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+photo.getFormatString(0);
		output.writeUTF(message);
		output.flush();
		
		sendBytes(photo.photo);
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		photo.photo=reciveBytes();
		
		db=new DB_newPhoto(photo);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

}
