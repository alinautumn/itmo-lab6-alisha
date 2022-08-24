package com.alisha.server.utilities;

import com.alisha.common.data.Route;
import com.alisha.common.utilities.CollectionManager;

import java.time.LocalDate;
import java.util.TreeSet;

public class CollectionManagerImpl implements CollectionManager {

    private final LocalDate creationDate = LocalDate.now();
    private TreeSet<Route> mainData = new TreeSet<>();

    public void initialiseData(TreeSet<Route> treeSet) {
        this.mainData = treeSet;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public TreeSet<Route> getMainData() {
        return mainData;
    }

    public int getMaxId() {
        int maxId = 0;
        for (Route route : mainData) {
            if (route.getId() > maxId) {
                maxId = route.getId();
            }
        }
        return maxId;
    }
}
