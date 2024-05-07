package com.auberer.compilerdesignlectureproject;

import com.auberer.compilerdesignlectureproject.antlr.ASTBuilder;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfLexer;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CompilerDesignLectureProject {
  public static void main(String[] args) {
    Options cliOptions = new Options()
        .addOption("h", "help", false, "Print this help text")
        .addOption("antlr", "use-antlr-parser", false, "Use ANTLR generated parser")
        .addOption("ast", "dump-ast", false, "Dump the AST as dot file");

    DefaultParser cliParser = new DefaultParser();
    try {
      CommandLine cli = cliParser.parse(cliOptions, args);
      if (cli.hasOption('h')) {
        new HelpFormatter().printHelp("apache args...", cliOptions);
        System.exit(0);
      }

      // Convert the first command line argument to a Path
      String[] positionalArgs = cli.getArgs();
      Path path = Paths.get(positionalArgs[0]).toAbsolutePath();

      if (cli.hasOption("antlr")) {
        System.out.println("Compiling with ANTLR parser...");
        compileAntlrParser(path);
      } else {
        System.out.println("Compiling with own parser...");
        compileOwnParser(path);
      }
    } catch (Exception e) {
      new HelpFormatter().printHelp("apache args...", cliOptions);
    }

    if (args.length == 0) {
      System.out.println("No file path provided!");
      System.exit(1);
    }
  }

  static void compileOwnParser(Path path) {
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
    // Print last token
    System.out.println(lexer.getToken());
  }

  static void compileAntlrParser(Path path) {
    try {
      // Setup ANTLR
      ANTLRInputStream input = new ANTLRFileStream(path.toString());

      // Lex
      TInfLexer lexer = new TInfLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);

      // Parse
      TInfParser parser = new TInfParser(tokens);
      TInfParser.EntryContext entryContext = parser.entry();

      // Transform parse tree to AST
      ASTBuilder astBuilder = new ASTBuilder();
      astBuilder.visit(entryContext);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
