package com.example.gang.testkube;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

public class GLWorld {
    int count;
    private IntBuffer mColorBuffer;
    private ShortBuffer mIndexBuffer;
    private int mIndexCount;
    private ArrayList<GLShape> mShapeList;
    private IntBuffer mVertexBuffer;
    private ArrayList<GLVertex> mVertexList;

    public GLWorld() {
        this.count = 0;
        this.mShapeList = new ArrayList();
        this.mVertexList = new ArrayList();
        this.mIndexCount = 0;
    }

    public void addShape(GLShape shape) {
        this.mShapeList.add(shape);
        this.mIndexCount += shape.getIndexCount();
    }

    public void generate() {
        ByteBuffer bb = ByteBuffer.allocateDirect((this.mVertexList.size() * 4) * 4);
        bb.order(ByteOrder.nativeOrder());
        this.mColorBuffer = bb.asIntBuffer();
        bb = ByteBuffer.allocateDirect((this.mVertexList.size() * 4) * 3);
        bb.order(ByteOrder.nativeOrder());
        this.mVertexBuffer = bb.asIntBuffer();
        bb = ByteBuffer.allocateDirect(this.mIndexCount * 2);
        bb.order(ByteOrder.nativeOrder());
        this.mIndexBuffer = bb.asShortBuffer();
        Iterator<GLVertex> iter2 = this.mVertexList.iterator();
        while (iter2.hasNext()) {
            ((GLVertex) iter2.next()).put(this.mVertexBuffer, this.mColorBuffer);
        }
        Iterator<GLShape> iter3 = this.mShapeList.iterator();
        while (iter3.hasNext()) {
            ((GLShape) iter3.next()).putIndices(this.mIndexBuffer);
        }
    }

    public GLVertex addVertex(float x, float y, float z) {
        GLVertex vertex = new GLVertex(x, y, z, this.mVertexList.size());
        this.mVertexList.add(vertex);
        return vertex;
    }

    public void transformVertex(GLVertex vertex, M4 transform) {
        vertex.update(this.mVertexBuffer, transform);
    }

    public void draw(GL10 gl) {
        this.mColorBuffer.position(0);
        this.mVertexBuffer.position(0);
        this.mIndexBuffer.position(0);
        gl.glFrontFace(2304);
        gl.glShadeModel(7424);
        gl.glVertexPointer(3, 5132, 0, this.mVertexBuffer);
        gl.glColorPointer(4, 5132, 0, this.mColorBuffer);
        gl.glDrawElements(4, this.mIndexCount, 5123, this.mIndexBuffer);
        this.count++;
    }

    public static float toFloat(int x) {
        return ((float) x) / 65536.0f;
    }
}
