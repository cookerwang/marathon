package com.example.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cook.opengl.DrawPointActivity;
import com.cook.opengl.SimpleDrawActivity;
import com.cook.opengl.TetrahedronActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	public void onClickButton(View v) {
		Intent intent = null;
		switch( v.getId() ) {
		case R.id.simple_draw:
			intent = new Intent(this, SimpleDrawActivity.class);
			break;
		case R.id.draw_points:
			intent = new Intent(this, DrawPointActivity.class);
			break;
		case R.id.tetrahedron:
			intent = new Intent(this, TetrahedronActivity.class);
			break;
		}
		if( intent != null ) {
			startActivity(intent);
		}
	}

}
