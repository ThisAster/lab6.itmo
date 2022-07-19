package com.freiz.common.commands.subcommand;

import com.freiz.common.data.Chapter;
import com.freiz.common.data.Coordinates;
import com.freiz.common.data.SpaceMarine;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.OutputManager;

import java.time.ZonedDateTime;

public final class AddElem {
    public static final double MINX = 0D;
    public static final float MINY = -381F;
    public static final int MINHEARTCOUNT = 0;
    public static final int MAXHEARTCOUNT = 3;
    public static final Integer MINMARINESCOUNT = 0;
    public static final Integer MAXMARINESCOUNT = 1000;
    public static final float MAXHEALTH = 0F;
    private AddElem() {
        //private construction
    }

    public static SpaceMarine add(InputManager inputManager, OutputManager outputManager) {
        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        coordinatesBuilder.x(inputManager.readDoubleValueH(" x(coordinates)", outputManager, x -> x <= MINX, "Значение поля должно быть больше 0"));
        coordinatesBuilder.y(inputManager.readFloatValueWithPredicatH(" y(coordinates)", outputManager, y -> y <= MINY, "Значение поля должно быть больше -381"));

        Chapter chapter;
        Chapter.ChapterBuilder chapterBuilder = Chapter.builder();
        chapterBuilder.name(inputManager.readStringNameValue(" name(Chapter, dataFormat: String)", outputManager, "Поле не может быть null, Строка не может быть пустой"));
        chapterBuilder.parentLegion(inputManager.readStringValue(" parentLegion(Chapter, dataFormat: String)", outputManager));
        chapterBuilder.marinesCount(inputManager.readIntegerValueH(" marinesCount(Chapter, dataFormat: Integer)", outputManager, x -> x <= MINMARINESCOUNT || x > MAXMARINESCOUNT, "Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000 or Check dataFormat please"));
        chapterBuilder.world(inputManager.readStringValue(" world(Chapter, dataFormat: String)", outputManager));
        chapter = chapterBuilder.build();

        SpaceMarine.SpaceMarineBuilder spaceMarineBuilder = SpaceMarine.builder();
        spaceMarineBuilder.id(0L);
        spaceMarineBuilder.name(inputManager.readStringNameValue(" name(SpaceMarine, dataFormat: String)", outputManager, "Поле не может быть null, Строка не может быть пустой"));
        spaceMarineBuilder.chapter(chapter);
        spaceMarineBuilder.coordinates(coordinatesBuilder.build());
        spaceMarineBuilder.health(inputManager.readFloatValueWithPredicatH(" health(SpaceMarine, dataFormat: float)", outputManager, x -> x <= MAXHEALTH, "Значение поля должно быть больше 0"));
        spaceMarineBuilder.heartCount(inputManager.readIntegerValueHeartCount(" heartCount(SpaceMarine, dataFormat: int)", outputManager, x -> x <= MINHEARTCOUNT || x > MAXHEARTCOUNT, "Значение поля должно быть больше 0, Максимальное значение поля: 3 or Check dataFormat please"));
        spaceMarineBuilder.weaponType(inputManager.readWeaponType(" HEAVY_BOLTGUN or BOLT_RIFLE or GRENADE_LAUNCHER or INFERNO_PISTOL or MULTI_MELTA(dataFormat: Weapon)", outputManager, "Поле не может быть null or Check dataFormat please"));
        spaceMarineBuilder.meleeWeapon(inputManager.readMeleeWeaponType(" CHAIN_SWORD or MANREAPER or LIGHTING_CLAW or POWER_BLADE or POWER_FIST(dataFormat: MeleeWeapon)", outputManager, "Поле не может быть null or Check dataFormat please"));

        spaceMarineBuilder.creationDate(ZonedDateTime.now());
        return spaceMarineBuilder.build();
    }
}
