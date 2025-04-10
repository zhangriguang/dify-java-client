package io.github.imfangs.dify.client.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTypeTest {

    @Test
    void getByFileExtension() {
        assertEquals(FileType.DOCUMENT, FileType.getByFileExtension("docx"));
        assertEquals(FileType.DOCUMENT, FileType.getByFileExtension("test.pdf"));
        assertEquals(FileType.IMAGE, FileType.getByFileExtension(".jpg"));
        assertEquals(FileType.IMAGE, FileType.getByFileExtension("png"));
        assertEquals(FileType.AUDIO, FileType.getByFileExtension("test.mp3"));
        assertEquals(FileType.AUDIO, FileType.getByFileExtension("test.amr"));
        assertEquals(FileType.VIDEO, FileType.getByFileExtension("MP4"));
        assertEquals(FileType.VIDEO, FileType.getByFileExtension(".MPEG"));
        assertEquals(FileType.CUSTOM, FileType.getByFileExtension("test.cad"));
        assertEquals(FileType.CUSTOM, FileType.getByFileExtension("INI"));
    }
}