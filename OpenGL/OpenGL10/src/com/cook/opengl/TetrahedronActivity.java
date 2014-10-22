package com.cook.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.Window;

public class TetrahedronActivity extends Activity implements
		TRenderer.IRenderer {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		init();

		GLSurfaceView view = new GLSurfaceView(this);
		view.setRenderer(new TRenderer(this));
		setContentView(view);
	}

	private FloatBuffer vertextBuffer = null;
	private FloatBuffer colorBuffer = null;
	private ShortBuffer indiceBuffer = null;

	private void init() {
		float[] vertices = new float[] { 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, -2.0f,
				-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f };
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertextBuffer = vbb.asFloatBuffer();
		vertextBuffer.put(vertices);
		vertextBuffer.position(0);

		short[] indices = new short[] { 0, 1, 2, 0, 2, 3, 3, 2, 1, 0, 3, 1 };
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indiceBuffer = ibb.asShortBuffer();
		indiceBuffer.put(indices);
		indiceBuffer.position(0);

		float[] colors = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, };
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);

		ball = new Ball(20000, 2);
		sphere = new Sphere();
	}

	private Sphere sphere;
	private Ball ball;
	private float rx, ry, rz;

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glPushMatrix();

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 3.0f, -18.0f);
		gl.glRotatef(rx, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(ry, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rz, 0.0f, 0.0f, 1.0f);
		rx += 0.5f;
		ry += 0.7f;
		rz -= 0.5f;

		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertextBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, 12, GL10.GL_UNSIGNED_SHORT,
				indiceBuffer);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);

		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -18.0f);
		ball.angX = -rx;
		ball.angY = -ry;
		ball.angZ = -rz;
		ball.drawSelf(gl);
		gl.glPopMatrix();

		gl.glPopMatrix();

		// gl.glPushMatrix();
		// gl.glLoadIdentity();
		// gl.glTranslatef(0.0f, 0.0f, -15.0f);
		// sphere.draw(gl);
		// gl.glPopMatrix();
	}
}

class TRenderer implements GLSurfaceView.Renderer {
	public interface IRenderer {
		public void onDrawFrame(GL10 gl);
	}

	private IRenderer mIRenderer = null;

	public TRenderer(IRenderer render) {
		mIRenderer = render;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		if (mIRenderer != null) {
			mIRenderer.onDrawFrame(gl);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glClearDepthf(1.0f);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		initScene(gl);
	}

	public void initScene(GL10 gl) {
		// 环境光
		float[] amb = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		// 漫反射
		float[] diff = new float[] { 0.5f, 0.5f, 0.5f, 0.5f };
		// 镜面反射
		float[] spec = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

		float[] pos = new float[] { -5.0f, 5.0f, -5.0f, 1.0f };
		float[] spot_dir = { 0.0f, -1.0f, -1.0f };

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);

		ByteBuffer abb = ByteBuffer.allocateDirect(amb.length * 4);
		abb.order(ByteOrder.nativeOrder());
		FloatBuffer ambBuf = abb.asFloatBuffer();
		ambBuf.put(amb);
		ambBuf.position(0);

		ByteBuffer dbb = ByteBuffer.allocateDirect(diff.length * 4);
		dbb.order(ByteOrder.nativeOrder());
		FloatBuffer diffBuf = dbb.asFloatBuffer();
		diffBuf.put(diff);
		diffBuf.position(0);

		ByteBuffer sbb = ByteBuffer.allocateDirect(spec.length * 4);
		sbb.order(ByteOrder.nativeOrder());
		FloatBuffer specBuf = sbb.asFloatBuffer();
		specBuf.put(spec);
		specBuf.position(0);

		ByteBuffer pbb = ByteBuffer.allocateDirect(pos.length * 4);
		pbb.order(ByteOrder.nativeOrder());
		FloatBuffer posBuf = pbb.asFloatBuffer();
		posBuf.put(pos);
		posBuf.position(0);

		ByteBuffer spbb = ByteBuffer.allocateDirect(spot_dir.length * 4);
		spbb.order(ByteOrder.nativeOrder());
		FloatBuffer spot_dirBuf = spbb.asFloatBuffer();
		spot_dirBuf.put(spot_dir);
		spot_dirBuf.position(0);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specBuf);
		// 聚光灯方向
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, spot_dirBuf);
		// 位置，定位灯
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, posBuf);
		
		// 聚光能力
		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_EXPONENT, 0.0f);
		// 0-90，发散角度
		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 90.0F);
		
		gl.glLoadIdentity();
//		GLU.gluLookAt(gl, 4.0f, 4.0f, 0.0f, 
//				0.0f, 0.0f, 0.0f,
//				0.0f, 1.0f, 0.0f);
	}

}