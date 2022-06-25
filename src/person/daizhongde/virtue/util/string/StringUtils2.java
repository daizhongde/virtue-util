package person.daizhongde.virtue.util.string;

import java.io.File;
import java.lang.reflect.Array;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * StringUtils 扩展
 * 
 * @author: 湘邮科技-戴忠德 
 * @date:   2021年6月13日 下午2:08:38   
 *   
 * @Copyright: 2021 www.copote.comInc. All rights reserved. 
 * 注意：本内容仅限于邮储银行湖南省分行内部传阅，禁止外泄以及用于其他的商业目的
 */
public class StringUtils2  {
    /**
     * 匹配字符串模版
     */
    private static final Pattern FORMAT_PATTERN = Pattern.compile(".*?(\\{(\\w+)?}).*?", Pattern.MULTILINE);
    private static final int UNICODE_CHAR_LENGTH = 4;

    /**
     * 生成随机数字
     *
     * @return
     */
    @Deprecated
    public static String randomNumeric(int length) {
        Double max = Math.pow(10, length);
        if (max > Integer.MAX_VALUE) {
            max = Double.valueOf(Integer.MAX_VALUE);
        }
        int random = ThreadLocalRandom.current().nextInt(0, max.intValue());
        return leftPad(random + "", length, '0');
    }

    /**
     * 左补位操作
     *
     * @param origin 原始字符串
     * @param length 输出总长度
     * @param pad    补位字符
     * @return
     */
    public static String leftPad(String origin, int length, char pad) {
        return pad("", origin, length, pad);
    }

    /**
     * 右补位操作
     *
     * @param origin 原始字符串
     * @param length 输出总长度
     * @param pad    补位字符
     * @return
     */
    public static String rightPad(String origin, int length, char pad) {
        return pad("-", origin, length, pad);
    }

    /**
     * 补位操作
     *
     * @param type   类型 left: "" 或 right: "-"
     * @param origin 原始字符串
     * @param length 输出总长度
     * @param pad    补位字符
     * @return
     */
    public static String pad(String type, String origin, int length, char pad) {
        if (null == type) {
            return origin;
        }
        return String.format("%1$" + type + length + "s", origin).replace(' ', pad);
    }

