package com.inteliment.kiran.scenario1;

import com.inteliment.kiran.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment containing a simple text-view.
 */
public class SampleFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_FRAGMENT_NUMBER = "fragment_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static SampleFragment newInstance(int fragmentNumber) {
		SampleFragment fragment = new SampleFragment();
		Bundle args = new Bundle();
		args.putString(ARG_FRAGMENT_NUMBER, "Fragment " + fragmentNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public SampleFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activity1, container, false);
		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		textView.setText(getArguments().getString(ARG_FRAGMENT_NUMBER));
		
		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Clicked " + getArguments().getString(ARG_FRAGMENT_NUMBER), Toast.LENGTH_SHORT).show();
			}
		});
		
		return rootView;
	}
	
}
