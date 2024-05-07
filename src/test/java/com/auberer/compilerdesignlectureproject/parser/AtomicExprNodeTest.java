package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.ASTAtomicExprNode;
import com.auberer.compilerdesignlectureproject.ast.ASTCasesNode;
import com.auberer.compilerdesignlectureproject.ast.ASTLogicalExprNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AtomicExprNodeTest {
    @Spy
    private Parser parser; // Use spy to allow partial mocking

    @Mock
    private Lexer lexer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = new Parser(lexer);
        parser = spy(parser);
    }

    @Test
    @DisplayName("Test atomic expression")
    void testAtomicExpr() {
        // Arrange
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doReturn(new Token(TokenType.TOK_IDENTIFIER, "test", new CodeLoc(1, 1))).when(lexer).getToken();

        // Execute parse method
        ASTAtomicExprNode atomicExprNode = parser.parseAtomicExpression();

        // Assert
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        assertNotNull(atomicExprNode);
        assertInstanceOf(ASTAtomicExprNode.class, atomicExprNode);
    }
}
