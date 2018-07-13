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
	private Selector selector;//ͨ��ѡ��������������
	
	public static void main(String[] args) throws IOException{
		NioSocketDemo nio = new NioSocketDemo();
		nio.initServer(9788);
		nio.listenSelector();
	}
	public void initServer(int port) throws IOException{
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);//������
		serverChannel.socket().bind(new InetSocketAddress(port));
		
		this.selector=Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("������������������");
		
	}
		public void listenSelector() throws IOException{
		//��ѯ
		while(true){
			//�ȴ��ͻ����� 
			//select ģ��  ��·����
			this.selector.select();		

			java.util.Iterator<SelectionKey> iteKey = this.selector.selectedKeys().iterator();
			
			while(iteKey.hasNext()){
				SelectionKey key = iteKey.next();
				iteKey.remove();
				//��������
				handler(key);
			}
	
		}
	}

	/**
	 * ����ͻ�������
	 * @param key
	 * @throws IOException 
	 */
	private void handler(SelectionKey key) throws IOException {
		if(key.isAcceptable()){
			//����ͻ������������¼�
			System.out.println(" �¿ͻ��������ˡ�������");
			
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			//���տͻ��˷��͵���Ϣʱ����Ҫ��ͨ�� ���ö���Ȩ��
			socketChannel.register(selector,SelectionKey.OP_READ);
		}
		else
		{
			if(key.isReadable()){
				//��������¼�
				SocketChannel socketChannel = (SocketChannel)key.channel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int readData = socketChannel.read(buffer);
				if(readData >0)
				{
					String info = new String(buffer.array(),"GBK").trim();
					System.out.println(" ������յ�����---" + info);
				}
				else
				{
					System.out.println("�ͻ��˹ر��ˡ�����");
					key.cancel();
				}
			}
		}
		
		
	}
}
