package com.example.opengl20;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {

	public MyGLSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setEGLContextClientVersion(2);
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	
}
