package com.hanix.portfolio.common.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtil {


    /**
     * 텍스트 - > 파일
     * @param fileFullPath
     * @param msg
     * @param isAppend
     */
    public static void saveTextToFile(String fileFullPath, String msg, boolean isAppend) {
        try {
            File file = new File(fileFullPath);
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), isAppend);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(msg);
            bw.newLine();
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * txt 파일 불러오기
     * @param fileFullPath
     * @return
     */
    public static StringBuffer loadTextFile(String fileFullPath) {
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader(fileFullPath))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }


    /**
     * 파일 삭제하기
     * @param fileFullPath
     * @return
     */
    public static boolean delFile(String fileFullPath) {
        try {
            File existFile = new File(fileFullPath);
            if(existFile.exists()) {
                return existFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    /**
     * 캐시파일 있는지 확인
     * @param context
     * @param fileName
     * @return
     */
    public static boolean isCacheFileExist(Context context, String fileName) {
        try {
            File existFile = new File(context.getCacheDir(), fileName);
            return existFile.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 캐시파일 삭제
     * @param context
     * @param fileName
     */
    public static void deleteCacheFile(Context context, String fileName) {
        try {
            File file = File.createTempFile(fileName, null, context.getCacheDir());
            file.delete();
        } catch (Exception e) { e.printStackTrace();}
    }


    /**
     * 캐시파일 전체 삭제
     * @param context
     */
    public static void deleteAllCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }


    /**
     * 폴더 삭제
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children != null ? children : new String[0]) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
