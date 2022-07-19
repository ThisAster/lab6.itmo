package com.freiz.common.util;

import java.time.ZonedDateTime;
import java.util.HashSet;

import com.freiz.common.data.SpaceMarine;

public interface ICollectionManager {

    ZonedDateTime getCreationDate();

    HashSet<SpaceMarine> getSpaceMarinesCollection();

    Long getNewID();
}
