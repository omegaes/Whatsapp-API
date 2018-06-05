package com.mega4tech.whatsappapilibrary.model;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by ajaybhatt on 04/01/18.
 */

public class WMediaFile {

    private String mediaName;

    private String folderName;

    private String hashKey;

    private File file;

    public WMediaFile(File file) {
        this.file = file;
        mediaName = file.getName();
        hashKey = Base64.encodeToString(Hash.SHA256.checksum(file), Base64.DEFAULT).replace("\n","");
        folderName = file.getParent();
    }

    public WMediaFile(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof WMediaFile
                && ((WMediaFile) obj).getHashKey() != null && ((WMediaFile) obj).getHashKey().equals(hashKey);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    enum Hash {

        MD5("MD5"),
        MD4("MD4"),
        MD2("MD2"),
        SHA1("SHA1"),
        SHA256("SHA-256"),
        SHA512("SHA-512");

        private String name;

        Hash(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public byte[] checksum(File input) {
            try {
                InputStream in = new FileInputStream(input);
                MessageDigest digest = MessageDigest.getInstance(getName());
                byte[] block = new byte[1024];
                int length;
                while ((length = in.read(block)) > 0) {
                    digest.update(block, 0, length);
                }
                return digest.digest();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
