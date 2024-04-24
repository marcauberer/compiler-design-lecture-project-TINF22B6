package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ASTTypeNode extends ASTNode {

  public enum DataType {
    UNKNOWN,
    INT,
    DOUBLE,
    STRING,
    EMPTY
  }

  private DataType type = DataType.UNKNOWN;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitType(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(
        TokenType.TOK_TYPE_INT,
        TokenType.TOK_TYPE_DOUBLE,
        TokenType.TOK_TYPE_STRING,
        TokenType.TOK_TYPE_EMPTY
    );
  }
}
