package com.auberer.compilerdesignlectureproject.ast;

import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.Setter;

import java.util.Set;

@Setter
public class ASTAtomicExprNode extends ASTNode {

  public enum AtomicOperator {
    INT_LIT,
    DOUBLE_LIT,
    STRING_LIT,
    BOOL_LIT,
    IDENTIFIER,
    FCT_CALL,
    PRINT_BUILTIN_CALL,
    ASSIGN_EXPR
  }

  public AtomicOperator operator;
  public int intLit;
  public double doubleLit;
  public String stringLit;
  public boolean boolLit;
  public String identifier;

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visitAtomicExpr(this);
  }

  public static Set<TokenType> getSelectionSet() {
    return Set.of(
        TokenType.TOK_INT_LIT,
        TokenType.TOK_DOUBLE_LIT,
        TokenType.TOK_STRING_LIT,
        TokenType.TOK_IDENTIFIER,
        TokenType.TOK_CALL,
        TokenType.TOK_PRINT,
        TokenType.TOK_LPAREN
    );
  }
}
