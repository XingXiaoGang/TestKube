package com.example.gang.testkube;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements KubeRenderer.AnimationCallback {
    static final int kBack = 5;
    static final int kDown = 1;
    static final int kEquator = 7;
    static final int kFront = 4;
    static final int kLeft = 2;
    static final int kMiddle = 6;
    static final int kRight = 3;
    static final int kSide = 8;
    static final int kUp = 0;
    static int[][] mLayerPermutations;
    float mAngleIncrement;
    Cube[] mCubes;
    float mCurrentAngle;
    Layer mCurrentLayer;
    int[] mCurrentLayerPermutation;
    float mEndAngle;
    Layer[] mLayers;
    int[] mPermutation;
    Random mRandom;
    KubeRenderer mRenderer;
    GLSurfaceView mView;

    public MainActivity() {
        this.mCubes = new Cube[27];
        this.mLayers = new Layer[9];
        this.mRandom = new Random(System.currentTimeMillis());
        this.mCurrentLayer = null;
    }

    private GLWorld makeGLWorld() {
        int i;
        int j;
        GLWorld world = new GLWorld();
        this.mCubes[0] = new Cube(world, -1.0f, 0.38f, -1.0f, -0.38f, 1.0f, -0.38f);
        this.mCubes[kDown] = new Cube(world, -0.32f, 0.38f, -1.0f, 0.32f, 1.0f, -0.38f);
        this.mCubes[kLeft] = new Cube(world, 0.38f, 0.38f, -1.0f, 1.0f, 1.0f, -0.38f);
        this.mCubes[kRight] = new Cube(world, -1.0f, 0.38f, -0.32f, -0.38f, 1.0f, 0.32f);
        this.mCubes[kFront] = new Cube(world, -0.32f, 0.38f, -0.32f, 0.32f, 1.0f, 0.32f);
        this.mCubes[kBack] = new Cube(world, 0.38f, 0.38f, -0.32f, 1.0f, 1.0f, 0.32f);
        this.mCubes[kMiddle] = new Cube(world, -1.0f, 0.38f, 0.38f, -0.38f, 1.0f, 1.0f);
        this.mCubes[kEquator] = new Cube(world, -0.32f, 0.38f, 0.38f, 0.32f, 1.0f, 1.0f);
        this.mCubes[kSide] = new Cube(world, 0.38f, 0.38f, 0.38f, 1.0f, 1.0f, 1.0f);
        this.mCubes[9] = new Cube(world, -1.0f, -0.32f, -1.0f, -0.38f, 0.32f, -0.38f);
        this.mCubes[10] = new Cube(world, -0.32f, -0.32f, -1.0f, 0.32f, 0.32f, -0.38f);
        this.mCubes[11] = new Cube(world, 0.38f, -0.32f, -1.0f, 1.0f, 0.32f, -0.38f);
        this.mCubes[12] = new Cube(world, -1.0f, -0.32f, -0.32f, -0.38f, 0.32f, 0.32f);
        this.mCubes[13] = null;
        this.mCubes[14] = new Cube(world, 0.38f, -0.32f, -0.32f, 1.0f, 0.32f, 0.32f);
        this.mCubes[15] = new Cube(world, -1.0f, -0.32f, 0.38f, -0.38f, 0.32f, 1.0f);
        this.mCubes[16] = new Cube(world, -0.32f, -0.32f, 0.38f, 0.32f, 0.32f, 1.0f);
        this.mCubes[17] = new Cube(world, 0.38f, -0.32f, 0.38f, 1.0f, 0.32f, 1.0f);
        this.mCubes[18] = new Cube(world, -1.0f, -1.0f, -1.0f, -0.38f, -0.38f, -0.38f);
        this.mCubes[19] = new Cube(world, -0.32f, -1.0f, -1.0f, 0.32f, -0.38f, -0.38f);
        this.mCubes[20] = new Cube(world, 0.38f, -1.0f, -1.0f, 1.0f, -0.38f, -0.38f);
        this.mCubes[21] = new Cube(world, -1.0f, -1.0f, -0.32f, -0.38f, -0.38f, 0.32f);
        this.mCubes[22] = new Cube(world, -0.32f, -1.0f, -0.32f, 0.32f, -0.38f, 0.32f);
        this.mCubes[23] = new Cube(world, 0.38f, -1.0f, -0.32f, 1.0f, -0.38f, 0.32f);
        this.mCubes[24] = new Cube(world, -1.0f, -1.0f, 0.38f, -0.38f, -0.38f, 1.0f);
        this.mCubes[25] = new Cube(world, -0.32f, -1.0f, 0.38f, 0.32f, -0.38f, 1.0f);
        this.mCubes[26] = new Cube(world, 0.38f, -1.0f, 0.38f, 1.0f, -0.38f, 1.0f);

        GLColor gLColor = new GLColor(0, 0, 0);
        for (i = 0; i < 27; i += kDown) {
            Cube cube = this.mCubes[i];
            if (cube != null) {
                for (j = 0; j < kMiddle; j += kDown) {
                    cube.setFaceColor(j, gLColor);
                }
            }
        }
        gLColor = new GLColor(0, AccessibilityNodeInfoCompat.ACTION_CUT, 0);
        for (i = 0; i < 9; i += kDown) {
            this.mCubes[i].setFaceColor(kBack, gLColor);
        }
        gLColor = new GLColor(0, 0, AccessibilityNodeInfoCompat.ACTION_CUT);
        for (i = 18; i < 27; i += kDown) {
            this.mCubes[i].setFaceColor(0, gLColor);
        }
        gLColor = new GLColor(AccessibilityNodeInfoCompat.ACTION_CUT, AccessibilityNodeInfoCompat.ACTION_CUT, 0);
        for (i = 0; i < 27; i += kRight) {
            this.mCubes[i].setFaceColor(kLeft, gLColor);
        }
        gLColor = new GLColor(AccessibilityNodeInfoCompat.ACTION_CUT, AccessibilityNodeInfoCompat.ACTION_PASTE, 0);
        for (i = kLeft; i < 27; i += kRight) {
            this.mCubes[i].setFaceColor(kRight, gLColor);
        }
        gLColor = new GLColor(AccessibilityNodeInfoCompat.ACTION_CUT, AccessibilityNodeInfoCompat.ACTION_CUT, AccessibilityNodeInfoCompat.ACTION_CUT);
        for (i = 0; i < 27; i += 9) {
            for (j = 0; j < kRight; j += kDown) {
                this.mCubes[i + j].setFaceColor(kFront, gLColor);
            }
        }
        gLColor = new GLColor(AccessibilityNodeInfoCompat.ACTION_CUT, 0, 0);
        for (i = kMiddle; i < 27; i += 9) {
            for (j = 0; j < kRight; j += kDown) {
                this.mCubes[i + j].setFaceColor(kDown, gLColor);
            }
        }
        for (i = 0; i < 27; i += kDown) {
            if (this.mCubes[i] != null) {
                world.addShape(this.mCubes[i]);
            }
        }
        this.mPermutation = new int[27];
        for (i = 0; i < this.mPermutation.length; i += kDown) {
            this.mPermutation[i] = i;
        }
        createLayers();
        updateLayers();
        world.generate();
        return world;
    }

    private void createLayers() {
        this.mLayers[0] = new Layer(kDown);
        this.mLayers[kDown] = new Layer(kDown);
        this.mLayers[kLeft] = new Layer(0);
        this.mLayers[kRight] = new Layer(0);
        this.mLayers[kFront] = new Layer(kLeft);
        this.mLayers[kBack] = new Layer(kLeft);
        this.mLayers[kMiddle] = new Layer(0);
        this.mLayers[kEquator] = new Layer(kDown);
        this.mLayers[kSide] = new Layer(kLeft);
    }

    private void updateLayers() {
        int i;
        int k;
        GLShape[] shapes = this.mLayers[0].mShapes;
        for (i = 0; i < 9; i += kDown) {
            shapes[i] = this.mCubes[this.mPermutation[i]];
        }
        shapes = this.mLayers[kDown].mShapes;
        i = 18;
        int k2 = 0;
        while (i < 27) {
            k = k2 + kDown;
            shapes[k2] = this.mCubes[this.mPermutation[i]];
            i += kDown;
            k2 = k;
        }
        shapes = this.mLayers[kLeft].mShapes;
        i = 0;
        k = 0;
        while (i < 27) {
            int j = 0;
            k2 = k;
            while (j < 9) {
                k = k2 + kDown;
                shapes[k2] = this.mCubes[this.mPermutation[i + j]];
                j += kRight;
                k2 = k;
            }
            i += 9;
            k = k2;
        }
        shapes = this.mLayers[kRight].mShapes;
        i = kLeft;
        k = 0;
        int j;
        while (i < 27) {
            j = 0;
            k2 = k;
            while (j < 9) {
                k = k2 + kDown;
                shapes[k2] = this.mCubes[this.mPermutation[i + j]];
                j += kRight;
                k2 = k;
            }
            i += 9;
            k = k2;
        }
        shapes = this.mLayers[kFront].mShapes;
        i = kMiddle;
        k = 0;
        while (i < 27) {
            j = 0;
            k2 = k;
            while (j < kRight) {
                k = k2 + kDown;
                shapes[k2] = this.mCubes[this.mPermutation[i + j]];
                j += kDown;
                k2 = k;
            }
            i += 9;
            k = k2;
        }
        shapes = this.mLayers[kBack].mShapes;
        i = 0;
        k = 0;
        while (i < 27) {
            j = 0;
            k2 = k;
            while (j < kRight) {
                k = k2 + kDown;
                shapes[k2] = this.mCubes[this.mPermutation[i + j]];
                j += kDown;
                k2 = k;
            }
            i += 9;
            k = k2;
        }
        shapes = this.mLayers[kMiddle].mShapes;
        i = kDown;
        k = 0;
        while (i < 27) {
            j = 0;
            k2 = k;
            while (j < 9) {
                k = k2 + kDown;
                shapes[k2] = this.mCubes[this.mPermutation[i + j]];
                j += kRight;
                k2 = k;
            }
            i += 9;
            k = k2;
        }
        shapes = this.mLayers[kEquator].mShapes;
        i = 9;
        k2 = 0;
        while (i < 18) {
            k = k2 + kDown;
            shapes[k2] = this.mCubes[this.mPermutation[i]];
            i += kDown;
            k2 = k;
        }
        shapes = this.mLayers[kSide].mShapes;
        i = kRight;
        k = 0;
        while (i < 27) {
            j = 0;
            k2 = k;
            while (j < kRight) {
                k = k2 + kDown;
                shapes[k2] = this.mCubes[this.mPermutation[i + j]];
                j += kDown;
                k2 = k;
            }
            i += 9;
            k = k2;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(kDown);
        this.mView = new GLSurfaceView(getApplication());
        this.mRenderer = new KubeRenderer(makeGLWorld(), MainActivity.this);
        this.mView.setRenderer(this.mRenderer);
        setContentView(this.mView);
    }

    protected void onResume() {
        super.onResume();
        this.mView.onResume();
    }

    protected void onPause() {
        super.onPause();
        this.mView.onPause();
    }

    public void animate() {
        this.mRenderer.setAngle(this.mRenderer.getAngle() + 1.2f);
        if (this.mCurrentLayer == null) {
            int layerID = this.mRandom.nextInt(9);
            this.mCurrentLayer = this.mLayers[layerID];
            this.mCurrentLayerPermutation = mLayerPermutations[layerID];
            this.mCurrentLayer.startAnimation();
            boolean direction = this.mRandom.nextBoolean();
            int count = this.mRandom.nextInt(kRight) + kDown;
            this.mCurrentAngle = 0.0f;
            if (false) {
                this.mAngleIncrement = 0.06283186f;
                this.mEndAngle = this.mCurrentAngle + (3.1415927f / 2.0f);
            } else {
                this.mAngleIncrement = -0.06283186f;
                this.mEndAngle = this.mCurrentAngle - (3.1415927f / 2.0f);
            }
        }
        this.mCurrentAngle += this.mAngleIncrement;
        if ((this.mAngleIncrement <= 0.0f || this.mCurrentAngle < this.mEndAngle) && (this.mAngleIncrement >= 0.0f || this.mCurrentAngle > this.mEndAngle)) {
            this.mCurrentLayer.setAngle(this.mCurrentAngle);
            return;
        }
        this.mCurrentLayer.setAngle(this.mEndAngle);
        this.mCurrentLayer.endAnimation();
        this.mCurrentLayer = null;
        int[] newPermutation = new int[27];
        for (int i = 0; i < 27; i += kDown) {
            newPermutation[i] = this.mPermutation[this.mCurrentLayerPermutation[i]];
        }
        this.mPermutation = newPermutation;
        updateLayers();
    }

    static {
        mLayerPermutations = new int[][]{new int[]{kLeft, kBack, kSide, kDown, kFront, kEquator, 0, kRight, kMiddle, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}, new int[]{0, kDown, kLeft, kRight, kFront, kBack, kMiddle, kEquator, kSide, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 23, 26, 19, 22, 25, 18, 21, 24}, new int[]{kMiddle, kDown, kLeft, 15, kFront, kBack, 24, kEquator, kSide, kRight, 10, 11, 12, 13, 14, 21, 16, 17, 0, 19, 20, 9, 22, 23, 18, 25, 26}, new int[]{0, kDown, kSide, kRight, kFront, 17, kMiddle, kEquator, 26, 9, 10, kBack, 12, 13, 14, 15, 16, 23, 18, 19, kLeft, 21, 22, 11, 24, 25, 20}, new int[]{0, kDown, kLeft, kRight, kFront, kBack, 24, 15, kMiddle, 9, 10, 11, 12, 13, 14, 25, 16, kEquator, 18, 19, 20, 21, 22, 23, 26, 17, kSide}, new int[]{18, 9, 0, kRight, kFront, kBack, kMiddle, kEquator, kSide, 19, 10, kDown, 12, 13, 14, 15, 16, 17, 20, 11, kLeft, 21, 22, 23, 24, 25, 26}, new int[]{0, kEquator, kLeft, kRight, 16, kBack, kMiddle, 25, kSide, 9, kFront, 11, 12, 13, 14, 15, 22, 17, 18, kDown, 20, 21, 10, 23, 24, 19, 26}, new int[]{0, kDown, kLeft, kRight, kFront, kBack, kMiddle, kEquator, kSide, 11, 14, 17, 10, 13, 16, 9, 12, 15, 18, 19, 20, 21, 22, 23, 24, 25, 26}, new int[]{0, kDown, kLeft, 21, 12, kRight, kMiddle, kEquator, kSide, 9, 10, 11, 22, 13, kFront, 15, 16, 17, 18, 19, 20, 23, 14, kBack, 24, 25, 26}};
    }
}
