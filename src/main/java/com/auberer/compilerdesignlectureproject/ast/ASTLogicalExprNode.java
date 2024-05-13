package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ASTLogicalExprNode extends ASTNode{

    public static List<LogicalOperator> operatorList = new ArrayList<LogicalOperator>();

    public static void operatorsListAdd(LogicalOperator a){
        operatorList.add(a);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitLogicalExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTPrefixExprNode.getSelectionSet());
        return selectionSet;
    }

    public enum LogicalOperator {
        AND,
        OR
    }
}



