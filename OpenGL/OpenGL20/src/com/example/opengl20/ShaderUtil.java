package com.example.opengl20;

import android.opengl.GLES20;

public class ShaderUtil {
	public static int loadShader(int type, String shaderCode){
	    // 创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
	    // 或一个fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);
	    // 将源码添加到shader并编译它
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);
	    return shader;
	}

}
