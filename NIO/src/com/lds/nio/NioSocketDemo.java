package com.lds.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;


/**
 * NIO Socket
 * @author liuds
 *
 */
public class NioSocketDemo {
	private Selector selector;//通道选择器（管理器）
	
	public void initServer(int port) throws IOException{
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);//非阻塞
		serverChannel.socket().bind(new InetSocketAddress(port));
		
		this.selector=Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务已启动。。。。");
		
	}
	
	public void listenSelector(SelectionKey selectionKey){
		//轮询
		while(true){
			this.selector		
			
			
			
			
			
			
	
		}
	}
}
