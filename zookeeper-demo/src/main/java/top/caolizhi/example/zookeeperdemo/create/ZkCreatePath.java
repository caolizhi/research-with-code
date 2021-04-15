package top.caolizhi.example.zookeeperdemo.create;

import java.nio.charset.StandardCharsets;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class ZkCreatePath {

//	public final static String path = "/caolizhi/watcher-example";
	public final static String path = "/caolizhi/model-example";

	public static void main(String[] args) {
		RetryNTimes retryPolicy = new RetryNTimes(3, 3000);
		CuratorFramework client = CuratorFrameworkFactory.newClient("10.113.147.85:2181", retryPolicy);
		client.start();
		try {
			String my_value = "my_value_" + System.currentTimeMillis();
			client.create().creatingParentsIfNeeded().forPath(path, my_value.getBytes(StandardCharsets.UTF_8));
//			client.setData().forPath(path, my_value.getBytes(StandardCharsets.UTF_8));
			String s = new String(client.getData().forPath(path));
			System.out.println(s.equals(my_value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
