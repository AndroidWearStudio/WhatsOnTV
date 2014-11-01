package com.example.jagdeep.wear1.activity;

import android.app.*;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jagdeep.wear1.R;
import com.example.jagdeep.wear1.adapter.ShowAdapter;
import com.example.jagdeep.wear1.model.tv.URLType;
import com.example.jagdeep.wear1.model.tv.server.Shows;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonNetwork;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import java.util.Locale;


public class MainActivity extends Activity implements ActionBar.TabListener {

	private static final String TAG = "WHATSONTV";
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this becomes too
	 * memory intensive, it may be best to switch to a {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private static final String URL = "https://connector-i.zeebox" +
			".com/connector/2/me/tvpicks/2/en/uk/p3:r1/Europe/London/{here}/all/narrow?access_token" +
			"=_dHeb9Vj4Xd4nci3dVydi6Lzvv7oMgALCCvU-hN3lRY";

	private static final String URL_NOW = URL.replace("{here}", "now");

	private static final String URL_TONIGHT = URL.replace("{here}", "tonight");


	private static final String URL_WEEK = URL.replace("{here}", "week");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
							.setText(mSectionsPagerAdapter.getPageTitle(i))
							.setTabListener(this));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_refresh) {
			int val = mViewPager.getCurrentItem();
			recreate();
			mViewPager.setCurrentItem(val);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		private RecyclerView mRecyclerView;
		private View mProgress;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

			mProgress = rootView.findViewById(R.id.progress);

			// use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			mRecyclerView.setHasFixedSize(true);

			// use a linear layout manager
			LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
			mLayoutManager.setOrientation(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
					? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
			mRecyclerView.setLayoutManager(mLayoutManager);
			initialise(getArguments().getInt(ARG_SECTION_NUMBER));

			return rootView;
		}

		private void initialise(int anInt) {
			jacksonRequestQueue = JacksonNetwork.newRequestQueue(getActivity());
			volleyRequestQueue = Volley.newRequestQueue(getActivity());

			imageLoader = new ImageLoader(volleyRequestQueue, new ImageLoader.ImageCache() {
				private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

				public void putBitmap(String url, Bitmap bitmap) {
					mCache.put(url, bitmap);
				}

				public Bitmap getBitmap(String url) {
					return mCache.get(url);
				}
			});

			switch (anInt) {
			case 1:
				doRequest(URLType.NOW);
				break;
			case 2:
				doRequest(URLType.TONIGHT);
				break;
			case 3:
				doRequest(URLType.WEEK);
				break;
			}
		}

		private RequestQueue jacksonRequestQueue;
		private RequestQueue volleyRequestQueue;
		private ImageLoader imageLoader;

		private void doRequest(URLType urlType) {
			Log.v(TAG, "requested:" + urlType);
			String url = "";
			switch (urlType) {
			case NOW:
				url = URL_NOW;
				break;
			case TONIGHT:
				url = URL_TONIGHT;
				break;
			case WEEK:
				url = URL_WEEK;
				break;
			}
			mProgress.setVisibility(View.VISIBLE);
			jacksonRequestQueue.add(new JacksonRequest<Shows>(Request.Method.GET, url, programmeListener));
		}

		private JacksonRequestListener<Shows> programmeListener = new JacksonRequestListener<Shows>() {
			@Override
			public void onResponse(Shows response, int statusCode, VolleyError error) {

				if (response != null && response.getShows() != null) {
					Log.v(TAG, "response:" + response.getId());
					// specify an adapter (see also next example)
					ShowAdapter mAdapter = new ShowAdapter(response.getShows(), imageLoader);
					mRecyclerView.setAdapter(mAdapter);
					mProgress.setVisibility(View.GONE);
				}
			}

			@Override
			public JavaType getReturnType() {
				return SimpleType.construct(Shows.class);
			}
		};
	}

}
