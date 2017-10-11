/*
 * Copyright (c) 2017 Gowtham Parimelazhagan.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gm.rtonumbermatcher.util;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/25/17.
 */

public final class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Initialize the tool class
     *
     * @param context Context
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * Get the ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }


    public static int digSum(int data) {
        int sum = 0;

        while (data > 0 || sum > 9) {
            if (data == 0) {
                data = sum;
                sum = 0;
            }
            sum += data % 10;
            data /= 10;
        }
        return sum;
    }

    public static boolean findDuplicateChars(String str, int check) {
        boolean chk = false;
        Map<Character, Integer> dupMap = new HashMap<>();
        char[] chrs = str.toCharArray();
        for (Character ch : chrs) {
            if (dupMap.containsKey(ch)) {
                dupMap.put(ch, dupMap.get(ch) + 1);
            } else {
                dupMap.put(ch, 1);
            }
        }
        Set<Character> keys = dupMap.keySet();
        for (Character ch : keys) {
            if (dupMap.get(ch) > 1 && dupMap.get(ch) == check) {
//                if (dupMap.get(ch) == check) {
//                    System.out.println(ch + "--->" + dupMap.get(ch));
                chk = true;
//                }
            }
        }
        return chk;
    }


}
