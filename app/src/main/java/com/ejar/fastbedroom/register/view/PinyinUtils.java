package com.ejar.fastbedroom.register.view;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import static net.sourceforge.pinyin4j.format.HanyuPinyinCaseType.UPPERCASE;
import static net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITHOUT_TONE;

/**
 * Created by json on 2017/8/21.
 */

public class PinyinUtils {

    /**
     * 如果字符串的首字符为汉字，则返回该汉字的拼音大写首字母
     * 如果字符串的首字符为字母，也转化为大写字母返回
     * 其他情况均返回' '
     *
     * @param str 字符串
     * @return 首字母
     */
    public static char getHeadChar(String str) {

        HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
        hanYuPinOutputFormat.setCaseType(UPPERCASE);//大写
        hanYuPinOutputFormat.setToneType(WITHOUT_TONE);
        hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);


        if (str != null && str.trim().length() != 0) {
            char[] strChar = str.toCharArray();
            char headChar = strChar[0]; //如果是大写字母则直接返回
            if (Character.isUpperCase(headChar)) {
                return headChar;
            } else if (Character.isLowerCase(headChar)) {
                return Character.toUpperCase(headChar);
            }
            // 汉语拼音格式输出类

            if (String.valueOf(headChar).matches("[\\u4E00-\\u9FA5]+")) {
                try {
                    String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(headChar, hanYuPinOutputFormat);
                    if (stringArray != null && stringArray[0] != null) {
                        return stringArray[0].charAt(0);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    return '#';
                }
            }
        }
        return '#';
    }


}
