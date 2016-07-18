package hustphyt.network;

import hustphyt.db.DB_noticeComments;
import hustphyt.info.Info;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_noticeComments extends NetworkAb{
	public Info_notice notice;
	public Info_user user;
	public static String key="noticeComments";
	
	public Network_noticeComments(Info_notice notice)
	{
		this.notice=notice;
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_noticeComments.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+notice.id;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		
		if (flag==0)
			return;
		
		Info.comments=new ArrayList<Info_comment>();
		message=input.readUTF();
		String[] strArr=message.split(Info.sep2);
		if (strArr[0].equals(""))
			return;
		for (String string : strArr) {
			String[] strArr2=string.split(Info.sep1);
			Info_comment comment=new Info_comment(1, strArr2);
			Info.comments.add(comment);
		}
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		DB_noticeComments db=new DB_noticeComments(notice);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
		
		StringBuffer message=new StringBuffer();
		for (Info_comment comment : Info.comments) {
			message.append(comment.getFormatString(1));
		}
		System.out.println(message.toString());
		output.writeUTF(message.toString());
	}

}
