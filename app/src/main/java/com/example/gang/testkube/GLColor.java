package com.example.gang.testkube;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

public class GLColor {
    public final int alpha;
    public final int blue;
    public final int green;
    public final int red;

    public GLColor(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public GLColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = AccessibilityNodeInfoCompat.ACTION_CUT;
    }

    public boolean equals(Object other) {
        boolean z = false;
        if (!(other instanceof GLColor)) {
            return false;
        }
        GLColor color = (GLColor) other;
        if (this.red == color.red && this.green == color.green && this.blue == color.blue && this.alpha == color.alpha) {
            z = true;
        }
        return z;
    }
}
