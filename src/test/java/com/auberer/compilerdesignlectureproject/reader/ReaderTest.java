package com.auberer.compilerdesignlectureproject.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReaderTest {
    @Test
    @DisplayName("This is a Test Test")
    public void test() {
        System.out.printf("This is a test:%n");
        Assertions.assertEquals("Test", "Test");
    }
}
