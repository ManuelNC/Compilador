package AnalizadorLexico;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.JOptionPane;



public class Analizador
{
	int fila=1,col;
	boolean error=false,errorS,banLlave=false,banPar=false,archivoFind=true;
	int iniciador;
	ArrayList<Token> TablaSimbolos = new ArrayList<Token>();
	ArrayList<Token> PalabrasAnalizadas = new ArrayList<Token>();
	public Analizador(String dir) {
		AnalisisLexico(dir);
		if(archivoFind)
		{
			if(error)
				System.out.println("Error Lexico.");
			else
			{
				iniciador=0;
				errorS=false;
				AnalisisSintactico();
			
				if(!errorS)
				{
					System.out.println("No Ocurrio Ningun Error");
					
				
						//System.out.println(TablaSimbolos.get(i).toString());
						
						
						MostrarTablaSimbolos aplicacion= new MostrarTablaSimbolos("TABLA DE SIMBOLOS", TablaSimbolos);
						aplicacion.setSize(725,200);
						aplicacion.setLocationRelativeTo(null);
						aplicacion.setVisible(true);
					
					
				}
			}
		}
	}
	public void AnalisisLexico(String dir)
	{
		String linea="",cadena="";
		int contador;
		StringTokenizer tokenizer;	//Tokenizer -> Divide las cadenas delimitadas por espacios
		try
		{
			  //Acceder al archivo
	          FileReader file = new FileReader(dir);
	          BufferedReader archivo = new BufferedReader(file);
	          //Asigna la primera linea del archivo
	          linea = archivo.readLine();
	          while (linea!=null)
	          {
	        	    col=1;
	                tokenizer = new StringTokenizer(linea);
	                contador=tokenizer.countTokens();	//cuanta los tokens que contiene
	                //Recorre el tokenizer
	                for(int i=0; i<contador; i++)
	                {
	                	cadena = tokenizer.nextToken();	//Obtiene el siguiente token
	                	verificaToken(cadena);
	                	col++;
	                }
	                linea=archivo.readLine();	//asigna la siguiente linea
	                fila++;
	          }
	          archivo.close();	//cierra el archivo
		}catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Archivo no encontrado.");
			archivoFind = false;
		}
		//Imprimir el contenido de cada token
		//for(int i=0; i<CadenasAnalizadas.size(); i++)
			//System.out.println(CadenasAnalizadas.get(i).toString());
	}
	
	
	public void AnalisisSintactico(){
		int cant = PalabrasAnalizadas.size();
		while(iniciador<cant){
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Modificador"))
			{
				if(!PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Tipo de dato") && !PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Clase"))
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un identificador o clase.");
					errorS=true;
				}
				iniciador++;
				continue;
			}
			
			//primer error
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Identificador"))
			{
				if(!Arrays.asList("{","=",";").contains(PalabrasAnalizadas.get(iniciador+1).getValor()))
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un simbolo.");
					errorS=true;
				}
				
				if(PalabrasAnalizadas.get(iniciador-1).getTipo().equals("Clase"))
					TablaSimbolos.add(new Token("Clase",PalabrasAnalizadas.get(iniciador-1).getValor(),PalabrasAnalizadas.get(iniciador).getNombre(),PalabrasAnalizadas.get(iniciador).getAsignacion(),PalabrasAnalizadas.get(iniciador).getLinea()));
				else
					TablaSimbolos.add(new Token("Identificador",PalabrasAnalizadas.get(iniciador-1).getValor(),PalabrasAnalizadas.get(iniciador).getNombre(),PalabrasAnalizadas.get(iniciador+2).getAsignacion(),PalabrasAnalizadas.get(iniciador).getLinea()));
				iniciador++;
				continue;
			}
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Clase") || PalabrasAnalizadas.get(iniciador).getTipo().equals("Tipo de dato"))
			{
				if(!PalabrasAnalizadas.get(iniciador-1).getTipo().equals("Modificador") && PalabrasAnalizadas.get(iniciador).getTipo().equals("Tipo de dato"))
				{
					if(!PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Identificador"))
					{
						System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un identificador.");
						errorS=true;
					}
				}
				else if(PalabrasAnalizadas.get(iniciador-1).getTipo().equals("Modificador"))
				{
					if(!PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Identificador"))
					{
						System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un identificador.");
						errorS=true;
					}
				}
				else
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un modificador.");
					errorS=true;
				}
				
				iniciador++;
				continue;
			}
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Simbolo"))
			{
				if(Arrays.asList("{","}").contains(PalabrasAnalizadas.get(iniciador).getValor()))
				{
					if(cuentaLlaves())
					{
						banLlave = true;
						errorS=true;
					}
				}
				else if(Arrays.asList("(",")").contains(PalabrasAnalizadas.get(iniciador).getValor()))
				{
					if(cuentaParentesis())
					{						
						banPar = true;
						errorS=true;
					}
				}
				//error 2
				else if(PalabrasAnalizadas.get(iniciador).getValor().equals("="))
				{
					if(PalabrasAnalizadas.get(iniciador-1).getTipo().equals("Identificador"))
					{
						if(!PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Constante"))
						{
							System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba una constante.");
							errorS=true;
						}
					}
					else
					{
						System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un identificador.");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Constante"))
			{
				if(PalabrasAnalizadas.get(iniciador-1).getValor().equals("="))
				{
					if(!PalabrasAnalizadas.get(iniciador+1).getValor().equals(";"))
					{
						System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" asignacion invalida.");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Palabra Reservada"))
			{
				if(PalabrasAnalizadas.get(iniciador).getValor().equals("if"))
				{
					if(!PalabrasAnalizadas.get(iniciador+1).getValor().equals("("))
					{
						System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un (");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Palabra Reservada"))
			{
				if(PalabrasAnalizadas.get(iniciador).getValor().equals("while"))
				{
					if(!PalabrasAnalizadas.get(iniciador+1).getValor().equals("("))
					{
						System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba un (");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			
			//error 2
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Operador Logico"))
			{
				if(!PalabrasAnalizadas.get(iniciador-1).getTipo().equals("Constante"))
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba una constante");
					errorS=true;
				}
				if(!PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Constante"))
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba una constante");
					errorS=true;
				}
				iniciador++;
				continue;
			}
			if(PalabrasAnalizadas.get(iniciador).getTipo().equals("Operador Aritmetico"))
			{
				if(!PalabrasAnalizadas.get(iniciador-1).getTipo().equals("Constante"))
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba una constante");
					errorS=true;
				}
				if(!PalabrasAnalizadas.get(iniciador+1).getTipo().equals("Constante"))
				{
					System.out.println("Error Sintactico Linea "+PalabrasAnalizadas.get(iniciador).getLinea()+" se esperaba una constante");
					errorS=true;
				}
				iniciador++;
				continue;
			}
		}
		if(banLlave)
			System.out.println("Error Sintactico falta de llave.");
		if(banPar)
			System.out.println("Error Sintactico falta de parentesis.");
	}
	
	public boolean cuentaParentesis(){
		int cant1=0,cant2=0;
		for(int i = 0;i<PalabrasAnalizadas.size();i++)
		{
			if(PalabrasAnalizadas.get(i).getValor().equals("("))
				cant1++;
			if(PalabrasAnalizadas.get(i).getValor().equals(")"))
				cant2++;
		}
		if(cant1==cant2)
			return false;
		return true;
	}
	public boolean cuentaLlaves(){
		int cant1=0,cant2=0;
		for(int i = 0;i<PalabrasAnalizadas.size();i++)
		{
			if(PalabrasAnalizadas.get(i).getValor().equals("{"))
				cant1++;
			if(PalabrasAnalizadas.get(i).getValor().equals("}"))
				cant2++;
		}
		if(cant1==cant2)
			return false;
		return true;
	}
	public void verificaToken(String token)
	{
		if(modificador(token))
			return;
		if(palabraReservada(token))
			return;
		if(tipoEspecifico(token))
			return;
		if(simbolo(token))
			return;
		if(operadorLogico(token))
			return;
		if(operadorAritmetico(token))
			return;
		if(tipoClase(token))
			return;
		if(constante(token))
			return;
		Pattern patron = Pattern.compile("[a-zA-Z0-9_]");//Expresion regular(alfabeto del resto de identificadores)
		Matcher match = patron.matcher(token);
		if(match.find())
			PalabrasAnalizadas.add(new Token("Identificador",token,token,token,fila));
		else
		{
			System.out.println("Token no valido en la fila: "+fila+" Columna: "+col);
			System.out.println(token);
			error=true;
		}
	}
	public boolean modificador(String token)
	{
		if(token.equals("public")||token.equals("private"))
		{
			PalabrasAnalizadas.add(new Token("Modificador",token,token,token,fila));
			return true;
		} 
		return false;
	}
	public boolean palabraReservada(String token)
	{
		if(token.equals("if") || token.equals("while"))
		{
			PalabrasAnalizadas.add(new Token("Palabra Reservada",token,token,token,fila));
			return true;
		}
		return false;
	}
	public boolean tipoEspecifico(String token)
	{
		if(token.equals("int")|| token.equals("boolean"))
		{
			PalabrasAnalizadas.add(new Token("Tipo de dato",token,token,token,fila));
			return true;
		}
		return false;
	}
	public boolean simbolo(String token)
	{
		if(token.equals("(")||token.equals(")")||token.equals("{")||token.equals("}")||token.equals(";")||token.equals("="))
		{
			PalabrasAnalizadas.add(new Token("Simbolo","Simbolo",token,token,fila));
			return true;
		}
		return false;
	}
	public boolean operadorLogico(String token)
	{
		if(token.equals("<")||token.equals(">")||token.equals("<=")||token.equals(">=")||token.equals("!=")||token.equals("=="))
		{
			PalabrasAnalizadas.add(new Token("Operador Logico","Operador Logico",token,token,fila));
			return true;
		}
		return false;
	}
	
	public boolean operadorAritmetico(String token)
	{
		if(token.equals("+")||token.equals("-")||token.equals("/")||token.equals("*")||token.equals("="))
		{
			PalabrasAnalizadas.add(new Token("Operador Aritmetico","Operador Aritmetico",token,token,fila));
			return true;
		}
		return false;
	}
	public boolean tipoClase(String token)
	{
		if(token.equals("class"))
		{
			PalabrasAnalizadas.add(new Token("Clase","Clase",token,token,fila));
			return true;
		}
		return false;
	}
	public boolean constante(String token){
		if(token.equals("true") || token.equals("false") || token.equals("1") || token.equals("2") || token.equals("3") || token.equals("4") || token.equals("5") || token.equals("6") || token.equals("7") || token.equals("8") || token.equals("9") || token.equals("0"))
		{
			PalabrasAnalizadas.add(new Token("Constante",token,token,token,fila));
			return true;
		}
		return false;
	}
}
