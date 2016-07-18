package hustphyt.network;

import hustphyt.db.DB_icon;
import hustphyt.info.Info;
import hustphyt.info.Info_group;
import hustphyt.info.Info_notice;
import hustphyt.info.Info_user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Network_icons extends NetworkAb{
	public Info_user user;
	public static String key="icons";
	public List<Info_group> groups;
	public List<Info_notice> notices;
			
	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		return DB_icon.str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	protected void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		groups=new ArrayList<Info_group>();
		StringBuffer message=new StringBuffer(key);
		for (int i=0;i<Info.groups.size();i++)
			if (Info.groups.get(i).icon==null)
			{
				groups.add(Info.groups.get(i));
				message.append(Info.sep1);
				message.append(Info.groups.get(i).id+"");
			}
		output.writeUTF(message.toString());
		output.flush();
		
		for (int i=0;i<groups.size();i++)
			groups.get(i).icon=reciveBytes();
		
		notices=new ArrayList<Info_notice>();
		message=new StringBuffer();
		for (int i=0;i<Info.notices.size();i++)
			if (Info.notices.get(i).icon==null)
			{
				notices.add(Info.notices.get(i));
				message.append(Info.notices.get(i).id+"");
				message.append(Info.sep1);
			}
		
		output.writeUTF(message.toString());
		output.flush();
		
		for (int i=0;i<notices.size();i++)
			try {
				notices.get(i).icon=reciveBytes();
				if (notices.get(i).icon.length==0)
					notices.get(i).icon=null;
			} catch (Exception e) {
				notices.get(i).icon=null;
			}
		
		flag=1;
	}

	@Override
	protected void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		
		for (int i=1;i<strArray.length;i++)
		{
			DB_icon db=new DB_icon(new Info_group(Integer.parseInt(strArray[i])));
			db.operate();
			
			sendBytes(db.group.icon);
		}
		
		String string=input.readUTF();
		String[] strArr2=string.split(Info.sep1);
		System.out.println(string);
		if (!string.equals(""))
		for (int i=0;i<strArr2.length;i++)
		{
			DB_icon db=new DB_icon(new Info_notice(Integer.parseInt(strArr2[i])));
			db.operate();
			
			sendBytes(db.notice.icon);
		}
		flag=1;
	}
}
