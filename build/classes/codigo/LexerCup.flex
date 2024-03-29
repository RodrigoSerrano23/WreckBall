package codigo;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Tipos de dato entero */
( int ) {return new Symbol(sym.Int, yychar, yyline, yytext());}

/* Tipos de dato entero */
( bool ) {return new Symbol(sym.Bool, yychar, yyline, yytext());}

/* Palabra reservada If */
( if ) {return new Symbol(sym.If, yychar, yyline, yytext());}

/* Método WaitTime */
( waitTime ) {return new Symbol(sym.WaitTime, yychar, yyline, yytext());}

/* Palabra reservada Else */
( else ) {return new Symbol(sym.Else, yychar, yyline, yytext());}

/* Palabra reservada Do */
( do ) {return new Symbol(sym.Do, yychar, yyline, yytext());}

/* Palabra reservada While */
( while ) {return new Symbol(sym.While, yychar, yyline, yytext());}

/* Palabra reservada StopLoop */
( stopLoop ) {return new Symbol(sym.StopLoop, yychar, yyline, yytext());}

/* Método SpinCraneLeft */
( spinCraneLeft ) {return new Symbol(sym.SpinCraneLeft, yychar, yyline, yytext());}

/* Método SpinCraneRight */
( spinCraneRight ) {return new Symbol(sym.SpinCraneRight, yychar, yyline, yytext());}

/* Método MoveFowardCrane */
( moveFowardCrane ) {return new Symbol(sym.MoveFowardCrane, yychar, yyline, yytext());}

/* Método MoveBackCrane */
( moveBackCrane ) {return new Symbol(sym.MoveBackCrane, yychar, yyline, yytext());}

/* Método SpinBallLeft */
( spinBallLeft ) {return new Symbol(sym.SpinBallLeft, yychar, yyline, yytext());}

/* Método SpinBallRight */
( spinBallRight ) {return new Symbol(sym.SpinBallRight, yychar, yyline, yytext());}

/* Método HitToTheLeft */
( hitToTheLeft ) {return new Symbol(sym.HitToTheLeft, yychar, yyline, yytext());}

/* Método HitToTheRight */
( hitToTheRight ) {return new Symbol(sym.HitToTheRight, yychar, yyline, yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual, yychar, yyline, yytext());}

/* Operador Suma */
( "+" ) {return new Symbol(sym.Suma, yychar, yyline, yytext());}

/* Operador Resta */
( "-" ) {return new Symbol(sym.Resta, yychar, yyline, yytext());}

/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Multiplicacion, yychar, yyline, yytext());}

/* Operador Division */
( "/" ) {return new Symbol(sym.Division, yychar, yyline, yytext());}

/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|" ) {return new Symbol(sym.Op_logico, yychar, yyline, yytext());}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" | "<<" | ">>" ) {return new Symbol(sym.Op_relacional, yychar, yyline, yytext());}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" ) {return new Symbol(sym.Op_atribucion, yychar, yyline, yytext());}

/* Operadores Incremento y decremento */
( "++" | "--" ) {return new Symbol(sym.Op_incremento, yychar, yyline, yytext());}

/*Operadores Booleanos*/
( true | false ) {return new Symbol(sym.Op_booleano, yychar, yyline, yytext());}

/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a, yychar, yyline, yytext());}

/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c, yychar, yyline, yytext());}

/* Llave de apertura */
( "{" ) {return new Symbol(sym.Llave_a, yychar, yyline, yytext());}

/* Llave de cierre */
( "}" ) {return new Symbol(sym.Llave_c, yychar, yyline, yytext());}

/* Corchete de apertura */
( "[" ) {return new Symbol(sym.Corchete_a, yychar, yyline, yytext());}

/* Corchete de cierre */
( "]" ) {return new Symbol(sym.Corchete_c, yychar, yyline, yytext());}

/* Marcador de inicio de algoritmo */
( "start" ) {return new Symbol(sym.Start, yychar, yyline, yytext());}

/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma, yychar, yyline, yytext());}

/* Identificador */
{L}({L}|{D})* {return new Symbol(sym.Identificador, yychar, yyline, yytext());}

/* Numero */
("(-"{D}+")")|{D}+ {return new Symbol(sym.Numero, yychar, yyline, yytext());}

/* Sentencia Desconocida */
//^(("start")|(.))+ {return new Symbol(sym.Desconocido, yychar, yyline, yytext());}

/* Error de analisis */
 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
