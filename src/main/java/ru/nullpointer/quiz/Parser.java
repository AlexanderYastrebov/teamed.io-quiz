package ru.nullpointer.quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Read and writes content to/from file. This class is thread safe.
 *
 * @todo Support more general data source by using java.io.Reader or a set of
 * constructors
 * @todo Separate storing and reading data into different classes
 * @todo There should be a saveContentWithoutUnicode pair method
 */
public class Parser {

    private final File file;
    private final String encoding;

    public Parser(File file, String encoding) {
        this.file = file;
        this.encoding = encoding;
    }

    public File getFile() {
        return file;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getContent() throws IOException {
        return read(ANY_FILTER);
    }

    public String getContentWithoutUnicode() throws IOException {
        return read(UNICODE_FILTER);
    }

    public void saveContent(String content) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), encoding)) {
            writer.write(content);
        }
    }

    private String read(CharFilter filter) throws IOException {
        StringBuilder sb;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding)) {
            sb = new StringBuilder((int) file.length());
            int c;
            while ((c = reader.read()) != -1) {
                if (filter.apply(c)) {
                    sb.append((char) c);
                }
            }
        }
        return sb.toString();
    }

    private interface CharFilter {

        boolean apply(int ch);
    }
    //
    private static final CharFilter ANY_FILTER = new CharFilter() {

        @Override
        public boolean apply(int ch) {
            return true;
        }
    };
    //
    private static final CharFilter UNICODE_FILTER = new CharFilter() {

        @Override
        public boolean apply(int ch) {
            return (ch < 0x80);
        }

    };
}
