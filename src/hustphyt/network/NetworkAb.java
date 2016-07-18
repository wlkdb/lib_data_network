package hustphyt.network;

import hustphyt.db.DBAb;
import hustphyt.info.Info;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class NetworkAb implements Runnable{

	protected DataInputStream input;
	protected DataOutputStream output;
	protected String[] strArray;
	protected Socket socket;
	protected DBAb db;
	protected int flag;
	private Thread th_clientToServer,th_serverToClient;
	private int message_len=1024*4;

	protected String key;
	protected String[] str_results;
	
	public static NetworkEndAb networkEnd;
	
	public NetworkAb()
	{
		flag=-1;
		str_results=setStr_results();
		key=setKey();
		th_clientToServer=new Thread(this);
		th_serverToClient=new Thread(this);
	}
	
	public void clientToServer()
	{
		th_clientToServer.start();
	}
	
	public void serverToClient(Socket socket)
	{
		this.socket=socket;
		th_serverToClient.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		Thread th = new Thread(this);
		try {
			
			if (th.currentThread()==th_clientToServer)
			{
				Socket socket;
				socket = new Socket(Info.serverIP,Info.serverPort);
				input=new DataInputStream(socket.getInputStream());
				output=new DataOutputStream(socket.getOutputStream());
				run_clientToServer();
				input.close();
				output.close();
				socket.close();
				if (networkEnd!=null)
				{
					networkEnd.isEnd(this);
				}
			}
			else if (th.currentThread()==th_serverToClient)
			{
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				//String str=input.readLine();
				//output.writeBytes("hello");
				String str = input.readUTF();
				strArray = str.split(Info.sep1);

				System.out.println("开始处理"+strArray[0]+"请求");
				//System.out.println(str);
				
				run_serverToClient();
				input.close();
				output.close();
				socket.close();
				
				System.out.println(getStr_result());
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (th.currentThread()==th_clientToServer)
				if (networkEnd!=null)
					networkEnd.isEnd(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (th.currentThread()==th_clientToServer)
				if (networkEnd!=null)
					networkEnd.isEnd(this);
		}
		
	}
	
	public String getStr_result()
	{
		if (flag==-1)
			return "连接服务器失败";
		else
			return str_results[flag];
	}
	
	public int getFlag()
	{
		return flag;
	}
	
	public String getKey()
	{
		return key;
	}
	
	protected void sendBytes(byte[] bytes) throws IOException
	{
		if (bytes==null)
			bytes=new byte[0];
		int len=bytes.length;
		output.writeInt(bytes.length);
        output.flush();
		System.out.println(len);
		int index=0;
        System.out.println("开始发送数据...");
        while (index<len) {
            if (index+message_len>len)
            	message_len=len-index;
            System.out.println(index);
            System.out.println(message_len);
            output.write(bytes, index, message_len);
            output.flush();
            index+=message_len;
        }
        System.out.println("完成发送");
	}
	
	protected byte[] reciveBytes() throws IOException
	{
		int len=input.readInt();
		System.out.println(len);
		byte[] bytes=new byte[len];
		int index=0;
        System.out.println("开始接收数据...");
        while (index<len) {
        	if (index+message_len>len)
        		message_len=len-index;
            System.out.println(message_len);
            index+=input.read(bytes, index, message_len);
        }
        System.out.println("完成接收");
		return bytes;
	}
	
	protected abstract String[] setStr_results();
	
	protected abstract String setKey();
	
	protected abstract void run_clientToServer() throws IOException;
	
	protected abstract void run_serverToClient() throws IOException;
}
