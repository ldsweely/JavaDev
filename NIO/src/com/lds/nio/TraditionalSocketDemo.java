package com.lds.nio;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TraditionalSocketDemo {
	public static void main(String[] args) throws IOException{
		ServerSocket  serverSocket=  new ServerSocket(9999);
		System.out.println("�����������������");
		while(true){
			Socket socket = serverSocket.accept();
			System.out.println("���¿ͻ������������ˡ�������");
			
			InputStream is =  socket.getInputStream();
			byte[] b= new byte[1024];
			while(true){
				int data = is.read();
				if(data != -1)
				{
					String info = new String(b,0,data);
					System.out.println(info);
				}
			}
		}
	}
}
