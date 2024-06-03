package com.auberer.compilerdesignlectureproject.sema;

import com.auberer.compilerdesignlectureproject.ast.ASTCasesNode;
import com.auberer.compilerdesignlectureproject.ast.ASTDefaultNode;
import com.auberer.compilerdesignlectureproject.ast.ASTSwitchStmtNode;
import com.auberer.compilerdesignlectureproject.lexer.Lexer;
import com.auberer.compilerdesignlectureproject.parser.Parser;
import com.auberer.compilerdesignlectureproject.reader.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchStmtTest {
    @Test
    @DisplayName("Integration test")
    void switchIntegrationTest() {
        String code = """
                switch(x){
                        case 1:
                            int i = 0;
                        case 2:
                            int i = 1;
                        default:
                            int i = 5;
                    }
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTSwitchStmtNode astSwitchStmt = parser.parseSwitchStmt();
        symbolTableBuilder.visitSwitchStmt(astSwitchStmt);
        ExprResult exprResult = typeChecker.visitSwitchStmt(astSwitchStmt);


        assertNotNull(astSwitchStmt);
        assertInstanceOf(ASTSwitchStmtNode.class, astSwitchStmt);
        assertInstanceOf(ASTCasesNode.class, astSwitchStmt.getCases());
        assertInstanceOf(ASTDefaultNode.class, astSwitchStmt.getDefault());
        assert(astSwitchStmt.getCases().getCasesSize() == 2);
        assert(astSwitchStmt.getCases().getStmtLists().size() == 2);
        assert(exprResult.getType().getSuperType().equals(SuperType.TY_EMPTY));
    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong switch type")
    void switchIntegrationTestExceptionType() {
        String code = """
                switch(true){
                        case 1:
                            int i = 0;
                        case 2:
                            int i = 1;
                        default:
                            int i = 5;
                    }
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTSwitchStmtNode astSwitchStmt = parser.parseSwitchStmt();
        symbolTableBuilder.visitSwitchStmt(astSwitchStmt);

        assertNotNull(astSwitchStmt);
        assertInstanceOf(ASTSwitchStmtNode.class, astSwitchStmt);
        assertInstanceOf(ASTCasesNode.class, astSwitchStmt.getCases());
        assertInstanceOf(ASTDefaultNode.class, astSwitchStmt.getDefault());
        assert(astSwitchStmt.getCases().getCasesSize() == 2);
        assert(astSwitchStmt.getCases().getStmtLists().size() == 2);

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitSwitchStmt(astSwitchStmt));
        assertEquals(exception.getMessage(), "Switch statement expects int, double or string, but got 'TY_BOOL'");

    }

    @Test
    @DisplayName("Integration test should throw SemaError with wrong case type")
    void switchIntegrationTestExceptionCaseType() {
        String code = """
                switch(x){
                         case 1:
                             int i = 0;
                         case 2.2:
                             int i = 3;
                         default:
                             int i = 5;
                     }
                    """;
        Reader reader = new Reader(code);
        Lexer lexer = new Lexer(reader, false);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        TypeChecker typeChecker = new TypeChecker();

        ASTSwitchStmtNode astSwitchStmt = parser.parseSwitchStmt();
        symbolTableBuilder.visitSwitchStmt(astSwitchStmt);

        assertNotNull(astSwitchStmt);
        assertInstanceOf(ASTSwitchStmtNode.class, astSwitchStmt);
        assertInstanceOf(ASTCasesNode.class, astSwitchStmt.getCases());
        assertInstanceOf(ASTDefaultNode.class, astSwitchStmt.getDefault());
        assert(astSwitchStmt.getCases().getCasesSize() == 2);
        assert(astSwitchStmt.getCases().getStmtLists().size() == 2);

        SemaError exception = Assertions.assertThrows(SemaError.class, () -> typeChecker.visitSwitchStmt(astSwitchStmt));
        assertEquals(exception.getMessage(), "Switch case expects 'INT_LIT' but got 'DOUBLE_LIT'");

    }
}
