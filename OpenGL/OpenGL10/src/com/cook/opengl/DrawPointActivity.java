package com.cook.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;

public class DrawPointActivity extends OpenGLESActivity {

	private float vertices[] = {
			-0.5f, 0.5f, -6.0f,
			1.0f, 0.5f, -6.0f,
			-1.0f, -0.5f, -10.0f,
			1.0f, -1.0f, -2.0f};
	private FloatBuffer vertexBuffer = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initVertexBuffer();
	}

	private void initVertexBuffer() {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		super.onDrawFrame(gl);
		gl.glLoadIdentity();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -10.f);
		gl.glClearColor(0.0f, 1.0f, 1.0f, 0.5f);
		
		gl.glPointSize(20.0f);
		gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_POINTS, 0, 4);
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -20.0f);
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 3.0f, -10.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		
	}

}
