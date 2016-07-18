package hustphyt.network;

import hustphyt.db.DB_inviteJoinGroup;
import hustphyt.db.DB_joinGroup;
import hustphyt.info.Info;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_inviteJoinGroup extends NetworkAb{
	public Info_user user;
	public Info_groupUsers groupUsers;
	public static String key="inviteJoinGroup";

	public Network_inviteJoinGroup(Info_groupUsers groupUsers) {
		// TODO Auto-generated constructor stub
		this.groupUsers=groupUsers;
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_inviteJoinGroup.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		StringBuffer message=new StringBuffer(key+Info.sep1+groupUsers.id);
		for (Info_user user : groupUsers.users) 
			message.append(Info.sep1+user.phoneNumber+Info.sep1+user.name);
		output.writeUTF(message.toString());
		output.flush();
		
		flag=1;
		String string=input.readUTF();
		String[] strArr=string.split(Info.sep1);
		Info.users=new ArrayList<Info_user>();
		for (int i=0;i<strArr.length;i+=2) {
			Info_user user=new Info_user(strArr[i]);
			user.name=strArr[i+1];
			Info.users.add(user);
		}
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		db=new DB_inviteJoinGroup(groupUsers);
		flag=db.operate();
		
		StringBuffer message=new StringBuffer("");
		for (Info_user user : db.users) {
			message.append(user.phoneNumber+Info.sep1+user.name+Info.sep1);
		}
		
		output.writeUTF(message.toString());
		output.flush();
	}

}
