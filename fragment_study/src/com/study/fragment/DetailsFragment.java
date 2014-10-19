package com.study.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	
	public DetailsFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static DetailsFragment newInstance(int index) {
		DetailsFragment details = new DetailsFragment();
		Bundle args = new Bundle();
		args.putInt(TitlesFragment.KEY_INDEX, index);
		details.setArguments(args);
		return details;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container,
		// savedInstanceState);
		if (container == null) {
//			TextView tv = new TextView(getActivity());
//			tv.setText("还没选择一个标题哦");
//			return tv;
			return null;
		}

		ScrollView scrooler = new ScrollView(getActivity());
		TextView tv = new TextView(getActivity());
		int padding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
						.getResources().getDisplayMetrics());
		tv.setPadding(padding, padding, padding, padding);
		scrooler.addView(tv);
		tv.setText(TitlesFragment.titlesArray[getShownIndex()]);
		return scrooler;
	}

	public int getShownIndex() {
		return getArguments().getInt(TitlesFragment.KEY_INDEX, 0);
	}
}