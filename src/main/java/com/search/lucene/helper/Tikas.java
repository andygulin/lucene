package com.search.lucene.helper;

import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class Tikas {

    private Tikas() {
    }

    public static String parse(File file) {
        String content;
        Parser parser = new AutoDetectParser();
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
        try {
            parser.parse(is, handler, new Metadata(), new ParseContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
        content = handler.toString();
        return content;
    }
}