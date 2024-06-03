package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import com.auberer.compilerdesignlectureproject.reader.CodeLoc;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.ExprResult;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableEntry;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FunctionCallTest {

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
    @DisplayName("Test function call")
    void testFunctionCall() {
        Token identifier = (new Token(TokenType.TOK_IDENTIFIER, "", new CodeLoc(1, 1)));

        doNothing().when(lexer).expect(TokenType.TOK_CALL);
        doNothing().when(lexer).expect(TokenType.TOK_IDENTIFIER);
        doNothing().when(lexer).expect(TokenType.TOK_LPAREN);
        doReturn(null).when(parser).parseCallParams();
        doNothing().when(lexer).expect(TokenType.TOK_RPAREN);
        doReturn(identifier).when(lexer).getToken();

        ASTFctCallNode astFctCallNode = parser.parseFctCall();
        assertNotNull(astFctCallNode);

        verify(parser, times(1)).parseFctCall();
        verify(lexer, times(1)).expect(TokenType.TOK_CALL);
        verify(lexer, times(1)).expect(TokenType.TOK_IDENTIFIER);
        verify(lexer, times(1)).expect(TokenType.TOK_LPAREN);
        verify(lexer, times(1)).expect(TokenType.TOK_RPAREN);

        verify(parser, times(1)).parseCallParams();
    }

    @Test
    @DisplayName("Integration test for function call")
    void testIntegrationTestForFunctionCall() {
        String fctDef = "func int myFunc(int x) int i = 17; return x; cnuf func int fn2() call myFunc(7); return 2; cnuf";

        Reader reader = new Reader(fctDef);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTEntryNode parseNode = parser.parse();

        ASTFctDefNode defNode = parseNode.getChild(ASTFctDefNode.class, 0);
        ASTFctDefNode defWithCallNode = parseNode.getChild(ASTFctDefNode.class, 1);

        ASTFctCallNode callNode = defWithCallNode.getChild(ASTLogicNode.class, 0).getChild(ASTStmtLstNode.class, 0).getChild(ASTStmtNode.class, 0).getChild(ASTAssignStmtNode.class, 0).getChild(ASTLogicalExprNode.class, 0).getChild(ASTCompareExprNode.class, 0).getChild(ASTAdditiveExprNode.class, 0).getChild(ASTMultiplicativeExprNode.class, 0).getChild(ASTPrefixExprNode.class, 0).getChild(ASTAtomicExprNode.class, 0).getFctCall();

        SymbolTableBuilder symboltablebuilder = new SymbolTableBuilder();
        symboltablebuilder.visitFctDef(defNode);
        symboltablebuilder.visitFctCall(callNode);


        TypeChecker checker =  new TypeChecker(parseNode);
        checker.visitFctDef(defNode);
        checker.visitFctCall(callNode);
    }

    private static void searchNodeForFctCallNode(ASTNode node, String path) {
        if(node.getChildren().isEmpty()){
            System.out.println("no node found for path: " + path);
        }
        node.getChildren().forEach((child) -> {
            if (child instanceof ASTFctCallNode) {
                System.out.println(path + "ASTFctCallNode");
            }
            else {
                searchNodeForFctCallNode(child, path  + child.getClass().toString()+  " -> ");
            }
        });
    }
}
