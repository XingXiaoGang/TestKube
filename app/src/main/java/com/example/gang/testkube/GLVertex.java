package com.example.gang.testkube;

import java.nio.IntBuffer;

public class GLVertex {
    GLColor color;
    final short index;
    public float x;
    public float y;
    public float z;

    GLVertex() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.index = (short) -1;
    }

    GLVertex(float x, float y, float z, int index) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.index = (short) index;
    }

    public boolean equals(Object other) {
        boolean z = false;
        if (!(other instanceof GLVertex)) {
            return false;
        }
        GLVertex v = (GLVertex) other;
        if (this.x == v.x && this.y == v.y && this.z == v.z) {
            z = true;
        }
        return z;
    }

    public static int toFixed(float x) {
        return (int) (65536.0f * x);
    }

    public void put(IntBuffer vertexBuffer, IntBuffer colorBuffer) {
        vertexBuffer.put(toFixed(this.x));
        vertexBuffer.put(toFixed(this.y));
        vertexBuffer.put(toFixed(this.z));
        if (this.color == null) {
            colorBuffer.put(0);
            colorBuffer.put(0);
            colorBuffer.put(0);
            colorBuffer.put(0);
            return;
        }
        colorBuffer.put(this.color.red);
        colorBuffer.put(this.color.green);
        colorBuffer.put(this.color.blue);
        colorBuffer.put(this.color.alpha);
    }

    public void update(IntBuffer vertexBuffer, M4 transform) {
        vertexBuffer.position(this.index * 3);
        if (transform == null) {
            vertexBuffer.put(toFixed(this.x));
            vertexBuffer.put(toFixed(this.y));
            vertexBuffer.put(toFixed(this.z));
            return;
        }
        GLVertex temp = new GLVertex();
        transform.multiply(this, temp);
        vertexBuffer.put(toFixed(temp.x));
        vertexBuffer.put(toFixed(temp.y));
        vertexBuffer.put(toFixed(temp.z));
    }
}
