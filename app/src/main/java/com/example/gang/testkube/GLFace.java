package com.example.gang.testkube;

import android.util.Log;

import java.nio.ShortBuffer;
import java.util.ArrayList;

public class GLFace {
    private GLColor mColor;
    private ArrayList<GLVertex> mVertexList;

    public GLFace() {
        this.mVertexList = new ArrayList();
    }

    public GLFace(GLVertex v1, GLVertex v2, GLVertex v3) {
        this.mVertexList = new ArrayList();
        addVertex(v1);
        addVertex(v2);
        addVertex(v3);
    }

    public GLFace(GLVertex v1, GLVertex v2, GLVertex v3, GLVertex v4) {
        this.mVertexList = new ArrayList();
        addVertex(v1);
        addVertex(v2);
        addVertex(v3);
        addVertex(v4);
    }

    public void addVertex(GLVertex v) {
        this.mVertexList.add(v);
    }

    public void setColor(GLColor c) {
        int last = this.mVertexList.size() - 1;
        if (last < 2) {
            Log.e("GLFace", "not enough vertices in setColor()");
        } else {
            GLVertex vertex = (GLVertex) this.mVertexList.get(last);
            if (this.mColor == null) {
                while (vertex.color != null) {
                    this.mVertexList.add(0, vertex);
                    this.mVertexList.remove(last + 1);
                    vertex = (GLVertex) this.mVertexList.get(last);
                }
            }
            vertex.color = c;
        }
        this.mColor = c;
    }

    public int getIndexCount() {
        return (this.mVertexList.size() - 2) * 3;
    }

    public void putIndices(ShortBuffer buffer) {
        int last = this.mVertexList.size() - 1;
        GLVertex v0 = (GLVertex) this.mVertexList.get(0);
        GLVertex vn = (GLVertex) this.mVertexList.get(last);
        for (int i = 1; i < last; i++) {
            GLVertex v1 = (GLVertex) this.mVertexList.get(i);
            buffer.put(v0.index);
            buffer.put(v1.index);
            buffer.put(vn.index);
            v0 = v1;
        }
    }
}
