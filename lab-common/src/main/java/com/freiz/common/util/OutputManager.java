package com.freiz.common.util;

import java.io.Serializable;

public class OutputManager {
    public void println(Serializable serializable) {
        System.out.println(serializable);
    }

    public void print(String string) {
        System.out.print(string);
    }
}
