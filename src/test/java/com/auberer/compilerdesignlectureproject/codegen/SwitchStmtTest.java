package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTSwitchStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchStmtTest {

    @Test
    void testSwitchStmtCorrectInput(){
        String code = """
                switch(6){
                        case 1:
                            int i = 0;
                        case 2:
                            int i = 1;
                        default:
                            int i = 4;
                    }
                """;

        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        ASTSwitchStmtNode astSwitchStmt = parser.parseSwitchStmt();

        astSwitchStmt.getCases().setCases(List.of("1", "2"));

        IRGenerator irGenerator = new IRGenerator("test_module");
        BasicBlock startBlock = new BasicBlock("start-block");
        irGenerator.setCurrentBlock(startBlock);

        IRExprResult result = irGenerator.visitSwitchStmt(astSwitchStmt);

        assertNull(result.getValue());
        assert(result.getNode().equals(astSwitchStmt));
        assertNull(result.getEntry());

        assert(irGenerator.getCurrentBlock().getLabel().equals("switch-end"));

        StringBuilder sb = new StringBuilder();
        Function function = new Function("switch-test");
        function.setEntryBlock(startBlock);
        function.dumpIR(sb);
        String irCode = sb.toString();
        assert(irCode.contains("switch-case 1"));
        assert(irCode.contains("switch-case 2"));
        assert(irCode.contains("switch-default"));
    }


}
