package com.example.jagdeep.wear1.model.tv.server;

import com.example.jagdeep.wear1.model.tv.client.Show;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Shows {
	private Section[] sections;

	private Shows() {
		//jackson
	}

	public Shows(Section[] sections) {
		this.sections = sections;
	}

	public Section[] getSections() {
		return sections;
	}

	public List<Show> getShows() {
		List<Show> shows = new ArrayList<Show>();
		for (Section section : getSections()) {
			if (section.getId() != null && (section.getId().equals("popular") || section.getId().equals
					("toppick-tonight") || section.getId().equals("featured-tonight") || section.getId().equals
					("popular-tonight") || section.getId().equals("popular-week") || section.getId().equals
					("featured-week"))) {
				for (Item item : section.getItems()) {
					if (item.getType().equals("show")) {
						Show show = new Show(item.getProgramme().getBroadcastevent_id(), item.getProgramme().getTitle(), item.getTime_string(),
								item.getProgramme().getChannel().getChannel_name(), item.getProgramme().getImage()
								.replace("{width}", "640").replace("{height}", "360"),
								Long.parseLong(item.getProgramme().getStart_time()), section.getId().equals("toppick-tonight") || section.getId().equals
								("featured-tonight") || section.getId().equals("featured-week")
						);
						shows.add(show);
					}
				}
			}
		}
		return shows;
	}

	public boolean containsID(String id) {
		for (Section section : getSections()) {
			if (section.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public String getId() {
		return getSections()[0].getId();
	}
}
