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
	private Selector selector;//ͨ��ѡ��������������
	
	public void initServer(int port) throws IOException{
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);//������
		serverChannel.socket().bind(new InetSocketAddress(port));
		
		this.selector=Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("������������������");
		
	}
	
	public void listenSelector(SelectionKey selectionKey){
		//��ѯ
		while(true){
			this.selector		
			
			
			
			
			
			
	
		}
	}
}
