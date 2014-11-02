package com.example.jagdeep.sharedlibrary.model.tv.client;

import android.os.Parcel;
import android.os.Parcelable;

public class Show implements Parcelable {
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

	public Show(Parcel source) {
		title = source.readString();
		time = source.readString();
		channel_name = source.readString();
		image = source.readString();
		isTopPick = source.readByte() == 1;
		timeStamp = source.readLong();
		broadcastID = source.readString();
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(time);
		dest.writeString(channel_name);
		dest.writeString(image);
		dest.writeByte((byte) (isTopPick ? 1 : 0));
		dest.writeLong(timeStamp);
		dest.writeString(broadcastID);
	}

	public static final Creator<Show> CREATOR = new Creator<Show>() {
		@Override
		public Show createFromParcel(Parcel source) {
			return new Show(source);
		}

		@Override
		public Show[] newArray(int size) {
			return new Show[size];
		}
	};
}
