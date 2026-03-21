grammar Prv;


@header {
package org.bstill66.Expression;
}


program:  expr EOF;

expr:
       expr 'and' expr
     | expr 'or'  expr
     | Variable 'in'  list
     | Variable 'not' 'in' list
     | Variable '=' literal
     | Variable '!=' literal
     | Variable Like String
     | 'not' expr
     | '(' expr ')';



literal
    : Number
    | String
    | Eidr;

list:
   '[' literal? (',' literal)* ']';

Variable
    : Nondigit (Nondigit | Digit)*;

Number
   :  Sign? Digit+;

fragment Sign
     : [+-];

fragment Nondigit
    : [a-zA-Z_]
    ;

fragment Digit
    : [0-9]
    ;

fragment Hexdigit
    : [0-9a-fA-F];

String
    : '"' StrChar* '"'
    ;
Like:
   '~=';

Eidr
    : 'E' '"' EidrPrefix EidrSeg '-' EidrSeg '-' EidrSeg '-' EidrSeg '-' EidrCheck '"';

EidrPrefix
    : '10.240/' | '/'?;

EidrSeg
    : Hexdigit Hexdigit Hexdigit Hexdigit;

EidrCheck
    : [0-9A-Z];

/*  Lexical Analysis */

fragment StrChar
    : ~["\\\r\n];


Whitespace
    : [ \t]+ -> channel(HIDDEN)
    ;

Newline
    : ('\r' '\n'? | '\n') -> channel(HIDDEN)
    ;

BlockComment
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;

LineComment
    : '//' ~[\r\n]* -> channel(HIDDEN)
    ;
