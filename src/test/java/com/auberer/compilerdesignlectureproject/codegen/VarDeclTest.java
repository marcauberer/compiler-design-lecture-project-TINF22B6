package com.auberer.compilerdesignlectureproject.codegen;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VarDeclTest {

    @Test
    @DisplayName("Integration test - Codegen VarDecl (Correct Input)")
    public void VarDeclTestTrue() {
        String code = "int x = 20";

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTVarDeclNode node = parser.parseVarDecl();

        IRGenerator irGenerator = new IRGenerator("VarDecl");
        BasicBlock basicBlock = new BasicBlock("Start-Block");
        irGenerator.setCurrentBlock(basicBlock);

        IRExprResult irExprResult = irGenerator.visitVarDecl(node);

        assertEquals(irExprResult.getValue(), node.getCurrentSymbol().getType());
        assertEquals(irExprResult.getNode(), node);
        assertEquals(irExprResult.getEntry(), node.getCurrentSymbol());

        assertTrue(irGenerator.getCurrentBlock().getLabel().equals("Exit"));

        StringBuilder sb = new StringBuilder();
        Function function = new Function("varDecl");
        function.setEntryBlock(basicBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assertTrue(irCode.contains("Start-Block"));
        assertTrue(irCode.contains("VarDecl-Body"));
        assertTrue(irCode.contains("Exit"));
    }
}
