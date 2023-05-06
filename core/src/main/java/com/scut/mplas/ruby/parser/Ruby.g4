/*
 * [The "BSD license"]
 *  Copyright (c) 2014 Alexander Belov
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 *  A grammar for Ruby-like language written in ANTLR v4.
 *  Not support iterator and until statement
 */

grammar Ruby;

prog : expression_list EOF;

expression_list : expression terminator
                | expression_list expression terminator
                | terminator
                ;

expression : for_statement
           | function_definition
           | function_inline_call
           | require_block
           | if_statement
           | unless_statement
           | rvalue
           | raise_expression
           | return_statement
           | while_statement
           | pir_inline
           | hash_expression
           | class_definition
           | module_definition
           | begin_expression
           | end_expression
           | begin_rescue_expression
           | case_expression
           ;

case_expression : CASE case_exp  crlf* (WHEN when_cond crlf* statement_body )*(else_token crlf statement_body)?    END;

case_exp : rvalue;

when_cond: cond_expression | array_definition;

begin_expression :BEGIN LEFT_BBRACKET crlf* statement_body  RIGHT_BBRACKET crlf*;

end_expression : END LEFT_BBRACKET crlf* statement_body RIGHT_BBRACKET crlf*;

begin_rescue_expression : BEGIN crlf* statement_body  (rescue_expression error_type? crlf* statement_body )* (else_token crlf statement_body )? (ensure_expression crlf* statement_body )?  END ;

error_type : id_;

rescue_expression : RESCUE;

ensure_expression: ENSURE;

global_get : var_name=lvalue op=ASSIGN global_name=id_global;

global_set : global_name=id_global op=ASSIGN result=all_result;

global_result : id_global;

instance_get : instance_name=lvalue op=ASSIGN result=id_instance;


instance_set : instance_name=id_instance op=ASSIGN result=all_result;

instance_result : id_instance;

const_set : constance_name=id_constence op=ASSIGN result=all_result ;

function_inline_call : function_call;

require_block : REQUIRE literal_t;

pir_inline : PIR crlf pir_expression_list END;

pir_expression_list : expression_list;

class_definition : 'class' lvalue  ( '<'superclass_id = lvalue )? crlf* statement_expression_list? crlf* END;



module_definition: 'module' id_ CRLF statement_expression_list? CRLF END;



function_definition : function_definition_header function_definition_body END;

function_definition_body : expression_list;

function_definition_header : DEF function_name crlf
                           | DEF function_name function_definition_params crlf
                           ;

function_name : id_function
              | id_
              ;

function_definition_params : LEFT_RBRACKET RIGHT_RBRACKET
                           | LEFT_RBRACKET function_definition_params_list RIGHT_RBRACKET
                           | function_definition_params_list
                           ;

function_definition_params_list : function_definition_param_id
                                | function_definition_params_list COMMA function_definition_param_id
                                ;

function_definition_param_id : id_;

return_statement : RETURN all_result;



function_call : name=function_name LEFT_RBRACKET params=function_call_param_list RIGHT_RBRACKET
              | name=function_name params=function_call_param_list
              | name=function_name LEFT_RBRACKET RIGHT_RBRACKET
              | id_ '.' name=function_name LEFT_RBRACKET (params=function_call_param_list) ? RIGHT_RBRACKET
              | id_ '.' name=function_name (params=function_call_param_list)?
              | id_ '::' name=function_name LEFT_RBRACKET (params=function_call_param_list) ? RIGHT_RBRACKET
              | id_ '::' name=function_name (params=function_call_param_list)?
              ;

function_call_param_list : function_call_params;

function_call_params : function_param
                     | function_call_params COMMA function_param
                     ;

function_param : ( function_unnamed_param | function_named_param );

function_unnamed_param : ( int_result | float_result | string_result | dynamic_result );

function_named_param : id_ op=ASSIGN ( int_result | float_result | string_result | dynamic_result );

function_call_assignment : function_call;

all_result : ( int_result | float_result | string_result | dynamic_result | global_result  | instance_result);

elsif_statement : if_elsif_statement;

if_elsif_statement : ELSIF cond_expression (THEN)? crlf statement_body
                   | ELSIF cond_expression (THEN)? crlf statement_body else_token crlf statement_body
                   | ELSIF cond_expression (THEN)? crlf statement_body if_elsif_statement
                   ;

if_statement : IF cond_expression (THEN)? crlf statement_body END
             | IF cond_expression (THEN)? crlf statement_body else_token crlf statement_body END
             | IF cond_expression (THEN)? crlf statement_body elsif_statement END
             ;

unless_statement : UNLESS cond_expression crlf statement_body END
                 | UNLESS cond_expression crlf statement_body else_token crlf statement_body END
                 | UNLESS cond_expression crlf statement_body elsif_statement END
                 ;

