package cn.com.transaction;

import cn.com.transaction.controller.TransactionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class TransactionDemoApplicationTests {

	@Autowired
	private TransactionController transactionController;

	@Test
	void contextLoads() {
	}

	@Test
	public void testLock() throws InterruptedException {
		long start = System.currentTimeMillis();
		ExecutorService executorService = Executors.newCachedThreadPool();
		CountDownLatch downLatch = new CountDownLatch(10000);
		for (int i = 0; i< 10000; i++) {
			final String key = "hello" + (i%100);
			executorService.submit(new Runnable() {
				@lombok.SneakyThrows
				@Override
				public void run() {
					transactionController.helloWorld(key);
					downLatch.countDown();
				}
			});
		}
		downLatch.await();
		long end = System.currentTimeMillis();
		long diff = end - start;
		System.out.println("毫秒数=" + diff + "   秒数=" + (diff/1000) + "  分钟数=" + (diff/(60*1000)));
	}

	@Test
	public void getCpu() {
		int a = Runtime.getRuntime().availableProcessors();
		System.out.println(a);
	}

	@Test
	public void getREs() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		System.out.println("Local HostAddress: "+addr.getHostAddress());
				String hostname = addr.getHostName();
		System.out.println("Local host name: "+hostname);
	}

	public static void main(String[] args) throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		System.out.println("Local HostAddress: "+addr.getHostAddress());
		String hostname = addr.getHostName();
		System.out.println("Local host name: "+hostname);
	}

}
