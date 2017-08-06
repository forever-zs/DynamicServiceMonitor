import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ClusterClient {
	private String ZK_SERVER_LIST = "mini1:2181,mini2:2181,mini3:2181";

	private static final int sessionTimeout = 2000;

	private String SERVER_DIR = "/servers";

	private ZooKeeper zk;
	
	private List<String> servers=new ArrayList<>();
	public void connect() throws IOException {
		zk = new ZooKeeper(ZK_SERVER_LIST, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.toString());
				try {
					getServerList();
					System.out.println(servers);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void getServerList() throws KeeperException, InterruptedException{
		List<String> nodeList = zk.getChildren(SERVER_DIR,true);
		servers.clear();
		for(String node:nodeList){
			byte []data = zk.getData(SERVER_DIR+"/"+node, false, null);
			String hostName = new String(data);
			servers.add(hostName);
		}
	}
	
	public void diy(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true){
					
				}
			}
		}).start();;
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ClusterClient client =new ClusterClient();
		client.connect();
		client.getServerList();
		client.diy();
	}
}
