package com.hawk.hadoop.defguid4.ch21_zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Example 21-1. A program to create a znode representing a group in ZooKeeper
 * 
 * @author pzhang1
 * 
 */
public class CreateGroup implements Watcher{
	private static final int SESSION_TIMEOUT = 5000;
	private ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	
	public void connect(String hosts) throws IOException, InterruptedException{
		zk = new ZooKeeper(hosts,SESSION_TIMEOUT,this);
		connectedSignal.await();
	}
	
	

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected){
			connectedSignal.countDown();
		}
		
	}
}
