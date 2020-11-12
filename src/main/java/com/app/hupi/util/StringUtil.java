package com.app.hupi.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil extends org.apache.commons.lang3.StringUtils  {
	  private static final Pattern underlinePattern = Pattern.compile("_(\\w)");

	    private static final Pattern camelPattern = Pattern.compile("[A-Z]");
	public static boolean equals(String str1, String str2) {
		str1 = trim(Optional.ofNullable(str1).orElse(""));
		str2 = trim(Optional.ofNullable(str2).orElse(""));
		return Objects.equals(str1, str2);
	}

	public static boolean notEquals(String str1, String str2) {
		return !equals(str1,str2);
	}
	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	public static String getString(String[] str,String spliChar) {
		StringBuffer sb=new StringBuffer();
		if(str!=null) {
			for(String s:str) {
				sb.append(s).append(spliChar);
			}
		}
		if(sb.toString().contains(spliChar)) {
			return sb.subSequence(0, sb.length()-1).toString();
		}
		return "";
	}

	/**
	 * 去掉字符串两端的空白，null值返回""
	 *
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return Optional.ofNullable(str).map(s -> s.trim()).orElse("");
	}
	
	 /**
     * 校验是否为数字
     */
    public static boolean isNum(String num) {

        if (num == null || "".equals(num)) {
            return false;
        }

        if (num.startsWith("-")) {
            return isNum(num.substring(1));
        }

        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher isNum = pattern.matcher(num);
        return isNum.matches();
    }
    /**
     * 校验是否为数字或者字母
     */
    public static boolean isNumOrChar(String num) {

        if (num == null || "".equals(num)) {
            return false;
        }

        if (num.startsWith("-")) {
            return isNum(num.substring(1));
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher isNum = pattern.matcher(num);
        return isNum.matches();
    }
    /**
     * 一对多比较 values notNUll
     *
     * @param str1
     * @param values
     * @return
     */
    public static boolean equalsAny(String str1, String... values) {
        final String str = trim(Optional.ofNullable(str1).orElse(""));
        if (Arrays.asList(values).stream()
            .anyMatch(value -> Objects.equals(str, trim(Optional.ofNullable(value).orElse(""))))) {
            return true;
        }
        return false;
    }


    /**
     * 字符串分割成集合
     *
     * @param str
     * @param separator
     * @return
     */
    public static Set<String> stringToSet(String str, String separator) {
        if (null == str || null == separator || str.isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(separator)).collect(Collectors.toSet());
    }
    
    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        if (isBlank(str)) {
            return str;
        }

        Matcher matcher = camelPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String underlineToCamel(String str) {
        if (isBlank(str)) {
            return str;
        }

        str = str.toLowerCase();
        Matcher matcher = underlinePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
