package com.easyrr.EasyResponse;

public class Event {

	private String string;
	private String author;

	public Event(String string, String author) {
		this.string = string;
		this.author = author;
	}

	@Override
	public String toString() {
		return "Event [string=" + string + ", author=" + author + "]";
	}
	
}
