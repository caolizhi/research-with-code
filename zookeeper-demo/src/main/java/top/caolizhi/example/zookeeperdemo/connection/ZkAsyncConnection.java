package top.caolizhi.example.zookeeperdemo.connection;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.curator.x.async.AsyncCuratorFramework;

import top.caolizhi.example.zookeeperdemo.configuration.CuratorClient;

public class ZkAsyncConnection {

	public static void main(String[] args) {
		AsyncCuratorFramework asyncClient = CuratorClient.getAsyncClient();
		try {
			asyncClient.create()
				.forPath("/caolizhi/async-create-test-" + System.currentTimeMillis(), "my_value".getBytes(StandardCharsets.UTF_8))
				.whenComplete((str, exception) -> {
					if (Objects.nonNull(exception)) {
						exception.printStackTrace();
					} else {
						System.out.println("======================================= result : " + str);
					}
				});
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
