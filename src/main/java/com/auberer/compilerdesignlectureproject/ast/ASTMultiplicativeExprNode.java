package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ASTMultiplicativeExprNode extends ASTNode {

    public enum MultiplicativeOperator {
        MUL,
        DIV
    }

    public List<MultiplicativeOperator> operatorList = new ArrayList<>();

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitMultiplicativeExpr(this);
    }

    public static Set<TokenType> getSelectionSet() {
      return ASTCompareExprNode.getSelectionSet();
    }

    public void operatorsListAdd(MultiplicativeOperator a) {
        operatorList.add(a);
    }

    public List<ASTPrefixExprNode> operands() {
        return getChildren(ASTPrefixExprNode.class);
    }
}
