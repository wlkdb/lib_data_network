package hustphyt.network;

import hustphyt.info.Info_comment;
import hustphyt.info.Info_group;
import hustphyt.info.Info_groupUsers;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_photo;
import hustphyt.info.Info_user;

import java.io.IOException;

public class Network_Factory extends NetworkAb{

	public void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		NetworkAb network = null;
		String key=strArray[0];
		if (key.equals(Network_register.key))
			network=new Network_register(new Info_user(1, strArray));
		else if (key.equals(Network_login.key))
			network=new Network_login(new Info_user(2, strArray));
		else if (key.equals(Network_refresh.key))
			network=new Network_refresh(new Info_user(3, strArray));
		else if (key.equals(Network_icons.key))
			network=new Network_icons();
		else if (key.equals(Network_groupRecord.key))
			network=new Network_groupRecord(new Info_user(3,strArray), new Info_group(3, strArray));
		else if (key.equals(Network_groupUsers.key))
			network=new Network_groupUsers(new Info_group(4,strArray));
		else if (key.equals(Network_joinGroup.key))
			network=new Network_joinGroup(new Info_user(5, strArray));
		else if (key.equals(Network_joinInfo.key))
			network=new Network_joinInfo(new Info_notice(4,strArray), new Info_user(3,strArray));
		else if (key.equals(Network_newGroup.key))
			network=new Network_newGroup(new Info_group(2,strArray));
		else if (key.equals(Network_newNotice.key))
			network=new Network_newNotice(new Info_notice(2,strArray));
		else if (key.equals(Network_nocheckNotice.key))
			network=new Network_nocheckNotice(new Info_user(3,strArray));
		else if (key.equals(Network_noticeState.key))
			network=new Network_noticeState(new Info_noticeUsers(1,strArray));
		else if (key.equals(Network_inviteJoinGroup.key))
			network=new Network_inviteJoinGroup(new Info_groupUsers(strArray));
		else if (key.equals(Network_updateJoinInfo.key))
			network=new Network_updateJoinInfo(new Info_noticeUsers(strArray));
		else if (key.equals(Network_groupCount.key))
			network=new Network_groupCount(new Info_group(4,strArray),Integer.parseInt(strArray[2]));
		else if (key.equals(Network_checkApk.key))
			network=new Network_checkApk();
		else if (key.equals(Network_feedback.key))
			network=new Network_feedback(new Info_user(9, strArray));
		else if (key.equals(Network_groupPermission.key))
			network=new Network_groupPermission(new Info_user(10,strArray));
		else if (key.equals(Network_newComment.key))
			network=new Network_newComment(new Info_comment(0, strArray));
		else if (key.equals(Network_noticeComments.key))
			network=new Network_noticeComments(new Info_notice(Integer.parseInt(strArray[1])));
		else if (key.equals(Network_newPhoto.key))
			network=new Network_newPhoto(new Info_photo(0,strArray));
		else if (key.equals(Network_noticePhoto.key))
			network=new Network_noticePhoto(new Info_photo(Integer.parseInt(strArray[1]),Integer.parseInt(strArray[2])));
		else if (key.equals(Network_quitGroup.key))
			network=new Network_quitGroup(new Info_user(5, strArray));
		
		if (network==null)
			return;
		
		network.strArray=strArray;
		network.input=input;
		network.output=output;
		network.run_serverToClient();
		flag=network.flag;
		str_results=network.str_results;
	}

	@Override
	public String[] setStr_results() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return "factory";
	}
	
}
