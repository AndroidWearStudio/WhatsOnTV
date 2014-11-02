package com.example.jagdeep.wear1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.ImageReference;
import android.view.Gravity;
import com.example.jagdeep.sharedlibrary.model.tv.client.Show;

import java.util.List;

public class ShowsGridPagerAdapter extends FragmentGridPagerAdapter {
	private Context mContext;
	private List<List<Show>> shows;

	public ShowsGridPagerAdapter(Context context, FragmentManager fragmentManager, List<List<Show>> shows) {
		super(fragmentManager);
		this.mContext = context;
		this.shows = shows;
	}

	@Override
	public int getRowCount() {
		return shows.size();
	}

	@Override
	public int getColumnCount(int i) {
		return shows.get(i).size();
	}

	@Override
	public ImageReference getBackground(int row, int column) {
		//		return ImageReference.forImageUri(Uri.parse(shows.get(row).get(column).getImage()));
		return null;
	}

	@Override
	public Fragment getFragment(int row, int col) {
		Show show = shows.get(row).get(col);
		CardFragment fragment = CardFragment.create(show.getTitle(), show.getTime() + " on " + show.getChannel_name());
		// Advanced settings
		fragment.setCardGravity(Gravity.BOTTOM);
		fragment.setExpansionEnabled(false);
		return fragment;
	}
}
