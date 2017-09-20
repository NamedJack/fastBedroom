package com.ejar.fastbedroom.fastmail;

import com.ejar.fastbedroom.fastmail.bean.MailWayBean;
import com.ejar.fastbedroom.register.bean.School;

import java.util.Comparator;

/**
 * Created by json on 2017/9/12.
 */

public class PinyinComparatorMail implements Comparator<MailWayBean> {
public int compare(MailWayBean o1, MailWayBean o2) {
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
