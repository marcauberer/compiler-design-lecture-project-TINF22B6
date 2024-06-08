package com.auberer.compilerdesignlectureproject.codegen;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class VarDeclTest {

    @Test
    @DisplayName("Integration test - Codegen VarDecl (Correct Input)")
    public void VarDeclTesttrue() {
        String code = "int x = 10";

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTVarDeclNode node = parser.parseVarDecl();

        IRGenerator irGenerator = new IRGenerator("VarDecl");
        BasicBlock basicBlock = new BasicBlock("Start-Block");
        irGenerator.setCurrentBlock(basicBlock);

        IRExprResult irExprResult = irGenerator.visitVarDecl(node);

        assertEquals(irExprResult.getEntry().getType(), irExprResult.getNode().getType());
        assertEquals(irExprResult.getValue(), "x");
        assertEquals(irExprResult.getNode(), "10");
        assertNull(irExprResult.getEntry(), "");

        assertTrue(irGenerator.getCurrentBlock().getLabel().equals("Exit"));

        StringBuilder sb = new StringBuilder();
        Function function = new Function("whileLoop");
        function.setEntryBlock(basicBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assertTrue(irCode.contains("Start-Block"));
        assertTrue(irCode.contains("While-Condition"));
        assertTrue(irCode.contains("While-Body"));
        assertTrue(irCode.contains("Exit"));
    }
}