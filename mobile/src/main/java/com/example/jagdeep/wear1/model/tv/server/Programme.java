package com.example.jagdeep.wear1.model.tv.server;


public class Programme {
    private Channel channel;
    private String channelbroadcastevent_id;
    private String broadcastevent_id;
    private String episode_id;
    private String brand_id;
    private String title;
    private String image;
    private String featured_image;
    private String start_time;
    private String end_time;

	private Programme() {
	}

	public Programme(Channel channel, String channelbroadcastevent_id, String broadcastevent_id, String episode_id,
			String brand_id, String title, String image, String featured_image, String start_time, String end_time) {
		this.channel = channel;
		this.channelbroadcastevent_id = channelbroadcastevent_id;
		this.broadcastevent_id = broadcastevent_id;
		this.episode_id = episode_id;
		this.brand_id = brand_id;
		this.title = title;
		this.image = image;
		this.featured_image = featured_image;
		this.start_time = start_time;
		this.end_time = end_time;
	}

	public Channel getChannel() {
		return channel;
	}

	public String getChannelbroadcastevent_id() {
		return channelbroadcastevent_id;
	}

	public String getBroadcastevent_id() {
		return broadcastevent_id;
	}

	public String getEpisode_id() {
		return episode_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public String getFeatured_image() {
		return featured_image;
	}

	public String getStart_time() {
		return start_time;
	}

	public String getEnd_time() {
		return end_time;
	}
}
