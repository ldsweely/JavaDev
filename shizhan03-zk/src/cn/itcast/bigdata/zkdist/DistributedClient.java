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
	
	//注意：加volatiler的意义何在？
	private volatile List<String> serverList;
	/**
	 * 创建到ZK的客户端连接
	 * @throws Exception
	 */
	public void getConnect() throws Exception {
		zk = new ZooKeeper(connectString,sessionTimeout,new Watcher(){
			@Override
			public void process(WatchedEvent event){
				//收到事件通知后回调函数（应该是我们自己的事件处理逻辑）
				try {
					//重新更新服务器列表，并且注册了监听
					getServerList();
				} catch (Exception e) {
				}
				
			}
		});
	}

	public void getServerList() throws Exception{
		
		//攻取服务器子节点信息，并对父节点进行监听
		List<String> children = zk.getChildren(parentNode,true);
		//先创建一个局部List来存取服务器信息
		ArrayList<String> servers = new ArrayList<String>();
		for(String child:children){
			//child只是子节点的节点名
			byte[] data = zk.getData(parentNode+"/"+child, false, null);
			servers.add(new String(data));
		}
		//把server赋值给成员变量serverList,已提供给各业务线程使用
		serverList=servers;
		//打印服务器列表
		System.out.println(serverList);
	}
	
	/**
	 * 业务功能
	 * @throws Exception 
	 */
	public void handleBussiness() throws Exception{
		System.out.println(" client start working.....");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		
		//获取连接
		DistributedClient client = new DistributedClient();
		client.getConnect();
			
		//获取servers的子节点信息(并监听)，从中获取服务器信息列表
		client.getServerList();
		
		//业务线程启动
		client.handleBussiness();
		
		
		
		
		
		
		
		
		
	}
	
	
}
