package com.cook.opengl;

public class SimplePlane extends Mesh {
	public SimplePlane() {
		// TODO Auto-generated constructor stub
		this(1.0f, 1.0f);
	}

	public SimplePlane(float width, float height) {
		float textureCoordinates[] = {
				0.0f, 4.0f,
				4.0f, 4.0f,
				0.0f, 0.0f,
				4.0f, 0.0f,
		};
		
		short[] indices = {
				0, 1, 2,
				1, 3, 2
		};
		
		float[] vertices = { 
				 -0.5f, -0.5f, 0.0f,
				  0.5f, -0.5f, 0.0f,
				  -0.5f,  0.5f, 0.0f,
				  0.5f, 0.5f, 0.0f 
		};

		setIndices(indices);
		setTextureCoordinates(textureCoordinates);
		setVertices(vertices);
	}
}
