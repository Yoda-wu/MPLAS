// Generated from D:/school/github/mplas/core/src/main/java/com/scut/mplas/ruby/parser\Ruby.g4 by ANTLR 4.12.0
package com.scut.mplas.ruby.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class RubyParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, LITERAL = 4, COMMA = 5, ELLIPSIS = 6, SEMICOLON = 7, CRLF = 8,
            CASE = 9, WHEN = 10, REQUIRE = 11, BEGIN = 12, END = 13, DEF = 14, RETURN = 15, PIR = 16,
            RAISE = 17, ENSURE = 18, IF = 19, ELSE = 20, ELSIF = 21, UNLESS = 22, WHILE = 23, RETRY = 24,
            BREAK = 25, FOR = 26, THEN = 27, IN = 28, DO = 29, RESCUE = 30, TRUE = 31, FALSE = 32,
            YIELD = 33, EACH = 34, PLUS = 35, MINUS = 36, MUL = 37, DIV = 38, MOD = 39, EXP = 40,
            EQUAL = 41, NOT_EQUAL = 42, GREATER = 43, LESS = 44, LESS_EQUAL = 45, GREATER_EQUAL = 46,
            ASSIGN = 47, PLUS_ASSIGN = 48, MINUS_ASSIGN = 49, MUL_ASSIGN = 50, DIV_ASSIGN = 51,
            MOD_ASSIGN = 52, EXP_ASSIGN = 53, BIT_AND = 54, BIT_OR = 55, BIT_XOR = 56, BIT_NOT = 57,
            BIT_SHL = 58, BIT_SHR = 59, AND = 60, OR = 61, NOT = 62, HASH_OP = 63, LEFT_RBRACKET = 64,
            RIGHT_RBRACKET = 65, LEFT_SBRACKET = 66, RIGHT_SBRACKET = 67, LEFT_BBRACKET = 68,
            RIGHT_BBRACKET = 69, NIL = 70, SL_COMMENT = 71, ML_COMMENT = 72, WS = 73, INT = 74,
            FLOAT = 75, ID = 76, CONST_ID = 77, ID_GLOBAL = 78, ID_INSTANCE = 79, ID_FUNCTION = 80,
            DOT = 81;
    public static final int
            RULE_prog = 0, RULE_expression_list = 1, RULE_expression = 2, RULE_case_expression = 3,
            RULE_case_exp = 4, RULE_when_cond = 5, RULE_begin_expression = 6, RULE_end_expression = 7,
            RULE_begin_rescue_expression = 8, RULE_error_type = 9, RULE_rescue_expression = 10,
            RULE_ensure_expression = 11, RULE_global_get = 12, RULE_global_set = 13,
            RULE_global_result = 14, RULE_instance_get = 15, RULE_instance_set = 16,
            RULE_instance_result = 17, RULE_const_set = 18, RULE_function_inline_call = 19,
            RULE_require_block = 20, RULE_pir_inline = 21, RULE_pir_expression_list = 22,
            RULE_class_definition = 23, RULE_module_definition = 24, RULE_function_definition = 25,
            RULE_function_definition_body = 26, RULE_function_definition_header = 27,
            RULE_function_name = 28, RULE_function_definition_params = 29, RULE_function_definition_params_list = 30,
            RULE_function_definition_param_id = 31, RULE_return_statement = 32, RULE_function_call = 33,
            RULE_function_call_param_list = 34, RULE_function_call_params = 35, RULE_function_param = 36,
            RULE_function_unnamed_param = 37, RULE_function_named_param = 38, RULE_function_call_assignment = 39,
            RULE_all_result = 40, RULE_elsif_statement = 41, RULE_if_elsif_statement = 42,
            RULE_if_statement = 43, RULE_unless_statement = 44, RULE_while_statement = 45,
            RULE_all_assignment = 46, RULE_for_statement = 47, RULE_for_each_statement = 48,
            RULE_cond_expression = 49, RULE_loop_expression = 50, RULE_hash_expression = 51,
            RULE_statement_body = 52, RULE_statement_expression_list = 53, RULE_assignment = 54,
            RULE_dynamic_assignment = 55, RULE_int_assignment = 56, RULE_float_assignment = 57,
            RULE_string_assignment = 58, RULE_initial_array_assignment = 59, RULE_array_assignment = 60,
            RULE_array_definition = 61, RULE_array_definition_elements = 62, RULE_array_selector = 63,
            RULE_dynamic_result = 64, RULE_map_result = 65, RULE_dynamic_ = 66, RULE_int_result = 67,
            RULE_float_result = 68, RULE_string_result = 69, RULE_comparison_list = 70,
            RULE_comparison = 71, RULE_comp_var = 72, RULE_lvalue = 73, RULE_rvalue = 74,
            RULE_break_expression = 75, RULE_raise_expression = 76, RULE_yield_expression = 77,
            RULE_literal_t = 78, RULE_float_t = 79, RULE_int_t = 80, RULE_bool_t = 81,
            RULE_nil_t = 82, RULE_id_ = 83, RULE_id_global = 84, RULE_id_instance = 85,
            RULE_id_constence = 86, RULE_id_function = 87, RULE_terminator = 88, RULE_else_token = 89,
            RULE_crlf = 90;

    private static String[] makeRuleNames() {
        return new String[]{
                "prog", "expression_list", "expression", "case_expression", "case_exp",
                "when_cond", "begin_expression", "end_expression", "begin_rescue_expression",
                "error_type", "rescue_expression", "ensure_expression", "global_get",
                "global_set", "global_result", "instance_get", "instance_set", "instance_result",
                "const_set", "function_inline_call", "require_block", "pir_inline", "pir_expression_list",
                "class_definition", "module_definition", "function_definition", "function_definition_body",
                "function_definition_header", "function_name", "function_definition_params",
                "function_definition_params_list", "function_definition_param_id", "return_statement",
                "function_call", "function_call_param_list", "function_call_params",
                "function_param", "function_unnamed_param", "function_named_param", "function_call_assignment",
                "all_result", "elsif_statement", "if_elsif_statement", "if_statement",
                "unless_statement", "while_statement", "all_assignment", "for_statement",
                "for_each_statement", "cond_expression", "loop_expression", "hash_expression",
                "statement_body", "statement_expression_list", "assignment", "dynamic_assignment",
                "int_assignment", "float_assignment", "string_assignment", "initial_array_assignment",
                "array_assignment", "array_definition", "array_definition_elements",
                "array_selector", "dynamic_result", "map_result", "dynamic_", "int_result",
                "float_result", "string_result", "comparison_list", "comparison", "comp_var",
                "lvalue", "rvalue", "break_expression", "raise_expression", "yield_expression",
                "literal_t", "float_t", "int_t", "bool_t", "nil_t", "id_", "id_global",
                "id_instance", "id_constence", "id_function", "terminator", "else_token",
                "crlf"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'class'", "'module'", "'::'", null, "','", null, "';'", null,
                "'case'", "'when'", "'require'", "'begin'", "'end'", "'def'", "'return'",
                "'pir'", "'raise'", "'ensure'", "'if'", "'else'", "'elsif'", "'unless'",
                "'while'", "'retry'", "'break'", "'for'", "'then'", "'in'", "'do'", "'rescue'",
                "'true'", "'false'", "'yield'", "'each'", "'+'", "'-'", "'*'", "'/'",
                "'%'", "'**'", "'=='", "'!='", "'>'", "'<'", "'<='", "'>='", "'='", "'+='",
                "'-='", "'*='", "'/='", "'%='", "'**='", "'&'", "'|'", "'^'", "'~'",
                "'<<'", "'>>'", null, null, null, "'=>'", "'('", "')'", "'['", "']'",
                "'{'", "'}'", "'nil'", null, null, null, null, null, null, null, null,
                null, null, "'.'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, "LITERAL", "COMMA", "ELLIPSIS", "SEMICOLON",
                "CRLF", "CASE", "WHEN", "REQUIRE", "BEGIN", "END", "DEF", "RETURN", "PIR",
                "RAISE", "ENSURE", "IF", "ELSE", "ELSIF", "UNLESS", "WHILE", "RETRY",
                "BREAK", "FOR", "THEN", "IN", "DO", "RESCUE", "TRUE", "FALSE", "YIELD",
                "EACH", "PLUS", "MINUS", "MUL", "DIV", "MOD", "EXP", "EQUAL", "NOT_EQUAL",
                "GREATER", "LESS", "LESS_EQUAL", "GREATER_EQUAL", "ASSIGN", "PLUS_ASSIGN",
                "MINUS_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", "EXP_ASSIGN",
                "BIT_AND", "BIT_OR", "BIT_XOR", "BIT_NOT", "BIT_SHL", "BIT_SHR", "AND",
                "OR", "NOT", "HASH_OP", "LEFT_RBRACKET", "RIGHT_RBRACKET", "LEFT_SBRACKET",
                "RIGHT_SBRACKET", "LEFT_BBRACKET", "RIGHT_BBRACKET", "NIL", "SL_COMMENT",
                "ML_COMMENT", "WS", "INT", "FLOAT", "ID", "CONST_ID", "ID_GLOBAL", "ID_INSTANCE",
                "ID_FUNCTION", "DOT"
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
    public String getGrammarFileName() {
        return "Ruby.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public RubyParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ProgContext extends ParserRuleContext {
        public Expression_listContext expression_list() {
            return getRuleContext(Expression_listContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(RubyParser.EOF, 0);
        }

        public ProgContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_prog;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterProg(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitProg(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitProg(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ProgContext prog() throws RecognitionException {
        ProgContext _localctx = new ProgContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_prog);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(182);
                expression_list(0);
                setState(183);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Expression_listContext extends ParserRuleContext {
        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminatorContext terminator() {
            return getRuleContext(TerminatorContext.class, 0);
        }

        public Expression_listContext expression_list() {
            return getRuleContext(Expression_listContext.class, 0);
        }

        public Expression_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterExpression_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitExpression_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitExpression_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Expression_listContext expression_list() throws RecognitionException {
        return expression_list(0);
    }

    private Expression_listContext expression_list(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Expression_listContext _localctx = new Expression_listContext(_ctx, _parentState);
        Expression_listContext _prevctx = _localctx;
        int _startState = 2;
        enterRecursionRule(_localctx, 2, RULE_expression_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(190);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__0:
                    case T__1:
                    case LITERAL:
                    case CASE:
                    case REQUIRE:
                    case BEGIN:
                    case END:
                    case DEF:
                    case RETURN:
                    case PIR:
                    case RAISE:
                    case IF:
                    case UNLESS:
                    case WHILE:
                    case FOR:
                    case TRUE:
                    case FALSE:
                    case BIT_NOT:
                    case NOT:
                    case LEFT_RBRACKET:
                    case LEFT_SBRACKET:
                    case LEFT_BBRACKET:
                    case NIL:
                    case INT:
                    case FLOAT:
                    case ID:
                    case CONST_ID:
                    case ID_GLOBAL:
                    case ID_INSTANCE:
                    case ID_FUNCTION: {
                        setState(186);
                        expression();
                        setState(187);
                        terminator(0);
                    }
                    break;
                    case SEMICOLON:
                    case CRLF: {
                        setState(189);
                        terminator(0);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(198);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 1, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new Expression_listContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_expression_list);
                                setState(192);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(193);
                                expression();
                                setState(194);
                                terminator(0);
                            }
                        }
                    }
                    setState(200);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 1, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExpressionContext extends ParserRuleContext {
        public For_statementContext for_statement() {
            return getRuleContext(For_statementContext.class, 0);
        }

        public Function_definitionContext function_definition() {
            return getRuleContext(Function_definitionContext.class, 0);
        }

        public Function_inline_callContext function_inline_call() {
            return getRuleContext(Function_inline_callContext.class, 0);
        }

        public Require_blockContext require_block() {
            return getRuleContext(Require_blockContext.class, 0);
        }

        public If_statementContext if_statement() {
            return getRuleContext(If_statementContext.class, 0);
        }

        public Unless_statementContext unless_statement() {
            return getRuleContext(Unless_statementContext.class, 0);
        }

        public RvalueContext rvalue() {
            return getRuleContext(RvalueContext.class, 0);
        }

        public Raise_expressionContext raise_expression() {
            return getRuleContext(Raise_expressionContext.class, 0);
        }

        public Return_statementContext return_statement() {
            return getRuleContext(Return_statementContext.class, 0);
        }

        public While_statementContext while_statement() {
            return getRuleContext(While_statementContext.class, 0);
        }

        public Pir_inlineContext pir_inline() {
            return getRuleContext(Pir_inlineContext.class, 0);
        }

        public Hash_expressionContext hash_expression() {
            return getRuleContext(Hash_expressionContext.class, 0);
        }

        public Class_definitionContext class_definition() {
            return getRuleContext(Class_definitionContext.class, 0);
        }

        public Module_definitionContext module_definition() {
            return getRuleContext(Module_definitionContext.class, 0);
        }

        public Begin_expressionContext begin_expression() {
            return getRuleContext(Begin_expressionContext.class, 0);
        }

        public End_expressionContext end_expression() {
            return getRuleContext(End_expressionContext.class, 0);
        }

        public Begin_rescue_expressionContext begin_rescue_expression() {
            return getRuleContext(Begin_rescue_expressionContext.class, 0);
        }

        public Case_expressionContext case_expression() {
            return getRuleContext(Case_expressionContext.class, 0);
        }

        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ExpressionContext expression() throws RecognitionException {
        ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_expression);
        try {
            setState(219);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(201);
                    for_statement();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(202);
                    function_definition();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(203);
                    function_inline_call();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(204);
                    require_block();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(205);
                    if_statement();
                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(206);
                    unless_statement();
                }
                break;
                case 7:
                    enterOuterAlt(_localctx, 7);
                {
                    setState(207);
                    rvalue(0);
                }
                break;
                case 8:
                    enterOuterAlt(_localctx, 8);
                {
                    setState(208);
                    raise_expression();
                }
                break;
                case 9:
                    enterOuterAlt(_localctx, 9);
                {
                    setState(209);
                    return_statement();
                }
                break;
                case 10:
                    enterOuterAlt(_localctx, 10);
                {
                    setState(210);
                    while_statement();
                }
                break;
                case 11:
                    enterOuterAlt(_localctx, 11);
                {
                    setState(211);
                    pir_inline();
                }
                break;
                case 12:
                    enterOuterAlt(_localctx, 12);
                {
                    setState(212);
                    hash_expression();
                }
                break;
                case 13:
                    enterOuterAlt(_localctx, 13);
                {
                    setState(213);
                    class_definition();
                }
                break;
                case 14:
                    enterOuterAlt(_localctx, 14);
                {
                    setState(214);
                    module_definition();
                }
                break;
                case 15:
                    enterOuterAlt(_localctx, 15);
                {
                    setState(215);
                    begin_expression();
                }
                break;
                case 16:
                    enterOuterAlt(_localctx, 16);
                {
                    setState(216);
                    end_expression();
                }
                break;
                case 17:
                    enterOuterAlt(_localctx, 17);
                {
                    setState(217);
                    begin_rescue_expression();
                }
                break;
                case 18:
                    enterOuterAlt(_localctx, 18);
                {
                    setState(218);
                    case_expression();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Case_expressionContext extends ParserRuleContext {
        public TerminalNode CASE() {
            return getToken(RubyParser.CASE, 0);
        }

        public Case_expContext case_exp() {
            return getRuleContext(Case_expContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public List<TerminalNode> WHEN() {
            return getTokens(RubyParser.WHEN);
        }

        public TerminalNode WHEN(int i) {
            return getToken(RubyParser.WHEN, i);
        }

        public List<When_condContext> when_cond() {
            return getRuleContexts(When_condContext.class);
        }

        public When_condContext when_cond(int i) {
            return getRuleContext(When_condContext.class, i);
        }

        public List<Statement_bodyContext> statement_body() {
            return getRuleContexts(Statement_bodyContext.class);
        }

        public Statement_bodyContext statement_body(int i) {
            return getRuleContext(Statement_bodyContext.class, i);
        }

        public Else_tokenContext else_token() {
            return getRuleContext(Else_tokenContext.class, 0);
        }

        public Case_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_case_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterCase_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitCase_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitCase_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Case_expressionContext case_expression() throws RecognitionException {
        Case_expressionContext _localctx = new Case_expressionContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_case_expression);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(221);
                match(CASE);
                setState(222);
                case_exp();
                setState(226);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CRLF) {
                    {
                        {
                            setState(223);
                            crlf();
                        }
                    }
                    setState(228);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(241);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == WHEN) {
                    {
                        {
                            setState(229);
                            match(WHEN);
                            setState(230);
                            when_cond();
                            setState(234);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                            while (_la == CRLF) {
                                {
                                    {
                                        setState(231);
                                        crlf();
                                    }
                                }
                                setState(236);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                            }
                            setState(237);
                            statement_body();
                        }
                    }
                    setState(243);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(248);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == ELSE) {
                    {
                        setState(244);
                        else_token();
                        setState(245);
                        crlf();
                        setState(246);
                        statement_body();
                    }
                }

                setState(250);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Case_expContext extends ParserRuleContext {
        public RvalueContext rvalue() {
            return getRuleContext(RvalueContext.class, 0);
        }

        public Case_expContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_case_exp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterCase_exp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitCase_exp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitCase_exp(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Case_expContext case_exp() throws RecognitionException {
        Case_expContext _localctx = new Case_expContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_case_exp);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(252);
                rvalue(0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class When_condContext extends ParserRuleContext {
        public Cond_expressionContext cond_expression() {
            return getRuleContext(Cond_expressionContext.class, 0);
        }

        public Array_definitionContext array_definition() {
            return getRuleContext(Array_definitionContext.class, 0);
        }

        public When_condContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_when_cond;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterWhen_cond(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitWhen_cond(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitWhen_cond(this);
            else return visitor.visitChildren(this);
        }
    }

    public final When_condContext when_cond() throws RecognitionException {
        When_condContext _localctx = new When_condContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_when_cond);
        try {
            setState(256);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(254);
                    cond_expression();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(255);
                    array_definition();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Begin_expressionContext extends ParserRuleContext {
        public TerminalNode BEGIN() {
            return getToken(RubyParser.BEGIN, 0);
        }

        public TerminalNode LEFT_BBRACKET() {
            return getToken(RubyParser.LEFT_BBRACKET, 0);
        }

        public Statement_bodyContext statement_body() {
            return getRuleContext(Statement_bodyContext.class, 0);
        }

        public TerminalNode RIGHT_BBRACKET() {
            return getToken(RubyParser.RIGHT_BBRACKET, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public Begin_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_begin_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterBegin_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitBegin_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitBegin_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Begin_expressionContext begin_expression() throws RecognitionException {
        Begin_expressionContext _localctx = new Begin_expressionContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_begin_expression);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(258);
                match(BEGIN);
                setState(259);
                match(LEFT_BBRACKET);
                setState(263);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CRLF) {
                    {
                        {
                            setState(260);
                            crlf();
                        }
                    }
                    setState(265);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(266);
                statement_body();
                setState(267);
                match(RIGHT_BBRACKET);
                setState(271);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(268);
                                crlf();
                            }
                        }
                    }
                    setState(273);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class End_expressionContext extends ParserRuleContext {
        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public TerminalNode LEFT_BBRACKET() {
            return getToken(RubyParser.LEFT_BBRACKET, 0);
        }

        public Statement_bodyContext statement_body() {
            return getRuleContext(Statement_bodyContext.class, 0);
        }

        public TerminalNode RIGHT_BBRACKET() {
            return getToken(RubyParser.RIGHT_BBRACKET, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public End_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_end_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterEnd_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitEnd_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitEnd_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final End_expressionContext end_expression() throws RecognitionException {
        End_expressionContext _localctx = new End_expressionContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_end_expression);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(274);
                match(END);
                setState(275);
                match(LEFT_BBRACKET);
                setState(279);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CRLF) {
                    {
                        {
                            setState(276);
                            crlf();
                        }
                    }
                    setState(281);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(282);
                statement_body();
                setState(283);
                match(RIGHT_BBRACKET);
                setState(287);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(284);
                                crlf();
                            }
                        }
                    }
                    setState(289);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Begin_rescue_expressionContext extends ParserRuleContext {
        public TerminalNode BEGIN() {
            return getToken(RubyParser.BEGIN, 0);
        }

        public List<Statement_bodyContext> statement_body() {
            return getRuleContexts(Statement_bodyContext.class);
        }

        public Statement_bodyContext statement_body(int i) {
            return getRuleContext(Statement_bodyContext.class, i);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public List<Rescue_expressionContext> rescue_expression() {
            return getRuleContexts(Rescue_expressionContext.class);
        }

        public Rescue_expressionContext rescue_expression(int i) {
            return getRuleContext(Rescue_expressionContext.class, i);
        }

        public Else_tokenContext else_token() {
            return getRuleContext(Else_tokenContext.class, 0);
        }

        public Ensure_expressionContext ensure_expression() {
            return getRuleContext(Ensure_expressionContext.class, 0);
        }

        public List<Error_typeContext> error_type() {
            return getRuleContexts(Error_typeContext.class);
        }

        public Error_typeContext error_type(int i) {
            return getRuleContext(Error_typeContext.class, i);
        }

        public Begin_rescue_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_begin_rescue_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterBegin_rescue_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitBegin_rescue_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitBegin_rescue_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Begin_rescue_expressionContext begin_rescue_expression() throws RecognitionException {
        Begin_rescue_expressionContext _localctx = new Begin_rescue_expressionContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_begin_rescue_expression);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(290);
                match(BEGIN);
                setState(294);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CRLF) {
                    {
                        {
                            setState(291);
                            crlf();
                        }
                    }
                    setState(296);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(297);
                statement_body();
                setState(312);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == RESCUE) {
                    {
                        {
                            setState(298);
                            rescue_expression();
                            setState(300);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                                case 1: {
                                    setState(299);
                                    error_type();
                                }
                                break;
                            }
                            setState(305);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                            while (_la == CRLF) {
                                {
                                    {
                                        setState(302);
                                        crlf();
                                    }
                                }
                                setState(307);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                            }
                            setState(308);
                            statement_body();
                        }
                    }
                    setState(314);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(319);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == ELSE) {
                    {
                        setState(315);
                        else_token();
                        setState(316);
                        crlf();
                        setState(317);
                        statement_body();
                    }
                }

                setState(330);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == ENSURE) {
                    {
                        setState(321);
                        ensure_expression();
                        setState(325);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == CRLF) {
                            {
                                {
                                    setState(322);
                                    crlf();
                                }
                            }
                            setState(327);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(328);
                        statement_body();
                    }
                }

                setState(332);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Error_typeContext extends ParserRuleContext {
        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public Error_typeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_error_type;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterError_type(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitError_type(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitError_type(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Error_typeContext error_type() throws RecognitionException {
        Error_typeContext _localctx = new Error_typeContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_error_type);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(334);
                id_();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Rescue_expressionContext extends ParserRuleContext {
        public TerminalNode RESCUE() {
            return getToken(RubyParser.RESCUE, 0);
        }

        public Rescue_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_rescue_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterRescue_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitRescue_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitRescue_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Rescue_expressionContext rescue_expression() throws RecognitionException {
        Rescue_expressionContext _localctx = new Rescue_expressionContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_rescue_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(336);
                match(RESCUE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Ensure_expressionContext extends ParserRuleContext {
        public TerminalNode ENSURE() {
            return getToken(RubyParser.ENSURE, 0);
        }

        public Ensure_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_ensure_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterEnsure_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitEnsure_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitEnsure_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Ensure_expressionContext ensure_expression() throws RecognitionException {
        Ensure_expressionContext _localctx = new Ensure_expressionContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_ensure_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(338);
                match(ENSURE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Global_getContext extends ParserRuleContext {
        public LvalueContext var_name;
        public Token op;
        public Id_globalContext global_name;

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public Id_globalContext id_global() {
            return getRuleContext(Id_globalContext.class, 0);
        }

        public Global_getContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_global_get;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterGlobal_get(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitGlobal_get(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitGlobal_get(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Global_getContext global_get() throws RecognitionException {
        Global_getContext _localctx = new Global_getContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_global_get);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(340);
                ((Global_getContext) _localctx).var_name = lvalue();
                setState(341);
                ((Global_getContext) _localctx).op = match(ASSIGN);
                setState(342);
                ((Global_getContext) _localctx).global_name = id_global();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Global_setContext extends ParserRuleContext {
        public Id_globalContext global_name;
        public Token op;
        public All_resultContext result;

        public Id_globalContext id_global() {
            return getRuleContext(Id_globalContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public All_resultContext all_result() {
            return getRuleContext(All_resultContext.class, 0);
        }

        public Global_setContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_global_set;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterGlobal_set(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitGlobal_set(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitGlobal_set(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Global_setContext global_set() throws RecognitionException {
        Global_setContext _localctx = new Global_setContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_global_set);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(344);
                ((Global_setContext) _localctx).global_name = id_global();
                setState(345);
                ((Global_setContext) _localctx).op = match(ASSIGN);
                setState(346);
                ((Global_setContext) _localctx).result = all_result();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Global_resultContext extends ParserRuleContext {
        public Id_globalContext id_global() {
            return getRuleContext(Id_globalContext.class, 0);
        }

        public Global_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_global_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterGlobal_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitGlobal_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitGlobal_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Global_resultContext global_result() throws RecognitionException {
        Global_resultContext _localctx = new Global_resultContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_global_result);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(348);
                id_global();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Instance_getContext extends ParserRuleContext {
        public LvalueContext instance_name;
        public Token op;
        public Id_instanceContext result;

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public Id_instanceContext id_instance() {
            return getRuleContext(Id_instanceContext.class, 0);
        }

        public Instance_getContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_instance_get;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInstance_get(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInstance_get(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInstance_get(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Instance_getContext instance_get() throws RecognitionException {
        Instance_getContext _localctx = new Instance_getContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_instance_get);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(350);
                ((Instance_getContext) _localctx).instance_name = lvalue();
                setState(351);
                ((Instance_getContext) _localctx).op = match(ASSIGN);
                setState(352);
                ((Instance_getContext) _localctx).result = id_instance();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Instance_setContext extends ParserRuleContext {
        public Id_instanceContext instance_name;
        public Token op;
        public All_resultContext result;

        public Id_instanceContext id_instance() {
            return getRuleContext(Id_instanceContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public All_resultContext all_result() {
            return getRuleContext(All_resultContext.class, 0);
        }

        public Instance_setContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_instance_set;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInstance_set(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInstance_set(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInstance_set(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Instance_setContext instance_set() throws RecognitionException {
        Instance_setContext _localctx = new Instance_setContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_instance_set);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(354);
                ((Instance_setContext) _localctx).instance_name = id_instance();
                setState(355);
                ((Instance_setContext) _localctx).op = match(ASSIGN);
                setState(356);
                ((Instance_setContext) _localctx).result = all_result();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Instance_resultContext extends ParserRuleContext {
        public Id_instanceContext id_instance() {
            return getRuleContext(Id_instanceContext.class, 0);
        }

        public Instance_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_instance_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInstance_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInstance_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInstance_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Instance_resultContext instance_result() throws RecognitionException {
        Instance_resultContext _localctx = new Instance_resultContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_instance_result);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(358);
                id_instance();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Const_setContext extends ParserRuleContext {
        public Id_constenceContext constance_name;
        public Token op;
        public All_resultContext result;

        public Id_constenceContext id_constence() {
            return getRuleContext(Id_constenceContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public All_resultContext all_result() {
            return getRuleContext(All_resultContext.class, 0);
        }

        public Const_setContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_const_set;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterConst_set(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitConst_set(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitConst_set(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Const_setContext const_set() throws RecognitionException {
        Const_setContext _localctx = new Const_setContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_const_set);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(360);
                ((Const_setContext) _localctx).constance_name = id_constence();
                setState(361);
                ((Const_setContext) _localctx).op = match(ASSIGN);
                setState(362);
                ((Const_setContext) _localctx).result = all_result();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_inline_callContext extends ParserRuleContext {
        public Function_callContext function_call() {
            return getRuleContext(Function_callContext.class, 0);
        }

        public Function_inline_callContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_inline_call;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_inline_call(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_inline_call(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_inline_call(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_inline_callContext function_inline_call() throws RecognitionException {
        Function_inline_callContext _localctx = new Function_inline_callContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_function_inline_call);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(364);
                function_call();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Require_blockContext extends ParserRuleContext {
        public TerminalNode REQUIRE() {
            return getToken(RubyParser.REQUIRE, 0);
        }

        public Literal_tContext literal_t() {
            return getRuleContext(Literal_tContext.class, 0);
        }

        public Require_blockContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_require_block;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterRequire_block(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitRequire_block(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitRequire_block(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Require_blockContext require_block() throws RecognitionException {
        Require_blockContext _localctx = new Require_blockContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_require_block);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(366);
                match(REQUIRE);
                setState(367);
                literal_t();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Pir_inlineContext extends ParserRuleContext {
        public TerminalNode PIR() {
            return getToken(RubyParser.PIR, 0);
        }

        public CrlfContext crlf() {
            return getRuleContext(CrlfContext.class, 0);
        }

        public Pir_expression_listContext pir_expression_list() {
            return getRuleContext(Pir_expression_listContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public Pir_inlineContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pir_inline;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterPir_inline(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitPir_inline(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitPir_inline(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Pir_inlineContext pir_inline() throws RecognitionException {
        Pir_inlineContext _localctx = new Pir_inlineContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_pir_inline);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(369);
                match(PIR);
                setState(370);
                crlf();
                setState(371);
                pir_expression_list();
                setState(372);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Pir_expression_listContext extends ParserRuleContext {
        public Expression_listContext expression_list() {
            return getRuleContext(Expression_listContext.class, 0);
        }

        public Pir_expression_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pir_expression_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterPir_expression_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitPir_expression_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitPir_expression_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Pir_expression_listContext pir_expression_list() throws RecognitionException {
        Pir_expression_listContext _localctx = new Pir_expression_listContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_pir_expression_list);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(374);
                expression_list(0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Class_definitionContext extends ParserRuleContext {
        public LvalueContext superclass_id;

        public List<LvalueContext> lvalue() {
            return getRuleContexts(LvalueContext.class);
        }

        public LvalueContext lvalue(int i) {
            return getRuleContext(LvalueContext.class, i);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public TerminalNode LESS() {
            return getToken(RubyParser.LESS, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public Statement_expression_listContext statement_expression_list() {
            return getRuleContext(Statement_expression_listContext.class, 0);
        }

        public Class_definitionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_class_definition;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterClass_definition(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitClass_definition(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitClass_definition(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Class_definitionContext class_definition() throws RecognitionException {
        Class_definitionContext _localctx = new Class_definitionContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_class_definition);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(376);
                match(T__0);
                setState(377);
                lvalue();
                setState(380);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == LESS) {
                    {
                        setState(378);
                        match(LESS);
                        setState(379);
                        ((Class_definitionContext) _localctx).superclass_id = lvalue();
                    }
                }

                setState(385);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(382);
                                crlf();
                            }
                        }
                    }
                    setState(387);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
                }
                setState(389);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
                    case 1: {
                        setState(388);
                        statement_expression_list(0);
                    }
                    break;
                }
                setState(394);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == CRLF) {
                    {
                        {
                            setState(391);
                            crlf();
                        }
                    }
                    setState(396);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(397);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Module_definitionContext extends ParserRuleContext {
        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public List<TerminalNode> CRLF() {
            return getTokens(RubyParser.CRLF);
        }

        public TerminalNode CRLF(int i) {
            return getToken(RubyParser.CRLF, i);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public Statement_expression_listContext statement_expression_list() {
            return getRuleContext(Statement_expression_listContext.class, 0);
        }

        public Module_definitionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_module_definition;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterModule_definition(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitModule_definition(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitModule_definition(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Module_definitionContext module_definition() throws RecognitionException {
        Module_definitionContext _localctx = new Module_definitionContext(_ctx, getState());
        enterRule(_localctx, 48, RULE_module_definition);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(399);
                match(T__1);
                setState(400);
                id_();
                setState(401);
                match(CRLF);
                setState(403);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4755801221666437654L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 130133L) != 0)) {
                    {
                        setState(402);
                        statement_expression_list(0);
                    }
                }

                setState(405);
                match(CRLF);
                setState(406);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_definitionContext extends ParserRuleContext {
        public Function_definition_headerContext function_definition_header() {
            return getRuleContext(Function_definition_headerContext.class, 0);
        }

        public Function_definition_bodyContext function_definition_body() {
            return getRuleContext(Function_definition_bodyContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public Function_definitionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_definition;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_definition(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_definition(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_definition(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_definitionContext function_definition() throws RecognitionException {
        Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
        enterRule(_localctx, 50, RULE_function_definition);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(408);
                function_definition_header();
                setState(409);
                function_definition_body();
                setState(410);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_definition_bodyContext extends ParserRuleContext {
        public Expression_listContext expression_list() {
            return getRuleContext(Expression_listContext.class, 0);
        }

        public Function_definition_bodyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_definition_body;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_definition_body(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_definition_body(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_definition_body(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_definition_bodyContext function_definition_body() throws RecognitionException {
        Function_definition_bodyContext _localctx = new Function_definition_bodyContext(_ctx, getState());
        enterRule(_localctx, 52, RULE_function_definition_body);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(412);
                expression_list(0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_definition_headerContext extends ParserRuleContext {
        public TerminalNode DEF() {
            return getToken(RubyParser.DEF, 0);
        }

        public Function_nameContext function_name() {
            return getRuleContext(Function_nameContext.class, 0);
        }

        public CrlfContext crlf() {
            return getRuleContext(CrlfContext.class, 0);
        }

        public Function_definition_paramsContext function_definition_params() {
            return getRuleContext(Function_definition_paramsContext.class, 0);
        }

        public Function_definition_headerContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_definition_header;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_definition_header(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_definition_header(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_definition_header(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_definition_headerContext function_definition_header() throws RecognitionException {
        Function_definition_headerContext _localctx = new Function_definition_headerContext(_ctx, getState());
        enterRule(_localctx, 54, RULE_function_definition_header);
        try {
            setState(423);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 24, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(414);
                    match(DEF);
                    setState(415);
                    function_name();
                    setState(416);
                    crlf();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(418);
                    match(DEF);
                    setState(419);
                    function_name();
                    setState(420);
                    function_definition_params();
                    setState(421);
                    crlf();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_nameContext extends ParserRuleContext {
        public Id_functionContext id_function() {
            return getRuleContext(Id_functionContext.class, 0);
        }

        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public Function_nameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_name;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_name(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_name(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFunction_name(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_nameContext function_name() throws RecognitionException {
        Function_nameContext _localctx = new Function_nameContext(_ctx, getState());
        enterRule(_localctx, 56, RULE_function_name);
        try {
            setState(427);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID_FUNCTION:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(425);
                    id_function();
                }
                break;
                case ID:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(426);
                    id_();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_definition_paramsContext extends ParserRuleContext {
        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public Function_definition_params_listContext function_definition_params_list() {
            return getRuleContext(Function_definition_params_listContext.class, 0);
        }

        public Function_definition_paramsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_definition_params;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_definition_params(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_definition_params(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_definition_params(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_definition_paramsContext function_definition_params() throws RecognitionException {
        Function_definition_paramsContext _localctx = new Function_definition_paramsContext(_ctx, getState());
        enterRule(_localctx, 58, RULE_function_definition_params);
        try {
            setState(436);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(429);
                    match(LEFT_RBRACKET);
                    setState(430);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(431);
                    match(LEFT_RBRACKET);
                    setState(432);
                    function_definition_params_list(0);
                    setState(433);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(435);
                    function_definition_params_list(0);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_definition_params_listContext extends ParserRuleContext {
        public Function_definition_param_idContext function_definition_param_id() {
            return getRuleContext(Function_definition_param_idContext.class, 0);
        }

        public Function_definition_params_listContext function_definition_params_list() {
            return getRuleContext(Function_definition_params_listContext.class, 0);
        }

        public TerminalNode COMMA() {
            return getToken(RubyParser.COMMA, 0);
        }

        public Function_definition_params_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_definition_params_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_definition_params_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_definition_params_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_definition_params_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_definition_params_listContext function_definition_params_list() throws RecognitionException {
        return function_definition_params_list(0);
    }

    private Function_definition_params_listContext function_definition_params_list(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Function_definition_params_listContext _localctx = new Function_definition_params_listContext(_ctx, _parentState);
        Function_definition_params_listContext _prevctx = _localctx;
        int _startState = 60;
        enterRecursionRule(_localctx, 60, RULE_function_definition_params_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(439);
                    function_definition_param_id();
                }
                _ctx.stop = _input.LT(-1);
                setState(446);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new Function_definition_params_listContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_function_definition_params_list);
                                setState(441);
                                if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                setState(442);
                                match(COMMA);
                                setState(443);
                                function_definition_param_id();
                            }
                        }
                    }
                    setState(448);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_definition_param_idContext extends ParserRuleContext {
        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public Function_definition_param_idContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_definition_param_id;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_definition_param_id(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_definition_param_id(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_definition_param_id(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_definition_param_idContext function_definition_param_id() throws RecognitionException {
        Function_definition_param_idContext _localctx = new Function_definition_param_idContext(_ctx, getState());
        enterRule(_localctx, 62, RULE_function_definition_param_id);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(449);
                id_();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Return_statementContext extends ParserRuleContext {
        public TerminalNode RETURN() {
            return getToken(RubyParser.RETURN, 0);
        }

        public All_resultContext all_result() {
            return getRuleContext(All_resultContext.class, 0);
        }

        public Return_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_return_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterReturn_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitReturn_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitReturn_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Return_statementContext return_statement() throws RecognitionException {
        Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
        enterRule(_localctx, 64, RULE_return_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(451);
                match(RETURN);
                setState(452);
                all_result();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_callContext extends ParserRuleContext {
        public Function_nameContext name;
        public Function_call_param_listContext params;

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public Function_nameContext function_name() {
            return getRuleContext(Function_nameContext.class, 0);
        }

        public Function_call_param_listContext function_call_param_list() {
            return getRuleContext(Function_call_param_listContext.class, 0);
        }

        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public TerminalNode DOT() {
            return getToken(RubyParser.DOT, 0);
        }

        public Function_callContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_call;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_call(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_call(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFunction_call(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_callContext function_call() throws RecognitionException {
        Function_callContext _localctx = new Function_callContext(_ctx, getState());
        enterRule(_localctx, 66, RULE_function_call);
        int _la;
        try {
            setState(496);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 32, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(454);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(455);
                    match(LEFT_RBRACKET);
                    setState(456);
                    ((Function_callContext) _localctx).params = function_call_param_list();
                    setState(457);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(459);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(460);
                    ((Function_callContext) _localctx).params = function_call_param_list();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(462);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(463);
                    match(LEFT_RBRACKET);
                    setState(464);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(466);
                    id_();
                    setState(467);
                    match(DOT);
                    setState(468);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(469);
                    match(LEFT_RBRACKET);
                    setState(471);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == LITERAL || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 89105L) != 0)) {
                        {
                            setState(470);
                            ((Function_callContext) _localctx).params = function_call_param_list();
                        }
                    }

                    setState(473);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(475);
                    id_();
                    setState(476);
                    match(DOT);
                    setState(477);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(479);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
                        case 1: {
                            setState(478);
                            ((Function_callContext) _localctx).params = function_call_param_list();
                        }
                        break;
                    }
                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(481);
                    id_();
                    setState(482);
                    match(T__2);
                    setState(483);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(484);
                    match(LEFT_RBRACKET);
                    setState(486);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == LITERAL || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 89105L) != 0)) {
                        {
                            setState(485);
                            ((Function_callContext) _localctx).params = function_call_param_list();
                        }
                    }

                    setState(488);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 7:
                    enterOuterAlt(_localctx, 7);
                {
                    setState(490);
                    id_();
                    setState(491);
                    match(T__2);
                    setState(492);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(494);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 31, _ctx)) {
                        case 1: {
                            setState(493);
                            ((Function_callContext) _localctx).params = function_call_param_list();
                        }
                        break;
                    }
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_call_param_listContext extends ParserRuleContext {
        public Function_call_paramsContext function_call_params() {
            return getRuleContext(Function_call_paramsContext.class, 0);
        }

        public Function_call_param_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_call_param_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_call_param_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_call_param_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_call_param_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_call_param_listContext function_call_param_list() throws RecognitionException {
        Function_call_param_listContext _localctx = new Function_call_param_listContext(_ctx, getState());
        enterRule(_localctx, 68, RULE_function_call_param_list);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(498);
                function_call_params(0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_call_paramsContext extends ParserRuleContext {
        public Function_paramContext function_param() {
            return getRuleContext(Function_paramContext.class, 0);
        }

        public Function_call_paramsContext function_call_params() {
            return getRuleContext(Function_call_paramsContext.class, 0);
        }

        public TerminalNode COMMA() {
            return getToken(RubyParser.COMMA, 0);
        }

        public Function_call_paramsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_call_params;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_call_params(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_call_params(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_call_params(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_call_paramsContext function_call_params() throws RecognitionException {
        return function_call_params(0);
    }

    private Function_call_paramsContext function_call_params(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Function_call_paramsContext _localctx = new Function_call_paramsContext(_ctx, _parentState);
        Function_call_paramsContext _prevctx = _localctx;
        int _startState = 70;
        enterRecursionRule(_localctx, 70, RULE_function_call_params, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(501);
                    function_param();
                }
                _ctx.stop = _input.LT(-1);
                setState(508);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 33, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new Function_call_paramsContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_function_call_params);
                                setState(503);
                                if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                setState(504);
                                match(COMMA);
                                setState(505);
                                function_param();
                            }
                        }
                    }
                    setState(510);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 33, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_paramContext extends ParserRuleContext {
        public Function_unnamed_paramContext function_unnamed_param() {
            return getRuleContext(Function_unnamed_paramContext.class, 0);
        }

        public Function_named_paramContext function_named_param() {
            return getRuleContext(Function_named_paramContext.class, 0);
        }

        public Function_paramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_param;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_param(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_param(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFunction_param(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_paramContext function_param() throws RecognitionException {
        Function_paramContext _localctx = new Function_paramContext(_ctx, getState());
        enterRule(_localctx, 72, RULE_function_param);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(513);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 34, _ctx)) {
                    case 1: {
                        setState(511);
                        function_unnamed_param();
                    }
                    break;
                    case 2: {
                        setState(512);
                        function_named_param();
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_unnamed_paramContext extends ParserRuleContext {
        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public Float_resultContext float_result() {
            return getRuleContext(Float_resultContext.class, 0);
        }

        public String_resultContext string_result() {
            return getRuleContext(String_resultContext.class, 0);
        }

        public Dynamic_resultContext dynamic_result() {
            return getRuleContext(Dynamic_resultContext.class, 0);
        }

        public Function_unnamed_paramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_unnamed_param;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_unnamed_param(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_unnamed_param(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_unnamed_param(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_unnamed_paramContext function_unnamed_param() throws RecognitionException {
        Function_unnamed_paramContext _localctx = new Function_unnamed_paramContext(_ctx, getState());
        enterRule(_localctx, 74, RULE_function_unnamed_param);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(519);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
                    case 1: {
                        setState(515);
                        int_result(0);
                    }
                    break;
                    case 2: {
                        setState(516);
                        float_result(0);
                    }
                    break;
                    case 3: {
                        setState(517);
                        string_result(0);
                    }
                    break;
                    case 4: {
                        setState(518);
                        dynamic_result(0);
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_named_paramContext extends ParserRuleContext {
        public Token op;

        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public Float_resultContext float_result() {
            return getRuleContext(Float_resultContext.class, 0);
        }

        public String_resultContext string_result() {
            return getRuleContext(String_resultContext.class, 0);
        }

        public Dynamic_resultContext dynamic_result() {
            return getRuleContext(Dynamic_resultContext.class, 0);
        }

        public Function_named_paramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_named_param;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_named_param(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_named_param(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_named_param(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_named_paramContext function_named_param() throws RecognitionException {
        Function_named_paramContext _localctx = new Function_named_paramContext(_ctx, getState());
        enterRule(_localctx, 76, RULE_function_named_param);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(521);
                id_();
                setState(522);
                ((Function_named_paramContext) _localctx).op = match(ASSIGN);
                setState(527);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 36, _ctx)) {
                    case 1: {
                        setState(523);
                        int_result(0);
                    }
                    break;
                    case 2: {
                        setState(524);
                        float_result(0);
                    }
                    break;
                    case 3: {
                        setState(525);
                        string_result(0);
                    }
                    break;
                    case 4: {
                        setState(526);
                        dynamic_result(0);
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Function_call_assignmentContext extends ParserRuleContext {
        public Function_callContext function_call() {
            return getRuleContext(Function_callContext.class, 0);
        }

        public Function_call_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_function_call_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFunction_call_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFunction_call_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFunction_call_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Function_call_assignmentContext function_call_assignment() throws RecognitionException {
        Function_call_assignmentContext _localctx = new Function_call_assignmentContext(_ctx, getState());
        enterRule(_localctx, 78, RULE_function_call_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(529);
                function_call();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class All_resultContext extends ParserRuleContext {
        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public Float_resultContext float_result() {
            return getRuleContext(Float_resultContext.class, 0);
        }

        public String_resultContext string_result() {
            return getRuleContext(String_resultContext.class, 0);
        }

        public Dynamic_resultContext dynamic_result() {
            return getRuleContext(Dynamic_resultContext.class, 0);
        }

        public Global_resultContext global_result() {
            return getRuleContext(Global_resultContext.class, 0);
        }

        public Instance_resultContext instance_result() {
            return getRuleContext(Instance_resultContext.class, 0);
        }

        public All_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_all_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterAll_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitAll_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitAll_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final All_resultContext all_result() throws RecognitionException {
        All_resultContext _localctx = new All_resultContext(_ctx, getState());
        enterRule(_localctx, 80, RULE_all_result);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(537);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 37, _ctx)) {
                    case 1: {
                        setState(531);
                        int_result(0);
                    }
                    break;
                    case 2: {
                        setState(532);
                        float_result(0);
                    }
                    break;
                    case 3: {
                        setState(533);
                        string_result(0);
                    }
                    break;
                    case 4: {
                        setState(534);
                        dynamic_result(0);
                    }
                    break;
                    case 5: {
                        setState(535);
                        global_result();
                    }
                    break;
                    case 6: {
                        setState(536);
                        instance_result();
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Elsif_statementContext extends ParserRuleContext {
        public If_elsif_statementContext if_elsif_statement() {
            return getRuleContext(If_elsif_statementContext.class, 0);
        }

        public Elsif_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_elsif_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterElsif_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitElsif_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitElsif_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Elsif_statementContext elsif_statement() throws RecognitionException {
        Elsif_statementContext _localctx = new Elsif_statementContext(_ctx, getState());
        enterRule(_localctx, 82, RULE_elsif_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(539);
                if_elsif_statement();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class If_elsif_statementContext extends ParserRuleContext {
        public TerminalNode ELSIF() {
            return getToken(RubyParser.ELSIF, 0);
        }

        public Cond_expressionContext cond_expression() {
            return getRuleContext(Cond_expressionContext.class, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public List<Statement_bodyContext> statement_body() {
            return getRuleContexts(Statement_bodyContext.class);
        }

        public Statement_bodyContext statement_body(int i) {
            return getRuleContext(Statement_bodyContext.class, i);
        }

        public TerminalNode THEN() {
            return getToken(RubyParser.THEN, 0);
        }

        public Else_tokenContext else_token() {
            return getRuleContext(Else_tokenContext.class, 0);
        }

        public If_elsif_statementContext if_elsif_statement() {
            return getRuleContext(If_elsif_statementContext.class, 0);
        }

        public If_elsif_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_if_elsif_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterIf_elsif_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitIf_elsif_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitIf_elsif_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final If_elsif_statementContext if_elsif_statement() throws RecognitionException {
        If_elsif_statementContext _localctx = new If_elsif_statementContext(_ctx, getState());
        enterRule(_localctx, 84, RULE_if_elsif_statement);
        int _la;
        try {
            setState(569);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 41, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(541);
                    match(ELSIF);
                    setState(542);
                    cond_expression();
                    setState(544);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == THEN) {
                        {
                            setState(543);
                            match(THEN);
                        }
                    }

                    setState(546);
                    crlf();
                    setState(547);
                    statement_body();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(549);
                    match(ELSIF);
                    setState(550);
                    cond_expression();
                    setState(552);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == THEN) {
                        {
                            setState(551);
                            match(THEN);
                        }
                    }

                    setState(554);
                    crlf();
                    setState(555);
                    statement_body();
                    setState(556);
                    else_token();
                    setState(557);
                    crlf();
                    setState(558);
                    statement_body();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(560);
                    match(ELSIF);
                    setState(561);
                    cond_expression();
                    setState(563);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == THEN) {
                        {
                            setState(562);
                            match(THEN);
                        }
                    }

                    setState(565);
                    crlf();
                    setState(566);
                    statement_body();
                    setState(567);
                    if_elsif_statement();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class If_statementContext extends ParserRuleContext {
        public TerminalNode IF() {
            return getToken(RubyParser.IF, 0);
        }

        public Cond_expressionContext cond_expression() {
            return getRuleContext(Cond_expressionContext.class, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public List<Statement_bodyContext> statement_body() {
            return getRuleContexts(Statement_bodyContext.class);
        }

        public Statement_bodyContext statement_body(int i) {
            return getRuleContext(Statement_bodyContext.class, i);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public TerminalNode THEN() {
            return getToken(RubyParser.THEN, 0);
        }

        public Else_tokenContext else_token() {
            return getRuleContext(Else_tokenContext.class, 0);
        }

        public Elsif_statementContext elsif_statement() {
            return getRuleContext(Elsif_statementContext.class, 0);
        }

        public If_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_if_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterIf_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitIf_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitIf_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final If_statementContext if_statement() throws RecognitionException {
        If_statementContext _localctx = new If_statementContext(_ctx, getState());
        enterRule(_localctx, 86, RULE_if_statement);
        int _la;
        try {
            setState(602);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 45, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(571);
                    match(IF);
                    setState(572);
                    cond_expression();
                    setState(574);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == THEN) {
                        {
                            setState(573);
                            match(THEN);
                        }
                    }

                    setState(576);
                    crlf();
                    setState(577);
                    statement_body();
                    setState(578);
                    match(END);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(580);
                    match(IF);
                    setState(581);
                    cond_expression();
                    setState(583);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == THEN) {
                        {
                            setState(582);
                            match(THEN);
                        }
                    }

                    setState(585);
                    crlf();
                    setState(586);
                    statement_body();
                    setState(587);
                    else_token();
                    setState(588);
                    crlf();
                    setState(589);
                    statement_body();
                    setState(590);
                    match(END);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(592);
                    match(IF);
                    setState(593);
                    cond_expression();
                    setState(595);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == THEN) {
                        {
                            setState(594);
                            match(THEN);
                        }
                    }

                    setState(597);
                    crlf();
                    setState(598);
                    statement_body();
                    setState(599);
                    elsif_statement();
                    setState(600);
                    match(END);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Unless_statementContext extends ParserRuleContext {
        public TerminalNode UNLESS() {
            return getToken(RubyParser.UNLESS, 0);
        }

        public Cond_expressionContext cond_expression() {
            return getRuleContext(Cond_expressionContext.class, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public List<Statement_bodyContext> statement_body() {
            return getRuleContexts(Statement_bodyContext.class);
        }

        public Statement_bodyContext statement_body(int i) {
            return getRuleContext(Statement_bodyContext.class, i);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public Else_tokenContext else_token() {
            return getRuleContext(Else_tokenContext.class, 0);
        }

        public Elsif_statementContext elsif_statement() {
            return getRuleContext(Elsif_statementContext.class, 0);
        }

        public Unless_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unless_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterUnless_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitUnless_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitUnless_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Unless_statementContext unless_statement() throws RecognitionException {
        Unless_statementContext _localctx = new Unless_statementContext(_ctx, getState());
        enterRule(_localctx, 88, RULE_unless_statement);
        try {
            setState(626);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 46, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(604);
                    match(UNLESS);
                    setState(605);
                    cond_expression();
                    setState(606);
                    crlf();
                    setState(607);
                    statement_body();
                    setState(608);
                    match(END);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(610);
                    match(UNLESS);
                    setState(611);
                    cond_expression();
                    setState(612);
                    crlf();
                    setState(613);
                    statement_body();
                    setState(614);
                    else_token();
                    setState(615);
                    crlf();
                    setState(616);
                    statement_body();
                    setState(617);
                    match(END);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(619);
                    match(UNLESS);
                    setState(620);
                    cond_expression();
                    setState(621);
                    crlf();
                    setState(622);
                    statement_body();
                    setState(623);
                    elsif_statement();
                    setState(624);
                    match(END);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class While_statementContext extends ParserRuleContext {
        public TerminalNode WHILE() {
            return getToken(RubyParser.WHILE, 0);
        }

        public Cond_expressionContext cond_expression() {
            return getRuleContext(Cond_expressionContext.class, 0);
        }

        public CrlfContext crlf() {
            return getRuleContext(CrlfContext.class, 0);
        }

        public Statement_bodyContext statement_body() {
            return getRuleContext(Statement_bodyContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public While_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_while_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterWhile_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitWhile_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitWhile_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final While_statementContext while_statement() throws RecognitionException {
        While_statementContext _localctx = new While_statementContext(_ctx, getState());
        enterRule(_localctx, 90, RULE_while_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(628);
                match(WHILE);
                setState(629);
                cond_expression();
                setState(630);
                crlf();
                setState(631);
                statement_body();
                setState(632);
                match(END);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class All_assignmentContext extends ParserRuleContext {
        public Int_assignmentContext int_assignment() {
            return getRuleContext(Int_assignmentContext.class, 0);
        }

        public Float_assignmentContext float_assignment() {
            return getRuleContext(Float_assignmentContext.class, 0);
        }

        public String_assignmentContext string_assignment() {
            return getRuleContext(String_assignmentContext.class, 0);
        }

        public Dynamic_assignmentContext dynamic_assignment() {
            return getRuleContext(Dynamic_assignmentContext.class, 0);
        }

        public All_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_all_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterAll_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitAll_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitAll_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final All_assignmentContext all_assignment() throws RecognitionException {
        All_assignmentContext _localctx = new All_assignmentContext(_ctx, getState());
        enterRule(_localctx, 92, RULE_all_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(638);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 47, _ctx)) {
                    case 1: {
                        setState(634);
                        int_assignment();
                    }
                    break;
                    case 2: {
                        setState(635);
                        float_assignment();
                    }
                    break;
                    case 3: {
                        setState(636);
                        string_assignment();
                    }
                    break;
                    case 4: {
                        setState(637);
                        dynamic_assignment();
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class For_statementContext extends ParserRuleContext {
        public TerminalNode FOR() {
            return getToken(RubyParser.FOR, 0);
        }

        public List<LvalueContext> lvalue() {
            return getRuleContexts(LvalueContext.class);
        }

        public LvalueContext lvalue(int i) {
            return getRuleContext(LvalueContext.class, i);
        }

        public TerminalNode IN() {
            return getToken(RubyParser.IN, 0);
        }

        public Loop_expressionContext loop_expression() {
            return getRuleContext(Loop_expressionContext.class, 0);
        }

        public Statement_bodyContext statement_body() {
            return getRuleContext(Statement_bodyContext.class, 0);
        }

        public TerminalNode END() {
            return getToken(RubyParser.END, 0);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(RubyParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(RubyParser.COMMA, i);
        }

        public TerminalNode DO() {
            return getToken(RubyParser.DO, 0);
        }

        public List<TerminalNode> CRLF() {
            return getTokens(RubyParser.CRLF);
        }

        public TerminalNode CRLF(int i) {
            return getToken(RubyParser.CRLF, i);
        }

        public For_each_statementContext for_each_statement() {
            return getRuleContext(For_each_statementContext.class, 0);
        }

        public For_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_for_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFor_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFor_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFor_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final For_statementContext for_statement() throws RecognitionException {
        For_statementContext _localctx = new For_statementContext(_ctx, getState());
        enterRule(_localctx, 94, RULE_for_statement);
        int _la;
        try {
            setState(664);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case FOR:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(640);
                    match(FOR);
                    setState(641);
                    lvalue();
                    setState(646);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == COMMA) {
                        {
                            {
                                setState(642);
                                match(COMMA);
                                setState(643);
                                lvalue();
                            }
                        }
                        setState(648);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(649);
                    match(IN);
                    setState(650);
                    loop_expression();
                    setState(652);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == DO) {
                        {
                            setState(651);
                            match(DO);
                        }
                    }

                    setState(657);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == CRLF) {
                        {
                            {
                                setState(654);
                                match(CRLF);
                            }
                        }
                        setState(659);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(660);
                    statement_body();
                    setState(661);
                    match(END);
                }
                break;
                case LITERAL:
                case LEFT_RBRACKET:
                case LEFT_SBRACKET:
                case LEFT_BBRACKET:
                case INT:
                case FLOAT:
                case ID:
                case ID_GLOBAL:
                case ID_FUNCTION:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(663);
                    for_each_statement();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class For_each_statementContext extends ParserRuleContext {
        public Array_definitionContext array_definition() {
            return getRuleContext(Array_definitionContext.class, 0);
        }

        public TerminalNode DOT() {
            return getToken(RubyParser.DOT, 0);
        }

        public TerminalNode EACH() {
            return getToken(RubyParser.EACH, 0);
        }

        public TerminalNode LEFT_BBRACKET() {
            return getToken(RubyParser.LEFT_BBRACKET, 0);
        }

        public List<TerminalNode> BIT_OR() {
            return getTokens(RubyParser.BIT_OR);
        }

        public TerminalNode BIT_OR(int i) {
            return getToken(RubyParser.BIT_OR, i);
        }

        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public Statement_bodyContext statement_body() {
            return getRuleContext(Statement_bodyContext.class, 0);
        }

        public TerminalNode RIGHT_BBRACKET() {
            return getToken(RubyParser.RIGHT_BBRACKET, 0);
        }

        public List<CrlfContext> crlf() {
            return getRuleContexts(CrlfContext.class);
        }

        public CrlfContext crlf(int i) {
            return getRuleContext(CrlfContext.class, i);
        }

        public For_each_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_for_each_statement;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFor_each_statement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFor_each_statement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitFor_each_statement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final For_each_statementContext for_each_statement() throws RecognitionException {
        For_each_statementContext _localctx = new For_each_statementContext(_ctx, getState());
        enterRule(_localctx, 96, RULE_for_each_statement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(666);
                array_definition();
                setState(667);
                match(DOT);
                setState(668);
                match(EACH);
                setState(669);
                match(LEFT_BBRACKET);
                setState(671);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == CRLF) {
                    {
                        setState(670);
                        crlf();
                    }
                }

                setState(673);
                match(BIT_OR);
                setState(674);
                id_();
                setState(675);
                match(BIT_OR);
                setState(677);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == CRLF) {
                    {
                        setState(676);
                        crlf();
                    }
                }

                setState(679);
                statement_body();
                setState(680);
                match(RIGHT_BBRACKET);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Cond_expressionContext extends ParserRuleContext {
        public Comparison_listContext comparison_list() {
            return getRuleContext(Comparison_listContext.class, 0);
        }

        public Cond_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_cond_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterCond_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitCond_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitCond_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Cond_expressionContext cond_expression() throws RecognitionException {
        Cond_expressionContext _localctx = new Cond_expressionContext(_ctx, getState());
        enterRule(_localctx, 98, RULE_cond_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(682);
                comparison_list();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Loop_expressionContext extends ParserRuleContext {
        public Array_definitionContext array_definition() {
            return getRuleContext(Array_definitionContext.class, 0);
        }

        public Loop_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_loop_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterLoop_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitLoop_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitLoop_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Loop_expressionContext loop_expression() throws RecognitionException {
        Loop_expressionContext _localctx = new Loop_expressionContext(_ctx, getState());
        enterRule(_localctx, 100, RULE_loop_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(684);
                array_definition();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Hash_expressionContext extends ParserRuleContext {
        public Token op;

        public TerminalNode LEFT_BBRACKET() {
            return getToken(RubyParser.LEFT_BBRACKET, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode RIGHT_BBRACKET() {
            return getToken(RubyParser.RIGHT_BBRACKET, 0);
        }

        public TerminalNode HASH_OP() {
            return getToken(RubyParser.HASH_OP, 0);
        }

        public Hash_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_hash_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterHash_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitHash_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitHash_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Hash_expressionContext hash_expression() throws RecognitionException {
        Hash_expressionContext _localctx = new Hash_expressionContext(_ctx, getState());
        enterRule(_localctx, 102, RULE_hash_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(686);
                match(LEFT_BBRACKET);
                setState(687);
                expression();
                setState(688);
                ((Hash_expressionContext) _localctx).op = match(HASH_OP);
                setState(689);
                expression();
                setState(690);
                match(RIGHT_BBRACKET);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Statement_bodyContext extends ParserRuleContext {
        public Statement_expression_listContext statement_expression_list() {
            return getRuleContext(Statement_expression_listContext.class, 0);
        }

        public Statement_bodyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement_body;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterStatement_body(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitStatement_body(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitStatement_body(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Statement_bodyContext statement_body() throws RecognitionException {
        Statement_bodyContext _localctx = new Statement_bodyContext(_ctx, getState());
        enterRule(_localctx, 104, RULE_statement_body);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(692);
                statement_expression_list(0);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Statement_expression_listContext extends ParserRuleContext {
        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminatorContext terminator() {
            return getRuleContext(TerminatorContext.class, 0);
        }

        public TerminalNode RETRY() {
            return getToken(RubyParser.RETRY, 0);
        }

        public Break_expressionContext break_expression() {
            return getRuleContext(Break_expressionContext.class, 0);
        }

        public Raise_expressionContext raise_expression() {
            return getRuleContext(Raise_expressionContext.class, 0);
        }

        public Yield_expressionContext yield_expression() {
            return getRuleContext(Yield_expressionContext.class, 0);
        }

        public Statement_expression_listContext statement_expression_list() {
            return getRuleContext(Statement_expression_listContext.class, 0);
        }

        public Statement_expression_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement_expression_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterStatement_expression_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitStatement_expression_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitStatement_expression_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Statement_expression_listContext statement_expression_list() throws RecognitionException {
        return statement_expression_list(0);
    }

    private Statement_expression_listContext statement_expression_list(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Statement_expression_listContext _localctx = new Statement_expression_listContext(_ctx, _parentState);
        Statement_expression_listContext _prevctx = _localctx;
        int _startState = 106;
        enterRecursionRule(_localctx, 106, RULE_statement_expression_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(715);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 59, _ctx)) {
                    case 1: {
                        setState(695);
                        expression();
                        setState(697);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 54, _ctx)) {
                            case 1: {
                                setState(696);
                                terminator(0);
                            }
                            break;
                        }
                    }
                    break;
                    case 2: {
                        setState(699);
                        match(RETRY);
                        setState(701);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 55, _ctx)) {
                            case 1: {
                                setState(700);
                                terminator(0);
                            }
                            break;
                        }
                    }
                    break;
                    case 3: {
                        setState(703);
                        break_expression();
                        setState(705);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 56, _ctx)) {
                            case 1: {
                                setState(704);
                                terminator(0);
                            }
                            break;
                        }
                    }
                    break;
                    case 4: {
                        setState(707);
                        raise_expression();
                        setState(709);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 57, _ctx)) {
                            case 1: {
                                setState(708);
                                terminator(0);
                            }
                            break;
                        }
                    }
                    break;
                    case 5: {
                        setState(711);
                        yield_expression();
                        setState(713);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 58, _ctx)) {
                            case 1: {
                                setState(712);
                                terminator(0);
                            }
                            break;
                        }
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(734);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 64, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(732);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 63, _ctx)) {
                                case 1: {
                                    _localctx = new Statement_expression_listContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_statement_expression_list);
                                    setState(717);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(718);
                                    expression();
                                    setState(720);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 60, _ctx)) {
                                        case 1: {
                                            setState(719);
                                            terminator(0);
                                        }
                                        break;
                                    }
                                }
                                break;
                                case 2: {
                                    _localctx = new Statement_expression_listContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_statement_expression_list);
                                    setState(722);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(723);
                                    match(RETRY);
                                    setState(725);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 61, _ctx)) {
                                        case 1: {
                                            setState(724);
                                            terminator(0);
                                        }
                                        break;
                                    }
                                }
                                break;
                                case 3: {
                                    _localctx = new Statement_expression_listContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_statement_expression_list);
                                    setState(727);
                                    if (!(precpred(_ctx, 1)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                    setState(728);
                                    break_expression();
                                    setState(730);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 62, _ctx)) {
                                        case 1: {
                                            setState(729);
                                            terminator(0);
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    setState(736);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 64, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AssignmentContext extends ParserRuleContext {
        public LvalueContext var_id;
        public Token op;

        public RvalueContext rvalue() {
            return getRuleContext(RvalueContext.class, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public TerminalNode PLUS_ASSIGN() {
            return getToken(RubyParser.PLUS_ASSIGN, 0);
        }

        public TerminalNode MINUS_ASSIGN() {
            return getToken(RubyParser.MINUS_ASSIGN, 0);
        }

        public TerminalNode MUL_ASSIGN() {
            return getToken(RubyParser.MUL_ASSIGN, 0);
        }

        public TerminalNode DIV_ASSIGN() {
            return getToken(RubyParser.DIV_ASSIGN, 0);
        }

        public TerminalNode MOD_ASSIGN() {
            return getToken(RubyParser.MOD_ASSIGN, 0);
        }

        public TerminalNode EXP_ASSIGN() {
            return getToken(RubyParser.EXP_ASSIGN, 0);
        }

        public AssignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterAssignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitAssignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitAssignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AssignmentContext assignment() throws RecognitionException {
        AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
        enterRule(_localctx, 108, RULE_assignment);
        int _la;
        try {
            setState(745);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 65, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(737);
                    ((AssignmentContext) _localctx).var_id = lvalue();
                    setState(738);
                    ((AssignmentContext) _localctx).op = match(ASSIGN);
                    setState(739);
                    rvalue(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(741);
                    ((AssignmentContext) _localctx).var_id = lvalue();
                    setState(742);
                    ((AssignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 17732923532771328L) != 0))) {
                        ((AssignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(743);
                    rvalue(0);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Dynamic_assignmentContext extends ParserRuleContext {
        public LvalueContext var_id;
        public Token op;

        public Dynamic_resultContext dynamic_result() {
            return getRuleContext(Dynamic_resultContext.class, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public TerminalNode PLUS_ASSIGN() {
            return getToken(RubyParser.PLUS_ASSIGN, 0);
        }

        public TerminalNode MINUS_ASSIGN() {
            return getToken(RubyParser.MINUS_ASSIGN, 0);
        }

        public TerminalNode MUL_ASSIGN() {
            return getToken(RubyParser.MUL_ASSIGN, 0);
        }

        public TerminalNode DIV_ASSIGN() {
            return getToken(RubyParser.DIV_ASSIGN, 0);
        }

        public TerminalNode MOD_ASSIGN() {
            return getToken(RubyParser.MOD_ASSIGN, 0);
        }

        public TerminalNode EXP_ASSIGN() {
            return getToken(RubyParser.EXP_ASSIGN, 0);
        }

        public Dynamic_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_dynamic_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterDynamic_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitDynamic_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitDynamic_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Dynamic_assignmentContext dynamic_assignment() throws RecognitionException {
        Dynamic_assignmentContext _localctx = new Dynamic_assignmentContext(_ctx, getState());
        enterRule(_localctx, 110, RULE_dynamic_assignment);
        int _la;
        try {
            setState(755);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 66, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(747);
                    ((Dynamic_assignmentContext) _localctx).var_id = lvalue();
                    setState(748);
                    ((Dynamic_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(749);
                    dynamic_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(751);
                    ((Dynamic_assignmentContext) _localctx).var_id = lvalue();
                    setState(752);
                    ((Dynamic_assignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 17732923532771328L) != 0))) {
                        ((Dynamic_assignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(753);
                    dynamic_result(0);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Int_assignmentContext extends ParserRuleContext {
        public LvalueContext var_id;
        public Token op;

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public TerminalNode PLUS_ASSIGN() {
            return getToken(RubyParser.PLUS_ASSIGN, 0);
        }

        public TerminalNode MINUS_ASSIGN() {
            return getToken(RubyParser.MINUS_ASSIGN, 0);
        }

        public TerminalNode MUL_ASSIGN() {
            return getToken(RubyParser.MUL_ASSIGN, 0);
        }

        public TerminalNode DIV_ASSIGN() {
            return getToken(RubyParser.DIV_ASSIGN, 0);
        }

        public TerminalNode MOD_ASSIGN() {
            return getToken(RubyParser.MOD_ASSIGN, 0);
        }

        public TerminalNode EXP_ASSIGN() {
            return getToken(RubyParser.EXP_ASSIGN, 0);
        }

        public Int_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_int_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInt_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInt_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInt_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Int_assignmentContext int_assignment() throws RecognitionException {
        Int_assignmentContext _localctx = new Int_assignmentContext(_ctx, getState());
        enterRule(_localctx, 112, RULE_int_assignment);
        int _la;
        try {
            setState(765);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 67, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(757);
                    ((Int_assignmentContext) _localctx).var_id = lvalue();
                    setState(758);
                    ((Int_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(759);
                    int_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(761);
                    ((Int_assignmentContext) _localctx).var_id = lvalue();
                    setState(762);
                    ((Int_assignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 17732923532771328L) != 0))) {
                        ((Int_assignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(763);
                    int_result(0);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Float_assignmentContext extends ParserRuleContext {
        public LvalueContext var_id;
        public Token op;

        public Float_resultContext float_result() {
            return getRuleContext(Float_resultContext.class, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public TerminalNode PLUS_ASSIGN() {
            return getToken(RubyParser.PLUS_ASSIGN, 0);
        }

        public TerminalNode MINUS_ASSIGN() {
            return getToken(RubyParser.MINUS_ASSIGN, 0);
        }

        public TerminalNode MUL_ASSIGN() {
            return getToken(RubyParser.MUL_ASSIGN, 0);
        }

        public TerminalNode DIV_ASSIGN() {
            return getToken(RubyParser.DIV_ASSIGN, 0);
        }

        public TerminalNode MOD_ASSIGN() {
            return getToken(RubyParser.MOD_ASSIGN, 0);
        }

        public TerminalNode EXP_ASSIGN() {
            return getToken(RubyParser.EXP_ASSIGN, 0);
        }

        public Float_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_float_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFloat_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFloat_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFloat_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Float_assignmentContext float_assignment() throws RecognitionException {
        Float_assignmentContext _localctx = new Float_assignmentContext(_ctx, getState());
        enterRule(_localctx, 114, RULE_float_assignment);
        int _la;
        try {
            setState(775);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 68, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(767);
                    ((Float_assignmentContext) _localctx).var_id = lvalue();
                    setState(768);
                    ((Float_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(769);
                    float_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(771);
                    ((Float_assignmentContext) _localctx).var_id = lvalue();
                    setState(772);
                    ((Float_assignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 17732923532771328L) != 0))) {
                        ((Float_assignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(773);
                    float_result(0);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class String_assignmentContext extends ParserRuleContext {
        public LvalueContext var_id;
        public Token op;

        public String_resultContext string_result() {
            return getRuleContext(String_resultContext.class, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public TerminalNode PLUS_ASSIGN() {
            return getToken(RubyParser.PLUS_ASSIGN, 0);
        }

        public String_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_string_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterString_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitString_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitString_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final String_assignmentContext string_assignment() throws RecognitionException {
        String_assignmentContext _localctx = new String_assignmentContext(_ctx, getState());
        enterRule(_localctx, 116, RULE_string_assignment);
        try {
            setState(785);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 69, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(777);
                    ((String_assignmentContext) _localctx).var_id = lvalue();
                    setState(778);
                    ((String_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(779);
                    string_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(781);
                    ((String_assignmentContext) _localctx).var_id = lvalue();
                    setState(782);
                    ((String_assignmentContext) _localctx).op = match(PLUS_ASSIGN);
                    setState(783);
                    string_result(0);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Initial_array_assignmentContext extends ParserRuleContext {
        public LvalueContext var_id;
        public Token op;

        public TerminalNode LEFT_SBRACKET() {
            return getToken(RubyParser.LEFT_SBRACKET, 0);
        }

        public TerminalNode RIGHT_SBRACKET() {
            return getToken(RubyParser.RIGHT_SBRACKET, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public Initial_array_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initial_array_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInitial_array_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInitial_array_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitInitial_array_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Initial_array_assignmentContext initial_array_assignment() throws RecognitionException {
        Initial_array_assignmentContext _localctx = new Initial_array_assignmentContext(_ctx, getState());
        enterRule(_localctx, 118, RULE_initial_array_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(787);
                ((Initial_array_assignmentContext) _localctx).var_id = lvalue();
                setState(788);
                ((Initial_array_assignmentContext) _localctx).op = match(ASSIGN);
                setState(789);
                match(LEFT_SBRACKET);
                setState(790);
                match(RIGHT_SBRACKET);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Array_assignmentContext extends ParserRuleContext {
        public Array_selectorContext arr_def;
        public Token op;
        public All_resultContext arr_val;

        public Array_selectorContext array_selector() {
            return getRuleContext(Array_selectorContext.class, 0);
        }

        public TerminalNode ASSIGN() {
            return getToken(RubyParser.ASSIGN, 0);
        }

        public All_resultContext all_result() {
            return getRuleContext(All_resultContext.class, 0);
        }

        public Array_assignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_array_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterArray_assignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitArray_assignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitArray_assignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Array_assignmentContext array_assignment() throws RecognitionException {
        Array_assignmentContext _localctx = new Array_assignmentContext(_ctx, getState());
        enterRule(_localctx, 120, RULE_array_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(792);
                ((Array_assignmentContext) _localctx).arr_def = array_selector();
                setState(793);
                ((Array_assignmentContext) _localctx).op = match(ASSIGN);
                setState(794);
                ((Array_assignmentContext) _localctx).arr_val = all_result();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Array_definitionContext extends ParserRuleContext {
        public TerminalNode LEFT_SBRACKET() {
            return getToken(RubyParser.LEFT_SBRACKET, 0);
        }

        public Array_definition_elementsContext array_definition_elements() {
            return getRuleContext(Array_definition_elementsContext.class, 0);
        }

        public TerminalNode RIGHT_SBRACKET() {
            return getToken(RubyParser.RIGHT_SBRACKET, 0);
        }

        public Array_definitionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_array_definition;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterArray_definition(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitArray_definition(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitArray_definition(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Array_definitionContext array_definition() throws RecognitionException {
        Array_definitionContext _localctx = new Array_definitionContext(_ctx, getState());
        enterRule(_localctx, 122, RULE_array_definition);
        try {
            setState(801);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case LEFT_SBRACKET:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(796);
                    match(LEFT_SBRACKET);
                    setState(797);
                    array_definition_elements(0);
                    setState(798);
                    match(RIGHT_SBRACKET);
                }
                break;
                case LITERAL:
                case LEFT_RBRACKET:
                case LEFT_BBRACKET:
                case INT:
                case FLOAT:
                case ID:
                case ID_GLOBAL:
                case ID_FUNCTION:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(800);
                    array_definition_elements(0);
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Array_definition_elementsContext extends ParserRuleContext {
        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public Dynamic_resultContext dynamic_result() {
            return getRuleContext(Dynamic_resultContext.class, 0);
        }

        public Array_definition_elementsContext array_definition_elements() {
            return getRuleContext(Array_definition_elementsContext.class, 0);
        }

        public TerminalNode COMMA() {
            return getToken(RubyParser.COMMA, 0);
        }

        public TerminalNode ELLIPSIS() {
            return getToken(RubyParser.ELLIPSIS, 0);
        }

        public Array_definition_elementsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_array_definition_elements;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterArray_definition_elements(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitArray_definition_elements(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor)
                return ((RubyVisitor<? extends T>) visitor).visitArray_definition_elements(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Array_definition_elementsContext array_definition_elements() throws RecognitionException {
        return array_definition_elements(0);
    }

    private Array_definition_elementsContext array_definition_elements(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Array_definition_elementsContext _localctx = new Array_definition_elementsContext(_ctx, _parentState);
        Array_definition_elementsContext _prevctx = _localctx;
        int _startState = 124;
        enterRecursionRule(_localctx, 124, RULE_array_definition_elements, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(806);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 71, _ctx)) {
                        case 1: {
                            setState(804);
                            int_result(0);
                        }
                        break;
                        case 2: {
                            setState(805);
                            dynamic_result(0);
                        }
                        break;
                    }
                }
                _ctx.stop = _input.LT(-1);
                setState(822);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 75, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(820);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 74, _ctx)) {
                                case 1: {
                                    _localctx = new Array_definition_elementsContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_array_definition_elements);
                                    setState(808);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(809);
                                    match(COMMA);
                                    setState(812);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 72, _ctx)) {
                                        case 1: {
                                            setState(810);
                                            int_result(0);
                                        }
                                        break;
                                        case 2: {
                                            setState(811);
                                            dynamic_result(0);
                                        }
                                        break;
                                    }
                                }
                                break;
                                case 2: {
                                    _localctx = new Array_definition_elementsContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_array_definition_elements);
                                    setState(814);
                                    if (!(precpred(_ctx, 1)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                    setState(815);
                                    match(ELLIPSIS);
                                    setState(818);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 73, _ctx)) {
                                        case 1: {
                                            setState(816);
                                            int_result(0);
                                        }
                                        break;
                                        case 2: {
                                            setState(817);
                                            dynamic_result(0);
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    setState(824);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 75, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Array_selectorContext extends ParserRuleContext {
        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public TerminalNode LEFT_SBRACKET() {
            return getToken(RubyParser.LEFT_SBRACKET, 0);
        }

        public TerminalNode RIGHT_SBRACKET() {
            return getToken(RubyParser.RIGHT_SBRACKET, 0);
        }

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public Dynamic_resultContext dynamic_result() {
            return getRuleContext(Dynamic_resultContext.class, 0);
        }

        public Id_globalContext id_global() {
            return getRuleContext(Id_globalContext.class, 0);
        }

        public Array_selectorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_array_selector;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterArray_selector(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitArray_selector(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitArray_selector(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Array_selectorContext array_selector() throws RecognitionException {
        Array_selectorContext _localctx = new Array_selectorContext(_ctx, getState());
        enterRule(_localctx, 126, RULE_array_selector);
        try {
            setState(841);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(825);
                    id_();
                    setState(826);
                    match(LEFT_SBRACKET);
                    setState(829);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 76, _ctx)) {
                        case 1: {
                            setState(827);
                            int_result(0);
                        }
                        break;
                        case 2: {
                            setState(828);
                            dynamic_result(0);
                        }
                        break;
                    }
                    setState(831);
                    match(RIGHT_SBRACKET);
                }
                break;
                case ID_GLOBAL:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(833);
                    id_global();
                    setState(834);
                    match(LEFT_SBRACKET);
                    setState(837);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 77, _ctx)) {
                        case 1: {
                            setState(835);
                            int_result(0);
                        }
                        break;
                        case 2: {
                            setState(836);
                            dynamic_result(0);
                        }
                        break;
                    }
                    setState(839);
                    match(RIGHT_SBRACKET);
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Dynamic_resultContext extends ParserRuleContext {
        public Token op;

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public List<Dynamic_resultContext> dynamic_result() {
            return getRuleContexts(Dynamic_resultContext.class);
        }

        public Dynamic_resultContext dynamic_result(int i) {
            return getRuleContext(Dynamic_resultContext.class, i);
        }

        public TerminalNode MUL() {
            return getToken(RubyParser.MUL, 0);
        }

        public TerminalNode DIV() {
            return getToken(RubyParser.DIV, 0);
        }

        public TerminalNode MOD() {
            return getToken(RubyParser.MOD, 0);
        }

        public Float_resultContext float_result() {
            return getRuleContext(Float_resultContext.class, 0);
        }

        public String_resultContext string_result() {
            return getRuleContext(String_resultContext.class, 0);
        }

        public TerminalNode PLUS() {
            return getToken(RubyParser.PLUS, 0);
        }

        public TerminalNode MINUS() {
            return getToken(RubyParser.MINUS, 0);
        }

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public Map_resultContext map_result() {
            return getRuleContext(Map_resultContext.class, 0);
        }

        public Dynamic_Context dynamic_() {
            return getRuleContext(Dynamic_Context.class, 0);
        }

        public Dynamic_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_dynamic_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterDynamic_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitDynamic_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitDynamic_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Dynamic_resultContext dynamic_result() throws RecognitionException {
        return dynamic_result(0);
    }

    private Dynamic_resultContext dynamic_result(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Dynamic_resultContext _localctx = new Dynamic_resultContext(_ctx, _parentState);
        Dynamic_resultContext _prevctx = _localctx;
        int _startState = 128;
        enterRecursionRule(_localctx, 128, RULE_dynamic_result, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(870);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 79, _ctx)) {
                    case 1: {
                        setState(844);
                        int_result(0);
                        setState(845);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(846);
                        dynamic_result(14);
                    }
                    break;
                    case 2: {
                        setState(848);
                        float_result(0);
                        setState(849);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(850);
                        dynamic_result(12);
                    }
                    break;
                    case 3: {
                        setState(852);
                        string_result(0);
                        setState(853);
                        ((Dynamic_resultContext) _localctx).op = match(MUL);
                        setState(854);
                        dynamic_result(9);
                    }
                    break;
                    case 4: {
                        setState(856);
                        int_result(0);
                        setState(857);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == PLUS || _la == MINUS)) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(858);
                        dynamic_result(7);
                    }
                    break;
                    case 5: {
                        setState(860);
                        float_result(0);
                        setState(861);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == PLUS || _la == MINUS)) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(862);
                        dynamic_result(5);
                    }
                    break;
                    case 6: {
                        setState(864);
                        match(LEFT_RBRACKET);
                        setState(865);
                        dynamic_result(0);
                        setState(866);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                    case 7: {
                        setState(868);
                        map_result();
                    }
                    break;
                    case 8: {
                        setState(869);
                        dynamic_();
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(895);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 81, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(893);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 80, _ctx)) {
                                case 1: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(872);
                                    if (!(precpred(_ctx, 11)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                                    setState(873);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(874);
                                    dynamic_result(12);
                                }
                                break;
                                case 2: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(875);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(876);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(877);
                                    dynamic_result(5);
                                }
                                break;
                                case 3: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(878);
                                    if (!(precpred(_ctx, 15)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 15)");
                                    setState(879);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(880);
                                    int_result(0);
                                }
                                break;
                                case 4: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(881);
                                    if (!(precpred(_ctx, 13)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 13)");
                                    setState(882);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(883);
                                    float_result(0);
                                }
                                break;
                                case 5: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(884);
                                    if (!(precpred(_ctx, 10)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 10)");
                                    setState(885);
                                    ((Dynamic_resultContext) _localctx).op = match(MUL);
                                    setState(886);
                                    string_result(0);
                                }
                                break;
                                case 6: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(887);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(888);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(889);
                                    int_result(0);
                                }
                                break;
                                case 7: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(890);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(891);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(892);
                                    float_result(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(897);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 81, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Map_resultContext extends ParserRuleContext {
        public Hash_expressionContext hash_expression() {
            return getRuleContext(Hash_expressionContext.class, 0);
        }

        public Map_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_map_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterMap_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitMap_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitMap_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Map_resultContext map_result() throws RecognitionException {
        Map_resultContext _localctx = new Map_resultContext(_ctx, getState());
        enterRule(_localctx, 130, RULE_map_result);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(898);
                hash_expression();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Dynamic_Context extends ParserRuleContext {
        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public Function_call_assignmentContext function_call_assignment() {
            return getRuleContext(Function_call_assignmentContext.class, 0);
        }

        public Array_selectorContext array_selector() {
            return getRuleContext(Array_selectorContext.class, 0);
        }

        public Dynamic_Context(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_dynamic_;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterDynamic_(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitDynamic_(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitDynamic_(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Dynamic_Context dynamic_() throws RecognitionException {
        Dynamic_Context _localctx = new Dynamic_Context(_ctx, getState());
        enterRule(_localctx, 132, RULE_dynamic_);
        try {
            setState(903);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 82, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(900);
                    id_();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(901);
                    function_call_assignment();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(902);
                    array_selector();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Int_resultContext extends ParserRuleContext {
        public Token op;

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public List<Int_resultContext> int_result() {
            return getRuleContexts(Int_resultContext.class);
        }

        public Int_resultContext int_result(int i) {
            return getRuleContext(Int_resultContext.class, i);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public Int_tContext int_t() {
            return getRuleContext(Int_tContext.class, 0);
        }

        public TerminalNode MUL() {
            return getToken(RubyParser.MUL, 0);
        }

        public TerminalNode DIV() {
            return getToken(RubyParser.DIV, 0);
        }

        public TerminalNode MOD() {
            return getToken(RubyParser.MOD, 0);
        }

        public TerminalNode PLUS() {
            return getToken(RubyParser.PLUS, 0);
        }

        public TerminalNode MINUS() {
            return getToken(RubyParser.MINUS, 0);
        }

        public Int_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_int_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInt_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInt_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInt_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Int_resultContext int_result() throws RecognitionException {
        return int_result(0);
    }

    private Int_resultContext int_result(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Int_resultContext _localctx = new Int_resultContext(_ctx, _parentState);
        Int_resultContext _prevctx = _localctx;
        int _startState = 134;
        enterRecursionRule(_localctx, 134, RULE_int_result, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(911);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LEFT_RBRACKET: {
                        setState(906);
                        match(LEFT_RBRACKET);
                        setState(907);
                        int_result(0);
                        setState(908);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                    case INT: {
                        setState(910);
                        int_t();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(921);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 85, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(919);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 84, _ctx)) {
                                case 1: {
                                    _localctx = new Int_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_int_result);
                                    setState(913);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(914);
                                    ((Int_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        ((Int_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(915);
                                    int_result(5);
                                }
                                break;
                                case 2: {
                                    _localctx = new Int_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_int_result);
                                    setState(916);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(917);
                                    ((Int_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Int_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(918);
                                    int_result(4);
                                }
                                break;
                            }
                        }
                    }
                    setState(923);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 85, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Float_resultContext extends ParserRuleContext {
        public Token op;

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public List<Float_resultContext> float_result() {
            return getRuleContexts(Float_resultContext.class);
        }

        public Float_resultContext float_result(int i) {
            return getRuleContext(Float_resultContext.class, i);
        }

        public TerminalNode MUL() {
            return getToken(RubyParser.MUL, 0);
        }

        public TerminalNode DIV() {
            return getToken(RubyParser.DIV, 0);
        }

        public TerminalNode MOD() {
            return getToken(RubyParser.MOD, 0);
        }

        public TerminalNode PLUS() {
            return getToken(RubyParser.PLUS, 0);
        }

        public TerminalNode MINUS() {
            return getToken(RubyParser.MINUS, 0);
        }

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public Float_tContext float_t() {
            return getRuleContext(Float_tContext.class, 0);
        }

        public Float_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_float_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFloat_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFloat_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFloat_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Float_resultContext float_result() throws RecognitionException {
        return float_result(0);
    }

    private Float_resultContext float_result(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        Float_resultContext _localctx = new Float_resultContext(_ctx, _parentState);
        Float_resultContext _prevctx = _localctx;
        int _startState = 136;
        enterRecursionRule(_localctx, 136, RULE_float_result, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(938);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 86, _ctx)) {
                    case 1: {
                        setState(925);
                        int_result(0);
                        setState(926);
                        ((Float_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                            ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(927);
                        float_result(7);
                    }
                    break;
                    case 2: {
                        setState(929);
                        int_result(0);
                        setState(930);
                        ((Float_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == PLUS || _la == MINUS)) {
                            ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(931);
                        float_result(4);
                    }
                    break;
                    case 3: {
                        setState(933);
                        match(LEFT_RBRACKET);
                        setState(934);
                        float_result(0);
                        setState(935);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                    case 4: {
                        setState(937);
                        float_t();
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(954);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 88, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(952);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 87, _ctx)) {
                                case 1: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(940);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(941);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(942);
                                    float_result(9);
                                }
                                break;
                                case 2: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(943);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(944);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(945);
                                    float_result(6);
                                }
                                break;
                                case 3: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(946);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(947);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(948);
                                    int_result(0);
                                }
                                break;
                                case 4: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(949);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(950);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(951);
                                    int_result(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(956);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 88, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class String_resultContext extends ParserRuleContext {
        public Token op;

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public List<String_resultContext> string_result() {
            return getRuleContexts(String_resultContext.class);
        }

        public String_resultContext string_result(int i) {
            return getRuleContext(String_resultContext.class, i);
        }

        public TerminalNode MUL() {
            return getToken(RubyParser.MUL, 0);
        }

        public Literal_tContext literal_t() {
            return getRuleContext(Literal_tContext.class, 0);
        }

        public TerminalNode PLUS() {
            return getToken(RubyParser.PLUS, 0);
        }

        public String_resultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_string_result;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterString_result(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitString_result(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitString_result(this);
            else return visitor.visitChildren(this);
        }
    }

    public final String_resultContext string_result() throws RecognitionException {
        return string_result(0);
    }

    private String_resultContext string_result(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        String_resultContext _localctx = new String_resultContext(_ctx, _parentState);
        String_resultContext _prevctx = _localctx;
        int _startState = 138;
        enterRecursionRule(_localctx, 138, RULE_string_result, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(963);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LEFT_RBRACKET:
                    case INT: {
                        setState(958);
                        int_result(0);
                        setState(959);
                        ((String_resultContext) _localctx).op = match(MUL);
                        setState(960);
                        string_result(3);
                    }
                    break;
                    case LITERAL: {
                        setState(962);
                        literal_t();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(973);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 91, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(971);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 90, _ctx)) {
                                case 1: {
                                    _localctx = new String_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_string_result);
                                    setState(965);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(966);
                                    ((String_resultContext) _localctx).op = match(PLUS);
                                    setState(967);
                                    string_result(3);
                                }
                                break;
                                case 2: {
                                    _localctx = new String_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_string_result);
                                    setState(968);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(969);
                                    ((String_resultContext) _localctx).op = match(MUL);
                                    setState(970);
                                    int_result(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(975);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 91, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Comparison_listContext extends ParserRuleContext {
        public ComparisonContext left;
        public Token op;
        public Comparison_listContext right;

        public ComparisonContext comparison() {
            return getRuleContext(ComparisonContext.class, 0);
        }

        public TerminalNode BIT_AND() {
            return getToken(RubyParser.BIT_AND, 0);
        }

        public Comparison_listContext comparison_list() {
            return getRuleContext(Comparison_listContext.class, 0);
        }

        public TerminalNode AND() {
            return getToken(RubyParser.AND, 0);
        }

        public TerminalNode BIT_OR() {
            return getToken(RubyParser.BIT_OR, 0);
        }

        public TerminalNode OR() {
            return getToken(RubyParser.OR, 0);
        }

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public Comparison_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparison_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterComparison_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitComparison_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitComparison_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Comparison_listContext comparison_list() throws RecognitionException {
        Comparison_listContext _localctx = new Comparison_listContext(_ctx, getState());
        enterRule(_localctx, 140, RULE_comparison_list);
        try {
            setState(997);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 92, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(976);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(977);
                    ((Comparison_listContext) _localctx).op = match(BIT_AND);
                    setState(978);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(980);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(981);
                    ((Comparison_listContext) _localctx).op = match(AND);
                    setState(982);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(984);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(985);
                    ((Comparison_listContext) _localctx).op = match(BIT_OR);
                    setState(986);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(988);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(989);
                    ((Comparison_listContext) _localctx).op = match(OR);
                    setState(990);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(992);
                    match(LEFT_RBRACKET);
                    setState(993);
                    comparison_list();
                    setState(994);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(996);
                    comparison();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ComparisonContext extends ParserRuleContext {
        public Comp_varContext left;
        public Token op;
        public Comp_varContext right;

        public List<Comp_varContext> comp_var() {
            return getRuleContexts(Comp_varContext.class);
        }

        public Comp_varContext comp_var(int i) {
            return getRuleContext(Comp_varContext.class, i);
        }

        public TerminalNode LESS() {
            return getToken(RubyParser.LESS, 0);
        }

        public TerminalNode GREATER() {
            return getToken(RubyParser.GREATER, 0);
        }

        public TerminalNode LESS_EQUAL() {
            return getToken(RubyParser.LESS_EQUAL, 0);
        }

        public TerminalNode GREATER_EQUAL() {
            return getToken(RubyParser.GREATER_EQUAL, 0);
        }

        public TerminalNode EQUAL() {
            return getToken(RubyParser.EQUAL, 0);
        }

        public TerminalNode NOT_EQUAL() {
            return getToken(RubyParser.NOT_EQUAL, 0);
        }

        public ComparisonContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparison;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterComparison(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitComparison(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitComparison(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ComparisonContext comparison() throws RecognitionException {
        ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
        enterRule(_localctx, 142, RULE_comparison);
        int _la;
        try {
            setState(1007);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 93, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(999);
                    ((ComparisonContext) _localctx).left = comp_var();
                    setState(1000);
                    ((ComparisonContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 131941395333120L) != 0))) {
                        ((ComparisonContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(1001);
                    ((ComparisonContext) _localctx).right = comp_var();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(1003);
                    ((ComparisonContext) _localctx).left = comp_var();
                    setState(1004);
                    ((ComparisonContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!(_la == EQUAL || _la == NOT_EQUAL)) {
                        ((ComparisonContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(1005);
                    ((ComparisonContext) _localctx).right = comp_var();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Comp_varContext extends ParserRuleContext {
        public All_resultContext all_result() {
            return getRuleContext(All_resultContext.class, 0);
        }

        public Array_selectorContext array_selector() {
            return getRuleContext(Array_selectorContext.class, 0);
        }

        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public Id_constenceContext id_constence() {
            return getRuleContext(Id_constenceContext.class, 0);
        }

        public Comp_varContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comp_var;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterComp_var(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitComp_var(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitComp_var(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Comp_varContext comp_var() throws RecognitionException {
        Comp_varContext _localctx = new Comp_varContext(_ctx, getState());
        enterRule(_localctx, 144, RULE_comp_var);
        try {
            setState(1013);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 94, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(1009);
                    all_result();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(1010);
                    array_selector();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(1011);
                    id_();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(1012);
                    id_constence();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LvalueContext extends ParserRuleContext {
        public Id_constenceContext id_constence() {
            return getRuleContext(Id_constenceContext.class, 0);
        }

        public Id_globalContext id_global() {
            return getRuleContext(Id_globalContext.class, 0);
        }

        public Id_Context id_() {
            return getRuleContext(Id_Context.class, 0);
        }

        public LvalueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_lvalue;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterLvalue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitLvalue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitLvalue(this);
            else return visitor.visitChildren(this);
        }
    }

    public final LvalueContext lvalue() throws RecognitionException {
        LvalueContext _localctx = new LvalueContext(_ctx, getState());
        enterRule(_localctx, 146, RULE_lvalue);
        try {
            setState(1018);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case CONST_ID:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(1015);
                    id_constence();
                }
                break;
                case ID_GLOBAL:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(1016);
                    id_global();
                }
                break;
                case ID:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(1017);
                    id_();
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class RvalueContext extends ParserRuleContext {
        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public Initial_array_assignmentContext initial_array_assignment() {
            return getRuleContext(Initial_array_assignmentContext.class, 0);
        }

        public Array_assignmentContext array_assignment() {
            return getRuleContext(Array_assignmentContext.class, 0);
        }

        public Int_resultContext int_result() {
            return getRuleContext(Int_resultContext.class, 0);
        }

        public Float_resultContext float_result() {
            return getRuleContext(Float_resultContext.class, 0);
        }

        public String_resultContext string_result() {
            return getRuleContext(String_resultContext.class, 0);
        }

        public Global_setContext global_set() {
            return getRuleContext(Global_setContext.class, 0);
        }

        public Global_getContext global_get() {
            return getRuleContext(Global_getContext.class, 0);
        }

        public Instance_setContext instance_set() {
            return getRuleContext(Instance_setContext.class, 0);
        }

        public Instance_getContext instance_get() {
            return getRuleContext(Instance_getContext.class, 0);
        }

        public Dynamic_assignmentContext dynamic_assignment() {
            return getRuleContext(Dynamic_assignmentContext.class, 0);
        }

        public String_assignmentContext string_assignment() {
            return getRuleContext(String_assignmentContext.class, 0);
        }

        public Float_assignmentContext float_assignment() {
            return getRuleContext(Float_assignmentContext.class, 0);
        }

        public Int_assignmentContext int_assignment() {
            return getRuleContext(Int_assignmentContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public Function_callContext function_call() {
            return getRuleContext(Function_callContext.class, 0);
        }

        public Literal_tContext literal_t() {
            return getRuleContext(Literal_tContext.class, 0);
        }

        public Bool_tContext bool_t() {
            return getRuleContext(Bool_tContext.class, 0);
        }

        public Float_tContext float_t() {
            return getRuleContext(Float_tContext.class, 0);
        }

        public Int_tContext int_t() {
            return getRuleContext(Int_tContext.class, 0);
        }

        public Nil_tContext nil_t() {
            return getRuleContext(Nil_tContext.class, 0);
        }

        public List<RvalueContext> rvalue() {
            return getRuleContexts(RvalueContext.class);
        }

        public RvalueContext rvalue(int i) {
            return getRuleContext(RvalueContext.class, i);
        }

        public TerminalNode NOT() {
            return getToken(RubyParser.NOT, 0);
        }

        public TerminalNode BIT_NOT() {
            return getToken(RubyParser.BIT_NOT, 0);
        }

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
        }

        public TerminalNode EXP() {
            return getToken(RubyParser.EXP, 0);
        }

        public TerminalNode MUL() {
            return getToken(RubyParser.MUL, 0);
        }

        public TerminalNode DIV() {
            return getToken(RubyParser.DIV, 0);
        }

        public TerminalNode MOD() {
            return getToken(RubyParser.MOD, 0);
        }

        public TerminalNode PLUS() {
            return getToken(RubyParser.PLUS, 0);
        }

        public TerminalNode MINUS() {
            return getToken(RubyParser.MINUS, 0);
        }

        public TerminalNode BIT_SHL() {
            return getToken(RubyParser.BIT_SHL, 0);
        }

        public TerminalNode BIT_SHR() {
            return getToken(RubyParser.BIT_SHR, 0);
        }

        public TerminalNode BIT_AND() {
            return getToken(RubyParser.BIT_AND, 0);
        }

        public TerminalNode BIT_OR() {
            return getToken(RubyParser.BIT_OR, 0);
        }

        public TerminalNode BIT_XOR() {
            return getToken(RubyParser.BIT_XOR, 0);
        }

        public TerminalNode LESS() {
            return getToken(RubyParser.LESS, 0);
        }

        public TerminalNode GREATER() {
            return getToken(RubyParser.GREATER, 0);
        }

        public TerminalNode LESS_EQUAL() {
            return getToken(RubyParser.LESS_EQUAL, 0);
        }

        public TerminalNode GREATER_EQUAL() {
            return getToken(RubyParser.GREATER_EQUAL, 0);
        }

        public TerminalNode EQUAL() {
            return getToken(RubyParser.EQUAL, 0);
        }

        public TerminalNode NOT_EQUAL() {
            return getToken(RubyParser.NOT_EQUAL, 0);
        }

        public TerminalNode OR() {
            return getToken(RubyParser.OR, 0);
        }

        public TerminalNode AND() {
            return getToken(RubyParser.AND, 0);
        }

        public RvalueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_rvalue;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterRvalue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitRvalue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitRvalue(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RvalueContext rvalue() throws RecognitionException {
        return rvalue(0);
    }

    private RvalueContext rvalue(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        RvalueContext _localctx = new RvalueContext(_ctx, _parentState);
        RvalueContext _prevctx = _localctx;
        int _startState = 148;
        enterRecursionRule(_localctx, 148, RULE_rvalue, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(1048);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 96, _ctx)) {
                    case 1: {
                        setState(1021);
                        lvalue();
                    }
                    break;
                    case 2: {
                        setState(1022);
                        initial_array_assignment();
                    }
                    break;
                    case 3: {
                        setState(1023);
                        array_assignment();
                    }
                    break;
                    case 4: {
                        setState(1024);
                        int_result(0);
                    }
                    break;
                    case 5: {
                        setState(1025);
                        float_result(0);
                    }
                    break;
                    case 6: {
                        setState(1026);
                        string_result(0);
                    }
                    break;
                    case 7: {
                        setState(1027);
                        global_set();
                    }
                    break;
                    case 8: {
                        setState(1028);
                        global_get();
                    }
                    break;
                    case 9: {
                        setState(1029);
                        instance_set();
                    }
                    break;
                    case 10: {
                        setState(1030);
                        instance_get();
                    }
                    break;
                    case 11: {
                        setState(1031);
                        dynamic_assignment();
                    }
                    break;
                    case 12: {
                        setState(1032);
                        string_assignment();
                    }
                    break;
                    case 13: {
                        setState(1033);
                        float_assignment();
                    }
                    break;
                    case 14: {
                        setState(1034);
                        int_assignment();
                    }
                    break;
                    case 15: {
                        setState(1035);
                        assignment();
                    }
                    break;
                    case 16: {
                        setState(1036);
                        function_call();
                    }
                    break;
                    case 17: {
                        setState(1037);
                        literal_t();
                    }
                    break;
                    case 18: {
                        setState(1038);
                        bool_t();
                    }
                    break;
                    case 19: {
                        setState(1039);
                        float_t();
                    }
                    break;
                    case 20: {
                        setState(1040);
                        int_t();
                    }
                    break;
                    case 21: {
                        setState(1041);
                        nil_t();
                    }
                    break;
                    case 22: {
                        setState(1042);
                        _la = _input.LA(1);
                        if (!(_la == BIT_NOT || _la == NOT)) {
                            _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(1043);
                        rvalue(10);
                    }
                    break;
                    case 23: {
                        setState(1044);
                        match(LEFT_RBRACKET);
                        setState(1045);
                        rvalue(0);
                        setState(1046);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(1079);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 98, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(1077);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 97, _ctx)) {
                                case 1: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1050);
                                    if (!(precpred(_ctx, 11)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                                    setState(1051);
                                    match(EXP);
                                    setState(1052);
                                    rvalue(12);
                                }
                                break;
                                case 2: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1053);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(1054);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0))) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1055);
                                    rvalue(10);
                                }
                                break;
                                case 3: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1056);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(1057);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1058);
                                    rvalue(9);
                                }
                                break;
                                case 4: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1059);
                                    if (!(precpred(_ctx, 7)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    setState(1060);
                                    _la = _input.LA(1);
                                    if (!(_la == BIT_SHL || _la == BIT_SHR)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1061);
                                    rvalue(8);
                                }
                                break;
                                case 5: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1062);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(1063);
                                    match(BIT_AND);
                                    setState(1064);
                                    rvalue(7);
                                }
                                break;
                                case 6: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1065);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(1066);
                                    _la = _input.LA(1);
                                    if (!(_la == BIT_OR || _la == BIT_XOR)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1067);
                                    rvalue(6);
                                }
                                break;
                                case 7: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1068);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(1069);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 131941395333120L) != 0))) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1070);
                                    rvalue(5);
                                }
                                break;
                                case 8: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1071);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(1072);
                                    _la = _input.LA(1);
                                    if (!(_la == EQUAL || _la == NOT_EQUAL)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1073);
                                    rvalue(4);
                                }
                                break;
                                case 9: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(1074);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(1075);
                                    _la = _input.LA(1);
                                    if (!(_la == AND || _la == OR)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(1076);
                                    rvalue(3);
                                }
                                break;
                            }
                        }
                    }
                    setState(1081);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 98, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Break_expressionContext extends ParserRuleContext {
        public TerminalNode BREAK() {
            return getToken(RubyParser.BREAK, 0);
        }

        public Break_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_break_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterBreak_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitBreak_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitBreak_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Break_expressionContext break_expression() throws RecognitionException {
        Break_expressionContext _localctx = new Break_expressionContext(_ctx, getState());
        enterRule(_localctx, 150, RULE_break_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1082);
                match(BREAK);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Raise_expressionContext extends ParserRuleContext {
        public TerminalNode RAISE() {
            return getToken(RubyParser.RAISE, 0);
        }

        public LvalueContext lvalue() {
            return getRuleContext(LvalueContext.class, 0);
        }

        public Raise_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_raise_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterRaise_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitRaise_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitRaise_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Raise_expressionContext raise_expression() throws RecognitionException {
        Raise_expressionContext _localctx = new Raise_expressionContext(_ctx, getState());
        enterRule(_localctx, 152, RULE_raise_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1084);
                match(RAISE);
                setState(1085);
                lvalue();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Yield_expressionContext extends ParserRuleContext {
        public TerminalNode YIELD() {
            return getToken(RubyParser.YIELD, 0);
        }

        public Yield_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_yield_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterYield_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitYield_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitYield_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Yield_expressionContext yield_expression() throws RecognitionException {
        Yield_expressionContext _localctx = new Yield_expressionContext(_ctx, getState());
        enterRule(_localctx, 154, RULE_yield_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1087);
                match(YIELD);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Literal_tContext extends ParserRuleContext {
        public TerminalNode LITERAL() {
            return getToken(RubyParser.LITERAL, 0);
        }

        public Literal_tContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_literal_t;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterLiteral_t(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitLiteral_t(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitLiteral_t(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Literal_tContext literal_t() throws RecognitionException {
        Literal_tContext _localctx = new Literal_tContext(_ctx, getState());
        enterRule(_localctx, 156, RULE_literal_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1089);
                match(LITERAL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Float_tContext extends ParserRuleContext {
        public TerminalNode FLOAT() {
            return getToken(RubyParser.FLOAT, 0);
        }

        public Float_tContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_float_t;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFloat_t(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFloat_t(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFloat_t(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Float_tContext float_t() throws RecognitionException {
        Float_tContext _localctx = new Float_tContext(_ctx, getState());
        enterRule(_localctx, 158, RULE_float_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1091);
                match(FLOAT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Int_tContext extends ParserRuleContext {
        public TerminalNode INT() {
            return getToken(RubyParser.INT, 0);
        }

        public Int_tContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_int_t;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInt_t(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInt_t(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInt_t(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Int_tContext int_t() throws RecognitionException {
        Int_tContext _localctx = new Int_tContext(_ctx, getState());
        enterRule(_localctx, 160, RULE_int_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1093);
                match(INT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Bool_tContext extends ParserRuleContext {
        public TerminalNode TRUE() {
            return getToken(RubyParser.TRUE, 0);
        }

        public TerminalNode FALSE() {
            return getToken(RubyParser.FALSE, 0);
        }

        public Bool_tContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_bool_t;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterBool_t(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitBool_t(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitBool_t(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Bool_tContext bool_t() throws RecognitionException {
        Bool_tContext _localctx = new Bool_tContext(_ctx, getState());
        enterRule(_localctx, 162, RULE_bool_t);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1095);
                _la = _input.LA(1);
                if (!(_la == TRUE || _la == FALSE)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Nil_tContext extends ParserRuleContext {
        public TerminalNode NIL() {
            return getToken(RubyParser.NIL, 0);
        }

        public Nil_tContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nil_t;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterNil_t(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitNil_t(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitNil_t(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Nil_tContext nil_t() throws RecognitionException {
        Nil_tContext _localctx = new Nil_tContext(_ctx, getState());
        enterRule(_localctx, 164, RULE_nil_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1097);
                match(NIL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Id_Context extends ParserRuleContext {
        public TerminalNode ID() {
            return getToken(RubyParser.ID, 0);
        }

        public Id_Context(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_id_;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterId_(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitId_(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitId_(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Id_Context id_() throws RecognitionException {
        Id_Context _localctx = new Id_Context(_ctx, getState());
        enterRule(_localctx, 166, RULE_id_);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1099);
                match(ID);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Id_globalContext extends ParserRuleContext {
        public TerminalNode ID_GLOBAL() {
            return getToken(RubyParser.ID_GLOBAL, 0);
        }

        public Id_globalContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_id_global;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterId_global(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitId_global(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitId_global(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Id_globalContext id_global() throws RecognitionException {
        Id_globalContext _localctx = new Id_globalContext(_ctx, getState());
        enterRule(_localctx, 168, RULE_id_global);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1101);
                match(ID_GLOBAL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Id_instanceContext extends ParserRuleContext {
        public TerminalNode ID_INSTANCE() {
            return getToken(RubyParser.ID_INSTANCE, 0);
        }

        public Id_instanceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_id_instance;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterId_instance(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitId_instance(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitId_instance(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Id_instanceContext id_instance() throws RecognitionException {
        Id_instanceContext _localctx = new Id_instanceContext(_ctx, getState());
        enterRule(_localctx, 170, RULE_id_instance);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1103);
                match(ID_INSTANCE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Id_constenceContext extends ParserRuleContext {
        public TerminalNode CONST_ID() {
            return getToken(RubyParser.CONST_ID, 0);
        }

        public Id_constenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_id_constence;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterId_constence(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitId_constence(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitId_constence(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Id_constenceContext id_constence() throws RecognitionException {
        Id_constenceContext _localctx = new Id_constenceContext(_ctx, getState());
        enterRule(_localctx, 172, RULE_id_constence);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1105);
                match(CONST_ID);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Id_functionContext extends ParserRuleContext {
        public TerminalNode ID_FUNCTION() {
            return getToken(RubyParser.ID_FUNCTION, 0);
        }

        public Id_functionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_id_function;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterId_function(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitId_function(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitId_function(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Id_functionContext id_function() throws RecognitionException {
        Id_functionContext _localctx = new Id_functionContext(_ctx, getState());
        enterRule(_localctx, 174, RULE_id_function);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1107);
                match(ID_FUNCTION);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class TerminatorContext extends ParserRuleContext {
        public TerminalNode SEMICOLON() {
            return getToken(RubyParser.SEMICOLON, 0);
        }

        public CrlfContext crlf() {
            return getRuleContext(CrlfContext.class, 0);
        }

        public TerminatorContext terminator() {
            return getRuleContext(TerminatorContext.class, 0);
        }

        public TerminatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_terminator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterTerminator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitTerminator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitTerminator(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TerminatorContext terminator() throws RecognitionException {
        return terminator(0);
    }

    private TerminatorContext terminator(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        TerminatorContext _localctx = new TerminatorContext(_ctx, _parentState);
        TerminatorContext _prevctx = _localctx;
        int _startState = 176;
        enterRecursionRule(_localctx, 176, RULE_terminator, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(1112);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SEMICOLON: {
                        setState(1110);
                        match(SEMICOLON);
                    }
                    break;
                    case CRLF: {
                        setState(1111);
                        crlf();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(1120);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 101, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(1118);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 100, _ctx)) {
                                case 1: {
                                    _localctx = new TerminatorContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_terminator);
                                    setState(1114);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(1115);
                                    match(SEMICOLON);
                                }
                                break;
                                case 2: {
                                    _localctx = new TerminatorContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_terminator);
                                    setState(1116);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(1117);
                                    crlf();
                                }
                                break;
                            }
                        }
                    }
                    setState(1122);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 101, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class Else_tokenContext extends ParserRuleContext {
        public TerminalNode ELSE() {
            return getToken(RubyParser.ELSE, 0);
        }

        public Else_tokenContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_else_token;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterElse_token(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitElse_token(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitElse_token(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Else_tokenContext else_token() throws RecognitionException {
        Else_tokenContext _localctx = new Else_tokenContext(_ctx, getState());
        enterRule(_localctx, 178, RULE_else_token);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1123);
                match(ELSE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class CrlfContext extends ParserRuleContext {
        public TerminalNode CRLF() {
            return getToken(RubyParser.CRLF, 0);
        }

        public CrlfContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_crlf;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterCrlf(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitCrlf(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitCrlf(this);
            else return visitor.visitChildren(this);
        }
    }

    public final CrlfContext crlf() throws RecognitionException {
        CrlfContext _localctx = new CrlfContext(_ctx, getState());
        enterRule(_localctx, 180, RULE_crlf);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(1125);
                match(CRLF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 1:
                return expression_list_sempred((Expression_listContext) _localctx, predIndex);
            case 30:
                return function_definition_params_list_sempred((Function_definition_params_listContext) _localctx, predIndex);
            case 35:
                return function_call_params_sempred((Function_call_paramsContext) _localctx, predIndex);
            case 53:
                return statement_expression_list_sempred((Statement_expression_listContext) _localctx, predIndex);
            case 62:
                return array_definition_elements_sempred((Array_definition_elementsContext) _localctx, predIndex);
            case 64:
                return dynamic_result_sempred((Dynamic_resultContext) _localctx, predIndex);
            case 67:
                return int_result_sempred((Int_resultContext) _localctx, predIndex);
            case 68:
                return float_result_sempred((Float_resultContext) _localctx, predIndex);
            case 69:
                return string_result_sempred((String_resultContext) _localctx, predIndex);
            case 74:
                return rvalue_sempred((RvalueContext) _localctx, predIndex);
            case 88:
                return terminator_sempred((TerminatorContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean expression_list_sempred(Expression_listContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean function_definition_params_list_sempred(Function_definition_params_listContext _localctx, int predIndex) {
        switch (predIndex) {
            case 1:
                return precpred(_ctx, 1);
        }
        return true;
    }

    private boolean function_call_params_sempred(Function_call_paramsContext _localctx, int predIndex) {
        switch (predIndex) {
            case 2:
                return precpred(_ctx, 1);
        }
        return true;
    }

    private boolean statement_expression_list_sempred(Statement_expression_listContext _localctx, int predIndex) {
        switch (predIndex) {
            case 3:
                return precpred(_ctx, 3);
            case 4:
                return precpred(_ctx, 2);
            case 5:
                return precpred(_ctx, 1);
        }
        return true;
    }

    private boolean array_definition_elements_sempred(Array_definition_elementsContext _localctx, int predIndex) {
        switch (predIndex) {
            case 6:
                return precpred(_ctx, 2);
            case 7:
                return precpred(_ctx, 1);
        }
        return true;
    }

    private boolean dynamic_result_sempred(Dynamic_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 8:
                return precpred(_ctx, 11);
            case 9:
                return precpred(_ctx, 4);
            case 10:
                return precpred(_ctx, 15);
            case 11:
                return precpred(_ctx, 13);
            case 12:
                return precpred(_ctx, 10);
            case 13:
                return precpred(_ctx, 8);
            case 14:
                return precpred(_ctx, 6);
        }
        return true;
    }

    private boolean int_result_sempred(Int_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 15:
                return precpred(_ctx, 4);
            case 16:
                return precpred(_ctx, 3);
        }
        return true;
    }

    private boolean float_result_sempred(Float_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 17:
                return precpred(_ctx, 8);
            case 18:
                return precpred(_ctx, 5);
            case 19:
                return precpred(_ctx, 6);
            case 20:
                return precpred(_ctx, 3);
        }
        return true;
    }

    private boolean string_result_sempred(String_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 21:
                return precpred(_ctx, 2);
            case 22:
                return precpred(_ctx, 4);
        }
        return true;
    }

    private boolean rvalue_sempred(RvalueContext _localctx, int predIndex) {
        switch (predIndex) {
            case 23:
                return precpred(_ctx, 11);
            case 24:
                return precpred(_ctx, 9);
            case 25:
                return precpred(_ctx, 8);
            case 26:
                return precpred(_ctx, 7);
            case 27:
                return precpred(_ctx, 6);
            case 28:
                return precpred(_ctx, 5);
            case 29:
                return precpred(_ctx, 4);
            case 30:
                return precpred(_ctx, 3);
            case 31:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean terminator_sempred(TerminatorContext _localctx, int predIndex) {
        switch (predIndex) {
            case 32:
                return precpred(_ctx, 4);
            case 33:
                return precpred(_ctx, 3);
        }
        return true;
    }

    public static final String _serializedATN =
            "\u0004\u0001Q\u0468\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002" +
                    "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002" +
                    "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002" +
                    "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002" +
                    "\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f" +
                    "\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012" +
                    "\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015" +
                    "\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018" +
                    "\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b" +
                    "\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e" +
                    "\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002" +
                    "#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002" +
                    "(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002" +
                    "-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002" +
                    "2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002" +
                    "7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007;\u0002" +
                    "<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007?\u0002@\u0007@\u0002" +
                    "A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007D\u0002E\u0007E\u0002" +
                    "F\u0007F\u0002G\u0007G\u0002H\u0007H\u0002I\u0007I\u0002J\u0007J\u0002" +
                    "K\u0007K\u0002L\u0007L\u0002M\u0007M\u0002N\u0007N\u0002O\u0007O\u0002" +
                    "P\u0007P\u0002Q\u0007Q\u0002R\u0007R\u0002S\u0007S\u0002T\u0007T\u0002" +
                    "U\u0007U\u0002V\u0007V\u0002W\u0007W\u0002X\u0007X\u0002Y\u0007Y\u0002" +
                    "Z\u0007Z\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u00bf\b\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0005\u0001\u00c5\b\u0001\n\u0001\f\u0001" +
                    "\u00c8\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002" +
                    "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002" +
                    "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002" +
                    "\u0001\u0002\u0003\u0002\u00dc\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003" +
                    "\u0005\u0003\u00e1\b\u0003\n\u0003\f\u0003\u00e4\t\u0003\u0001\u0003\u0001" +
                    "\u0003\u0001\u0003\u0005\u0003\u00e9\b\u0003\n\u0003\f\u0003\u00ec\t\u0003" +
                    "\u0001\u0003\u0001\u0003\u0005\u0003\u00f0\b\u0003\n\u0003\f\u0003\u00f3" +
                    "\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u00f9" +
                    "\b\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001" +
                    "\u0005\u0003\u0005\u0101\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0005" +
                    "\u0006\u0106\b\u0006\n\u0006\f\u0006\u0109\t\u0006\u0001\u0006\u0001\u0006" +
                    "\u0001\u0006\u0005\u0006\u010e\b\u0006\n\u0006\f\u0006\u0111\t\u0006\u0001" +
                    "\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0116\b\u0007\n\u0007\f\u0007" +
                    "\u0119\t\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u011e\b" +
                    "\u0007\n\u0007\f\u0007\u0121\t\u0007\u0001\b\u0001\b\u0005\b\u0125\b\b" +
                    "\n\b\f\b\u0128\t\b\u0001\b\u0001\b\u0001\b\u0003\b\u012d\b\b\u0001\b\u0005" +
                    "\b\u0130\b\b\n\b\f\b\u0133\t\b\u0001\b\u0001\b\u0005\b\u0137\b\b\n\b\f" +
                    "\b\u013a\t\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0140\b\b\u0001\b" +
                    "\u0001\b\u0005\b\u0144\b\b\n\b\f\b\u0147\t\b\u0001\b\u0001\b\u0003\b\u014b" +
                    "\b\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001" +
                    "\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r" +
                    "\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f" +
                    "\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011" +
                    "\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013" +
                    "\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015" +
                    "\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017" +
                    "\u0001\u0017\u0001\u0017\u0003\u0017\u017d\b\u0017\u0001\u0017\u0005\u0017" +
                    "\u0180\b\u0017\n\u0017\f\u0017\u0183\t\u0017\u0001\u0017\u0003\u0017\u0186" +
                    "\b\u0017\u0001\u0017\u0005\u0017\u0189\b\u0017\n\u0017\f\u0017\u018c\t" +
                    "\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001" +
                    "\u0018\u0003\u0018\u0194\b\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001" +
                    "\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001" +
                    "\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001" +
                    "\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u01a8\b\u001b\u0001\u001c\u0001" +
                    "\u001c\u0003\u001c\u01ac\b\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001" +
                    "\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u01b5\b\u001d\u0001" +
                    "\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0005" +
                    "\u001e\u01bd\b\u001e\n\u001e\f\u001e\u01c0\t\u001e\u0001\u001f\u0001\u001f" +
                    "\u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001" +
                    "!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001" +
                    "!\u0003!\u01d8\b!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0003!\u01e0" +
                    "\b!\u0001!\u0001!\u0001!\u0001!\u0001!\u0003!\u01e7\b!\u0001!\u0001!\u0001" +
                    "!\u0001!\u0001!\u0001!\u0003!\u01ef\b!\u0003!\u01f1\b!\u0001\"\u0001\"" +
                    "\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0005#\u01fb\b#\n#\f#\u01fe" +
                    "\t#\u0001$\u0001$\u0003$\u0202\b$\u0001%\u0001%\u0001%\u0001%\u0003%\u0208" +
                    "\b%\u0001&\u0001&\u0001&\u0001&\u0001&\u0001&\u0003&\u0210\b&\u0001\'" +
                    "\u0001\'\u0001(\u0001(\u0001(\u0001(\u0001(\u0001(\u0003(\u021a\b(\u0001" +
                    ")\u0001)\u0001*\u0001*\u0001*\u0003*\u0221\b*\u0001*\u0001*\u0001*\u0001" +
                    "*\u0001*\u0001*\u0003*\u0229\b*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001" +
                    "*\u0001*\u0001*\u0001*\u0003*\u0234\b*\u0001*\u0001*\u0001*\u0001*\u0003" +
                    "*\u023a\b*\u0001+\u0001+\u0001+\u0003+\u023f\b+\u0001+\u0001+\u0001+\u0001" +
                    "+\u0001+\u0001+\u0001+\u0003+\u0248\b+\u0001+\u0001+\u0001+\u0001+\u0001" +
                    "+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003+\u0254\b+\u0001+\u0001+\u0001" +
                    "+\u0001+\u0001+\u0003+\u025b\b+\u0001,\u0001,\u0001,\u0001,\u0001,\u0001" +
                    ",\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001" +
                    ",\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0003,\u0273\b,\u0001-\u0001" +
                    "-\u0001-\u0001-\u0001-\u0001-\u0001.\u0001.\u0001.\u0001.\u0003.\u027f" +
                    "\b.\u0001/\u0001/\u0001/\u0001/\u0005/\u0285\b/\n/\f/\u0288\t/\u0001/" +
                    "\u0001/\u0001/\u0003/\u028d\b/\u0001/\u0005/\u0290\b/\n/\f/\u0293\t/\u0001" +
                    "/\u0001/\u0001/\u0001/\u0003/\u0299\b/\u00010\u00010\u00010\u00010\u0001" +
                    "0\u00030\u02a0\b0\u00010\u00010\u00010\u00010\u00030\u02a6\b0\u00010\u0001" +
                    "0\u00010\u00011\u00011\u00012\u00012\u00013\u00013\u00013\u00013\u0001" +
                    "3\u00013\u00014\u00014\u00015\u00015\u00015\u00035\u02ba\b5\u00015\u0001" +
                    "5\u00035\u02be\b5\u00015\u00015\u00035\u02c2\b5\u00015\u00015\u00035\u02c6" +
                    "\b5\u00015\u00015\u00035\u02ca\b5\u00035\u02cc\b5\u00015\u00015\u0001" +
                    "5\u00035\u02d1\b5\u00015\u00015\u00015\u00035\u02d6\b5\u00015\u00015\u0001" +
                    "5\u00035\u02db\b5\u00055\u02dd\b5\n5\f5\u02e0\t5\u00016\u00016\u00016" +
                    "\u00016\u00016\u00016\u00016\u00016\u00036\u02ea\b6\u00017\u00017\u0001" +
                    "7\u00017\u00017\u00017\u00017\u00017\u00037\u02f4\b7\u00018\u00018\u0001" +
                    "8\u00018\u00018\u00018\u00018\u00018\u00038\u02fe\b8\u00019\u00019\u0001" +
                    "9\u00019\u00019\u00019\u00019\u00019\u00039\u0308\b9\u0001:\u0001:\u0001" +
                    ":\u0001:\u0001:\u0001:\u0001:\u0001:\u0003:\u0312\b:\u0001;\u0001;\u0001" +
                    ";\u0001;\u0001;\u0001<\u0001<\u0001<\u0001<\u0001=\u0001=\u0001=\u0001" +
                    "=\u0001=\u0003=\u0322\b=\u0001>\u0001>\u0001>\u0003>\u0327\b>\u0001>\u0001" +
                    ">\u0001>\u0001>\u0003>\u032d\b>\u0001>\u0001>\u0001>\u0001>\u0003>\u0333" +
                    "\b>\u0005>\u0335\b>\n>\f>\u0338\t>\u0001?\u0001?\u0001?\u0001?\u0003?" +
                    "\u033e\b?\u0001?\u0001?\u0001?\u0001?\u0001?\u0001?\u0003?\u0346\b?\u0001" +
                    "?\u0001?\u0003?\u034a\b?\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001" +
                    "@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001" +
                    "@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001" +
                    "@\u0003@\u0367\b@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001" +
                    "@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001" +
                    "@\u0001@\u0001@\u0001@\u0005@\u037e\b@\n@\f@\u0381\t@\u0001A\u0001A\u0001" +
                    "B\u0001B\u0001B\u0003B\u0388\bB\u0001C\u0001C\u0001C\u0001C\u0001C\u0001" +
                    "C\u0003C\u0390\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0005C\u0398" +
                    "\bC\nC\fC\u039b\tC\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001" +
                    "D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0003D\u03ab\bD\u0001D\u0001" +
                    "D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001D\u0001" +
                    "D\u0005D\u03b9\bD\nD\fD\u03bc\tD\u0001E\u0001E\u0001E\u0001E\u0001E\u0001" +
                    "E\u0003E\u03c4\bE\u0001E\u0001E\u0001E\u0001E\u0001E\u0001E\u0005E\u03cc" +
                    "\bE\nE\fE\u03cf\tE\u0001F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001" +
                    "F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001F\u0001" +
                    "F\u0001F\u0001F\u0001F\u0003F\u03e6\bF\u0001G\u0001G\u0001G\u0001G\u0001" +
                    "G\u0001G\u0001G\u0001G\u0003G\u03f0\bG\u0001H\u0001H\u0001H\u0001H\u0003" +
                    "H\u03f6\bH\u0001I\u0001I\u0001I\u0003I\u03fb\bI\u0001J\u0001J\u0001J\u0001" +
                    "J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001" +
                    "J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001" +
                    "J\u0001J\u0001J\u0001J\u0001J\u0003J\u0419\bJ\u0001J\u0001J\u0001J\u0001" +
                    "J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001" +
                    "J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001" +
                    "J\u0001J\u0001J\u0001J\u0005J\u0436\bJ\nJ\fJ\u0439\tJ\u0001K\u0001K\u0001" +
                    "L\u0001L\u0001L\u0001M\u0001M\u0001N\u0001N\u0001O\u0001O\u0001P\u0001" +
                    "P\u0001Q\u0001Q\u0001R\u0001R\u0001S\u0001S\u0001T\u0001T\u0001U\u0001" +
                    "U\u0001V\u0001V\u0001W\u0001W\u0001X\u0001X\u0001X\u0003X\u0459\bX\u0001" +
                    "X\u0001X\u0001X\u0001X\u0005X\u045f\bX\nX\fX\u0462\tX\u0001Y\u0001Y\u0001" +
                    "Z\u0001Z\u0001Z\u0000\u000b\u0002<Fj|\u0080\u0086\u0088\u008a\u0094\u00b0" +
                    "[\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a" +
                    "\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082" +
                    "\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a" +
                    "\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2" +
                    "\u00b4\u0000\n\u0001\u000005\u0001\u0000%\'\u0001\u0000#$\u0001\u0000" +
                    "+.\u0001\u0000)*\u0002\u000099>>\u0001\u0000:;\u0001\u000078\u0001\u0000" +
                    "<=\u0001\u0000\u001f \u04cc\u0000\u00b6\u0001\u0000\u0000\u0000\u0002" +
                    "\u00be\u0001\u0000\u0000\u0000\u0004\u00db\u0001\u0000\u0000\u0000\u0006" +
                    "\u00dd\u0001\u0000\u0000\u0000\b\u00fc\u0001\u0000\u0000\u0000\n\u0100" +
                    "\u0001\u0000\u0000\u0000\f\u0102\u0001\u0000\u0000\u0000\u000e\u0112\u0001" +
                    "\u0000\u0000\u0000\u0010\u0122\u0001\u0000\u0000\u0000\u0012\u014e\u0001" +
                    "\u0000\u0000\u0000\u0014\u0150\u0001\u0000\u0000\u0000\u0016\u0152\u0001" +
                    "\u0000\u0000\u0000\u0018\u0154\u0001\u0000\u0000\u0000\u001a\u0158\u0001" +
                    "\u0000\u0000\u0000\u001c\u015c\u0001\u0000\u0000\u0000\u001e\u015e\u0001" +
                    "\u0000\u0000\u0000 \u0162\u0001\u0000\u0000\u0000\"\u0166\u0001\u0000" +
                    "\u0000\u0000$\u0168\u0001\u0000\u0000\u0000&\u016c\u0001\u0000\u0000\u0000" +
                    "(\u016e\u0001\u0000\u0000\u0000*\u0171\u0001\u0000\u0000\u0000,\u0176" +
                    "\u0001\u0000\u0000\u0000.\u0178\u0001\u0000\u0000\u00000\u018f\u0001\u0000" +
                    "\u0000\u00002\u0198\u0001\u0000\u0000\u00004\u019c\u0001\u0000\u0000\u0000" +
                    "6\u01a7\u0001\u0000\u0000\u00008\u01ab\u0001\u0000\u0000\u0000:\u01b4" +
                    "\u0001\u0000\u0000\u0000<\u01b6\u0001\u0000\u0000\u0000>\u01c1\u0001\u0000" +
                    "\u0000\u0000@\u01c3\u0001\u0000\u0000\u0000B\u01f0\u0001\u0000\u0000\u0000" +
                    "D\u01f2\u0001\u0000\u0000\u0000F\u01f4\u0001\u0000\u0000\u0000H\u0201" +
                    "\u0001\u0000\u0000\u0000J\u0207\u0001\u0000\u0000\u0000L\u0209\u0001\u0000" +
                    "\u0000\u0000N\u0211\u0001\u0000\u0000\u0000P\u0219\u0001\u0000\u0000\u0000" +
                    "R\u021b\u0001\u0000\u0000\u0000T\u0239\u0001\u0000\u0000\u0000V\u025a" +
                    "\u0001\u0000\u0000\u0000X\u0272\u0001\u0000\u0000\u0000Z\u0274\u0001\u0000" +
                    "\u0000\u0000\\\u027e\u0001\u0000\u0000\u0000^\u0298\u0001\u0000\u0000" +
                    "\u0000`\u029a\u0001\u0000\u0000\u0000b\u02aa\u0001\u0000\u0000\u0000d" +
                    "\u02ac\u0001\u0000\u0000\u0000f\u02ae\u0001\u0000\u0000\u0000h\u02b4\u0001" +
                    "\u0000\u0000\u0000j\u02cb\u0001\u0000\u0000\u0000l\u02e9\u0001\u0000\u0000" +
                    "\u0000n\u02f3\u0001\u0000\u0000\u0000p\u02fd\u0001\u0000\u0000\u0000r" +
                    "\u0307\u0001\u0000\u0000\u0000t\u0311\u0001\u0000\u0000\u0000v\u0313\u0001" +
                    "\u0000\u0000\u0000x\u0318\u0001\u0000\u0000\u0000z\u0321\u0001\u0000\u0000" +
                    "\u0000|\u0323\u0001\u0000\u0000\u0000~\u0349\u0001\u0000\u0000\u0000\u0080" +
                    "\u0366\u0001\u0000\u0000\u0000\u0082\u0382\u0001\u0000\u0000\u0000\u0084" +
                    "\u0387\u0001\u0000\u0000\u0000\u0086\u038f\u0001\u0000\u0000\u0000\u0088" +
                    "\u03aa\u0001\u0000\u0000\u0000\u008a\u03c3\u0001\u0000\u0000\u0000\u008c" +
                    "\u03e5\u0001\u0000\u0000\u0000\u008e\u03ef\u0001\u0000\u0000\u0000\u0090" +
                    "\u03f5\u0001\u0000\u0000\u0000\u0092\u03fa\u0001\u0000\u0000\u0000\u0094" +
                    "\u0418\u0001\u0000\u0000\u0000\u0096\u043a\u0001\u0000\u0000\u0000\u0098" +
                    "\u043c\u0001\u0000\u0000\u0000\u009a\u043f\u0001\u0000\u0000\u0000\u009c" +
                    "\u0441\u0001\u0000\u0000\u0000\u009e\u0443\u0001\u0000\u0000\u0000\u00a0" +
                    "\u0445\u0001\u0000\u0000\u0000\u00a2\u0447\u0001\u0000\u0000\u0000\u00a4" +
                    "\u0449\u0001\u0000\u0000\u0000\u00a6\u044b\u0001\u0000\u0000\u0000\u00a8" +
                    "\u044d\u0001\u0000\u0000\u0000\u00aa\u044f\u0001\u0000\u0000\u0000\u00ac" +
                    "\u0451\u0001\u0000\u0000\u0000\u00ae\u0453\u0001\u0000\u0000\u0000\u00b0" +
                    "\u0458\u0001\u0000\u0000\u0000\u00b2\u0463\u0001\u0000\u0000\u0000\u00b4" +
                    "\u0465\u0001\u0000\u0000\u0000\u00b6\u00b7\u0003\u0002\u0001\u0000\u00b7" +
                    "\u00b8\u0005\u0000\u0000\u0001\u00b8\u0001\u0001\u0000\u0000\u0000\u00b9" +
                    "\u00ba\u0006\u0001\uffff\uffff\u0000\u00ba\u00bb\u0003\u0004\u0002\u0000" +
                    "\u00bb\u00bc\u0003\u00b0X\u0000\u00bc\u00bf\u0001\u0000\u0000\u0000\u00bd" +
                    "\u00bf\u0003\u00b0X\u0000\u00be\u00b9\u0001\u0000\u0000\u0000\u00be\u00bd" +
                    "\u0001\u0000\u0000\u0000\u00bf\u00c6\u0001\u0000\u0000\u0000\u00c0\u00c1" +
                    "\n\u0002\u0000\u0000\u00c1\u00c2\u0003\u0004\u0002\u0000\u00c2\u00c3\u0003" +
                    "\u00b0X\u0000\u00c3\u00c5\u0001\u0000\u0000\u0000\u00c4\u00c0\u0001\u0000" +
                    "\u0000\u0000\u00c5\u00c8\u0001\u0000\u0000\u0000\u00c6\u00c4\u0001\u0000" +
                    "\u0000\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u0003\u0001\u0000" +
                    "\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c9\u00dc\u0003^/\u0000" +
                    "\u00ca\u00dc\u00032\u0019\u0000\u00cb\u00dc\u0003&\u0013\u0000\u00cc\u00dc" +
                    "\u0003(\u0014\u0000\u00cd\u00dc\u0003V+\u0000\u00ce\u00dc\u0003X,\u0000" +
                    "\u00cf\u00dc\u0003\u0094J\u0000\u00d0\u00dc\u0003\u0098L\u0000\u00d1\u00dc" +
                    "\u0003@ \u0000\u00d2\u00dc\u0003Z-\u0000\u00d3\u00dc\u0003*\u0015\u0000" +
                    "\u00d4\u00dc\u0003f3\u0000\u00d5\u00dc\u0003.\u0017\u0000\u00d6\u00dc" +
                    "\u00030\u0018\u0000\u00d7\u00dc\u0003\f\u0006\u0000\u00d8\u00dc\u0003" +
                    "\u000e\u0007\u0000\u00d9\u00dc\u0003\u0010\b\u0000\u00da\u00dc\u0003\u0006" +
                    "\u0003\u0000\u00db\u00c9\u0001\u0000\u0000\u0000\u00db\u00ca\u0001\u0000" +
                    "\u0000\u0000\u00db\u00cb\u0001\u0000\u0000\u0000\u00db\u00cc\u0001\u0000" +
                    "\u0000\u0000\u00db\u00cd\u0001\u0000\u0000\u0000\u00db\u00ce\u0001\u0000" +
                    "\u0000\u0000\u00db\u00cf\u0001\u0000\u0000\u0000\u00db\u00d0\u0001\u0000" +
                    "\u0000\u0000\u00db\u00d1\u0001\u0000\u0000\u0000\u00db\u00d2\u0001\u0000" +
                    "\u0000\u0000\u00db\u00d3\u0001\u0000\u0000\u0000\u00db\u00d4\u0001\u0000" +
                    "\u0000\u0000\u00db\u00d5\u0001\u0000\u0000\u0000\u00db\u00d6\u0001\u0000" +
                    "\u0000\u0000\u00db\u00d7\u0001\u0000\u0000\u0000\u00db\u00d8\u0001\u0000" +
                    "\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00da\u0001\u0000" +
                    "\u0000\u0000\u00dc\u0005\u0001\u0000\u0000\u0000\u00dd\u00de\u0005\t\u0000" +
                    "\u0000\u00de\u00e2\u0003\b\u0004\u0000\u00df\u00e1\u0003\u00b4Z\u0000" +
                    "\u00e0\u00df\u0001\u0000\u0000\u0000\u00e1\u00e4\u0001\u0000\u0000\u0000" +
                    "\u00e2\u00e0\u0001\u0000\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000\u0000" +
                    "\u00e3\u00f1\u0001\u0000\u0000\u0000\u00e4\u00e2\u0001\u0000\u0000\u0000" +
                    "\u00e5\u00e6\u0005\n\u0000\u0000\u00e6\u00ea\u0003\n\u0005\u0000\u00e7" +
                    "\u00e9\u0003\u00b4Z\u0000\u00e8\u00e7\u0001\u0000\u0000\u0000\u00e9\u00ec" +
                    "\u0001\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00ea\u00eb" +
                    "\u0001\u0000\u0000\u0000\u00eb\u00ed\u0001\u0000\u0000\u0000\u00ec\u00ea" +
                    "\u0001\u0000\u0000\u0000\u00ed\u00ee\u0003h4\u0000\u00ee\u00f0\u0001\u0000" +
                    "\u0000\u0000\u00ef\u00e5\u0001\u0000\u0000\u0000\u00f0\u00f3\u0001\u0000" +
                    "\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000" +
                    "\u0000\u0000\u00f2\u00f8\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000" +
                    "\u0000\u0000\u00f4\u00f5\u0003\u00b2Y\u0000\u00f5\u00f6\u0003\u00b4Z\u0000" +
                    "\u00f6\u00f7\u0003h4\u0000\u00f7\u00f9\u0001\u0000\u0000\u0000\u00f8\u00f4" +
                    "\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa" +
                    "\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005\r\u0000\u0000\u00fb\u0007\u0001" +
                    "\u0000\u0000\u0000\u00fc\u00fd\u0003\u0094J\u0000\u00fd\t\u0001\u0000" +
                    "\u0000\u0000\u00fe\u0101\u0003b1\u0000\u00ff\u0101\u0003z=\u0000\u0100" +
                    "\u00fe\u0001\u0000\u0000\u0000\u0100\u00ff\u0001\u0000\u0000\u0000\u0101" +
                    "\u000b\u0001\u0000\u0000\u0000\u0102\u0103\u0005\f\u0000\u0000\u0103\u0107" +
                    "\u0005D\u0000\u0000\u0104\u0106\u0003\u00b4Z\u0000\u0105\u0104\u0001\u0000" +
                    "\u0000\u0000\u0106\u0109\u0001\u0000\u0000\u0000\u0107\u0105\u0001\u0000" +
                    "\u0000\u0000\u0107\u0108\u0001\u0000\u0000\u0000\u0108\u010a\u0001\u0000" +
                    "\u0000\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u010a\u010b\u0003h4\u0000" +
                    "\u010b\u010f\u0005E\u0000\u0000\u010c\u010e\u0003\u00b4Z\u0000\u010d\u010c" +
                    "\u0001\u0000\u0000\u0000\u010e\u0111\u0001\u0000\u0000\u0000\u010f\u010d" +
                    "\u0001\u0000\u0000\u0000\u010f\u0110\u0001\u0000\u0000\u0000\u0110\r\u0001" +
                    "\u0000\u0000\u0000\u0111\u010f\u0001\u0000\u0000\u0000\u0112\u0113\u0005" +
                    "\r\u0000\u0000\u0113\u0117\u0005D\u0000\u0000\u0114\u0116\u0003\u00b4" +
                    "Z\u0000\u0115\u0114\u0001\u0000\u0000\u0000\u0116\u0119\u0001\u0000\u0000" +
                    "\u0000\u0117\u0115\u0001\u0000\u0000\u0000\u0117\u0118\u0001\u0000\u0000" +
                    "\u0000\u0118\u011a\u0001\u0000\u0000\u0000\u0119\u0117\u0001\u0000\u0000" +
                    "\u0000\u011a\u011b\u0003h4\u0000\u011b\u011f\u0005E\u0000\u0000\u011c" +
                    "\u011e\u0003\u00b4Z\u0000\u011d\u011c\u0001\u0000\u0000\u0000\u011e\u0121" +
                    "\u0001\u0000\u0000\u0000\u011f\u011d\u0001\u0000\u0000\u0000\u011f\u0120" +
                    "\u0001\u0000\u0000\u0000\u0120\u000f\u0001\u0000\u0000\u0000\u0121\u011f" +
                    "\u0001\u0000\u0000\u0000\u0122\u0126\u0005\f\u0000\u0000\u0123\u0125\u0003" +
                    "\u00b4Z\u0000\u0124\u0123\u0001\u0000\u0000\u0000\u0125\u0128\u0001\u0000" +
                    "\u0000\u0000\u0126\u0124\u0001\u0000\u0000\u0000\u0126\u0127\u0001\u0000" +
                    "\u0000\u0000\u0127\u0129\u0001\u0000\u0000\u0000\u0128\u0126\u0001\u0000" +
                    "\u0000\u0000\u0129\u0138\u0003h4\u0000\u012a\u012c\u0003\u0014\n\u0000" +
                    "\u012b\u012d\u0003\u0012\t\u0000\u012c\u012b\u0001\u0000\u0000\u0000\u012c" +
                    "\u012d\u0001\u0000\u0000\u0000\u012d\u0131\u0001\u0000\u0000\u0000\u012e" +
                    "\u0130\u0003\u00b4Z\u0000\u012f\u012e\u0001\u0000\u0000\u0000\u0130\u0133" +
                    "\u0001\u0000\u0000\u0000\u0131\u012f\u0001\u0000\u0000\u0000\u0131\u0132" +
                    "\u0001\u0000\u0000\u0000\u0132\u0134\u0001\u0000\u0000\u0000\u0133\u0131" +
                    "\u0001\u0000\u0000\u0000\u0134\u0135\u0003h4\u0000\u0135\u0137\u0001\u0000" +
                    "\u0000\u0000\u0136\u012a\u0001\u0000\u0000\u0000\u0137\u013a\u0001\u0000" +
                    "\u0000\u0000\u0138\u0136\u0001\u0000\u0000\u0000\u0138\u0139\u0001\u0000" +
                    "\u0000\u0000\u0139\u013f\u0001\u0000\u0000\u0000\u013a\u0138\u0001\u0000" +
                    "\u0000\u0000\u013b\u013c\u0003\u00b2Y\u0000\u013c\u013d\u0003\u00b4Z\u0000" +
                    "\u013d\u013e\u0003h4\u0000\u013e\u0140\u0001\u0000\u0000\u0000\u013f\u013b" +
                    "\u0001\u0000\u0000\u0000\u013f\u0140\u0001\u0000\u0000\u0000\u0140\u014a" +
                    "\u0001\u0000\u0000\u0000\u0141\u0145\u0003\u0016\u000b\u0000\u0142\u0144" +
                    "\u0003\u00b4Z\u0000\u0143\u0142\u0001\u0000\u0000\u0000\u0144\u0147\u0001" +
                    "\u0000\u0000\u0000\u0145\u0143\u0001\u0000\u0000\u0000\u0145\u0146\u0001" +
                    "\u0000\u0000\u0000\u0146\u0148\u0001\u0000\u0000\u0000\u0147\u0145\u0001" +
                    "\u0000\u0000\u0000\u0148\u0149\u0003h4\u0000\u0149\u014b\u0001\u0000\u0000" +
                    "\u0000\u014a\u0141\u0001\u0000\u0000\u0000\u014a\u014b\u0001\u0000\u0000" +
                    "\u0000\u014b\u014c\u0001\u0000\u0000\u0000\u014c\u014d\u0005\r\u0000\u0000" +
                    "\u014d\u0011\u0001\u0000\u0000\u0000\u014e\u014f\u0003\u00a6S\u0000\u014f" +
                    "\u0013\u0001\u0000\u0000\u0000\u0150\u0151\u0005\u001e\u0000\u0000\u0151" +
                    "\u0015\u0001\u0000\u0000\u0000\u0152\u0153\u0005\u0012\u0000\u0000\u0153" +
                    "\u0017\u0001\u0000\u0000\u0000\u0154\u0155\u0003\u0092I\u0000\u0155\u0156" +
                    "\u0005/\u0000\u0000\u0156\u0157\u0003\u00a8T\u0000\u0157\u0019\u0001\u0000" +
                    "\u0000\u0000\u0158\u0159\u0003\u00a8T\u0000\u0159\u015a\u0005/\u0000\u0000" +
                    "\u015a\u015b\u0003P(\u0000\u015b\u001b\u0001\u0000\u0000\u0000\u015c\u015d" +
                    "\u0003\u00a8T\u0000\u015d\u001d\u0001\u0000\u0000\u0000\u015e\u015f\u0003" +
                    "\u0092I\u0000\u015f\u0160\u0005/\u0000\u0000\u0160\u0161\u0003\u00aaU" +
                    "\u0000\u0161\u001f\u0001\u0000\u0000\u0000\u0162\u0163\u0003\u00aaU\u0000" +
                    "\u0163\u0164\u0005/\u0000\u0000\u0164\u0165\u0003P(\u0000\u0165!\u0001" +
                    "\u0000\u0000\u0000\u0166\u0167\u0003\u00aaU\u0000\u0167#\u0001\u0000\u0000" +
                    "\u0000\u0168\u0169\u0003\u00acV\u0000\u0169\u016a\u0005/\u0000\u0000\u016a" +
                    "\u016b\u0003P(\u0000\u016b%\u0001\u0000\u0000\u0000\u016c\u016d\u0003" +
                    "B!\u0000\u016d\'\u0001\u0000\u0000\u0000\u016e\u016f\u0005\u000b\u0000" +
                    "\u0000\u016f\u0170\u0003\u009cN\u0000\u0170)\u0001\u0000\u0000\u0000\u0171" +
                    "\u0172\u0005\u0010\u0000\u0000\u0172\u0173\u0003\u00b4Z\u0000\u0173\u0174" +
                    "\u0003,\u0016\u0000\u0174\u0175\u0005\r\u0000\u0000\u0175+\u0001\u0000" +
                    "\u0000\u0000\u0176\u0177\u0003\u0002\u0001\u0000\u0177-\u0001\u0000\u0000" +
                    "\u0000\u0178\u0179\u0005\u0001\u0000\u0000\u0179\u017c\u0003\u0092I\u0000" +
                    "\u017a\u017b\u0005,\u0000\u0000\u017b\u017d\u0003\u0092I\u0000\u017c\u017a" +
                    "\u0001\u0000\u0000\u0000\u017c\u017d\u0001\u0000\u0000\u0000\u017d\u0181" +
                    "\u0001\u0000\u0000\u0000\u017e\u0180\u0003\u00b4Z\u0000\u017f\u017e\u0001" +
                    "\u0000\u0000\u0000\u0180\u0183\u0001\u0000\u0000\u0000\u0181\u017f\u0001" +
                    "\u0000\u0000\u0000\u0181\u0182\u0001\u0000\u0000\u0000\u0182\u0185\u0001" +
                    "\u0000\u0000\u0000\u0183\u0181\u0001\u0000\u0000\u0000\u0184\u0186\u0003" +
                    "j5\u0000\u0185\u0184\u0001\u0000\u0000\u0000\u0185\u0186\u0001\u0000\u0000" +
                    "\u0000\u0186\u018a\u0001\u0000\u0000\u0000\u0187\u0189\u0003\u00b4Z\u0000" +
                    "\u0188\u0187\u0001\u0000\u0000\u0000\u0189\u018c\u0001\u0000\u0000\u0000" +
                    "\u018a\u0188\u0001\u0000\u0000\u0000\u018a\u018b\u0001\u0000\u0000\u0000" +
                    "\u018b\u018d\u0001\u0000\u0000\u0000\u018c\u018a\u0001\u0000\u0000\u0000" +
                    "\u018d\u018e\u0005\r\u0000\u0000\u018e/\u0001\u0000\u0000\u0000\u018f" +
                    "\u0190\u0005\u0002\u0000\u0000\u0190\u0191\u0003\u00a6S\u0000\u0191\u0193" +
                    "\u0005\b\u0000\u0000\u0192\u0194\u0003j5\u0000\u0193\u0192\u0001\u0000" +
                    "\u0000\u0000\u0193\u0194\u0001\u0000\u0000\u0000\u0194\u0195\u0001\u0000" +
                    "\u0000\u0000\u0195\u0196\u0005\b\u0000\u0000\u0196\u0197\u0005\r\u0000" +
                    "\u0000\u01971\u0001\u0000\u0000\u0000\u0198\u0199\u00036\u001b\u0000\u0199" +
                    "\u019a\u00034\u001a\u0000\u019a\u019b\u0005\r\u0000\u0000\u019b3\u0001" +
                    "\u0000\u0000\u0000\u019c\u019d\u0003\u0002\u0001\u0000\u019d5\u0001\u0000" +
                    "\u0000\u0000\u019e\u019f\u0005\u000e\u0000\u0000\u019f\u01a0\u00038\u001c" +
                    "\u0000\u01a0\u01a1\u0003\u00b4Z\u0000\u01a1\u01a8\u0001\u0000\u0000\u0000" +
                    "\u01a2\u01a3\u0005\u000e\u0000\u0000\u01a3\u01a4\u00038\u001c\u0000\u01a4" +
                    "\u01a5\u0003:\u001d\u0000\u01a5\u01a6\u0003\u00b4Z\u0000\u01a6\u01a8\u0001" +
                    "\u0000\u0000\u0000\u01a7\u019e\u0001\u0000\u0000\u0000\u01a7\u01a2\u0001" +
                    "\u0000\u0000\u0000\u01a87\u0001\u0000\u0000\u0000\u01a9\u01ac\u0003\u00ae" +
                    "W\u0000\u01aa\u01ac\u0003\u00a6S\u0000\u01ab\u01a9\u0001\u0000\u0000\u0000" +
                    "\u01ab\u01aa\u0001\u0000\u0000\u0000\u01ac9\u0001\u0000\u0000\u0000\u01ad" +
                    "\u01ae\u0005@\u0000\u0000\u01ae\u01b5\u0005A\u0000\u0000\u01af\u01b0\u0005" +
                    "@\u0000\u0000\u01b0\u01b1\u0003<\u001e\u0000\u01b1\u01b2\u0005A\u0000" +
                    "\u0000\u01b2\u01b5\u0001\u0000\u0000\u0000\u01b3\u01b5\u0003<\u001e\u0000" +
                    "\u01b4\u01ad\u0001\u0000\u0000\u0000\u01b4\u01af\u0001\u0000\u0000\u0000" +
                    "\u01b4\u01b3\u0001\u0000\u0000\u0000\u01b5;\u0001\u0000\u0000\u0000\u01b6" +
                    "\u01b7\u0006\u001e\uffff\uffff\u0000\u01b7\u01b8\u0003>\u001f\u0000\u01b8" +
                    "\u01be\u0001\u0000\u0000\u0000\u01b9\u01ba\n\u0001\u0000\u0000\u01ba\u01bb" +
                    "\u0005\u0005\u0000\u0000\u01bb\u01bd\u0003>\u001f\u0000\u01bc\u01b9\u0001" +
                    "\u0000\u0000\u0000\u01bd\u01c0\u0001\u0000\u0000\u0000\u01be\u01bc\u0001" +
                    "\u0000\u0000\u0000\u01be\u01bf\u0001\u0000\u0000\u0000\u01bf=\u0001\u0000" +
                    "\u0000\u0000\u01c0\u01be\u0001\u0000\u0000\u0000\u01c1\u01c2\u0003\u00a6" +
                    "S\u0000\u01c2?\u0001\u0000\u0000\u0000\u01c3\u01c4\u0005\u000f\u0000\u0000" +
                    "\u01c4\u01c5\u0003P(\u0000\u01c5A\u0001\u0000\u0000\u0000\u01c6\u01c7" +
                    "\u00038\u001c\u0000\u01c7\u01c8\u0005@\u0000\u0000\u01c8\u01c9\u0003D" +
                    "\"\u0000\u01c9\u01ca\u0005A\u0000\u0000\u01ca\u01f1\u0001\u0000\u0000" +
                    "\u0000\u01cb\u01cc\u00038\u001c\u0000\u01cc\u01cd\u0003D\"\u0000\u01cd" +
                    "\u01f1\u0001\u0000\u0000\u0000\u01ce\u01cf\u00038\u001c\u0000\u01cf\u01d0" +
                    "\u0005@\u0000\u0000\u01d0\u01d1\u0005A\u0000\u0000\u01d1\u01f1\u0001\u0000" +
                    "\u0000\u0000\u01d2\u01d3\u0003\u00a6S\u0000\u01d3\u01d4\u0005Q\u0000\u0000" +
                    "\u01d4\u01d5\u00038\u001c\u0000\u01d5\u01d7\u0005@\u0000\u0000\u01d6\u01d8" +
                    "\u0003D\"\u0000\u01d7\u01d6\u0001\u0000\u0000\u0000\u01d7\u01d8\u0001" +
                    "\u0000\u0000\u0000\u01d8\u01d9\u0001\u0000\u0000\u0000\u01d9\u01da\u0005" +
                    "A\u0000\u0000\u01da\u01f1\u0001\u0000\u0000\u0000\u01db\u01dc\u0003\u00a6" +
                    "S\u0000\u01dc\u01dd\u0005Q\u0000\u0000\u01dd\u01df\u00038\u001c\u0000" +
                    "\u01de\u01e0\u0003D\"\u0000\u01df\u01de\u0001\u0000\u0000\u0000\u01df" +
                    "\u01e0\u0001\u0000\u0000\u0000\u01e0\u01f1\u0001\u0000\u0000\u0000\u01e1" +
                    "\u01e2\u0003\u00a6S\u0000\u01e2\u01e3\u0005\u0003\u0000\u0000\u01e3\u01e4" +
                    "\u00038\u001c\u0000\u01e4\u01e6\u0005@\u0000\u0000\u01e5\u01e7\u0003D" +
                    "\"\u0000\u01e6\u01e5\u0001\u0000\u0000\u0000\u01e6\u01e7\u0001\u0000\u0000" +
                    "\u0000\u01e7\u01e8\u0001\u0000\u0000\u0000\u01e8\u01e9\u0005A\u0000\u0000" +
                    "\u01e9\u01f1\u0001\u0000\u0000\u0000\u01ea\u01eb\u0003\u00a6S\u0000\u01eb" +
                    "\u01ec\u0005\u0003\u0000\u0000\u01ec\u01ee\u00038\u001c\u0000\u01ed\u01ef" +
                    "\u0003D\"\u0000\u01ee\u01ed\u0001\u0000\u0000\u0000\u01ee\u01ef\u0001" +
                    "\u0000\u0000\u0000\u01ef\u01f1\u0001\u0000\u0000\u0000\u01f0\u01c6\u0001" +
                    "\u0000\u0000\u0000\u01f0\u01cb\u0001\u0000\u0000\u0000\u01f0\u01ce\u0001" +
                    "\u0000\u0000\u0000\u01f0\u01d2\u0001\u0000\u0000\u0000\u01f0\u01db\u0001" +
                    "\u0000\u0000\u0000\u01f0\u01e1\u0001\u0000\u0000\u0000\u01f0\u01ea\u0001" +
                    "\u0000\u0000\u0000\u01f1C\u0001\u0000\u0000\u0000\u01f2\u01f3\u0003F#" +
                    "\u0000\u01f3E\u0001\u0000\u0000\u0000\u01f4\u01f5\u0006#\uffff\uffff\u0000" +
                    "\u01f5\u01f6\u0003H$\u0000\u01f6\u01fc\u0001\u0000\u0000\u0000\u01f7\u01f8" +
                    "\n\u0001\u0000\u0000\u01f8\u01f9\u0005\u0005\u0000\u0000\u01f9\u01fb\u0003" +
                    "H$\u0000\u01fa\u01f7\u0001\u0000\u0000\u0000\u01fb\u01fe\u0001\u0000\u0000" +
                    "\u0000\u01fc\u01fa\u0001\u0000\u0000\u0000\u01fc\u01fd\u0001\u0000\u0000" +
                    "\u0000\u01fdG\u0001\u0000\u0000\u0000\u01fe\u01fc\u0001\u0000\u0000\u0000" +
                    "\u01ff\u0202\u0003J%\u0000\u0200\u0202\u0003L&\u0000\u0201\u01ff\u0001" +
                    "\u0000\u0000\u0000\u0201\u0200\u0001\u0000\u0000\u0000\u0202I\u0001\u0000" +
                    "\u0000\u0000\u0203\u0208\u0003\u0086C\u0000\u0204\u0208\u0003\u0088D\u0000" +
                    "\u0205\u0208\u0003\u008aE\u0000\u0206\u0208\u0003\u0080@\u0000\u0207\u0203" +
                    "\u0001\u0000\u0000\u0000\u0207\u0204\u0001\u0000\u0000\u0000\u0207\u0205" +
                    "\u0001\u0000\u0000\u0000\u0207\u0206\u0001\u0000\u0000\u0000\u0208K\u0001" +
                    "\u0000\u0000\u0000\u0209\u020a\u0003\u00a6S\u0000\u020a\u020f\u0005/\u0000" +
                    "\u0000\u020b\u0210\u0003\u0086C\u0000\u020c\u0210\u0003\u0088D\u0000\u020d" +
                    "\u0210\u0003\u008aE\u0000\u020e\u0210\u0003\u0080@\u0000\u020f\u020b\u0001" +
                    "\u0000\u0000\u0000\u020f\u020c\u0001\u0000\u0000\u0000\u020f\u020d\u0001" +
                    "\u0000\u0000\u0000\u020f\u020e\u0001\u0000\u0000\u0000\u0210M\u0001\u0000" +
                    "\u0000\u0000\u0211\u0212\u0003B!\u0000\u0212O\u0001\u0000\u0000\u0000" +
                    "\u0213\u021a\u0003\u0086C\u0000\u0214\u021a\u0003\u0088D\u0000\u0215\u021a" +
                    "\u0003\u008aE\u0000\u0216\u021a\u0003\u0080@\u0000\u0217\u021a\u0003\u001c" +
                    "\u000e\u0000\u0218\u021a\u0003\"\u0011\u0000\u0219\u0213\u0001\u0000\u0000" +
                    "\u0000\u0219\u0214\u0001\u0000\u0000\u0000\u0219\u0215\u0001\u0000\u0000" +
                    "\u0000\u0219\u0216\u0001\u0000\u0000\u0000\u0219\u0217\u0001\u0000\u0000" +
                    "\u0000\u0219\u0218\u0001\u0000\u0000\u0000\u021aQ\u0001\u0000\u0000\u0000" +
                    "\u021b\u021c\u0003T*\u0000\u021cS\u0001\u0000\u0000\u0000\u021d\u021e" +
                    "\u0005\u0015\u0000\u0000\u021e\u0220\u0003b1\u0000\u021f\u0221\u0005\u001b" +
                    "\u0000\u0000\u0220\u021f\u0001\u0000\u0000\u0000\u0220\u0221\u0001\u0000" +
                    "\u0000\u0000\u0221\u0222\u0001\u0000\u0000\u0000\u0222\u0223\u0003\u00b4" +
                    "Z\u0000\u0223\u0224\u0003h4\u0000\u0224\u023a\u0001\u0000\u0000\u0000" +
                    "\u0225\u0226\u0005\u0015\u0000\u0000\u0226\u0228\u0003b1\u0000\u0227\u0229" +
                    "\u0005\u001b\u0000\u0000\u0228\u0227\u0001\u0000\u0000\u0000\u0228\u0229" +
                    "\u0001\u0000\u0000\u0000\u0229\u022a\u0001\u0000\u0000\u0000\u022a\u022b" +
                    "\u0003\u00b4Z\u0000\u022b\u022c\u0003h4\u0000\u022c\u022d\u0003\u00b2" +
                    "Y\u0000\u022d\u022e\u0003\u00b4Z\u0000\u022e\u022f\u0003h4\u0000\u022f" +
                    "\u023a\u0001\u0000\u0000\u0000\u0230\u0231\u0005\u0015\u0000\u0000\u0231" +
                    "\u0233\u0003b1\u0000\u0232\u0234\u0005\u001b\u0000\u0000\u0233\u0232\u0001" +
                    "\u0000\u0000\u0000\u0233\u0234\u0001\u0000\u0000\u0000\u0234\u0235\u0001" +
                    "\u0000\u0000\u0000\u0235\u0236\u0003\u00b4Z\u0000\u0236\u0237\u0003h4" +
                    "\u0000\u0237\u0238\u0003T*\u0000\u0238\u023a\u0001\u0000\u0000\u0000\u0239" +
                    "\u021d\u0001\u0000\u0000\u0000\u0239\u0225\u0001\u0000\u0000\u0000\u0239" +
                    "\u0230\u0001\u0000\u0000\u0000\u023aU\u0001\u0000\u0000\u0000\u023b\u023c" +
                    "\u0005\u0013\u0000\u0000\u023c\u023e\u0003b1\u0000\u023d\u023f\u0005\u001b" +
                    "\u0000\u0000\u023e\u023d\u0001\u0000\u0000\u0000\u023e\u023f\u0001\u0000" +
                    "\u0000\u0000\u023f\u0240\u0001\u0000\u0000\u0000\u0240\u0241\u0003\u00b4" +
                    "Z\u0000\u0241\u0242\u0003h4\u0000\u0242\u0243\u0005\r\u0000\u0000\u0243" +
                    "\u025b\u0001\u0000\u0000\u0000\u0244\u0245\u0005\u0013\u0000\u0000\u0245" +
                    "\u0247\u0003b1\u0000\u0246\u0248\u0005\u001b\u0000\u0000\u0247\u0246\u0001" +
                    "\u0000\u0000\u0000\u0247\u0248\u0001\u0000\u0000\u0000\u0248\u0249\u0001" +
                    "\u0000\u0000\u0000\u0249\u024a\u0003\u00b4Z\u0000\u024a\u024b\u0003h4" +
                    "\u0000\u024b\u024c\u0003\u00b2Y\u0000\u024c\u024d\u0003\u00b4Z\u0000\u024d" +
                    "\u024e\u0003h4\u0000\u024e\u024f\u0005\r\u0000\u0000\u024f\u025b\u0001" +
                    "\u0000\u0000\u0000\u0250\u0251\u0005\u0013\u0000\u0000\u0251\u0253\u0003" +
                    "b1\u0000\u0252\u0254\u0005\u001b\u0000\u0000\u0253\u0252\u0001\u0000\u0000" +
                    "\u0000\u0253\u0254\u0001\u0000\u0000\u0000\u0254\u0255\u0001\u0000\u0000" +
                    "\u0000\u0255\u0256\u0003\u00b4Z\u0000\u0256\u0257\u0003h4\u0000\u0257" +
                    "\u0258\u0003R)\u0000\u0258\u0259\u0005\r\u0000\u0000\u0259\u025b\u0001" +
                    "\u0000\u0000\u0000\u025a\u023b\u0001\u0000\u0000\u0000\u025a\u0244\u0001" +
                    "\u0000\u0000\u0000\u025a\u0250\u0001\u0000\u0000\u0000\u025bW\u0001\u0000" +
                    "\u0000\u0000\u025c\u025d\u0005\u0016\u0000\u0000\u025d\u025e\u0003b1\u0000" +
                    "\u025e\u025f\u0003\u00b4Z\u0000\u025f\u0260\u0003h4\u0000\u0260\u0261" +
                    "\u0005\r\u0000\u0000\u0261\u0273\u0001\u0000\u0000\u0000\u0262\u0263\u0005" +
                    "\u0016\u0000\u0000\u0263\u0264\u0003b1\u0000\u0264\u0265\u0003\u00b4Z" +
                    "\u0000\u0265\u0266\u0003h4\u0000\u0266\u0267\u0003\u00b2Y\u0000\u0267" +
                    "\u0268\u0003\u00b4Z\u0000\u0268\u0269\u0003h4\u0000\u0269\u026a\u0005" +
                    "\r\u0000\u0000\u026a\u0273\u0001\u0000\u0000\u0000\u026b\u026c\u0005\u0016" +
                    "\u0000\u0000\u026c\u026d\u0003b1\u0000\u026d\u026e\u0003\u00b4Z\u0000" +
                    "\u026e\u026f\u0003h4\u0000\u026f\u0270\u0003R)\u0000\u0270\u0271\u0005" +
                    "\r\u0000\u0000\u0271\u0273\u0001\u0000\u0000\u0000\u0272\u025c\u0001\u0000" +
                    "\u0000\u0000\u0272\u0262\u0001\u0000\u0000\u0000\u0272\u026b\u0001\u0000" +
                    "\u0000\u0000\u0273Y\u0001\u0000\u0000\u0000\u0274\u0275\u0005\u0017\u0000" +
                    "\u0000\u0275\u0276\u0003b1\u0000\u0276\u0277\u0003\u00b4Z\u0000\u0277" +
                    "\u0278\u0003h4\u0000\u0278\u0279\u0005\r\u0000\u0000\u0279[\u0001\u0000" +
                    "\u0000\u0000\u027a\u027f\u0003p8\u0000\u027b\u027f\u0003r9\u0000\u027c" +
                    "\u027f\u0003t:\u0000\u027d\u027f\u0003n7\u0000\u027e\u027a\u0001\u0000" +
                    "\u0000\u0000\u027e\u027b\u0001\u0000\u0000\u0000\u027e\u027c\u0001\u0000" +
                    "\u0000\u0000\u027e\u027d\u0001\u0000\u0000\u0000\u027f]\u0001\u0000\u0000" +
                    "\u0000\u0280\u0281\u0005\u001a\u0000\u0000\u0281\u0286\u0003\u0092I\u0000" +
                    "\u0282\u0283\u0005\u0005\u0000\u0000\u0283\u0285\u0003\u0092I\u0000\u0284" +
                    "\u0282\u0001\u0000\u0000\u0000\u0285\u0288\u0001\u0000\u0000\u0000\u0286" +
                    "\u0284\u0001\u0000\u0000\u0000\u0286\u0287\u0001\u0000\u0000\u0000\u0287" +
                    "\u0289\u0001\u0000\u0000\u0000\u0288\u0286\u0001\u0000\u0000\u0000\u0289" +
                    "\u028a\u0005\u001c\u0000\u0000\u028a\u028c\u0003d2\u0000\u028b\u028d\u0005" +
                    "\u001d\u0000\u0000\u028c\u028b\u0001\u0000\u0000\u0000\u028c\u028d\u0001" +
                    "\u0000\u0000\u0000\u028d\u0291\u0001\u0000\u0000\u0000\u028e\u0290\u0005" +
                    "\b\u0000\u0000\u028f\u028e\u0001\u0000\u0000\u0000\u0290\u0293\u0001\u0000" +
                    "\u0000\u0000\u0291\u028f\u0001\u0000\u0000\u0000\u0291\u0292\u0001\u0000" +
                    "\u0000\u0000\u0292\u0294\u0001\u0000\u0000\u0000\u0293\u0291\u0001\u0000" +
                    "\u0000\u0000\u0294\u0295\u0003h4\u0000\u0295\u0296\u0005\r\u0000\u0000" +
                    "\u0296\u0299\u0001\u0000\u0000\u0000\u0297\u0299\u0003`0\u0000\u0298\u0280" +
                    "\u0001\u0000\u0000\u0000\u0298\u0297\u0001\u0000\u0000\u0000\u0299_\u0001" +
                    "\u0000\u0000\u0000\u029a\u029b\u0003z=\u0000\u029b\u029c\u0005Q\u0000" +
                    "\u0000\u029c\u029d\u0005\"\u0000\u0000\u029d\u029f\u0005D\u0000\u0000" +
                    "\u029e\u02a0\u0003\u00b4Z\u0000\u029f\u029e\u0001\u0000\u0000\u0000\u029f" +
                    "\u02a0\u0001\u0000\u0000\u0000\u02a0\u02a1\u0001\u0000\u0000\u0000\u02a1" +
                    "\u02a2\u00057\u0000\u0000\u02a2\u02a3\u0003\u00a6S\u0000\u02a3\u02a5\u0005" +
                    "7\u0000\u0000\u02a4\u02a6\u0003\u00b4Z\u0000\u02a5\u02a4\u0001\u0000\u0000" +
                    "\u0000\u02a5\u02a6\u0001\u0000\u0000\u0000\u02a6\u02a7\u0001\u0000\u0000" +
                    "\u0000\u02a7\u02a8\u0003h4\u0000\u02a8\u02a9\u0005E\u0000\u0000\u02a9" +
                    "a\u0001\u0000\u0000\u0000\u02aa\u02ab\u0003\u008cF\u0000\u02abc\u0001" +
                    "\u0000\u0000\u0000\u02ac\u02ad\u0003z=\u0000\u02ade\u0001\u0000\u0000" +
                    "\u0000\u02ae\u02af\u0005D\u0000\u0000\u02af\u02b0\u0003\u0004\u0002\u0000" +
                    "\u02b0\u02b1\u0005?\u0000\u0000\u02b1\u02b2\u0003\u0004\u0002\u0000\u02b2" +
                    "\u02b3\u0005E\u0000\u0000\u02b3g\u0001\u0000\u0000\u0000\u02b4\u02b5\u0003" +
                    "j5\u0000\u02b5i\u0001\u0000\u0000\u0000\u02b6\u02b7\u00065\uffff\uffff" +
                    "\u0000\u02b7\u02b9\u0003\u0004\u0002\u0000\u02b8\u02ba\u0003\u00b0X\u0000" +
                    "\u02b9\u02b8\u0001\u0000\u0000\u0000\u02b9\u02ba\u0001\u0000\u0000\u0000" +
                    "\u02ba\u02cc\u0001\u0000\u0000\u0000\u02bb\u02bd\u0005\u0018\u0000\u0000" +
                    "\u02bc\u02be\u0003\u00b0X\u0000\u02bd\u02bc\u0001\u0000\u0000\u0000\u02bd" +
                    "\u02be\u0001\u0000\u0000\u0000\u02be\u02cc\u0001\u0000\u0000\u0000\u02bf" +
                    "\u02c1\u0003\u0096K\u0000\u02c0\u02c2\u0003\u00b0X\u0000\u02c1\u02c0\u0001" +
                    "\u0000\u0000\u0000\u02c1\u02c2\u0001\u0000\u0000\u0000\u02c2\u02cc\u0001" +
                    "\u0000\u0000\u0000\u02c3\u02c5\u0003\u0098L\u0000\u02c4\u02c6\u0003\u00b0" +
                    "X\u0000\u02c5\u02c4\u0001\u0000\u0000\u0000\u02c5\u02c6\u0001\u0000\u0000" +
                    "\u0000\u02c6\u02cc\u0001\u0000\u0000\u0000\u02c7\u02c9\u0003\u009aM\u0000" +
                    "\u02c8\u02ca\u0003\u00b0X\u0000\u02c9\u02c8\u0001\u0000\u0000\u0000\u02c9" +
                    "\u02ca\u0001\u0000\u0000\u0000\u02ca\u02cc\u0001\u0000\u0000\u0000\u02cb" +
                    "\u02b6\u0001\u0000\u0000\u0000\u02cb\u02bb\u0001\u0000\u0000\u0000\u02cb" +
                    "\u02bf\u0001\u0000\u0000\u0000\u02cb\u02c3\u0001\u0000\u0000\u0000\u02cb" +
                    "\u02c7\u0001\u0000\u0000\u0000\u02cc\u02de\u0001\u0000\u0000\u0000\u02cd" +
                    "\u02ce\n\u0003\u0000\u0000\u02ce\u02d0\u0003\u0004\u0002\u0000\u02cf\u02d1" +
                    "\u0003\u00b0X\u0000\u02d0\u02cf\u0001\u0000\u0000\u0000\u02d0\u02d1\u0001" +
                    "\u0000\u0000\u0000\u02d1\u02dd\u0001\u0000\u0000\u0000\u02d2\u02d3\n\u0002" +
                    "\u0000\u0000\u02d3\u02d5\u0005\u0018\u0000\u0000\u02d4\u02d6\u0003\u00b0" +
                    "X\u0000\u02d5\u02d4\u0001\u0000\u0000\u0000\u02d5\u02d6\u0001\u0000\u0000" +
                    "\u0000\u02d6\u02dd\u0001\u0000\u0000\u0000\u02d7\u02d8\n\u0001\u0000\u0000" +
                    "\u02d8\u02da\u0003\u0096K\u0000\u02d9\u02db\u0003\u00b0X\u0000\u02da\u02d9" +
                    "\u0001\u0000\u0000\u0000\u02da\u02db\u0001\u0000\u0000\u0000\u02db\u02dd" +
                    "\u0001\u0000\u0000\u0000\u02dc\u02cd\u0001\u0000\u0000\u0000\u02dc\u02d2" +
                    "\u0001\u0000\u0000\u0000\u02dc\u02d7\u0001\u0000\u0000\u0000\u02dd\u02e0" +
                    "\u0001\u0000\u0000\u0000\u02de\u02dc\u0001\u0000\u0000\u0000\u02de\u02df" +
                    "\u0001\u0000\u0000\u0000\u02dfk\u0001\u0000\u0000\u0000\u02e0\u02de\u0001" +
                    "\u0000\u0000\u0000\u02e1\u02e2\u0003\u0092I\u0000\u02e2\u02e3\u0005/\u0000" +
                    "\u0000\u02e3\u02e4\u0003\u0094J\u0000\u02e4\u02ea\u0001\u0000\u0000\u0000" +
                    "\u02e5\u02e6\u0003\u0092I\u0000\u02e6\u02e7\u0007\u0000\u0000\u0000\u02e7" +
                    "\u02e8\u0003\u0094J\u0000\u02e8\u02ea\u0001\u0000\u0000\u0000\u02e9\u02e1" +
                    "\u0001\u0000\u0000\u0000\u02e9\u02e5\u0001\u0000\u0000\u0000\u02eam\u0001" +
                    "\u0000\u0000\u0000\u02eb\u02ec\u0003\u0092I\u0000\u02ec\u02ed\u0005/\u0000" +
                    "\u0000\u02ed\u02ee\u0003\u0080@\u0000\u02ee\u02f4\u0001\u0000\u0000\u0000" +
                    "\u02ef\u02f0\u0003\u0092I\u0000\u02f0\u02f1\u0007\u0000\u0000\u0000\u02f1" +
                    "\u02f2\u0003\u0080@\u0000\u02f2\u02f4\u0001\u0000\u0000\u0000\u02f3\u02eb" +
                    "\u0001\u0000\u0000\u0000\u02f3\u02ef\u0001\u0000\u0000\u0000\u02f4o\u0001" +
                    "\u0000\u0000\u0000\u02f5\u02f6\u0003\u0092I\u0000\u02f6\u02f7\u0005/\u0000" +
                    "\u0000\u02f7\u02f8\u0003\u0086C\u0000\u02f8\u02fe\u0001\u0000\u0000\u0000" +
                    "\u02f9\u02fa\u0003\u0092I\u0000\u02fa\u02fb\u0007\u0000\u0000\u0000\u02fb" +
                    "\u02fc\u0003\u0086C\u0000\u02fc\u02fe\u0001\u0000\u0000\u0000\u02fd\u02f5" +
                    "\u0001\u0000\u0000\u0000\u02fd\u02f9\u0001\u0000\u0000\u0000\u02feq\u0001" +
                    "\u0000\u0000\u0000\u02ff\u0300\u0003\u0092I\u0000\u0300\u0301\u0005/\u0000" +
                    "\u0000\u0301\u0302\u0003\u0088D\u0000\u0302\u0308\u0001\u0000\u0000\u0000" +
                    "\u0303\u0304\u0003\u0092I\u0000\u0304\u0305\u0007\u0000\u0000\u0000\u0305" +
                    "\u0306\u0003\u0088D\u0000\u0306\u0308\u0001\u0000\u0000\u0000\u0307\u02ff" +
                    "\u0001\u0000\u0000\u0000\u0307\u0303\u0001\u0000\u0000\u0000\u0308s\u0001" +
                    "\u0000\u0000\u0000\u0309\u030a\u0003\u0092I\u0000\u030a\u030b\u0005/\u0000" +
                    "\u0000\u030b\u030c\u0003\u008aE\u0000\u030c\u0312\u0001\u0000\u0000\u0000" +
                    "\u030d\u030e\u0003\u0092I\u0000\u030e\u030f\u00050\u0000\u0000\u030f\u0310" +
                    "\u0003\u008aE\u0000\u0310\u0312\u0001\u0000\u0000\u0000\u0311\u0309\u0001" +
                    "\u0000\u0000\u0000\u0311\u030d\u0001\u0000\u0000\u0000\u0312u\u0001\u0000" +
                    "\u0000\u0000\u0313\u0314\u0003\u0092I\u0000\u0314\u0315\u0005/\u0000\u0000" +
                    "\u0315\u0316\u0005B\u0000\u0000\u0316\u0317\u0005C\u0000\u0000\u0317w" +
                    "\u0001\u0000\u0000\u0000\u0318\u0319\u0003~?\u0000\u0319\u031a\u0005/" +
                    "\u0000\u0000\u031a\u031b\u0003P(\u0000\u031by\u0001\u0000\u0000\u0000" +
                    "\u031c\u031d\u0005B\u0000\u0000\u031d\u031e\u0003|>\u0000\u031e\u031f" +
                    "\u0005C\u0000\u0000\u031f\u0322\u0001\u0000\u0000\u0000\u0320\u0322\u0003" +
                    "|>\u0000\u0321\u031c\u0001\u0000\u0000\u0000\u0321\u0320\u0001\u0000\u0000" +
                    "\u0000\u0322{\u0001\u0000\u0000\u0000\u0323\u0326\u0006>\uffff\uffff\u0000" +
                    "\u0324\u0327\u0003\u0086C\u0000\u0325\u0327\u0003\u0080@\u0000\u0326\u0324" +
                    "\u0001\u0000\u0000\u0000\u0326\u0325\u0001\u0000\u0000\u0000\u0327\u0336" +
                    "\u0001\u0000\u0000\u0000\u0328\u0329\n\u0002\u0000\u0000\u0329\u032c\u0005" +
                    "\u0005\u0000\u0000\u032a\u032d\u0003\u0086C\u0000\u032b\u032d\u0003\u0080" +
                    "@\u0000\u032c\u032a\u0001\u0000\u0000\u0000\u032c\u032b\u0001\u0000\u0000" +
                    "\u0000\u032d\u0335\u0001\u0000\u0000\u0000\u032e\u032f\n\u0001\u0000\u0000" +
                    "\u032f\u0332\u0005\u0006\u0000\u0000\u0330\u0333\u0003\u0086C\u0000\u0331" +
                    "\u0333\u0003\u0080@\u0000\u0332\u0330\u0001\u0000\u0000\u0000\u0332\u0331" +
                    "\u0001\u0000\u0000\u0000\u0333\u0335\u0001\u0000\u0000\u0000\u0334\u0328" +
                    "\u0001\u0000\u0000\u0000\u0334\u032e\u0001\u0000\u0000\u0000\u0335\u0338" +
                    "\u0001\u0000\u0000\u0000\u0336\u0334\u0001\u0000\u0000\u0000\u0336\u0337" +
                    "\u0001\u0000\u0000\u0000\u0337}\u0001\u0000\u0000\u0000\u0338\u0336\u0001" +
                    "\u0000\u0000\u0000\u0339\u033a\u0003\u00a6S\u0000\u033a\u033d\u0005B\u0000" +
                    "\u0000\u033b\u033e\u0003\u0086C\u0000\u033c\u033e\u0003\u0080@\u0000\u033d" +
                    "\u033b\u0001\u0000\u0000\u0000\u033d\u033c\u0001\u0000\u0000\u0000\u033e" +
                    "\u033f\u0001\u0000\u0000\u0000\u033f\u0340\u0005C\u0000\u0000\u0340\u034a" +
                    "\u0001\u0000\u0000\u0000\u0341\u0342\u0003\u00a8T\u0000\u0342\u0345\u0005" +
                    "B\u0000\u0000\u0343\u0346\u0003\u0086C\u0000\u0344\u0346\u0003\u0080@" +
                    "\u0000\u0345\u0343\u0001\u0000\u0000\u0000\u0345\u0344\u0001\u0000\u0000" +
                    "\u0000\u0346\u0347\u0001\u0000\u0000\u0000\u0347\u0348\u0005C\u0000\u0000" +
                    "\u0348\u034a\u0001\u0000\u0000\u0000\u0349\u0339\u0001\u0000\u0000\u0000" +
                    "\u0349\u0341\u0001\u0000\u0000\u0000\u034a\u007f\u0001\u0000\u0000\u0000" +
                    "\u034b\u034c\u0006@\uffff\uffff\u0000\u034c\u034d\u0003\u0086C\u0000\u034d" +
                    "\u034e\u0007\u0001\u0000\u0000\u034e\u034f\u0003\u0080@\u000e\u034f\u0367" +
                    "\u0001\u0000\u0000\u0000\u0350\u0351\u0003\u0088D\u0000\u0351\u0352\u0007" +
                    "\u0001\u0000\u0000\u0352\u0353\u0003\u0080@\f\u0353\u0367\u0001\u0000" +
                    "\u0000\u0000\u0354\u0355\u0003\u008aE\u0000\u0355\u0356\u0005%\u0000\u0000" +
                    "\u0356\u0357\u0003\u0080@\t\u0357\u0367\u0001\u0000\u0000\u0000\u0358" +
                    "\u0359\u0003\u0086C\u0000\u0359\u035a\u0007\u0002\u0000\u0000\u035a\u035b" +
                    "\u0003\u0080@\u0007\u035b\u0367\u0001\u0000\u0000\u0000\u035c\u035d\u0003" +
                    "\u0088D\u0000\u035d\u035e\u0007\u0002\u0000\u0000\u035e\u035f\u0003\u0080" +
                    "@\u0005\u035f\u0367\u0001\u0000\u0000\u0000\u0360\u0361\u0005@\u0000\u0000" +
                    "\u0361\u0362\u0003\u0080@\u0000\u0362\u0363\u0005A\u0000\u0000\u0363\u0367" +
                    "\u0001\u0000\u0000\u0000\u0364\u0367\u0003\u0082A\u0000\u0365\u0367\u0003" +
                    "\u0084B\u0000\u0366\u034b\u0001\u0000\u0000\u0000\u0366\u0350\u0001\u0000" +
                    "\u0000\u0000\u0366\u0354\u0001\u0000\u0000\u0000\u0366\u0358\u0001\u0000" +
                    "\u0000\u0000\u0366\u035c\u0001\u0000\u0000\u0000\u0366\u0360\u0001\u0000" +
                    "\u0000\u0000\u0366\u0364\u0001\u0000\u0000\u0000\u0366\u0365\u0001\u0000" +
                    "\u0000\u0000\u0367\u037f\u0001\u0000\u0000\u0000\u0368\u0369\n\u000b\u0000" +
                    "\u0000\u0369\u036a\u0007\u0001\u0000\u0000\u036a\u037e\u0003\u0080@\f" +
                    "\u036b\u036c\n\u0004\u0000\u0000\u036c\u036d\u0007\u0002\u0000\u0000\u036d" +
                    "\u037e\u0003\u0080@\u0005\u036e\u036f\n\u000f\u0000\u0000\u036f\u0370" +
                    "\u0007\u0001\u0000\u0000\u0370\u037e\u0003\u0086C\u0000\u0371\u0372\n" +
                    "\r\u0000\u0000\u0372\u0373\u0007\u0001\u0000\u0000\u0373\u037e\u0003\u0088" +
                    "D\u0000\u0374\u0375\n\n\u0000\u0000\u0375\u0376\u0005%\u0000\u0000\u0376" +
                    "\u037e\u0003\u008aE\u0000\u0377\u0378\n\b\u0000\u0000\u0378\u0379\u0007" +
                    "\u0002\u0000\u0000\u0379\u037e\u0003\u0086C\u0000\u037a\u037b\n\u0006" +
                    "\u0000\u0000\u037b\u037c\u0007\u0002\u0000\u0000\u037c\u037e\u0003\u0088" +
                    "D\u0000\u037d\u0368\u0001\u0000\u0000\u0000\u037d\u036b\u0001\u0000\u0000" +
                    "\u0000\u037d\u036e\u0001\u0000\u0000\u0000\u037d\u0371\u0001\u0000\u0000" +
                    "\u0000\u037d\u0374\u0001\u0000\u0000\u0000\u037d\u0377\u0001\u0000\u0000" +
                    "\u0000\u037d\u037a\u0001\u0000\u0000\u0000\u037e\u0381\u0001\u0000\u0000" +
                    "\u0000\u037f\u037d\u0001\u0000\u0000\u0000\u037f\u0380\u0001\u0000\u0000" +
                    "\u0000\u0380\u0081\u0001\u0000\u0000\u0000\u0381\u037f\u0001\u0000\u0000" +
                    "\u0000\u0382\u0383\u0003f3\u0000\u0383\u0083\u0001\u0000\u0000\u0000\u0384" +
                    "\u0388\u0003\u00a6S\u0000\u0385\u0388\u0003N\'\u0000\u0386\u0388\u0003" +
                    "~?\u0000\u0387\u0384\u0001\u0000\u0000\u0000\u0387\u0385\u0001\u0000\u0000" +
                    "\u0000\u0387\u0386\u0001\u0000\u0000\u0000\u0388\u0085\u0001\u0000\u0000" +
                    "\u0000\u0389\u038a\u0006C\uffff\uffff\u0000\u038a\u038b\u0005@\u0000\u0000" +
                    "\u038b\u038c\u0003\u0086C\u0000\u038c\u038d\u0005A\u0000\u0000\u038d\u0390" +
                    "\u0001\u0000\u0000\u0000\u038e\u0390\u0003\u00a0P\u0000\u038f\u0389\u0001" +
                    "\u0000\u0000\u0000\u038f\u038e\u0001\u0000\u0000\u0000\u0390\u0399\u0001" +
                    "\u0000\u0000\u0000\u0391\u0392\n\u0004\u0000\u0000\u0392\u0393\u0007\u0001" +
                    "\u0000\u0000\u0393\u0398\u0003\u0086C\u0005\u0394\u0395\n\u0003\u0000" +
                    "\u0000\u0395\u0396\u0007\u0002\u0000\u0000\u0396\u0398\u0003\u0086C\u0004" +
                    "\u0397\u0391\u0001\u0000\u0000\u0000\u0397\u0394\u0001\u0000\u0000\u0000" +
                    "\u0398\u039b\u0001\u0000\u0000\u0000\u0399\u0397\u0001\u0000\u0000\u0000" +
                    "\u0399\u039a\u0001\u0000\u0000\u0000\u039a\u0087\u0001\u0000\u0000\u0000" +
                    "\u039b\u0399\u0001\u0000\u0000\u0000\u039c\u039d\u0006D\uffff\uffff\u0000" +
                    "\u039d\u039e\u0003\u0086C\u0000\u039e\u039f\u0007\u0001\u0000\u0000\u039f" +
                    "\u03a0\u0003\u0088D\u0007\u03a0\u03ab\u0001\u0000\u0000\u0000\u03a1\u03a2" +
                    "\u0003\u0086C\u0000\u03a2\u03a3\u0007\u0002\u0000\u0000\u03a3\u03a4\u0003" +
                    "\u0088D\u0004\u03a4\u03ab\u0001\u0000\u0000\u0000\u03a5\u03a6\u0005@\u0000" +
                    "\u0000\u03a6\u03a7\u0003\u0088D\u0000\u03a7\u03a8\u0005A\u0000\u0000\u03a8" +
                    "\u03ab\u0001\u0000\u0000\u0000\u03a9\u03ab\u0003\u009eO\u0000\u03aa\u039c" +
                    "\u0001\u0000\u0000\u0000\u03aa\u03a1\u0001\u0000\u0000\u0000\u03aa\u03a5" +
                    "\u0001\u0000\u0000\u0000\u03aa\u03a9\u0001\u0000\u0000\u0000\u03ab\u03ba" +
                    "\u0001\u0000\u0000\u0000\u03ac\u03ad\n\b\u0000\u0000\u03ad\u03ae\u0007" +
                    "\u0001\u0000\u0000\u03ae\u03b9\u0003\u0088D\t\u03af\u03b0\n\u0005\u0000" +
                    "\u0000\u03b0\u03b1\u0007\u0002\u0000\u0000\u03b1\u03b9\u0003\u0088D\u0006" +
                    "\u03b2\u03b3\n\u0006\u0000\u0000\u03b3\u03b4\u0007\u0001\u0000\u0000\u03b4" +
                    "\u03b9\u0003\u0086C\u0000\u03b5\u03b6\n\u0003\u0000\u0000\u03b6\u03b7" +
                    "\u0007\u0002\u0000\u0000\u03b7\u03b9\u0003\u0086C\u0000\u03b8\u03ac\u0001" +
                    "\u0000\u0000\u0000\u03b8\u03af\u0001\u0000\u0000\u0000\u03b8\u03b2\u0001" +
                    "\u0000\u0000\u0000\u03b8\u03b5\u0001\u0000\u0000\u0000\u03b9\u03bc\u0001" +
                    "\u0000\u0000\u0000\u03ba\u03b8\u0001\u0000\u0000\u0000\u03ba\u03bb\u0001" +
                    "\u0000\u0000\u0000\u03bb\u0089\u0001\u0000\u0000\u0000\u03bc\u03ba\u0001" +
                    "\u0000\u0000\u0000\u03bd\u03be\u0006E\uffff\uffff\u0000\u03be\u03bf\u0003" +
                    "\u0086C\u0000\u03bf\u03c0\u0005%\u0000\u0000\u03c0\u03c1\u0003\u008aE" +
                    "\u0003\u03c1\u03c4\u0001\u0000\u0000\u0000\u03c2\u03c4\u0003\u009cN\u0000" +
                    "\u03c3\u03bd\u0001\u0000\u0000\u0000\u03c3\u03c2\u0001\u0000\u0000\u0000" +
                    "\u03c4\u03cd\u0001\u0000\u0000\u0000\u03c5\u03c6\n\u0002\u0000\u0000\u03c6" +
                    "\u03c7\u0005#\u0000\u0000\u03c7\u03cc\u0003\u008aE\u0003\u03c8\u03c9\n" +
                    "\u0004\u0000\u0000\u03c9\u03ca\u0005%\u0000\u0000\u03ca\u03cc\u0003\u0086" +
                    "C\u0000\u03cb\u03c5\u0001\u0000\u0000\u0000\u03cb\u03c8\u0001\u0000\u0000" +
                    "\u0000\u03cc\u03cf\u0001\u0000\u0000\u0000\u03cd\u03cb\u0001\u0000\u0000" +
                    "\u0000\u03cd\u03ce\u0001\u0000\u0000\u0000\u03ce\u008b\u0001\u0000\u0000" +
                    "\u0000\u03cf\u03cd\u0001\u0000\u0000\u0000\u03d0\u03d1\u0003\u008eG\u0000" +
                    "\u03d1\u03d2\u00056\u0000\u0000\u03d2\u03d3\u0003\u008cF\u0000\u03d3\u03e6" +
                    "\u0001\u0000\u0000\u0000\u03d4\u03d5\u0003\u008eG\u0000\u03d5\u03d6\u0005" +
                    "<\u0000\u0000\u03d6\u03d7\u0003\u008cF\u0000\u03d7\u03e6\u0001\u0000\u0000" +
                    "\u0000\u03d8\u03d9\u0003\u008eG\u0000\u03d9\u03da\u00057\u0000\u0000\u03da" +
                    "\u03db\u0003\u008cF\u0000\u03db\u03e6\u0001\u0000\u0000\u0000\u03dc\u03dd" +
                    "\u0003\u008eG\u0000\u03dd\u03de\u0005=\u0000\u0000\u03de\u03df\u0003\u008c" +
                    "F\u0000\u03df\u03e6\u0001\u0000\u0000\u0000\u03e0\u03e1\u0005@\u0000\u0000" +
                    "\u03e1\u03e2\u0003\u008cF\u0000\u03e2\u03e3\u0005A\u0000\u0000\u03e3\u03e6" +
                    "\u0001\u0000\u0000\u0000\u03e4\u03e6\u0003\u008eG\u0000\u03e5\u03d0\u0001" +
                    "\u0000\u0000\u0000\u03e5\u03d4\u0001\u0000\u0000\u0000\u03e5\u03d8\u0001" +
                    "\u0000\u0000\u0000\u03e5\u03dc\u0001\u0000\u0000\u0000\u03e5\u03e0\u0001" +
                    "\u0000\u0000\u0000\u03e5\u03e4\u0001\u0000\u0000\u0000\u03e6\u008d\u0001" +
                    "\u0000\u0000\u0000\u03e7\u03e8\u0003\u0090H\u0000\u03e8\u03e9\u0007\u0003" +
                    "\u0000\u0000\u03e9\u03ea\u0003\u0090H\u0000\u03ea\u03f0\u0001\u0000\u0000" +
                    "\u0000\u03eb\u03ec\u0003\u0090H\u0000\u03ec\u03ed\u0007\u0004\u0000\u0000" +
                    "\u03ed\u03ee\u0003\u0090H\u0000\u03ee\u03f0\u0001\u0000\u0000\u0000\u03ef" +
                    "\u03e7\u0001\u0000\u0000\u0000\u03ef\u03eb\u0001\u0000\u0000\u0000\u03f0" +
                    "\u008f\u0001\u0000\u0000\u0000\u03f1\u03f6\u0003P(\u0000\u03f2\u03f6\u0003" +
                    "~?\u0000\u03f3\u03f6\u0003\u00a6S\u0000\u03f4\u03f6\u0003\u00acV\u0000" +
                    "\u03f5\u03f1\u0001\u0000\u0000\u0000\u03f5\u03f2\u0001\u0000\u0000\u0000" +
                    "\u03f5\u03f3\u0001\u0000\u0000\u0000\u03f5\u03f4\u0001\u0000\u0000\u0000" +
                    "\u03f6\u0091\u0001\u0000\u0000\u0000\u03f7\u03fb\u0003\u00acV\u0000\u03f8" +
                    "\u03fb\u0003\u00a8T\u0000\u03f9\u03fb\u0003\u00a6S\u0000\u03fa\u03f7\u0001" +
                    "\u0000\u0000\u0000\u03fa\u03f8\u0001\u0000\u0000\u0000\u03fa\u03f9\u0001" +
                    "\u0000\u0000\u0000\u03fb\u0093\u0001\u0000\u0000\u0000\u03fc\u03fd\u0006" +
                    "J\uffff\uffff\u0000\u03fd\u0419\u0003\u0092I\u0000\u03fe\u0419\u0003v" +
                    ";\u0000\u03ff\u0419\u0003x<\u0000\u0400\u0419\u0003\u0086C\u0000\u0401" +
                    "\u0419\u0003\u0088D\u0000\u0402\u0419\u0003\u008aE\u0000\u0403\u0419\u0003" +
                    "\u001a\r\u0000\u0404\u0419\u0003\u0018\f\u0000\u0405\u0419\u0003 \u0010" +
                    "\u0000\u0406\u0419\u0003\u001e\u000f\u0000\u0407\u0419\u0003n7\u0000\u0408" +
                    "\u0419\u0003t:\u0000\u0409\u0419\u0003r9\u0000\u040a\u0419\u0003p8\u0000" +
                    "\u040b\u0419\u0003l6\u0000\u040c\u0419\u0003B!\u0000\u040d\u0419\u0003" +
                    "\u009cN\u0000\u040e\u0419\u0003\u00a2Q\u0000\u040f\u0419\u0003\u009eO" +
                    "\u0000\u0410\u0419\u0003\u00a0P\u0000\u0411\u0419\u0003\u00a4R\u0000\u0412" +
                    "\u0413\u0007\u0005\u0000\u0000\u0413\u0419\u0003\u0094J\n\u0414\u0415" +
                    "\u0005@\u0000\u0000\u0415\u0416\u0003\u0094J\u0000\u0416\u0417\u0005A" +
                    "\u0000\u0000\u0417\u0419\u0001\u0000\u0000\u0000\u0418\u03fc\u0001\u0000" +
                    "\u0000\u0000\u0418\u03fe\u0001\u0000\u0000\u0000\u0418\u03ff\u0001\u0000" +
                    "\u0000\u0000\u0418\u0400\u0001\u0000\u0000\u0000\u0418\u0401\u0001\u0000" +
                    "\u0000\u0000\u0418\u0402\u0001\u0000\u0000\u0000\u0418\u0403\u0001\u0000" +
                    "\u0000\u0000\u0418\u0404\u0001\u0000\u0000\u0000\u0418\u0405\u0001\u0000" +
                    "\u0000\u0000\u0418\u0406\u0001\u0000\u0000\u0000\u0418\u0407\u0001\u0000" +
                    "\u0000\u0000\u0418\u0408\u0001\u0000\u0000\u0000\u0418\u0409\u0001\u0000" +
                    "\u0000\u0000\u0418\u040a\u0001\u0000\u0000\u0000\u0418\u040b\u0001\u0000" +
                    "\u0000\u0000\u0418\u040c\u0001\u0000\u0000\u0000\u0418\u040d\u0001\u0000" +
                    "\u0000\u0000\u0418\u040e\u0001\u0000\u0000\u0000\u0418\u040f\u0001\u0000" +
                    "\u0000\u0000\u0418\u0410\u0001\u0000\u0000\u0000\u0418\u0411\u0001\u0000" +
                    "\u0000\u0000\u0418\u0412\u0001\u0000\u0000\u0000\u0418\u0414\u0001\u0000" +
                    "\u0000\u0000\u0419\u0437\u0001\u0000\u0000\u0000\u041a\u041b\n\u000b\u0000" +
                    "\u0000\u041b\u041c\u0005(\u0000\u0000\u041c\u0436\u0003\u0094J\f\u041d" +
                    "\u041e\n\t\u0000\u0000\u041e\u041f\u0007\u0001\u0000\u0000\u041f\u0436" +
                    "\u0003\u0094J\n\u0420\u0421\n\b\u0000\u0000\u0421\u0422\u0007\u0002\u0000" +
                    "\u0000\u0422\u0436\u0003\u0094J\t\u0423\u0424\n\u0007\u0000\u0000\u0424" +
                    "\u0425\u0007\u0006\u0000\u0000\u0425\u0436\u0003\u0094J\b\u0426\u0427" +
                    "\n\u0006\u0000\u0000\u0427\u0428\u00056\u0000\u0000\u0428\u0436\u0003" +
                    "\u0094J\u0007\u0429\u042a\n\u0005\u0000\u0000\u042a\u042b\u0007\u0007" +
                    "\u0000\u0000\u042b\u0436\u0003\u0094J\u0006\u042c\u042d\n\u0004\u0000" +
                    "\u0000\u042d\u042e\u0007\u0003\u0000\u0000\u042e\u0436\u0003\u0094J\u0005" +
                    "\u042f\u0430\n\u0003\u0000\u0000\u0430\u0431\u0007\u0004\u0000\u0000\u0431" +
                    "\u0436\u0003\u0094J\u0004\u0432\u0433\n\u0002\u0000\u0000\u0433\u0434" +
                    "\u0007\b\u0000\u0000\u0434\u0436\u0003\u0094J\u0003\u0435\u041a\u0001" +
                    "\u0000\u0000\u0000\u0435\u041d\u0001\u0000\u0000\u0000\u0435\u0420\u0001" +
                    "\u0000\u0000\u0000\u0435\u0423\u0001\u0000\u0000\u0000\u0435\u0426\u0001" +
                    "\u0000\u0000\u0000\u0435\u0429\u0001\u0000\u0000\u0000\u0435\u042c\u0001" +
                    "\u0000\u0000\u0000\u0435\u042f\u0001\u0000\u0000\u0000\u0435\u0432\u0001" +
                    "\u0000\u0000\u0000\u0436\u0439\u0001\u0000\u0000\u0000\u0437\u0435\u0001" +
                    "\u0000\u0000\u0000\u0437\u0438\u0001\u0000\u0000\u0000\u0438\u0095\u0001" +
                    "\u0000\u0000\u0000\u0439\u0437\u0001\u0000\u0000\u0000\u043a\u043b\u0005" +
                    "\u0019\u0000\u0000\u043b\u0097\u0001\u0000\u0000\u0000\u043c\u043d\u0005" +
                    "\u0011\u0000\u0000\u043d\u043e\u0003\u0092I\u0000\u043e\u0099\u0001\u0000" +
                    "\u0000\u0000\u043f\u0440\u0005!\u0000\u0000\u0440\u009b\u0001\u0000\u0000" +
                    "\u0000\u0441\u0442\u0005\u0004\u0000\u0000\u0442\u009d\u0001\u0000\u0000" +
                    "\u0000\u0443\u0444\u0005K\u0000\u0000\u0444\u009f\u0001\u0000\u0000\u0000" +
                    "\u0445\u0446\u0005J\u0000\u0000\u0446\u00a1\u0001\u0000\u0000\u0000\u0447" +
                    "\u0448\u0007\t\u0000\u0000\u0448\u00a3\u0001\u0000\u0000\u0000\u0449\u044a" +
                    "\u0005F\u0000\u0000\u044a\u00a5\u0001\u0000\u0000\u0000\u044b\u044c\u0005" +
                    "L\u0000\u0000\u044c\u00a7\u0001\u0000\u0000\u0000\u044d\u044e\u0005N\u0000" +
                    "\u0000\u044e\u00a9\u0001\u0000\u0000\u0000\u044f\u0450\u0005O\u0000\u0000" +
                    "\u0450\u00ab\u0001\u0000\u0000\u0000\u0451\u0452\u0005M\u0000\u0000\u0452" +
                    "\u00ad\u0001\u0000\u0000\u0000\u0453\u0454\u0005P\u0000\u0000\u0454\u00af" +
                    "\u0001\u0000\u0000\u0000\u0455\u0456\u0006X\uffff\uffff\u0000\u0456\u0459" +
                    "\u0005\u0007\u0000\u0000\u0457\u0459\u0003\u00b4Z\u0000\u0458\u0455\u0001" +
                    "\u0000\u0000\u0000\u0458\u0457\u0001\u0000\u0000\u0000\u0459\u0460\u0001" +
                    "\u0000\u0000\u0000\u045a\u045b\n\u0004\u0000\u0000\u045b\u045f\u0005\u0007" +
                    "\u0000\u0000\u045c\u045d\n\u0003\u0000\u0000\u045d\u045f\u0003\u00b4Z" +
                    "\u0000\u045e\u045a\u0001\u0000\u0000\u0000\u045e\u045c\u0001\u0000\u0000" +
                    "\u0000\u045f\u0462\u0001\u0000\u0000\u0000\u0460\u045e\u0001\u0000\u0000" +
                    "\u0000\u0460\u0461\u0001\u0000\u0000\u0000\u0461\u00b1\u0001\u0000\u0000" +
                    "\u0000\u0462\u0460\u0001\u0000\u0000\u0000\u0463\u0464\u0005\u0014\u0000" +
                    "\u0000\u0464\u00b3\u0001\u0000\u0000\u0000\u0465\u0466\u0005\b\u0000\u0000" +
                    "\u0466\u00b5\u0001\u0000\u0000\u0000f\u00be\u00c6\u00db\u00e2\u00ea\u00f1" +
                    "\u00f8\u0100\u0107\u010f\u0117\u011f\u0126\u012c\u0131\u0138\u013f\u0145" +
                    "\u014a\u017c\u0181\u0185\u018a\u0193\u01a7\u01ab\u01b4\u01be\u01d7\u01df" +
                    "\u01e6\u01ee\u01f0\u01fc\u0201\u0207\u020f\u0219\u0220\u0228\u0233\u0239" +
                    "\u023e\u0247\u0253\u025a\u0272\u027e\u0286\u028c\u0291\u0298\u029f\u02a5" +
                    "\u02b9\u02bd\u02c1\u02c5\u02c9\u02cb\u02d0\u02d5\u02da\u02dc\u02de\u02e9" +
                    "\u02f3\u02fd\u0307\u0311\u0321\u0326\u032c\u0332\u0334\u0336\u033d\u0345" +
                    "\u0349\u0366\u037d\u037f\u0387\u038f\u0397\u0399\u03aa\u03b8\u03ba\u03c3" +
                    "\u03cb\u03cd\u03e5\u03ef\u03f5\u03fa\u0418\u0435\u0437\u0458\u045e\u0460";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}