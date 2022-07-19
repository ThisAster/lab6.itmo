package com.freiz.common.dto;

import java.io.Serializable;

public class CommandResultDto implements Serializable {
    private static final long serialVersionUID = -9221007002394355516L;
    private final Serializable output;

    public CommandResultDto(Serializable output) {
        this.output = output;
    }

    public Serializable getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "CommandResult{"
                + "output='" + output.toString() + '\''
                + '}';
    }
}
