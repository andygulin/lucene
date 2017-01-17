package com.search.lucene.helper;

import org.apache.commons.lang.time.DateUtils;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Date;

public final class Fields {

    private Fields() {
    }

    public static Field newFileReader(String name, File file) {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Field(name, reader);
    }

    public static Field newFileReader(String name, String file) {
        return newFileReader(name, new File(file));
    }

    public static Field newString(String name, String value, Store store, Index index) {
        return new Field(name, value, store, index);
    }

    public static NumericField newInt(String name, int value, Store store, boolean index) {
        return new NumericField(name, store, index).setIntValue(value);
    }

    public static NumericField newFloat(String name, float value, Store store, boolean index) {
        return new NumericField(name, store, index).setFloatValue(value);
    }

    public static NumericField newDouble(String name, double value, Store store, boolean index) {
        return new NumericField(name, store, index).setDoubleValue(value);
    }

    public static NumericField newLong(String name, long value, Store store, boolean index) {
        return new NumericField(name, store, index).setLongValue(value);
    }

    public static NumericField newLong(String name, String date, Store store, boolean index) {
        String[] parsePattens = {"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd"};
        Date d = null;
        try {
            d = DateUtils.parseDate(name, parsePattens);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long value = d.getTime();
        return newLong(name, value, store, index);
    }

    public static NumericField newLong(String name, Date date, Store store, boolean index) {
        return newLong(name, date.getTime(), store, index);
    }
}
