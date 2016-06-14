package com.example.gang.testkube;

import java.lang.reflect.Array;

public class M4 {
    public float[][] m;

    public M4() {
        this.m = (float[][]) Array.newInstance(Float.TYPE, new int[]{4, 4});
    }

    public M4(M4 other) {
        this.m = (float[][]) Array.newInstance(Float.TYPE, new int[]{4, 4});
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] = other.m[i][j];
            }
        }
    }

    public void multiply(GLVertex src, GLVertex dest) {
        dest.x = (((src.x * this.m[0][0]) + (src.y * this.m[1][0])) + (src.z * this.m[2][0])) + this.m[3][0];
        dest.y = (((src.x * this.m[0][1]) + (src.y * this.m[1][1])) + (src.z * this.m[2][1])) + this.m[3][1];
        dest.z = (((src.x * this.m[0][2]) + (src.y * this.m[1][2])) + (src.z * this.m[2][2])) + this.m[3][2];
    }

    public M4 multiply(M4 other) {
        M4 result = new M4();
        float[][] m1 = this.m;
        float[][] m2 = other.m;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.m[i][j] = (((m1[i][0] * m2[0][j]) + (m1[i][1] * m2[1][j])) + (m1[i][2] * m2[2][j])) + (m1[i][3] * m2[3][j]);
            }
        }
        return result;
    }

    public void setIdentity() {
        int i = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                this.m[i][j] = i == j ? 1.0f : 0.0f;
                j++;
            }
            i++;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("[ ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                builder.append(this.m[i][j]);
                builder.append(" ");
            }
            if (i < 2) {
                builder.append("\n  ");
            }
        }
        builder.append(" ]");
        return builder.toString();
    }
}
