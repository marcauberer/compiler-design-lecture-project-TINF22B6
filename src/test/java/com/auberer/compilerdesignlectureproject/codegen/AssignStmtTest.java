package com.auberer.compilerdesignlectureproject.codegen;
import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.codegen.instructions.Instruction;
import com.auberer.compilerdesignlectureproject.interpreter.Value;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import com.auberer.compilerdesignlectureproject.sema.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssignStmtTest {
    @Test
    @DisplayName("Integration test - Codegen AssignStmt (Correct Input)")
    public void AssignStmtTestTrue() {
        String code = "x = 20;";

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTAssignStmtNode node = parser.parseAssignStmt();

        IRGenerator irGenerator = new IRGenerator("AssignStmt");
        BasicBlock basicBlock = new BasicBlock("Start-Block");
        irGenerator.setCurrentBlock(basicBlock);

        SymbolTableBuilder symboltable = new SymbolTableBuilder();
        symboltable.getCurrentScopes().peek().insertSymbol("x", node);
        SymbolTableEntry entry = symboltable.getCurrentScopes().peek().lookupSymbol("x", node);

        symboltable.visitAssignStmt(node);

        TypeChecker typeChecker = new TypeChecker();
        entry.updateType(new Type(SuperType.TY_INT));
        typeChecker.visitAssignStmt(node);

        entry.setValue(new Value(node));
        IRExprResult irExprResult = irGenerator.visitAssignStmt(node);

        assertEquals("x", node.getVariableName());
       // assertEquals(20, node.getLogicalExpr().getValue().getIntValue());
        assertNotNull(irExprResult.getEntry());

        assertTrue(irGenerator.getCurrentBlock().getLabel().equals("Start-Block"));
        assertTrue(irGenerator.getCurrentBlock().getInstructions().size() == 1);
        Instruction instruction = irGenerator.getCurrentBlock().getInstructions().get(0);
        assertTrue(irGenerator.getCurrentBlock().getInstructions().contains(instruction));

        StringBuilder sb = new StringBuilder();
        Function function = new Function("assignStmt");
        function.setEntryBlock(basicBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assertTrue(irCode.contains("Start-Block"));
    }
}