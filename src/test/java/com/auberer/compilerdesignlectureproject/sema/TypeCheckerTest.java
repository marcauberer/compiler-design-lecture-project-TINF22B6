package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TypeCheckerTest
 */
public class TypeCheckerTest {

    @Test
    public void checkIfExprResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
                        if (true == false) {
                            print("XSLT");
                        }
                        """);

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitIf(ifStmtNode);

        assertEquals(SuperType.TY_EMPTY.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }

    @Test
    public void checkIfElseExprResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
                        if (true == false) {
                            print("XSLT");
                        } else {
                            print("http://www.jroethig.de/");
                        }
                        """);

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitIf(ifStmtNode);

        assertEquals(SuperType.TY_EMPTY.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());

        assertEquals(SuperType.TY_EMPTY.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }

    @Test
    public void checkElseIfExprResult() {
        // Create a new Reader object with the given file path
        Reader reader = new Reader(
                """
                        if (true == false) {
                            print("XSLT");
                        } else if (true == false) {
                            print("http://www.jroethig.de/");
                        }
                        """);

        // Create lexer and parser
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        // Execute parse method
        ASTIfStmtNode ifStmtNode = parser.parseIfStmt();

        TypeChecker checker = new TypeChecker();
        ExprResult result = checker.visitIf(ifStmtNode);

        assertEquals(SuperType.TY_EMPTY.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());

        assertEquals(SuperType.TY_EMPTY.toString(), result.getType().toString());
        assertEquals(null, result.getEntry());
    }
}
