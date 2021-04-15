package top.caolizhi.example.zookeeperdemo.locking;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class LockingExample {

	private static final int QTY = 5;
	private static final int REPETITIONS = QTY * 10;

	private static final String PATH = "/caolizhi/locks";

	public static void main(String[] args) throws Exception {
		// all of the useful sample code is in ExampleClientThatLocks.java

		// FakeLimitedResource simulates some external resource that can only be access by one process at a time
		final FakeLimitedResource resource = new FakeLimitedResource();
		ExecutorService service = Executors.newFixedThreadPool(QTY);
		for (int i = 0; i < QTY; ++i) {
			final int index = i;
			Callable<Void> task = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
						new ExponentialBackoffRetry(1000, 3));
					try {
						client.start();

						ExampleClientThatLocks example = new ExampleClientThatLocks(client, PATH, resource,
							"Client " + index);
						for (int j = 0; j < REPETITIONS; ++j) {
							example.doWork(10, TimeUnit.SECONDS);
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (Exception e) {
						e.printStackTrace();
						// log or do something
					} finally {
						CloseableUtils.closeQuietly(client);
					}
					return null;
				}
			};
			service.submit(task);
		}

		service.shutdown();
		service.awaitTermination(10, TimeUnit.MINUTES);
	}

	static class FakeLimitedResource {
		private final AtomicBoolean inUse = new AtomicBoolean(false);

		public void use() throws InterruptedException {
			// in a real application this would be accessing/manipulating a shared resource

			if (!inUse.compareAndSet(false, true)) {
				throw new IllegalStateException("Needs to be used by one client at a time");
			}

			try {
				Thread.sleep((long)(3 * Math.random()));
			} finally {
				inUse.set(false);
			}
		}
	}

	static class ExampleClientThatLocks {
		private final InterProcessMutex lock;
		private final FakeLimitedResource resource;
		private final String clientName;

		public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource,
			String clientName) {
			this.resource = resource;
			this.clientName = clientName;
			lock = new InterProcessMutex(client, lockPath);
		}

		public void doWork(long time, TimeUnit unit) throws Exception {
			if (!lock.acquire(time, unit)) {
				throw new IllegalStateException(clientName + " could not acquire the lock");
			}
			try {
				System.out.println(clientName + " has the lock");
				resource.use();
			} finally {
				System.out.println(clientName + " releasing the lock");
				lock.release(); // always release the lock in a finally block
			}
		}
	}

}
