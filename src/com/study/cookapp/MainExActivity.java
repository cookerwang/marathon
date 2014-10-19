package com.study.cookapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.monster.appstudy.R;
import com.study.fragment.FragmentExActivity;

public class MainExActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_ex);
	}

	public void onClickButton(View v) {
		// TODO Auto-generated method stub
		switch( v.getId() ) {
		case R.id.fragment_ex:
			showFragmentEx();
			break;
		}
	}
	
	public void showFragmentEx() {
		Intent intent = new Intent(this, FragmentExActivity.class);
		startActivity(intent);
	}
}
