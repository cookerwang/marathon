package com.cook.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;

import com.example.opengl.R.id;

public class ThreeDGL implements Renderer {

	float rotateTri = 0.0f, rotateQuad = 0.0f;
	int one = 65535;
	
	//private IntBuffer triggerBuffer =  IntBuffer.wrap();
	private int triggerArr[] = new int[]{
			0, one, 0,
			 -one, -one, 0,
			 one, -one, 0 };
	//private IntBuffer quateBuffer = IntBuffer.wrap();
	private int quateArr[] = new int[] {
			 -one,one,0,
			one,one,0,
            -one,-one,0,
            one,-one,0,
    };
	
	//private IntBuffer colorBuffer = IntBuffer.wrap();
	private int[] colorArr = new int[]{
            one,0,0,one,
            0,one,0,one,
            0,0,one,one};
	
	private IntBuffer triggerBuffer;
	private IntBuffer quateBuffer;
	private IntBuffer colorBuffer;
	
	private Square square;
	
	private Cube cube;
	private SimplePlane simplePlane;
	
	public ThreeDGL(Bitmap bitmap) {
		triggerBuffer = bufferUtil(triggerArr);
		quateBuffer = bufferUtil(quateArr);
		colorBuffer = bufferUtil(colorArr);
		
		square = new Square();
		cube = new Cube(-1, 1, -4);
		simplePlane = new SimplePlane();
		simplePlane.loadBitmap(bitmap);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		gl.glPushMatrix();
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glFinish();
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslatef(1.5f, 0, -6.0f);
		
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotateQuad, 1.0f, 0.0f, 0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, quateBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glColor4f(0.0f, 1.0f, 0.0f, 0.2f);
		gl.glTranslatef(0.0f, 1.5f, -6.0f);
		square.draw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		cube.rx = rotateQuad;
		cube.draw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, -0.5f, -6.0f);
		simplePlane.draw(gl);
		gl.glPopMatrix();
		
		rotateQuad += 0.5f;
		rotateTri -= 0.5f;
		
	}

	
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		float ratio = (float) width / (float) height;
				
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0, 0, 1.0f, 0);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		
	}

	public IntBuffer bufferUtil(int[] arr) {
		IntBuffer buffer;
		
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		qbb.order(ByteOrder.nativeOrder());
		
		buffer = qbb.asIntBuffer();
		buffer.put(arr);
		buffer.position(0);
		return buffer;
	}
	public FloatBuffer bufferUtil(float []arr){  
        FloatBuffer buffer;
 
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
 
        buffer = qbb.asFloatBuffer();
        buffer.put(arr);
        buffer.position(0);
 
        return buffer;
   }
}

class Square {
	private float[] vertices = new float[] {
		-1.0f, 1.0f, 0.0f,
		-1.0f, -1.0f, 0.0f,
		1.0f, -1.0f, 0.0f,
		1.0f, 1.0f, 0.0f
	};
	private short[] indices = new short[] {
			0, 1, 2, 
			0, 2, 3
	};
	private float[] colors = new float[] {
			1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 0.5f, 1.0f
	};
	
	private FloatBuffer vertexBuffer;
	private ShortBuffer indiceBuffer;
	private FloatBuffer colorBuffer;
	
	public Square() {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indiceBuffer = ibb.asShortBuffer();
		indiceBuffer.put(indices);
		indiceBuffer.position(0);
		
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glColorPointer(colors.length, GL10.GL_FLAT, 0, colorBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indiceBuffer);
		
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
