package com.study.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.monster.appstudy.R;

@SuppressLint("NewApi")
public class TitlesFragment extends ListFragment  {
	public static String[] titlesArray = new String[]{ "text1", "text2", "text3", "text4",
		"text5", "text6", "text7", "text8" };
	public static final String KEY_CUR_TITLE = "curChoice";
	public static final String KEY_INDEX = "index";
	
	private static final String TAG = "FragmentExActivity.TitlesFragment";
	boolean mDualPane;
	int mCurrentPosition = 0;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.i(TAG, "Fragment--> onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setHasOptionsMenu(true);
		Log.i(TAG, "Fragment--> onCreate");
		
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, titlesArray));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		Log.i(TAG, "Fragment--> onCreateOptionsMenu");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Fragment--> onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
		//return inflater.inflate(R.layout.listview_fragment_impl, container, false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "Fragment--> onStart");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "Fragment--> onResume");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "Fragment--> onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "Fragment--> onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "Fragment--> onDestroy");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.i(TAG, "Fragment--> onDetach");
	}

	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Log.i(TAG, "Fragment--> onViewCreated");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "Fragment--> onActivityCreated");

		
		
		View detailsFrame = getActivity().findViewById(R.id.article_details);

		mDualPane = detailsFrame != null
				&& detailsFrame.getVisibility() == View.VISIBLE;

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(KEY_CUR_TITLE, 0);
		} 

		if (mDualPane) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			showDetails(mCurrentPosition);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CUR_TITLE, mCurrentPosition);
	}

	void showDetails(int index) {
		mCurrentPosition = index;
		if (mDualPane) {
			getListView().setItemChecked(mCurrentPosition, true);
			DetailsFragment details = (DetailsFragment) getFragmentManager()
					.findFragmentById(R.id.article_details);
			if( details == null || index != details.getShownIndex() ) {
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				details = DetailsFragment.newInstance(index);
				ft.replace(R.id.article_details, details);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				int k = ft.commit();
				System.out.println("k: " + k);
			}
		} else {
			new AlertDialog.Builder(getActivity())
					.setTitle(android.R.string.dialog_alert_title)
					.setMessage(titlesArray[index])
					.setPositiveButton(android.R.string.ok, null).show();
		}
	}

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		showDetails(position);
	}


}