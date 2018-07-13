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
				//收到事件通知后回调函数（应该是我们自己的事件处理逻辑）
				System.out.println(event.getType()+ "---" + event.getPath());
				try {
					zkClient.getChildren("/", true);
				} catch (Exception e) {
				}
				
			}
		});
	}

	

		
	/**
	 * 数据的增删改查
	 * @throws KeeperException
	 * @throws InterruptedException
	 * 
	 */
	//创建数据节点到zk中
	public void testCreate() throws KeeperException, InterruptedException{
		//参数1：要创建的节点的路径 参数2：节点的数据   参数3：节点的权限   参数4：节点的类型
		String nodeCreated = zkClient.create("/eclipse", "hellozk".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		//上传的数据可以是任何类型，但要转成byte
	}
	
	//判断znode是否存在
	@Test
	public void testExist() throws Exception{
		Stat  stat = zkClient.exists("/eclipse", false);
		System.out.println(stat==null?"not exist":"exist");
	}
	//获取子节点
	@Test
	public void getChildren() throws KeeperException, InterruptedException{
		List<String> children = zkClient.getChildren("/", true);
		for(String child:children){
			System.out.println(child);
		}
		Thread.sleep(Long.MAX_VALUE);		
	}
	//获取znode的数据
	@Test
	 public void getData() throws Exception{
		 byte[] data = zkClient.getData("/eclipse", false, new Stat());
		 System.out.println(new String(data));
	 }
	//删除znode的数据
	@Test
	 public void deleteData() throws Exception{
		//参数2：指定要删除的版本，-1为所有版本
		 zkClient.delete("/eclipse", -1);
	 }
	//修改znode的数据
	@Test
	 public void setData() throws Exception{
		 zkClient.setData("/app1", "I miss you angelababy".getBytes(),-1);
		 
		 byte[] data = zkClient.getData("/app1", false, null);
		 System.out.println(new String(data));
	 }
}
