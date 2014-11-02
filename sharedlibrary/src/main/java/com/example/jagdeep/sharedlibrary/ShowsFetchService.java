package com.example.jagdeep.sharedlibrary;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.jagdeep.sharedlibrary.model.tv.URLType;
import com.example.jagdeep.sharedlibrary.model.tv.server.Shows;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonNetwork;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static helper methods.
 */
public class ShowsFetchService extends IntentService {
	private static final String ACTION_FETCH_SHOWS = "com.example.jagdeep.sharedlibrary.action.shows";
	private static final String ACTION_FETCH_IMAGE = "com.example.jagdeep.sharedlibrary.action.image";

	private static final String URL = "https://connector-i.zeebox" +
			".com/connector/2/me/tvpicks/2/en/uk/p3:r1/Europe/London/{here}/all/narrow?access_token" +
			"=_dHeb9Vj4Xd4nci3dVydi6Lzvv7oMgALCCvU-hN3lRY";

	private static final String URL_NOW = URL.replace("{here}", "now");

	private static final String URL_TONIGHT = URL.replace("{here}", "tonight");


	private static final String URL_WEEK = URL.replace("{here}", "week");

	private static final String TAG = "WHATSONTV";

	private static final String PARAM_TYPE = "type";
	private static final String PARAM_URL = "url";

	public static final String BROADCAST_ACTION_SHOWS_NOW = "com.example.jagdeep.sharedlibrary.BROADCAST.shows.now";
	public static final String BROADCAST_ACTION_SHOWS_TONIGHT = "com.example.jagdeep.sharedlibrary.BROADCAST.shows" +
			".tonight";
	public static final String BROADCAST_ACTION_SHOWS_WEEK = "com.example.jagdeep.sharedlibrary.BROADCAST.shows.week";
	//	public static final String BROADCAST_ACTION_IMAGE = "com.example.jagdeep.sharedlibrary.BROADCAST.image";
	public static final String DATA_SHOWS = "com.example.jagdeep.sharedlibrary.data.shows";
	public static final String DATA_IMAGE = "com.example.jagdeep.sharedlibrary.data.image";

	private LocalBroadcastManager localBroadcastManager;

	public static void startActionFetchTvShows(Context context, int type) {
		Intent intent = new Intent(context, ShowsFetchService.class);
		intent.setAction(ACTION_FETCH_SHOWS);
		intent.putExtra(PARAM_TYPE, type);
		context.startService(intent);
	}

	public static void startActionFetchImage(Context context, String url) {
		Intent intent = new Intent(context, ShowsFetchService.class);
		intent.setAction(ACTION_FETCH_IMAGE);
		intent.putExtra(PARAM_URL, url);
		context.startService(intent);
	}

	public ShowsFetchService() {
		super("ShowsFetchService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initialise();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent != null) {
			final String action = intent.getAction();
			if (ACTION_FETCH_SHOWS.equals(action)) {
				handleActionShows(intent.getIntExtra(PARAM_TYPE, 0));
			}
			//			else if (ACTION_FETCH_IMAGE.equals(action)) {
			//				handleActionImage(intent.getStringExtra(PARAM_URL));
			//			}
		}
	}


	private void handleActionShows(int type) {
		switch (type) {
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

	//	private void handleActionImage(String url) {
	//
	//		volleyRequestQueue.add(new ImageRequest(url, new Response.Listener<Bitmap>() {
	//			@Override
	//			public void onResponse(Bitmap response) {
	//				if (response != null) {
	//					sendImageInIntent(response);
	//				}
	//			}
	//		}, 0, 0, null, new Response.ErrorListener() {
	//			@Override
	//			public void onErrorResponse(VolleyError error) {
	//				sendImageInIntent(null);
	//			}
	//		}));
	//	}
	//
	//	private void sendImageInIntent(Bitmap bitmap) {
	//		Intent localIntent = new Intent(BROADCAST_ACTION_IMAGE).putExtra(DATA_IMAGE, bitmap);
	//		// Broadcasts the Intent to receivers in this app
	//		localBroadcastManager.sendBroadcast(localIntent);
	//	}

	private void initialise() {
		jacksonRequestQueue = JacksonNetwork.newRequestQueue(this);
		volleyRequestQueue = Volley.newRequestQueue(this);
		localBroadcastManager = LocalBroadcastManager.getInstance(this);

	}

	private RequestQueue jacksonRequestQueue;
	RequestQueue volleyRequestQueue;

	private void doRequest(final URLType urlType) {
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
		jacksonRequestQueue.add(new JacksonRequest<Shows>(Request.Method.GET, url, new JacksonRequestListener<Shows>() {
			@Override
			public void onResponse(Shows response, int statusCode, VolleyError error) {

				if (response != null && response.getShows() != null) {
					Log.v(TAG, "response:" + response.getId());

					//send data out

					Intent localIntent = new Intent(getFilter(urlType)).putParcelableArrayListExtra(DATA_SHOWS,
							response.getShows());
					localBroadcastManager.sendBroadcast(localIntent);
				}
			}

			@Override
			public JavaType getReturnType() {
				return SimpleType.construct(Shows.class);
			}
		}));


	}

	String getFilter(URLType urlType) {
		switch (urlType) {
		case NOW:
			return BROADCAST_ACTION_SHOWS_NOW;
		case TONIGHT:
			return BROADCAST_ACTION_SHOWS_TONIGHT;
		case WEEK:
			return BROADCAST_ACTION_SHOWS_WEEK;
		}
		return null;
	}
}
