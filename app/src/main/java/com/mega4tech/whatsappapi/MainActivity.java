package com.mega4tech.whatsappapi;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mega4tech.whatsappapilibrary.WhatsappApi;
import com.mega4tech.whatsappapilibrary.exception.WhatsappNotInstalledException;
import com.mega4tech.whatsappapilibrary.liseteners.GetContactsListener;
import com.mega4tech.whatsappapilibrary.liseteners.SendMessageListener;
import com.mega4tech.whatsappapilibrary.model.WContact;
import com.mega4tech.whatsappapilibrary.model.WMessage;
import com.miguelgaeta.media_picker.MediaPicker;
import com.miguelgaeta.media_picker.RequestType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPicker.Provider, MediaPicker.OnError {

    String[] mContacts;
    boolean[] mSelectedContacts;

    HashMap<WContact, Integer> mDictionary;
    List<WContact> mAllContacts;
    List<WContact> mReceivers;
    File attachmentFile;


    private RecyclerView mContactsRv;
    private Button mAddContactsBtn;
    private EditText mMessageTextTv;
    private Button mSendMsgBtn;
    private TextView mAttachmentTv;
    private Button mAddAttachmentBtn;
    private ContactCardRecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDictionary = new HashMap<>();
        mReceivers = new LinkedList<>();

        if (!WhatsappApi.getInstance().isWhatsappInstalled()) {
            Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!WhatsappApi.getInstance().isRootAvailable()) {
            Toast.makeText(this, "Root is not available", Toast.LENGTH_SHORT).show();
            return;
        }

        setContentView(R.layout.activity_main);
        initView();

        mSendMsgBtn.setOnClickListener(this);
        mAddContactsBtn.setOnClickListener(this);
        mAddAttachmentBtn.setOnClickListener(this);
        mAddContactsBtn.setEnabled(false);
        mSendMsgBtn.setEnabled(false);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        mContactsRv.setLayoutManager(mLayoutManager);
        mContactsRv.setItemAnimator(new DefaultItemAnimator());

        try {
            WhatsappApi.getInstance().getContacts(this, new GetContactsListener() {
                @Override
                public void receiveWhatsappContacts(List<WContact> contacts) {
                    mAllContacts = contacts;
                    mAddContactsBtn.setEnabled(true);
                    mSendMsgBtn.setEnabled(true);
                    mContacts = new String[contacts.size()];
                    mSelectedContacts = new boolean[contacts.size()];
                    mAdapter = new ContactCardRecyclerViewAdapter(MainActivity.this, mReceivers, mContacts, mSelectedContacts, mDictionary);
                    mContactsRv.setAdapter(mAdapter);
                    int i = 0;
                    for (WContact contact : contacts) {
                        mContacts[i] = contact.getName();//+ ", " + contact.getId().split("@")[0];
                        i++;
                    }
                }
            });
        } catch (WhatsappNotInstalledException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        mContactsRv = (RecyclerView) findViewById(R.id.contacts_rv);
        mAddContactsBtn = (Button) findViewById(R.id.add_contacts_btn);
        mMessageTextTv = (EditText) findViewById(R.id.message_text_tv);
        mSendMsgBtn = (Button) findViewById(R.id.send_msg_btn);
        mAttachmentTv = (TextView) findViewById(R.id.attachment_tv);
        mAddAttachmentBtn = (Button) findViewById(R.id.add_attachment_btn);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_contacts_btn:
                new AlertDialog.Builder(this)
                        .setMultiChoiceItems(mContacts, mSelectedContacts, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                mSelectedContacts[which] = isChecked;
                                if (isChecked) {
                                    mReceivers.add(mAllContacts.get(which));
                                    mDictionary.put(mAllContacts.get(which), which);
                                } else {
                                    mReceivers.remove(mAllContacts.get(which));
                                    mDictionary.remove(mAllContacts.get(which));

                                }
                            }
                        })
                        .setPositiveButton(R.string.ok_button_label, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                mAdapter.setmObjects(mReceivers);

                            }
                        })
                        .show();
                break;

            case R.id.add_attachment_btn:
                new TedPermission(this)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                MediaPicker.startForDocuments(MainActivity.this, MainActivity.this);
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                                Toast.makeText(MainActivity.this, "We need \"read file from storage\" permission to select attachment", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
                break;
            case R.id.send_msg_btn:
                if (mReceivers.size() < 1) {
                    Toast.makeText(this, "You should select one receiver at least", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mReceivers.size() > 5) {
                    Toast.makeText(this, "You should select less than 5 receivers, demo version !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mMessageTextTv.getText()) && attachmentFile == null) {
                    Toast.makeText(this, "please enter your message or select a media file to send", Toast.LENGTH_SHORT).show();
                    return;
                }
                String text = (!TextUtils.isEmpty(mMessageTextTv.getText())) ? mMessageTextTv.getText().toString() : "";
                WMessage message = new WMessage(text, attachmentFile, this);

                try {
                    WhatsappApi.getInstance().sendMessage(mReceivers, message, this, new SendMessageListener() {
                        @Override
                        public void finishSendWMessage(List<WContact> contact, WMessage message) {
                            Toast.makeText(MainActivity.this, "your message has been sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (WhatsappNotInstalledException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        MediaPicker.handleActivityResult(this, requestCode, resultCode, data, new MediaPicker.OnResult() {

            @Override
            public void onError(IOException e) {
                Log.e("MediaPicker", "Got file error.", e);
                e.printStackTrace();
                attachmentFile = null;
                mAttachmentTv.setText("");
            }

            @Override
            public void onSuccess(File mediaFile, String mimeType, RequestType request) {
                attachmentFile = mediaFile;
                mAttachmentTv.setText("Attachment file: " + mediaFile.getName());
            }

            @Override
            public void onCancelled() {
                Log.e("MediaPicker", "Got cancelled event.");
                attachmentFile = null;
                mAttachmentTv.setText("");
            }
        });
    }

    @Override
    public void onError(IOException e) {
        e.printStackTrace();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public File getImageFile() {
        return null;
    }
}
