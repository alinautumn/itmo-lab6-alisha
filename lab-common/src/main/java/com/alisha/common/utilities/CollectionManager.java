package com.alisha.common.utilities;

import com.alisha.common.data.Route;

import java.time.LocalDate;
import java.util.TreeSet;

public interface CollectionManager {

    void initialiseData(TreeSet<Route> treeSet);

    LocalDate getCreationDate();

    TreeSet<Route> getMainData();

    int getMaxId();
}
