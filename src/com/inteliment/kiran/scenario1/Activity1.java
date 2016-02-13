package com.inteliment.kiran.scenario1;

import com.inteliment.kiran.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Activity1 extends Activity implements OnCheckedChangeListener {

	/**
	 * The custom FragmentPagerAdapter implementation that will provide fragments
	 */
	private ViewPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	
	private static final int TAB_COUNT = 5;
	
	/**
	 * The TextView that shows the selected tab/item from the ActionBar-Tabs
	 */
	private TextView mTabTextView;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		
		// Top-section consisting of 5-tab-buttons
		mTabTextView = (TextView) findViewById(R.id.tabTextView);
		mTabTextView.setText("");
		
		((RadioGroup)findViewById(R.id.radioGroup)).setOnCheckedChangeListener(this);
		((RadioButton)findViewById(R.id.radio1)).setChecked(true);
		setTabWidth();
		
		// -------
		// Middle section -ViewPager + Fragments
		// Create the adapter that will return a fragment for the 4 sections
		mSectionsPagerAdapter = new ViewPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				Toast.makeText(Activity1.this, "Fragment " + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setTabWidth() {
		int width = (int) (getResources().getDisplayMetrics().widthPixels / 3f);
		RadioGroup view = (RadioGroup) findViewById(R.id.radioGroup);
		for (int i = 0; i < TAB_COUNT; i++) {
			View tab = view.getChildAt(i);
			tab.getLayoutParams().width = width;
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton button = (RadioButton) group.findViewById(group.getCheckedRadioButtonId());
		mTabTextView.setText(button.getText());
	}

	public void onRadioButtonClicked(View view) {
		((RadioGroup)view.getParent()).check(view.getId());
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setTabWidth();
	}

	public void onColorButtonClicked(View view) {
		switch (view.getId()) {
		case R.id.redBtn:
			findViewById(R.id.buttons).setBackgroundColor(getResources().getColor(R.color.redBtn));
			break;
		case R.id.blueBtn:
			findViewById(R.id.buttons).setBackgroundColor(getResources().getColor(R.color.blueBtn));
			break;
		case R.id.greenBtn:
			findViewById(R.id.buttons).setBackgroundColor(getResources().getColor(R.color.greenBtn));
			break;
		}
	}
}
