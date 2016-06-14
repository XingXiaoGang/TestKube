package com.example.gang.testkube;

public class Layer {
    public static final int kAxisX = 0;
    public static final int kAxisY = 1;
    public static final int kAxisZ = 2;
    int mAxis;
    GLShape[] mShapes;
    M4 mTransform;

    public Layer(int axis) {
        this.mShapes = new GLShape[9];
        this.mTransform = new M4();
        this.mAxis = axis;
        this.mTransform.setIdentity();
    }

    public void startAnimation() {
        for (int i = kAxisX; i < this.mShapes.length; i += kAxisY) {
            GLShape shape = this.mShapes[i];
            if (shape != null) {
                shape.startAnimation();
            }
        }
    }

    public void endAnimation() {
        for (int i = kAxisX; i < this.mShapes.length; i += kAxisY) {
            GLShape shape = this.mShapes[i];
            if (shape != null) {
                shape.endAnimation();
            }
        }
    }

    public void setAngle(float angle) {
        while (angle >= 6.2831855f) {
            angle -= 6.2831855f;
        }
        while (angle < 0.0f) {
            angle += 6.2831855f;
        }
        float sin = (float) Math.sin((double) angle);
        float cos = (float) Math.cos((double) angle);
        float[][] m = this.mTransform.m;
        float[] vArr;
        switch (this.mAxis) {
            case kAxisX /*0*/:
                m[kAxisY][kAxisY] = cos;
                m[kAxisY][kAxisZ] = sin;
                m[kAxisZ][kAxisY] = -sin;
                m[kAxisZ][kAxisZ] = cos;
                m[kAxisX][kAxisX] = 1.0f;
                vArr = m[kAxisX];
                m[kAxisZ][kAxisX] = kAxisX;
                m[kAxisY][kAxisX] = kAxisX;
                m[kAxisX][kAxisZ] = kAxisX;
                vArr[kAxisY] = kAxisX;
                break;
            case kAxisY /*1*/:
                m[kAxisX][kAxisX] = cos;
                m[kAxisX][kAxisZ] = sin;
                m[kAxisZ][kAxisX] = -sin;
                m[kAxisZ][kAxisZ] = cos;
                m[kAxisY][kAxisY] = 1.0f;
                vArr = m[kAxisX];
                m[kAxisZ][kAxisY] = kAxisX;
                m[kAxisY][kAxisZ] = kAxisX;
                m[kAxisY][kAxisX] = kAxisX;
                vArr[kAxisY] = kAxisX;
                break;
            case kAxisZ /*2*/:
                m[kAxisX][kAxisX] = cos;
                m[kAxisX][kAxisY] = sin;
                m[kAxisY][kAxisX] = -sin;
                m[kAxisY][kAxisY] = cos;
                m[kAxisZ][kAxisZ] = 1.0f;
                float[] fArr = m[kAxisZ];
                m[kAxisY][kAxisZ] = kAxisX;
                m[kAxisX][kAxisZ] = kAxisX;
                m[kAxisZ][kAxisY] = kAxisX;
                fArr[kAxisX] = kAxisX;
                break;
        }
        for (int i = kAxisX; i < this.mShapes.length; i += kAxisY) {
            GLShape shape = this.mShapes[i];
            if (shape != null) {
                shape.animateTransform(this.mTransform);
            }
        }
    }
}
