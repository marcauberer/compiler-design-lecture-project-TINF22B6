package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.Token;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ASTAdditiveExprNode extends ASTNode{

    public static List<AdditiveOperator> operatorList = new ArrayList<AdditiveOperator>();

    public static void operatorsListAdd(AdditiveOperator a){
        operatorList.add(a);
    }

    public List<AdditiveOperator> operatorsList = new ArrayList<>();

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAdditiveExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
        Set<TokenType> selectionSet = new HashSet<>();
        selectionSet.addAll(ASTPrefixExprNode.getSelectionSet());
        return selectionSet;
    }

    public enum AdditiveOperator {
        PLUS,
        MINUS
    }
}
