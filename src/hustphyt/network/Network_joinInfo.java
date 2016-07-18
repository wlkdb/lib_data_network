package hustphyt.network;

import hustphyt.db.DB_joinInfo;
import hustphyt.info.Info;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_noticeUsers;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;

public class Network_joinInfo extends NetworkAb{
	public Info_notice notice;
	public Info_user user;
	public static String key="joinInfo";
	
	public Network_joinInfo(Info_notice notice,Info_user user)
	{
		this.notice=notice;
		this.user=user;
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
		String message=key+Info.sep1+user.id+Info.sep1+notice.id;
		output.writeUTF(message);
		output.flush();
		
		flag=input.readInt();
		
		Info.users=new ArrayList<Info_user>();
		Info.joinInfos=new String[3];
		int type=notice.type;
		for (int i=0;i<3;i++)
		{
			String str=input.readUTF();
			String[] strArray = str.split(Info.sep2);
			int num=Integer.parseInt(strArray[0]);
			if ((type==0)&&(i==2))
				continue;
			int t=type;
			if (t>9) t=4;
			Info.joinInfos[i]=Info.joinInfoTitles[t][i]+num+"»À£∫\n";
			for (int j=1;j<strArray.length;j++)
			{
				String[] strArray2= strArray[j].split(Info.sep1);
				Info_user user=new Info_user(Integer.parseInt(strArray2[0]));
				user.name=strArray2[1];
				user.oldState=i;
				user.state=i;
				if (strArray2.length>2)
					user.refuseInfo=strArray2[2];
				Info.users.add(user);
				
				if (strArray2.length>2)
					Info.joinInfos[i]=Info.joinInfos[i]+strArray2[1]+" : "+strArray2[2];
				else
					Info.joinInfos[i]=Info.joinInfos[i]+strArray2[1];
				if (j<strArray.length-1)
					if (strArray2.length>1)
						Info.joinInfos[i]=Info.joinInfos[i]+"\n";
					else
						Info.joinInfos[i]=Info.joinInfos[i]+"£¨";
			}
		}
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		DB_joinInfo db=new DB_joinInfo(notice,user);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
		
		int index=0;
		for (int i=0;i<3;i++)
		{
			StringBuffer message=new StringBuffer(""+db.userNum[i]+Info.sep2);
			for (int j=0;j<db.userNum[i];j++)
			{
				Info_user user=db.users.get(index+j);
				message.append(user.id+Info.sep1+user.name);
				if (user.refuseInfo!=null)
					message.append(Info.sep1+user.refuseInfo);
				message.append(Info.sep2);
				System.out.println(message);
			}
			index+=db.userNum[i];
			output.writeUTF(message.toString());
			output.flush();
		}
	}

}
