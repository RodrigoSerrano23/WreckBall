package codigo;
import static codigo.Tokens.*;
%%
%class Lexer
%type Tokens
L=[_a-zA-Z]+
D=[0-9]+
espacio=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Salto de linea */
( "\n" ) {return Linea;}

/* Tipo de datos entero*/
( int ) {lexeme=yytext(); return Int;}

/* Tipo de datos booleano*/
( bool) {lexeme=yytext(); return Bool;}

/* Palabra reservada If */
( if ) {lexeme=yytext(); return If;}

/* Palabra reservada Else */
( else ) {lexeme=yytext(); return Else;}

/* Palabra reservada Do */
( do ) {lexeme=yytext(); return Do;}

/* Palabra reservada While */
( while ) {lexeme=yytext(); return While;}

/* Palabra reservada For */
( for ) {lexeme=yytext(); return For;}

/* Palabra reservada StopLoop */
( stopLoop ) {lexeme=yytext(); return StopLoop;}

/* Método SpinCraneLeft */
( spinCraneLeft ) {lexeme=yytext(); return SpinCraneLeft;}

/* Método SpinCraneRight */
( spinCraneRight ) {lexeme=yytext(); return SpinCraneRight;}

/* Método MoveFowardCrane */
( moveFowardCrane ) {lexeme=yytext(); return MoveFowardCrane;}

/* Método MoveBackCrane */
( moveBackCrane ) {lexeme=yytext(); return MoveBackCrane;}

/* Método SpinBallLeft */
( spinBallLeft ) {lexeme=yytext(); return SpinBallLeft;}

/* Método SpinBallRight */
( spinBallRight ) {lexeme=yytext(); return SpinBallRight;}

/* Método HitToTheLeft */
( hitToTheLeft ) {lexeme=yytext(); return HitToTheLeft;}

/* Método HitToTheRight */
( hitToTheRight ) {lexeme=yytext(); return HitToTheRight;}

/* Operador Igual */
( "=" ) {lexeme=yytext(); return Igual;}

/* Operador Suma */
( "+" ) {lexeme=yytext(); return Suma;}

/* Operador Resta */
( "-" ) {lexeme=yytext(); return Resta;}

/* Operador Multiplicacion */
( "*" ) {lexeme=yytext(); return Multiplicacion;}

/* Operador Division */
( "/" ) {lexeme=yytext(); return Division;}

/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|" ) {lexeme=yytext(); return Op_logico;}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" | "<<" | ">>" ) {lexeme = yytext(); return Op_relacional;}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" ) {lexeme = yytext(); return Op_atribucion;}

/* Operadores Incremento y decremento */
( "++" | "--" ) {lexeme = yytext(); return Op_incremento;}

/* Método WaitTime */
( waitTime ) {lexeme=yytext(); return WaitTime;}

/*Operadores Booleanos*/
(true | false)      {lexeme = yytext(); return Op_booleano;}

/* Parentesis de apertura */
( "(" ) {lexeme=yytext(); return Parentesis_a;}

/* Parentesis de cierre */
( ")" ) {lexeme=yytext(); return Parentesis_c;}

/* Llave de apertura */
( "{" ) {lexeme=yytext(); return Llave_a;}

/* Llave de cierre */
( "}" ) {lexeme=yytext(); return Llave_c;}

/* Corchete de apertura */
( "[" ) {lexeme = yytext(); return Corchete_a;}

/* Corchete de cierre */
( "]" ) {lexeme = yytext(); return Corchete_c;}

/* Marcador de inicio de algoritmo */
( "start" ) {lexeme=yytext(); return Start;}

/* Punto y coma */
( ";" ) {lexeme=yytext(); return P_coma;}

/* Identificador */
{L}({L}|{D})* {lexeme=yytext(); return Identificador;}

/* Numero */
("(-"{D}+")")|{D}+ {lexeme=yytext(); return Numero;}

/* Sentencia Desconocida */
//^(("start")|(.))+ {lexeme=yytext(); return Desconocido;}

/* Error de analisis */
 . {return ERROR;}
