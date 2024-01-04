

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
COM_MULTI ="/*"([^ \*\/]|{SALTOLINEA})* "*/"

%%
/* 3. Reglas Semanticas */


"sino" {  
    System.out.println("Reconocio else: "+yytext());
    listaTokens.add(new elToken(yytext(),"RELSE",yyline,yycolumn));

    return new Symbol(sym.RELSE,yyline,yycolumn,yytext());
}

"si" {  
    System.out.println("Reconocio if: "+yytext());
    listaTokens.add(new elToken(yytext(),"RIF",yyline,yycolumn));

    return new Symbol(sym.RIF,yyline,yycolumn,yytext());
}

"para" {  
    System.out.println("Reconocio for: "+yytext());
    listaTokens.add(new elToken(yytext(),"RFOR",yyline,yycolumn));

    return new Symbol(sym.RFOR,yyline,yycolumn,yytext());
}

"selector" {  
    System.out.println("Reconocio switch: "+yytext());
    listaTokens.add(new elToken(yytext(),"RSWITCH",yyline,yycolumn));

    return new Symbol(sym.RSWITCH,yyline,yycolumn,yytext());
}

"continuar" {  
    System.out.println("Reconocio continue: "+yytext());
    listaTokens.add(new elToken(yytext(),"RCONTINUE",yyline,yycolumn));

    return new Symbol(sym.RCONTINUE,yyline,yycolumn,yytext());
}

"cortar" {  
    System.out.println("Reconocio break: "+yytext());
    listaTokens.add(new elToken(yytext(),"RBREAK",yyline,yycolumn));

    return new Symbol(sym.RBREAK,yyline,yycolumn,yytext());
}

"retorno" {  
    System.out.println("Reconocio return: "+yytext());
    listaTokens.add(new elToken(yytext(),"RRETURN",yyline,yycolumn));

    return new Symbol(sym.RRETURN,yyline,yycolumn,yytext());
}

"caso" {  
    System.out.println("Reconocio caso: "+yytext());
    listaTokens.add(new elToken(yytext(),"RCASE",yyline,yycolumn));

    return new Symbol(sym.RCASE,yyline,yycolumn,yytext());
}

"pordefecto" {  
    System.out.println("Reconocio caso: "+yytext());
    listaTokens.add(new elToken(yytext(),"RDEFAULT",yyline,yycolumn));

    return new Symbol(sym.RDEFAULT,yyline,yycolumn,yytext());
}

"hacer" {  
    System.out.println("Reconocio do: "+yytext());
    listaTokens.add(new elToken(yytext(),"RDO",yyline,yycolumn));

    return new Symbol(sym.RDO,yyline,yycolumn,yytext());
}

"ejecutar" {  
    System.out.println("Reconocio ejecutar: "+yytext());
    listaTokens.add(new elToken(yytext(),"REJECUTAR",yyline,yycolumn));

    return new Symbol(sym.REJECUTAR,yyline,yycolumn,yytext());
}

"mientras" {  
    System.out.println("Reconocio while: "+yytext());
    listaTokens.add(new elToken(yytext(),"RWHILE",yyline,yycolumn));

    return new Symbol(sym.RWHILE,yyline,yycolumn,yytext());
}



"imprimir" {  
    System.out.println("Reconocio imprimir: "+yytext());
    listaTokens.add(new elToken(yytext(),"RIMPRIMIR",yyline,yycolumn));

    return new Symbol(sym.RIMPRIMIR,yyline,yycolumn,yytext());
}

"cadena" {  
    System.out.println("Reconocio string: "+yytext());
    listaTokens.add(new elToken(yytext(),"RSTRING",yyline,yycolumn));

    return new Symbol(sym.RSTRING,yyline,yycolumn,yytext());
}

