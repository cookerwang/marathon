package com.cook.opengl;

public class Plane extends Mesh {

	public Plane() {
		this(1, 1, 1, 1);
	}
	
	public Plane(float width, float height) {
		this(width, height, 1, 1);
	}
	
	public Plane(float width, float height, int widthSegments, int heightSegments) {
		float[] vertices = new float[(widthSegments + 1) * (heightSegments + 1) * 3];
		short[] indices = new short[(widthSegments + 1) * (heightSegments + 1) * 6];
		
		float xOffset = width / -2;
		float yOffset = height / -2;
		float xWidth = width / widthSegments;
		float yHeight = height / heightSegments;
		
		int curVertex = 0;
		int curIndex = 0;
		
		short w = (short) (widthSegments + 1);
		for( int y=0; y<heightSegments+1; y++) {
			for( int x=0; x<widthSegments+1; x++) {
				vertices[curVertex] = xOffset + x * xWidth;
				vertices[curVertex + 1] = yOffset + y * yHeight;
				vertices[curVertex + 2] = 0;
				curVertex += 3;
				
				// 画图理解一下
				int n = y * (widthSegments + 1) + x;
				if( y < heightSegments && x < widthSegments ) {
					indices[curIndex] = (short) n;
					indices[curIndex + 1] = (short) (n + 1);
					indices[curIndex + 2] = (short) (n + w);
					
					indices[curIndex] = (short) (n + 1);
					indices[curIndex + 1] = (short) (n + 1 + w);
					indices[curIndex + 2] = (short) (n + 1 + w -1);
					curIndex += 6;
				}
			}
		}
		setVertices(vertices);
		setIndices(indices);
	}

	
}
