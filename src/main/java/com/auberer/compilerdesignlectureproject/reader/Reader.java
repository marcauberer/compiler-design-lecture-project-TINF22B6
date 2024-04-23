package com.auberer.compilerdesignlectureproject.reader;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;

/**
 * Reader class for reading characters from a file.
 * Input: File path
 * Output: Character stream from file
 */
@Slf4j
public class Reader implements IReader {

  private BufferedReader inputReader;
  private char curChar;
  private final CodeLoc curCodeLoc = new CodeLoc(1, 0);
  private boolean eofReached = false;

  public Reader(Path path) {
    try {
      File file = path.toFile();
      FileReader fileReader = new FileReader(file);
      inputReader = new BufferedReader(fileReader);
      if (!inputReader.ready())
        throw new IOException("Reader error: Source file could not be read");

      // Read first character
      advance();
    } catch (IOException e) {
      log.error("Reader error in constructor: {}", e.getMessage());
    }
  }

  // Only for testing purposes
  public Reader(String input) {
    try {
      StringReader stringReader = new StringReader(input);
      inputReader = new BufferedReader(stringReader);
      assert inputReader.ready();

      // Read first character
      advance();
    } catch (IOException e) {
      log.error("Reader error in testing constructor: {}", e.getMessage());
      assert false;
    }
  }

  @Override
  public char getChar() {
    return curChar;
  }

  @Override
  public CodeLoc getCodeLoc() {
    return curCodeLoc;
  }

  @Override
  public void advance() {
    assert !isEOF();
    try {
      int input = inputReader.read();

      if (input == -1) {
        eofReached = true;
        return;
      }

      curChar = (char) input;
      if (curChar == '\n') {
        curCodeLoc.setLine(curCodeLoc.getLine() + 1);
        curCodeLoc.setColumn(0);
      } else {
        curCodeLoc.setColumn(curCodeLoc.getColumn() + 1);
      }
    } catch (IOException e) {
      log.error("Reader error in advance: {}", e.getMessage());
    }
  }

  @Override
  public void expect(char expectedChar) throws RuntimeException {
    if (curChar != expectedChar)
      throw new RuntimeException("Expected character '" + expectedChar + "' but got '" + curChar + "'");
    advance();
  }

  @Override
  public boolean isEOF() {
    return eofReached;
  }

  @Override
  public void close() {
    try {
      inputReader.close();
    } catch (IOException e) {
      log.error("Reader error in close: {}", e.getMessage());
    }
  }
}
