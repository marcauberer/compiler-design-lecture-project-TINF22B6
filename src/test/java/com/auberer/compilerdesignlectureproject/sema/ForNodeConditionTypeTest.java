package com.auberer.compilerdesignlectureproject.sema;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auberer.compilerdesignlectureproject.ast.ASTForNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ForNodeConditionTypeTest {
    private Parser parserCorrect;
    private Parser parserFalse;
    private TypeChecker typeChecker;

    @BeforeEach
    void setUp() {
        Reader readerCorrect = new Reader("for (int i = 0; i == 10; i = i + 1) {  } cnuf");
        Lexer lexerCorrect = new Lexer(readerCorrect, false);
        parserCorrect = new Parser(lexerCorrect);

        Reader readerFalse = new Reader("for (int i = 0; 10.5; i = i + 1) {  } cnuf");
        Lexer lexerFalse = new Lexer(readerFalse, false);
        parserFalse = new Parser(lexerFalse);

        typeChecker = new TypeChecker(null);
    }

    // Test correct condition type
    @Test
    void testCorrectForNodeConditionType() {
        ASTForNode forNode = assertDoesNotThrow(() -> parserCorrect.parseForLoop());
        assertDoesNotThrow(() -> typeChecker.visitForLoop(forNode));
    }

    // Test wrong condition type
    @Test
    void testForNodeConditionType() {
        ASTForNode forNode = assertDoesNotThrow(() -> parserFalse.parseForLoop());

        SemaError thrown = assertThrows(
            SemaError.class,
            () -> typeChecker.visitForLoop(forNode),
            "For loop condition must be a boolean expression"
        );

        assertTrue(thrown.getMessage().contains("For loop condition must be a boolean expression"));
    }
}
