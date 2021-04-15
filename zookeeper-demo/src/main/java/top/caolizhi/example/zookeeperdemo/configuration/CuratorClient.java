package top.caolizhi.example.zookeeperdemo.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.async.AsyncCuratorFramework;

public class CuratorClient {

	public static CuratorFramework getClient() {
		CuratorFramework client = CuratorFrameworkFactory.builder()
			.retryPolicy(new RetryNTimes(3, 3000))
			.connectString("127.0.0.1:2181")
			.connectionTimeoutMs(60000)
			.build();
		client.start();
		return client;
	}

	public static AsyncCuratorFramework getAsyncClient() {
		return AsyncCuratorFramework.wrap(getClient());
	}
}
