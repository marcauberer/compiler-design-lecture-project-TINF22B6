grammar TInf;

// Given language constructs
entry: fctDef*;
stmtLst: (stmt | ifStmt | whileLoop | doWhileLoop | forLoop | switchStmt)*;
stmt: (varDecl | assignStmt) SEMICOLON;
type: TYPE_INT | TYPE_DOUBLE | TYPE_STRING | TYPE_BOOL | TYPE_EMPTY;
printBuiltinCall: PRINT LPAREN logicalExpr RPAREN;

// If statement (team 1)
ifStmt: IF LPAREN logicalExpr RPAREN LBRACE stmtLst RBRACE afterIf?;
afterIf: elsePre elsePost;
elsePre: ELSE;
elsePost: ifStmt | else;
else: LBRACE stmtLst RBRACE;

// While loop (team 2)
whileLoop: WHILE LPAREN logicalExpr RPAREN LBRACE stmtLst RBRACE;

// Do-While loop (team 3)
doWhileLoop: DO LBRACE stmtLst RBRACE WHILE LPAREN logicalExpr RPAREN SEMICOLON;

// For loop (team 4)
forLoop: FOR LPAREN varDecl SEMICOLON logicalExpr SEMICOLON assignStmt RPAREN LBRACE stmtLst RBRACE;

// Switch statement (team 5)
switchStmt: SWITCH LPAREN logicalExpr RPAREN LBRACE cases default? RBRACE;
cases: (CASE (INT_LIT | DOUBLE_LIT | STRING_LIT) COLON stmtLst)*;
default: DEFAULT COLON stmtLst;

// Function definition / call (team 6)
fctDef: FUNC type IDENTIFIER LPAREN paramLst? RPAREN logic CNUF;
paramLst: param (COMMA param)*;
param: type IDENTIFIER;
logic: stmtLst RETURN logicalExpr? SEMICOLON;
fctCall: CALL IDENTIFIER LPAREN callParams? RPAREN;
callParams: logicalExpr (COMMA logicalExpr)*;

// Variable declaration / assignment (team 7)
varDecl: type IDENTIFIER (ASSIGN logicalExpr)?;
assignStmt: (IDENTIFIER ASSIGN)? logicalExpr;

// Expression loop (team 8)
logicalExpr: compareExpr ((LOGICAL_AND | LOGICAL_OR) compareExpr)*;
compareExpr: additiveExpr ((EQUAL | NOT_EQUAL) additiveExpr)?;
additiveExpr: multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*;
multiplicativeExpr: prefixExpr ((MUL | DIV) prefixExpr)*;
prefixExpr: (PLUS | MINUS)? atomicExpr;
atomicExpr: INT_LIT | DOUBLE_LIT | STRING_LIT | TRUE | FALSE | IDENTIFIER | fctCall | printBuiltinCall | LPAREN logicalExpr RPAREN;

// Terminals
TYPE_INT: 'int';
TYPE_DOUBLE: 'double';
TYPE_STRING: 'string';
TYPE_BOOL: 'bool';
TYPE_EMPTY: 'empty';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
DO: 'do';
FOR: 'for';
FUNC: 'func';
CNUF: 'cnuf';
RETURN: 'return';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
CALL: 'call';
PRINT: 'print';
TRUE: 'true';
FALSE: 'false';
LBRACE: '{';
RBRACE: '}';
LPAREN: '(';
RPAREN: ')';
COMMA: ',';
COLON: ':';
PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';
EQUAL: '==';
NOT_EQUAL: '!=';
LOGICAL_AND: '&&';
LOGICAL_OR: '||';
SEMICOLON: ';';
ASSIGN: '=';
IDENTIFIER: [a-z_][a-zA-Z0-9_]*;
INT_LIT: [1-9][0-9]* | '0';
DOUBLE_LIT: [0-9]*[.][0-9]+;
STRING_LIT: '"' (~["\\\r\n])* '"';

// Skipped tokens
WS: [ \t\r\n]+ -> skip;
