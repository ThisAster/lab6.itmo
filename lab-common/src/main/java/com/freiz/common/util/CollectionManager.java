package com.freiz.common.util;

import com.freiz.common.data.SpaceMarine;

import java.time.ZonedDateTime;
import java.util.HashSet;
public class CollectionManager implements ICollectionManager {
    private final HashSet<Long> hashSetId = new HashSet<>();
    private Long idIter = 1L;
    private HashSet<SpaceMarine> spaceMarinesCollection = new HashSet<>();
    private ZonedDateTime creationDate = ZonedDateTime.now();

    public CollectionManager() {
    }

    public Class<? extends HashSet> getClassCOllection() {
        return spaceMarinesCollection.getClass();
    }

    public HashSet<SpaceMarine> getSpaceMarinesCollection() {
        return spaceMarinesCollection;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Long getNewID() {
        idIter = 1L;
        while (hashSetId.contains(idIter)) {
            idIter++;
        }
        return idIter;
    }

    public HashSet<Long> getHashSetId() {
        return hashSetId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (SpaceMarine i : spaceMarinesCollection) {
            sb.append('\n' + i.toString() + '\n');
        }
        sb.append(']');

        return sb.toString();
    }

}
