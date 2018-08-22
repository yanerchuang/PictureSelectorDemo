package com.ywj.pictureselectordemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by weijing on 2017-07-05 10:46.
 */

public class FileUtils {
    public static void copy(File s, File t) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);

            in = fi.getChannel();// 得到对应的文件通道

            out = fo.getChannel();// 得到对应的文件通道

            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
