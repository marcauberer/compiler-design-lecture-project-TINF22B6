package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ASTAdditiveExprNode extends ASTNode{

    public enum AdditiveOperator {
        PLUS,
        MINUS
    }

    public List<AdditiveOperator> operatorList = new ArrayList<>();

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitAdditiveExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
      return ASTCompareExprNode.getSelectionSet();
    }

    public void operatorsListAdd(AdditiveOperator a) {
        operatorList.add(a);
    }
}
