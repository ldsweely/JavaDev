package cn.itcast.bigdata.zkdist;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DistributedClient {
	private static final String connectString="Mini01:2181,Mini02:2181,Mini03:2181";
	private static final int sessionTimeout = 200;
	private ZooKeeper zk = null;
	private static final String parentNode = "/servers";
	
	//ע�⣺��volatiler��������ڣ�
	private volatile List<String> serverList;
	/**
	 * ������ZK�Ŀͻ�������
	 * @throws Exception
	 */
	public void getConnect() throws Exception {
		zk = new ZooKeeper(connectString,sessionTimeout,new Watcher(){
			@Override
			public void process(WatchedEvent event){
				//�յ��¼�֪ͨ��ص�������Ӧ���������Լ����¼������߼���
				try {
					//���¸��·������б�����ע���˼���
					getServerList();
				} catch (Exception e) {
				}
				
			}
		});
	}

	public void getServerList() throws Exception{
		
		//��ȡ�������ӽڵ���Ϣ�����Ը��ڵ���м���
		List<String> children = zk.getChildren(parentNode,true);
		//�ȴ���һ���ֲ�List����ȡ��������Ϣ
		ArrayList<String> servers = new ArrayList<String>();
		for(String child:children){
			//childֻ���ӽڵ�Ľڵ���
			byte[] data = zk.getData(parentNode+"/"+child, false, null);
			servers.add(new String(data));
		}
		//��server��ֵ����Ա����serverList,���ṩ����ҵ���߳�ʹ��
		serverList=servers;
		//��ӡ�������б�
		System.out.println(serverList);
	}
	
	/**
	 * ҵ����
	 * @throws Exception 
	 */
	public void handleBussiness() throws Exception{
		System.out.println(" client start working.....");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		
		//��ȡ����
		DistributedClient client = new DistributedClient();
		client.getConnect();
			
		//��ȡservers���ӽڵ���Ϣ(������)�����л�ȡ��������Ϣ�б�
		client.getServerList();
		
		//ҵ���߳�����
		client.handleBussiness();
		
		
		
		
		
		
		
		
		
	}
	
	
}
