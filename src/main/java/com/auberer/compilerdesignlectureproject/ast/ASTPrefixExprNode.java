package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
public class ASTPrefixExprNode extends ASTNode {

  public enum PrefixOperator {
    PLUS,
    MINUS
  }

  public PrefixOperator operator;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitPrefixExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    Set<TokenType> selectionSet = new HashSet<>();
    selectionSet.add(TokenType.TOK_PLUS);
    selectionSet.add(TokenType.TOK_MINUS);
    selectionSet.addAll(ASTAtomicExprNode.getSelectionSet());
    return selectionSet;
  }

    public ASTNode operands() {
        return getChild(ASTAtomicExprNode.class, 0);
    }
}
