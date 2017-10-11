package com.gm.rtonumbermatcher.util;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/10/17.
 */

public class Enabled {
    private boolean raw;

    Enabled(boolean initialValue) {
        this.raw = initialValue;
    }

    public boolean get() {
        return raw;
    }

    public void set(boolean enabled) {
        this.raw = enabled;
    }
}
