package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FctCallTest {

    @Test
    @DisplayName("Integration test for function call")
    void testIntegrationTestForFunctionCall() {
        String fctDef = """
            func int myFunc(int x)
                int i = 17;
                return x;
            cnuf
            
            func int fn2()
                call myFunc(7);
                return 2;
            cnuf
            """;

        Reader reader = new Reader(fctDef);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTEntryNode parseNode = parser.parse();

        ASTFctDefNode defNode = parseNode.getChild(ASTFctDefNode.class, 0);
        ASTFctDefNode defWithCallNode = parseNode.getChild(ASTFctDefNode.class, 1);

        ASTFctCallNode callNode = defWithCallNode.getChild(ASTLogicNode.class, 0).getChild(ASTStmtLstNode.class, 0).getChild(ASTStmtNode.class, 0).getChild(ASTAssignStmtNode.class, 0).getChild(ASTLogicalExprNode.class, 0).getChild(ASTCompareExprNode.class, 0).getChild(ASTAdditiveExprNode.class, 0).getChild(ASTMultiplicativeExprNode.class, 0).getChild(ASTPrefixExprNode.class, 0).getChild(ASTAtomicExprNode.class, 0).getFctCall();

        /*IRGenerator generator =  new IRGenerator("");
        generator.visitFctDef(defNode);
        generator.visitFctCall(callNode);
         */
    }
}
