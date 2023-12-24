

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
STRING = (\")[^\"]*(\")

TCHAR = (\')[^\"](\')

ENTERO=[0-9]+
DECIMAL= [0-9]+("."[0-9]+)

ID = [a-zA-Z_][a-zA-Z0-9_]*


CARACTER = [^\r\n]



BLANCOS=[ \r\t]+
SALTOLINEA = [\ \n]

COM_LINEA = "//"{CARACTER}* {SALTOLINEA}?
COM_MULTI ="/*"([^ \*\/]|{SALTOLINEA})* "*/>"

%%
/* 3. Reglas Semanticas */

"cadena" {  
    System.out.println("Reconocio string: "+yytext());
    listaTokens.add(new elToken(yytext(),"RSTRING",yyline,yycolumn));

    return new Symbol(sym.RSTRING,yyline,yychar,yytext());
}

"doble" {  
    System.out.println("Reconocio double: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"RDOUBLE",yyline,yycolumn));
    return new Symbol(sym.RDOUBLE,yyline,yychar,yytext());
}

"entero" {  
    System.out.println("Reconocio int: "+yytext());
    listaTokens.add(new elToken(yytext(),"RINT",yyline,yycolumn)); 
    return new Symbol(sym.RINT,yyline,yychar,yytext());
} 

"caracter" {  
    System.out.println("Reconocio char: "+yytext());
    listaTokens.add(new elToken(yytext(),"RCHAR",yyline,yycolumn)); 
    return new Symbol(sym.RCHAR,yyline,yychar,yytext());
} 

"binario" {  
    System.out.println("Reconocio bool: "+yytext());
    listaTokens.add(new elToken(yytext(),"RBOOL",yyline,yycolumn)); 
    return new Symbol(sym.RBOOL,yyline,yychar,yytext());
} 

"true" {  
    System.out.println("Reconocio true: "+yytext());
    listaTokens.add(new elToken(yytext(),"RTRUE",yyline,yycolumn));
    
    return new Symbol(sym.RTRUE,yyline,yychar,yytext());
}

"false" {  
    System.out.println("Reconocio false: "+yytext());
    listaTokens.add(new elToken(yytext(),"RFALSE",yyline,yycolumn));
    
    return new Symbol(sym.RFALSE,yyline,yychar,yytext());
}

";" { System.out.println("Reconocio "+yytext()+" punto y coma"); 
        listaTokens.add(new elToken(yytext(),"PUNTOCOMA",yyline,yycolumn));
        
        return new Symbol(sym.PUNTOCOMA,yyline,yychar, yytext());} 

"," { System.out.println("Reconocio "+yytext()+" , coma"); 
        listaTokens.add(new elToken(yytext(),"COMA",yyline,yycolumn));
        
        return new Symbol(sym.COMA ,yyline,yychar, yytext());} 

"[" { System.out.println("Reconocio "+yytext()+" ["); 
    listaTokens.add(new elToken(yytext(),"COR_IZQ",yyline,yycolumn));
    return new Symbol(sym.COR_IZQ,yyline,yychar, yytext());} 

"]" { System.out.println("Reconocio "+yytext()+" ]"); 
    listaTokens.add(new elToken(yytext(),"COR_DER",yyline,yycolumn));
    return new Symbol(sym.COR_DER,yyline,yychar, yytext());}

"(" { System.out.println("Reconocio "+yytext()+" ("); 
    listaTokens.add(new elToken(yytext(),"PAR_IZQ",yyline,yycolumn));
   
    return new Symbol(sym.PAR_IZQ,yyline,yychar, yytext());}

")" { System.out.println("Reconocio "+yytext()+" )"); 
    listaTokens.add(new elToken(yytext(),"PAR_DER",yyline,yycolumn));
    
    return new Symbol(sym.PAR_DER,yyline,yychar, yytext());}

"+" { System.out.println("Reconocio "+yytext()+" +"); 
    listaTokens.add(new elToken(yytext(),"MAS",yyline,yycolumn));
    
    return new Symbol(sym.MAS,yyline,yychar, yytext());}

"-" { System.out.println("Reconocio "+yytext()+" -"); 
    listaTokens.add(new elToken(yytext(),"MENOS",yyline,yycolumn));
    
    return new Symbol(sym.MENOS,yyline,yychar, yytext());}

"*" { System.out.println("Reconocio "+yytext()+" *"); 
    listaTokens.add(new elToken(yytext(),"POR",yyline,yycolumn));
   
    return new Symbol(sym.POR,yyline,yychar, yytext());}

"=" { System.out.println("Reconocio "+yytext()+" ="); 
    listaTokens.add(new elToken(yytext(),"IGUAL",yyline,yycolumn));
   
    return new Symbol(sym.IGUAL,yyline,yychar, yytext());}

"/" { System.out.println("Reconocio "+yytext()+" /"); 
    listaTokens.add(new elToken(yytext(),"DIV",yyline,yycolumn));
   
    return new Symbol(sym.DIV,yyline,yychar, yytext());}

">" { System.out.println("Reconocio "+yytext()+" >"); 
    listaTokens.add(new elToken(yytext(),"MAYOR",yyline,yycolumn));
  
    return new Symbol(sym.MAYOR,yyline,yychar, yytext());}

"<" { System.out.println("Reconocio "+yytext()+" <"); 
    listaTokens.add(new elToken(yytext(),"MENOR",yyline,yycolumn));
    
    return new Symbol(sym.MENOR,yyline,yychar, yytext());}

">=" { System.out.println("Reconocio "+yytext()+" >="); 
    listaTokens.add(new elToken(yytext(),"MAYORIGUAL",yyline,yycolumn));
   
    return new Symbol(sym.MAYORIGUAL,yyline,yychar, yytext());}

"<=" { System.out.println("Reconocio "+yytext()+" <="); 
    listaTokens.add(new elToken(yytext(),"MENORIGUAL",yyline,yycolumn));
   
    return new Symbol(sym.MENORIGUAL,yyline,yychar, yytext());}

"==" { System.out.println("Reconocio "+yytext()+" =="); 
    listaTokens.add(new elToken(yytext(),"EQUIVALENTE",yyline,yycolumn));
  
    return new Symbol(sym.EQUIVALENTE,yyline,yychar, yytext());}

"!=" { System.out.println("Reconocio "+yytext()+" !="); 
    listaTokens.add(new elToken(yytext(),"DISTINTO",yyline,yycolumn));
   

    return new Symbol(sym.DISTINTO,yyline,yychar, yytext());}

"&&" { System.out.println("Reconocio "+yytext()+" &&"); 
    listaTokens.add(new elToken(yytext(),"AND",yyline,yycolumn));
  
    return new Symbol(sym.AND,yyline,yychar, yytext());}

"||" { System.out.println("Reconocio "+yytext()+" ||"); 
    listaTokens.add(new elToken(yytext(),"OR",yyline,yycolumn));
   
    return new Symbol(sym.OR,yyline,yychar, yytext());}

"!" { System.out.println("Reconocio "+yytext()+" !"); 
    listaTokens.add(new elToken(yytext(),"NOT",yyline,yycolumn));
  
    return new Symbol(sym.NOT,yyline,yychar, yytext());}    


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

{DECIMAL} {System.out.println("Reconocio Decimal: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"DECIMAL",yyline,yycolumn));
    
    return new Symbol(sym.DECIMAL,yyline,yychar, yytext());} 


{ENTERO} {System.out.println("Reconocio Entero: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"ENTERO",yyline,yycolumn));
   
    return new Symbol(sym.INT,yyline,yychar, yytext());}

{TCHAR} {System.out.println("Reconocio caracter: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"TCHAR",yyline,yycolumn));
   
    return new Symbol(sym.TCHAR,yyline,yychar, yytext());}


. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.
    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
    listaErrores.add(new fallos(yytext(),"Error Lexico",yyline,yycolumn));
}