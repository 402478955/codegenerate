package com.me.code.customize.generator.utils;

import java.util.LinkedList;
import java.util.List;

public class GeneratUtil {
    public static void main(String [] args){
        System.out.println(fromGang2Tuofeng("aaa_bbb_cc"));

    }
    public static String fromGang2Tuofeng(String tableName) {
        char[] charList = tableName.toCharArray();
        String tableNameReturn = "";
        List<Character> changedCharList = new LinkedList ();
        for (int i = 0; i < charList.length; i++) {

            if (charList[i] == '_' ) {
                i++;
                charList[i] -= 32;

            }
            changedCharList.add(charList[i]);

        }
        char[] returnChar = new char[changedCharList.size()];
        for (int i = 0; i < returnChar.length; i++) {
            returnChar[i] = changedCharList.get(i);
        }
        return new String(returnChar);
    }
    public static String fromTuofengToGang(String tableName) {
        char[] charList = tableName.toCharArray();
        String tableNameReturn = "";
        List<Character> changedCharList = new LinkedList ();
        for (int i = 0; i < charList.length; i++) {

            if (charList[i] >= 'A' && charList[i] <= 'Z') {
                charList[i] += 32;
                changedCharList.add('_');

            }
            changedCharList.add(charList[i]);

        }
        char[] returnChar = new char[changedCharList.size()];
        for (int i = 0; i < returnChar.length; i++) {
            returnChar[i] = changedCharList.get(i);
        }
        return new String(returnChar);
    }
    public static String firstCharUpperCase(String fieldName) {
       return fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
    }
    public static String firstCharLowCase(String fieldName) {
        return fieldName.substring(0,1).toLowerCase()+fieldName.substring(1,fieldName.length());
    }
}
