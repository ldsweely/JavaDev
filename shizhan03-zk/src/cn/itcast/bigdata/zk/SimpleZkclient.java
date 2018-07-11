package cn.itcast.bigdata.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class SimpleZkclient {
	private static final String connectString="Mini01:2181,Mini02:2181,Mini03:2181";
	private static final int sessionTimeout = 200;
	ZooKeeper zkClient = null;
	
	@Before
	public void init() throws Exception{
		zkClient = new ZooKeeper(connectString,sessionTimeout,new Watcher(){
			@Override
			public void process(WatchedEvent event){
				//�յ��¼�֪ͨ��ص�������Ӧ���������Լ����¼������߼���
				System.out.println(event.getType()+ "---" + event.getPath());
				try {
					zkClient.getChildren("/", true);
				} catch (Exception e) {
				}
				
			}
		});
	}

	

		
	/**
	 * ���ݵ���ɾ�Ĳ�
	 * @throws KeeperException
	 * @throws InterruptedException
	 * 
	 */
	//�������ݽڵ㵽zk��
	public void testCreate() throws KeeperException, InterruptedException{
		//����1��Ҫ�����Ľڵ��·�� ����2���ڵ������   ����3���ڵ��Ȩ��   ����4���ڵ������
		String nodeCreated = zkClient.create("/eclipse", "hellozk".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		//�ϴ������ݿ������κ����ͣ���Ҫת��byte
	}
	
	//�ж�znode�Ƿ����
	@Test
	public void testExist() throws Exception{
		Stat  stat = zkClient.exists("/eclipse", false);
		System.out.println(stat==null?"not exist":"exist");
	}
	//��ȡ�ӽڵ�
	@Test
	public void getChildren() throws KeeperException, InterruptedException{
		List<String> children = zkClient.getChildren("/", true);
		for(String child:children){
			System.out.println(child);
		}
		Thread.sleep(Long.MAX_VALUE);		
	}
	//��ȡznode������
	@Test
	 public void getData() throws Exception{
		 byte[] data = zkClient.getData("/eclipse", false, new Stat());
		 System.out.println(new String(data));
	 }
	//ɾ��znode������
	@Test
	 public void deleteData() throws Exception{
		//����2��ָ��Ҫɾ���İ汾��-1Ϊ���а汾
		 zkClient.delete("/eclipse", -1);
	 }
	//�޸�znode������
	@Test
	 public void setData() throws Exception{
		 zkClient.setData("/app1", "I miss you angelababy".getBytes(),-1);
		 
		 byte[] data = zkClient.getData("/app1", false, null);
		 System.out.println(new String(data));
	 }
}
