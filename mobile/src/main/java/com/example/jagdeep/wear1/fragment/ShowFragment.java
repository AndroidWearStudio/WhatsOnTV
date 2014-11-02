package com.example.jagdeep.wear1.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jagdeep.sharedlibrary.ShowsFetchService;
import com.example.jagdeep.sharedlibrary.model.tv.client.Show;
import com.example.jagdeep.wear1.R;
import com.example.jagdeep.wear1.adapter.ShowAdapter;

public class ShowFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private RecyclerView mRecyclerView;
	private View mProgress;

	ImageLoader imageLoader;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ShowFragment newInstance(int sectionNumber) {
		ShowFragment fragment = new ShowFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ShowFragment() {
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

		imageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), new ImageLoader.ImageCache() {
			int cacheSize = 20 * 1024 * 1024;
			private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(cacheSize);

			public void putBitmap(String url, Bitmap bitmap) {
				mCache.put(url, bitmap);
			}

			public Bitmap getBitmap(String url) {
				return mCache.get(url);
			}
		});

		doRequest(getArguments().getInt(ARG_SECTION_NUMBER));

		return rootView;
	}

	private void doRequest(int pageNumber) {
		mProgress.setVisibility(View.VISIBLE);

		ShowsFetchService.startActionFetchTvShows(getActivity(), pageNumber);

		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				ShowAdapter mAdapter = new ShowAdapter(intent.<Show>getParcelableArrayListExtra(ShowsFetchService
						.DATA_SHOWS), imageLoader);
				mRecyclerView.setAdapter(mAdapter);
				mProgress.setVisibility(View.GONE);
			}
		}, getIntentFilter(pageNumber));

	}

	IntentFilter getIntentFilter(int pageNumber) {
		String filter = "";
		switch (pageNumber) {
		case 1:
			filter = ShowsFetchService.BROADCAST_ACTION_SHOWS_NOW;
			break;
		case 2:
			filter = ShowsFetchService.BROADCAST_ACTION_SHOWS_TONIGHT;
			break;
		case 3:
			filter = ShowsFetchService.BROADCAST_ACTION_SHOWS_WEEK;
			break;
		}
		return new IntentFilter(filter);
	}


}
