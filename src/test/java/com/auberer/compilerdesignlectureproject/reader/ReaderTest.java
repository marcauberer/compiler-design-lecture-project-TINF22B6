package com.auberer.compilerdesignlectureproject.reader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {
  private Reader reader;
  private final Path testFilePath = Paths.get("test-input.tinf");

  @BeforeEach
  public void setUp() throws IOException {
    // Create a test file
    Files.write(testFilePath, "Test content\nSecond line".getBytes());
    reader = new Reader(testFilePath);
  }

  @AfterEach
  public void tearDown() throws IOException {
    // Delete the test file
    Files.deleteIfExists(testFilePath);
    reader = null;
  }

  @Test
  public void testGetChar() {
    assertEquals('T', reader.getChar());
    reader.advance();
    assertEquals('e', reader.getChar());
  }

  @Test
  public void testGetCodeLoc() {
    assertEquals(new CodeLoc(1, 1), reader.getCodeLoc());
    reader.advance();
    assertEquals(new CodeLoc(1, 2), reader.getCodeLoc());
    for (int i = 0; i < 11; i++) {
      reader.advance();
    }
    assertEquals(new CodeLoc(2, 0), reader.getCodeLoc());
    for (int i = 0; i < 11; i++) {
      reader.advance();
    }
    assertEquals(new CodeLoc(2, 11), reader.getCodeLoc());
    reader.advance();
    assert reader.isEOF();
  }

  @Test
  public void testAdvance() {
    reader.advance();
    assertEquals('e', reader.getChar());
    assertEquals(new CodeLoc(1, 2), reader.getCodeLoc());
  }

  @Test
  public void testIsEOF() {
    assertFalse(reader.isEOF());
    // Consume all characters
    while (!reader.isEOF()) {
      reader.advance();
    }
    assertTrue(reader.isEOF());
  }
}
