package com.inteliment.kiran.scenario2;

import java.io.IOException;
import java.util.List;

import com.inteliment.kiran.R;
import com.inteliment.kiran.scenario2.network.Location;
import com.inteliment.kiran.scenario2.network.NetworkOperations;
import com.inteliment.kiran.scenario2.network.Place;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Activity2 extends Activity implements Callback {
	
	/**
	 * The spinner indicating the place(s)
	 */
	private Spinner mSpinner;
	
	/**
	 * Button that launches the Maps to show the selected place in spinner
	 */
	private Button mNavigateBtn;
	
	private TextView mText;
	private View mContent;
	private View mProgressIndicator;

	/**
	 * List of places obtained from the JSON-data
	 */
	private List<Place> places;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Init NetworkOperations
		NetworkOperations.getInstance().setContext(this);
		setContentView(R.layout.activity2);
		
		// Initailize Views
		mSpinner = (Spinner) findViewById(R.id.spinner);
		mNavigateBtn = (Button) findViewById(R.id.navigateBtn);
		mText = (TextView) findViewById(R.id.text);
		mContent = findViewById(R.id.content);
		mProgressIndicator = findViewById(R.id.progress);
		
		// Set Listeners
		mSpinner.setOnItemSelectedListener(mListener);
		mNavigateBtn.setOnClickListener(mNavigateListener);
		
		// Load the data
		loadData();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	private void loadData() {
		// Show the progress-indicator
		setProgressBarVisibility(View.VISIBLE);
		NetworkOperations.getInstance().fetchData(this);
	}
	
	public void setProgressBarVisibility(int visibility) {
		if (mProgressIndicator == null) {
			mProgressIndicator = findViewById(R.id.progress);
		}

		if (mContent == null) {
			mContent = findViewById(R.id.content);
		}

		if (visibility == View.VISIBLE) {
			mProgressIndicator.setVisibility(View.VISIBLE);
			mContent.setVisibility(View.GONE);
		} else {
			mProgressIndicator.setVisibility(View.GONE);
			mContent.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Called when the request could not be executed due to cancellation, a
	 * connectivity problem or timeout. Because networks can fail during an
	 * exchange, it is possible that the remote server accepted the request
	 * before the failure
	 */
	@Override
	public void onFailure(Call call, IOException throwable) {
		final String message = throwable.getMessage();

		// Run view-related code back on the main thread
        Activity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	setProgressBarVisibility(View.GONE);
        		Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
        		mText.setText("Unexpected error: " + message);
            }
        });
	}

	/**
	 * Called when the HTTP response was successfully returned by the remote
	 * server. The callback may proceed to read the response body with
	 * Response.body.
	 */
	@Override
	public void onResponse(Call call, final Response response) throws IOException {
		final String responseData = response.body().string();

		if (response.isSuccessful() && response.code() == 200) {
			places = NetworkOperations.getInstance().parseJson(responseData);

			// Run view-related code back on the main thread
	        Activity2.this.runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	            	setProgressBarVisibility(View.GONE);
	            	
	        		ArrayAdapter<Place> dataAdapter = new ArrayAdapter<Place>(Activity2.this, android.R.layout.simple_spinner_item, places);
	        		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		mSpinner.setAdapter(dataAdapter);
	            }
	        });
		} else {
			// Run view-related code back on the main thread
	        Activity2.this.runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	            	setProgressBarVisibility(View.GONE);
        			mText.setText("Unexpected error: " + response);
	            }
	        });
		}
	}
	
	private OnItemSelectedListener mListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			if (mSpinner != null) {
				Place place = (Place) mSpinner.getAdapter().getItem(position);
				mText.setText(place.getFromcentral());
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			mText.setText("");
		}
	};
	
	private OnClickListener mNavigateListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (mSpinner != null && mSpinner.getSelectedItem() != null) {
				Place place = (Place) mSpinner.getSelectedItem();
				Location location = place.getLocation();

				// Format: geo:latitude,longitude
				Uri mapsIntentUri = Uri.parse("geo:<" + location.getLatitude() + ">,<" + location.getLongitude()
						+ ">?q=<" + location.getLatitude() + ">,<" + location.getLongitude() + ">(" + place.getName()
						+ ")");

				Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapsIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");
				if (mapIntent.resolveActivity(getPackageManager()) != null) {
				    startActivity(mapIntent);
				} else {
					// Probably Maps app isn't present on the device
					Toast.makeText(Activity2.this, "Google Maps not found!", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(Activity2.this, "No data available!", Toast.LENGTH_LONG).show();
			}
		}
	};
	
}
