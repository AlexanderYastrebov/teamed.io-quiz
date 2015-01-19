package ru.nullpointer.quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Please review the class below and suggest improvements. How would you
 * refactor this class if it would be in a real-life project? There are many
 * problems here, both high-level design mistakes, and low-level implementation
 * bugs. We're interested to see high-level problems first, since they are most
 * critical. The more mistakes you can spot, the better programmer you are.
 */
/**
 * This class is thread safe.
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
        StringBuilder sb;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding)) {
            sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        StringBuilder sb;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding)) {
            sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                if (c < 0x80) {
                    sb.append((char) c);
                }
            }
        }
        return sb.toString();
    }

    public void saveContent(String content) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), encoding)) {
            writer.write(content);
        }
    }
}
