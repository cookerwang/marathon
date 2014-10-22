package com.cook.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ball {

	private IntBuffer vertexBuffer;
	private IntBuffer normalBuffer;
	private ShortBuffer indexBuffer;

	public float angX;
	public float angY;
	public float angZ;

	private int r;
	private int span;
	private int iCount;

	// private IntBuffer testBuffer;
	public Ball(int r, int span) {
		this.r = r;
		this.span = span;

		ArrayList<Integer> alVertex = new ArrayList<Integer>();
		final int vSpan = span;
		final int hSpan = span;

		for (int vAngle = -90; vAngle <= 90; vAngle += vSpan) {
			for (int hAngle = 0; hAngle < 360; hAngle += hSpan) {
				// xoz平面上投影点到原点的距离
				double xozLen = r * Math.cos(Math.toRadians(vAngle));
				int x = (int) (xozLen * Math.sin(Math.toRadians(hAngle)));
				int y = (int) (r * Math.sin(Math.toRadians(vAngle)));
				int z = (int) (xozLen * Math.cos(Math.toRadians(hAngle)));
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);
				System.out.println("vAn: " + vAngle);
			}
		}
		final int size = alVertex.size();

		System.out.println("size: " + size + ", realSize: " + (180 / vSpan + 1)
				* (360 / hSpan));
		// final int vCnt = size / 3;
		int[] vertices = new int[size];
		for (int i = 0; i < size; i++) {
			vertices[i] = alVertex.get(i);
		}
		System.out.println("vertices.size: " + vertices.length);
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asIntBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length * 4);
		nbb.order(ByteOrder.nativeOrder());
		normalBuffer = nbb.asIntBuffer();
		normalBuffer.put(vertices);
		normalBuffer.position(0);

		ArrayList<Integer> alIndex = new ArrayList<Integer>();
		int row = (180 / vSpan) + 1;
		int col = 360 / hSpan;
		for (int i = 1; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int k = i * col + j;
				if (j < col - 1) {
					alIndex.add(k);
					alIndex.add(k - col);
					alIndex.add(k + 1);

					alIndex.add(k - col);
					alIndex.add(k - col + 1);
					alIndex.add(k + 1);

				} else {
					alIndex.add(k);
					alIndex.add(k - col);
					alIndex.add(k - j);

					alIndex.add(k - col);
					alIndex.add(k - j);
					alIndex.add(k - j - col);
				}
			}
		}

		final int iCnt = alIndex.size();
		short indices[] = new short[iCnt];
		System.out.println("alIndex.size: " + iCnt);
		for (int i = 0; i < iCnt; i++) {
			indices[i] = alIndex.get(i).shortValue();
		}
		iCount = iCnt;
		ByteBuffer ibb = ByteBuffer.allocateDirect(iCnt * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);

		// int m = 65535;
		// int[] test = new int[] {
		// m, m, 0,
		// m, -m, 0,
		// -m, -m, 0,
		// -m, m, 0};
		// ByteBuffer tbb = ByteBuffer.allocateDirect(test.length * 4);
		// tbb.order(ByteOrder.nativeOrder());
		// testBuffer = tbb.asIntBuffer();
		// testBuffer.put(test);
		// testBuffer.position(0);
	}

	private float rgba[] = new float[] { 1.0f, 0.0f, 0.0f, 0.0f };

	public void drawSelf(GL10 gl) {
		gl.glRotatef(angX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(angZ, 0.0f, 0.0f, 1.0f);
		gl.glPointSize(10.0f);
		gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

		gl.glNormalPointer(GL10.GL_FIXED, 0, normalBuffer);

		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, iCount, GL10.GL_UNSIGNED_SHORT,
				indexBuffer);
		// gl.glDrawArrays(GL10.GL_POINTS, 0, 3 * 20);

		// gl.glVertexPointer(3, GL10.GL_FIXED, 0, testBuffer);
		// gl.glDrawArrays(GL10.GL_POINTS, 0, 4);

		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	}

}
