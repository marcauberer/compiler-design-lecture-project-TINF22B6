package com.auberer.compilerdesignlectureproject;

import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.reader.Reader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CompilerDesignLectureProject {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("No file path provided!");
      System.exit(1);
    }

    // Convert the first command line argument to a Path
    Path path = Paths.get(args[0]).toAbsolutePath();

    // Create a new Reader object with the given file path
    Reader reader = new Reader(path);

    // Trigger the lexer
    Lexer lexer = new Lexer(reader);

    // Print the tokens
    System.out.println("Tokens:");
    while (!lexer.isEOF()) {
      System.out.println(lexer.getToken());
      lexer.advance();
    }
  }
}
