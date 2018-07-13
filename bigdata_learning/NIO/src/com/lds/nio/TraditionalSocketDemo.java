package com.lds.nio;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TraditionalSocketDemo {
	public static void main(String[] args) throws IOException{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		ServerSocket  serverSocket=  new ServerSocket(9788);
		System.out.println("服务端启动。。。。");
		while(true){
			final Socket socket = serverSocket.accept();
			threadPool.execute(new Runnable(){
				@Override
				public void run()
				{
					try {
						System.out.println("有新客户端连接上来了。。。。");
						
						InputStream is =  socket.getInputStream();
						byte[] b= new byte[1024];
						while(true){
							int data = is.read(b);
							if(data != -1)
							{
								String info = new String(b,0,data,"GBK");
								System.out.println(info.toString());
							}
							else
							{
								break;
							}
						} 

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
