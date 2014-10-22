package com.example.opengl20;

import android.opengl.GLES20;

public class ShaderUtil {
	public static int loadShader(int type, String shaderCode){
	    // ����һ��vertex shader����(GLES20.GL_VERTEX_SHADER)
	    // ��һ��fragment shader����(GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);
	    // ��Դ����ӵ�shader��������
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);
	    return shader;
	}

}
