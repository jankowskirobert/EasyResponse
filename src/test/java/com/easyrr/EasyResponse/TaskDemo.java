package com.easyrr.EasyResponse;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TaskDemo implements Callable<Event>{

	private String taskName;
	private String author;
	
	public TaskDemo(String taskName, String author) {
		super();
		this.taskName = taskName;
		this.author = author;
	}

	@Override
	public Event call() throws Exception {
		int x =new Random().nextInt(25);
		 TimeUnit.SECONDS.sleep(x);
		return new Event(taskName + " [" + x + "]", author);
	}

}
