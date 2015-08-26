package gestor.recursosdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;

public class TablaEmpleados
{
	private Connection conexion;
	
	public TablaEmpleados (Connection conexion)
	{
		this.conexion = conexion;
	}
	
	public void crearTabla () throws SQLException
	{
		String sentenciaCrear = "CREATE TABLE Empleados " + "(codigo CHAR(9) NOT NULL, " + 
					"nombre VARCHAR(10) NOT NULL, " + "apellido VARCHAR(10) NOT NULL, " +
					"puesto VARCHAR(10) NOT NULL, " + "PRIMARY KEY (codigo))";

		Statement sentencia = conexion.createStatement();
		
		sentencia.executeUpdate(sentenciaCrear);

		sentencia.close();
	}
	public void poblarTabla (String codigo, String nombre, String apellido, String puesto) throws SQLException
	{
		String sentenciaPoblar = "INSERT INTO Empleados " + "VALUES (?, ?, ?, ?)";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaPoblar);
		
		preSentencia.setString(1, codigo);
		preSentencia.setString(2, nombre);
		preSentencia.setString(3, apellido);
		preSentencia.setString(4, puesto);
		preSentencia.executeUpdate();
	
		preSentencia.close();
	}
	public void consultarTabla (int opcion, String valor) throws SQLException
	{
		String sentenciaConsultar;
		PreparedStatement preSentencia = null;

		switch (opcion)
		{
			case 1:
				sentenciaConsultar = "SELECT * FROM Empleados " + "WHERE codigo = ?";
	
				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 2:
				sentenciaConsultar = "SELECT * FROM Empleados " + "WHERE nombre = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 3:
				sentenciaConsultar = "SELECT * FROM Empleados " + "WHERE apellido = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 4:
				sentenciaConsultar = "SELECT * FROM Empleados " + "WHERE puesto = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
		}
				
		ResultSet resultado = preSentencia.executeQuery();
		System.out.println("\t  Codigo  \t\t  Nombre  \t\t Apellido \t\t  Puesto  ");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("nombre") + 
					   "\t\t" + resultado.getString("apellido") + 
					   "\t\t" + resultado.getString("puesto"));
		}
			
		preSentencia.close();
	}
	public void verTabla () throws SQLException
	{
		String sentenciaVer = "SELECT * FROM Empleados";
		
		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaVer);
		System.out.println("\t  Codigo  \t\t  Nombre  \t\t Apellido \t\t  Puesto  ");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("nombre") + 
					   "\t\t" + resultado.getString("apellido") + 
					   "\t\t" + resultado.getString("puesto"));
		}
		
		sentencia.close();
	}

	public void eliminarEmpleado (String codigo) throws SQLException
	{
		String sentenciaEliminar = "DELETE FROM Empleados " + "WHERE codigo = ?";
	
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaEliminar);
		preSentencia.setString(1, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	
	public void actualizarNombre (String nombre, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Empleados " + "SET nombre = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, nombre);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarApellido (String apellido, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Empleados " + "SET apellido = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, apellido);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarPuesto (String puesto, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Empleados " + "SET puesto = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, puesto);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public String recuperarNombre (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT nombre FROM Empleados " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String nombre = resultado.getString("nombre");
		
		preSentencia.close();

		return nombre;
	}
	public String recuperarApellido (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT apellido FROM Empleados " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String apellido = resultado.getString("apellido");
		
		preSentencia.close();

		return apellido;
	}
	public String recuperarPuesto (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT puesto FROM Empleados " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String puesto = resultado.getString("puesto");
		
		preSentencia.close();

		return puesto;
	}

	public LinkedList<String> getCodigos () throws SQLException
	{
		LinkedList<String> codigos = new LinkedList<String>();
		String sentenciaGet = "SELECT codigo FROM Empleados";

		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaGet);
		while (resultado.next())
		{
			codigos.add(resultado.getString("codigo"));
		}

		return codigos;
	}
}
