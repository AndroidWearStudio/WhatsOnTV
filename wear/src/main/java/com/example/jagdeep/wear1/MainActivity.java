package com.example.jagdeep.wear1;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.WindowInsets;
import com.example.jagdeep.sharedlibrary.model.tv.client.Show;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Resources res = getResources();
		final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
		pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
			@Override
			public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
				// Adjust page margins:
				//   A little extra horizontal spacing between pages looks a bit
				//   less crowded on a round display.
				final boolean round = insets.isRound();
				int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
				int colMargin = res.getDimensionPixelOffset(round ?
						R.dimen.page_column_margin_round : R.dimen.page_column_margin);
				pager.setPageMargins(rowMargin, colMargin);
				return insets;
			}
		});

		List<List<Show>> shows = new ArrayList<List<Show>>();

		shows.add(new ArrayList<Show>());
		shows.add(new ArrayList<Show>());

		Show show = new Show();
		show.title = "Big bang theory";
		show.channel_name = "ITV";
		show.time = "NOW";
		show.image = "http://placekitten.com/200/300";

		shows.get(0).add(show);

		show.title = "Bigg boss";
		show.channel_name = "Colors";
		show.time = "9:00pm";
		show.image = "http://placekitten.com/300/287.jpg";

		shows.get(0).add(show);

		show.title = "Apprentice";
		show.channel_name = "BBC";
		show.time = "1:00pm";
		show.image = "http://placekitten.com/300/100.jpg";

		shows.get(1).add(show);

		pager.setAdapter(new ShowsGridPagerAdapter(this, getFragmentManager(), shows));
	}
}
