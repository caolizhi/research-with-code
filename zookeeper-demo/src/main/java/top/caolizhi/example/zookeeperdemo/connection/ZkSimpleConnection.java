package top.caolizhi.example.zookeeperdemo.connection;


import java.util.Objects;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

public class ZkSimpleConnection {

	public static void main(String[] args) {
		RetryNTimes retryPolicy = new RetryNTimes(3, 3000);
		CuratorFramework client = CuratorFrameworkFactory.newClient("10.113.147.85:2181", retryPolicy);
		client.start();
		try {
			Stat stat = client.checkExists().forPath("/");
			System.out.println("path '/' is null or not ? +   " + Objects.isNull(stat));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
