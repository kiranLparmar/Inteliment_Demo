package com.inteliment.kiran.scenario2.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkOperations {

	private static final String TAG = "NetworkOperations";
	
	private static final String SERVER_URL = "http://express-it.optusnet.com.au/sample.json";

	/**
	 * Singleton instance of NetworkOperations class
	 */
	private static NetworkOperations sInstance;
	
	private Context mContext;
	
	/**
	 * ConnectivityManager instance to check if network-connectivity is available
	 */
	private ConnectivityManager mConnectivityManager;
	
	private OkHttpClient mClient = new OkHttpClient();

	private NetworkOperations() {
	}

	/**
	 * Used to get instance of this class
	 * 
	 * @return instance of NetworkOperations
	 */
	public static synchronized NetworkOperations getInstance() {
		if (sInstance == null) {
			sInstance = new NetworkOperations();
		}
		return sInstance;
	}
	
	public void setContext(Context context) {
		mContext = context;
	}

	public boolean isNetworkAvailable() {
		if (mConnectivityManager == null) {
			mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		boolean connected = (mConnectivityManager.getActiveNetworkInfo() != null &&
				mConnectivityManager.getActiveNetworkInfo().isAvailable() && 
				mConnectivityManager.getActiveNetworkInfo().isConnected());
		if (!connected) {
			Log.e(TAG, "Network Unavailable!");
			return false;
		}
		return true;
	}
	
	public void fetchData(Callback callback) {
		Request request = new Request.Builder().url(SERVER_URL).build();
		// Asynchronous call
		mClient.newCall(request).enqueue(callback);
	}
	
	public List<Place> parseJson(String responseData) {
		List<Place> places = new ArrayList<Place>();

		// Parse the data
		try {
			JSONArray jsonArray = new JSONArray(responseData);
			
			// looping through All Entries
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                
                Place place = new Place();
                StringBuilder fromCentral = new StringBuilder("Mode of Transport:\n\n");
                Location location = new Location();

                place.setId(json.getInt(Place.TAG_ID));
                place.setName(json.getString(Place.TAG_NAME));

                // From-central node is JSON Object
                JSONObject fromCentralJson = json.getJSONObject(Place.TAG_FROM_CENTRAL);
                String mode = fromCentralJson.optString(Place.TAG_CAR, null);
                if (mode != null) {
                	fromCentral.append("Car - ").append(mode).append("\n");
                }
                mode = fromCentralJson.optString(Place.TAG_TRAIN, null);
                if (mode != null) {
                	fromCentral.append("Train - ").append(mode).append("\n");
                }
                place.setFromcentral(fromCentral.toString());
                
                // Location node is JSON Object
                JSONObject locationJson = json.getJSONObject(Place.TAG_LOCATION);
                location.setLatitude(locationJson.getDouble(Place.TAG_LATITUDE));
                location.setLongitude(locationJson.getDouble(Place.TAG_LONGITUDE));
                place.setLocation(location);

                // add this place to our list
                places.add(place);
            }
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return places;
	}
}
