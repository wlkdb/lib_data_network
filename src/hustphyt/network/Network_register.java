package hustphyt.network;

import java.io.IOException;
import java.util.Random;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;


import hustphyt.db.DB_register;
import hustphyt.info.Info;
import hustphyt.info.Info_user;

public class Network_register extends NetworkAb{
	public Info_user user;
	public static String key="register";
	public int num=0;

	public Network_register(Info_user user)
	{
		this.user=user;
	}
	
	public void run_clientToServer() throws IOException {
		// TODO Auto-generated method stub
		//long beginTime=System.currentTimeMillis();

		user.password=Info_user.encryptmd5(user.password);
		String message=key+Info.sep1+user.getFormatString(1);
		output.writeUTF(message);
		output.flush();
		synchronized (this){
			try {
				wait();
				output.writeInt(num);
				output.flush();
				
				flag=input.readInt();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run_serverToClient() throws IOException {
		// TODO Auto-generated method stub
		int identifying=new Random().nextInt(899999)+100000;
		String product="群通告";
		
		TaobaoClient client = new DefaultTaobaoClient("1", "1", "1");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int num=input.readInt();
		if (num!=identifying)
		{
			output.writeInt(DB_register.str_results.length);
			output.flush();
			return;
		}
		
		db=new DB_register(user);
		flag=db.operate();
		
		output.writeInt(flag);
		output.flush();
	}

	@Override
	protected String[] setStr_results() {
		// TODO Auto-generated method stub
		String[] str_results=new String[DB_register.str_results.length+1];
		for (int i=0;i<str_results.length-1;i++)
			str_results[i]=DB_register.str_results[i];
		str_results[str_results.length-1]="验证码错误";
		return str_results;
	}

	@Override
	protected String setKey() {
		// TODO Auto-generated method stub
		return Network_register.key;
	}

}
