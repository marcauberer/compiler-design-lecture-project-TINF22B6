package com.auberer.compilerdesignlectureproject.codegen;

import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IfStmtIRGeneratorTest {

    private static final String ifElseIfElseString = """
            if (true == false) {
                print("XSLT");
            } else if (false == true) {
                print("Else Bad");
            } else {
                print("XML");
            }
        """;
    private static final String ifElseIfString = """
            if (true == false) {
                print("XSLT");
            } else if (false == true) {
                print("Else Bad");
            }
        """;
    private static final String ifElseString = """
            if (true == false) {
                print("XSLT");
            } else {
                print("Else Bad");
            }
        """;
    private static final String ifString = """
            if (true == false) {
                print("XSLT");
            }
        """;

    @ParameterizedTest
    @ValueSource(strings = {ifString, ifElseString, ifElseIfString, ifElseIfElseString})
    @DisplayName("Test IfStmt IR Generator")
    public void testIfStmtIRGen(String s) {
        Reader reader = new Reader(s);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);
        ASTIfStmtNode ast = parser.parseIfStmt();

        BasicBlock startBlock = new BasicBlock("root");
        IRGenerator irGenerator = new IRGenerator("ifStatement");
        irGenerator.setCurrentBlock(startBlock);
        irGenerator.visitIf(ast);
        Function function = new Function("main", new ArrayList<>());
        function.setEntryBlock(startBlock);
        StringBuilder sb = new StringBuilder();
        function.dumpIR(sb);

        assertTrue(sb.toString().contains("function main(): {\n"));
    }

}
