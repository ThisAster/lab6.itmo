package com.freiz.common.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter

public final class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private static final long serialVersionUID = 8710511246607229027L;
    @NonNull
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NonNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NonNull
    private Coordinates coordinates; //Поле не может быть null
    @NonNull
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float health; //Значение поля должно быть больше 0
    private int heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    @NonNull
    private Weapon weaponType; //Поле не может быть null
    @NonNull
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    @NonNull
    private Chapter chapter; //Поле не может быть null

    public int getNameLength() {
        return name.length();
    }

    @Override
    public int compareTo(SpaceMarine o) {
        if (o.getHeartCount() - this.getHeartCount() != 0) {
            return this.getHeartCount() - o.getHeartCount();
        } else if (o.getNameLength() - this.getNameLength() != 0) {
            return this.getNameLength() - o.getNameLength();
        } else {
            return this.getHealth().compareTo(o.getHealth());
        }
    }

    @Override
    public String toString() {
        return "SpaceMarine"
                + "\nid='" + id
                + "\nname=" + name
                + "\ncoordinates=" + coordinates
                + "\ncreationDate=" + creationDate
                + "\nhealth=" + health
                + "\nheartCount=" + heartCount
                + "\nweaponType=" + weaponType
                + "\nmeleeWeapon=" + meleeWeapon
                + "\nchapter=" + chapter;
    }
}
