package com.wyverno.Utils.Unicode;

public class UTF16 {
    private UTF16() {
    }

    public static String UTF16ToText(String text) {
        StringBuilder result = new StringBuilder();

        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (i+5 != charArray.length) {
                if (charArray[i] == '\\' && charArray[i+1] == 'u') {
                    StringBuilder sb = new StringBuilder("\\u");
                    sb.append(charArray[i+2]);
                    sb.append(charArray[i+3]);
                    sb.append(charArray[i+4]);
                    sb.append(charArray[i+5]);
                    i += 5;

                    result.append(UTF16Decoder(sb.toString()));
                    continue;
                }
            }
            result.append(charArray[i]);

        }

        return result.toString();
    }

    private static char UTF16Decoder(String utf16) {
        utf16 = utf16.trim().substring("\\u".length());
        return Character.toChars(Integer.parseInt(utf16,16))[0];
    }
}
