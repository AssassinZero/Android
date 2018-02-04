package com.rd.network.utils;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;

import com.rd.tools.log.Logger;
import com.rd.tools.utils.ContextHolder;
import com.rd.tools.utils.PermissionCheck;
import com.rd.tools.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import okhttp3.ResponseBody;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/7/6 09:53
 * <p/>
 * Description:
 */
public class FileDownloadUtil {
    /**
     * 保存文件
     */
    public static String writeResponseBodyToDisk(ResponseBody body, String filePath, String fileName) {
        if (null == body || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            ToastUtil.toast("下载失败");
            return null;
        }
        Logger.i("下载完成，准备写入文件.");
        Context context = ContextHolder.getContext();
        // 如果没有读写SD卡的权限，则不写入文件
        if (null != context && !PermissionCheck.getInstance().checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.i("请求读写SD卡权限");
            return null;
        }
        // 目录不存在  则创建
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            File         targetFile   = new File(filePath + File.separator + fileName);
            InputStream  inputStream  = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize           = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(targetFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Logger.i("file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                Logger.i("下载成功");
                return targetFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件是否存在
     */
    public static boolean isExists(String filePath, String fileName) {
        try {
            File file = new File(filePath + File.separator + fileName);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 解压缩一个文件
     *
     * @param sourceFilePath
     *         要解压的路径
     * @param targetFilePath
     *         解压到的路径
     */
    public static int upZipFile(String sourceFilePath, String targetFilePath) {
        int count = 0;

        File zipFile = new File(sourceFilePath);
        File desDir  = new File(targetFilePath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }

        try {
            ZipFile zf = new ZipFile(zipFile);
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                ZipEntry    entry = ((ZipEntry) entries.nextElement());
                InputStream in    = zf.getInputStream(entry);
                String      str   = targetFilePath + File.separator + entry.getName();
                str = new String(str.getBytes("8859_1"), "GB2312");
                File desFile = new File(str);
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                OutputStream out      = new FileOutputStream(desFile);
                byte         buffer[] = new byte[1024];
                int          realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static List<String> searchFiles(String folderPath) {
        List<String> list   = new ArrayList<>();
        File         desDir = new File(folderPath);
        if (!desDir.exists()) {
            Logger.i("no file");
            return list;
        }
        File[] files = new File(folderPath).listFiles();
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath
     *         String 原文件路径 如：c:/fqf.txt
     * @param newPath
     *         String 复制后路径 如：f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int  byteSum  = 0;
            int  byteRead = 0;
            File oldFile  = new File(oldPath);
            if (oldFile.exists()) { //文件不存在时
                InputStream      inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs       = new FileOutputStream(newPath);
                byte[]           buffer   = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead; //字节数 文件大小
                    Logger.i(byteSum);
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Logger.i("复制单个文件操作出错");
            e.printStackTrace();
        }
    }
}