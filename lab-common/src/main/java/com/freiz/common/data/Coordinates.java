package com.freiz.common.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public final class Coordinates implements Serializable {
    private static final long serialVersionUID = 8325469406389968966L;
    private double x;
    private float y;

    @Override
    public String toString() {
        return "\n\tx=" + x
                + "\n\ty=" + y
                + "\n";
    }

}
