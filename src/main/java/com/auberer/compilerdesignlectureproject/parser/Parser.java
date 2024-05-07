package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.TokenType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.Stack;

@Slf4j
@NoArgsConstructor
public class Parser implements IParser {

  // Lexer interface that can be used to accept the given input
  ILexer lexer;
  // Stack to keep track of the parent nodes
  Stack<ASTNode> parentStack = new Stack<>();

  public Parser(ILexer lexer) {
    this.lexer = lexer;
  }

  /**
   * Entry point to the parser. This method should parse the input file and return the root node of the AST.
   *
   * @return AST root node
   */
  @Override
  public ASTEntryNode parse() {
    ASTEntryNode node = new ASTEntryNode();
    enterNode(node);

    // Parse all the tokens until the end of the input file
    while (!lexer.isEOF()) {
      // ToDo: Team 6: Uncomment the following line as soon as function definitions work
      // parseFctDef();
    }

    exitNode(node);
    return node;
  }

  public ASTForNode parseForLoop() {
    ASTForNode node = new ASTForNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_FOR);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_SEMICOLON);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_SEMICOLON);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);
    lexer.expect(TokenType.TOK_LBRACE);
    parseStmtLst();
    lexer.expect(TokenType.TOK_RBRACE);

    exitNode(node);
    return node;
  }

  public ASTStmtLstNode parseStmtLst() {
    ASTStmtLstNode node = new ASTStmtLstNode();
    enterNode(node);

    // Parse all statements until the end of the statement list
    Set<TokenType> stmtLstSelectionSet = ASTStmtLstNode.getSelectionSet();
    Set<TokenType> stmtSelectionSet = ASTStmtNode.getSelectionSet();
    while (!stmtLstSelectionSet.contains(lexer.getToken().getType())) {
      if (stmtSelectionSet.contains(lexer.getToken().getType())) {
        parseStmt();
      } else {
        assert false : "Unexpected token in statement list";
      }
    }

    exitNode(node);
    return node;
  }

  public ASTStmtNode parseStmt() {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node);

    // Parse the statement
    // ToDo: Team 7: Uncomment as soon as varDecl and assignExpr work
    /*Set<TokenType> varDeclSelectionSet = ASTVarDeclNode.getSelectionSet();
    Set<TokenType> assignExprSelectionSet = ASTAssignExprNode.getSelectionSet();
    if (varDeclSelectionSet.contains(lexer.getToken().getType())) {
      parseVarDecl();
    } else if (assignExprSelectionSet.contains(lexer.getToken().getType())) {
      parseAssignExpr();
    } else {
      log.error("Unexpected token in statement");
    }*/

    exitNode(node);
    return node;
  }

  public ASTPrintBuiltinCallNode parsePrintBuiltinCall() {
    ASTPrintBuiltinCallNode node = new ASTPrintBuiltinCallNode();
    enterNode(node);

    // Parse the print builtin
    lexer.expect(TokenType.TOK_PRINT);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);

    exitNode(node);
    return node;
  }

  public ASTTypeNode parseType() {
    ASTTypeNode node = new ASTTypeNode();
    enterNode(node);

    // Parse the type
    if (lexer.getToken().getType() == TokenType.TOK_TYPE_INT) {
      lexer.expect(TokenType.TOK_TYPE_INT);
      node.setType(ASTTypeNode.DataType.INT);
    } else if (lexer.getToken().getType() == TokenType.TOK_TYPE_DOUBLE) {
      lexer.expect(TokenType.TOK_TYPE_DOUBLE);
      node.setType(ASTTypeNode.DataType.DOUBLE);
    } else if (lexer.getToken().getType() == TokenType.TOK_TYPE_STRING) {
      lexer.expect(TokenType.TOK_TYPE_STRING);
      node.setType(ASTTypeNode.DataType.STRING);
    } else if (lexer.getToken().getType() == TokenType.TOK_TYPE_EMPTY) {
      lexer.expect(TokenType.TOK_TYPE_EMPTY);
      node.setType(ASTTypeNode.DataType.EMPTY);
    } else {
      assert false : "Unexpected token in type";
    }

    exitNode(node);
    return node;
  }

  public ASTDoWhileLoopNode parseDoWhile() {
    ASTDoWhileLoopNode node = new ASTDoWhileLoopNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_DO);
    lexer.expect(TokenType.TOK_LBRACE);
    parseStmtLst();
    lexer.expect(TokenType.TOK_RBRACE);
    lexer.expect(TokenType.TOK_WHILE);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);
    lexer.expect(TokenType.TOK_SEMICOLON);

    exitNode(node);
    return node;
  }

  public ASTSwitchStmtNode parseSwitchStmt() {
    ASTSwitchStmtNode node = new ASTSwitchStmtNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_SWITCH);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);
    lexer.expect(TokenType.TOK_LBRACE);
    parseCases();
    if (ASTDefaultNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseDefault();
    }
    lexer.expect(TokenType.TOK_RBRACE);

    exitNode(node);
    return node;
  }

  public ASTCasesNode parseCases() {
    ASTCasesNode node = new ASTCasesNode();
    enterNode(node);

    while (ASTCasesNode.getSelectionSet().contains(lexer.getToken().getType())) {
      lexer.expect(TokenType.TOK_CASE);
      lexer.expectOneOf(Set.of(TokenType.TOK_INT_LIT, TokenType.TOK_DOUBLE_LIT, TokenType.TOK_STRING_LIT));
      lexer.expect(TokenType.TOK_COLON);
      parseStmtLst();
    }

    exitNode(node);
    return node;
  }

  public ASTDefaultNode parseDefault() {
    ASTDefaultNode node = new ASTDefaultNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_DEFAULT);
    lexer.expect(TokenType.TOK_COLON);
    parseStmtLst();

    exitNode(node);
    return node;

  }

  // ToDo: Method stub for other teams to rely on. Team 7: Implement this method
  public void parseAssignExpr() {
  }

  public ASTWhileLoopNode parseWhileLoop() {
    ASTWhileLoopNode node = new ASTWhileLoopNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_WHILE);
    lexer.expect(TokenType.TOK_LPAREN);
    parseAssignExpr();
    lexer.expect(TokenType.TOK_RPAREN);
    lexer.expect(TokenType.TOK_LBRACE);
    parseStmtLst();
    lexer.expect(TokenType.TOK_RBRACE);

    exitNode(node);
    return node;
  }

  public ASTFctDefNode parseFctDef() {
    ASTFctDefNode node = new ASTFctDefNode();
    enterNode(node);

    // Parse the print builtin
    lexer.expect(TokenType.TOK_FUNC);
    parseType();
    lexer.expect(TokenType.TOK_IDENTIFIER);
    lexer.expect(TokenType.TOK_LPAREN);
    if (ASTParamLstNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseParamLst();
    }
    lexer.expect(TokenType.TOK_RPAREN);
    parseLogic();
    lexer.expect(TokenType.TOK_CNUF);

    exitNode(node);
    return node;
  }

  public ASTParamLstNode parseParamLst() {
    ASTParamLstNode node = new ASTParamLstNode();
    enterNode(node);

    parseType();
    lexer.expect(TokenType.TOK_IDENTIFIER);
    while (lexer.getToken().getType() == TokenType.TOK_COMMA) {
      lexer.expect(TokenType.TOK_COMMA);
      parseType();
      lexer.expect(TokenType.TOK_IDENTIFIER);
    }
    exitNode(node);
    return node;
  }

  public ASTLogicNode parseLogic() {
    ASTLogicNode node = new ASTLogicNode();
    enterNode(node);

    parseStmtLst();
    lexer.expect(TokenType.TOK_RETURN);
    if (ASTAssignExprNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseParamLst();
    }
    lexer.expect(TokenType.TOK_SEMICOLON);
    exitNode(node);
    return node;
  }

  public ASTFctCallNode parseFctCall() {
    ASTFctCallNode node = new ASTFctCallNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_CALL);
    lexer.expect(TokenType.TOK_IDENTIFIER);
    lexer.expect(TokenType.TOK_LPAREN);
    if (ASTCallParamsNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseCallParams();
    }
    lexer.expect(TokenType.TOK_RPAREN);

    exitNode(node);
    return node;
  }

  public ASTCallParamsNode parseCallParams() {
    ASTCallParamsNode node = new ASTCallParamsNode();
    enterNode(node);

    parseAssignExpr();
    while (lexer.getToken().getType() == TokenType.TOK_COMMA) {
      lexer.expect(TokenType.TOK_COMMA);
      parseAssignExpr();
    }
    exitNode(node);
    return node;
  }

  public ASTLogicalExprNode parseLogicalExpression() {
    ASTLogicalExprNode node = new ASTLogicalExprNode();
    enterNode(node);

    parseCompareExpression();
    while (lexer.getToken().getType()==TokenType.TOK_LOGICAL_AND || lexer.getToken().getType()==TokenType.TOK_LOGICAL_OR) {
      lexer.expectOneOf(Set.of(TokenType.TOK_LOGICAL_AND, TokenType.TOK_LOGICAL_OR));
      parseCompareExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTCompareExprNode parseCompareExpression() {
    ASTCompareExprNode node = new ASTCompareExprNode();
    enterNode(node);

    parseAdditiveExpression();
    if (lexer.getToken().getType()==TokenType.TOK_EQUAL || lexer.getToken().getType()==TokenType.TOK_NOT_EQUAL) {
      lexer.expectOneOf(Set.of(TokenType.TOK_EQUAL, TokenType.TOK_NOT_EQUAL));
      parseAdditiveExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTAdditiveExprNode parseAdditiveExpression() {
    ASTAdditiveExprNode node = new ASTAdditiveExprNode();
    enterNode(node);

    parseMultiplicativeExpression();
    while (lexer.getToken().getType()==TokenType.TOK_PLUS || lexer.getToken().getType()==TokenType.TOK_MINUS) {
      lexer.expectOneOf(Set.of(TokenType.TOK_PLUS, TokenType.TOK_MINUS));
      parseMultiplicativeExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTMultiplicativeExprNode parseMultiplicativeExpression() {
    ASTMultiplicativeExprNode node = new ASTMultiplicativeExprNode();
    enterNode(node);

    parsePrefixExpression();
    while (lexer.getToken().getType()==TokenType.TOK_MUL || lexer.getToken().getType()==TokenType.TOK_DIV) {
      lexer.expectOneOf(Set.of(TokenType.TOK_MUL, TokenType.TOK_DIV));
      parsePrefixExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTPrefixExprNode parsePrefixExpression() {
    ASTPrefixExprNode node = new ASTPrefixExprNode();
    enterNode(node);

    if (lexer.getToken().getType()==TokenType.TOK_PLUS || lexer.getToken().getType()==TokenType.TOK_MINUS) {
      lexer.expectOneOf(Set.of(TokenType.TOK_PLUS, TokenType.TOK_MINUS));
      parseAtomicExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTAtomicExprNode parseAtomicExpression() {
    ASTAtomicExprNode node = new ASTAtomicExprNode();
    enterNode(node);

    switch (lexer.getToken().getType()) {
      case TokenType.TOK_INT_LIT: {
        lexer.expect(TokenType.TOK_INT_LIT);
        break;
      }
      case TokenType.TOK_DOUBLE_LIT: {
        lexer.expect(TokenType.TOK_DOUBLE_LIT);
        break;
      }
      case TokenType.TOK_STRING_LIT: {
        lexer.expect(TokenType.TOK_STRING_LIT);
        break;
      }
      case TokenType.TOK_TRUE: {
        lexer.expect(TokenType.TOK_TRUE);
        break;
      }
      case TokenType.TOK_FALSE: {
        lexer.expect(TokenType.TOK_FALSE);
        break;
      }
      case TokenType.TOK_IDENTIFIER: {
        lexer.expect(TokenType.TOK_IDENTIFIER);
        break;
      }
      case TokenType.TOK_LPAREN: {
        lexer.expect(TokenType.TOK_LPAREN);
        parseAssignExpr();
        lexer.expect(TokenType.TOK_RPAREN);
        break;
      }
      case TokenType.TOK_CALL: {
        parseFctCall();
        break;
      }
      case TokenType.TOK_PRINT: {
        parsePrintBuiltinCall();
        break;
      }
    }

    exitNode(node);
    return node;
  }

  private void enterNode(ASTNode node) {
    if (!parentStack.isEmpty()) {
      // Make sure the node is not pushed twice
      assert parentStack.peek() != node;
      // Link parent and child nodes, so we can traverse the tree
      ASTNode parent = parentStack.peek();
      parent.addChild(node);
      node.setParent(parent);
    }
    // Push the node to the stack
    parentStack.push(node);
  }

  private void exitNode(ASTNode node) {
    // Make sure the node is the last one pushed
    assert parentStack.peek() == node;
    // Remove the node from the stack
    parentStack.pop();
  }
}
