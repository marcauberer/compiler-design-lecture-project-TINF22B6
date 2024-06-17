// Generated from TInf.g4 by ANTLR 4.13.1
package com.auberer.compilerdesignlectureproject.antlr.gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class TInfParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TYPE_INT=1, TYPE_DOUBLE=2, TYPE_STRING=3, TYPE_BOOL=4, TYPE_EMPTY=5, IF=6, 
		ELSE=7, WHILE=8, DO=9, FOR=10, FUNC=11, CNUF=12, RETURN=13, SWITCH=14, 
		CASE=15, DEFAULT=16, CALL=17, PRINT=18, TRUE=19, FALSE=20, LBRACE=21, 
		RBRACE=22, LPAREN=23, RPAREN=24, COMMA=25, COLON=26, PLUS=27, MINUS=28, 
		MUL=29, DIV=30, EQUAL=31, NOT_EQUAL=32, LOGICAL_AND=33, LOGICAL_OR=34, 
		SEMICOLON=35, ASSIGN=36, IDENTIFIER=37, INT_LIT=38, DOUBLE_LIT=39, STRING_LIT=40, 
		WS=41;
	public static final int
		RULE_entry = 0, RULE_stmtLst = 1, RULE_stmt = 2, RULE_type = 3, RULE_printBuiltinCall = 4, 
		RULE_ifStmt = 5, RULE_afterIf = 6, RULE_elsePre = 7, RULE_elsePost = 8, 
		RULE_else = 9, RULE_whileLoop = 10, RULE_doWhileLoop = 11, RULE_forLoop = 12, 
		RULE_switchStmt = 13, RULE_case = 14, RULE_default = 15, RULE_fctDef = 16, 
		RULE_paramLst = 17, RULE_param = 18, RULE_logic = 19, RULE_fctCall = 20, 
		RULE_callParams = 21, RULE_varDecl = 22, RULE_assignStmt = 23, RULE_logicalExpr = 24, 
		RULE_compareExpr = 25, RULE_additiveExpr = 26, RULE_multiplicativeExpr = 27, 
		RULE_prefixExpr = 28, RULE_atomicExpr = 29;
	private static String[] makeRuleNames() {
		return new String[] {
			"entry", "stmtLst", "stmt", "type", "printBuiltinCall", "ifStmt", "afterIf", 
			"elsePre", "elsePost", "else", "whileLoop", "doWhileLoop", "forLoop", 
			"switchStmt", "case", "default", "fctDef", "paramLst", "param", "logic", 
			"fctCall", "callParams", "varDecl", "assignStmt", "logicalExpr", "compareExpr", 
			"additiveExpr", "multiplicativeExpr", "prefixExpr", "atomicExpr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'double'", "'string'", "'bool'", "'empty'", "'if'", "'else'", 
			"'while'", "'do'", "'for'", "'func'", "'cnuf'", "'return'", "'switch'", 
			"'case'", "'default'", "'call'", "'print'", "'true'", "'false'", "'{'", 
			"'}'", "'('", "')'", "','", "':'", "'+'", "'-'", "'*'", "'/'", "'=='", 
			"'!='", "'&&'", "'||'", "';'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "TYPE_INT", "TYPE_DOUBLE", "TYPE_STRING", "TYPE_BOOL", "TYPE_EMPTY", 
			"IF", "ELSE", "WHILE", "DO", "FOR", "FUNC", "CNUF", "RETURN", "SWITCH", 
			"CASE", "DEFAULT", "CALL", "PRINT", "TRUE", "FALSE", "LBRACE", "RBRACE", 
			"LPAREN", "RPAREN", "COMMA", "COLON", "PLUS", "MINUS", "MUL", "DIV", 
			"EQUAL", "NOT_EQUAL", "LOGICAL_AND", "LOGICAL_OR", "SEMICOLON", "ASSIGN", 
			"IDENTIFIER", "INT_LIT", "DOUBLE_LIT", "STRING_LIT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TInf.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TInfParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EntryContext extends ParserRuleContext {
		public List<FctDefContext> fctDef() {
			return getRuleContexts(FctDefContext.class);
		}
		public FctDefContext fctDef(int i) {
			return getRuleContext(FctDefContext.class,i);
		}
		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entry; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_entry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUNC) {
				{
				{
				setState(60);
				fctDef();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtLstContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<IfStmtContext> ifStmt() {
			return getRuleContexts(IfStmtContext.class);
		}
		public IfStmtContext ifStmt(int i) {
			return getRuleContext(IfStmtContext.class,i);
		}
		public List<WhileLoopContext> whileLoop() {
			return getRuleContexts(WhileLoopContext.class);
		}
		public WhileLoopContext whileLoop(int i) {
			return getRuleContext(WhileLoopContext.class,i);
		}
		public List<DoWhileLoopContext> doWhileLoop() {
			return getRuleContexts(DoWhileLoopContext.class);
		}
		public DoWhileLoopContext doWhileLoop(int i) {
			return getRuleContext(DoWhileLoopContext.class,i);
		}
		public List<ForLoopContext> forLoop() {
			return getRuleContexts(ForLoopContext.class);
		}
		public ForLoopContext forLoop(int i) {
			return getRuleContext(ForLoopContext.class,i);
		}
		public List<SwitchStmtContext> switchStmt() {
			return getRuleContexts(SwitchStmtContext.class);
		}
		public SwitchStmtContext switchStmt(int i) {
			return getRuleContext(SwitchStmtContext.class,i);
		}
		public StmtLstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmtLst; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitStmtLst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtLstContext stmtLst() throws RecognitionException {
		StmtLstContext _localctx = new StmtLstContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stmtLst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2061997328254L) != 0)) {
				{
				setState(72);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TYPE_INT:
				case TYPE_DOUBLE:
				case TYPE_STRING:
				case TYPE_BOOL:
				case TYPE_EMPTY:
				case CALL:
				case PRINT:
				case TRUE:
				case FALSE:
				case LPAREN:
				case PLUS:
				case MINUS:
				case IDENTIFIER:
				case INT_LIT:
				case DOUBLE_LIT:
				case STRING_LIT:
					{
					setState(66);
					stmt();
					}
					break;
				case IF:
					{
					setState(67);
					ifStmt();
					}
					break;
				case WHILE:
					{
					setState(68);
					whileLoop();
					}
					break;
				case DO:
					{
					setState(69);
					doWhileLoop();
					}
					break;
				case FOR:
					{
					setState(70);
					forLoop();
					}
					break;
				case SWITCH:
					{
					setState(71);
					switchStmt();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(TInfParser.SEMICOLON, 0); }
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public AssignStmtContext assignStmt() {
			return getRuleContext(AssignStmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE_INT:
			case TYPE_DOUBLE:
			case TYPE_STRING:
			case TYPE_BOOL:
			case TYPE_EMPTY:
				{
				setState(77);
				varDecl();
				}
				break;
			case CALL:
			case PRINT:
			case TRUE:
			case FALSE:
			case LPAREN:
			case PLUS:
			case MINUS:
			case IDENTIFIER:
			case INT_LIT:
			case DOUBLE_LIT:
			case STRING_LIT:
				{
				setState(78);
				assignStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(81);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode TYPE_INT() { return getToken(TInfParser.TYPE_INT, 0); }
		public TerminalNode TYPE_DOUBLE() { return getToken(TInfParser.TYPE_DOUBLE, 0); }
		public TerminalNode TYPE_STRING() { return getToken(TInfParser.TYPE_STRING, 0); }
		public TerminalNode TYPE_BOOL() { return getToken(TInfParser.TYPE_BOOL, 0); }
		public TerminalNode TYPE_EMPTY() { return getToken(TInfParser.TYPE_EMPTY, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 62L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrintBuiltinCallContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(TInfParser.PRINT, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public PrintBuiltinCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printBuiltinCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitPrintBuiltinCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintBuiltinCallContext printBuiltinCall() throws RecognitionException {
		PrintBuiltinCallContext _localctx = new PrintBuiltinCallContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_printBuiltinCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(PRINT);
			setState(86);
			match(LPAREN);
			setState(87);
			logicalExpr();
			setState(88);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(TInfParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(TInfParser.LBRACE, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(TInfParser.RBRACE, 0); }
		public AfterIfContext afterIf() {
			return getRuleContext(AfterIfContext.class,0);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_ifStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(IF);
			setState(91);
			match(LPAREN);
			setState(92);
			logicalExpr();
			setState(93);
			match(RPAREN);
			setState(94);
			match(LBRACE);
			setState(95);
			stmtLst();
			setState(96);
			match(RBRACE);
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(97);
				afterIf();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AfterIfContext extends ParserRuleContext {
		public ElsePreContext elsePre() {
			return getRuleContext(ElsePreContext.class,0);
		}
		public ElsePostContext elsePost() {
			return getRuleContext(ElsePostContext.class,0);
		}
		public AfterIfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_afterIf; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitAfterIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AfterIfContext afterIf() throws RecognitionException {
		AfterIfContext _localctx = new AfterIfContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_afterIf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			elsePre();
			setState(101);
			elsePost();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElsePreContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(TInfParser.ELSE, 0); }
		public ElsePreContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elsePre; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitElsePre(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElsePreContext elsePre() throws RecognitionException {
		ElsePreContext _localctx = new ElsePreContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_elsePre);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(ELSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElsePostContext extends ParserRuleContext {
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public ElseContext else_() {
			return getRuleContext(ElseContext.class,0);
		}
		public ElsePostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elsePost; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitElsePost(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElsePostContext elsePost() throws RecognitionException {
		ElsePostContext _localctx = new ElsePostContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_elsePost);
		try {
			setState(107);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				ifStmt();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(106);
				else_();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(TInfParser.LBRACE, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(TInfParser.RBRACE, 0); }
		public ElseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitElse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseContext else_() throws RecognitionException {
		ElseContext _localctx = new ElseContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_else);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(LBRACE);
			setState(110);
			stmtLst();
			setState(111);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileLoopContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(TInfParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(TInfParser.LBRACE, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(TInfParser.RBRACE, 0); }
		public WhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileLoopContext whileLoop() throws RecognitionException {
		WhileLoopContext _localctx = new WhileLoopContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_whileLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(WHILE);
			setState(114);
			match(LPAREN);
			setState(115);
			logicalExpr();
			setState(116);
			match(RPAREN);
			setState(117);
			match(LBRACE);
			setState(118);
			stmtLst();
			setState(119);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoWhileLoopContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(TInfParser.DO, 0); }
		public TerminalNode LBRACE() { return getToken(TInfParser.LBRACE, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(TInfParser.RBRACE, 0); }
		public TerminalNode WHILE() { return getToken(TInfParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(TInfParser.SEMICOLON, 0); }
		public DoWhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitDoWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileLoopContext doWhileLoop() throws RecognitionException {
		DoWhileLoopContext _localctx = new DoWhileLoopContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_doWhileLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(DO);
			setState(122);
			match(LBRACE);
			setState(123);
			stmtLst();
			setState(124);
			match(RBRACE);
			setState(125);
			match(WHILE);
			setState(126);
			match(LPAREN);
			setState(127);
			logicalExpr();
			setState(128);
			match(RPAREN);
			setState(129);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForLoopContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(TInfParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(TInfParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(TInfParser.SEMICOLON, i);
		}
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public AssignStmtContext assignStmt() {
			return getRuleContext(AssignStmtContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(TInfParser.LBRACE, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(TInfParser.RBRACE, 0); }
		public ForLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitForLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForLoopContext forLoop() throws RecognitionException {
		ForLoopContext _localctx = new ForLoopContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_forLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(FOR);
			setState(132);
			match(LPAREN);
			setState(133);
			varDecl();
			setState(134);
			match(SEMICOLON);
			setState(135);
			logicalExpr();
			setState(136);
			match(SEMICOLON);
			setState(137);
			assignStmt();
			setState(138);
			match(RPAREN);
			setState(139);
			match(LBRACE);
			setState(140);
			stmtLst();
			setState(141);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchStmtContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(TInfParser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(TInfParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(TInfParser.RBRACE, 0); }
		public List<CaseContext> case_() {
			return getRuleContexts(CaseContext.class);
		}
		public CaseContext case_(int i) {
			return getRuleContext(CaseContext.class,i);
		}
		public DefaultContext default_() {
			return getRuleContext(DefaultContext.class,0);
		}
		public SwitchStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitSwitchStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStmtContext switchStmt() throws RecognitionException {
		SwitchStmtContext _localctx = new SwitchStmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_switchStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(SWITCH);
			setState(144);
			match(LPAREN);
			setState(145);
			logicalExpr();
			setState(146);
			match(RPAREN);
			setState(147);
			match(LBRACE);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(148);
				case_();
				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(154);
				default_();
				}
			}

			setState(157);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(TInfParser.CASE, 0); }
		public TerminalNode COLON() { return getToken(TInfParser.COLON, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode INT_LIT() { return getToken(TInfParser.INT_LIT, 0); }
		public TerminalNode DOUBLE_LIT() { return getToken(TInfParser.DOUBLE_LIT, 0); }
		public TerminalNode STRING_LIT() { return getToken(TInfParser.STRING_LIT, 0); }
		public CaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseContext case_() throws RecognitionException {
		CaseContext _localctx = new CaseContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_case);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(159);
			match(CASE);
			setState(160);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1924145348608L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(161);
			match(COLON);
			setState(162);
			stmtLst();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultContext extends ParserRuleContext {
		public TerminalNode DEFAULT() { return getToken(TInfParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(TInfParser.COLON, 0); }
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public DefaultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_default; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitDefault(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultContext default_() throws RecognitionException {
		DefaultContext _localctx = new DefaultContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_default);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(DEFAULT);
			setState(165);
			match(COLON);
			setState(166);
			stmtLst();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FctDefContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(TInfParser.FUNC, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(TInfParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public TerminalNode CNUF() { return getToken(TInfParser.CNUF, 0); }
		public ParamLstContext paramLst() {
			return getRuleContext(ParamLstContext.class,0);
		}
		public FctDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fctDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitFctDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FctDefContext fctDef() throws RecognitionException {
		FctDefContext _localctx = new FctDefContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fctDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(FUNC);
			setState(169);
			type();
			setState(170);
			match(IDENTIFIER);
			setState(171);
			match(LPAREN);
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 62L) != 0)) {
				{
				setState(172);
				paramLst();
				}
			}

			setState(175);
			match(RPAREN);
			setState(176);
			logic();
			setState(177);
			match(CNUF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamLstContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TInfParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TInfParser.COMMA, i);
		}
		public ParamLstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramLst; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitParamLst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamLstContext paramLst() throws RecognitionException {
		ParamLstContext _localctx = new ParamLstContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_paramLst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			param();
			setState(184);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(180);
				match(COMMA);
				setState(181);
				param();
				}
				}
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(TInfParser.IDENTIFIER, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			type();
			setState(188);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicContext extends ParserRuleContext {
		public StmtLstContext stmtLst() {
			return getRuleContext(StmtLstContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(TInfParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(TInfParser.SEMICOLON, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public LogicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitLogic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicContext logic() throws RecognitionException {
		LogicContext _localctx = new LogicContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_logic);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			stmtLst();
			setState(191);
			match(RETURN);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2061997309952L) != 0)) {
				{
				setState(192);
				logicalExpr();
				}
			}

			setState(195);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FctCallContext extends ParserRuleContext {
		public TerminalNode CALL() { return getToken(TInfParser.CALL, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TInfParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public CallParamsContext callParams() {
			return getRuleContext(CallParamsContext.class,0);
		}
		public FctCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fctCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitFctCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FctCallContext fctCall() throws RecognitionException {
		FctCallContext _localctx = new FctCallContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_fctCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(CALL);
			setState(198);
			match(IDENTIFIER);
			setState(199);
			match(LPAREN);
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2061997309952L) != 0)) {
				{
				setState(200);
				callParams();
				}
			}

			setState(203);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallParamsContext extends ParserRuleContext {
		public List<LogicalExprContext> logicalExpr() {
			return getRuleContexts(LogicalExprContext.class);
		}
		public LogicalExprContext logicalExpr(int i) {
			return getRuleContext(LogicalExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TInfParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TInfParser.COMMA, i);
		}
		public CallParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callParams; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitCallParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallParamsContext callParams() throws RecognitionException {
		CallParamsContext _localctx = new CallParamsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_callParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			logicalExpr();
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(206);
				match(COMMA);
				setState(207);
				logicalExpr();
				}
				}
				setState(212);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(TInfParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(TInfParser.ASSIGN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			type();
			setState(214);
			match(IDENTIFIER);
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(215);
				match(ASSIGN);
				setState(216);
				logicalExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignStmtContext extends ParserRuleContext {
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(TInfParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(TInfParser.ASSIGN, 0); }
		public AssignStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignStmtContext assignStmt() throws RecognitionException {
		AssignStmtContext _localctx = new AssignStmtContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_assignStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(219);
				match(IDENTIFIER);
				setState(220);
				match(ASSIGN);
				}
				break;
			}
			setState(223);
			logicalExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalExprContext extends ParserRuleContext {
		public List<CompareExprContext> compareExpr() {
			return getRuleContexts(CompareExprContext.class);
		}
		public CompareExprContext compareExpr(int i) {
			return getRuleContext(CompareExprContext.class,i);
		}
		public List<TerminalNode> LOGICAL_AND() { return getTokens(TInfParser.LOGICAL_AND); }
		public TerminalNode LOGICAL_AND(int i) {
			return getToken(TInfParser.LOGICAL_AND, i);
		}
		public List<TerminalNode> LOGICAL_OR() { return getTokens(TInfParser.LOGICAL_OR); }
		public TerminalNode LOGICAL_OR(int i) {
			return getToken(TInfParser.LOGICAL_OR, i);
		}
		public LogicalExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitLogicalExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalExprContext logicalExpr() throws RecognitionException {
		LogicalExprContext _localctx = new LogicalExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_logicalExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			compareExpr();
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LOGICAL_AND || _la==LOGICAL_OR) {
				{
				{
				setState(226);
				_la = _input.LA(1);
				if ( !(_la==LOGICAL_AND || _la==LOGICAL_OR) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(227);
				compareExpr();
				}
				}
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompareExprContext extends ParserRuleContext {
		public List<AdditiveExprContext> additiveExpr() {
			return getRuleContexts(AdditiveExprContext.class);
		}
		public AdditiveExprContext additiveExpr(int i) {
			return getRuleContext(AdditiveExprContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(TInfParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(TInfParser.NOT_EQUAL, 0); }
		public CompareExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitCompareExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareExprContext compareExpr() throws RecognitionException {
		CompareExprContext _localctx = new CompareExprContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_compareExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			additiveExpr();
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQUAL || _la==NOT_EQUAL) {
				{
				setState(234);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(235);
				additiveExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExprContext extends ParserRuleContext {
		public List<MultiplicativeExprContext> multiplicativeExpr() {
			return getRuleContexts(MultiplicativeExprContext.class);
		}
		public MultiplicativeExprContext multiplicativeExpr(int i) {
			return getRuleContext(MultiplicativeExprContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(TInfParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(TInfParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(TInfParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(TInfParser.MINUS, i);
		}
		public AdditiveExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitAdditiveExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExprContext additiveExpr() throws RecognitionException {
		AdditiveExprContext _localctx = new AdditiveExprContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_additiveExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			multiplicativeExpr();
			setState(243);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(239);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(240);
				multiplicativeExpr();
				}
				}
				setState(245);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicativeExprContext extends ParserRuleContext {
		public List<PrefixExprContext> prefixExpr() {
			return getRuleContexts(PrefixExprContext.class);
		}
		public PrefixExprContext prefixExpr(int i) {
			return getRuleContext(PrefixExprContext.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(TInfParser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(TInfParser.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(TInfParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(TInfParser.DIV, i);
		}
		public MultiplicativeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitMultiplicativeExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExprContext multiplicativeExpr() throws RecognitionException {
		MultiplicativeExprContext _localctx = new MultiplicativeExprContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_multiplicativeExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			prefixExpr();
			setState(251);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MUL || _la==DIV) {
				{
				{
				setState(247);
				_la = _input.LA(1);
				if ( !(_la==MUL || _la==DIV) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(248);
				prefixExpr();
				}
				}
				setState(253);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrefixExprContext extends ParserRuleContext {
		public AtomicExprContext atomicExpr() {
			return getRuleContext(AtomicExprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(TInfParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(TInfParser.MINUS, 0); }
		public PrefixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixExprContext prefixExpr() throws RecognitionException {
		PrefixExprContext _localctx = new PrefixExprContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_prefixExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(254);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(257);
			atomicExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomicExprContext extends ParserRuleContext {
		public TerminalNode INT_LIT() { return getToken(TInfParser.INT_LIT, 0); }
		public TerminalNode DOUBLE_LIT() { return getToken(TInfParser.DOUBLE_LIT, 0); }
		public TerminalNode STRING_LIT() { return getToken(TInfParser.STRING_LIT, 0); }
		public TerminalNode TRUE() { return getToken(TInfParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(TInfParser.FALSE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TInfParser.IDENTIFIER, 0); }
		public FctCallContext fctCall() {
			return getRuleContext(FctCallContext.class,0);
		}
		public PrintBuiltinCallContext printBuiltinCall() {
			return getRuleContext(PrintBuiltinCallContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(TInfParser.LPAREN, 0); }
		public LogicalExprContext logicalExpr() {
			return getRuleContext(LogicalExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TInfParser.RPAREN, 0); }
		public AtomicExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TInfVisitor ) return ((TInfVisitor<? extends T>)visitor).visitAtomicExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicExprContext atomicExpr() throws RecognitionException {
		AtomicExprContext _localctx = new AtomicExprContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_atomicExpr);
		try {
			setState(271);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_LIT:
				enterOuterAlt(_localctx, 1);
				{
				setState(259);
				match(INT_LIT);
				}
				break;
			case DOUBLE_LIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(260);
				match(DOUBLE_LIT);
				}
				break;
			case STRING_LIT:
				enterOuterAlt(_localctx, 3);
				{
				setState(261);
				match(STRING_LIT);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 4);
				{
				setState(262);
				match(TRUE);
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 5);
				{
				setState(263);
				match(FALSE);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 6);
				{
				setState(264);
				match(IDENTIFIER);
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 7);
				{
				setState(265);
				fctCall();
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 8);
				{
				setState(266);
				printBuiltinCall();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 9);
				{
				setState(267);
				match(LPAREN);
				setState(268);
				logicalExpr();
				setState(269);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001)\u0112\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0001\u0000\u0005\u0000"+
		">\b\u0000\n\u0000\f\u0000A\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001I\b\u0001\n\u0001\f\u0001"+
		"L\t\u0001\u0001\u0002\u0001\u0002\u0003\u0002P\b\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005c\b\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0003\bl\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0005\r\u0096\b\r\n\r\f\r\u0099\t\r\u0001\r\u0003\r"+
		"\u009c\b\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00ae\b\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0005\u0011\u00b7\b\u0011\n\u0011\f\u0011\u00ba\t\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u00c2\b\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0003\u0014\u00ca\b\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u00d1\b\u0015\n\u0015\f\u0015"+
		"\u00d4\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016"+
		"\u00da\b\u0016\u0001\u0017\u0001\u0017\u0003\u0017\u00de\b\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u00e5"+
		"\b\u0018\n\u0018\f\u0018\u00e8\t\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u00ed\b\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a"+
		"\u00f2\b\u001a\n\u001a\f\u001a\u00f5\t\u001a\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0005\u001b\u00fa\b\u001b\n\u001b\f\u001b\u00fd\t\u001b\u0001\u001c"+
		"\u0003\u001c\u0100\b\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u0110\b\u001d"+
		"\u0001\u001d\u0000\u0000\u001e\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:\u0000\u0006"+
		"\u0001\u0000\u0001\u0005\u0001\u0000&(\u0001\u0000!\"\u0001\u0000\u001f"+
		" \u0001\u0000\u001b\u001c\u0001\u0000\u001d\u001e\u0113\u0000?\u0001\u0000"+
		"\u0000\u0000\u0002J\u0001\u0000\u0000\u0000\u0004O\u0001\u0000\u0000\u0000"+
		"\u0006S\u0001\u0000\u0000\u0000\bU\u0001\u0000\u0000\u0000\nZ\u0001\u0000"+
		"\u0000\u0000\fd\u0001\u0000\u0000\u0000\u000eg\u0001\u0000\u0000\u0000"+
		"\u0010k\u0001\u0000\u0000\u0000\u0012m\u0001\u0000\u0000\u0000\u0014q"+
		"\u0001\u0000\u0000\u0000\u0016y\u0001\u0000\u0000\u0000\u0018\u0083\u0001"+
		"\u0000\u0000\u0000\u001a\u008f\u0001\u0000\u0000\u0000\u001c\u009f\u0001"+
		"\u0000\u0000\u0000\u001e\u00a4\u0001\u0000\u0000\u0000 \u00a8\u0001\u0000"+
		"\u0000\u0000\"\u00b3\u0001\u0000\u0000\u0000$\u00bb\u0001\u0000\u0000"+
		"\u0000&\u00be\u0001\u0000\u0000\u0000(\u00c5\u0001\u0000\u0000\u0000*"+
		"\u00cd\u0001\u0000\u0000\u0000,\u00d5\u0001\u0000\u0000\u0000.\u00dd\u0001"+
		"\u0000\u0000\u00000\u00e1\u0001\u0000\u0000\u00002\u00e9\u0001\u0000\u0000"+
		"\u00004\u00ee\u0001\u0000\u0000\u00006\u00f6\u0001\u0000\u0000\u00008"+
		"\u00ff\u0001\u0000\u0000\u0000:\u010f\u0001\u0000\u0000\u0000<>\u0003"+
		" \u0010\u0000=<\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001"+
		"\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@\u0001\u0001\u0000\u0000"+
		"\u0000A?\u0001\u0000\u0000\u0000BI\u0003\u0004\u0002\u0000CI\u0003\n\u0005"+
		"\u0000DI\u0003\u0014\n\u0000EI\u0003\u0016\u000b\u0000FI\u0003\u0018\f"+
		"\u0000GI\u0003\u001a\r\u0000HB\u0001\u0000\u0000\u0000HC\u0001\u0000\u0000"+
		"\u0000HD\u0001\u0000\u0000\u0000HE\u0001\u0000\u0000\u0000HF\u0001\u0000"+
		"\u0000\u0000HG\u0001\u0000\u0000\u0000IL\u0001\u0000\u0000\u0000JH\u0001"+
		"\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000K\u0003\u0001\u0000\u0000"+
		"\u0000LJ\u0001\u0000\u0000\u0000MP\u0003,\u0016\u0000NP\u0003.\u0017\u0000"+
		"OM\u0001\u0000\u0000\u0000ON\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000"+
		"\u0000QR\u0005#\u0000\u0000R\u0005\u0001\u0000\u0000\u0000ST\u0007\u0000"+
		"\u0000\u0000T\u0007\u0001\u0000\u0000\u0000UV\u0005\u0012\u0000\u0000"+
		"VW\u0005\u0017\u0000\u0000WX\u00030\u0018\u0000XY\u0005\u0018\u0000\u0000"+
		"Y\t\u0001\u0000\u0000\u0000Z[\u0005\u0006\u0000\u0000[\\\u0005\u0017\u0000"+
		"\u0000\\]\u00030\u0018\u0000]^\u0005\u0018\u0000\u0000^_\u0005\u0015\u0000"+
		"\u0000_`\u0003\u0002\u0001\u0000`b\u0005\u0016\u0000\u0000ac\u0003\f\u0006"+
		"\u0000ba\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000c\u000b\u0001"+
		"\u0000\u0000\u0000de\u0003\u000e\u0007\u0000ef\u0003\u0010\b\u0000f\r"+
		"\u0001\u0000\u0000\u0000gh\u0005\u0007\u0000\u0000h\u000f\u0001\u0000"+
		"\u0000\u0000il\u0003\n\u0005\u0000jl\u0003\u0012\t\u0000ki\u0001\u0000"+
		"\u0000\u0000kj\u0001\u0000\u0000\u0000l\u0011\u0001\u0000\u0000\u0000"+
		"mn\u0005\u0015\u0000\u0000no\u0003\u0002\u0001\u0000op\u0005\u0016\u0000"+
		"\u0000p\u0013\u0001\u0000\u0000\u0000qr\u0005\b\u0000\u0000rs\u0005\u0017"+
		"\u0000\u0000st\u00030\u0018\u0000tu\u0005\u0018\u0000\u0000uv\u0005\u0015"+
		"\u0000\u0000vw\u0003\u0002\u0001\u0000wx\u0005\u0016\u0000\u0000x\u0015"+
		"\u0001\u0000\u0000\u0000yz\u0005\t\u0000\u0000z{\u0005\u0015\u0000\u0000"+
		"{|\u0003\u0002\u0001\u0000|}\u0005\u0016\u0000\u0000}~\u0005\b\u0000\u0000"+
		"~\u007f\u0005\u0017\u0000\u0000\u007f\u0080\u00030\u0018\u0000\u0080\u0081"+
		"\u0005\u0018\u0000\u0000\u0081\u0082\u0005#\u0000\u0000\u0082\u0017\u0001"+
		"\u0000\u0000\u0000\u0083\u0084\u0005\n\u0000\u0000\u0084\u0085\u0005\u0017"+
		"\u0000\u0000\u0085\u0086\u0003,\u0016\u0000\u0086\u0087\u0005#\u0000\u0000"+
		"\u0087\u0088\u00030\u0018\u0000\u0088\u0089\u0005#\u0000\u0000\u0089\u008a"+
		"\u0003.\u0017\u0000\u008a\u008b\u0005\u0018\u0000\u0000\u008b\u008c\u0005"+
		"\u0015\u0000\u0000\u008c\u008d\u0003\u0002\u0001\u0000\u008d\u008e\u0005"+
		"\u0016\u0000\u0000\u008e\u0019\u0001\u0000\u0000\u0000\u008f\u0090\u0005"+
		"\u000e\u0000\u0000\u0090\u0091\u0005\u0017\u0000\u0000\u0091\u0092\u0003"+
		"0\u0018\u0000\u0092\u0093\u0005\u0018\u0000\u0000\u0093\u0097\u0005\u0015"+
		"\u0000\u0000\u0094\u0096\u0003\u001c\u000e\u0000\u0095\u0094\u0001\u0000"+
		"\u0000\u0000\u0096\u0099\u0001\u0000\u0000\u0000\u0097\u0095\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098\u009b\u0001\u0000"+
		"\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u009a\u009c\u0003\u001e"+
		"\u000f\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000"+
		"\u0000\u0000\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u009e\u0005\u0016"+
		"\u0000\u0000\u009e\u001b\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\u000f"+
		"\u0000\u0000\u00a0\u00a1\u0007\u0001\u0000\u0000\u00a1\u00a2\u0005\u001a"+
		"\u0000\u0000\u00a2\u00a3\u0003\u0002\u0001\u0000\u00a3\u001d\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a5\u0005\u0010\u0000\u0000\u00a5\u00a6\u0005\u001a"+
		"\u0000\u0000\u00a6\u00a7\u0003\u0002\u0001\u0000\u00a7\u001f\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0005\u000b\u0000\u0000\u00a9\u00aa\u0003\u0006"+
		"\u0003\u0000\u00aa\u00ab\u0005%\u0000\u0000\u00ab\u00ad\u0005\u0017\u0000"+
		"\u0000\u00ac\u00ae\u0003\"\u0011\u0000\u00ad\u00ac\u0001\u0000\u0000\u0000"+
		"\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00af\u0001\u0000\u0000\u0000"+
		"\u00af\u00b0\u0005\u0018\u0000\u0000\u00b0\u00b1\u0003&\u0013\u0000\u00b1"+
		"\u00b2\u0005\f\u0000\u0000\u00b2!\u0001\u0000\u0000\u0000\u00b3\u00b8"+
		"\u0003$\u0012\u0000\u00b4\u00b5\u0005\u0019\u0000\u0000\u00b5\u00b7\u0003"+
		"$\u0012\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b7\u00ba\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000"+
		"\u0000\u0000\u00b9#\u0001\u0000\u0000\u0000\u00ba\u00b8\u0001\u0000\u0000"+
		"\u0000\u00bb\u00bc\u0003\u0006\u0003\u0000\u00bc\u00bd\u0005%\u0000\u0000"+
		"\u00bd%\u0001\u0000\u0000\u0000\u00be\u00bf\u0003\u0002\u0001\u0000\u00bf"+
		"\u00c1\u0005\r\u0000\u0000\u00c0\u00c2\u00030\u0018\u0000\u00c1\u00c0"+
		"\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u00c3"+
		"\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005#\u0000\u0000\u00c4\'\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c6\u0005\u0011\u0000\u0000\u00c6\u00c7\u0005"+
		"%\u0000\u0000\u00c7\u00c9\u0005\u0017\u0000\u0000\u00c8\u00ca\u0003*\u0015"+
		"\u0000\u00c9\u00c8\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00cc\u0005\u0018\u0000"+
		"\u0000\u00cc)\u0001\u0000\u0000\u0000\u00cd\u00d2\u00030\u0018\u0000\u00ce"+
		"\u00cf\u0005\u0019\u0000\u0000\u00cf\u00d1\u00030\u0018\u0000\u00d0\u00ce"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d4\u0001\u0000\u0000\u0000\u00d2\u00d0"+
		"\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000\u00d3+\u0001"+
		"\u0000\u0000\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d5\u00d6\u0003"+
		"\u0006\u0003\u0000\u00d6\u00d9\u0005%\u0000\u0000\u00d7\u00d8\u0005$\u0000"+
		"\u0000\u00d8\u00da\u00030\u0018\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000"+
		"\u00d9\u00da\u0001\u0000\u0000\u0000\u00da-\u0001\u0000\u0000\u0000\u00db"+
		"\u00dc\u0005%\u0000\u0000\u00dc\u00de\u0005$\u0000\u0000\u00dd\u00db\u0001"+
		"\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u00df\u0001"+
		"\u0000\u0000\u0000\u00df\u00e0\u00030\u0018\u0000\u00e0/\u0001\u0000\u0000"+
		"\u0000\u00e1\u00e6\u00032\u0019\u0000\u00e2\u00e3\u0007\u0002\u0000\u0000"+
		"\u00e3\u00e5\u00032\u0019\u0000\u00e4\u00e2\u0001\u0000\u0000\u0000\u00e5"+
		"\u00e8\u0001\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e7\u0001\u0000\u0000\u0000\u00e71\u0001\u0000\u0000\u0000\u00e8\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e9\u00ec\u00034\u001a\u0000\u00ea\u00eb\u0007"+
		"\u0003\u0000\u0000\u00eb\u00ed\u00034\u001a\u0000\u00ec\u00ea\u0001\u0000"+
		"\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed3\u0001\u0000\u0000"+
		"\u0000\u00ee\u00f3\u00036\u001b\u0000\u00ef\u00f0\u0007\u0004\u0000\u0000"+
		"\u00f0\u00f2\u00036\u001b\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f2"+
		"\u00f5\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000\u0000\u0000\u00f3"+
		"\u00f4\u0001\u0000\u0000\u0000\u00f45\u0001\u0000\u0000\u0000\u00f5\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f6\u00fb\u00038\u001c\u0000\u00f7\u00f8\u0007"+
		"\u0005\u0000\u0000\u00f8\u00fa\u00038\u001c\u0000\u00f9\u00f7\u0001\u0000"+
		"\u0000\u0000\u00fa\u00fd\u0001\u0000\u0000\u0000\u00fb\u00f9\u0001\u0000"+
		"\u0000\u0000\u00fb\u00fc\u0001\u0000\u0000\u0000\u00fc7\u0001\u0000\u0000"+
		"\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000\u00fe\u0100\u0007\u0004\u0000"+
		"\u0000\u00ff\u00fe\u0001\u0000\u0000\u0000\u00ff\u0100\u0001\u0000\u0000"+
		"\u0000\u0100\u0101\u0001\u0000\u0000\u0000\u0101\u0102\u0003:\u001d\u0000"+
		"\u01029\u0001\u0000\u0000\u0000\u0103\u0110\u0005&\u0000\u0000\u0104\u0110"+
		"\u0005\'\u0000\u0000\u0105\u0110\u0005(\u0000\u0000\u0106\u0110\u0005"+
		"\u0013\u0000\u0000\u0107\u0110\u0005\u0014\u0000\u0000\u0108\u0110\u0005"+
		"%\u0000\u0000\u0109\u0110\u0003(\u0014\u0000\u010a\u0110\u0003\b\u0004"+
		"\u0000\u010b\u010c\u0005\u0017\u0000\u0000\u010c\u010d\u00030\u0018\u0000"+
		"\u010d\u010e\u0005\u0018\u0000\u0000\u010e\u0110\u0001\u0000\u0000\u0000"+
		"\u010f\u0103\u0001\u0000\u0000\u0000\u010f\u0104\u0001\u0000\u0000\u0000"+
		"\u010f\u0105\u0001\u0000\u0000\u0000\u010f\u0106\u0001\u0000\u0000\u0000"+
		"\u010f\u0107\u0001\u0000\u0000\u0000\u010f\u0108\u0001\u0000\u0000\u0000"+
		"\u010f\u0109\u0001\u0000\u0000\u0000\u010f\u010a\u0001\u0000\u0000\u0000"+
		"\u010f\u010b\u0001\u0000\u0000\u0000\u0110;\u0001\u0000\u0000\u0000\u0015"+
		"?HJObk\u0097\u009b\u00ad\u00b8\u00c1\u00c9\u00d2\u00d9\u00dd\u00e6\u00ec"+
		"\u00f3\u00fb\u00ff\u010f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}