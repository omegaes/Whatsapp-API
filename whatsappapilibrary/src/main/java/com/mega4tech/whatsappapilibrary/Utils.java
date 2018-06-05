package com.mega4tech.whatsappapilibrary;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by aboodba on 08/03/2017.
 */

public class Utils {

    public static String getMimeType(Context context , File file) {
        String mimeType = null;
        Uri uri = Uri.fromFile(file);
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static String bytesToString(byte[] data) {
        StringBuilder s = new StringBuilder();
        for(byte b: data) {
            s.append((char)b);
        }
        return s.toString();
    }

    public static byte[] readFileContent(File file) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte fileContent[] = new byte[(int)file.length()];
            fin.read(fileContent);
            return fileContent;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String join(String delimiter, List<String> list) {
        if (list.size() == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append(delimiter);
        }
        String output = stringBuilder.toString();
        return output.substring(0, output.length() - 1);
    }

}
