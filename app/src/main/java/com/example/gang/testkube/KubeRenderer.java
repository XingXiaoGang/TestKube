package com.example.gang.testkube;

import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class KubeRenderer implements Renderer {
    private float mAngle;
    private AnimationCallback mCallback;
    private GLWorld mWorld;

    public interface AnimationCallback {
        void animate();
    }

    public KubeRenderer(GLWorld world, AnimationCallback callback) {
        this.mWorld = world;
        this.mCallback = callback;
    }

    public void onDrawFrame(GL10 gl) {
        if (this.mCallback != null) {
            this.mCallback.animate();
        }
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        gl.glClear(16640);

        gl.glMatrixMode(5888);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glRotatef(this.mAngle, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(this.mAngle * 0.25f, 1.0f, 0.0f, 0.0f);
        gl.glColor4f(0.7f, 0.7f, 0.7f, 1.0f);
        gl.glEnableClientState(32884);
        gl.glEnableClientState(32886);
        gl.glEnable(2884);
        gl.glShadeModel(7425);
        gl.glEnable(2929);
        this.mWorld.draw(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = ((float) width) / ((float) height);
        gl.glMatrixMode(5889);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 2.0f, 12.0f);
        gl.glDisable(3024);
        gl.glActiveTexture(33984);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public void setAngle(float angle) {
        this.mAngle = angle;
    }

    public float getAngle() {
        return this.mAngle;
    }
}
