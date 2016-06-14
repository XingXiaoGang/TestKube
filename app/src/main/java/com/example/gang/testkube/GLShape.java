package com.example.gang.testkube;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class GLShape {
    public M4 mAnimateTransform;
    protected ArrayList<GLFace> mFaceList;
    protected ArrayList<Integer> mIndexList;
    public M4 mTransform;
    protected ArrayList<GLVertex> mVertexList;
    protected GLWorld mWorld;

    public GLShape(GLWorld world) {
        this.mFaceList = new ArrayList();
        this.mVertexList = new ArrayList();
        this.mIndexList = new ArrayList();
        this.mWorld = world;
    }

    public void addFace(GLFace face) {
        this.mFaceList.add(face);
    }

    public void setFaceColor(int face, GLColor color) {
        ((GLFace) this.mFaceList.get(face)).setColor(color);
    }

    public void putIndices(ShortBuffer buffer) {
        Iterator<GLFace> iter = this.mFaceList.iterator();
        while (iter.hasNext()) {
            ((GLFace) iter.next()).putIndices(buffer);
        }
    }

    public int getIndexCount() {
        int count = 0;
        Iterator<GLFace> iter = this.mFaceList.iterator();
        while (iter.hasNext()) {
            count += ((GLFace) iter.next()).getIndexCount();
        }
        return count;
    }

    public GLVertex addVertex(float x, float y, float z) {
        GLVertex vertex;
        Iterator<GLVertex> iter = this.mVertexList.iterator();
        while (iter.hasNext()) {
            vertex = (GLVertex) iter.next();
            if (vertex.x == x && vertex.y == y && vertex.z == z) {
                return vertex;
            }
        }
        vertex = this.mWorld.addVertex(x, y, z);
        this.mVertexList.add(vertex);
        return vertex;
    }

    public void animateTransform(M4 transform) {
        this.mAnimateTransform = transform;
        if (this.mTransform != null) {
            transform = this.mTransform.multiply(transform);
        }
        Iterator<GLVertex> iter = this.mVertexList.iterator();
        while (iter.hasNext()) {

            this.mWorld.transformVertex((GLVertex) iter.next(), transform);
        }
    }

    public void startAnimation() {
    }

    public void endAnimation() {
        if (this.mTransform == null) {
            this.mTransform = new M4(this.mAnimateTransform);
        } else {
            this.mTransform = this.mTransform.multiply(this.mAnimateTransform);
        }
    }
}
