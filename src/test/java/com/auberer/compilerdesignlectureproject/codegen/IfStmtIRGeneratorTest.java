package com.auberer.compilerdesignlectureproject.codegen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.auberer.compilerdesignlectureproject.ast.ASTIfStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class IfStmtIRGeneratorTest {

    @ParameterizedTest
    @ValueSource(strings = {"""
            if (true == false) {
                print("XSLT");
            }
        """, """
            if (true == false) {
                print("XSLT");
            } else {
                print("Else Bad");
            }
        """, """
            if (true == false) {
                print("XSLT");
            } else if (false == true) {
                print("Else Bad");
            }
        """, """
            if (true == false) {
                print("XSLT");
            } else if (false == true) {
                print("Else Bad");
            } else {
                print("XML");
            }
        """})
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
        Function function = new Function("ifStatement");
        function.setEntryBlock(startBlock);
        StringBuilder sb = new StringBuilder();
        function.dumpIR(sb);

        System.out.printf("%s%n", sb);

        assertEquals(sb.toString().trim(), "function ifStatement: {\n}");
    }

}
