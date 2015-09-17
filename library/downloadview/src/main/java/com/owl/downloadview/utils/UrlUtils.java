
package com.owl.downloadview.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    private static String getFileNameFromUrl(String url) {
        int slashIndex = url.lastIndexOf('/');
        int dotIndex = url.lastIndexOf('.');
        String filenameWithoutExtension;
        String fileExtension = null;
        String fileNamePatternString = "[\\.A-Za-z0-9_\\-\\$]+";
        if (dotIndex == -1 || dotIndex < slashIndex) {
            filenameWithoutExtension = url.substring(slashIndex + 1);
            fileExtension = "";
        } else {
            filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
            StringBuffer sb = new StringBuffer(filenameWithoutExtension);
            sb.reverse();
            Matcher fileNameMatcher = Pattern.compile(fileNamePatternString).matcher(sb.toString());
            fileNameMatcher.find();
            String reverseFileName = fileNameMatcher.group(0);
            StringBuffer reverseFileNameBuffer = new StringBuffer(reverseFileName);
            reverseFileNameBuffer.reverse();
            filenameWithoutExtension = reverseFileNameBuffer.toString();

            String patternString = "\\w*";
            Matcher matcher = Pattern.compile(patternString).matcher(url.substring(dotIndex + 1));
            if (matcher != null && matcher.find()) {
                fileExtension = matcher.group(0);
            } else {
                fileExtension = "";
            }
        }

        if (filenameWithoutExtension == null || "".equals(filenameWithoutExtension.trim())) {// 如果获取不到文件名称
            filenameWithoutExtension = UUID.randomUUID() + ".undefined";// 默认取一个文件名
        }
        return filenameWithoutExtension + "." + fileExtension;
    }

    public static String parseRealFileName(String urlText) {
        // TODO Auto-generated method stub
        String realFileName = null;
        try {
            URL url = new URL(urlText);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.getResponseCode();
            String realUrl = conn.getURL().toString();
            realFileName = getFileNameFromUrl(realUrl);
            conn.disconnect();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return realFileName;
    }
}
