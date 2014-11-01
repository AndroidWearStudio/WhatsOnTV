package com.example.jagdeep.wear1.model.tv.client;

public class Show {
	private String title;
	private String time;
	private String channel_name;
	private String image;
	private boolean isTopPick;
	private Long timeStamp;
	private String broadcastID;

	public Show(String broadcastID, String title, String time, String channel_name, String image, long timeStamp, boolean isTopPick) {
		this.broadcastID = broadcastID;
		this.title = title;
		this.time = time;
		this.channel_name = channel_name;
		this.image = image;
		this.isTopPick = isTopPick;
		this.timeStamp = timeStamp;
	}

	public Show() {
	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public String getImage() {
		return image;
	}

	public boolean isTopPick() {
		return isTopPick;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public String getBroadcastID() {
		return broadcastID;
	}
}
