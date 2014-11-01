package com.example.jagdeep.wear1.model.tv.server;

public class Section {
	private String id;
	private String name;
	private Item[] items;

	private Section() {
	}

	public Section(String id, String name, Item[] items) {
		this.id = id;
		this.name = name;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Item[] getItems() {
		return items;
	}
}
