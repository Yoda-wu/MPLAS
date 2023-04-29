// Generated from D:/Application/IDEA/Project/mplas/core/src/main/java/com/scut/mplas/ruby/parser\Ruby.g4 by ANTLR 4.12.0
package com.scut.mplas.ruby.parser.old;

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
            LITERAL = 1, COMMA = 2, SEMICOLON = 3, CRLF = 4, REQUIRE = 5, END = 6, DEF = 7, RETURN = 8,
            PIR = 9, IF = 10, ELSE = 11, ELSIF = 12, UNLESS = 13, WHILE = 14, RETRY = 15, BREAK = 16,
            FOR = 17, TRUE = 18, FALSE = 19, PLUS = 20, MINUS = 21, MUL = 22, DIV = 23, MOD = 24,
            EXP = 25, EQUAL = 26, NOT_EQUAL = 27, GREATER = 28, LESS = 29, LESS_EQUAL = 30, GREATER_EQUAL = 31,
            ASSIGN = 32, PLUS_ASSIGN = 33, MINUS_ASSIGN = 34, MUL_ASSIGN = 35, DIV_ASSIGN = 36,
            MOD_ASSIGN = 37, EXP_ASSIGN = 38, BIT_AND = 39, BIT_OR = 40, BIT_XOR = 41, BIT_NOT = 42,
            BIT_SHL = 43, BIT_SHR = 44, AND = 45, OR = 46, NOT = 47, LEFT_RBRACKET = 48, RIGHT_RBRACKET = 49,
            LEFT_SBRACKET = 50, RIGHT_SBRACKET = 51, NIL = 52, SL_COMMENT = 53, ML_COMMENT = 54,
            WS = 55, INT = 56, FLOAT = 57, ID = 58, ID_GLOBAL = 59, ID_FUNCTION = 60;
    public static final int
            RULE_prog = 0, RULE_expression_list = 1, RULE_expression = 2, RULE_global_get = 3,
            RULE_global_set = 4, RULE_global_result = 5, RULE_function_inline_call = 6,
            RULE_require_block = 7, RULE_pir_inline = 8, RULE_pir_expression_list = 9,
            RULE_function_definition = 10, RULE_function_definition_body = 11, RULE_function_definition_header = 12,
            RULE_function_name = 13, RULE_function_definition_params = 14, RULE_function_definition_params_list = 15,
            RULE_function_definition_param_id = 16, RULE_return_statement = 17, RULE_function_call = 18,
            RULE_function_call_param_list = 19, RULE_function_call_params = 20, RULE_function_param = 21,
            RULE_function_unnamed_param = 22, RULE_function_named_param = 23, RULE_function_call_assignment = 24,
            RULE_all_result = 25, RULE_elsif_statement = 26, RULE_if_elsif_statement = 27,
            RULE_if_statement = 28, RULE_unless_statement = 29, RULE_while_statement = 30,
            RULE_for_statement = 31, RULE_init_expression = 32, RULE_all_assignment = 33,
            RULE_for_init_list = 34, RULE_cond_expression = 35, RULE_loop_expression = 36,
            RULE_for_loop_list = 37, RULE_statement_body = 38, RULE_statement_expression_list = 39,
            RULE_assignment = 40, RULE_dynamic_assignment = 41, RULE_int_assignment = 42,
            RULE_float_assignment = 43, RULE_string_assignment = 44, RULE_initial_array_assignment = 45,
            RULE_array_assignment = 46, RULE_array_definition = 47, RULE_array_definition_elements = 48,
            RULE_array_selector = 49, RULE_dynamic_result = 50, RULE_dynamic_ = 51,
            RULE_int_result = 52, RULE_float_result = 53, RULE_string_result = 54,
            RULE_comparison_list = 55, RULE_comparison = 56, RULE_comp_var = 57, RULE_lvalue = 58,
            RULE_rvalue = 59, RULE_break_expression = 60, RULE_literal_t = 61, RULE_float_t = 62,
            RULE_int_t = 63, RULE_bool_t = 64, RULE_nil_t = 65, RULE_id_ = 66, RULE_id_global = 67,
            RULE_id_function = 68, RULE_terminator = 69, RULE_else_token = 70, RULE_crlf = 71;

    private static String[] makeRuleNames() {
        return new String[]{
                "prog", "expression_list", "expression", "global_get", "global_set",
                "global_result", "function_inline_call", "require_block", "pir_inline",
                "pir_expression_list", "function_definition", "function_definition_body",
                "function_definition_header", "function_name", "function_definition_params",
                "function_definition_params_list", "function_definition_param_id", "return_statement",
                "function_call", "function_call_param_list", "function_call_params",
                "function_param", "function_unnamed_param", "function_named_param", "function_call_assignment",
                "all_result", "elsif_statement", "if_elsif_statement", "if_statement",
                "unless_statement", "while_statement", "for_statement", "init_expression",
                "all_assignment", "for_init_list", "cond_expression", "loop_expression",
                "for_loop_list", "statement_body", "statement_expression_list", "assignment",
                "dynamic_assignment", "int_assignment", "float_assignment", "string_assignment",
                "initial_array_assignment", "array_assignment", "array_definition", "array_definition_elements",
                "array_selector", "dynamic_result", "dynamic_", "int_result", "float_result",
                "string_result", "comparison_list", "comparison", "comp_var", "lvalue",
                "rvalue", "break_expression", "literal_t", "float_t", "int_t", "bool_t",
                "nil_t", "id_", "id_global", "id_function", "terminator", "else_token",
                "crlf"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, null, "','", "';'", null, "'require'", "'end'", "'def'", "'return'",
                "'pir'", "'if'", "'else'", "'elsif'", "'unless'", "'while'", "'retry'",
                "'break'", "'for'", "'true'", "'false'", "'+'", "'-'", "'*'", "'/'",
                "'%'", "'**'", "'=='", "'!='", "'>'", "'<'", "'<='", "'>='", "'='", "'+='",
                "'-='", "'*='", "'/='", "'%='", "'**='", "'&'", "'|'", "'^'", "'~'",
                "'<<'", "'>>'", null, null, null, "'('", "')'", "'['", "']'", "'nil'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "LITERAL", "COMMA", "SEMICOLON", "CRLF", "REQUIRE", "END", "DEF",
                "RETURN", "PIR", "IF", "ELSE", "ELSIF", "UNLESS", "WHILE", "RETRY", "BREAK",
                "FOR", "TRUE", "FALSE", "PLUS", "MINUS", "MUL", "DIV", "MOD", "EXP",
                "EQUAL", "NOT_EQUAL", "GREATER", "LESS", "LESS_EQUAL", "GREATER_EQUAL",
                "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN",
                "MOD_ASSIGN", "EXP_ASSIGN", "BIT_AND", "BIT_OR", "BIT_XOR", "BIT_NOT",
                "BIT_SHL", "BIT_SHR", "AND", "OR", "NOT", "LEFT_RBRACKET", "RIGHT_RBRACKET",
                "LEFT_SBRACKET", "RIGHT_SBRACKET", "NIL", "SL_COMMENT", "ML_COMMENT",
                "WS", "INT", "FLOAT", "ID", "ID_GLOBAL", "ID_FUNCTION"
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
                setState(144);
                expression_list(0);
                setState(145);
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
                setState(152);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LITERAL:
                    case REQUIRE:
                    case DEF:
                    case RETURN:
                    case PIR:
                    case IF:
                    case UNLESS:
                    case WHILE:
                    case FOR:
                    case TRUE:
                    case FALSE:
                    case BIT_NOT:
                    case NOT:
                    case LEFT_RBRACKET:
                    case NIL:
                    case INT:
                    case FLOAT:
                    case ID:
                    case ID_GLOBAL:
                    case ID_FUNCTION: {
                        setState(148);
                        expression();
                        setState(149);
                        terminator(0);
                    }
                    break;
                    case SEMICOLON:
                    case CRLF: {
                        setState(151);
                        terminator(0);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(160);
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
                                setState(154);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(155);
                                expression();
                                setState(156);
                                terminator(0);
                            }
                        }
                    }
                    setState(162);
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

        public Return_statementContext return_statement() {
            return getRuleContext(Return_statementContext.class, 0);
        }

        public While_statementContext while_statement() {
            return getRuleContext(While_statementContext.class, 0);
        }

        public For_statementContext for_statement() {
            return getRuleContext(For_statementContext.class, 0);
        }

        public Pir_inlineContext pir_inline() {
            return getRuleContext(Pir_inlineContext.class, 0);
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
            setState(173);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(163);
                    function_definition();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(164);
                    function_inline_call();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(165);
                    require_block();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(166);
                    if_statement();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(167);
                    unless_statement();
                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(168);
                    rvalue(0);
                }
                break;
                case 7:
                    enterOuterAlt(_localctx, 7);
                {
                    setState(169);
                    return_statement();
                }
                break;
                case 8:
                    enterOuterAlt(_localctx, 8);
                {
                    setState(170);
                    while_statement();
                }
                break;
                case 9:
                    enterOuterAlt(_localctx, 9);
                {
                    setState(171);
                    for_statement();
                }
                break;
                case 10:
                    enterOuterAlt(_localctx, 10);
                {
                    setState(172);
                    pir_inline();
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
        enterRule(_localctx, 6, RULE_global_get);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(175);
                ((Global_getContext) _localctx).var_name = lvalue();
                setState(176);
                ((Global_getContext) _localctx).op = match(ASSIGN);
                setState(177);
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
        enterRule(_localctx, 8, RULE_global_set);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(179);
                ((Global_setContext) _localctx).global_name = id_global();
                setState(180);
                ((Global_setContext) _localctx).op = match(ASSIGN);
                setState(181);
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
        enterRule(_localctx, 10, RULE_global_result);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(183);
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
        enterRule(_localctx, 12, RULE_function_inline_call);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(185);
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
        enterRule(_localctx, 14, RULE_require_block);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(187);
                match(REQUIRE);
                setState(188);
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
        enterRule(_localctx, 16, RULE_pir_inline);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(190);
                match(PIR);
                setState(191);
                crlf();
                setState(192);
                pir_expression_list();
                setState(193);
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
        enterRule(_localctx, 18, RULE_pir_expression_list);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(195);
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
        enterRule(_localctx, 20, RULE_function_definition);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(197);
                function_definition_header();
                setState(198);
                function_definition_body();
                setState(199);
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
        enterRule(_localctx, 22, RULE_function_definition_body);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(201);
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
        enterRule(_localctx, 24, RULE_function_definition_header);
        try {
            setState(212);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(203);
                    match(DEF);
                    setState(204);
                    function_name();
                    setState(205);
                    crlf();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(207);
                    match(DEF);
                    setState(208);
                    function_name();
                    setState(209);
                    function_definition_params();
                    setState(210);
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
        enterRule(_localctx, 26, RULE_function_name);
        try {
            setState(216);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID_FUNCTION:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(214);
                    id_function();
                }
                break;
                case ID:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(215);
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
        enterRule(_localctx, 28, RULE_function_definition_params);
        try {
            setState(225);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(218);
                    match(LEFT_RBRACKET);
                    setState(219);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(220);
                    match(LEFT_RBRACKET);
                    setState(221);
                    function_definition_params_list(0);
                    setState(222);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(224);
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
        int _startState = 30;
        enterRecursionRule(_localctx, 30, RULE_function_definition_params_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(228);
                    function_definition_param_id();
                }
                _ctx.stop = _input.LT(-1);
                setState(235);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new Function_definition_params_listContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_function_definition_params_list);
                                setState(230);
                                if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                setState(231);
                                match(COMMA);
                                setState(232);
                                function_definition_param_id();
                            }
                        }
                    }
                    setState(237);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
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
        enterRule(_localctx, 32, RULE_function_definition_param_id);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(238);
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
        enterRule(_localctx, 34, RULE_return_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(240);
                match(RETURN);
                setState(241);
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
        enterRule(_localctx, 36, RULE_function_call);
        try {
            setState(255);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(243);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(244);
                    match(LEFT_RBRACKET);
                    setState(245);
                    ((Function_callContext) _localctx).params = function_call_param_list();
                    setState(246);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(248);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(249);
                    ((Function_callContext) _localctx).params = function_call_param_list();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(251);
                    ((Function_callContext) _localctx).name = function_name();
                    setState(252);
                    match(LEFT_RBRACKET);
                    setState(253);
                    match(RIGHT_RBRACKET);
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
        enterRule(_localctx, 38, RULE_function_call_param_list);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(257);
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
        int _startState = 40;
        enterRecursionRule(_localctx, 40, RULE_function_call_params, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(260);
                    function_param();
                }
                _ctx.stop = _input.LT(-1);
                setState(267);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new Function_call_paramsContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_function_call_params);
                                setState(262);
                                if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                setState(263);
                                match(COMMA);
                                setState(264);
                                function_param();
                            }
                        }
                    }
                    setState(269);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
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
        enterRule(_localctx, 42, RULE_function_param);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(272);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                    case 1: {
                        setState(270);
                        function_unnamed_param();
                    }
                    break;
                    case 2: {
                        setState(271);
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
        enterRule(_localctx, 44, RULE_function_unnamed_param);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(278);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
                    case 1: {
                        setState(274);
                        int_result(0);
                    }
                    break;
                    case 2: {
                        setState(275);
                        float_result(0);
                    }
                    break;
                    case 3: {
                        setState(276);
                        string_result(0);
                    }
                    break;
                    case 4: {
                        setState(277);
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
        enterRule(_localctx, 46, RULE_function_named_param);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(280);
                id_();
                setState(281);
                ((Function_named_paramContext) _localctx).op = match(ASSIGN);
                setState(286);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                    case 1: {
                        setState(282);
                        int_result(0);
                    }
                    break;
                    case 2: {
                        setState(283);
                        float_result(0);
                    }
                    break;
                    case 3: {
                        setState(284);
                        string_result(0);
                    }
                    break;
                    case 4: {
                        setState(285);
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
        enterRule(_localctx, 48, RULE_function_call_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(288);
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
        enterRule(_localctx, 50, RULE_all_result);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(295);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                    case 1: {
                        setState(290);
                        int_result(0);
                    }
                    break;
                    case 2: {
                        setState(291);
                        float_result(0);
                    }
                    break;
                    case 3: {
                        setState(292);
                        string_result(0);
                    }
                    break;
                    case 4: {
                        setState(293);
                        dynamic_result(0);
                    }
                    break;
                    case 5: {
                        setState(294);
                        global_result();
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
        enterRule(_localctx, 52, RULE_elsif_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(297);
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
        enterRule(_localctx, 54, RULE_if_elsif_statement);
        try {
            setState(318);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(299);
                    match(ELSIF);
                    setState(300);
                    cond_expression();
                    setState(301);
                    crlf();
                    setState(302);
                    statement_body();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(304);
                    match(ELSIF);
                    setState(305);
                    cond_expression();
                    setState(306);
                    crlf();
                    setState(307);
                    statement_body();
                    setState(308);
                    else_token();
                    setState(309);
                    crlf();
                    setState(310);
                    statement_body();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(312);
                    match(ELSIF);
                    setState(313);
                    cond_expression();
                    setState(314);
                    crlf();
                    setState(315);
                    statement_body();
                    setState(316);
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
        enterRule(_localctx, 56, RULE_if_statement);
        try {
            setState(342);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(320);
                    match(IF);
                    setState(321);
                    cond_expression();
                    setState(322);
                    crlf();
                    setState(323);
                    statement_body();
                    setState(324);
                    match(END);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(326);
                    match(IF);
                    setState(327);
                    cond_expression();
                    setState(328);
                    crlf();
                    setState(329);
                    statement_body();
                    setState(330);
                    else_token();
                    setState(331);
                    crlf();
                    setState(332);
                    statement_body();
                    setState(333);
                    match(END);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(335);
                    match(IF);
                    setState(336);
                    cond_expression();
                    setState(337);
                    crlf();
                    setState(338);
                    statement_body();
                    setState(339);
                    elsif_statement();
                    setState(340);
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
        enterRule(_localctx, 58, RULE_unless_statement);
        try {
            setState(366);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(344);
                    match(UNLESS);
                    setState(345);
                    cond_expression();
                    setState(346);
                    crlf();
                    setState(347);
                    statement_body();
                    setState(348);
                    match(END);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(350);
                    match(UNLESS);
                    setState(351);
                    cond_expression();
                    setState(352);
                    crlf();
                    setState(353);
                    statement_body();
                    setState(354);
                    else_token();
                    setState(355);
                    crlf();
                    setState(356);
                    statement_body();
                    setState(357);
                    match(END);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(359);
                    match(UNLESS);
                    setState(360);
                    cond_expression();
                    setState(361);
                    crlf();
                    setState(362);
                    statement_body();
                    setState(363);
                    elsif_statement();
                    setState(364);
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
        enterRule(_localctx, 60, RULE_while_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(368);
                match(WHILE);
                setState(369);
                cond_expression();
                setState(370);
                crlf();
                setState(371);
                statement_body();
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
    public static class For_statementContext extends ParserRuleContext {
        public TerminalNode FOR() {
            return getToken(RubyParser.FOR, 0);
        }

        public TerminalNode LEFT_RBRACKET() {
            return getToken(RubyParser.LEFT_RBRACKET, 0);
        }

        public Init_expressionContext init_expression() {
            return getRuleContext(Init_expressionContext.class, 0);
        }

        public List<TerminalNode> SEMICOLON() {
            return getTokens(RubyParser.SEMICOLON);
        }

        public TerminalNode SEMICOLON(int i) {
            return getToken(RubyParser.SEMICOLON, i);
        }

        public Cond_expressionContext cond_expression() {
            return getRuleContext(Cond_expressionContext.class, 0);
        }

        public Loop_expressionContext loop_expression() {
            return getRuleContext(Loop_expressionContext.class, 0);
        }

        public TerminalNode RIGHT_RBRACKET() {
            return getToken(RubyParser.RIGHT_RBRACKET, 0);
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
        enterRule(_localctx, 62, RULE_for_statement);
        try {
            setState(396);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(374);
                    match(FOR);
                    setState(375);
                    match(LEFT_RBRACKET);
                    setState(376);
                    init_expression();
                    setState(377);
                    match(SEMICOLON);
                    setState(378);
                    cond_expression();
                    setState(379);
                    match(SEMICOLON);
                    setState(380);
                    loop_expression();
                    setState(381);
                    match(RIGHT_RBRACKET);
                    setState(382);
                    crlf();
                    setState(383);
                    statement_body();
                    setState(384);
                    match(END);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(386);
                    match(FOR);
                    setState(387);
                    init_expression();
                    setState(388);
                    match(SEMICOLON);
                    setState(389);
                    cond_expression();
                    setState(390);
                    match(SEMICOLON);
                    setState(391);
                    loop_expression();
                    setState(392);
                    crlf();
                    setState(393);
                    statement_body();
                    setState(394);
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
    public static class Init_expressionContext extends ParserRuleContext {
        public For_init_listContext for_init_list() {
            return getRuleContext(For_init_listContext.class, 0);
        }

        public Init_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_init_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterInit_expression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitInit_expression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitInit_expression(this);
            else return visitor.visitChildren(this);
        }
    }

    public final Init_expressionContext init_expression() throws RecognitionException {
        Init_expressionContext _localctx = new Init_expressionContext(_ctx, getState());
        enterRule(_localctx, 64, RULE_init_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(398);
                for_init_list(0);
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
        enterRule(_localctx, 66, RULE_all_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(404);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                    case 1: {
                        setState(400);
                        int_assignment();
                    }
                    break;
                    case 2: {
                        setState(401);
                        float_assignment();
                    }
                    break;
                    case 3: {
                        setState(402);
                        string_assignment();
                    }
                    break;
                    case 4: {
                        setState(403);
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
    public static class For_init_listContext extends ParserRuleContext {
        public All_assignmentContext all_assignment() {
            return getRuleContext(All_assignmentContext.class, 0);
        }

        public For_init_listContext for_init_list() {
            return getRuleContext(For_init_listContext.class, 0);
        }

        public TerminalNode COMMA() {
            return getToken(RubyParser.COMMA, 0);
        }

        public For_init_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_for_init_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFor_init_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFor_init_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFor_init_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final For_init_listContext for_init_list() throws RecognitionException {
        return for_init_list(0);
    }

    private For_init_listContext for_init_list(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        For_init_listContext _localctx = new For_init_listContext(_ctx, _parentState);
        For_init_listContext _prevctx = _localctx;
        int _startState = 68;
        enterRecursionRule(_localctx, 68, RULE_for_init_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(407);
                    all_assignment();
                }
                _ctx.stop = _input.LT(-1);
                setState(414);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new For_init_listContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_for_init_list);
                                setState(409);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(410);
                                match(COMMA);
                                setState(411);
                                all_assignment();
                            }
                        }
                    }
                    setState(416);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
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
        enterRule(_localctx, 70, RULE_cond_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(417);
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
        public For_loop_listContext for_loop_list() {
            return getRuleContext(For_loop_listContext.class, 0);
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
        enterRule(_localctx, 72, RULE_loop_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(419);
                for_loop_list(0);
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
    public static class For_loop_listContext extends ParserRuleContext {
        public All_assignmentContext all_assignment() {
            return getRuleContext(All_assignmentContext.class, 0);
        }

        public For_loop_listContext for_loop_list() {
            return getRuleContext(For_loop_listContext.class, 0);
        }

        public TerminalNode COMMA() {
            return getToken(RubyParser.COMMA, 0);
        }

        public For_loop_listContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_for_loop_list;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).enterFor_loop_list(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof RubyListener) ((RubyListener) listener).exitFor_loop_list(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof RubyVisitor) return ((RubyVisitor<? extends T>) visitor).visitFor_loop_list(this);
            else return visitor.visitChildren(this);
        }
    }

    public final For_loop_listContext for_loop_list() throws RecognitionException {
        return for_loop_list(0);
    }

    private For_loop_listContext for_loop_list(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        For_loop_listContext _localctx = new For_loop_listContext(_ctx, _parentState);
        For_loop_listContext _prevctx = _localctx;
        int _startState = 74;
        enterRecursionRule(_localctx, 74, RULE_for_loop_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(422);
                    all_assignment();
                }
                _ctx.stop = _input.LT(-1);
                setState(429);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new For_loop_listContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_for_loop_list);
                                setState(424);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(425);
                                match(COMMA);
                                setState(426);
                                all_assignment();
                            }
                        }
                    }
                    setState(431);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
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
        enterRule(_localctx, 76, RULE_statement_body);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(432);
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
        int _startState = 78;
        enterRecursionRule(_localctx, 78, RULE_statement_expression_list, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(443);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LITERAL:
                    case REQUIRE:
                    case DEF:
                    case RETURN:
                    case PIR:
                    case IF:
                    case UNLESS:
                    case WHILE:
                    case FOR:
                    case TRUE:
                    case FALSE:
                    case BIT_NOT:
                    case NOT:
                    case LEFT_RBRACKET:
                    case NIL:
                    case INT:
                    case FLOAT:
                    case ID:
                    case ID_GLOBAL:
                    case ID_FUNCTION: {
                        setState(435);
                        expression();
                        setState(436);
                        terminator(0);
                    }
                    break;
                    case RETRY: {
                        setState(438);
                        match(RETRY);
                        setState(439);
                        terminator(0);
                    }
                    break;
                    case BREAK: {
                        setState(440);
                        break_expression();
                        setState(441);
                        terminator(0);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(458);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(456);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
                                case 1: {
                                    _localctx = new Statement_expression_listContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_statement_expression_list);
                                    setState(445);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(446);
                                    expression();
                                    setState(447);
                                    terminator(0);
                                }
                                break;
                                case 2: {
                                    _localctx = new Statement_expression_listContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_statement_expression_list);
                                    setState(449);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(450);
                                    match(RETRY);
                                    setState(451);
                                    terminator(0);
                                }
                                break;
                                case 3: {
                                    _localctx = new Statement_expression_listContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_statement_expression_list);
                                    setState(452);
                                    if (!(precpred(_ctx, 1)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                    setState(453);
                                    break_expression();
                                    setState(454);
                                    terminator(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(460);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
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
        enterRule(_localctx, 80, RULE_assignment);
        int _la;
        try {
            setState(469);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(461);
                    ((AssignmentContext) _localctx).var_id = lvalue();
                    setState(462);
                    ((AssignmentContext) _localctx).op = match(ASSIGN);
                    setState(463);
                    rvalue(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(465);
                    ((AssignmentContext) _localctx).var_id = lvalue();
                    setState(466);
                    ((AssignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 541165879296L) != 0))) {
                        ((AssignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(467);
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
        enterRule(_localctx, 82, RULE_dynamic_assignment);
        int _la;
        try {
            setState(479);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 24, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(471);
                    ((Dynamic_assignmentContext) _localctx).var_id = lvalue();
                    setState(472);
                    ((Dynamic_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(473);
                    dynamic_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(475);
                    ((Dynamic_assignmentContext) _localctx).var_id = lvalue();
                    setState(476);
                    ((Dynamic_assignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 541165879296L) != 0))) {
                        ((Dynamic_assignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(477);
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
        enterRule(_localctx, 84, RULE_int_assignment);
        int _la;
        try {
            setState(489);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 25, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(481);
                    ((Int_assignmentContext) _localctx).var_id = lvalue();
                    setState(482);
                    ((Int_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(483);
                    int_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(485);
                    ((Int_assignmentContext) _localctx).var_id = lvalue();
                    setState(486);
                    ((Int_assignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 541165879296L) != 0))) {
                        ((Int_assignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(487);
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
        enterRule(_localctx, 86, RULE_float_assignment);
        int _la;
        try {
            setState(499);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(491);
                    ((Float_assignmentContext) _localctx).var_id = lvalue();
                    setState(492);
                    ((Float_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(493);
                    float_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(495);
                    ((Float_assignmentContext) _localctx).var_id = lvalue();
                    setState(496);
                    ((Float_assignmentContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 541165879296L) != 0))) {
                        ((Float_assignmentContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(497);
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
        enterRule(_localctx, 88, RULE_string_assignment);
        try {
            setState(509);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(501);
                    ((String_assignmentContext) _localctx).var_id = lvalue();
                    setState(502);
                    ((String_assignmentContext) _localctx).op = match(ASSIGN);
                    setState(503);
                    string_result(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(505);
                    ((String_assignmentContext) _localctx).var_id = lvalue();
                    setState(506);
                    ((String_assignmentContext) _localctx).op = match(PLUS_ASSIGN);
                    setState(507);
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
        enterRule(_localctx, 90, RULE_initial_array_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(511);
                ((Initial_array_assignmentContext) _localctx).var_id = lvalue();
                setState(512);
                ((Initial_array_assignmentContext) _localctx).op = match(ASSIGN);
                setState(513);
                match(LEFT_SBRACKET);
                setState(514);
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
        enterRule(_localctx, 92, RULE_array_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(516);
                ((Array_assignmentContext) _localctx).arr_def = array_selector();
                setState(517);
                ((Array_assignmentContext) _localctx).op = match(ASSIGN);
                setState(518);
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
        enterRule(_localctx, 94, RULE_array_definition);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(520);
                match(LEFT_SBRACKET);
                setState(521);
                array_definition_elements(0);
                setState(522);
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
        int _startState = 96;
        enterRecursionRule(_localctx, 96, RULE_array_definition_elements, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                {
                    setState(527);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
                        case 1: {
                            setState(525);
                            int_result(0);
                        }
                        break;
                        case 2: {
                            setState(526);
                            dynamic_result(0);
                        }
                        break;
                    }
                }
                _ctx.stop = _input.LT(-1);
                setState(537);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 30, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new Array_definition_elementsContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_array_definition_elements);
                                setState(529);
                                if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                setState(530);
                                match(COMMA);
                                setState(533);
                                _errHandler.sync(this);
                                switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
                                    case 1: {
                                        setState(531);
                                        int_result(0);
                                    }
                                    break;
                                    case 2: {
                                        setState(532);
                                        dynamic_result(0);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    setState(539);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 30, _ctx);
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
        enterRule(_localctx, 98, RULE_array_selector);
        try {
            setState(556);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(540);
                    id_();
                    setState(541);
                    match(LEFT_SBRACKET);
                    setState(544);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 31, _ctx)) {
                        case 1: {
                            setState(542);
                            int_result(0);
                        }
                        break;
                        case 2: {
                            setState(543);
                            dynamic_result(0);
                        }
                        break;
                    }
                    setState(546);
                    match(RIGHT_SBRACKET);
                }
                break;
                case ID_GLOBAL:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(548);
                    id_global();
                    setState(549);
                    match(LEFT_SBRACKET);
                    setState(552);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 32, _ctx)) {
                        case 1: {
                            setState(550);
                            int_result(0);
                        }
                        break;
                        case 2: {
                            setState(551);
                            dynamic_result(0);
                        }
                        break;
                    }
                    setState(554);
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
        int _startState = 100;
        enterRecursionRule(_localctx, 100, RULE_dynamic_result, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(584);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 34, _ctx)) {
                    case 1: {
                        setState(559);
                        int_result(0);
                        setState(560);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(561);
                        dynamic_result(13);
                    }
                    break;
                    case 2: {
                        setState(563);
                        float_result(0);
                        setState(564);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(565);
                        dynamic_result(11);
                    }
                    break;
                    case 3: {
                        setState(567);
                        string_result(0);
                        setState(568);
                        ((Dynamic_resultContext) _localctx).op = match(MUL);
                        setState(569);
                        dynamic_result(8);
                    }
                    break;
                    case 4: {
                        setState(571);
                        int_result(0);
                        setState(572);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == PLUS || _la == MINUS)) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(573);
                        dynamic_result(6);
                    }
                    break;
                    case 5: {
                        setState(575);
                        float_result(0);
                        setState(576);
                        ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == PLUS || _la == MINUS)) {
                            ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(577);
                        dynamic_result(4);
                    }
                    break;
                    case 6: {
                        setState(579);
                        match(LEFT_RBRACKET);
                        setState(580);
                        dynamic_result(0);
                        setState(581);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                    case 7: {
                        setState(583);
                        dynamic_();
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(609);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 36, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(607);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
                                case 1: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(586);
                                    if (!(precpred(_ctx, 10)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 10)");
                                    setState(587);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(588);
                                    dynamic_result(11);
                                }
                                break;
                                case 2: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(589);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(590);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(591);
                                    dynamic_result(4);
                                }
                                break;
                                case 3: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(592);
                                    if (!(precpred(_ctx, 14)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 14)");
                                    setState(593);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(594);
                                    int_result(0);
                                }
                                break;
                                case 4: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(595);
                                    if (!(precpred(_ctx, 12)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 12)");
                                    setState(596);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(597);
                                    float_result(0);
                                }
                                break;
                                case 5: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(598);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(599);
                                    ((Dynamic_resultContext) _localctx).op = match(MUL);
                                    setState(600);
                                    string_result(0);
                                }
                                break;
                                case 6: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(601);
                                    if (!(precpred(_ctx, 7)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    setState(602);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(603);
                                    int_result(0);
                                }
                                break;
                                case 7: {
                                    _localctx = new Dynamic_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_dynamic_result);
                                    setState(604);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(605);
                                    ((Dynamic_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Dynamic_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(606);
                                    float_result(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(611);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 36, _ctx);
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
        enterRule(_localctx, 102, RULE_dynamic_);
        try {
            setState(615);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 37, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(612);
                    id_();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(613);
                    function_call_assignment();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(614);
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
        int _startState = 104;
        enterRecursionRule(_localctx, 104, RULE_int_result, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(623);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LEFT_RBRACKET: {
                        setState(618);
                        match(LEFT_RBRACKET);
                        setState(619);
                        int_result(0);
                        setState(620);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                    case INT: {
                        setState(622);
                        int_t();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(633);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 40, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(631);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 39, _ctx)) {
                                case 1: {
                                    _localctx = new Int_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_int_result);
                                    setState(625);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(626);
                                    ((Int_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        ((Int_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(627);
                                    int_result(5);
                                }
                                break;
                                case 2: {
                                    _localctx = new Int_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_int_result);
                                    setState(628);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(629);
                                    ((Int_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Int_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(630);
                                    int_result(4);
                                }
                                break;
                            }
                        }
                    }
                    setState(635);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 40, _ctx);
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
        int _startState = 106;
        enterRecursionRule(_localctx, 106, RULE_float_result, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(650);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 41, _ctx)) {
                    case 1: {
                        setState(637);
                        int_result(0);
                        setState(638);
                        ((Float_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                            ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(639);
                        float_result(7);
                    }
                    break;
                    case 2: {
                        setState(641);
                        int_result(0);
                        setState(642);
                        ((Float_resultContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!(_la == PLUS || _la == MINUS)) {
                            ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(643);
                        float_result(4);
                    }
                    break;
                    case 3: {
                        setState(645);
                        match(LEFT_RBRACKET);
                        setState(646);
                        float_result(0);
                        setState(647);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                    case 4: {
                        setState(649);
                        float_t();
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(666);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 43, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(664);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 42, _ctx)) {
                                case 1: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(652);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(653);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(654);
                                    float_result(9);
                                }
                                break;
                                case 2: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(655);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(656);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(657);
                                    float_result(6);
                                }
                                break;
                                case 3: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(658);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(659);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(660);
                                    int_result(0);
                                }
                                break;
                                case 4: {
                                    _localctx = new Float_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_float_result);
                                    setState(661);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(662);
                                    ((Float_resultContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        ((Float_resultContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(663);
                                    int_result(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(668);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 43, _ctx);
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
        int _startState = 108;
        enterRecursionRule(_localctx, 108, RULE_string_result, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(675);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case LEFT_RBRACKET:
                    case INT: {
                        setState(670);
                        int_result(0);
                        setState(671);
                        ((String_resultContext) _localctx).op = match(MUL);
                        setState(672);
                        string_result(3);
                    }
                    break;
                    case LITERAL: {
                        setState(674);
                        literal_t();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(685);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 46, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(683);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 45, _ctx)) {
                                case 1: {
                                    _localctx = new String_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_string_result);
                                    setState(677);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(678);
                                    ((String_resultContext) _localctx).op = match(PLUS);
                                    setState(679);
                                    string_result(3);
                                }
                                break;
                                case 2: {
                                    _localctx = new String_resultContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_string_result);
                                    setState(680);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(681);
                                    ((String_resultContext) _localctx).op = match(MUL);
                                    setState(682);
                                    int_result(0);
                                }
                                break;
                            }
                        }
                    }
                    setState(687);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 46, _ctx);
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
        enterRule(_localctx, 110, RULE_comparison_list);
        try {
            setState(709);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 47, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(688);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(689);
                    ((Comparison_listContext) _localctx).op = match(BIT_AND);
                    setState(690);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(692);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(693);
                    ((Comparison_listContext) _localctx).op = match(AND);
                    setState(694);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(696);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(697);
                    ((Comparison_listContext) _localctx).op = match(BIT_OR);
                    setState(698);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(700);
                    ((Comparison_listContext) _localctx).left = comparison();
                    setState(701);
                    ((Comparison_listContext) _localctx).op = match(OR);
                    setState(702);
                    ((Comparison_listContext) _localctx).right = comparison_list();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(704);
                    match(LEFT_RBRACKET);
                    setState(705);
                    comparison_list();
                    setState(706);
                    match(RIGHT_RBRACKET);
                }
                break;
                case 6:
                    enterOuterAlt(_localctx, 6);
                {
                    setState(708);
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
        enterRule(_localctx, 112, RULE_comparison);
        int _la;
        try {
            setState(719);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 48, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(711);
                    ((ComparisonContext) _localctx).left = comp_var();
                    setState(712);
                    ((ComparisonContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 4026531840L) != 0))) {
                        ((ComparisonContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(713);
                    ((ComparisonContext) _localctx).right = comp_var();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(715);
                    ((ComparisonContext) _localctx).left = comp_var();
                    setState(716);
                    ((ComparisonContext) _localctx).op = _input.LT(1);
                    _la = _input.LA(1);
                    if (!(_la == EQUAL || _la == NOT_EQUAL)) {
                        ((ComparisonContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(717);
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
        enterRule(_localctx, 114, RULE_comp_var);
        try {
            setState(724);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 49, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(721);
                    all_result();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(722);
                    array_selector();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(723);
                    id_();
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
        enterRule(_localctx, 116, RULE_lvalue);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(726);
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
        int _startState = 118;
        enterRecursionRule(_localctx, 118, RULE_rvalue, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(754);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 50, _ctx)) {
                    case 1: {
                        setState(729);
                        lvalue();
                    }
                    break;
                    case 2: {
                        setState(730);
                        initial_array_assignment();
                    }
                    break;
                    case 3: {
                        setState(731);
                        array_assignment();
                    }
                    break;
                    case 4: {
                        setState(732);
                        int_result(0);
                    }
                    break;
                    case 5: {
                        setState(733);
                        float_result(0);
                    }
                    break;
                    case 6: {
                        setState(734);
                        string_result(0);
                    }
                    break;
                    case 7: {
                        setState(735);
                        global_set();
                    }
                    break;
                    case 8: {
                        setState(736);
                        global_get();
                    }
                    break;
                    case 9: {
                        setState(737);
                        dynamic_assignment();
                    }
                    break;
                    case 10: {
                        setState(738);
                        string_assignment();
                    }
                    break;
                    case 11: {
                        setState(739);
                        float_assignment();
                    }
                    break;
                    case 12: {
                        setState(740);
                        int_assignment();
                    }
                    break;
                    case 13: {
                        setState(741);
                        assignment();
                    }
                    break;
                    case 14: {
                        setState(742);
                        function_call();
                    }
                    break;
                    case 15: {
                        setState(743);
                        literal_t();
                    }
                    break;
                    case 16: {
                        setState(744);
                        bool_t();
                    }
                    break;
                    case 17: {
                        setState(745);
                        float_t();
                    }
                    break;
                    case 18: {
                        setState(746);
                        int_t();
                    }
                    break;
                    case 19: {
                        setState(747);
                        nil_t();
                    }
                    break;
                    case 20: {
                        setState(748);
                        _la = _input.LA(1);
                        if (!(_la == BIT_NOT || _la == NOT)) {
                            _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(749);
                        rvalue(10);
                    }
                    break;
                    case 21: {
                        setState(750);
                        match(LEFT_RBRACKET);
                        setState(751);
                        rvalue(0);
                        setState(752);
                        match(RIGHT_RBRACKET);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(785);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 52, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(783);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 51, _ctx)) {
                                case 1: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(756);
                                    if (!(precpred(_ctx, 11)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                                    setState(757);
                                    match(EXP);
                                    setState(758);
                                    rvalue(12);
                                }
                                break;
                                case 2: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(759);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(760);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 29360128L) != 0))) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(761);
                                    rvalue(10);
                                }
                                break;
                                case 3: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(762);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(763);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == MINUS)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(764);
                                    rvalue(9);
                                }
                                break;
                                case 4: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(765);
                                    if (!(precpred(_ctx, 7)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    setState(766);
                                    _la = _input.LA(1);
                                    if (!(_la == BIT_SHL || _la == BIT_SHR)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(767);
                                    rvalue(8);
                                }
                                break;
                                case 5: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(768);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(769);
                                    match(BIT_AND);
                                    setState(770);
                                    rvalue(7);
                                }
                                break;
                                case 6: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(771);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(772);
                                    _la = _input.LA(1);
                                    if (!(_la == BIT_OR || _la == BIT_XOR)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(773);
                                    rvalue(6);
                                }
                                break;
                                case 7: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(774);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(775);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 4026531840L) != 0))) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(776);
                                    rvalue(5);
                                }
                                break;
                                case 8: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(777);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(778);
                                    _la = _input.LA(1);
                                    if (!(_la == EQUAL || _la == NOT_EQUAL)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(779);
                                    rvalue(4);
                                }
                                break;
                                case 9: {
                                    _localctx = new RvalueContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_rvalue);
                                    setState(780);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(781);
                                    _la = _input.LA(1);
                                    if (!(_la == AND || _la == OR)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(782);
                                    rvalue(3);
                                }
                                break;
                            }
                        }
                    }
                    setState(787);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 52, _ctx);
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
        enterRule(_localctx, 120, RULE_break_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(788);
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
        enterRule(_localctx, 122, RULE_literal_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(790);
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
        enterRule(_localctx, 124, RULE_float_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(792);
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
        enterRule(_localctx, 126, RULE_int_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(794);
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
        enterRule(_localctx, 128, RULE_bool_t);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(796);
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
        enterRule(_localctx, 130, RULE_nil_t);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(798);
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
        enterRule(_localctx, 132, RULE_id_);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(800);
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
        enterRule(_localctx, 134, RULE_id_global);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(802);
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
        enterRule(_localctx, 136, RULE_id_function);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(804);
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
        int _startState = 138;
        enterRecursionRule(_localctx, 138, RULE_terminator, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(809);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SEMICOLON: {
                        setState(807);
                        match(SEMICOLON);
                    }
                    break;
                    case CRLF: {
                        setState(808);
                        crlf();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(817);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 55, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(815);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 54, _ctx)) {
                                case 1: {
                                    _localctx = new TerminatorContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_terminator);
                                    setState(811);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(812);
                                    match(SEMICOLON);
                                }
                                break;
                                case 2: {
                                    _localctx = new TerminatorContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_terminator);
                                    setState(813);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(814);
                                    crlf();
                                }
                                break;
                            }
                        }
                    }
                    setState(819);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 55, _ctx);
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
        enterRule(_localctx, 140, RULE_else_token);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(820);
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
        enterRule(_localctx, 142, RULE_crlf);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(822);
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
            case 15:
                return function_definition_params_list_sempred((Function_definition_params_listContext) _localctx, predIndex);
            case 20:
                return function_call_params_sempred((Function_call_paramsContext) _localctx, predIndex);
            case 34:
                return for_init_list_sempred((For_init_listContext) _localctx, predIndex);
            case 37:
                return for_loop_list_sempred((For_loop_listContext) _localctx, predIndex);
            case 39:
                return statement_expression_list_sempred((Statement_expression_listContext) _localctx, predIndex);
            case 48:
                return array_definition_elements_sempred((Array_definition_elementsContext) _localctx, predIndex);
            case 50:
                return dynamic_result_sempred((Dynamic_resultContext) _localctx, predIndex);
            case 52:
                return int_result_sempred((Int_resultContext) _localctx, predIndex);
            case 53:
                return float_result_sempred((Float_resultContext) _localctx, predIndex);
            case 54:
                return string_result_sempred((String_resultContext) _localctx, predIndex);
            case 59:
                return rvalue_sempred((RvalueContext) _localctx, predIndex);
            case 69:
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

    private boolean for_init_list_sempred(For_init_listContext _localctx, int predIndex) {
        switch (predIndex) {
            case 3:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean for_loop_list_sempred(For_loop_listContext _localctx, int predIndex) {
        switch (predIndex) {
            case 4:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean statement_expression_list_sempred(Statement_expression_listContext _localctx, int predIndex) {
        switch (predIndex) {
            case 5:
                return precpred(_ctx, 3);
            case 6:
                return precpred(_ctx, 2);
            case 7:
                return precpred(_ctx, 1);
        }
        return true;
    }

    private boolean array_definition_elements_sempred(Array_definition_elementsContext _localctx, int predIndex) {
        switch (predIndex) {
            case 8:
                return precpred(_ctx, 1);
        }
        return true;
    }

    private boolean dynamic_result_sempred(Dynamic_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 9:
                return precpred(_ctx, 10);
            case 10:
                return precpred(_ctx, 3);
            case 11:
                return precpred(_ctx, 14);
            case 12:
                return precpred(_ctx, 12);
            case 13:
                return precpred(_ctx, 9);
            case 14:
                return precpred(_ctx, 7);
            case 15:
                return precpred(_ctx, 5);
        }
        return true;
    }

    private boolean int_result_sempred(Int_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 16:
                return precpred(_ctx, 4);
            case 17:
                return precpred(_ctx, 3);
        }
        return true;
    }

    private boolean float_result_sempred(Float_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 18:
                return precpred(_ctx, 8);
            case 19:
                return precpred(_ctx, 5);
            case 20:
                return precpred(_ctx, 6);
            case 21:
                return precpred(_ctx, 3);
        }
        return true;
    }

    private boolean string_result_sempred(String_resultContext _localctx, int predIndex) {
        switch (predIndex) {
            case 22:
                return precpred(_ctx, 2);
            case 23:
                return precpred(_ctx, 4);
        }
        return true;
    }

    private boolean rvalue_sempred(RvalueContext _localctx, int predIndex) {
        switch (predIndex) {
            case 24:
                return precpred(_ctx, 11);
            case 25:
                return precpred(_ctx, 9);
            case 26:
                return precpred(_ctx, 8);
            case 27:
                return precpred(_ctx, 7);
            case 28:
                return precpred(_ctx, 6);
            case 29:
                return precpred(_ctx, 5);
            case 30:
                return precpred(_ctx, 4);
            case 31:
                return precpred(_ctx, 3);
            case 32:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean terminator_sempred(TerminatorContext _localctx, int predIndex) {
        switch (predIndex) {
            case 33:
                return precpred(_ctx, 4);
            case 34:
                return precpred(_ctx, 3);
        }
        return true;
    }

    public static final String _serializedATN =
            "\u0004\u0001<\u0339\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002" +
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
                    "F\u0007F\u0002G\u0007G\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0099\b\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001\u009f\b\u0001" +
                    "\n\u0001\f\u0001\u00a2\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001" +
                    "\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001" +
                    "\u0002\u0003\u0002\u00ae\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001" +
                    "\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001" +
                    "\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                    "\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001" +
                    "\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001" +
                    "\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00d5\b\f\u0001\r\u0001\r\u0003" +
                    "\r\u00d9\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e" +
                    "\u0001\u000e\u0001\u000e\u0003\u000e\u00e2\b\u000e\u0001\u000f\u0001\u000f" +
                    "\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00ea\b\u000f" +
                    "\n\u000f\f\u000f\u00ed\t\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001" +
                    "\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001" +
                    "\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001" +
                    "\u0012\u0001\u0012\u0003\u0012\u0100\b\u0012\u0001\u0013\u0001\u0013\u0001" +
                    "\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005" +
                    "\u0014\u010a\b\u0014\n\u0014\f\u0014\u010d\t\u0014\u0001\u0015\u0001\u0015" +
                    "\u0003\u0015\u0111\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016" +
                    "\u0003\u0016\u0117\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017" +
                    "\u0001\u0017\u0001\u0017\u0003\u0017\u011f\b\u0017\u0001\u0018\u0001\u0018" +
                    "\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019" +
                    "\u0128\b\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b" +
                    "\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b" +
                    "\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b" +
                    "\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u013f\b\u001b" +
                    "\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c" +
                    "\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c" +
                    "\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c" +
                    "\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u0157\b\u001c" +
                    "\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d" +
                    "\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d" +
                    "\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d" +
                    "\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u016f\b\u001d" +
                    "\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e" +
                    "\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f" +
                    "\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f" +
                    "\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f" +
                    "\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f\u018d\b\u001f" +
                    "\u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0003!\u0195\b!\u0001\"\u0001" +
                    "\"\u0001\"\u0001\"\u0001\"\u0001\"\u0005\"\u019d\b\"\n\"\f\"\u01a0\t\"" +
                    "\u0001#\u0001#\u0001$\u0001$\u0001%\u0001%\u0001%\u0001%\u0001%\u0001" +
                    "%\u0005%\u01ac\b%\n%\f%\u01af\t%\u0001&\u0001&\u0001\'\u0001\'\u0001\'" +
                    "\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0003\'\u01bc\b\'\u0001" +
                    "\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001" +
                    "\'\u0001\'\u0005\'\u01c9\b\'\n\'\f\'\u01cc\t\'\u0001(\u0001(\u0001(\u0001" +
                    "(\u0001(\u0001(\u0001(\u0001(\u0003(\u01d6\b(\u0001)\u0001)\u0001)\u0001" +
                    ")\u0001)\u0001)\u0001)\u0001)\u0003)\u01e0\b)\u0001*\u0001*\u0001*\u0001" +
                    "*\u0001*\u0001*\u0001*\u0001*\u0003*\u01ea\b*\u0001+\u0001+\u0001+\u0001" +
                    "+\u0001+\u0001+\u0001+\u0001+\u0003+\u01f4\b+\u0001,\u0001,\u0001,\u0001" +
                    ",\u0001,\u0001,\u0001,\u0001,\u0003,\u01fe\b,\u0001-\u0001-\u0001-\u0001" +
                    "-\u0001-\u0001.\u0001.\u0001.\u0001.\u0001/\u0001/\u0001/\u0001/\u0001" +
                    "0\u00010\u00010\u00030\u0210\b0\u00010\u00010\u00010\u00010\u00030\u0216" +
                    "\b0\u00050\u0218\b0\n0\f0\u021b\t0\u00011\u00011\u00011\u00011\u00031" +
                    "\u0221\b1\u00011\u00011\u00011\u00011\u00011\u00011\u00031\u0229\b1\u0001" +
                    "1\u00011\u00031\u022d\b1\u00012\u00012\u00012\u00012\u00012\u00012\u0001" +
                    "2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001" +
                    "2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0003" +
                    "2\u0249\b2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001" +
                    "2\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u00012\u0001" +
                    "2\u00012\u00012\u00052\u0260\b2\n2\f2\u0263\t2\u00013\u00013\u00013\u0003" +
                    "3\u0268\b3\u00014\u00014\u00014\u00014\u00014\u00014\u00034\u0270\b4\u0001" +
                    "4\u00014\u00014\u00014\u00014\u00014\u00054\u0278\b4\n4\f4\u027b\t4\u0001" +
                    "5\u00015\u00015\u00015\u00015\u00015\u00015\u00015\u00015\u00015\u0001" +
                    "5\u00015\u00015\u00015\u00035\u028b\b5\u00015\u00015\u00015\u00015\u0001" +
                    "5\u00015\u00015\u00015\u00015\u00015\u00015\u00015\u00055\u0299\b5\n5" +
                    "\f5\u029c\t5\u00016\u00016\u00016\u00016\u00016\u00016\u00036\u02a4\b" +
                    "6\u00016\u00016\u00016\u00016\u00016\u00016\u00056\u02ac\b6\n6\f6\u02af" +
                    "\t6\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u0001" +
                    "7\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u0001" +
                    "7\u00017\u00037\u02c6\b7\u00018\u00018\u00018\u00018\u00018\u00018\u0001" +
                    "8\u00018\u00038\u02d0\b8\u00019\u00019\u00019\u00039\u02d5\b9\u0001:\u0001" +
                    ":\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001" +
                    ";\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001" +
                    ";\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0003;\u02f3\b;\u0001;\u0001" +
                    ";\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001" +
                    ";\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001;\u0001" +
                    ";\u0001;\u0001;\u0001;\u0001;\u0001;\u0005;\u0310\b;\n;\f;\u0313\t;\u0001" +
                    "<\u0001<\u0001=\u0001=\u0001>\u0001>\u0001?\u0001?\u0001@\u0001@\u0001" +
                    "A\u0001A\u0001B\u0001B\u0001C\u0001C\u0001D\u0001D\u0001E\u0001E\u0001" +
                    "E\u0003E\u032a\bE\u0001E\u0001E\u0001E\u0001E\u0005E\u0330\bE\nE\fE\u0333" +
                    "\tE\u0001F\u0001F\u0001G\u0001G\u0001G\u0000\r\u0002\u001e(DJN`dhjlv\u008a" +
                    "H\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a" +
                    "\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082" +
                    "\u0084\u0086\u0088\u008a\u008c\u008e\u0000\n\u0001\u0000!&\u0001\u0000" +
                    "\u0016\u0018\u0001\u0000\u0014\u0015\u0001\u0000\u001c\u001f\u0001\u0000" +
                    "\u001a\u001b\u0002\u0000**//\u0001\u0000+,\u0001\u0000()\u0001\u0000-" +
                    ".\u0001\u0000\u0012\u0013\u036e\u0000\u0090\u0001\u0000\u0000\u0000\u0002" +
                    "\u0098\u0001\u0000\u0000\u0000\u0004\u00ad\u0001\u0000\u0000\u0000\u0006" +
                    "\u00af\u0001\u0000\u0000\u0000\b\u00b3\u0001\u0000\u0000\u0000\n\u00b7" +
                    "\u0001\u0000\u0000\u0000\f\u00b9\u0001\u0000\u0000\u0000\u000e\u00bb\u0001" +
                    "\u0000\u0000\u0000\u0010\u00be\u0001\u0000\u0000\u0000\u0012\u00c3\u0001" +
                    "\u0000\u0000\u0000\u0014\u00c5\u0001\u0000\u0000\u0000\u0016\u00c9\u0001" +
                    "\u0000\u0000\u0000\u0018\u00d4\u0001\u0000\u0000\u0000\u001a\u00d8\u0001" +
                    "\u0000\u0000\u0000\u001c\u00e1\u0001\u0000\u0000\u0000\u001e\u00e3\u0001" +
                    "\u0000\u0000\u0000 \u00ee\u0001\u0000\u0000\u0000\"\u00f0\u0001\u0000" +
                    "\u0000\u0000$\u00ff\u0001\u0000\u0000\u0000&\u0101\u0001\u0000\u0000\u0000" +
                    "(\u0103\u0001\u0000\u0000\u0000*\u0110\u0001\u0000\u0000\u0000,\u0116" +
                    "\u0001\u0000\u0000\u0000.\u0118\u0001\u0000\u0000\u00000\u0120\u0001\u0000" +
                    "\u0000\u00002\u0127\u0001\u0000\u0000\u00004\u0129\u0001\u0000\u0000\u0000" +
                    "6\u013e\u0001\u0000\u0000\u00008\u0156\u0001\u0000\u0000\u0000:\u016e" +
                    "\u0001\u0000\u0000\u0000<\u0170\u0001\u0000\u0000\u0000>\u018c\u0001\u0000" +
                    "\u0000\u0000@\u018e\u0001\u0000\u0000\u0000B\u0194\u0001\u0000\u0000\u0000" +
                    "D\u0196\u0001\u0000\u0000\u0000F\u01a1\u0001\u0000\u0000\u0000H\u01a3" +
                    "\u0001\u0000\u0000\u0000J\u01a5\u0001\u0000\u0000\u0000L\u01b0\u0001\u0000" +
                    "\u0000\u0000N\u01bb\u0001\u0000\u0000\u0000P\u01d5\u0001\u0000\u0000\u0000" +
                    "R\u01df\u0001\u0000\u0000\u0000T\u01e9\u0001\u0000\u0000\u0000V\u01f3" +
                    "\u0001\u0000\u0000\u0000X\u01fd\u0001\u0000\u0000\u0000Z\u01ff\u0001\u0000" +
                    "\u0000\u0000\\\u0204\u0001\u0000\u0000\u0000^\u0208\u0001\u0000\u0000" +
                    "\u0000`\u020c\u0001\u0000\u0000\u0000b\u022c\u0001\u0000\u0000\u0000d" +
                    "\u0248\u0001\u0000\u0000\u0000f\u0267\u0001\u0000\u0000\u0000h\u026f\u0001" +
                    "\u0000\u0000\u0000j\u028a\u0001\u0000\u0000\u0000l\u02a3\u0001\u0000\u0000" +
                    "\u0000n\u02c5\u0001\u0000\u0000\u0000p\u02cf\u0001\u0000\u0000\u0000r" +
                    "\u02d4\u0001\u0000\u0000\u0000t\u02d6\u0001\u0000\u0000\u0000v\u02f2\u0001" +
                    "\u0000\u0000\u0000x\u0314\u0001\u0000\u0000\u0000z\u0316\u0001\u0000\u0000" +
                    "\u0000|\u0318\u0001\u0000\u0000\u0000~\u031a\u0001\u0000\u0000\u0000\u0080" +
                    "\u031c\u0001\u0000\u0000\u0000\u0082\u031e\u0001\u0000\u0000\u0000\u0084" +
                    "\u0320\u0001\u0000\u0000\u0000\u0086\u0322\u0001\u0000\u0000\u0000\u0088" +
                    "\u0324\u0001\u0000\u0000\u0000\u008a\u0329\u0001\u0000\u0000\u0000\u008c" +
                    "\u0334\u0001\u0000\u0000\u0000\u008e\u0336\u0001\u0000\u0000\u0000\u0090" +
                    "\u0091\u0003\u0002\u0001\u0000\u0091\u0092\u0005\u0000\u0000\u0001\u0092" +
                    "\u0001\u0001\u0000\u0000\u0000\u0093\u0094\u0006\u0001\uffff\uffff\u0000" +
                    "\u0094\u0095\u0003\u0004\u0002\u0000\u0095\u0096\u0003\u008aE\u0000\u0096" +
                    "\u0099\u0001\u0000\u0000\u0000\u0097\u0099\u0003\u008aE\u0000\u0098\u0093" +
                    "\u0001\u0000\u0000\u0000\u0098\u0097\u0001\u0000\u0000\u0000\u0099\u00a0" +
                    "\u0001\u0000\u0000\u0000\u009a\u009b\n\u0002\u0000\u0000\u009b\u009c\u0003" +
                    "\u0004\u0002\u0000\u009c\u009d\u0003\u008aE\u0000\u009d\u009f\u0001\u0000" +
                    "\u0000\u0000\u009e\u009a\u0001\u0000\u0000\u0000\u009f\u00a2\u0001\u0000" +
                    "\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000" +
                    "\u0000\u0000\u00a1\u0003\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000" +
                    "\u0000\u0000\u00a3\u00ae\u0003\u0014\n\u0000\u00a4\u00ae\u0003\f\u0006" +
                    "\u0000\u00a5\u00ae\u0003\u000e\u0007\u0000\u00a6\u00ae\u00038\u001c\u0000" +
                    "\u00a7\u00ae\u0003:\u001d\u0000\u00a8\u00ae\u0003v;\u0000\u00a9\u00ae" +
                    "\u0003\"\u0011\u0000\u00aa\u00ae\u0003<\u001e\u0000\u00ab\u00ae\u0003" +
                    ">\u001f\u0000\u00ac\u00ae\u0003\u0010\b\u0000\u00ad\u00a3\u0001\u0000" +
                    "\u0000\u0000\u00ad\u00a4\u0001\u0000\u0000\u0000\u00ad\u00a5\u0001\u0000" +
                    "\u0000\u0000\u00ad\u00a6\u0001\u0000\u0000\u0000\u00ad\u00a7\u0001\u0000" +
                    "\u0000\u0000\u00ad\u00a8\u0001\u0000\u0000\u0000\u00ad\u00a9\u0001\u0000" +
                    "\u0000\u0000\u00ad\u00aa\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000" +
                    "\u0000\u0000\u00ad\u00ac\u0001\u0000\u0000\u0000\u00ae\u0005\u0001\u0000" +
                    "\u0000\u0000\u00af\u00b0\u0003t:\u0000\u00b0\u00b1\u0005 \u0000\u0000" +
                    "\u00b1\u00b2\u0003\u0086C\u0000\u00b2\u0007\u0001\u0000\u0000\u0000\u00b3" +
                    "\u00b4\u0003\u0086C\u0000\u00b4\u00b5\u0005 \u0000\u0000\u00b5\u00b6\u0003" +
                    "2\u0019\u0000\u00b6\t\u0001\u0000\u0000\u0000\u00b7\u00b8\u0003\u0086" +
                    "C\u0000\u00b8\u000b\u0001\u0000\u0000\u0000\u00b9\u00ba\u0003$\u0012\u0000" +
                    "\u00ba\r\u0001\u0000\u0000\u0000\u00bb\u00bc\u0005\u0005\u0000\u0000\u00bc" +
                    "\u00bd\u0003z=\u0000\u00bd\u000f\u0001\u0000\u0000\u0000\u00be\u00bf\u0005" +
                    "\t\u0000\u0000\u00bf\u00c0\u0003\u008eG\u0000\u00c0\u00c1\u0003\u0012" +
                    "\t\u0000\u00c1\u00c2\u0005\u0006\u0000\u0000\u00c2\u0011\u0001\u0000\u0000" +
                    "\u0000\u00c3\u00c4\u0003\u0002\u0001\u0000\u00c4\u0013\u0001\u0000\u0000" +
                    "\u0000\u00c5\u00c6\u0003\u0018\f\u0000\u00c6\u00c7\u0003\u0016\u000b\u0000" +
                    "\u00c7\u00c8\u0005\u0006\u0000\u0000\u00c8\u0015\u0001\u0000\u0000\u0000" +
                    "\u00c9\u00ca\u0003\u0002\u0001\u0000\u00ca\u0017\u0001\u0000\u0000\u0000" +
                    "\u00cb\u00cc\u0005\u0007\u0000\u0000\u00cc\u00cd\u0003\u001a\r\u0000\u00cd" +
                    "\u00ce\u0003\u008eG\u0000\u00ce\u00d5\u0001\u0000\u0000\u0000\u00cf\u00d0" +
                    "\u0005\u0007\u0000\u0000\u00d0\u00d1\u0003\u001a\r\u0000\u00d1\u00d2\u0003" +
                    "\u001c\u000e\u0000\u00d2\u00d3\u0003\u008eG\u0000\u00d3\u00d5\u0001\u0000" +
                    "\u0000\u0000\u00d4\u00cb\u0001\u0000\u0000\u0000\u00d4\u00cf\u0001\u0000" +
                    "\u0000\u0000\u00d5\u0019\u0001\u0000\u0000\u0000\u00d6\u00d9\u0003\u0088" +
                    "D\u0000\u00d7\u00d9\u0003\u0084B\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000" +
                    "\u00d8\u00d7\u0001\u0000\u0000\u0000\u00d9\u001b\u0001\u0000\u0000\u0000" +
                    "\u00da\u00db\u00050\u0000\u0000\u00db\u00e2\u00051\u0000\u0000\u00dc\u00dd" +
                    "\u00050\u0000\u0000\u00dd\u00de\u0003\u001e\u000f\u0000\u00de\u00df\u0005" +
                    "1\u0000\u0000\u00df\u00e2\u0001\u0000\u0000\u0000\u00e0\u00e2\u0003\u001e" +
                    "\u000f\u0000\u00e1\u00da\u0001\u0000\u0000\u0000\u00e1\u00dc\u0001\u0000" +
                    "\u0000\u0000\u00e1\u00e0\u0001\u0000\u0000\u0000\u00e2\u001d\u0001\u0000" +
                    "\u0000\u0000\u00e3\u00e4\u0006\u000f\uffff\uffff\u0000\u00e4\u00e5\u0003" +
                    " \u0010\u0000\u00e5\u00eb\u0001\u0000\u0000\u0000\u00e6\u00e7\n\u0001" +
                    "\u0000\u0000\u00e7\u00e8\u0005\u0002\u0000\u0000\u00e8\u00ea\u0003 \u0010" +
                    "\u0000\u00e9\u00e6\u0001\u0000\u0000\u0000\u00ea\u00ed\u0001\u0000\u0000" +
                    "\u0000\u00eb\u00e9\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000" +
                    "\u0000\u00ec\u001f\u0001\u0000\u0000\u0000\u00ed\u00eb\u0001\u0000\u0000" +
                    "\u0000\u00ee\u00ef\u0003\u0084B\u0000\u00ef!\u0001\u0000\u0000\u0000\u00f0" +
                    "\u00f1\u0005\b\u0000\u0000\u00f1\u00f2\u00032\u0019\u0000\u00f2#\u0001" +
                    "\u0000\u0000\u0000\u00f3\u00f4\u0003\u001a\r\u0000\u00f4\u00f5\u00050" +
                    "\u0000\u0000\u00f5\u00f6\u0003&\u0013\u0000\u00f6\u00f7\u00051\u0000\u0000" +
                    "\u00f7\u0100\u0001\u0000\u0000\u0000\u00f8\u00f9\u0003\u001a\r\u0000\u00f9" +
                    "\u00fa\u0003&\u0013\u0000\u00fa\u0100\u0001\u0000\u0000\u0000\u00fb\u00fc" +
                    "\u0003\u001a\r\u0000\u00fc\u00fd\u00050\u0000\u0000\u00fd\u00fe\u0005" +
                    "1\u0000\u0000\u00fe\u0100\u0001\u0000\u0000\u0000\u00ff\u00f3\u0001\u0000" +
                    "\u0000\u0000\u00ff\u00f8\u0001\u0000\u0000\u0000\u00ff\u00fb\u0001\u0000" +
                    "\u0000\u0000\u0100%\u0001\u0000\u0000\u0000\u0101\u0102\u0003(\u0014\u0000" +
                    "\u0102\'\u0001\u0000\u0000\u0000\u0103\u0104\u0006\u0014\uffff\uffff\u0000" +
                    "\u0104\u0105\u0003*\u0015\u0000\u0105\u010b\u0001\u0000\u0000\u0000\u0106" +
                    "\u0107\n\u0001\u0000\u0000\u0107\u0108\u0005\u0002\u0000\u0000\u0108\u010a" +
                    "\u0003*\u0015\u0000\u0109\u0106\u0001\u0000\u0000\u0000\u010a\u010d\u0001" +
                    "\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b\u010c\u0001" +
                    "\u0000\u0000\u0000\u010c)\u0001\u0000\u0000\u0000\u010d\u010b\u0001\u0000" +
                    "\u0000\u0000\u010e\u0111\u0003,\u0016\u0000\u010f\u0111\u0003.\u0017\u0000" +
                    "\u0110\u010e\u0001\u0000\u0000\u0000\u0110\u010f\u0001\u0000\u0000\u0000" +
                    "\u0111+\u0001\u0000\u0000\u0000\u0112\u0117\u0003h4\u0000\u0113\u0117" +
                    "\u0003j5\u0000\u0114\u0117\u0003l6\u0000\u0115\u0117\u0003d2\u0000\u0116" +
                    "\u0112\u0001\u0000\u0000\u0000\u0116\u0113\u0001\u0000\u0000\u0000\u0116" +
                    "\u0114\u0001\u0000\u0000\u0000\u0116\u0115\u0001\u0000\u0000\u0000\u0117" +
                    "-\u0001\u0000\u0000\u0000\u0118\u0119\u0003\u0084B\u0000\u0119\u011e\u0005" +
                    " \u0000\u0000\u011a\u011f\u0003h4\u0000\u011b\u011f\u0003j5\u0000\u011c" +
                    "\u011f\u0003l6\u0000\u011d\u011f\u0003d2\u0000\u011e\u011a\u0001\u0000" +
                    "\u0000\u0000\u011e\u011b\u0001\u0000\u0000\u0000\u011e\u011c\u0001\u0000" +
                    "\u0000\u0000\u011e\u011d\u0001\u0000\u0000\u0000\u011f/\u0001\u0000\u0000" +
                    "\u0000\u0120\u0121\u0003$\u0012\u0000\u01211\u0001\u0000\u0000\u0000\u0122" +
                    "\u0128\u0003h4\u0000\u0123\u0128\u0003j5\u0000\u0124\u0128\u0003l6\u0000" +
                    "\u0125\u0128\u0003d2\u0000\u0126\u0128\u0003\n\u0005\u0000\u0127\u0122" +
                    "\u0001\u0000\u0000\u0000\u0127\u0123\u0001\u0000\u0000\u0000\u0127\u0124" +
                    "\u0001\u0000\u0000\u0000\u0127\u0125\u0001\u0000\u0000\u0000\u0127\u0126" +
                    "\u0001\u0000\u0000\u0000\u01283\u0001\u0000\u0000\u0000\u0129\u012a\u0003" +
                    "6\u001b\u0000\u012a5\u0001\u0000\u0000\u0000\u012b\u012c\u0005\f\u0000" +
                    "\u0000\u012c\u012d\u0003F#\u0000\u012d\u012e\u0003\u008eG\u0000\u012e" +
                    "\u012f\u0003L&\u0000\u012f\u013f\u0001\u0000\u0000\u0000\u0130\u0131\u0005" +
                    "\f\u0000\u0000\u0131\u0132\u0003F#\u0000\u0132\u0133\u0003\u008eG\u0000" +
                    "\u0133\u0134\u0003L&\u0000\u0134\u0135\u0003\u008cF\u0000\u0135\u0136" +
                    "\u0003\u008eG\u0000\u0136\u0137\u0003L&\u0000\u0137\u013f\u0001\u0000" +
                    "\u0000\u0000\u0138\u0139\u0005\f\u0000\u0000\u0139\u013a\u0003F#\u0000" +
                    "\u013a\u013b\u0003\u008eG\u0000\u013b\u013c\u0003L&\u0000\u013c\u013d" +
                    "\u00036\u001b\u0000\u013d\u013f\u0001\u0000\u0000\u0000\u013e\u012b\u0001" +
                    "\u0000\u0000\u0000\u013e\u0130\u0001\u0000\u0000\u0000\u013e\u0138\u0001" +
                    "\u0000\u0000\u0000\u013f7\u0001\u0000\u0000\u0000\u0140\u0141\u0005\n" +
                    "\u0000\u0000\u0141\u0142\u0003F#\u0000\u0142\u0143\u0003\u008eG\u0000" +
                    "\u0143\u0144\u0003L&\u0000\u0144\u0145\u0005\u0006\u0000\u0000\u0145\u0157" +
                    "\u0001\u0000\u0000\u0000\u0146\u0147\u0005\n\u0000\u0000\u0147\u0148\u0003" +
                    "F#\u0000\u0148\u0149\u0003\u008eG\u0000\u0149\u014a\u0003L&\u0000\u014a" +
                    "\u014b\u0003\u008cF\u0000\u014b\u014c\u0003\u008eG\u0000\u014c\u014d\u0003" +
                    "L&\u0000\u014d\u014e\u0005\u0006\u0000\u0000\u014e\u0157\u0001\u0000\u0000" +
                    "\u0000\u014f\u0150\u0005\n\u0000\u0000\u0150\u0151\u0003F#\u0000\u0151" +
                    "\u0152\u0003\u008eG\u0000\u0152\u0153\u0003L&\u0000\u0153\u0154\u0003" +
                    "4\u001a\u0000\u0154\u0155\u0005\u0006\u0000\u0000\u0155\u0157\u0001\u0000" +
                    "\u0000\u0000\u0156\u0140\u0001\u0000\u0000\u0000\u0156\u0146\u0001\u0000" +
                    "\u0000\u0000\u0156\u014f\u0001\u0000\u0000\u0000\u01579\u0001\u0000\u0000" +
                    "\u0000\u0158\u0159\u0005\r\u0000\u0000\u0159\u015a\u0003F#\u0000\u015a" +
                    "\u015b\u0003\u008eG\u0000\u015b\u015c\u0003L&\u0000\u015c\u015d\u0005" +
                    "\u0006\u0000\u0000\u015d\u016f\u0001\u0000\u0000\u0000\u015e\u015f\u0005" +
                    "\r\u0000\u0000\u015f\u0160\u0003F#\u0000\u0160\u0161\u0003\u008eG\u0000" +
                    "\u0161\u0162\u0003L&\u0000\u0162\u0163\u0003\u008cF\u0000\u0163\u0164" +
                    "\u0003\u008eG\u0000\u0164\u0165\u0003L&\u0000\u0165\u0166\u0005\u0006" +
                    "\u0000\u0000\u0166\u016f\u0001\u0000\u0000\u0000\u0167\u0168\u0005\r\u0000" +
                    "\u0000\u0168\u0169\u0003F#\u0000\u0169\u016a\u0003\u008eG\u0000\u016a" +
                    "\u016b\u0003L&\u0000\u016b\u016c\u00034\u001a\u0000\u016c\u016d\u0005" +
                    "\u0006\u0000\u0000\u016d\u016f\u0001\u0000\u0000\u0000\u016e\u0158\u0001" +
                    "\u0000\u0000\u0000\u016e\u015e\u0001\u0000\u0000\u0000\u016e\u0167\u0001" +
                    "\u0000\u0000\u0000\u016f;\u0001\u0000\u0000\u0000\u0170\u0171\u0005\u000e" +
                    "\u0000\u0000\u0171\u0172\u0003F#\u0000\u0172\u0173\u0003\u008eG\u0000" +
                    "\u0173\u0174\u0003L&\u0000\u0174\u0175\u0005\u0006\u0000\u0000\u0175=" +
                    "\u0001\u0000\u0000\u0000\u0176\u0177\u0005\u0011\u0000\u0000\u0177\u0178" +
                    "\u00050\u0000\u0000\u0178\u0179\u0003@ \u0000\u0179\u017a\u0005\u0003" +
                    "\u0000\u0000\u017a\u017b\u0003F#\u0000\u017b\u017c\u0005\u0003\u0000\u0000" +
                    "\u017c\u017d\u0003H$\u0000\u017d\u017e\u00051\u0000\u0000\u017e\u017f" +
                    "\u0003\u008eG\u0000\u017f\u0180\u0003L&\u0000\u0180\u0181\u0005\u0006" +
                    "\u0000\u0000\u0181\u018d\u0001\u0000\u0000\u0000\u0182\u0183\u0005\u0011" +
                    "\u0000\u0000\u0183\u0184\u0003@ \u0000\u0184\u0185\u0005\u0003\u0000\u0000" +
                    "\u0185\u0186\u0003F#\u0000\u0186\u0187\u0005\u0003\u0000\u0000\u0187\u0188" +
                    "\u0003H$\u0000\u0188\u0189\u0003\u008eG\u0000\u0189\u018a\u0003L&\u0000" +
                    "\u018a\u018b\u0005\u0006\u0000\u0000\u018b\u018d\u0001\u0000\u0000\u0000" +
                    "\u018c\u0176\u0001\u0000\u0000\u0000\u018c\u0182\u0001\u0000\u0000\u0000" +
                    "\u018d?\u0001\u0000\u0000\u0000\u018e\u018f\u0003D\"\u0000\u018fA\u0001" +
                    "\u0000\u0000\u0000\u0190\u0195\u0003T*\u0000\u0191\u0195\u0003V+\u0000" +
                    "\u0192\u0195\u0003X,\u0000\u0193\u0195\u0003R)\u0000\u0194\u0190\u0001" +
                    "\u0000\u0000\u0000\u0194\u0191\u0001\u0000\u0000\u0000\u0194\u0192\u0001" +
                    "\u0000\u0000\u0000\u0194\u0193\u0001\u0000\u0000\u0000\u0195C\u0001\u0000" +
                    "\u0000\u0000\u0196\u0197\u0006\"\uffff\uffff\u0000\u0197\u0198\u0003B" +
                    "!\u0000\u0198\u019e\u0001\u0000\u0000\u0000\u0199\u019a\n\u0002\u0000" +
                    "\u0000\u019a\u019b\u0005\u0002\u0000\u0000\u019b\u019d\u0003B!\u0000\u019c" +
                    "\u0199\u0001\u0000\u0000\u0000\u019d\u01a0\u0001\u0000\u0000\u0000\u019e" +
                    "\u019c\u0001\u0000\u0000\u0000\u019e\u019f\u0001\u0000\u0000\u0000\u019f" +
                    "E\u0001\u0000\u0000\u0000\u01a0\u019e\u0001\u0000\u0000\u0000\u01a1\u01a2" +
                    "\u0003n7\u0000\u01a2G\u0001\u0000\u0000\u0000\u01a3\u01a4\u0003J%\u0000" +
                    "\u01a4I\u0001\u0000\u0000\u0000\u01a5\u01a6\u0006%\uffff\uffff\u0000\u01a6" +
                    "\u01a7\u0003B!\u0000\u01a7\u01ad\u0001\u0000\u0000\u0000\u01a8\u01a9\n" +
                    "\u0002\u0000\u0000\u01a9\u01aa\u0005\u0002\u0000\u0000\u01aa\u01ac\u0003" +
                    "B!\u0000\u01ab\u01a8\u0001\u0000\u0000\u0000\u01ac\u01af\u0001\u0000\u0000" +
                    "\u0000\u01ad\u01ab\u0001\u0000\u0000\u0000\u01ad\u01ae\u0001\u0000\u0000" +
                    "\u0000\u01aeK\u0001\u0000\u0000\u0000\u01af\u01ad\u0001\u0000\u0000\u0000" +
                    "\u01b0\u01b1\u0003N\'\u0000\u01b1M\u0001\u0000\u0000\u0000\u01b2\u01b3" +
                    "\u0006\'\uffff\uffff\u0000\u01b3\u01b4\u0003\u0004\u0002\u0000\u01b4\u01b5" +
                    "\u0003\u008aE\u0000\u01b5\u01bc\u0001\u0000\u0000\u0000\u01b6\u01b7\u0005" +
                    "\u000f\u0000\u0000\u01b7\u01bc\u0003\u008aE\u0000\u01b8\u01b9\u0003x<" +
                    "\u0000\u01b9\u01ba\u0003\u008aE\u0000\u01ba\u01bc\u0001\u0000\u0000\u0000" +
                    "\u01bb\u01b2\u0001\u0000\u0000\u0000\u01bb\u01b6\u0001\u0000\u0000\u0000" +
                    "\u01bb\u01b8\u0001\u0000\u0000\u0000\u01bc\u01ca\u0001\u0000\u0000\u0000" +
                    "\u01bd\u01be\n\u0003\u0000\u0000\u01be\u01bf\u0003\u0004\u0002\u0000\u01bf" +
                    "\u01c0\u0003\u008aE\u0000\u01c0\u01c9\u0001\u0000\u0000\u0000\u01c1\u01c2" +
                    "\n\u0002\u0000\u0000\u01c2\u01c3\u0005\u000f\u0000\u0000\u01c3\u01c9\u0003" +
                    "\u008aE\u0000\u01c4\u01c5\n\u0001\u0000\u0000\u01c5\u01c6\u0003x<\u0000" +
                    "\u01c6\u01c7\u0003\u008aE\u0000\u01c7\u01c9\u0001\u0000\u0000\u0000\u01c8" +
                    "\u01bd\u0001\u0000\u0000\u0000\u01c8\u01c1\u0001\u0000\u0000\u0000\u01c8" +
                    "\u01c4\u0001\u0000\u0000\u0000\u01c9\u01cc\u0001\u0000\u0000\u0000\u01ca" +
                    "\u01c8\u0001\u0000\u0000\u0000\u01ca\u01cb\u0001\u0000\u0000\u0000\u01cb" +
                    "O\u0001\u0000\u0000\u0000\u01cc\u01ca\u0001\u0000\u0000\u0000\u01cd\u01ce" +
                    "\u0003t:\u0000\u01ce\u01cf\u0005 \u0000\u0000\u01cf\u01d0\u0003v;\u0000" +
                    "\u01d0\u01d6\u0001\u0000\u0000\u0000\u01d1\u01d2\u0003t:\u0000\u01d2\u01d3" +
                    "\u0007\u0000\u0000\u0000\u01d3\u01d4\u0003v;\u0000\u01d4\u01d6\u0001\u0000" +
                    "\u0000\u0000\u01d5\u01cd\u0001\u0000\u0000\u0000\u01d5\u01d1\u0001\u0000" +
                    "\u0000\u0000\u01d6Q\u0001\u0000\u0000\u0000\u01d7\u01d8\u0003t:\u0000" +
                    "\u01d8\u01d9\u0005 \u0000\u0000\u01d9\u01da\u0003d2\u0000\u01da\u01e0" +
                    "\u0001\u0000\u0000\u0000\u01db\u01dc\u0003t:\u0000\u01dc\u01dd\u0007\u0000" +
                    "\u0000\u0000\u01dd\u01de\u0003d2\u0000\u01de\u01e0\u0001\u0000\u0000\u0000" +
                    "\u01df\u01d7\u0001\u0000\u0000\u0000\u01df\u01db\u0001\u0000\u0000\u0000" +
                    "\u01e0S\u0001\u0000\u0000\u0000\u01e1\u01e2\u0003t:\u0000\u01e2\u01e3" +
                    "\u0005 \u0000\u0000\u01e3\u01e4\u0003h4\u0000\u01e4\u01ea\u0001\u0000" +
                    "\u0000\u0000\u01e5\u01e6\u0003t:\u0000\u01e6\u01e7\u0007\u0000\u0000\u0000" +
                    "\u01e7\u01e8\u0003h4\u0000\u01e8\u01ea\u0001\u0000\u0000\u0000\u01e9\u01e1" +
                    "\u0001\u0000\u0000\u0000\u01e9\u01e5\u0001\u0000\u0000\u0000\u01eaU\u0001" +
                    "\u0000\u0000\u0000\u01eb\u01ec\u0003t:\u0000\u01ec\u01ed\u0005 \u0000" +
                    "\u0000\u01ed\u01ee\u0003j5\u0000\u01ee\u01f4\u0001\u0000\u0000\u0000\u01ef" +
                    "\u01f0\u0003t:\u0000\u01f0\u01f1\u0007\u0000\u0000\u0000\u01f1\u01f2\u0003" +
                    "j5\u0000\u01f2\u01f4\u0001\u0000\u0000\u0000\u01f3\u01eb\u0001\u0000\u0000" +
                    "\u0000\u01f3\u01ef\u0001\u0000\u0000\u0000\u01f4W\u0001\u0000\u0000\u0000" +
                    "\u01f5\u01f6\u0003t:\u0000\u01f6\u01f7\u0005 \u0000\u0000\u01f7\u01f8" +
                    "\u0003l6\u0000\u01f8\u01fe\u0001\u0000\u0000\u0000\u01f9\u01fa\u0003t" +
                    ":\u0000\u01fa\u01fb\u0005!\u0000\u0000\u01fb\u01fc\u0003l6\u0000\u01fc" +
                    "\u01fe\u0001\u0000\u0000\u0000\u01fd\u01f5\u0001\u0000\u0000\u0000\u01fd" +
                    "\u01f9\u0001\u0000\u0000\u0000\u01feY\u0001\u0000\u0000\u0000\u01ff\u0200" +
                    "\u0003t:\u0000\u0200\u0201\u0005 \u0000\u0000\u0201\u0202\u00052\u0000" +
                    "\u0000\u0202\u0203\u00053\u0000\u0000\u0203[\u0001\u0000\u0000\u0000\u0204" +
                    "\u0205\u0003b1\u0000\u0205\u0206\u0005 \u0000\u0000\u0206\u0207\u0003" +
                    "2\u0019\u0000\u0207]\u0001\u0000\u0000\u0000\u0208\u0209\u00052\u0000" +
                    "\u0000\u0209\u020a\u0003`0\u0000\u020a\u020b\u00053\u0000\u0000\u020b" +
                    "_\u0001\u0000\u0000\u0000\u020c\u020f\u00060\uffff\uffff\u0000\u020d\u0210" +
                    "\u0003h4\u0000\u020e\u0210\u0003d2\u0000\u020f\u020d\u0001\u0000\u0000" +
                    "\u0000\u020f\u020e\u0001\u0000\u0000\u0000\u0210\u0219\u0001\u0000\u0000" +
                    "\u0000\u0211\u0212\n\u0001\u0000\u0000\u0212\u0215\u0005\u0002\u0000\u0000" +
                    "\u0213\u0216\u0003h4\u0000\u0214\u0216\u0003d2\u0000\u0215\u0213\u0001" +
                    "\u0000\u0000\u0000\u0215\u0214\u0001\u0000\u0000\u0000\u0216\u0218\u0001" +
                    "\u0000\u0000\u0000\u0217\u0211\u0001\u0000\u0000\u0000\u0218\u021b\u0001" +
                    "\u0000\u0000\u0000\u0219\u0217\u0001\u0000\u0000\u0000\u0219\u021a\u0001" +
                    "\u0000\u0000\u0000\u021aa\u0001\u0000\u0000\u0000\u021b\u0219\u0001\u0000" +
                    "\u0000\u0000\u021c\u021d\u0003\u0084B\u0000\u021d\u0220\u00052\u0000\u0000" +
                    "\u021e\u0221\u0003h4\u0000\u021f\u0221\u0003d2\u0000\u0220\u021e\u0001" +
                    "\u0000\u0000\u0000\u0220\u021f\u0001\u0000\u0000\u0000\u0221\u0222\u0001" +
                    "\u0000\u0000\u0000\u0222\u0223\u00053\u0000\u0000\u0223\u022d\u0001\u0000" +
                    "\u0000\u0000\u0224\u0225\u0003\u0086C\u0000\u0225\u0228\u00052\u0000\u0000" +
                    "\u0226\u0229\u0003h4\u0000\u0227\u0229\u0003d2\u0000\u0228\u0226\u0001" +
                    "\u0000\u0000\u0000\u0228\u0227\u0001\u0000\u0000\u0000\u0229\u022a\u0001" +
                    "\u0000\u0000\u0000\u022a\u022b\u00053\u0000\u0000\u022b\u022d\u0001\u0000" +
                    "\u0000\u0000\u022c\u021c\u0001\u0000\u0000\u0000\u022c\u0224\u0001\u0000" +
                    "\u0000\u0000\u022dc\u0001\u0000\u0000\u0000\u022e\u022f\u00062\uffff\uffff" +
                    "\u0000\u022f\u0230\u0003h4\u0000\u0230\u0231\u0007\u0001\u0000\u0000\u0231" +
                    "\u0232\u0003d2\r\u0232\u0249\u0001\u0000\u0000\u0000\u0233\u0234\u0003" +
                    "j5\u0000\u0234\u0235\u0007\u0001\u0000\u0000\u0235\u0236\u0003d2\u000b" +
                    "\u0236\u0249\u0001\u0000\u0000\u0000\u0237\u0238\u0003l6\u0000\u0238\u0239" +
                    "\u0005\u0016\u0000\u0000\u0239\u023a\u0003d2\b\u023a\u0249\u0001\u0000" +
                    "\u0000\u0000\u023b\u023c\u0003h4\u0000\u023c\u023d\u0007\u0002\u0000\u0000" +
                    "\u023d\u023e\u0003d2\u0006\u023e\u0249\u0001\u0000\u0000\u0000\u023f\u0240" +
                    "\u0003j5\u0000\u0240\u0241\u0007\u0002\u0000\u0000\u0241\u0242\u0003d" +
                    "2\u0004\u0242\u0249\u0001\u0000\u0000\u0000\u0243\u0244\u00050\u0000\u0000" +
                    "\u0244\u0245\u0003d2\u0000\u0245\u0246\u00051\u0000\u0000\u0246\u0249" +
                    "\u0001\u0000\u0000\u0000\u0247\u0249\u0003f3\u0000\u0248\u022e\u0001\u0000" +
                    "\u0000\u0000\u0248\u0233\u0001\u0000\u0000\u0000\u0248\u0237\u0001\u0000" +
                    "\u0000\u0000\u0248\u023b\u0001\u0000\u0000\u0000\u0248\u023f\u0001\u0000" +
                    "\u0000\u0000\u0248\u0243\u0001\u0000\u0000\u0000\u0248\u0247\u0001\u0000" +
                    "\u0000\u0000\u0249\u0261\u0001\u0000\u0000\u0000\u024a\u024b\n\n\u0000" +
                    "\u0000\u024b\u024c\u0007\u0001\u0000\u0000\u024c\u0260\u0003d2\u000b\u024d" +
                    "\u024e\n\u0003\u0000\u0000\u024e\u024f\u0007\u0002\u0000\u0000\u024f\u0260" +
                    "\u0003d2\u0004\u0250\u0251\n\u000e\u0000\u0000\u0251\u0252\u0007\u0001" +
                    "\u0000\u0000\u0252\u0260\u0003h4\u0000\u0253\u0254\n\f\u0000\u0000\u0254" +
                    "\u0255\u0007\u0001\u0000\u0000\u0255\u0260\u0003j5\u0000\u0256\u0257\n" +
                    "\t\u0000\u0000\u0257\u0258\u0005\u0016\u0000\u0000\u0258\u0260\u0003l" +
                    "6\u0000\u0259\u025a\n\u0007\u0000\u0000\u025a\u025b\u0007\u0002\u0000" +
                    "\u0000\u025b\u0260\u0003h4\u0000\u025c\u025d\n\u0005\u0000\u0000\u025d" +
                    "\u025e\u0007\u0002\u0000\u0000\u025e\u0260\u0003j5\u0000\u025f\u024a\u0001" +
                    "\u0000\u0000\u0000\u025f\u024d\u0001\u0000\u0000\u0000\u025f\u0250\u0001" +
                    "\u0000\u0000\u0000\u025f\u0253\u0001\u0000\u0000\u0000\u025f\u0256\u0001" +
                    "\u0000\u0000\u0000\u025f\u0259\u0001\u0000\u0000\u0000\u025f\u025c\u0001" +
                    "\u0000\u0000\u0000\u0260\u0263\u0001\u0000\u0000\u0000\u0261\u025f\u0001" +
                    "\u0000\u0000\u0000\u0261\u0262\u0001\u0000\u0000\u0000\u0262e\u0001\u0000" +
                    "\u0000\u0000\u0263\u0261\u0001\u0000\u0000\u0000\u0264\u0268\u0003\u0084" +
                    "B\u0000\u0265\u0268\u00030\u0018\u0000\u0266\u0268\u0003b1\u0000\u0267" +
                    "\u0264\u0001\u0000\u0000\u0000\u0267\u0265\u0001\u0000\u0000\u0000\u0267" +
                    "\u0266\u0001\u0000\u0000\u0000\u0268g\u0001\u0000\u0000\u0000\u0269\u026a" +
                    "\u00064\uffff\uffff\u0000\u026a\u026b\u00050\u0000\u0000\u026b\u026c\u0003" +
                    "h4\u0000\u026c\u026d\u00051\u0000\u0000\u026d\u0270\u0001\u0000\u0000" +
                    "\u0000\u026e\u0270\u0003~?\u0000\u026f\u0269\u0001\u0000\u0000\u0000\u026f" +
                    "\u026e\u0001\u0000\u0000\u0000\u0270\u0279\u0001\u0000\u0000\u0000\u0271" +
                    "\u0272\n\u0004\u0000\u0000\u0272\u0273\u0007\u0001\u0000\u0000\u0273\u0278" +
                    "\u0003h4\u0005\u0274\u0275\n\u0003\u0000\u0000\u0275\u0276\u0007\u0002" +
                    "\u0000\u0000\u0276\u0278\u0003h4\u0004\u0277\u0271\u0001\u0000\u0000\u0000" +
                    "\u0277\u0274\u0001\u0000\u0000\u0000\u0278\u027b\u0001\u0000\u0000\u0000" +
                    "\u0279\u0277\u0001\u0000\u0000\u0000\u0279\u027a\u0001\u0000\u0000\u0000" +
                    "\u027ai\u0001\u0000\u0000\u0000\u027b\u0279\u0001\u0000\u0000\u0000\u027c" +
                    "\u027d\u00065\uffff\uffff\u0000\u027d\u027e\u0003h4\u0000\u027e\u027f" +
                    "\u0007\u0001\u0000\u0000\u027f\u0280\u0003j5\u0007\u0280\u028b\u0001\u0000" +
                    "\u0000\u0000\u0281\u0282\u0003h4\u0000\u0282\u0283\u0007\u0002\u0000\u0000" +
                    "\u0283\u0284\u0003j5\u0004\u0284\u028b\u0001\u0000\u0000\u0000\u0285\u0286" +
                    "\u00050\u0000\u0000\u0286\u0287\u0003j5\u0000\u0287\u0288\u00051\u0000" +
                    "\u0000\u0288\u028b\u0001\u0000\u0000\u0000\u0289\u028b\u0003|>\u0000\u028a" +
                    "\u027c\u0001\u0000\u0000\u0000\u028a\u0281\u0001\u0000\u0000\u0000\u028a" +
                    "\u0285\u0001\u0000\u0000\u0000\u028a\u0289\u0001\u0000\u0000\u0000\u028b" +
                    "\u029a\u0001\u0000\u0000\u0000\u028c\u028d\n\b\u0000\u0000\u028d\u028e" +
                    "\u0007\u0001\u0000\u0000\u028e\u0299\u0003j5\t\u028f\u0290\n\u0005\u0000" +
                    "\u0000\u0290\u0291\u0007\u0002\u0000\u0000\u0291\u0299\u0003j5\u0006\u0292" +
                    "\u0293\n\u0006\u0000\u0000\u0293\u0294\u0007\u0001\u0000\u0000\u0294\u0299" +
                    "\u0003h4\u0000\u0295\u0296\n\u0003\u0000\u0000\u0296\u0297\u0007\u0002" +
                    "\u0000\u0000\u0297\u0299\u0003h4\u0000\u0298\u028c\u0001\u0000\u0000\u0000" +
                    "\u0298\u028f\u0001\u0000\u0000\u0000\u0298\u0292\u0001\u0000\u0000\u0000" +
                    "\u0298\u0295\u0001\u0000\u0000\u0000\u0299\u029c\u0001\u0000\u0000\u0000" +
                    "\u029a\u0298\u0001\u0000\u0000\u0000\u029a\u029b\u0001\u0000\u0000\u0000" +
                    "\u029bk\u0001\u0000\u0000\u0000\u029c\u029a\u0001\u0000\u0000\u0000\u029d" +
                    "\u029e\u00066\uffff\uffff\u0000\u029e\u029f\u0003h4\u0000\u029f\u02a0" +
                    "\u0005\u0016\u0000\u0000\u02a0\u02a1\u0003l6\u0003\u02a1\u02a4\u0001\u0000" +
                    "\u0000\u0000\u02a2\u02a4\u0003z=\u0000\u02a3\u029d\u0001\u0000\u0000\u0000" +
                    "\u02a3\u02a2\u0001\u0000\u0000\u0000\u02a4\u02ad\u0001\u0000\u0000\u0000" +
                    "\u02a5\u02a6\n\u0002\u0000\u0000\u02a6\u02a7\u0005\u0014\u0000\u0000\u02a7" +
                    "\u02ac\u0003l6\u0003\u02a8\u02a9\n\u0004\u0000\u0000\u02a9\u02aa\u0005" +
                    "\u0016\u0000\u0000\u02aa\u02ac\u0003h4\u0000\u02ab\u02a5\u0001\u0000\u0000" +
                    "\u0000\u02ab\u02a8\u0001\u0000\u0000\u0000\u02ac\u02af\u0001\u0000\u0000" +
                    "\u0000\u02ad\u02ab\u0001\u0000\u0000\u0000\u02ad\u02ae\u0001\u0000\u0000" +
                    "\u0000\u02aem\u0001\u0000\u0000\u0000\u02af\u02ad\u0001\u0000\u0000\u0000" +
                    "\u02b0\u02b1\u0003p8\u0000\u02b1\u02b2\u0005\'\u0000\u0000\u02b2\u02b3" +
                    "\u0003n7\u0000\u02b3\u02c6\u0001\u0000\u0000\u0000\u02b4\u02b5\u0003p" +
                    "8\u0000\u02b5\u02b6\u0005-\u0000\u0000\u02b6\u02b7\u0003n7\u0000\u02b7" +
                    "\u02c6\u0001\u0000\u0000\u0000\u02b8\u02b9\u0003p8\u0000\u02b9\u02ba\u0005" +
                    "(\u0000\u0000\u02ba\u02bb\u0003n7\u0000\u02bb\u02c6\u0001\u0000\u0000" +
                    "\u0000\u02bc\u02bd\u0003p8\u0000\u02bd\u02be\u0005.\u0000\u0000\u02be" +
                    "\u02bf\u0003n7\u0000\u02bf\u02c6\u0001\u0000\u0000\u0000\u02c0\u02c1\u0005" +
                    "0\u0000\u0000\u02c1\u02c2\u0003n7\u0000\u02c2\u02c3\u00051\u0000\u0000" +
                    "\u02c3\u02c6\u0001\u0000\u0000\u0000\u02c4\u02c6\u0003p8\u0000\u02c5\u02b0" +
                    "\u0001\u0000\u0000\u0000\u02c5\u02b4\u0001\u0000\u0000\u0000\u02c5\u02b8" +
                    "\u0001\u0000\u0000\u0000\u02c5\u02bc\u0001\u0000\u0000\u0000\u02c5\u02c0" +
                    "\u0001\u0000\u0000\u0000\u02c5\u02c4\u0001\u0000\u0000\u0000\u02c6o\u0001" +
                    "\u0000\u0000\u0000\u02c7\u02c8\u0003r9\u0000\u02c8\u02c9\u0007\u0003\u0000" +
                    "\u0000\u02c9\u02ca\u0003r9\u0000\u02ca\u02d0\u0001\u0000\u0000\u0000\u02cb" +
                    "\u02cc\u0003r9\u0000\u02cc\u02cd\u0007\u0004\u0000\u0000\u02cd\u02ce\u0003" +
                    "r9\u0000\u02ce\u02d0\u0001\u0000\u0000\u0000\u02cf\u02c7\u0001\u0000\u0000" +
                    "\u0000\u02cf\u02cb\u0001\u0000\u0000\u0000\u02d0q\u0001\u0000\u0000\u0000" +
                    "\u02d1\u02d5\u00032\u0019\u0000\u02d2\u02d5\u0003b1\u0000\u02d3\u02d5" +
                    "\u0003\u0084B\u0000\u02d4\u02d1\u0001\u0000\u0000\u0000\u02d4\u02d2\u0001" +
                    "\u0000\u0000\u0000\u02d4\u02d3\u0001\u0000\u0000\u0000\u02d5s\u0001\u0000" +
                    "\u0000\u0000\u02d6\u02d7\u0003\u0084B\u0000\u02d7u\u0001\u0000\u0000\u0000" +
                    "\u02d8\u02d9\u0006;\uffff\uffff\u0000\u02d9\u02f3\u0003t:\u0000\u02da" +
                    "\u02f3\u0003Z-\u0000\u02db\u02f3\u0003\\.\u0000\u02dc\u02f3\u0003h4\u0000" +
                    "\u02dd\u02f3\u0003j5\u0000\u02de\u02f3\u0003l6\u0000\u02df\u02f3\u0003" +
                    "\b\u0004\u0000\u02e0\u02f3\u0003\u0006\u0003\u0000\u02e1\u02f3\u0003R" +
                    ")\u0000\u02e2\u02f3\u0003X,\u0000\u02e3\u02f3\u0003V+\u0000\u02e4\u02f3" +
                    "\u0003T*\u0000\u02e5\u02f3\u0003P(\u0000\u02e6\u02f3\u0003$\u0012\u0000" +
                    "\u02e7\u02f3\u0003z=\u0000\u02e8\u02f3\u0003\u0080@\u0000\u02e9\u02f3" +
                    "\u0003|>\u0000\u02ea\u02f3\u0003~?\u0000\u02eb\u02f3\u0003\u0082A\u0000" +
                    "\u02ec\u02ed\u0007\u0005\u0000\u0000\u02ed\u02f3\u0003v;\n\u02ee\u02ef" +
                    "\u00050\u0000\u0000\u02ef\u02f0\u0003v;\u0000\u02f0\u02f1\u00051\u0000" +
                    "\u0000\u02f1\u02f3\u0001\u0000\u0000\u0000\u02f2\u02d8\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02da\u0001\u0000\u0000\u0000\u02f2\u02db\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02dc\u0001\u0000\u0000\u0000\u02f2\u02dd\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02de\u0001\u0000\u0000\u0000\u02f2\u02df\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02e0\u0001\u0000\u0000\u0000\u02f2\u02e1\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02e2\u0001\u0000\u0000\u0000\u02f2\u02e3\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02e4\u0001\u0000\u0000\u0000\u02f2\u02e5\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02e6\u0001\u0000\u0000\u0000\u02f2\u02e7\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02e8\u0001\u0000\u0000\u0000\u02f2\u02e9\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02ea\u0001\u0000\u0000\u0000\u02f2\u02eb\u0001\u0000\u0000" +
                    "\u0000\u02f2\u02ec\u0001\u0000\u0000\u0000\u02f2\u02ee\u0001\u0000\u0000" +
                    "\u0000\u02f3\u0311\u0001\u0000\u0000\u0000\u02f4\u02f5\n\u000b\u0000\u0000" +
                    "\u02f5\u02f6\u0005\u0019\u0000\u0000\u02f6\u0310\u0003v;\f\u02f7\u02f8" +
                    "\n\t\u0000\u0000\u02f8\u02f9\u0007\u0001\u0000\u0000\u02f9\u0310\u0003" +
                    "v;\n\u02fa\u02fb\n\b\u0000\u0000\u02fb\u02fc\u0007\u0002\u0000\u0000\u02fc" +
                    "\u0310\u0003v;\t\u02fd\u02fe\n\u0007\u0000\u0000\u02fe\u02ff\u0007\u0006" +
                    "\u0000\u0000\u02ff\u0310\u0003v;\b\u0300\u0301\n\u0006\u0000\u0000\u0301" +
                    "\u0302\u0005\'\u0000\u0000\u0302\u0310\u0003v;\u0007\u0303\u0304\n\u0005" +
                    "\u0000\u0000\u0304\u0305\u0007\u0007\u0000\u0000\u0305\u0310\u0003v;\u0006" +
                    "\u0306\u0307\n\u0004\u0000\u0000\u0307\u0308\u0007\u0003\u0000\u0000\u0308" +
                    "\u0310\u0003v;\u0005\u0309\u030a\n\u0003\u0000\u0000\u030a\u030b\u0007" +
                    "\u0004\u0000\u0000\u030b\u0310\u0003v;\u0004\u030c\u030d\n\u0002\u0000" +
                    "\u0000\u030d\u030e\u0007\b\u0000\u0000\u030e\u0310\u0003v;\u0003\u030f" +
                    "\u02f4\u0001\u0000\u0000\u0000\u030f\u02f7\u0001\u0000\u0000\u0000\u030f" +
                    "\u02fa\u0001\u0000\u0000\u0000\u030f\u02fd\u0001\u0000\u0000\u0000\u030f" +
                    "\u0300\u0001\u0000\u0000\u0000\u030f\u0303\u0001\u0000\u0000\u0000\u030f" +
                    "\u0306\u0001\u0000\u0000\u0000\u030f\u0309\u0001\u0000\u0000\u0000\u030f" +
                    "\u030c\u0001\u0000\u0000\u0000\u0310\u0313\u0001\u0000\u0000\u0000\u0311" +
                    "\u030f\u0001\u0000\u0000\u0000\u0311\u0312\u0001\u0000\u0000\u0000\u0312" +
                    "w\u0001\u0000\u0000\u0000\u0313\u0311\u0001\u0000\u0000\u0000\u0314\u0315" +
                    "\u0005\u0010\u0000\u0000\u0315y\u0001\u0000\u0000\u0000\u0316\u0317\u0005" +
                    "\u0001\u0000\u0000\u0317{\u0001\u0000\u0000\u0000\u0318\u0319\u00059\u0000" +
                    "\u0000\u0319}\u0001\u0000\u0000\u0000\u031a\u031b\u00058\u0000\u0000\u031b" +
                    "\u007f\u0001\u0000\u0000\u0000\u031c\u031d\u0007\t\u0000\u0000\u031d\u0081" +
                    "\u0001\u0000\u0000\u0000\u031e\u031f\u00054\u0000\u0000\u031f\u0083\u0001" +
                    "\u0000\u0000\u0000\u0320\u0321\u0005:\u0000\u0000\u0321\u0085\u0001\u0000" +
                    "\u0000\u0000\u0322\u0323\u0005;\u0000\u0000\u0323\u0087\u0001\u0000\u0000" +
                    "\u0000\u0324\u0325\u0005<\u0000\u0000\u0325\u0089\u0001\u0000\u0000\u0000" +
                    "\u0326\u0327\u0006E\uffff\uffff\u0000\u0327\u032a\u0005\u0003\u0000\u0000" +
                    "\u0328\u032a\u0003\u008eG\u0000\u0329\u0326\u0001\u0000\u0000\u0000\u0329" +
                    "\u0328\u0001\u0000\u0000\u0000\u032a\u0331\u0001\u0000\u0000\u0000\u032b" +
                    "\u032c\n\u0004\u0000\u0000\u032c\u0330\u0005\u0003\u0000\u0000\u032d\u032e" +
                    "\n\u0003\u0000\u0000\u032e\u0330\u0003\u008eG\u0000\u032f\u032b\u0001" +
                    "\u0000\u0000\u0000\u032f\u032d\u0001\u0000\u0000\u0000\u0330\u0333\u0001" +
                    "\u0000\u0000\u0000\u0331\u032f\u0001\u0000\u0000\u0000\u0331\u0332\u0001" +
                    "\u0000\u0000\u0000\u0332\u008b\u0001\u0000\u0000\u0000\u0333\u0331\u0001" +
                    "\u0000\u0000\u0000\u0334\u0335\u0005\u000b\u0000\u0000\u0335\u008d\u0001" +
                    "\u0000\u0000\u0000\u0336\u0337\u0005\u0004\u0000\u0000\u0337\u008f\u0001" +
                    "\u0000\u0000\u00008\u0098\u00a0\u00ad\u00d4\u00d8\u00e1\u00eb\u00ff\u010b" +
                    "\u0110\u0116\u011e\u0127\u013e\u0156\u016e\u018c\u0194\u019e\u01ad\u01bb" +
                    "\u01c8\u01ca\u01d5\u01df\u01e9\u01f3\u01fd\u020f\u0215\u0219\u0220\u0228" +
                    "\u022c\u0248\u025f\u0261\u0267\u026f\u0277\u0279\u028a\u0298\u029a\u02a3" +
                    "\u02ab\u02ad\u02c5\u02cf\u02d4\u02f2\u030f\u0311\u0329\u032f\u0331";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}