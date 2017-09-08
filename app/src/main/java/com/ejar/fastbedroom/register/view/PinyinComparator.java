package com.ejar.fastbedroom.register.view;

import com.ejar.fastbedroom.register.bean.School;

import java.util.Comparator;

/**
 * Created by json on 2017/9/5.
 */

public class PinyinComparator implements Comparator<School> {
    public int compare(School o1, School o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return 1;
        } else if (o1.getLetters().equals("#")
                || o2.getLetters().equals("@")) {
            return -1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }

}