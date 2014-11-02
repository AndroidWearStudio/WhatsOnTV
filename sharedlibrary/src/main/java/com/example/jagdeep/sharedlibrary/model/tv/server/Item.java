package com.example.jagdeep.sharedlibrary.model.tv.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String type;
    private String style;
    private String time_string;
    private Programme programme;

	private Item() {
	}

	public Item(String type, String style, String time_string, Programme programme) {
		this.type = type;
		this.style = style;
		this.time_string = time_string;
		this.programme = programme;
	}

	public String getType() {
		return type;
	}

	public String getStyle() {
		return style;
	}

	public String getTime_string() {
		return time_string;
	}

	public Programme getProgramme() {
		return programme;
	}
}
