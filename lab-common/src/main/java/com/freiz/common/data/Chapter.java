package com.freiz.common.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public final class Chapter implements Serializable {
    private static final long serialVersionUID = -1764482604936276701L;
    @NonNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    @NonNull
    private Integer marinesCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле может быть null

    @Override
    public String toString() {
        return "Chapter"
                + "\nname='" + name
                + "\nparentLegion=" + parentLegion
                + "\nmarinesCount=" + marinesCount
                + "\nworld=" + world;
    }
}
