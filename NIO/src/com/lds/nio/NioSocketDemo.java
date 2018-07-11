package com.lds.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import javax.swing.text.html.HTMLDocument.Iterator;


/**
 * NIO Socket
 * @author liuds
 *
 */
public class NioSocketDemo {
	private Selector selector;//通道选择器（管理器）
	
	public static void main(String[] args) throws IOException{
		NioSocketDemo nio = new NioSocketDemo();
		nio.initServer(9788);
		nio.listenSelector();
	}
	public void initServer(int port) throws IOException{
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);//非阻塞
		serverChannel.socket().bind(new InetSocketAddress(port));
		
		this.selector=Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务已启动。。。。");
		
	}
		public void listenSelector() throws IOException{
		//轮询
		while(true){
			//等待客户连接 
			//select 模型  多路复用
			this.selector.select();		

			java.util.Iterator<SelectionKey> iteKey = this.selector.selectedKeys().iterator();
			
			while(iteKey.hasNext()){
				SelectionKey key = iteKey.next();
				iteKey.remove();
				//处理请求
				handler(key);
			}
	
		}
	}

	/**
	 * 处理客户端请求
	 * @param key
	 * @throws IOException 
	 */
	private void handler(SelectionKey key) throws IOException {
		if(key.isAcceptable()){
			//处理客户端连接请求事件
			System.out.println(" 新客户端上来了。。。。");
			
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			//接收客户端发送的信息时，需要给通道 设置读的权限
			socketChannel.register(selector,SelectionKey.OP_READ);
		}
		else
		{
			if(key.isReadable()){
				//处理读的事件
				SocketChannel socketChannel = (SocketChannel)key.channel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int readData = socketChannel.read(buffer);
				if(readData >0)
				{
					String info = new String(buffer.array(),"GBK").trim();
					System.out.println(" 服务端收到数据---" + info);
				}
				else
				{
					System.out.println("客户端关闭了。。。");
					key.cancel();
				}
			}
		}
		
		
	}
}
