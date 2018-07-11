package cn.itcast.bigdata.zkdist;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class DistributeServer {
	private static final String connectString="Mini01:2181,Mini02:2181,Mini03:2181";
	private static final int sessionTimeout = 200;
	private ZooKeeper zk = null;
	private static final String parentNode = "/servers";
	
	/**
	 * ������ZK�Ŀͻ�������
	 * @throws Exception
	 */
	public void getConnect() throws Exception {
		zk = new ZooKeeper(connectString,sessionTimeout,new Watcher(){
			@Override
			public void process(WatchedEvent event){
				//�յ��¼�֪ͨ��ص�������Ӧ���������Լ����¼������߼���
				System.out.println(event.getType()+ "---" + event.getPath());
				try {
					zk.getChildren("/", true);
				} catch (Exception e) {
				}
				
			}
		});
	}
	/**	 
	 * ��ZK��Ⱥע���������Ϣ
	 * @param hostname
	 * @throws Exception
	 */
	public void registerServer(String hostname) throws Exception{
		String create = zk.create(parentNode+"/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + " is online.."+create);
	}
	/**
	 * ҵ����
	 * @throws Exception 
	 */
	public void handleBussiness(String hostname) throws Exception{
		System.out.println(hostname + " start working.....");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		//����ZK����
		DistributeServer server = new  DistributeServer();
		server.getConnect();
		//����Zk����ע���������Ϣ
		server.registerServer(args[0]);
		//����ҵ����
		server.handleBussiness(args[0]);
	}
}
