package com.scut.mplas.ruby;

import com.scut.mplas.graphs.ast.ASNode;
import com.scut.mplas.graphs.ast.AbstractSyntaxTree;
import com.scut.mplas.ruby.parser.RubyBaseVisitor;
import com.scut.mplas.ruby.parser.RubyLexer;
import com.scut.mplas.ruby.parser.RubyParser;
import ghaffarian.nanologger.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

public class RubyASTBuilder {
    /**
     * ‌Build and return the Abstract Syntax Tree (AST) for the given Java source file.
     */
    public static AbstractSyntaxTree build(String rubyFile) throws IOException {
        return build(new File(rubyFile));
    }

    public static AbstractSyntaxTree build(File rubyFile) throws IOException {
        if (!rubyFile.getName().endsWith(".rb"))
            throw new IOException("Not a ruby File!");
        InputStream inFile = new FileInputStream(rubyFile);
        ANTLRInputStream input = new ANTLRInputStream(inFile);
        RubyLexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        ParseTree tree = parser.prog();
        return build(rubyFile.getPath(), tree);
    }

    public static AbstractSyntaxTree build(String fileName, InputStream inputStream) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(inputStream);
        RubyLexer lexer = new RubyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RubyParser parser = new RubyParser(tokens);
        ParseTree tree = parser.prog();
        return build(fileName, tree);
    }

    public static AbstractSyntaxTree build(String fileName, ParseTree tree) {
        AbstractSyntaxVisitor visitor = new AbstractSyntaxVisitor(fileName, null, null);
        return visitor.build(tree);
    }

    private static class AbstractSyntaxVisitor extends RubyBaseVisitor<String> {
        private String propKey;
        private String typeModifier;
        private String memberModifier;
        private Deque<ASNode> parentStack;
        private final AbstractSyntaxTree AST;
        private Map<String, String> vars, fields, methods;
        private int varsCounter, fieldsCounter, methodsCounter;
        private Map<ParserRuleContext, Object> contexutalProperties;

        public AbstractSyntaxVisitor(String fileName, String propKey, Map<ParserRuleContext, Object> ctxProps) {
            parentStack = new ArrayDeque<>();
            AST = new AbstractSyntaxTree(fileName);
            this.propKey = propKey;
            contexutalProperties = ctxProps;
            vars = new LinkedHashMap<>();
            fields = new LinkedHashMap<>();
            methods = new LinkedHashMap<>();
            varsCounter = 0;
            fieldsCounter = 0;
            methodsCounter = 0;
        }

        public AbstractSyntaxTree build(ParseTree tree) {
            RubyParser.ProgContext rootCtx = (RubyParser.ProgContext) tree;
            AST.root.setCode(AST.fileName);
            parentStack.push(AST.root);
            // prog : expression_list EOF;
            visit(rootCtx.expression_list());

            parentStack.pop();
            vars.clear();
            fields.clear();
            methods.clear();
            methods.clear();
            return AST;
        }

        @Override
        public String visitExpression_list(RubyParser.Expression_listContext ctx) {
            // expression_list : expression terminator
            //                | expression_list expression terminator
            //                | terminator
            //                ;
            Logger.info("expList -- " + ctx.getText());

            if (ctx.expression_list() == null) {
                Logger.info("exp--" + ctx.expression().getText());
                ASNode expNode = new ASNode(ASNode.Type.RUBY_EXPRESSION);
                expNode.setLineOfCode(ctx.expression().getStart().getLine());
                AST.addVertex(expNode);
                AST.addEdge(parentStack.peek(), expNode);
                parentStack.push(expNode);
                visit(ctx.expression());
                parentStack.pop();
            } else {
                Logger.info("expList--" + ctx.expression_list().getText());
                // 递归访问
                visit(ctx.expression_list());
                ASNode expNode = new ASNode(ASNode.Type.RUBY_EXPRESSION);
                expNode.setLineOfCode(ctx.expression().getStart().getLine());
                AST.addVertex(expNode);
                AST.addEdge(parentStack.peek(), expNode);
                parentStack.push(expNode);
                visit(ctx.expression());
                parentStack.pop();

            }
            // Terminator是一定会有的
//            ASNode terminatorNode = new ASNode(ASNode.Type.RUBY_TERMINATOR);
//            terminatorNode.setLineOfCode(ctx.terminator().getStart().getLine());
//            terminatorNode.setCode(ctx.terminator().getText());
//            AST.addVertex(terminatorNode);
//            AST.addEdge(parentStack.peek(), terminatorNode);
            return "";
        }

        @Override
        public String visitExpression(RubyParser.ExpressionContext ctx) {
            // expression : function_definition
            //           | function_inline_call
            //           | require_block
            //           | rvalue
            //           | unless_statement
            //           | if_statement
            //           | return_statement
            //           | while_statement
            //           | for_statement
            //           | pir_inline
            //           ;
            if (ctx.function_definition() != null) {
                Logger.info("visit function_definition");
                ASNode funcDefNode = new ASNode(ASNode.Type.RUBY_FUNCTION);
                funcDefNode.setLineOfCode(ctx.function_definition().getStart().getLine());
                AST.addVertex(funcDefNode);
                AST.addEdge(parentStack.peek(), funcDefNode);
                parentStack.push(funcDefNode);
                visit(ctx.function_definition());
                parentStack.pop();
            } else if (ctx.function_inline_call() != null) {
                Logger.info("visit function_inline_call");
                ASNode funcInlineCallNode = new ASNode(ASNode.Type.RUBY_FUNCTION_CALL);
                funcInlineCallNode.setLineOfCode(ctx.function_inline_call().getStart().getLine());
                funcInlineCallNode.setCode(visit(ctx.function_inline_call()));
                AST.addVertex(funcInlineCallNode);
                AST.addEdge(parentStack.peek(), funcInlineCallNode);
//                parentStack.push(funcInlineCallNode);
//                visit(ctx.function_inline_call());
//                parentStack.pop();
            } else if (ctx.require_block() != null) {
                Logger.info("visit require_block");
                ASNode requireBlockNode = new ASNode(ASNode.Type.RUBY_REQUIRE_BLOCK);
                requireBlockNode.setLineOfCode(ctx.require_block().getStart().getLine());
                requireBlockNode.setCode(visitRequire_block(ctx.require_block()));
                AST.addVertex(requireBlockNode);
                AST.addEdge(parentStack.peek(), requireBlockNode);
//                parentStack.push(requireBlockNode);
//                visit(ctx.function_inline_call());
//                parentStack.pop();
            } else if (ctx.pir_inline() != null) {
                Logger.info("visit pir_inline");
                ASNode pirNode = new ASNode(ASNode.Type.RUBY_PIR_INLINE);
                pirNode.setLineOfCode(ctx.pir_inline().getStart().getLine());
                pirNode.setCode(ctx.pir_inline().PIR().getText() + ctx.pir_inline().crlf().getText());
                AST.addVertex(pirNode);
                AST.addEdge(parentStack.peek(), pirNode);
                parentStack.push(pirNode);
                visit(ctx.pir_inline().pir_expression_list());
                parentStack.pop();
            } else if (ctx.rvalue() != null) {
                Logger.info("visit rvalue");
                ASNode rvalueNode = new ASNode(ASNode.Type.RUBY_RVALUE);
                rvalueNode.setLineOfCode(ctx.rvalue().getStart().getLine());
                AST.addVertex(rvalueNode);
                AST.addEdge(parentStack.peek(), rvalueNode);
                parentStack.push(rvalueNode);
                visit(ctx.rvalue());
                parentStack.pop();
            } else {
                Logger.info("visit statement");
                // 直接visit statement部分
                visitChildren(ctx);
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitHash_expression(RubyParser.Hash_expressionContext ctx) {
            ASNode hashNode = new ASNode(ASNode.Type.RUBY_HASH_EXP);
            hashNode.setLineOfCode(ctx.getStart().getLine());
            hashNode.setCode(getOriginalCodeText(ctx));
            AST.addVertex(hashNode);
            AST.addEdge(parentStack.peek(), hashNode);
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitClass_definition(RubyParser.Class_definitionContext ctx) {
            // class_definition : 'class' lvalue  ( '<'superclass_id = lvalue )? CRLF statement_expression_list? CRLF END;
            ASNode classNode = new ASNode(ASNode.Type.CLASS);
            classNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("adding a class node...");
            AST.addVertex(classNode);
            AST.addEdge(parentStack.peek(), classNode);

            // lvalue -> id_
            ASNode nameNode = new ASNode(ASNode.Type.NAME);
            String className = getOriginalCodeText(ctx.lvalue(0));
            nameNode.setLineOfCode(ctx.getStart().getLine());
            nameNode.setCode("CLASS_" + className);
            Logger.debug("adding a class name: " + className);
            AST.addVertex(nameNode);
            AST.addEdge(classNode, nameNode);
            // 继承
            if (ctx.superclass_id != null) {
                ASNode superClassNode = new ASNode(ASNode.Type.RUBY_SUPER);
                superClassNode.setLineOfCode(ctx.superclass_id.getStart().getLine());
                superClassNode.setCode(getOriginalCodeText(ctx.superclass_id));
                Logger.debug("adding super class: " + getOriginalCodeText(ctx.superclass_id));
                AST.addVertex(superClassNode);
                AST.addEdge(classNode, superClassNode);

            }

            // 类体
            if (ctx.statement_expression_list() != null) {
                parentStack.push(classNode);
                visit(ctx.statement_expression_list());
                parentStack.pop();
            }
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitModule_definition(RubyParser.Module_definitionContext ctx) {
            // module_definition: 'module' id_ CRLF statement_expression_list? CRLF END;
            ASNode moduleNode = new ASNode(ASNode.Type.RUBY_MODULE);
            moduleNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("adding a module");
            AST.addVertex(moduleNode);
            AST.addEdge(parentStack.peek(), moduleNode);

            ASNode nameNode = new ASNode(ASNode.Type.NAME);
            nameNode.setLineOfCode(ctx.getStart().getLine());
            String moduleName = getOriginalCodeText(ctx.id_());
            Logger.debug("adding a module name: " + moduleName);
            nameNode.setCode(moduleName);
            AST.addVertex(nameNode);
            AST.addEdge(moduleNode, nameNode);

            if (ctx.statement_expression_list() != null) {
                parentStack.push(moduleNode);
                visit(ctx.statement_expression_list());
                parentStack.pop();
            }

            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitBegin_expression(RubyParser.Begin_expressionContext ctx) {
            ASNode beginNode = new ASNode(ASNode.Type.RUBY_BEGIN);
            beginNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("adding a begin node ");
            AST.addVertex(beginNode);
            AST.addEdge(parentStack.peek(), beginNode);
            parentStack.push(beginNode);
            visit(ctx.statement_body());
            parentStack.pop();
            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitEnd_expression(RubyParser.End_expressionContext ctx) {
            ASNode endNode = new ASNode(ASNode.Type.RUBY_END);
            endNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("adding a begin node ");
            AST.addVertex(endNode);
            AST.addEdge(parentStack.peek(), endNode);
            parentStack.push(endNode);
            visit(ctx.statement_body());
            parentStack.pop();
            return super.visitEnd_expression(ctx);
        }


        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitBegin_rescue_expression(RubyParser.Begin_rescue_expressionContext ctx) {
            // begin_rescue_expression : BEGIN crlf* statement_body
            // (RESCUE error_type? crlf* statement_body )*
            // (else_token crlf statement_body )?
            // (ENSURE crlf* statement_body )?  END ;

            ASNode beginRescueNode = new ASNode(ASNode.Type.RUBY_BEGIN_RESCUE);
            beginRescueNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("analysing a begin rescue expression");
            AST.addVertex(beginRescueNode);
            AST.addEdge(parentStack.peek(), beginRescueNode);

            parentStack.push((beginRescueNode));
            // begin块的语句
            visit(ctx.statement_body(0));
            parentStack.pop();
            int size = ctx.rescue_expression().size();
            // rescue语句
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    String errorType = getOriginalCodeText(ctx.error_type(i));
                    ASNode rescueNode = new ASNode(ASNode.Type.RUBY_RESCUE);
                    rescueNode.setLineOfCode(ctx.rescue_expression(i).getStart().getLine());
                    rescueNode.setCode(errorType);
                    AST.addVertex(rescueNode);
                    AST.addEdge(beginRescueNode, rescueNode);

                    parentStack.push(rescueNode);
                    visit(ctx.statement_body(i + 1));
                    parentStack.pop();
                }
            }
            // else 语句
            if (ctx.else_token() != null) {
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.else_token().getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(beginRescueNode, elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement_body(size + 1));
                parentStack.pop();
            }
            // ensure语句
            if (ctx.ensure_expression() != null) {
                ASNode ensure = new ASNode(ASNode.Type.RUBY_ENSURE);
                ensure.setLineOfCode(ctx.ensure_expression().getStart().getLine());
                AST.addVertex(ensure);
                AST.addEdge(beginRescueNode, ensure);
                parentStack.push(ensure);
                visit(ctx.statement_body(size + 2));
                parentStack.pop();
            }
            return "";
        }

        @Override
        public String visitGlobal_get(RubyParser.Global_getContext ctx) {
            ASNode globalGet = new ASNode(ASNode.Type.RUBY_GLOBAL_GET);

            globalGet.setLineOfCode(ctx.getStart().getLine());
            globalGet.setCode(visitLvalue(ctx.var_name) + ctx.op.getText() + ctx.global_name.getText());
            AST.addVertex(globalGet);
            AST.addEdge(parentStack.peek(), globalGet);
            return globalGet.getCode();
        }

        @Override
        public String visitGlobal_set(RubyParser.Global_setContext ctx) {
            ASNode globalSet = new ASNode(ASNode.Type.RUBY_GLOBAL_SET);
            String id = ctx.global_name.getText();
            if (!vars.containsKey(id)) {
                varsCounter++;
                String normalized = "$GLOBALVARL_" + varsCounter;
                globalSet.setNormalizedCode(normalized);
                vars.put(id, normalized);
            }
            globalSet.setLineOfCode(ctx.getStart().getLine());
            globalSet.setCode(ctx.global_name.getText() + ctx.op.getText() + visitAll_result(ctx.all_result()));
            AST.addVertex(globalSet);
            AST.addEdge(parentStack.peek(), globalSet);
            return globalSet.getCode();
        }

        @Override
        public String visitGlobal_result(RubyParser.Global_resultContext ctx) {
            ASNode globalResult = new ASNode(ASNode.Type.RUBY_GLOBAL_RESULT);
            globalResult.setLineOfCode(ctx.getStart().getLine());
            globalResult.setCode(getOriginalCodeText(ctx.id_global()));
            AST.addVertex(globalResult);
            AST.addEdge(parentStack.peek(), globalResult);
            return globalResult.getCode();
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitInstance_get(RubyParser.Instance_getContext ctx) {
            ASNode instanceGet = new ASNode(ASNode.Type.RUBY_INSTANCE_GET);
            instanceGet.setLineOfCode(ctx.getStart().getLine());
            instanceGet.setCode(ctx.instance_name.getText() + ctx.op.getText() + ctx.result.getText());
            AST.addVertex(instanceGet);
            AST.addEdge(parentStack.peek(), instanceGet);
            return instanceGet.getCode();
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitInstance_set(RubyParser.Instance_setContext ctx) {
            ASNode instanceSet = new ASNode(ASNode.Type.RUBY_INSTANCE_SET);
            String id = ctx.instance_name.getText();
            if (!fields.containsKey(id)) {
                fieldsCounter++;
                String normalized = "$VARL_" + fieldsCounter;
                instanceSet.setNormalizedCode(normalized);
                fields.put(id, normalized);
            }
            instanceSet.setLineOfCode(ctx.getStart().getLine());
            instanceSet.setCode(ctx.instance_name.getText() + ctx.op.getText() + ctx.result.getText());
            AST.addVertex(instanceSet);
            AST.addEdge(parentStack.peek(), instanceSet);
            return instanceSet.getCode();

        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitInstance_result(RubyParser.Instance_resultContext ctx) {
            ASNode globalResult = new ASNode(ASNode.Type.RUBY_GLOBAL_RESULT);
            globalResult.setLineOfCode(ctx.getStart().getLine());
            globalResult.setCode(getOriginalCodeText(ctx.id_instance()));
            AST.addVertex(globalResult);
            AST.addEdge(parentStack.peek(), globalResult);
            return globalResult.getCode();
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitConst_set(RubyParser.Const_setContext ctx) {
            return super.visitConst_set(ctx);
        }

        @Override
        public String visitFunction_inline_call(RubyParser.Function_inline_callContext ctx) {
            return visitFunction_call(ctx.function_call());
        }

        @Override
        public String visitRequire_block(RubyParser.Require_blockContext ctx) {

            return ctx.REQUIRE().getText() + ctx.literal_t().LITERAL().getText();
        }

        @Override
        public String visitPir_inline(RubyParser.Pir_inlineContext ctx) {
            return super.visitPir_inline(ctx);
        }

        @Override
        public String visitPir_expression_list(RubyParser.Pir_expression_listContext ctx) {
            return super.visitPir_expression_list(ctx);
        }

        @Override
        public String visitFunction_definition(RubyParser.Function_definitionContext ctx) {
            // function_definition : function_definition_header function_definition_body END;
            visit(ctx.function_definition_header());
            visit(ctx.function_definition_body());
            ASNode funcEND = new ASNode(ASNode.Type.RUBY_END);
            funcEND.setLineOfCode(ctx.getStart().getLine());
            funcEND.setCode("END");
            AST.addVertex(funcEND);
            AST.addEdge(parentStack.peek(), funcEND);
            return "";
        }

        @Override
        public String visitFunction_definition_body(RubyParser.Function_definition_bodyContext ctx) {
            // function_definition_body : expression_list;
            ASNode funcBody = new ASNode(ASNode.Type.BLOCK);
            funcBody.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(funcBody);
            AST.addEdge(parentStack.peek(), funcBody);
            parentStack.push(funcBody);
            visit(ctx.expression_list());
            parentStack.pop();
            resetLocalVars();
            return "";
        }

        @Override
        public String visitFunction_definition_header(RubyParser.Function_definition_headerContext ctx) {
            // function_definition_header : DEF function_name crlf
            //                           | DEF function_name function_definition_params crlf
            //                           ;
            ++methodsCounter;
            ASNode funcDEF = new ASNode(ASNode.Type.RUBY_FUNCTION_DEF);
            funcDEF.setLineOfCode(ctx.getStart().getLine());
            funcDEF.setCode("DEF");
            AST.addVertex(funcDEF);
            AST.addEdge(parentStack.peek(), funcDEF);

            ASNode funcName = new ASNode(ASNode.Type.NAME);
            funcName.setCode(visitFunction_name(ctx.function_name()));
            funcName.setLineOfCode(ctx.function_name().getStart().getLine());
            String normalized = "$FUNC_" + methodsCounter;
            funcName.setNormalizedCode(normalized);
            methods.put(funcName.getCode(), normalized);
            AST.addVertex(funcName);
            AST.addEdge(parentStack.peek(), funcName);

            if (ctx.function_definition_params() != null) {
                visit(ctx.function_definition_params());
            }
            return "";
        }

        @Override
        public String visitFunction_name(RubyParser.Function_nameContext ctx) {
            // function_name : id_function
            //              | id_
            //              ;
            if (ctx.id_function() != null) {
                return ctx.id_function().getText();
            }
            return ctx.id_().getText();
        }

        @Override
        public String visitFunction_definition_params(RubyParser.Function_definition_paramsContext ctx) {
            if (ctx.function_definition_params_list() != null) {
                visit(ctx.function_definition_params_list());
            }
            return "";
        }

        @Override
        public String visitFunction_definition_params_list(RubyParser.Function_definition_params_listContext ctx) {
            // function_definition_params_list : function_definition_param_id
            //                                | function_definition_params_list COMMA function_definition_param_id
            //                                ;
            // function_definition_param_id : id_;
            ++varsCounter;
            String id = visitFunction_definition_param_id(ctx.function_definition_param_id());
            String normalized = "$VARL_" + varsCounter;
            vars.put(id, normalized);
            ASNode param = new ASNode(ASNode.Type.RUBY_VAR);
            param.setLineOfCode(ctx.function_definition_param_id().getStart().getLine());
            param.setCode(id);
            param.setNormalizedCode(normalized);
            AST.addVertex(param);
            AST.addEdge(parentStack.peek(), param);
            return "";
        }

        @Override
        public String visitFunction_definition_param_id(RubyParser.Function_definition_param_idContext ctx) {
            return ctx.id_().getText();
        }

        @Override
        public String visitReturn_statement(RubyParser.Return_statementContext ctx) {
            ASNode returnNode = new ASNode(ASNode.Type.RETURN);
            returnNode.setLineOfCode(ctx.start.getLine());
            AST.addVertex(returnNode);
            AST.addEdge(parentStack.peek(), returnNode);

            ASNode result = new ASNode(ASNode.Type.RUBY_RESULT);
            result.setLineOfCode(ctx.all_result().start.getLine());
            String normalize = visitAll_result(ctx.all_result());
            result.setCode(normalize);
            result.setNormalizedCode(normalize);
            AST.addVertex(result);
            AST.addEdge(parentStack.peek(), result);
            return "";
        }

        @Override
        public String visitFunction_call(RubyParser.Function_callContext ctx) {
            // function_call : name=function_name LEFT_RBRACKET params=function_call_param_list RIGHT_RBRACKET
            //              | name=function_name params=function_call_param_list
            //              | name=function_name LEFT_RBRACKET RIGHT_RBRACKET
            //              | id_ '.' name=function_name LEFT_RBRACKET (params=function_call_param_list) ? RIGHT_RBRACKET
            //              | id_ '.' name=function_name (params=function_call_param_list)?
            //              | id_ '::' name=function_name LEFT_RBRACKET (params=function_call_param_list) ? RIGHT_RBRACKET
            //              | id_ '::' name=function_name (params=function_call_param_list)?
            //              ;

            return (ctx.id_() == null ? "" : getOriginalCodeText(ctx.id_()) + ".")
                    + visitFunction_name(ctx.function_name())
                    + '('
                    + visitFunction_call_param_list(ctx.function_call_param_list()) + ')';
        }

        @Override
        public String visitFunction_call_param_list(RubyParser.Function_call_param_listContext ctx) {
            if (ctx != null) {
                return visit(ctx.function_call_params());
            }
            return "";
        }

        @Override
        public String visitFunction_call_params(RubyParser.Function_call_paramsContext ctx) {
            if (ctx.function_param() != null) {
                return visit(ctx.function_param());
            }

            return visit(ctx.function_call_params()) + ',' + visit(ctx.function_param());
        }

        @Override
        public String visitFunction_param(RubyParser.Function_paramContext ctx) {
            return super.visitFunction_param(ctx);
        }

        @Override
        public String visitFunction_unnamed_param(RubyParser.Function_unnamed_paramContext ctx) {
            return super.visitFunction_unnamed_param(ctx);
        }

        @Override
        public String visitFunction_named_param(RubyParser.Function_named_paramContext ctx) {
            return super.visitFunction_named_param(ctx);
        }

        @Override
        public String visitFunction_call_assignment(RubyParser.Function_call_assignmentContext ctx) {
            return super.visitFunction_call_assignment(ctx);
        }

        @Override
        public String visitAll_result(RubyParser.All_resultContext ctx) {
            if (ctx.int_result() != null) {
                return visit(ctx.int_result());
            } else if (ctx.float_result() != null) {
                return visit(ctx.float_result());
            } else if (ctx.string_result() != null) {
                return visit(ctx.string_result());
            } else if (ctx.dynamic_result() != null) {
                return visit(ctx.dynamic_result());
            } else {
                return visit(ctx.global_result());
            }
        }


        private void visitStatement(ParserRuleContext ctx, String normalized) {
            Logger.printf(Logger.Level.DEBUG, "Visiting: (%d)  %s", ctx.getStart().getLine(), getOriginalCodeText(ctx));
            ASNode statementNode = new ASNode(ASNode.Type.STATEMENT);
            statementNode.setCode(getOriginalCodeText(ctx));
            statementNode.setNormalizedCode(normalized);
            statementNode.setLineOfCode(ctx.getStart().getLine());
            Logger.debug("Adding statement " + ctx.getStart().getLine());
            AST.addVertex(statementNode);
            AST.addEdge(parentStack.peek(), statementNode);
        }

        @Override
        public String visitElsif_statement(RubyParser.Elsif_statementContext ctx) {
            // elsif_statement : if_elsif_statement;
            return visit(ctx.if_elsif_statement());
        }

        @Override
        public String visitIf_elsif_statement(RubyParser.If_elsif_statementContext ctx) {
            // elsif_statement : if_elsif_statement;
            //
            //if_elsif_statement : ELSIF cond_expression crlf statement_body
            //                   | ELSIF cond_expression crlf statement_body else_token crlf statement_body
            //                   | ELSIF cond_expression crlf statement_body if_elsif_statement
            //                   ;
            ASNode elsIFNode = new ASNode(ASNode.Type.CONDITION);
            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
            AST.addVertex(condNode);
            AST.addEdge(parentStack.peek(), condNode);

            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement_body(0).getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(parentStack.peek(), thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement_body(0));
            parentStack.pop();

            if (ctx.else_token() != null) {
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.statement_body(1).getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(parentStack.peek(), elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement_body(1));
                parentStack.pop();
            }
            if (ctx.if_elsif_statement() != null) {
                ASNode elsifNode = new ASNode(ASNode.Type.RUBY_ELSEIF);
                elsifNode.setLineOfCode(ctx.if_elsif_statement().getStart().getLine());
                AST.addVertex(elsifNode);
                AST.addEdge(parentStack.peek(), elsifNode);
                visit(ctx.if_elsif_statement());
            }

            return "";
        }

        @Override
        public String visitIf_statement(RubyParser.If_statementContext ctx) {
            // if_statement : IF cond_expression crlf statement_body END
            //             | IF cond_expression crlf statement_body else_token crlf statement_body END
            //             | IF cond_expression crlf statement_body elsif_statement END
            //             ;
            ASNode ifNode = new ASNode(ASNode.Type.IF);
            ifNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(ifNode);
            AST.addEdge(parentStack.peek(), ifNode);

            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
            AST.addVertex(condNode);
            AST.addEdge(parentStack.peek(), condNode);


            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement_body(0).getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(parentStack.peek(), thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement_body(0));
            parentStack.pop();

            if (ctx.else_token() != null) {
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.statement_body(1).getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(parentStack.peek(), elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement_body(1));
                parentStack.pop();
            }
            if (ctx.elsif_statement() != null) {
                ASNode elsifNode = new ASNode(ASNode.Type.RUBY_ELSEIF);
                elsifNode.setLineOfCode(ctx.elsif_statement().getStart().getLine());
                AST.addVertex(elsifNode);
                AST.addEdge(parentStack.peek(), elsifNode);
                visit(ctx.elsif_statement());
            }

            ASNode endNode = new ASNode(ASNode.Type.RUBY_END);
            endNode.setLineOfCode(ctx.getStop().getLine());
            endNode.setCode(endNode.getType().label);
            AST.addVertex(endNode);
            AST.addEdge(parentStack.peek(), endNode);

            return "";
        }

        @Override
        public String visitUnless_statement(RubyParser.Unless_statementContext ctx) {
            //
            //unless_statement : UNLESS cond_expression crlf statement_body END
            //                 | UNLESS cond_expression crlf statement_body else_token crlf statement_body END
            //                 | UNLESS cond_expression crlf statement_body elsif_statement END
            //                 ;
            //
            ASNode unlessNode = new ASNode(ASNode.Type.RUBY_UNLESS);
            unlessNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(unlessNode);
            AST.addEdge(parentStack.peek(), unlessNode);

            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
            AST.addVertex(condNode);
            AST.addEdge(parentStack.peek(), condNode);

            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement_body(0).getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(parentStack.peek(), thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement_body(0));
            parentStack.pop();

            if (ctx.else_token() != null) {
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.statement_body(1).getStart().getLine());
                AST.addVertex(elseNode);
                AST.addEdge(parentStack.peek(), elseNode);
                parentStack.push(elseNode);
                visit(ctx.statement_body(1));
                parentStack.pop();
            }
            if (ctx.elsif_statement() != null) {
                ASNode elsifNode = new ASNode(ASNode.Type.RUBY_ELSEIF);
                elsifNode.setLineOfCode(ctx.elsif_statement().getStart().getLine());
                AST.addVertex(elsifNode);
                AST.addEdge(parentStack.peek(), elsifNode);
                visit(ctx.elsif_statement());
            }

            ASNode endNode = new ASNode(ASNode.Type.RUBY_END);
            endNode.setLineOfCode(ctx.getStop().getLine());
            endNode.setCode(endNode.getType().label);
            AST.addVertex(endNode);
            AST.addEdge(parentStack.peek(), endNode);

            return "";
        }

        @Override
        public String visitWhile_statement(RubyParser.While_statementContext ctx) {
            // while_statement : WHILE cond_expression crlf statement_body END;
            ASNode whileNode = new ASNode(ASNode.Type.WHILE);
            whileNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(whileNode);
            AST.addEdge(parentStack.peek(), whileNode);

            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
            AST.addVertex(condNode);
            AST.addEdge(parentStack.peek(), condNode);


            ASNode thenNode = new ASNode(ASNode.Type.THEN);
            thenNode.setLineOfCode(ctx.statement_body().getStart().getLine());
            AST.addVertex(thenNode);
            AST.addEdge(parentStack.peek(), thenNode);
            parentStack.push(thenNode);
            visit(ctx.statement_body());
            parentStack.pop();

            ASNode endNode = new ASNode(ASNode.Type.RUBY_END);
            endNode.setLineOfCode(ctx.getStop().getLine());
            endNode.setCode(endNode.getType().label);
            AST.addVertex(endNode);
            AST.addEdge(parentStack.peek(), endNode);


            return "";
        }

        @Override
        public String visitFor_statement(RubyParser.For_statementContext ctx) {
            //for_statement : FOR lvalue (COMMA lvalue)*  IN loop_expression DO? CRLF statement_body END;
            // loop_expression : array_definition;
//            ASNode forNode = new ASNode(ASNode.Type.FOR);
//            forNode.setLineOfCode(ctx.getStart().getLine());
//            AST.addVertex(forNode);
//            AST.addEdge(parentStack.peek(), forNode);
//
//            ASNode forInit = new ASNode(ASNode.Type.FOR_INIT);
//            AST.addVertex(forInit);
//            AST.addEdge(parentStack.peek(), forInit);
//            parentStack.push(forInit);
//            visit(ctx.init_expression());
//            parentStack.pop();
//
//
//            ASNode condNode = new ASNode(ASNode.Type.CONDITION);
//            condNode.setLineOfCode(ctx.cond_expression().getStart().getLine());
//            condNode.setCode(getOriginalCodeText(ctx.cond_expression().comparison_list()));
//            condNode.setNormalizedCode(visit(ctx.cond_expression().comparison_list()));
//            AST.addVertex(condNode);
//            AST.addEdge(parentStack.peek(), condNode);
//
//
//            ASNode loopExpr = new ASNode(ASNode.Type.RUBY_LOOP_EXPR);
//            loopExpr.setLineOfCode(ctx.loop_expression().getStart().getLine());
//            AST.addVertex(loopExpr);
//            AST.addEdge(parentStack.peek(), loopExpr);
//            parentStack.push(loopExpr);
//            visit(ctx.loop_expression());
//            parentStack.pop();
//
//
//            ASNode expr = new ASNode(ASNode.Type.STATEMENT);
//            expr.setLineOfCode(ctx.statement_body().getStart().getLine());
//            AST.addVertex(expr);
//            AST.addEdge(parentStack.peek(), expr);
//            parentStack.push(expr);
//            visit(ctx.statement_body());
//            parentStack.pop();
//
//
//            ASNode endNode = new ASNode(ASNode.Type.RUBY_END);
//            endNode.setLineOfCode(ctx.getStop().getLine());
//            endNode.setCode(endNode.getType().label);
//            AST.addVertex(endNode);
//
//            AST.addEdge(parentStack.peek(), endNode);

            // for_statement : FOR lvalue (COMMA lvalue)*  IN loop_expression DO? CRLF statement_body END
            //              | for_each_statement;
            //
            //for_each_statement:  array_definition DOT EACH LEFT_BBRACKET crlf? '|' id_ '|' crlf? statement_body RIGHT_BBRACKET;
            //
            //cond_expression : comparison_list;
            //
            //loop_expression : array_definition;
            if (ctx.for_each_statement() != null) {
                visit(ctx.for_each_statement());
                return "";
            }
            ASNode forNode = new ASNode(ASNode.Type.FOR);
            forNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(forNode);
            AST.addEdge(parentStack.peek(), forNode);

            for (int i = 0; i < ctx.lvalue().size(); i++) {
                ASNode forVar = new ASNode(ASNode.Type.RUBY_VAR);
                forVar.setLineOfCode(ctx.lvalue(i).getStart().getLine());
                forVar.setCode(getOriginalCodeText(ctx.lvalue(i)));
                varsCounter++;
                String normalize = "VARS_" + varsCounter;
                vars.put(getOriginalCodeText(ctx.lvalue(i)), normalize);
                forVar.setNormalizedCode(normalize);
                AST.addVertex(forVar);
                AST.addEdge(forNode, forVar);
            }
            if (ctx.loop_expression() != null) {
                ASNode loopExpression = new ASNode(ASNode.Type.RUBY_LOOP_EXPR);
                loopExpression.setLineOfCode(ctx.loop_expression().getStart().getLine());
                loopExpression.setCode(ctx.loop_expression().getText());
                AST.addVertex(loopExpression);
                AST.addEdge(forNode, loopExpression);
            }

            if (ctx.statement_body() != null) {
                parentStack.push(forNode);
                visit(ctx.statement_body());
                parentStack.pop();
            }

            return "";
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitFor_each_statement(RubyParser.For_each_statementContext ctx) {
            // for_each_statement:  array_definition DOT EACH LEFT_BBRACKET crlf? '|' id_ '|' crlf? statement_body RIGHT_BBRACKET;
            ASNode forNode = new ASNode(ASNode.Type.FOR);
            forNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(forNode);
            AST.addEdge(parentStack.peek(), forNode);

            ASNode array = new ASNode(ASNode.Type.NAME);
            array.setLineOfCode(ctx.array_definition().getStart().getLine());
            array.setCode(getOriginalCodeText(ctx.array_definition()));
            AST.addVertex(array);
            AST.addEdge(forNode, array);

            ASNode localVar = new ASNode(ASNode.Type.NAME);
            localVar.setLineOfCode(ctx.id_().getStart().getLine());
            localVar.setCode(getOriginalCodeText(ctx.id_()));
            AST.addVertex(localVar);
            AST.addEdge(forNode, localVar);

            parentStack.push(forNode);
            visit(ctx.statement_body());
            parentStack.pop();

            return "";
        }

        @Override
        public String visitAll_assignment(RubyParser.All_assignmentContext ctx) {
            // all_assignment : ( int_assignment | float_assignment | string_assignment | dynamic_assignment );
            if (ctx.int_assignment() != null) {
                visit(ctx.int_assignment());
            } else if (ctx.float_assignment() != null) {
                visit(ctx.float_assignment());
            } else if (ctx.string_assignment() != null) {
                visit(ctx.string_assignment());
            } else if (ctx.dynamic_assignment() != null) {
                visit(ctx.dynamic_assignment());
            }
            return "";
        }


        @Override
        public String visitCond_expression(RubyParser.Cond_expressionContext ctx) {
            // cond_expression : comparison_list;
            return visit(ctx.comparison_list());
        }

        /**
         * {@inheritDoc}
         *
         * <p>The default implementation returns the result of calling
         * {@link #visitChildren} on {@code ctx}.</p>
         *
         * @param ctx
         */
        @Override
        public String visitCase_expression(RubyParser.Case_expressionContext ctx) {
            // case_expression : CASE case_exp  crlf* (WHEN when_cond crlf* statement_body )*(else_token crlf statement_body)?    END;
            //
            // case_exp : rvalue;
            //
            // when_cond: cond_expression | array_definition;
            ASNode caseNode = new ASNode(ASNode.Type.RUBY_CASE);
            caseNode.setLineOfCode(ctx.getStart().getLine());
            AST.addVertex(caseNode);
            AST.addEdge(parentStack.peek(), caseNode);

            ASNode varNode = new ASNode(ASNode.Type.NAME);
            varNode.setCode(getOriginalCodeText(ctx.case_exp()));
            varNode.setNormalizedCode("CASE_$" + getOriginalCodeText(ctx.case_exp()));
            varNode.setLineOfCode(ctx.case_exp().getStart().getLine());
            AST.addVertex(varNode);
            AST.addEdge(caseNode, varNode);

            ASNode caseBodyNode = new ASNode(ASNode.Type.RUBY_CASE_BODY);
            AST.addVertex(caseBodyNode);
            AST.addEdge(caseNode, caseBodyNode);
            // when expression
            if (ctx.WHEN() != null) {
                for (int i = 0; i < ctx.WHEN().size(); i++) {
                    ASNode whenNode = new ASNode(ASNode.Type.RUBY_WHEN);
                    whenNode.setLineOfCode(ctx.when_cond(i).getStart().getLine());
                    whenNode.setCode(getOriginalCodeText(ctx.when_cond(i)));
                    AST.addVertex(whenNode);
                    AST.addEdge(caseBodyNode, whenNode);
                    parentStack.push(whenNode);
                    if (ctx.statement_body(i) != null) {
                        visit(ctx.statement_body(i));
                    }
                    parentStack.pop();
                }
            }
            if (ctx.else_token() != null) {
                ASNode elseNode = new ASNode(ASNode.Type.ELSE);
                elseNode.setLineOfCode(ctx.else_token().getStart().getLine());
                parentStack.push((elseNode));
                visit(ctx.statement_body(ctx.statement_body().size() - 1));
            }
            return "";
        }


        @Override
        public String visitStatement_body(RubyParser.Statement_bodyContext ctx) {
            return visit(ctx.statement_expression_list());
        }

        @Override
        public String visitStatement_expression_list(RubyParser.Statement_expression_listContext ctx) {
            if (ctx.statement_expression_list() != null) {
                visit(ctx.statement_expression_list());
            }
            if (ctx.RETRY() != null) {
                ASNode retryNode = new ASNode(ASNode.Type.RUBY_RETRY);
                retryNode.setLineOfCode(ctx.getStart().getLine());
                retryNode.setCode(retryNode.getType().label);
                AST.addVertex(retryNode);
                AST.addEdge(parentStack.peek(), retryNode);
            } else if (ctx.expression() != null) {
                visit(ctx.expression());
            } else if (ctx.raise_expression() != null) {
                ASNode raiseNode = new ASNode(ASNode.Type.RUBY_RAISE);
                raiseNode.setLineOfCode(ctx.raise_expression().getStart().getLine());
                raiseNode.setCode(getOriginalCodeText(ctx.raise_expression()));
                AST.addVertex(raiseNode);
                AST.addEdge(parentStack.peek(), raiseNode);
            } else if (ctx.yield_expression() != null) {
                ASNode yieldNode = new ASNode(ASNode.Type.YIELD);
                yieldNode.setLineOfCode(ctx.yield_expression().getStart().getLine());
                yieldNode.setCode(getOriginalCodeText(ctx.yield_expression()));
                AST.addVertex(yieldNode);
                AST.addEdge(parentStack.peek(), yieldNode);
            } else if (ctx.break_expression() != null) {
                ASNode breakNode = new ASNode(ASNode.Type.BREAK);
                breakNode.setLineOfCode(ctx.break_expression().getStart().getLine());
                breakNode.setCode(ctx.break_expression().getText());
                AST.addVertex(breakNode);
                AST.addEdge(parentStack.peek(), breakNode);
            }
            return "";
        }

        @Override
        public String visitAssignment(RubyParser.AssignmentContext ctx) {
            return ctx.getText();
        }

        @Override
        public String visitDynamic_assignment(RubyParser.Dynamic_assignmentContext ctx) {
            ASNode initNode = getLocalVarAssignment(ctx.getStart(), ctx.var_id, ctx);
            String code = ctx.op.getText() + " " + visit(ctx.dynamic_result());
            initNode.setCode(code);
            AST.addVertex(initNode);
            AST.addEdge(parentStack.peek(), initNode);
            return "";
        }

        @Override
        public String visitInt_assignment(RubyParser.Int_assignmentContext ctx) {


            ASNode initNode = getLocalVarAssignment(ctx.getStart(), ctx.var_id, ctx);
            String code = ctx.op.getText() + " " + visit(ctx.int_result());
            initNode.setCode(code);
            AST.addVertex(initNode);
            AST.addEdge(parentStack.peek(), initNode);
            return "";
        }

        @Override
        public String visitFloat_assignment(RubyParser.Float_assignmentContext ctx) {


            ASNode initNode = getLocalVarAssignment(ctx.getStart(), ctx.var_id, ctx);
            String code = ctx.op.getText() + " " + visit(ctx.float_result());
            initNode.setCode(code);
            AST.addVertex(initNode);
            AST.addEdge(parentStack.peek(), initNode);
            Logger.info("finish visit float_assignment code=" + initNode.getCode());
            return "";
        }

        @Override
        public String visitString_assignment(RubyParser.String_assignmentContext ctx) {

            ASNode initNode = getLocalVarAssignment(ctx.getStart(), ctx.var_id, ctx);
            String code = ctx.op.getText() + " " + visit(ctx.string_result());
            initNode.setCode(code);
            AST.addVertex(initNode);
            AST.addEdge(parentStack.peek(), initNode);
            return "";
        }

        private ASNode getLocalVarAssignment(Token start, RubyParser.LvalueContext var_id, ParserRuleContext ctx) {
            ASNode varNode = new ASNode(ASNode.Type.RUBY_VAR);
            varNode.setLineOfCode(start.getLine());
            AST.addVertex(varNode);
            AST.addEdge(parentStack.peek(), varNode);

            String id = var_id.getText();
            if (!vars.containsKey(var_id.getText())) {


                varsCounter++;
                String normalized = "$VARL_" + varsCounter;
                vars.put(id, normalized);
            }
            ASNode nameNode = new ASNode(ASNode.Type.NAME);
            nameNode.setCode(id);
            nameNode.setNormalizedCode(vars.get(id));
            nameNode.setLineOfCode(start.getLine());
            AST.addVertex(nameNode);
            AST.addEdge(parentStack.peek(), nameNode);

            ASNode initNode = new ASNode(ASNode.Type.RUBY_ASSIGNMENT);
            initNode.setLineOfCode(start.getLine());
            return initNode;
        }

        @Override
        public String visitInitial_array_assignment(RubyParser.Initial_array_assignmentContext ctx) {
            varsCounter++;
            String normalized = "$VARL_" + varsCounter;
            String id = ctx.var_id.getText();
            vars.put(id, normalized);
            ASNode initArray = new ASNode(ASNode.Type.RUBY_ARRAY_INIT);
            initArray.setLineOfCode(ctx.getStart().getLine());
            initArray.setCode(getOriginalCodeText(ctx));
            initArray.setNormalizedCode(normalized);

            AST.addVertex(initArray);
            AST.addEdge(parentStack.peek(), initArray);
            return "";
        }

        @Override
        public String visitArray_assignment(RubyParser.Array_assignmentContext ctx) {
            ASNode arrayAssign = new ASNode(ASNode.Type.RUBY_ARRAY_ASSIGN);
            arrayAssign.setLineOfCode(ctx.getStart().getLine());
            arrayAssign.setCode(visit(ctx.array_selector()) + ctx.op.getText() + visit(ctx.all_result()));
            AST.addVertex(arrayAssign);
            AST.addEdge(parentStack.peek(), arrayAssign);
            return "";
        }


        @Override
        public String visitArray_selector(RubyParser.Array_selectorContext ctx) {
            return ctx.getText();
        }

        @Override
        public String visitDynamic_result(RubyParser.Dynamic_resultContext ctx) {
            return ctx.getText();
        }


        @Override
        public String visitInt_result(RubyParser.Int_resultContext ctx) {

            return ctx.getText();
        }

        @Override
        public String visitFloat_result(RubyParser.Float_resultContext ctx) {

            if (ctx.float_t() != null) {
                return ctx.float_t().getText();
            }
            Logger.info("float result " + ctx.getText() + " " + ctx.float_result().isEmpty());
            if (ctx.float_result().size() > 1) {
                // float_result op=( PLUS | MINUS ) float_result
                // float_result op=( MUL | DIV | MOD ) float_result
                Logger.info("float result " + ctx.float_result(0).getText() + ctx.op.getText() + ctx.float_result(1).getText());
                return ctx.float_result(0).getText() + ctx.op.getText() + ctx.float_result(1).getText();
            }
            return ctx.getText();
        }

        @Override
        public String visitString_result(RubyParser.String_resultContext ctx) {
            return getOriginalCodeText(ctx);
        }

        @Override
        public String visitComparison_list(RubyParser.Comparison_listContext ctx) {

            if (ctx.LEFT_RBRACKET() != null && ctx.RIGHT_RBRACKET() != null) {
                return '(' + visit(ctx.comparison_list()) + ')';
            }
            if (ctx.comparison_list() == null) {
                return visit(ctx.comparison());
            }


            return visit(ctx.comparison()) + (ctx.op == null ? "" : ctx.op.getText()) + visit(ctx.comparison_list());
        }

        @Override
        public String visitComparison(RubyParser.ComparisonContext ctx) {
            return ctx.left.getText() + ctx.op.getText() + ctx.right.getText();
        }


        @Override
        public String visitLvalue(RubyParser.LvalueContext ctx) {
            ASNode lvalue = new ASNode(ASNode.Type.RUBY_LVALUE);
            lvalue.setLineOfCode(ctx.getStart().getLine());
            lvalue.setCode(getOriginalCodeText(ctx));
            AST.addVertex(lvalue);
            AST.addEdge(parentStack.peek(), lvalue);
            return super.visitLvalue(ctx);
        }

        @Override
        public String visitRvalue(RubyParser.RvalueContext ctx) {
            Logger.info("visit rvalue -- " + ctx.getText());
            if (ctx.lvalue() != null) {
                visit(ctx.lvalue());
            } else if (ctx.initial_array_assignment() != null) {
                visit(ctx.initial_array_assignment());
            } else if (ctx.array_assignment() != null) {
                visit(ctx.array_assignment());
            } else if (ctx.int_result() != null) {
                ASNode result = new ASNode(ASNode.Type.RUBY_RESULT);
                result.setLineOfCode(ctx.int_result().getStart().getLine());
                result.setCode(visit(ctx.int_result()));
                AST.addVertex(result);
                AST.addEdge(parentStack.peek(), result);
            } else if (ctx.float_result() != null) {
                Logger.info("rvalue visit float_result");
                ASNode result = new ASNode(ASNode.Type.RUBY_RESULT);
                result.setLineOfCode(ctx.float_result().getStart().getLine());
                result.setCode(visit(ctx.float_result()));
                AST.addVertex(result);
                AST.addEdge(parentStack.peek(), result);
            } else if (ctx.string_result() != null) {
                ASNode result = new ASNode(ASNode.Type.RUBY_RESULT);
                result.setLineOfCode(ctx.string_result().getStart().getLine());
                result.setCode(visit(ctx.string_result()));
                AST.addVertex(result);
                AST.addEdge(parentStack.peek(), result);
            } else if (ctx.dynamic_assignment() != null) {
                visit(ctx.dynamic_assignment());
            } else if (ctx.string_assignment() != null) {
                visit(ctx.string_assignment());
            } else if (ctx.float_assignment() != null) {
                Logger.info("rvalue visit float_assignment");
                visit(ctx.float_assignment());
            } else if (ctx.int_assignment() != null) {
                visit(ctx.int_assignment());
            } else if (ctx.assignment() != null) {
                ASNode assign = new ASNode(ASNode.Type.RUBY_ASSIGNMENT);
                assign.setLineOfCode(ctx.assignment().getStart().getLine());
                assign.setCode(visit(ctx.assignment()));
                AST.addVertex(assign);
                AST.addEdge(parentStack.peek(), assign);
            } else if (ctx.function_call() != null) {
                ASNode funcInlineCallNode = new ASNode(ASNode.Type.RUBY_FUNCTION_CALL);
                funcInlineCallNode.setLineOfCode(ctx.function_call().getStart().getLine());
                funcInlineCallNode.setCode(visit(ctx.function_call()));
                AST.addVertex(funcInlineCallNode);
                AST.addEdge(parentStack.peek(), funcInlineCallNode);
            } else if (ctx.literal_t() != null) {
                visit(ctx.literal_t());
            } else if (ctx.bool_t() != null) {
                visit(ctx.bool_t());
            } else if (ctx.float_t() != null) {
                visit(ctx.float_t());
            } else if (ctx.int_t() != null) {
                visit(ctx.int_t());
            } else if (ctx.nil_t() != null) {
                visit(ctx.nil_t());
            } else {
                //        | rvalue EXP rvalue
                //
                //
                //       | rvalue ( MUL | DIV | MOD ) rvalue
                //       | rvalue ( PLUS | MINUS ) rvalue
                //
                //       | rvalue ( BIT_SHL | BIT_SHR ) rvalue
                //
                //       | rvalue BIT_AND rvalue
                //
                //       | rvalue ( BIT_OR | BIT_XOR )rvalue
                //
                //       | rvalue ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) rvalue
                //
                //       | rvalue ( EQUAL | NOT_EQUAL ) rvalue
                //
                //       | rvalue ( OR | AND ) rvalue
                if (ctx.rvalue().size() > 1) {
                    Logger.info("rvalue visit more ");
                    visit(ctx.rvalue(0));
                    ASNode op = new ASNode(ASNode.Type.OPERATOR);
                    op.setLineOfCode(ctx.rvalue(0).getStart().getLine());
                    op.setCode(getRValueOP(ctx));
                    AST.addVertex(op);
                    AST.addEdge(parentStack.peek(), op);
                    visit(ctx.rvalue(1));
                }
                //        | ( NOT | BIT_NOT )rvalue
                //          LEFT_RBRACKET rvalue RIGHT_RBRACKET
                if (ctx.rvalue().size() == 1) {
                    if (ctx.LEFT_RBRACKET() != null) {
                        visit(ctx.rvalue(0));
                    } else {
                        ASNode op = new ASNode(ASNode.Type.OPERATOR);
                        op.setLineOfCode(ctx.rvalue(0).getStart().getLine());
                        op.setCode(getRValueOP(ctx));
                        AST.addVertex(op);
                        AST.addEdge(parentStack.peek(), op);
                        visit(ctx.rvalue(0));
                    }
                }
            }
            return "";
        }

        private String getRValueOP(RubyParser.RvalueContext ctx) {
            if (ctx.EXP() != null) {
                return ctx.EXP().getText();
            }
            if (ctx.NOT() != null) {
                return ctx.NOT().getText();
            }
            if (ctx.BIT_NOT() != null) {
                return ctx.BIT_NOT().getText();
            }
            if (ctx.MUL() != null) {
                return ctx.MUL().getText();
            }
            if (ctx.DIV() != null) {
                return ctx.DIV().getText();
            }
            if (ctx.MOD() != null) {
                return ctx.MOD().getText();
            }
            if (ctx.PLUS() != null) {
                return ctx.PLUS().getText();
            }
            if (ctx.MINUS() != null) {
                return ctx.MINUS().getText();
            }
            if (ctx.BIT_SHL() != null) {
                return ctx.BIT_SHL().getText();
            }
            if (ctx.BIT_SHR() != null) {
                return ctx.BIT_SHR().getText();
            }
            if (ctx.BIT_AND() != null) {
                return ctx.BIT_AND().getText();
            }
            return "op";
        }

        @Override
        public String visitBreak_expression(RubyParser.Break_expressionContext ctx) {
            return super.visitBreak_expression(ctx);
        }

        @Override
        public String visitLiteral_t(RubyParser.Literal_tContext ctx) {
            ASNode node = new ASNode(ASNode.Type.RUBY_LITERAL_T);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(getOriginalCodeText(ctx));
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            return "";
        }

        @Override
        public String visitFloat_t(RubyParser.Float_tContext ctx) {
            ASNode node = new ASNode(ASNode.Type.RUBY_FLOAT_T);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(getOriginalCodeText(ctx));
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            return "";
        }

        @Override
        public String visitInt_t(RubyParser.Int_tContext ctx) {
            ASNode node = new ASNode(ASNode.Type.RUBY_INT_T);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(getOriginalCodeText(ctx));
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            return "";
        }

        @Override
        public String visitBool_t(RubyParser.Bool_tContext ctx) {
            ASNode node = new ASNode(ASNode.Type.RUBY_BOOL_T);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(getOriginalCodeText(ctx));
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            return "";
        }

        @Override
        public String visitNil_t(RubyParser.Nil_tContext ctx) {
            ASNode node = new ASNode(ASNode.Type.RUBY_NIL_T);
            node.setLineOfCode(ctx.getStart().getLine());
            node.setCode(getOriginalCodeText(ctx));
            AST.addVertex(node);
            AST.addEdge(parentStack.peek(), node);
            return "";
        }

        @Override
        public String visitId_(RubyParser.Id_Context ctx) {
            return ctx.getText();
        }

        @Override
        public String visitId_global(RubyParser.Id_globalContext ctx) {
            ASNode globalID = new ASNode(ASNode.Type.RUBY_GLOBAL_ID);
            globalID.setLineOfCode(ctx.getStart().getLine());
            globalID.setCode(ctx.ID_GLOBAL().getText());
            return globalID.getCode();
        }


        @Override
        public String visitId_function(RubyParser.Id_functionContext ctx) {
            return ctx.getText();
        }


        @Override
        public String visitElse_token(RubyParser.Else_tokenContext ctx) {
            return ctx.getText();
        }


        /**
         * Get the original program text for the given parser-rule context.
         * This is required for preserving white-spaces.
         */
        private String getOriginalCodeText(ParserRuleContext ctx) {
            if (ctx == null)
                return "";
            int start = ctx.start.getStartIndex();
            int stop = ctx.stop.getStopIndex();
            Interval interval = new Interval(start, stop);
            return ctx.start.getInputStream().getText(interval);
        }

        private void resetLocalVars() {
            vars.clear();
            varsCounter = 0;
        }

        private String normalizedIdentifier(TerminalNode id) {
            String normalized = vars.get(id.getText());
            if (normalized == null || normalized.isEmpty())
                normalized = fields.get(id.getText());
            if (normalized == null || normalized.isEmpty())
                normalized = methods.get(id.getText());
            if (normalized == null || normalized.isEmpty())
                normalized = id.getText();
            return normalized;
        }

    }


}
