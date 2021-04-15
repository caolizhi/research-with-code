package top.caolizhi.example.zookeeperdemo.leader;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;

import top.caolizhi.example.zookeeperdemo.configuration.CuratorClient;

public class LeaderElectionExample {

	static String leaderPath = "/caolizhi/leader-election";

	private static Runnable getRunnable(String name) {
		return () -> {
			CuratorFramework client = CuratorClient.getClient();
			LeaderSelector leaderSelector = new LeaderSelector(client, leaderPath,
				new LeaderSelectorListener() {
					@Override
					public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
						System.out.println("************************* " + name + " became the leader ! ");
						Thread.sleep(TimeUnit.SECONDS.toMillis(3));
					}

					@Override
					public void stateChanged(CuratorFramework client, ConnectionState newState) {
						System.out.println("*************** " + name + " state changed " + newState.name());
					}
				});
			leaderSelector.autoRequeue();
			leaderSelector.start();
		};
	}

	public static void main(String[] args) throws InterruptedException {
		IntStream.range(0, 10).mapToObj(i -> new Thread(getRunnable("thread " + i))).forEach(Thread::start);
		Thread.sleep(Integer.MAX_VALUE);
	}

}
