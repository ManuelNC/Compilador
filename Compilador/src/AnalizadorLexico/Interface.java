package AnalizadorLexico;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Interface extends JFrame implements ActionListener
{
	JTextField direccion;
	JButton btnBuscar;
	public Interface()
	{
		super("INGRESA LA DIRECCION DEL ARCHIVO");
		Interfaz();
		
	}
	public void Interfaz()
	{
		
		setSize(400,150);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(0,1));
		
		Font fuente = new Font("Comic Sans", 3, 14);
      
		JLabel etq1 = new JLabel("Direccion Archivo: ");
		etq1.setFont(fuente);
		
		add(etq1);
		direccion = new JTextField(20);
		btnBuscar = new JButton("Buscar");
		direccion.setFont(fuente);
		btnBuscar.setFont(fuente);
		add(direccion);
		add(btnBuscar);
		
		btnBuscar.addActionListener(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent evt)
	{
		if(!direccion.getText().isEmpty())
			new Analizador(direccion.getText());
	}
	public static void main(String[] args)
	{
		new Interface();
	}
}