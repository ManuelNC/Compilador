package AnalizadorLexico;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;



import java.awt.BorderLayout;
import java.util.ArrayList;





public class MostrarTablaSimbolos extends JFrame
{
	
	JTable j;
	
	
	public MostrarTablaSimbolos( String string, ArrayList<AnalizadorLexico.Token> tablaSimbolos)
	{
		this.setTitle(string);
		String datos[][] = new String[tablaSimbolos.size()][5];  
		
		String cabecera[]= {"TIPO","NOMBRE","VALOR","ASIGNACION","LINEA"};
		
		for(int i = 0;i<tablaSimbolos.size();i++)
		{
			datos [i][0]= tablaSimbolos.get(i).getTipo();
			datos [i][1]= tablaSimbolos.get(i).getNombre();
			datos [i][2]= tablaSimbolos.get(i).getValor();
			datos [i][3]= tablaSimbolos.get(i).getAsignacion();
			datos [i][4]= Integer.toString(tablaSimbolos.get(i).getLinea());
		}
		
		
		
		
		
		
		
		JTable j = new JTable(datos,cabecera);
		
		
		add(new JScrollPane(j), BorderLayout.CENTER);
		pack();
		//setSize(500, 200);
		
	}
}




