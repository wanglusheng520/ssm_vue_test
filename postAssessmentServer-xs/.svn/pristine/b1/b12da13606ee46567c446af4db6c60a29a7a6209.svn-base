package com.beneway.common.common.utils;

import cn.hutool.core.util.StrUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/7/24
 * @time: 12:48
 */
public class PYUtils {

    /**
     * 汉语转拼音
     * @param hy
     * @return
     */
    public static String hanyuToPinyin(String hy){
        if(StringUtils.isEmpty(hy)){
            return null;
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        char[] input = hy.trim().toCharArray();
        try{
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else{
                    output += Character.toString(input[i]);
                }
            }
        }catch (Exception e){
            System.out.println("拼音转化失败");
            e.printStackTrace();
        }

        return output;

    }

    /**
     * 获取拼音首字母
     * @param hy
     * @return
     */
    public static String hanyuToPinyinFirst(String hy){
        if (StrUtil.isNotEmpty(hy)){
            int length = hy.length();
            String s = "";
            for (int i = 0; i < length; i++) {
                String y = hy.substring(i, i + 1);
                String pinyin = hanyuToPinyin(y);
                String first = pinyin.substring(0, 1);
                s += first;
            }
            return s;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(hanyuToPinyin("曾鸿渝"));
    }


}
