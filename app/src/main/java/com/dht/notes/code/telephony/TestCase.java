package com.dht.notes.code.telephony;

import java.util.ArrayList;
import java.util.List;

public class TestCase {


    public List<String> getAdPlacementList(int appPlacement) {
        List<String> adPlacementsList = new ArrayList<>();

        switch (appPlacement) {
            case 1:
                adPlacementsList.add("qwe");
                break;

            case 2:
                adPlacementsList.add("asd");
                break;

            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                adPlacementsList.add("zxc");
                break;

        }
        return adPlacementsList;
    }

}
