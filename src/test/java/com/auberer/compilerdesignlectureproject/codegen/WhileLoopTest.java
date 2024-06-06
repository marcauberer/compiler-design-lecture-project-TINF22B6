package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTWhileLoopNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WhileLoopTest {

    @Test
    @DisplayName("Integration test - CodeGen WhileLoop (Correct Input)")
    void testWhileLoopIntegratedTypeCheckerCorrectInput() {
        String code = "while(true) { int i = 6; i = i-1; }";

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTWhileLoopNode node = parser.parseWhileLoop();

        IRGenerator irGenerator = new IRGenerator("whileLoop");
        BasicBlock basicBlock = new BasicBlock("entry");
        irGenerator.setCurrentBlock(basicBlock);

        IRExprResult irExprResult = irGenerator.visitWhileLoop(node);
        assertNull(irExprResult.getValue());
        assertTrue(irExprResult.getNode().equals(node));
        assertNull(irExprResult.getEntry());

        assertTrue(irGenerator.getCurrentBlock().getLabel().equals("while.exit"));

        StringBuilder sb = new StringBuilder();
        Function function = new Function("whileLoop");
        function.setEntryBlock(basicBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assertTrue(irCode.contains("entry"));
        assertTrue(irCode.contains("while.cond"));
        assertTrue(irCode.contains("while.body"));
        assertTrue(irCode.contains("while.exit"));
    }
}
