package com.cook.opengl;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Group extends Mesh{
	private Vector<Mesh> children = new Vector<Mesh>();
	public Group() {
		
	}

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub
		int size = children.size();
		for( int i=0; i<size; i++ ) {
			children.get(i).draw(gl);
		}
	}

	public void add(int pos, Mesh obj) {
		children.add(pos, obj);
	}
	
	public void add(Mesh obj) {
		children.add(obj);
	}
	
	public void clear() {
		children.clear();
	}
	
	public Mesh get(int pos) {
		return children.get(pos);
	}
	
	public Mesh remove(int pos) {
		return children.remove(pos);
	}
	
	public int size() {
		return children.size();
	}
}
