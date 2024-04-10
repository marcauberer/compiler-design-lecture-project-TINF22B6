package com.auberer.compilerdesignlectureproject.lexer;

import com.auberer.compilerdesignlectureproject.reader.IReader;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerTest {

    private static class MockReader implements IReader {
        private String input;
        private int position;
        private int lineNumber;
        private int columnNumber;

        public MockReader(String input) {
            this.input = input;
            this.position = 0;
            this.lineNumber = 1;
            this.columnNumber = 1;
        }

        @Override
        public char getChar() {
            if (position >= input.length()) {
                return '\0'; // End-of-file
            }
            return input.charAt(position);
        }

        @Override
        public CodeLoc getCodeLoc() {
            return new CodeLoc(lineNumber, columnNumber);
        }

        @Override
        public void advance() {
            if (position < input.length()) {
                if (input.charAt(position) == '\n') {
                    lineNumber++;
                    columnNumber = 1;
                } else {
                    columnNumber++;
                }
                position++;
            }
        }

        @Override
        public void expect(char c) throws Exception {
            if (getChar() != c) {
                throw new Exception("Expected character " + c + " at " + getCodeLoc() + " but got " + getChar());
            }
            advance();
        }

        @Override
        public boolean isEOF() {
            return position >= input.length();
        }
    }

    @Test
    void getToken_shouldReturnIntegerToken() throws Exception {
        String input = "123";
        Lexer lexer = new Lexer(new MockReader(input));
        Token token = lexer.getToken();
        assertEquals(TokenType.TOK_INTEGER, token.getType());
        assertEquals("123", token.getText());
    }

    @Test
    void getToken_shouldReturnDoubleToken() throws Exception {
        String input = "3.14";
        Lexer lexer = new Lexer(new MockReader(input));
        Token token = lexer.getToken();
        assertEquals(TokenType.TOK_DOUBLE, token.getType());
        assertEquals("3.14", token.getText());
    }

//    @Test
//    void advance_shouldAdvanceToNextToken() {
//        String input = "123";
//        Lexer lexer = new Lexer(new MockReader(input));
//        lexer.advance();
//        assertEquals('2', lexer.());
//    }

    @Test
    void expect_shouldThrowExceptionForIncorrectToken() {
        String input = "123";
        Lexer lexer = new Lexer(new MockReader(input));
        assertThrows(Exception.class, () -> lexer.expect(TokenType.TOK_IDENTIFIER));
    }

    @Test
    void expectOneOf_shouldThrowExceptionForIncorrectToken() {
        String input = "123";
        Lexer lexer = new Lexer(new MockReader(input));
        Set<TokenType> expectedTypes = Set.of(TokenType.TOK_IDENTIFIER);
        assertThrows(Exception.class, () -> lexer.expectOneOf(expectedTypes));
    }

    @Test
    void isEOF_shouldReturnTrueAtEndOfFile() {
        String input = "";
        Lexer lexer = new Lexer(new MockReader(input));
        assertTrue(lexer.isEOF());
    }

    @Test
    void getCodeLoc_shouldReturnCorrectCodeLocation() {
        String input = "123\n456";
        Lexer lexer = new Lexer(new MockReader(input));
        lexer.advance(); // Advance to '2'
        lexer.advance(); // Advance to '3'
        assertEquals(new CodeLoc(1, 3), lexer.getCodeLoc());
    }
}
