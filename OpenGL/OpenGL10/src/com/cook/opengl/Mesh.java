package com.cook.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class Mesh {
	private FloatBuffer vertextBuffer = null;
	private ShortBuffer indiceBuffer = null;
	private FloatBuffer colorBuffer = null;
	
	private int numOfIndices = -1;
	
	private float[] rgba = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float rx = 0;
	public float ry = 0;
	public float rz = 0;
	
	private int mTextureId = -1;
	private Bitmap mBitmap = null;
	private boolean mShouldLoadTexture = false;
	private FloatBuffer textrueBuffer = null;
	
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertextBuffer);
		gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
		
		if( colorBuffer != null ) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		}
		
		if( mShouldLoadTexture ) {
			loadTexture(gl);
			mShouldLoadTexture = false;
		}
		if( mTextureId != -1 && textrueBuffer != null ) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textrueBuffer);
			//gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		}
		
		gl.glTranslatef(x, y, z);
		
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indiceBuffer);
		if( colorBuffer != null ) {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
		if( mTextureId != -1 && textrueBuffer != null ) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	
	private void loadTexture(GL10 gl) {
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		mTextureId = textureIds[0];
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
	}
	
	public void loadBitmap(Bitmap bitmap) {
		this.mBitmap = bitmap;
		mShouldLoadTexture = true;
	}
	
	protected void setTextureCoordinates(float[] textureCoords) {
		ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		textrueBuffer = tbb.asFloatBuffer();
		textrueBuffer.put(textureCoords);
		textrueBuffer.position(0);
	}
	
	protected void setVertices(float[] vertices) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertextBuffer = vbb.asFloatBuffer();
		vertextBuffer.put(vertices);
		vertextBuffer.position(0);
	}
	
	protected void setIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indiceBuffer = ibb.asShortBuffer();
		indiceBuffer.put(indices);
		indiceBuffer.position(0);
		numOfIndices = indices.length;
	}
	
	protected void setColors(float[] colors) {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}
	
	protected void setColor(float r, float g, float b, float a) {
		rgba[0] = r;
		rgba[1] = g;
		rgba[2] = b;
		rgba[3] = a;
	}
}