while_statement : WHILE cond_expression crlf statement_body END;

// for_statement : FOR LEFT_RBRACKET init_expression SEMICOLON cond_expression SEMICOLON loop_expression RIGHT_RBRACKET crlf statement_body END
//               | FOR init_expression SEMICOLON cond_expression SEMICOLON loop_expression crlf statement_body END
//               ;


// init_expression : for_init_list;

all_assignment : ( int_assignment | float_assignment | string_assignment | dynamic_assignment );

// for_init_list : for_init_list COMMA all_assignment
//               | all_assignment
//               ;

for_statement : FOR lvalue (COMMA lvalue)*  IN loop_expression DO? CRLF* statement_body END
              | for_each_statement;

for_each_statement:  array_definition DOT EACH LEFT_BBRACKET crlf? '|' id_ '|' crlf? statement_body RIGHT_BBRACKET;

cond_expression : comparison_list;

loop_expression : array_definition;

hash_expression :  LEFT_BBRACKET expression op=HASH_OP expression RIGHT_BBRACKET;

// for_loop_list : for_loop_list COMMA all_assignment
//               | all_assignment
//               ;

statement_body : statement_expression_list;

statement_expression_list : expression terminator?
                          | RETRY terminator?
                          | break_expression terminator?
                          | raise_expression terminator?
                          | yield_expression terminator?
                          | statement_expression_list expression terminator?
                          | statement_expression_list RETRY terminator?
                          | statement_expression_list break_expression terminator?
                          ;

assignment : var_id=lvalue op=ASSIGN rvalue
           | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) rvalue
           ;

dynamic_assignment : var_id=lvalue op=ASSIGN dynamic_result
                   | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) dynamic_result
                   ;

int_assignment : var_id=lvalue op=ASSIGN int_result
               | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) int_result
               ;

float_assignment : var_id=lvalue op=ASSIGN float_result
                 | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) float_result
                 ;

string_assignment : var_id=lvalue op=ASSIGN string_result
                  | var_id=lvalue op=PLUS_ASSIGN string_result
                  ;

initial_array_assignment : var_id=lvalue op=ASSIGN LEFT_SBRACKET RIGHT_SBRACKET;

array_assignment : arr_def=array_selector op=ASSIGN arr_val=all_result;

array_definition : LEFT_SBRACKET array_definition_elements RIGHT_SBRACKET
                 | array_definition_elements;

array_definition_elements : ( int_result | dynamic_result )
                          | array_definition_elements COMMA ( int_result | dynamic_result )
                          | array_definition_elements ELLIPSIS (int_result | dynamic_result)
                          ;

array_selector : id_ LEFT_SBRACKET ( int_result | dynamic_result ) RIGHT_SBRACKET
               | id_global LEFT_SBRACKET ( int_result | dynamic_result ) RIGHT_SBRACKET
               ;

dynamic_result : dynamic_result op=( MUL | DIV | MOD ) int_result
               | int_result op=( MUL | DIV | MOD ) dynamic_result
               | dynamic_result op=( MUL | DIV | MOD ) float_result
               | float_result op=( MUL | DIV | MOD ) dynamic_result
               | dynamic_result op=( MUL | DIV | MOD ) dynamic_result
               | dynamic_result op=MUL string_result
               | string_result op=MUL dynamic_result
               | dynamic_result op=( PLUS | MINUS ) int_result
               | int_result op=( PLUS | MINUS ) dynamic_result
               | dynamic_result op=( PLUS | MINUS )  float_result
               | float_result op=( PLUS | MINUS )  dynamic_result
               | dynamic_result op=( PLUS | MINUS ) dynamic_result
               | LEFT_RBRACKET dynamic_result RIGHT_RBRACKET
               | map_result
               | dynamic_
               ;

map_result : hash_expression;

dynamic_ : id_
        | function_call_assignment
        | array_selector
        ;

int_result : int_result op=( MUL | DIV | MOD ) int_result
           | int_result op=( PLUS | MINUS ) int_result
           | LEFT_RBRACKET int_result RIGHT_RBRACKET
           | int_t
           ;

float_result : float_result op=( MUL | DIV | MOD ) float_result
             | int_result op=( MUL | DIV | MOD ) float_result
             | float_result op=( MUL | DIV | MOD ) int_result
             | float_result op=( PLUS | MINUS ) float_result
             | int_result op=( PLUS | MINUS )  float_result
             | float_result op=( PLUS | MINUS )  int_result
             | LEFT_RBRACKET float_result RIGHT_RBRACKET
             | float_t
             ;

string_result : string_result op=MUL int_result
              | int_result op=MUL string_result
              | string_result op=PLUS string_result
              | literal_t
              ;

