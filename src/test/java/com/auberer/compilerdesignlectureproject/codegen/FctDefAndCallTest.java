package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.SymbolTableBuilder;
import com.auberer.compilerdesignlectureproject.sema.TypeChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FctDefAndCallTest {

    @Test
    @DisplayName("Integration test for function call")
    void testIntegrationTestForFunctionCall() {
        String fctDef = """
            
            func int myFunc(int x)
                    int i = 17;
                    return x;
            cnuf
            
            func empty main()
                    call myFunc(7);
                    return;
            cnuf
            
            """;

        Reader reader = new Reader(fctDef);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTEntryNode parseNode = parser.parse();


        ASTFctDefNode defNode = parseNode.getChild(ASTFctDefNode.class, 0);
        ASTFctDefNode defWithCallNode = parseNode.getChild(ASTFctDefNode.class, 1);

        ASTFctCallNode callNode = defWithCallNode.getChild(ASTLogicNode.class, 0).getChild(ASTStmtLstNode.class, 0).getChild(ASTStmtNode.class, 0).getChild(ASTAssignStmtNode.class, 0).getChild(ASTLogicalExprNode.class, 0).getChild(ASTCompareExprNode.class, 0).getChild(ASTAdditiveExprNode.class, 0).getChild(ASTMultiplicativeExprNode.class, 0).getChild(ASTPrefixExprNode.class, 0).getChild(ASTAtomicExprNode.class, 0).getFctCall();

        // Build symbol table

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        symbolTableBuilder.visit(parseNode);

        // Perform type checking
        TypeChecker typeChecker = new TypeChecker(parseNode);
        typeChecker.visit(parseNode);


        //definition of IRGenerator
        String moduleName = "test.tinf";
        IRGenerator defGenerator = new IRGenerator(moduleName);




        //fct def visit
        defGenerator.visitFctDef(defNode);
        defGenerator.visitFctDef(defWithCallNode);

        //fct Def codegen
        StringBuilder defBuilder = new StringBuilder();
        defGenerator.getModule().dumpIR(defBuilder);
        String irCode = defBuilder.toString();


        //fctDef Check
        assertTrue(irCode.contains("function myFunc(TY_INT x)"));
        assertTrue(irCode.contains("alloca x"));
        assertTrue(irCode.contains("store x"));
        assertTrue(irCode.contains("return"));

        //


        IRGenerator callGenerator = new IRGenerator(moduleName);
        callGenerator.visitFctCall(callNode);
        StringBuilder callBuilder = new StringBuilder();
        defGenerator.getModule().dumpIR(callBuilder);
        irCode = defBuilder.toString();
        System.out.println(irCode);



    }
}
