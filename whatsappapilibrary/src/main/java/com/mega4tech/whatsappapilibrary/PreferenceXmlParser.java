package com.mega4tech.whatsappapilibrary;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

/**
 * Created by ajaybhatt on 03/01/18.
 */

public class PreferenceXmlParser {

    private XmlPullParser mParser;
    private static final String NS = null;

    public PreferenceXmlParser(String data) {
        try {
            mParser = Xml.newPullParser();
            mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            mParser.setInput(new StringReader(data));
        } catch (Exception e) {
            //
        }
    }

    public PreferenceXmlParser(byte[] data) {
        try {
            mParser = Xml.newPullParser();
            mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            mParser.setInput(new StringReader(Utils.bytesToString(data)));
        } catch (Exception e) {
            //
        }
    }

    public PreferenceXmlParser(File file) {
        try {
            mParser = Xml.newPullParser();
            mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            FileInputStream fin = new FileInputStream(file);
            mParser.setInput(fin, null);
        } catch (Exception e) {
            //
        }
    }

    @SuppressWarnings("unused")
    public HashMap<String, Object> parse() throws XmlPullParserException, IOException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        mParser.nextTag();
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (mParser.getName().equals("string")) {
                readString(mParser, hashMap);
            } else if (mParser.getName().equals("int")) {
                readInt(mParser, hashMap);
            } else {
                skip(mParser);
            }
        }
        return hashMap;
    }

    private void readString(XmlPullParser parser, HashMap<String, Object> hashMap) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NS, "string");
        String contactName = parser.getAttributeValue(null, "name");
//        String contactInfo = parser.getAttributeValue(null, "value");
        String contactInfo = "";
        if (parser.next() == XmlPullParser.TEXT) {
            contactInfo = parser.getText();
            parser.nextTag();
        }
        hashMap.put(contactName, contactInfo);
        parser.require(XmlPullParser.END_TAG, NS, "string");
    }

    private void readInt(XmlPullParser parser, HashMap<String, Object> hashMap) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NS, "int");
        String contactName = parser.getAttributeValue(null, "name");
        int contactInfo = Integer.parseInt(parser.getAttributeValue(null, "value"));
        parser.nextTag();
        hashMap.put(contactName, contactInfo);
        parser.require(XmlPullParser.END_TAG, NS, "int");
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