comparison_list : left=comparison op=BIT_AND right=comparison_list
                | left=comparison op=AND right=comparison_list
                | left=comparison op=BIT_OR right=comparison_list
                | left=comparison op=OR right=comparison_list
                | LEFT_RBRACKET comparison_list RIGHT_RBRACKET
                | comparison
                ;

comparison : left=comp_var op=( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) right=comp_var
           | left=comp_var op=( EQUAL | NOT_EQUAL ) right=comp_var
           ;

comp_var : all_result
         | array_selector
         | id_
         | id_constence
         ;

lvalue : id_constence
       | id_global
       | id_
       ;

rvalue : lvalue

       | initial_array_assignment
       | array_assignment
       | int_result
       | float_result
       | string_result

       | global_set
       | global_get
       | instance_set
       | instance_get
       | dynamic_assignment
       | string_assignment
       | float_assignment
       | int_assignment
       | assignment

       | function_call
       | literal_t
       | bool_t
       | float_t
       | int_t
       | nil_t

       | rvalue EXP rvalue

       | ( NOT | BIT_NOT )rvalue

       | rvalue ( MUL | DIV | MOD ) rvalue
       | rvalue ( PLUS | MINUS ) rvalue

       | rvalue ( BIT_SHL | BIT_SHR ) rvalue

       | rvalue BIT_AND rvalue

       | rvalue ( BIT_OR | BIT_XOR )rvalue

       | rvalue ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) rvalue

       | rvalue ( EQUAL | NOT_EQUAL ) rvalue

       | rvalue ( OR | AND ) rvalue

       | LEFT_RBRACKET rvalue RIGHT_RBRACKET
       ;

break_expression : BREAK;

raise_expression: RAISE lvalue;

yield_expression : YIELD;

literal_t : LITERAL;

float_t : FLOAT;

int_t : INT;

bool_t : TRUE
       | FALSE
       ;

nil_t : NIL;

id_ : ID;

id_global : ID_GLOBAL;

id_instance : ID_INSTANCE;

id_constence : CONST_ID;

id_function : ID_FUNCTION;

terminator : terminator SEMICOLON
           | terminator crlf
           | SEMICOLON
           | crlf
           ;

else_token : ELSE;

crlf : CRLF;

fragment ESCAPED_QUOTE : '\\"';
LITERAL : '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"'
        | '\'' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '\'';

COMMA : ',';
ELLIPSIS: '..'|'...';
SEMICOLON : ';';
CRLF : '\r'? '\n';

CASE: 'case';
WHEN: 'when';
REQUIRE : 'require';
BEGIN: 'begin';
END : 'end';
DEF : 'def';
RETURN : 'return';
PIR : 'pir';
RAISE: 'raise';
ENSURE : 'ensure';
IF: 'if';
ELSE : 'else';
ELSIF : 'elsif';
UNLESS : 'unless';
WHILE : 'while';
RETRY : 'retry';
BREAK : 'break';
FOR : 'for';
THEN : 'then';
IN : 'in';
DO : 'do';
RESCUE: 'rescue';
TRUE : 'true';
FALSE : 'false';
YIELD : 'yield';
EACH :'each';
PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
MOD : '%';
EXP : '**';

EQUAL : '==';
NOT_EQUAL : '!=';
GREATER : '>';
LESS : '<';
LESS_EQUAL : '<=';
GREATER_EQUAL : '>=';

ASSIGN : '=';
PLUS_ASSIGN : '+=';
MINUS_ASSIGN : '-=';
MUL_ASSIGN : '*=';
DIV_ASSIGN : '/=';
MOD_ASSIGN : '%=';
EXP_ASSIGN : '**=';

BIT_AND : '&';
BIT_OR : '|';
BIT_XOR : '^';
BIT_NOT : '~';
BIT_SHL : '<<';
BIT_SHR : '>>';

AND : 'and' | '&&';
OR : 'or' | '||';
NOT : 'not' | '!';

HASH_OP : '=>';

LEFT_RBRACKET : '(';
RIGHT_RBRACKET : ')';
LEFT_SBRACKET : '[';
RIGHT_SBRACKET : ']';
LEFT_BBRACKET : '{';
RIGHT_BBRACKET : '}';
NIL : 'nil';

SL_COMMENT : ('#' ~('\r' | '\n')* '\r'? '\n') -> skip;
ML_COMMENT : ('=begin' .*? '=end' '\r'? '\n') -> skip;
WS : (' '|'\t')+ -> skip;

INT : [0-9]+;
FLOAT : [0-9]*'.'[0-9]+;
ID : [a-zA-Z_][a-zA-Z0-9_]*;
CONST_ID : [A-Z]*ID;
ID_GLOBAL : '$'ID;
ID_INSTANCE : '@'ID;
ID_FUNCTION : ID[?];
DOT:'.';
