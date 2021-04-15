package top.caolizhi.example.zookeeperdemo.async;

import java.util.List;
import java.util.Objects;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.curator.x.async.modeled.JacksonModelSerializer;
import org.apache.curator.x.async.modeled.ModelSpec;
import org.apache.curator.x.async.modeled.ModeledFramework;
import org.apache.curator.x.async.modeled.ZPath;
import org.apache.logging.log4j.util.Strings;

import com.google.common.collect.Lists;
import top.caolizhi.example.zookeeperdemo.configuration.CuratorClient;
import top.caolizhi.example.zookeeperdemo.dto.TestModel;

public class AsyncExample {


	// 只能监听一次，输出一次之后就失效，需要重新加上 watcher
	public static void watcherExample() {
		CuratorFramework client = CuratorClient.getClient();
		AsyncCuratorFramework asyncClient = AsyncCuratorFramework.wrap(client);
		List<String> changes = Lists.newArrayList();
		asyncClient.watched().getData().forPath("/caolizhi/watcher-example")
			.event()
			.thenAccept(watchedEvent -> {
				try {
					changes.add(new String(client.getData().forPath(watchedEvent.getPath())));
					System.out.println("================================" + Strings.join(changes, ','));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}

	public static void modelExample() {
		CuratorFramework client = CuratorClient.getClient();
		AsyncCuratorFramework asyncClient = AsyncCuratorFramework.wrap(client);
		ModelSpec<TestModel> modelSpec = ModelSpec.builder(ZPath.parseWithIds("/caolizhi/model-example"),
			JacksonModelSerializer.build(TestModel.class)).build();
		ModeledFramework<TestModel> modelClient = ModeledFramework.wrap(asyncClient, modelSpec);
		modelClient.set(new TestModel("localhost", "8080"));
		modelClient.read().whenComplete((model, e) -> {
			if (Objects.nonNull(e)) {
				e.printStackTrace();
			} else {
				System.out.println("************************** host is " + model.getHost());
				System.out.println("************************** port is " + model.getPort());
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
//		AsyncExample.watcherExample();
		AsyncExample.modelExample();
		Thread.sleep(Integer.MAX_VALUE);
	}


}
