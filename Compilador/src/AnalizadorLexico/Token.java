package AnalizadorLexico;

import javax.xml.crypto.Data;

public class Token
{
	private String tipo;
	private String nombre;
	private String valor;
	private String asignacion;
	private int linea;
	public Token(String tipo,String nombre, String valor,String asignacion,int linea)
	{
		this.tipo=tipo;
		this.nombre=nombre;
		this.valor=valor;
		this.asignacion=asignacion;
		this.linea=linea;
	}
	public String getTipo()
	{
		return tipo;
	}
	public String getValor()
	{
		return valor;
	}
	public int getLinea()
	{
		return linea;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	public void setValor(String valor)
	{
		this.valor = valor;
	}
	public void setLinea(int linea)
	{
		this.linea = linea;
	}
	
	public String getAsignacion() 
	{
		return asignacion;
	}
	public void setAsignacion(String asignacion) 
	{
		this.asignacion = asignacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		nombre = nombre;
	}
	
	public String toString() 
	{
		return "Tipo: "+getTipo()+"\nNombre: "+getNombre()+"\nValor: "+getValor()+"\nAsignacion: "+getAsignacion()+"\nLinea: "+getLinea();
	}
	
}