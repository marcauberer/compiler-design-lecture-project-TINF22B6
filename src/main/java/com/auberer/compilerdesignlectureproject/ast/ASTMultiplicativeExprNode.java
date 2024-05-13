package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ASTMultiplicativeExprNode extends ASTNode{

    public static List<MultiplicativeOperator> operatorList = new ArrayList<MultiplicativeOperator>();

    public static void operatorsListAdd(MultiplicativeOperator a){
        operatorList.add(a);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitMultiplicativeExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTPrefixExprNode.getSelectionSet());
        return selectionSet;
    }

    public enum MultiplicativeOperator {
        MUL,
        DIV
    }
}