    /**
     * 字符串不为空
     *
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    /**
     * 判断多个串是否为空
     *
     * @param css
     * @return
     */
    public static boolean isAnyEmpty(CharSequence... css) {
        if (null == css || Array.getLength(css) == 0) {
            return true;
        } else {
            CharSequence[] var1 = css;
            int var2 = css.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                CharSequence cs = var1[var3];
                if (isEmpty(cs)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 判断多个串不为空
     *
     * @param css
     * @return
     */
    public static boolean isNoneEmpty(CharSequence... css) {
        return !isAnyEmpty(css);
    }

    /**
     * 判断字符串是否为空（包含空白串）
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串不为空（包含空白串）
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断多个串是否为空（包含空白串）
     *
     * @param css
     * @return
     */
    public static boolean isAnyBlank(CharSequence... css) {
        if (null == css || Array.getLength(css) == 0) {
            return true;
        } else {
            CharSequence[] var1 = css;
            int var2 = css.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                CharSequence cs = var1[var3];
                if (isBlank(cs)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 判断多个串是否同时为空（包含空白串）
     *
     * @param css
     * @return
     */
    public static boolean isNoneBlank(CharSequence... css) {
        return !isAnyBlank(css);
    }

    /**
     * 替换任意字符串
     *
     * @param input   输入字符串
     * @param head    头部保留长度
     * @param tail    尾部保留长度
     * @param replace 替换结果字符(重复length次数)
     * @return
     */
    public static String maskString(String input, int head, int tail, char replace) {
        if (isEmpty(input)) {
            return input;
        }
        if (head + tail >= input.length()) {
            return input;
        }
        String replacement = String.format("%1$" + (input.length() - head - tail) + "s", "").replace(' ', replace);
        return maskString(input, head, tail, replacement);
    }
    public static boolean isEmpty(String input ) {
        return StringUtils.isEmpty(input);
    }
    public static boolean isEmpty(CharSequence cs ) {
        return StringUtils.isEmpty(cs);
    }
    /**
     * 替换任意字符串
     *
     * @param input   输入字符串
     * @param head    头部保留长度
     * @param tail    尾部保留长度
     * @param replace 替换结果字符
     * @return
     */
    public static String maskString(String input, int head, int tail, String replace) {
        if (isEmpty(input)) {
            return input;
        }
        if (head + tail >= input.length()) {
            return input;
        }
        String replacement = "$1" + replace + "$2";
        String regex = "(\\S{" + head + "})\\S{" + (input.length() - head - tail) + "}(\\S{" + tail + "})";
        return input.replaceAll(regex, replacement);
    }

    /**
     * 替换任意字符串
     *
     * @param input 输入字符串
     * @param head  头部保留长度
     * @param tail  尾部保留长度
     * @return
     */
    public static String maskString(String input, int head, int tail) {
        return maskString(input, head, tail, '*');
    }

//    /**
//     * 下划线转驼峰
//     *
//     * @param input
//     * @param single 中间单字母是否转换，true转换
//     * @param spec   是否包含数字
//     * @return
//     */
//    public static String underlineToCamelHump(String input, boolean single, boolean spec) {
//        if (isEmpty(input)) {
//            return null;
//        }
//        String str = input.toLowerCase();
//        String reg = "_[a-z]";
//        if (spec) {
//            reg = "_[a-z0-9]";
//        }
//        Matcher matcher = Pattern.compile(reg).matcher(str);
//        StringBuilder builder = new StringBuilder(str);
//        for (int i = 0; matcher.find(); ++i) {
//            String group = matcher.group();
//            // IDE风格get_n_state-> nState, lombok风格n_state-> getNState
//            if (single && group.length() == 2) {
//                builder.replace(matcher.start() - i, matcher.end() - i, group.substring(1).toUpperCase());
//            } else {
//                builder.replace(matcher.start() - i, matcher.end() - i, group.substring(1).toLowerCase());
//            }
//        }
//        // 首字母小写
//        return uncapitalize(builder.toString());
//    }

//    /**
//     * 下划线转驼峰（字母数字）
//     *
//     * @param input
//     * @return
//     */
//    public static String underlineToCamelHump(String input) {
//        return underlineToCamelHump(input, false, false);
//    }

    /**
     * 驼峰转下划线
     *
     * @param input
     * @param spec  是否包含数字
     * @return
     */
    public static String camelHumpToUnderline(String input, boolean spec) {
        if (isEmpty(input)) {
            return null;
        }
        int size;
        char[] chars;
        StringBuilder sb = new StringBuilder((size = (chars = input.toCharArray()).length) * 3 / 2 + 1);
        for (int i = 0; i < size; ++i) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else if (spec && Character.isDigit(c) && i > 0 && !Character.isDigit(chars[i - 1])) {
                //不是连续的数字
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.charAt(0) == 95 ? sb.substring(1) : sb.toString();
    }

    public static String camelHumpToUnderline(String str) {
        return camelHumpToUnderline(str, false);
    }

    /**
     * 判断字符c是否为中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        // GENERAL_PUNCTUATION 判断中文的“号
        // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 是否是数字
     *
     * @param cs
     * @return
     */
    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();
            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * unicode 转换成 中文
     *
     * @param src
     * @return
     */
    public static String decodeUnicode(String src) {
        char aChar;
        int len = src.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = src.charAt(x++);
            if (aChar == '\\') {
                aChar = src.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < UNICODE_CHAR_LENGTH; i++) {
                        aChar = src.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();

    }

    public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

    /**
     * 字符串格式化
     *
     * @param template 字符串模板
     * @param args     对应的产生
     * @return
     */
    public static String format(String template, Object... args) {
        if (!StringUtils2.isEmpty(template)) {
            if (args != null && args.length > 0) {
                StringBuilder builder = new StringBuilder();
                Matcher matcher = FORMAT_PATTERN.matcher(template);
                int index = 0, groupIndex = -1, start = 0, end = 0, length = 0;
                String subStr = null, groupStr = null;
                boolean isMatch = false;
                while (matcher.find()) {
                    isMatch = true;
                    start = matcher.start();
                    end = matcher.end();
                    //获取当前匹配成功的字符串
                    subStr = template.substring(start, end);
                    length += subStr.length();
                    if (index < args.length) {
                        groupStr = matcher.group(1);
                        //获取表达是字符串在子串中的位置
                        groupIndex = subStr.indexOf(groupStr);
                        if (groupIndex != -1) {
                            builder.append(subStr, 0, groupIndex);
                            builder.append(args[index]);
                            //处理子串尾部的数据
                            if (groupIndex + groupStr.length() < subStr.length()) {
                                builder.append(subStr.substring(groupIndex + groupStr.length()));
                            }
                        }
                    } else {
                        //如果参数不够,则不做任何处理
                        builder.append(subStr);
                    }
                    index++;
                }
                if (isMatch) {
                    //处理尾部没有匹配的数据
                    if (length != template.length()) {
                        builder.append(template.substring(length));
                    }
                    return builder.toString();
                }
            }
        }
        return template;
    }

    /**
     * 字符串格式化
     *
     * @param template 字符串模板
     * @param args     对应的产生
     * @return
     */
    public static String nvl(Object object) {
    	if( null == object || object.toString().toLowerCase().equalsIgnoreCase("null") ){
            return ""; 
    	}else{
    		return object.toString();
    	}
    }
	/** 计算中文个数 */
    public static int getChineseCharacterCount(String msg) {
		String chineseCode = "[\\u4e00-\\u9fa5|\\（|\\）]";
		Pattern pattern = Pattern.compile(chineseCode);
		Matcher matcher = pattern.matcher(msg);
		int k = 0;
		while (matcher.find()) {
			k++;
		}
		return k;
	}	
    
    public static String mergeStringWithSeparator(String...args){ 
	    StringBuilder sb=new StringBuilder();  
	    for (String arg : args) {
	        sb.append(arg);  
	        sb.append(File.separator);  
	    }  
	          
	    return sb.substring(0, sb.length()-1).toString();  
	}  
	/**
	 * 清除换行
	 * <p>
	 * eg:shell命令多行编辑功能支持添加.即支持编辑器中手工文本换行，也支持shell脚本换行语法
	 * @param str
	 * @return
	 */
	public static String replaceAllEnter(String str){
		str = str.replaceAll("\t|\r|\n", " ");
		return str.replace(" \\ ", " ");
	}  
}
