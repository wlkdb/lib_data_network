package hustphyt.network;

import hustphyt.db.DB_noticeComments;
import hustphyt.db.DB_noticePhoto;
import hustphyt.info.Info;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_photo;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_noticePhoto extends NetworkAb{
	public static String key="noticePhoto";
	public Info_photo photo;
	
	public Network_noticePhoto(Info_photo photo)
	{
		this.photo=photo;
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_noticePhoto.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+photo.noticeId+Info.sep1+photo.index;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		
		if (flag==0)
			return;
		
		message=input.readUTF();
		String[] strArr=message.split(Info.sep1);
		
		Info.photo=new Info_photo(1, strArr);
		Info.photo.photo=reciveBytes();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		DB_noticePhoto db=new DB_noticePhoto(photo);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
		
		output.writeUTF(db.photo.getFormatString(1));
		sendBytes(db.photo.photo);
	}

}
