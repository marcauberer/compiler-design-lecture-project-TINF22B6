package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class ASTAtomicExprNode extends ASTNode{
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAtomicExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        // The first set contains epsilon, so we also need the follow set of stmtLst
        Set<TokenType> followSet = Set.of(TokenType.TOK_EOF, TokenType.TOK_LOGICAL_AND, TokenType.TOK_LOGICAL_OR, TokenType.TOK_EQUAL, TokenType.TOK_NOT_EQUAL, TokenType.TOK_PLUS, TokenType.TOK_MINUS, TokenType.TOK_MUL, TokenType.TOK_DIV); //TODO: follow Assignexpr fehöt

        // The selection set of stmtLst contains the selection set of stmt and the follow set of stmtLst
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTStmtNode.getSelectionSet());
        // ToDo: Add selection set of ifStmt
        // ToDo: Add selection set of whileLoop
        // ToDo: Add selection set of doWhileLoop
        // ToDo: Add selection set of forLoop
        // ToDo: Add selection set of switchStmt
        selectionSet.addAll(followSet);
        return selectionSet;
    }
}
