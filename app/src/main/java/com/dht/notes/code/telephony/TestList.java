package com.dht.notes.code.telephony;

import java.util.ArrayList;
import java.util.List;

public class TestList {

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    private List<String> list = new ArrayList<>();

    public void setTestCase(TestCase testCase) {
        TestList.testCase = testCase;
    }

    private static TestCase testCase;

    public  List<String> getAdPlacementList(int value) {
        if (testCase != null) {
            return testCase.getAdPlacementList(value);
        }
        return new ArrayList<>();
    }


}
