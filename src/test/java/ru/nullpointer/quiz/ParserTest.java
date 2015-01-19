package ru.nullpointer.quiz;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author Alexander Yastrebov
 */
public class ParserTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void testGetContent() throws IOException {
        Parser p = new Parser();
        p.setFile(new File("src/test/resources/hello.txt"));

        assertEquals("Hello world!", p.getContent());
    }

    @Test
    public void testGetContentWithoutUnicode() throws IOException {
        Parser p = new Parser();
        p.setFile(new File("src/test/resources/hello_unicode.txt"));

        assertEquals(" world!", p.getContentWithoutUnicode());
    }

    @Test
    public void testSaveContent() throws IOException {
        Parser p = new Parser();
        p.setFile(temp.newFile());

        p.saveContent("Bye world!");

        assertEquals("Bye world!", p.getContent());
    }

    @Test
    public void testSaveContentUnicode() throws IOException {
        Parser p = new Parser();
        p.setFile(temp.newFile());

        p.saveContent("Пока world!");

        assertEquals(" world!", p.getContentWithoutUnicode());
    }
}
