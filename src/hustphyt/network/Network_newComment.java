package hustphyt.network;

import hustphyt.db.DB_newComment;
import hustphyt.db.DB_newNotice;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_comment;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_newComment extends NetworkAb{
	public Info_comment comment;
	public static String key="newComment";

	public Network_newComment(Info_comment comment)
	{
		this.comment=comment;
	}
	
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_newComment.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		String message=key+Info.sep1+comment.getFormatString(0);
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_newComment(comment);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

}
