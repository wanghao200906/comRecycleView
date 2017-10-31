package com.wh.comrecycleview.entitiy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghao on 2017/10/25.
 */

public class UserName {

    private String name;
    private String nameIndex;


    public UserName(String name, String nameIndex) {
        this.name = name;
        this.nameIndex = nameIndex;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(String nameIndex) {
        this.nameIndex = nameIndex;
    }

    public UserName() {
    }

    public List getData() {
        List<UserName> list = new ArrayList<>();

        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("我", "W"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("要", "Y"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("涨", "Z"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("工", "G"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));
        list.add(new UserName("资", "Z"));


        return list;
    }


}
