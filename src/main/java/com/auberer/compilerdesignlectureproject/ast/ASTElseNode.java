package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ASTElseNode extends ASTNode {
  private ASTAfterIfContext astAfterIfContext;

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_LBRACE);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitElse(this);
  }

}


