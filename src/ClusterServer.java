import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ClusterServer {

	private String ZK_SERVER_LIST = "mini1:2181,mini2:2181,mini3:2181";
	
	private static final int sessionTimeout = 2000;

	private String SERVER_DIR = "/servers";
	
	private ZooKeeper zk;

	public void connect() throws IOException {
		
		zk = new ZooKeeper(ZK_SERVER_LIST, sessionTimeout, new Watcher() {
			
			//当zookeeper服务器集群有断线时会调用
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.toString());
			}
		});
	}

	public void register(String hontname) throws IOException, KeeperException, InterruptedException {
		//创建瞬时态且带序号的节点，这样在节点下线后该节点就会被删除
		String result = zk.create(SERVER_DIR+"/server", hontname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println("创建结果："+result);
	}
	
	public void diy(){
		System.out.println("just do it");
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ClusterServer server =new ClusterServer();
		server.connect();
		server.register(args[0]);
		server.diy();
	}

}
