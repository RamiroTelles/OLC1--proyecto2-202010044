

/* 1. Package e importaciones */
package analizador;
import java_cup.runtime.Symbol;
import java.util.ArrayList;
import structuras.elToken;
import structuras.fallos;

%%
/* 2. Configuraciones para el analisis (Opciones y Declaraciones) */
%{
    //Codigo de usuario en sintaxis java
    //Agregar clases, variables, arreglos, objetos etc...
    public ArrayList<elToken> listaTokens = new ArrayList();

    public ArrayList<fallos> listaErrores = new ArrayList();

    

    
   
    

    public ArrayList<elToken> getTokens(){
        return listaTokens;
    }

    public ArrayList<fallos> getErrores(){
        return listaErrores;
    }


%}

//Directivas
%class scanner

%public 
%cup
%char
%column
%full
%line
%unicode
%ignorecase

//Inicializar el contador de columna y fila con 1
%init{ 
    yyline = 1; 
    yychar = 1; 
%init}

//Expresiones regulares
BLANCOS=[ \r\t]+
//CARACTER = ['][^'][']
STRING = (\"|\')[^\"\'](\"|\')

DECIMAL=[0-9]+
ID = [a-zA-Z_][a-zA-Z0-9_]*


CARACTER = [^\r\n]



BLANCOS=[ \r\t]+
SALTOLINEA = [\ \n]

COM_LINEA = "//"{CARACTER}* {SALTOLINEA}?
COM_MULTI ="<!"([^ !>]|{SALTOLINEA})* "!>"

%%
/* 3. Reglas Semanticas */

"~" { System.out.println("Reconocio "+yytext()+" ~"); 
        listaTokens.add(new elToken(yytext(),"CULEBRA",yyline,yycolumn));
        
        return new Symbol(sym.CULEBRA,yyline,yychar, yytext());} 

"," { System.out.println("Reconocio "+yytext()+" ~"); 
        listaTokens.add(new elToken(yytext(),"COMA",yyline,yycolumn));
        
        return new Symbol(sym.COMA,yyline,yychar, yytext());} 

"|" { System.out.println("Reconocio "+yytext()+" |"); 
        listaTokens.add(new elToken(yytext(),"BARRA_V",yyline,yycolumn));
        
        return new Symbol(sym.BARRA_V,yyline,yychar, yytext());} 

";" { System.out.println("Reconocio "+yytext()+" punto y coma"); 
        listaTokens.add(new elToken(yytext(),"PUNTOCOMA",yyline,yycolumn));
        
        return new Symbol(sym.PUNTOCOMA,yyline,yychar, yytext());} 

"{" {System.out.println("Reconocio "+yytext()+" {"); 
    listaTokens.add(new elToken(yytext(),"LLAVE_IZQ",yyline,yycolumn));
   
    
    return new Symbol(sym.LLAVE_IZQ,yyline,yychar, yytext());}

"}" {System.out.println("Reconocio "+yytext()+" }"); 
    listaTokens.add(new elToken(yytext(),"LLAVE_DER",yyline,yycolumn));
    
    return new Symbol(sym.LLAVE_DER,yyline,yychar, yytext());}


//"," { System.out.println("Reconocio "+yytext()+" ,"); 
//   listaTokens.add(new elToken(yytext(),"COMA",yyline,yycolumn));
//    return new Symbol(sym.COMA,yyline,yychar, yytext());}


"(" { System.out.println("Reconocio "+yytext()+" ("); 
    listaTokens.add(new elToken(yytext(),"PAR_IZQ",yyline,yycolumn));
   
    return new Symbol(sym.PAR_IZQ,yyline,yychar, yytext());}

")" { System.out.println("Reconocio "+yytext()+" )"); 
    listaTokens.add(new elToken(yytext(),"PAR_DER",yyline,yycolumn));
   
    return new Symbol(sym.PAR_DER,yyline,yychar, yytext());}

"+" { System.out.println("Reconocio "+yytext()+" +"); 
    listaTokens.add(new elToken(yytext(),"MAS",yyline,yycolumn));
  
    return new Symbol(sym.MAS,yyline,yychar, yytext());}


"*" { System.out.println("Reconocio "+yytext()+" *"); 
    listaTokens.add(new elToken(yytext(),"POR",yyline,yycolumn));
    
    return new Symbol(sym.POR,yyline,yychar, yytext());}


"->" { System.out.println("Reconocio "+yytext()+" ->"); 
    listaTokens.add(new elToken(yytext(),"FLECHA",yyline,yycolumn));
   
    return new Symbol(sym.FLECHA,yyline,yychar, yytext());}



":" { System.out.println("Reconocio "+yytext()+" :"); 
    listaTokens.add(new elToken(yytext(),"DOSPUNTOS",yyline,yycolumn));
   
    return new Symbol(sym.DOSPUNTOS,yyline,yychar, yytext());}

"?" { System.out.println("Reconocio "+yytext()+" ?"); 
    listaTokens.add(new elToken(yytext(),"INT",yyline,yycolumn));
   
    return new Symbol(sym.INT,yyline,yychar, yytext());}

"." { System.out.println("Reconocio "+yytext()+" ."); 
    listaTokens.add(new elToken(yytext(),"PUNTO",yyline,yycolumn));
   
    return new Symbol(sym.PUNTO,yyline,yychar, yytext());}

"CONJ" { System.out.println("Reconocio "+yytext()+" CONJ"); 
    listaTokens.add(new elToken(yytext(),"CONJ",yyline,yycolumn));
   
    return new Symbol(sym.CONJ,yyline,yychar, yytext());}

"\\n" { System.out.println("Reconocio "+yytext()+" \\n"); 
    listaTokens.add(new elToken(yytext(),"RSALTOLINEA",yyline,yycolumn));
   
    return new Symbol(sym.RSALTOLINEA,yyline,yychar, yytext());}

"\\\"" { System.out.println("Reconocio "+yytext()+" \""); 
    listaTokens.add(new elToken(yytext(),"RCOMILLAD",yyline,yycolumn));
   
    return new Symbol(sym.RCOMILLAD,yyline,yychar, yytext());}

"\\\'" { System.out.println("Reconocio "+yytext()+" \'"); 
    listaTokens.add(new elToken(yytext(),"RCOMILLAS",yyline,yycolumn));
   
    return new Symbol(sym.RCOMILLAS,yyline,yychar, yytext());}

{SALTOLINEA} {yychar=1;}

{BLANCOS} {} 
{COM_LINEA} { }
{COM_MULTI} { }

//{CARACTER} {System.out.println("Reconocio Caracter: "+yytext()); return new Symbol(sym.CARACTER,yyline,yychar, yytext());} 

{ID} {System.out.println("Reconocio ID: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"ID",yyline,yycolumn));
   
    return new Symbol(sym.ID,yyline,yychar, yytext());} 

//{DECIMAL} {System.out.println("Reconocio Decimal: "+yytext()); 
 //   listaTokens.add(new elToken(yytext(),"DECIMAL",yyline,yycolumn));
 
 //   return new Symbol(sym.DECIMAL,yyline,yychar, yytext());} 
{STRING} {System.out.println("Reconocio Cadena: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"STRING",yyline,yycolumn));
  
    return new Symbol(sym.STRING,yyline,yychar, yytext());}

{CARACTER} {System.out.println("Reconocio CARACTER: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"CARACTER",yyline,yycolumn));
  
    return new Symbol(sym.CARACTER,yyline,yychar, yytext());}


. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.
    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
    listaErrores.add(new fallos(yytext(),"Error Lexico",yyline,yycolumn));
}