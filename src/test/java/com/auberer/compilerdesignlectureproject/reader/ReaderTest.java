package com.auberer.compilerdesignlectureproject.reader;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class ReaderTest {
  private Path testFilePath = null;

  @BeforeEach
  public void setUp() throws IOException {
    // Create a test file
    testFilePath = Files.createTempFile("", ".tmp");
    Files.write(testFilePath, "Test content\nSecond line".getBytes());
  }

  @AfterEach
  public void tearDown() {
    // Delete the test file
    try {
      Files.deleteIfExists(testFilePath);
    } catch (IOException e) {
      log.debug("Error deleting test file: {}", e.getMessage());
    }
  }

  @Test
  public void testGetChar() {
    Reader reader = new Reader(testFilePath);
    assertEquals('T', reader.getChar());
    reader.advance();
    assertEquals('e', reader.getChar());
  }

  @Test
  public void testGetCodeLoc() {
    Reader reader = new Reader(testFilePath);
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
    Reader reader = new Reader(testFilePath);
    reader.advance();
    assertEquals('e', reader.getChar());
    assertEquals(new CodeLoc(1, 2), reader.getCodeLoc());
  }

  @Test
  public void testIsEOF() {
    Reader reader = new Reader(testFilePath);
    assertFalse(reader.isEOF());
    // Consume all characters
    while (!reader.isEOF()) {
      reader.advance();
    }
    assertTrue(reader.isEOF());
  }
}
