package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.ast.ASTAfterIfContext.DataType;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ASTElseNode extends ASTNode {

  DataType type;
  
  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitElse(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(TokenType.TOK_LBRACE);
  }
}
