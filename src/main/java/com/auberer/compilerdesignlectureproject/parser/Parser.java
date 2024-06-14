package com.auberer.compilerdesignlectureproject.parser;

import com.auberer.compilerdesignlectureproject.ast.*;
import com.auberer.compilerdesignlectureproject.lexer.ILexer;
import com.auberer.compilerdesignlectureproject.lexer.Token;
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
      parseFctDef();
    }

    exitNode(node);
    return node;
  }

  public ASTForNode parseForLoop() {
    ASTForNode node = new ASTForNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_FOR);
    lexer.expect(TokenType.TOK_LPAREN);
    parseVarDecl();
    lexer.expect(TokenType.TOK_SEMICOLON);
    parseLogicalExpression();
    lexer.expect(TokenType.TOK_SEMICOLON);
    parseAssignStmt();
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

    // Retrieve all relevant selection sets
    Set<TokenType> stmtLstSelectionSet = ASTStmtLstNode.getSelectionSet();
    Set<TokenType> stmtSelectionSet = ASTStmtNode.getSelectionSet();
    Set<TokenType> ifStmtSelectionSet = ASTIfStmtNode.getSelectionSet();
    Set<TokenType> whileLoopSelectionSet = ASTWhileLoopNode.getSelectionSet();
    Set<TokenType> doWhileLoopSelectionSet = ASTDoWhileLoopNode.getSelectionSet();
    Set<TokenType> forLoopSelectionSet = ASTForNode.getSelectionSet();
    Set<TokenType> switchStmtSelectionSet = ASTSwitchStmtNode.getSelectionSet();

    // Parse all statements until the end of the statement list
    while (stmtLstSelectionSet.contains(lexer.getToken().getType())) {
      TokenType tokenType = lexer.getToken().getType();
      if (stmtSelectionSet.contains(tokenType)) {
        parseStmt();
      } else if (ifStmtSelectionSet.contains(tokenType)) {
        parseIfStmt();
      } else if (whileLoopSelectionSet.contains(tokenType)) {
        parseWhileLoop();
      } else if (doWhileLoopSelectionSet.contains(tokenType)) {
        parseDoWhile();
      } else if (forLoopSelectionSet.contains(tokenType)) {
        parseForLoop();
      } else if (switchStmtSelectionSet.contains(tokenType)) {
        parseSwitchStmt();
      } else {
        break;
      }
    }

    exitNode(node);
    return node;
  }

  public ASTStmtNode parseStmt() {
    ASTStmtNode node = new ASTStmtNode();
    enterNode(node);

    // Parse the statement
    TokenType tokenType = lexer.getToken().getType();
    if (ASTVarDeclNode.getSelectionSet().contains(tokenType)) {
      parseVarDecl();
    } else if (ASTAssignStmtNode.getSelectionSet().contains(tokenType)) {
      parseAssignStmt();
    } else {
      throw new RuntimeException("Unexpected token in statement");
    }
    lexer.expect(TokenType.TOK_SEMICOLON);

    exitNode(node);
    return node;
  }

  public ASTPrintBuiltinCallNode parsePrintBuiltinCall() {
    ASTPrintBuiltinCallNode node = new ASTPrintBuiltinCallNode();
    enterNode(node);

    // Parse the print builtin
    lexer.expect(TokenType.TOK_PRINT);
    lexer.expect(TokenType.TOK_LPAREN);
    parseLogicalExpression();
    lexer.expect(TokenType.TOK_RPAREN);

    exitNode(node);
    return node;
  }

  public ASTTypeNode parseType() {
    ASTTypeNode node = new ASTTypeNode();
    enterNode(node);

    // Parse the type
    switch (lexer.getToken().getType()) {
      case TokenType.TOK_TYPE_INT: {
        lexer.expect(TokenType.TOK_TYPE_INT);
        node.setDataType(ASTTypeNode.DataType.INT);
        break;
      }
      case TokenType.TOK_TYPE_DOUBLE: {
        lexer.expect(TokenType.TOK_TYPE_DOUBLE);
        node.setDataType(ASTTypeNode.DataType.DOUBLE);
        break;
      }
      case TokenType.TOK_TYPE_STRING: {
        lexer.expect(TokenType.TOK_TYPE_STRING);
        node.setDataType(ASTTypeNode.DataType.STRING);
        break;
      }
      case TokenType.TOK_TYPE_BOOL: {
        lexer.expect(TokenType.TOK_TYPE_BOOL);
        node.setDataType(ASTTypeNode.DataType.BOOL);
        break;
      }
      case TokenType.TOK_TYPE_EMPTY: {
        lexer.expect(TokenType.TOK_TYPE_EMPTY);
        node.setDataType(ASTTypeNode.DataType.EMPTY);
        break;
      }
      default: {
        throw new RuntimeException("Unexpected token in type");
      }
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
    parseLogicalExpression();
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
    parseLogicalExpression();
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

    int casesSize = 0;

    while (ASTCasesNode.getSelectionSet().contains(lexer.getToken().getType())) {
      casesSize++;
      lexer.expect(TokenType.TOK_CASE);
      TokenType tokenType = lexer.getToken().getType();
      if (tokenType == TokenType.TOK_INT_LIT) {
        node.addCaseType(ASTCasesNode.CaseType.INT_LIT);
      } else if (tokenType == TokenType.TOK_DOUBLE_LIT) {
        node.addCaseType(ASTCasesNode.CaseType.DOUBLE_LIT);
      } else if (tokenType == TokenType.TOK_STRING_LIT) {
        node.addCaseType(ASTCasesNode.CaseType.STRING_LIT);
      }
      lexer.expectOneOf(Set.of(TokenType.TOK_INT_LIT, TokenType.TOK_DOUBLE_LIT, TokenType.TOK_STRING_LIT));
      lexer.expect(TokenType.TOK_COLON);
      parseStmtLst();
    }

    node.setCasesSize(casesSize);

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

  public ASTIfStmtNode parseIfStmt() {
    ASTIfStmtNode node = new ASTIfStmtNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_IF);
    lexer.expect(TokenType.TOK_LPAREN);
    parseLogicalExpression();
    lexer.expect(TokenType.TOK_RPAREN);
    lexer.expect(TokenType.TOK_LBRACE);
    parseStmtLst();
    lexer.expect(TokenType.TOK_RBRACE);
    if (ASTAfterIfNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseAfterIf();
      node.setHasAfterIf(true);
    }

    exitNode(node);
    return node;
  }

  public ASTAfterIfNode parseAfterIf() {
    ASTAfterIfNode node = new ASTAfterIfNode();
    enterNode(node);

    parseElsePre();
    parseElsePost();

    node.setElseIf(node.getElsePost() != null && node.getElsePost().getIfStmt() != null);

    exitNode(node);
    return node;
  }

  public ASTElsePreNode parseElsePre() {
    ASTElsePreNode node = new ASTElsePreNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_ELSE);

    exitNode(node);
    return node;
  }

  public ASTElsePostNode parseElsePost() {
    ASTElsePostNode node = new ASTElsePostNode();
    enterNode(node);

    if (ASTIfStmtNode.getSelectionSet().contains(lexer.getToken().getType())) {
      node.setExprType(ASTElsePostNode.ElseType.ELSE_IF);
      parseIfStmt();
    } else {
      node.setExprType(ASTElsePostNode.ElseType.ELSE);
      parseElseStmt();
    }

    exitNode(node);
    return node;
  }

  public ASTElseNode parseElseStmt() {
    ASTElseNode node = new ASTElseNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_LBRACE);
    parseStmtLst();
    lexer.expect(TokenType.TOK_RBRACE);

    exitNode(node);
    return node;
  }

  public ASTVarDeclNode parseVarDecl() {
    ASTVarDeclNode node = new ASTVarDeclNode();
    enterNode(node);

    parseType();
    node.setVariableName(lexer.getToken().getText());
    lexer.expect(TokenType.TOK_IDENTIFIER);
    if (lexer.getToken().getType() == TokenType.TOK_ASSIGN) {
      node.setHasLogicalExpr(true);
      lexer.expect(TokenType.TOK_ASSIGN);
      parseLogicalExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTAssignStmtNode parseAssignStmt() {
    ASTAssignStmtNode node = new ASTAssignStmtNode();
    enterNode(node);

    if (lexer.getToken().getType() == TokenType.TOK_IDENTIFIER) {
      node.setAssignment(true);
      node.setVariableName(lexer.getToken().getText());
      lexer.expect(TokenType.TOK_IDENTIFIER);
      lexer.expect(TokenType.TOK_ASSIGN);
    }
    parseLogicalExpression();

    exitNode(node);
    return node;
  }

  public ASTWhileLoopNode parseWhileLoop() {
    ASTWhileLoopNode node = new ASTWhileLoopNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_WHILE);
    lexer.expect(TokenType.TOK_LPAREN);
    parseLogicalExpression();
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
    node.setName(lexer.getToken().getText());
    lexer.expect(TokenType.TOK_IDENTIFIER);
    lexer.expect(TokenType.TOK_LPAREN);
    if (ASTParamLstNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseParamLst();
      node.hasParams(true);
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


    //lexer.expect(TokenType.TOK_IDENTIFIER);
    parseParamNode();

    while (lexer.getToken().getType() == TokenType.TOK_COMMA) {
      lexer.expect(TokenType.TOK_COMMA);
      parseParamNode();
    }
    exitNode(node);
    return node;
  }

  public ASTParamNode parseParamNode() {
    ASTParamNode node = new ASTParamNode();
    enterNode(node);
    parseType();
    node.setName(lexer.getToken().getText());
    lexer.expect(TokenType.TOK_IDENTIFIER);
    exitNode(node);
    return node;
  }

  public ASTLogicNode parseLogic() {
    ASTLogicNode node = new ASTLogicNode();
    enterNode(node);

    parseStmtLst();
    lexer.expect(TokenType.TOK_RETURN);
    if (ASTLogicalExprNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseLogicalExpression();
    }
    lexer.expect(TokenType.TOK_SEMICOLON);
    exitNode(node);
    return node;
  }

  public ASTFctCallNode parseFctCall() {
    ASTFctCallNode node = new ASTFctCallNode();
    enterNode(node);

    lexer.expect(TokenType.TOK_CALL);
    node.setName(lexer.getToken().getText());
    lexer.expect(TokenType.TOK_IDENTIFIER);
    lexer.expect(TokenType.TOK_LPAREN);
    if (ASTCallParamsNode.getSelectionSet().contains(lexer.getToken().getType())) {
      parseCallParams();
      node.hasArgs(true);
    }
    lexer.expect(TokenType.TOK_RPAREN);

    exitNode(node);
    return node;
  }

  public ASTCallParamsNode parseCallParams() {
    ASTCallParamsNode node = new ASTCallParamsNode();
    enterNode(node);

    parseLogicalExpression();
    while (lexer.getToken().getType() == TokenType.TOK_COMMA) {
      lexer.expect(TokenType.TOK_COMMA);
      parseLogicalExpression();
    }
    exitNode(node);
    return node;
  }

  public ASTLogicalExprNode parseLogicalExpression() {
    ASTLogicalExprNode node = new ASTLogicalExprNode();
    enterNode(node);

    parseCompareExpression();
    while (lexer.getToken().getType() == TokenType.TOK_LOGICAL_AND || lexer.getToken().getType() == TokenType.TOK_LOGICAL_OR) {
      if (lexer.getToken().getType() == TokenType.TOK_LOGICAL_AND) {
        lexer.expect(TokenType.TOK_LOGICAL_AND);
        node.operatorsListAdd(ASTLogicalExprNode.LogicalOperator.AND);
      } else if (lexer.getToken().getType() == TokenType.TOK_LOGICAL_OR) {
        lexer.expect(TokenType.TOK_LOGICAL_OR);
        node.operatorsListAdd(ASTLogicalExprNode.LogicalOperator.OR);
      }
      parseCompareExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTCompareExprNode parseCompareExpression() {
    ASTCompareExprNode node = new ASTCompareExprNode();
    enterNode(node);

    parseAdditiveExpression();
    if (lexer.getToken().getType() == TokenType.TOK_EQUAL || lexer.getToken().getType() == TokenType.TOK_NOT_EQUAL) {
      if (lexer.getToken().getType() == TokenType.TOK_EQUAL) {
        lexer.expect(TokenType.TOK_EQUAL);
        node.setOperator(ASTCompareExprNode.CompareOperator.EQUAL);
      } else if (lexer.getToken().getType() == TokenType.TOK_NOT_EQUAL) {
        lexer.expect(TokenType.TOK_NOT_EQUAL);
        node.setOperator(ASTCompareExprNode.CompareOperator.NOT_EQUAL);
      }
      parseAdditiveExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTAdditiveExprNode parseAdditiveExpression() {
    ASTAdditiveExprNode node = new ASTAdditiveExprNode();
    enterNode(node);

    parseMultiplicativeExpression();
    while (lexer.getToken().getType() == TokenType.TOK_PLUS || lexer.getToken().getType() == TokenType.TOK_MINUS) {
      if (lexer.getToken().getType() == TokenType.TOK_PLUS) {
        lexer.expect(TokenType.TOK_PLUS);
        node.operatorsListAdd(ASTAdditiveExprNode.AdditiveOperator.PLUS);
      } else if (lexer.getToken().getType() == TokenType.TOK_MINUS) {
        lexer.expect(TokenType.TOK_MINUS);
        node.operatorsListAdd(ASTAdditiveExprNode.AdditiveOperator.MINUS);
      }
      parseMultiplicativeExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTMultiplicativeExprNode parseMultiplicativeExpression() {
    ASTMultiplicativeExprNode node = new ASTMultiplicativeExprNode();
    enterNode(node);

    parsePrefixExpression();
    while (lexer.getToken().getType() == TokenType.TOK_MUL || lexer.getToken().getType() == TokenType.TOK_DIV) {
      if (lexer.getToken().getType() == TokenType.TOK_MUL) {
        lexer.expect(TokenType.TOK_MUL);
        node.operatorsListAdd(ASTMultiplicativeExprNode.MultiplicativeOperator.MUL);
      } else if (lexer.getToken().getType() == TokenType.TOK_DIV) {
        lexer.expect(TokenType.TOK_DIV);
        node.operatorsListAdd(ASTMultiplicativeExprNode.MultiplicativeOperator.DIV);
      }
      parsePrefixExpression();
    }

    exitNode(node);
    return node;
  }

  public ASTPrefixExprNode parsePrefixExpression() {
    ASTPrefixExprNode node = new ASTPrefixExprNode();
    enterNode(node);

    if (lexer.getToken().getType() == TokenType.TOK_PLUS) {
      lexer.expect(TokenType.TOK_PLUS);
      node.setOperator(ASTPrefixExprNode.PrefixOperator.PLUS);
    } else if (lexer.getToken().getType() == TokenType.TOK_MINUS) {
      lexer.expect(TokenType.TOK_MINUS);
      node.setOperator(ASTPrefixExprNode.PrefixOperator.MINUS);
    }
    parseAtomicExpression();

    exitNode(node);
    return node;
  }

  public ASTAtomicExprNode parseAtomicExpression() {
    ASTAtomicExprNode node = new ASTAtomicExprNode();
    enterNode(node);

    Token token = lexer.getToken();
    switch (token.getType()) {
      case TokenType.TOK_INT_LIT: {
        lexer.expect(TokenType.TOK_INT_LIT);
        node.setExprType(ASTAtomicExprNode.AtomicType.INT_LIT);
        node.setIntLit(Integer.parseInt(token.getText()));
        break;
      }
      case TokenType.TOK_DOUBLE_LIT: {
        lexer.expect(TokenType.TOK_DOUBLE_LIT);
        node.setExprType(ASTAtomicExprNode.AtomicType.DOUBLE_LIT);
        node.setDoubleLit(Double.parseDouble(token.getText()));
        break;
      }
      case TokenType.TOK_STRING_LIT: {
        lexer.expect(TokenType.TOK_STRING_LIT);
        node.setExprType(ASTAtomicExprNode.AtomicType.STRING_LIT);
        node.setStringLit(token.getText().substring(1, token.getText().length() - 1));
        break;
      }
      case TokenType.TOK_TRUE: {
        lexer.expect(TokenType.TOK_TRUE);
        node.setExprType(ASTAtomicExprNode.AtomicType.BOOL_LIT);
        node.setBoolLit(true);
        break;
      }
      case TokenType.TOK_FALSE: {
        lexer.expect(TokenType.TOK_FALSE);
        node.setExprType(ASTAtomicExprNode.AtomicType.BOOL_LIT);
        node.setBoolLit(false);
        break;
      }
      case TokenType.TOK_IDENTIFIER: {
        lexer.expect(TokenType.TOK_IDENTIFIER);
        node.setExprType(ASTAtomicExprNode.AtomicType.IDENTIFIER);
        node.setIdentifier(token.getText());
        break;
      }
      case TokenType.TOK_LPAREN: {
        lexer.expect(TokenType.TOK_LPAREN);
        parseLogicalExpression();
        lexer.expect(TokenType.TOK_RPAREN);
        node.setExprType(ASTAtomicExprNode.AtomicType.LOGICAL_EXPR);
        break;
      }
      case TokenType.TOK_CALL: {
        parseFctCall();
        node.setExprType(ASTAtomicExprNode.AtomicType.FCT_CALL);
        break;
      }
      case TokenType.TOK_PRINT: {
        parsePrintBuiltinCall();
        node.setExprType(ASTAtomicExprNode.AtomicType.PRINT_BUILTIN_CALL);
        break;
      }
      default: {
        throw new RuntimeException("Unexpected token in atomic expression");
      }
    }

    exitNode(node);
    return node;
  }

  private void enterNode(ASTNode node) {
    // Attach CodeLoc to AST node
    node.setCodeLoc(lexer.getToken().getCodeLoc());

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
