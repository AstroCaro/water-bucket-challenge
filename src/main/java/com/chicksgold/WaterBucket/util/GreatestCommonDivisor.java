package com.chicksgold.WaterBucket.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GreatestCommonDivisor {

    public static int calculate(int a, int b) {
        return b == 0 ? a : calculate(b, a % b);
    }
}
