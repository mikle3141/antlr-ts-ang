/** Parser for the Sample language (C-like without semicolons).
 *  Uses tokens from SampleLanguageLexer.
 */
parser grammar SampleLanguageParser;

options { tokenVocab = SampleLanguageLexer; }

/** The start rule: script, compilationUnit, etc. */
script
	:	vardef* function* statement* EOF
	;

function
	:	'func' ID '(' formal_args? ')' (':' type)? block
	;

formal_args : formal_arg (',' formal_arg)* ;

formal_arg : ID ':' type ;

type:	'int'                                               # IntTypeSpec
	|	'float'                                             # FloatTypeSpec
	|	'string'                                            # StringTypeSpec
	|	'boolean'											# BooleanTypeSpec
	|	'[' ']'                                             # VectorTypeSpec
	;

block
	:  '{' (statement|vardef)* '}';

statement
	:	'if' '(' expr ')' statement ('else' statement)?		# If
	|	'while' '(' expr ')' statement						# While
	|	ID '=' expr											# Assign
	|	ID '[' expr ']' '=' expr							# ElementAssign
	|	call_expr											# CallStatement
	|	'print' '(' expr? ')'								# Print
	|	'return' expr										# Return
	|	block				 								# BlockStatement
	;

vardef : 'var' ID '=' expr ;

expr
	:	expr operator expr									# Op
	|	'-' expr											# Negate
	|	'!' expr											# Not
	|	call_expr											# Call
	|	ID '[' expr ']'										# Index
	|	'(' expr ')'										# Parens
	|	primary												# Atom
	;

operator  : MUL|DIV|ADD|SUB|GT|GE|LT|LE|EQUAL_EQUAL|NOT_EQUAL|OR|AND|DOT ;

call_expr
	: ID '(' expr_list? ')' ;

expr_list : expr (',' expr)* ;

primary
	:	ID													# Identifier
	|	INT													# Integer
	|	FLOAT												# Float
	|	STRING												# String
	|	'[' expr_list ']'									# Vector
	|	'true'												# TrueLiteral
	|	'false'												# FalseLiteral
	;
