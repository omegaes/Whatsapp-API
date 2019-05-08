package com.mega4tech.whatsappapilibrary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.mega4tech.whatsappapilibrary.exception.WhatsappNotInstalledException;
import com.mega4tech.whatsappapilibrary.liseteners.GetContactsListener;
import com.mega4tech.whatsappapilibrary.liseteners.SendMessageListener;
import com.mega4tech.whatsappapilibrary.model.WContact;
import com.mega4tech.whatsappapilibrary.model.WMessage;
import com.whatsapp.MediaData;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by aboodba on 02/03/2017.
 */

public class WhatsappApi {

    private static WhatsappApi instance;
    boolean isRootAvailable;

    private static  String whatsAppFolder;
    private static  String whatsAppHost;
    private static  String whatsAppName;

    private static String docFolder ;
    private static String imgFolder ;
    private static String vidFolder ;
    private static String audFolder ;
    private SQLiteDatabase db;

    public static WhatsappApi getInstance() {
        if (instance == null)
            instance = new WhatsappApi();
        return instance;
    }

    private WhatsappApi() {

        boolean suAvailable = Shell.SU.available();
        isWhatsappInstalled();

        if (suAvailable) {
            Shell.SU.run("am force-stop "+ whatsAppHost);
            Shell.SU.run("mount -o -R rw,remount " + whatsAppFolder);
            Shell.SU.run("mount -o rw,remount " + whatsAppFolder + "databases");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases");
            Shell.SU.run("chmod 777 "  + whatsAppFolder + "files");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "shared_prefs");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases/msgstore.db");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases/msgstore.db-wal");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases/msgstore.db-shm");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases/wa.db");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases/wa.db-wal");
            Shell.SU.run("chmod 777 " + whatsAppFolder + "databases/wa.db-shm");
            Shell.SU.run("ls -l " + whatsAppFolder + "databases/msgstore.db-shm");
            isRootAvailable = true;

        } else {

            isRootAvailable = false;
        }


    }

    public boolean isWhatsappInstalled() {
        File fileW = new File("/data/data/com.whatsapp/");
        File fileW4B = new File("/data/data/com.whatsapp.w4b/");
        boolean isInstalled = false;

        if(fileW.exists()){
            whatsAppFolder ="/data/data/com.whatsapp/";
            whatsAppName = "WhatsApp";
            whatsAppHost = "com.whatsapp";
            isInstalled = true;
        }
        if(fileW4B.exists()){
            whatsAppFolder ="/data/data/com.whatsapp.w4b/";
            whatsAppName = "WhatsApp Business";
            whatsAppHost = "com.whatsapp.w4b";
            isInstalled = true;
        }

        imgFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + whatsAppName + "/Media/"+ whatsAppName +" Images/Sent";
        vidFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + whatsAppName + "/Media/"+ whatsAppName +" Video/Sent";
        audFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + whatsAppName + "/Media/"+ whatsAppName +" Audio/Sent";
        docFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + whatsAppName + "/Media/"+ whatsAppName +" Documents/Sent";

        return isInstalled;
    }

    public void sendMessage(WContact contact, WMessage message, Context context, SendMessageListener listener) throws IOException, WhatsappNotInstalledException {
        List<WContact> contacts = new LinkedList<>();
        contacts.add(contact);
        sendMessage(contacts, message, context, listener);
    }

    public synchronized void sendMessage(final List<WContact> contacts, final WMessage message, final Context context, final SendMessageListener listener) throws IOException, WhatsappNotInstalledException {


        if (isWhatsappInstalled()) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    Shell.SU.run("am force-stop "+ whatsAppHost);
                    db = SQLiteDatabase.openOrCreateDatabase(new File(whatsAppFolder +"databases/msgstore.db"), null);

                    for (WContact contact : contacts) {
                        try {
                            sendMessage(contact, message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    db.close();
                    PackageManager pm = context.getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(whatsAppHost);
                    context.startActivity(intent);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean finish) {
                    super.onPostExecute(finish);
                    if (listener != null) {
                        listener.finishSendWMessage(contacts, message);
                    }
                }
            }.execute();
        } else
            throw new WhatsappNotInstalledException();

    }

    private void sendMessage(WContact contact, WMessage message) throws IOException {

        String name = null;
        Calendar c = null;
        String formattedDate = null;
        SimpleDateFormat df = null;
        File source = null;
        Random rand = null;
        File destination = null;

        switch (message.getType()) {
            case TEXT:
                break;
            case VIDEO:
                name = message.getFile().getPath();
                c = Calendar.getInstance();
                df = new SimpleDateFormat("yyyyMMMdd");
                formattedDate = df.format(c.getTime());
                source = new File(name);
                rand = new Random();
                destination = new File(vidFolder, "VID-" + formattedDate + "-WA" + (rand.nextInt(100) + rand.nextInt(75) + rand.nextInt(50)) + "." + FilenameUtils.getExtension(message.getFile().getName()));
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                name = destination.getName();
                break;
            case IMAGE:
                name = message.getFile().getPath();
                c = Calendar.getInstance();
                df = new SimpleDateFormat("yyyyMMdd");
                formattedDate = df.format(c.getTime());
                source = new File(name);
                rand = new Random();
                destination = new File(imgFolder, "IMG-" + formattedDate + "-WA" + (rand.nextInt(100) + rand.nextInt(75) + rand.nextInt(50)) + "." + FilenameUtils.getExtension(message.getFile().getName()));
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                name = destination.getName();
                break;
            case AUDIO:
                name = message.getFile().getPath();
                c = Calendar.getInstance();
                df = new SimpleDateFormat("yyyyMMdd");
                formattedDate = df.format(c.getTime());
                source = new File(name);
                rand = new Random();
                destination = new File(audFolder, "AUD-" + formattedDate + "-WA" + (rand.nextInt(100) + rand.nextInt(75) + rand.nextInt(50)) + "." + FilenameUtils.getExtension(message.getFile().getName()));
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                name = destination.getName();
                break;
            case DOCUMENT:
                name = message.getFile().getPath();
                c = Calendar.getInstance();
                df = new SimpleDateFormat("yyyyMMdd");
                formattedDate = df.format(c.getTime());
                source = new File(name);
                rand = new Random();
                destination = new File(docFolder, "DOC-" + formattedDate + "-WA" + (rand.nextInt(100) + rand.nextInt(75) + rand.nextInt(50)) + "." + FilenameUtils.getExtension(message.getFile().getName()));
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                name = destination.getName();
                break;
        }
        sendBigMessage(contact.getId(), message.getText(), name, message.getMime());
    }

    public synchronized void getContacts(Context context, final GetContactsListener listener) throws WhatsappNotInstalledException {

        if (isWhatsappInstalled()) {
            new AsyncTask<Void, Void, List<WContact>>() {
                @Override
                protected List<WContact> doInBackground(Void... params) {
                    Shell.SU.run("am force-stop "+ whatsAppHost);
                    //db = SQLiteDatabase.openOrCreateDatabase(new File(whatsAppFolder + "databases/wa.db"), null);
                    db = SQLiteDatabase.openDatabase(whatsAppFolder +"databases/wa.db", null,SQLiteDatabase.OPEN_READONLY);
                    List<WContact> contactList = new LinkedList<>();
                    String selectQuery = "SELECT  jid, display_name FROM wa_contacts where phone_type is not null and is_whatsapp_user = 1";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()) {
                        do {
                            WContact contact = new WContact(cursor.getString(1), cursor.getString(0));
                            contactList.add(contact);
                        } while (cursor.moveToNext());
                    }
                    db.close();
                    return contactList;
                }

                @Override
                protected void onPostExecute(List<WContact> contacts) {
                    super.onPostExecute(contacts);
                    if (listener != null) {
                        listener.receiveWhatsappContacts(contacts);
                    }
                }
            }.execute();


        } else
            throw new WhatsappNotInstalledException();


    }

    private void sendBigMessage(String jid, String msg, String file, String mimeType) {


        long l1;
        long l2;
        int k;
        String query2, query1;

        Random localRandom = new Random(20L);
        l1 = System.currentTimeMillis();
        l2 = l1 / 1000L;
        k = localRandom.nextInt();

        int mediaType = 0;

        if (mimeType == null || mimeType.length() < 2)
            mediaType = 0;
        else
            mediaType = (mimeType.contains("video")) ? 3
                    : (mimeType.contains("image")) ? 1
                    : (mimeType.contains("audio")) ? 2
                    : (mimeType.contains("pdf")) ? 9
                    : 0;

        ContentValues initialValues = new ContentValues();
        initialValues.put("key_remote_jid", jid);
        initialValues.put("key_from_me", 1);
        initialValues.put("key_id", l2 + "-" + k);
        initialValues.put("status", 1);
        initialValues.put("needs_push", 0);
        initialValues.put("timestamp", l1);
        initialValues.put("media_wa_type", mediaType);
        initialValues.put("media_name", file);
        initialValues.put("latitude", 0.0);
        initialValues.put("longitude", 0.0);
        initialValues.put("received_timestamp", l1);
        initialValues.put("send_timestamp", -1);
        initialValues.put("receipt_server_timestamp", -1);
        initialValues.put("receipt_device_timestamp", -1);
        initialValues.put("raw_data", -1);
        initialValues.put("recipient_count", 0);
        initialValues.put("media_duration", 0);

        if (!TextUtils.isEmpty(file) && !TextUtils.isEmpty(mimeType)) {
            //boolean isVideo = mimeType.contains("video");
            Bitmap bMap = null;
            File spec;
            if (mediaType == 3) {
                spec = new File(vidFolder, file);
                bMap = ThumbnailUtils.createVideoThumbnail(spec.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
            } else if(mediaType == 2) {
                spec = new File(audFolder, file);
            }else if(mediaType == 9) {
                spec = new File(docFolder, file);
                msg = spec.getName();
                bMap = BitmapFactory.decodeFile(spec.getAbsolutePath());
                initialValues.put("media_mime_type", mimeType);
                initialValues.put("media_duration", 1);
            }else{
                spec = new File(imgFolder, file);
                bMap = BitmapFactory.decodeFile(spec.getAbsolutePath());
            }
            long mediaSize = (file.equals("")) ? 0 : spec.length();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            if(mediaType == 1 || mediaType ==3) {
                bMap = Bitmap.createScaledBitmap(bMap, 100, 59, false);
                bMap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            }
            byte[] bArray = bos.toByteArray();

            MediaData md = new MediaData();
            md.fileSize = mediaSize;
            md.file = spec;
            md.autodownloadRetryEnabled = true;
            byte[] arr = SerializationUtils.serialize(md);

            initialValues.put("thumb_image", arr);
            initialValues.put("quoted_row_id", 0);
            //initialValues.put("media_mime_type", mimeType);
            //initialValues.put("media_hash", "9vZ3oZyplgiZ40jJvo/sLNrk3c1fuLOA+hLEhEjL+rg=");
            initialValues.put("raw_data", bArray);
            initialValues.put("media_size", mediaSize);
            initialValues.put("origin", 0);
            initialValues.put("media_caption", msg);
        } else
            initialValues.put("data", msg);

        long idm = db.insert("messages", null, initialValues);

        query1 = " insert into chat_list (key_remote_jid) select '" + jid
                + "' where not exists (select 1 from chat_list where key_remote_jid='" + jid + "');";

        query2 = " update chat_list set message_table_id = (select max(messages._id) from messages) where chat_list.key_remote_jid='" + jid + "';";


        ContentValues values = new ContentValues();
        values.put("docid", idm);
        values.put("c0content", "null  ");
        db.insert("messages_fts_content", null, values);


        db.execSQL(query1 + query2);
    }

    public boolean isRootAvailable() {
        return isRootAvailable;
    }
}
