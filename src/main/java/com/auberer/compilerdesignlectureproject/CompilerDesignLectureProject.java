package com.auberer.compilerdesignlectureproject;

import com.auberer.compilerdesignlectureproject.antlr.ASTBuilder;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfLexer;
import com.auberer.compilerdesignlectureproject.antlr.gen.TInfParser;
import com.auberer.compilerdesignlectureproject.ast.ASTEntryNode;
import com.auberer.compilerdesignlectureproject.ast.ASTVisualizer;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CompilerDesignLectureProject {
  public static void main(String[] args) {
    Options cliOptions = new Options()
        .addOption("h", "help", false, "Print this help text")
        .addOption("antlr", "use-antlr-parser", false, "Use ANTLR generated parser")
        .addOption("tokens", "dump-tokens", false, "Dump the lexed tokens")
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

      ASTEntryNode ast;
      if (cli.hasOption("antlr")) {
        System.out.println("Compiling with ANTLR parser...");
        ast = parseWithANTLRParser(path);
      } else {
        System.out.println("Compiling with own parser...");

        boolean dumpTokens = cli.hasOption("tokens");
        ast = parseWithOwnParser(path, dumpTokens);
      }

      if (cli.hasOption("ast")) {
        System.out.println("Dumping AST...");
        assert ast != null;
        ASTVisualizer visualizer = new ASTVisualizer();
        String dot = visualizer.visitEntry(ast);
        System.out.println(dot);
      }
    } catch (ParseException e) {
      new HelpFormatter().printHelp("apache args...", cliOptions);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (args.length == 0) {
      System.out.println("No file path provided!");
      System.exit(1);
    }
  }

  static ASTEntryNode parseWithOwnParser(Path path, boolean dumpTokens) {
    // Create a new Reader object with the given file path
    Reader reader = new Reader(path);

    // Create lexer and parser
    Lexer lexer = new Lexer(reader, dumpTokens);
    Parser parser = new Parser(lexer);

    // Parse the input file
    return parser.parse();
  }

  static ASTEntryNode parseWithANTLRParser(Path path) {
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
      return (ASTEntryNode) astBuilder.visitEntry(entryContext);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
