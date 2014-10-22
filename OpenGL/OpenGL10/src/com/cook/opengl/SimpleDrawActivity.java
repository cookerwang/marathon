package com.cook.opengl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.opengl.R;

public class SimpleDrawActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GLSurfaceView view = new GLSurfaceView(this);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.texture);
		
		view.setRenderer(new ThreeDGL(bitmap));
		setContentView(view);
	}

}