"doble" {  
    System.out.println("Reconocio double: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"RDOUBLE",yyline,yycolumn));
    return new Symbol(sym.RDOUBLE,yyline,yycolumn,yytext());
}

"entero" {  
    System.out.println("Reconocio int: "+yytext());
    listaTokens.add(new elToken(yytext(),"RINT",yyline,yycolumn)); 
    return new Symbol(sym.RINT,yyline,yycolumn,yytext());
} 

"caracter" {  
    System.out.println("Reconocio char: "+yytext());
    listaTokens.add(new elToken(yytext(),"RCHAR",yyline,yycolumn)); 
    return new Symbol(sym.RCHAR,yyline,yycolumn,yytext());
} 

"void" {  
    System.out.println("Reconocio void: "+yytext());
    listaTokens.add(new elToken(yytext(),"RVOID",yyline,yycolumn)); 
    return new Symbol(sym.RVOID,yyline,yycolumn,yytext());
} 

"binario" {  
    System.out.println("Reconocio bool: "+yytext());
    listaTokens.add(new elToken(yytext(),"RBOOL",yyline,yycolumn)); 
    return new Symbol(sym.RBOOL,yyline,yycolumn,yytext());
} 

"true" {  
    System.out.println("Reconocio true: "+yytext());
    listaTokens.add(new elToken(yytext(),"RTRUE",yyline,yycolumn));
    
    return new Symbol(sym.RTRUE,yyline,yycolumn,yytext());
}

"false" {  
    System.out.println("Reconocio false: "+yytext());
    listaTokens.add(new elToken(yytext(),"RFALSE",yyline,yycolumn));
    
    return new Symbol(sym.RFALSE,yyline,yycolumn,yytext());
}

"==" { System.out.println("Reconocio "+yytext()+" =="); 
    listaTokens.add(new elToken(yytext(),"EQUIVALENTE",yyline,yycolumn));
  
    return new Symbol(sym.EQUIVALENTE,yyline,yycolumn, yytext());}

"!=" { System.out.println("Reconocio "+yytext()+" !="); 
    listaTokens.add(new elToken(yytext(),"DISTINTO",yyline,yycolumn));
    return new Symbol(sym.DISTINTO,yyline,yycolumn, yytext());}

"<=" { System.out.println("Reconocio "+yytext()+" <="); 
    listaTokens.add(new elToken(yytext(),"MENORIGUAL",yyline,yycolumn));
   
    return new Symbol(sym.MENORIGUAL,yyline,yycolumn, yytext());}

">=" { System.out.println("Reconocio "+yytext()+" >="); 
    listaTokens.add(new elToken(yytext(),"MAYORIGUAL",yyline,yycolumn));
   
    return new Symbol(sym.MAYORIGUAL,yyline,yycolumn, yytext());}

";" { System.out.println("Reconocio "+yytext()+" punto y coma"); 
        listaTokens.add(new elToken(yytext(),"PUNTOCOMA",yyline,yycolumn));
        
        return new Symbol(sym.PUNTOCOMA,yyline,yycolumn, yytext());} 

":" { System.out.println("Reconocio "+yytext()+" :"); 
        listaTokens.add(new elToken(yytext(),"DOSPUNTOS",yyline,yycolumn));
        
        return new Symbol(sym.DOSPUNTOS,yyline,yycolumn, yytext());} 

"," { System.out.println("Reconocio "+yytext()+" , coma"); 
        listaTokens.add(new elToken(yytext(),"COMA",yyline,yycolumn));
        
        return new Symbol(sym.COMA ,yyline,yycolumn, yytext());} 

"?" { System.out.println("Reconocio "+yytext()+" ? "); 
        listaTokens.add(new elToken(yytext(),"INTERROGACION",yyline,yycolumn));
        
        return new Symbol(sym.INTERROGACION ,yyline,yycolumn, yytext());} 

"{" { System.out.println("Reconocio "+yytext()+" {"); 
    listaTokens.add(new elToken(yytext(),"LL_IZQ",yyline,yycolumn));
    return new Symbol(sym.LL_IZQ,yyline,yycolumn, yytext());} 

"}" { System.out.println("Reconocio "+yytext()+" }"); 
    listaTokens.add(new elToken(yytext(),"LL_DER",yyline,yycolumn));
    return new Symbol(sym.LL_DER,yyline,yycolumn, yytext());} 


"[" { System.out.println("Reconocio "+yytext()+" ["); 
    listaTokens.add(new elToken(yytext(),"COR_IZQ",yyline,yycolumn));
    return new Symbol(sym.COR_IZQ,yyline,yycolumn, yytext());} 

"]" { System.out.println("Reconocio "+yytext()+" ]"); 
    listaTokens.add(new elToken(yytext(),"COR_DER",yyline,yycolumn));
    return new Symbol(sym.COR_DER,yyline,yycolumn, yytext());}

"(" { System.out.println("Reconocio "+yytext()+" ("); 
    listaTokens.add(new elToken(yytext(),"PAR_IZQ",yyline,yycolumn));
   
    return new Symbol(sym.PAR_IZQ,yyline,yycolumn, yytext());}

")" { System.out.println("Reconocio "+yytext()+" )"); 
    listaTokens.add(new elToken(yytext(),"PAR_DER",yyline,yycolumn));
    
    return new Symbol(sym.PAR_DER,yyline,yycolumn, yytext());}

"+" { System.out.println("Reconocio "+yytext()+" +"); 
    listaTokens.add(new elToken(yytext(),"MAS",yyline,yycolumn));
    
    return new Symbol(sym.MAS,yyline,yycolumn, yytext());}

"-" { System.out.println("Reconocio "+yytext()+" -"); 
    listaTokens.add(new elToken(yytext(),"MENOS",yyline,yycolumn));
    
    return new Symbol(sym.MENOS,yyline,yycolumn, yytext());}

"*" { System.out.println("Reconocio "+yytext()+" *"); 
    listaTokens.add(new elToken(yytext(),"POR",yyline,yycolumn));
   
    return new Symbol(sym.POR,yyline,yycolumn, yytext());}

"=" { System.out.println("Reconocio "+yytext()+" ="); 
    listaTokens.add(new elToken(yytext(),"IGUAL",yyline,yycolumn));
   
    return new Symbol(sym.IGUAL,yyline,yycolumn, yytext());}

"/" { System.out.println("Reconocio "+yytext()+" /"); 
    listaTokens.add(new elToken(yytext(),"DIV",yyline,yycolumn));
   
    return new Symbol(sym.DIV,yyline,yycolumn, yytext());}

">" { System.out.println("Reconocio "+yytext()+" >"); 
    listaTokens.add(new elToken(yytext(),"MAYOR",yyline,yycolumn));
  
    return new Symbol(sym.MAYOR,yyline,yycolumn, yytext());}

"<" { System.out.println("Reconocio "+yytext()+" <"); 
    listaTokens.add(new elToken(yytext(),"MENOR",yyline,yycolumn));
    
    return new Symbol(sym.MENOR,yyline,yycolumn, yytext());}

"^" { System.out.println("Reconocio "+yytext()+" ^"); 
    listaTokens.add(new elToken(yytext(),"potencia",yyline,yycolumn));
    
    return new Symbol(sym.POTENCIA,yyline,yycolumn, yytext());}

"%" { System.out.println("Reconocio "+yytext()+" %"); 
    listaTokens.add(new elToken(yytext(),"MODULO",yyline,yycolumn));
    
    return new Symbol(sym.MODULO,yyline,yycolumn, yytext());}






   

    

"&&" { System.out.println("Reconocio "+yytext()+" &&"); 
    listaTokens.add(new elToken(yytext(),"AND",yyline,yycolumn));
  
    return new Symbol(sym.AND,yyline,yycolumn, yytext());}

"||" { System.out.println("Reconocio "+yytext()+" ||"); 
    listaTokens.add(new elToken(yytext(),"OR",yyline,yycolumn));
   
    return new Symbol(sym.OR,yyline,yycolumn, yytext());}

"!" { System.out.println("Reconocio "+yytext()+" !"); 
    listaTokens.add(new elToken(yytext(),"NOT",yyline,yycolumn));
  
    return new Symbol(sym.NOT,yyline,yycolumn, yytext());}    


{SALTOLINEA} {yychar=1; yycolumn=0;}

{BLANCOS} {} 
{COM_LINEA} { }
{COM_MULTI} { }

//{CARACTER} {System.out.println("Reconocio Caracter: "+yytext()); return new Symbol(sym.CARACTER,yyline,yychar, yytext());} 

{ID} {System.out.println("Reconocio ID: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"ID",yyline,yycolumn));
   
    return new Symbol(sym.ID,yyline,yycolumn, yytext());} 

//{DECIMAL} {System.out.println("Reconocio Decimal: "+yytext()); 
 //   listaTokens.add(new elToken(yytext(),"DECIMAL",yyline,yycolumn));
 
 //   return new Symbol(sym.DECIMAL,yyline,yychar, yytext());} 
{STRING} {System.out.println("Reconocio Cadena: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"STRING",yyline,yycolumn));
  
    return new Symbol(sym.STRING,yyline,yycolumn, yytext());}

{DECIMAL} {System.out.println("Reconocio Decimal: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"DECIMAL",yyline,yycolumn));
    
    return new Symbol(sym.DECIMAL,yyline,yycolumn, yytext());} 


{ENTERO} {System.out.println("Reconocio Entero: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"ENTERO",yyline,yycolumn));
   
    return new Symbol(sym.INT,yyline,yycolumn, yytext());}

{TCHAR} {System.out.println("Reconocio caracter: "+yytext()); 
    listaTokens.add(new elToken(yytext(),"TCHAR",yyline,yycolumn));
   
    return new Symbol(sym.TCHAR,yyline,yycolumn, yytext());}


. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.
    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
    listaErrores.add(new fallos(yytext(),"Error Lexico",yyline,yycolumn));
}