package com.example.jagdeep.wear1.model.tv.server;


public class Channel {
    private String service_id;
    private String channel_id;
    private String channel_name;
    private String masterbrand_id;

	private Channel() {
	}

	public Channel(String service_id, String channel_id, String channel_name, String masterbrand_id) {
		this.service_id = service_id;
		this.channel_id = channel_id;
		this.channel_name = channel_name;
		this.masterbrand_id = masterbrand_id;
	}

	public String getService_id() {
		return service_id;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public String getMasterbrand_id() {
		return masterbrand_id;
	}
}
