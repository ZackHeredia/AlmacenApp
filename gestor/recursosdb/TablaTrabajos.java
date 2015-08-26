package gestor.recursosdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;

public class TablaTrabajos
{
	private Connection conexion;
	
	public TablaTrabajos (Connection conexion)
	{
		this.conexion = conexion;
	}
	
	public void crearTabla () throws SQLException
	{
		String sentenciaCrear = "CREATE TABLE Trabajos " + "(codigo CHAR(9) NOT NULL, " + 
					"descripcion VARCHAR(40) NOT NULL, " + "nombreCliente VARCHAR(20) NOT NULL, " +
					"PRIMARY KEY (codigo))";

		Statement sentencia = conexion.createStatement();
		
		sentencia.executeUpdate(sentenciaCrear);

		sentencia.close();
	}
	public void poblarTabla (String codigo, String descripcion, String nombreCliente) throws SQLException
	{
		String sentenciaPoblar = "INSERT INTO Trabajos " + "VALUES (?, ?, ?)";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaPoblar);
		
		preSentencia.setString(1, codigo);
		preSentencia.setString(2, descripcion);
		preSentencia.setString(3, nombreCliente);
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
				sentenciaConsultar = "SELECT * FROM Trabajos " + "WHERE codigo = ?";
	
				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 2:
				sentenciaConsultar = "SELECT * FROM Trabajos " + "WHERE descripcion = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 3:
				sentenciaConsultar = "SELECT * FROM Trabajos " + "WHERE nombreCliente = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
		}
				
		ResultSet resultado = preSentencia.executeQuery();
		System.out.println("\t  Codigo  \t\t               Descripcion               \t\t    NombreCliente    ");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("descripcion") + 
					   "\t\t" + resultado.getString("nombreCliente"));
		}
			
		preSentencia.close();
	}
	public void verTabla () throws SQLException
	{
		String sentenciaVer = "SELECT * FROM Trabajos";
		
		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaVer);
		System.out.println("\t  Codigo  \t\t               Descripcion               \t\t    NombreCliente    ");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("descripcion") + 
					   "\t\t" + resultado.getString("nombreCliente"));
		}
		
		sentencia.close();
	}
	
	public void eliminarTrabajo (String codigo) throws SQLException
	{
		String sentenciaEliminar = "DELETE FROM Trabajos " + "WHERE codigo = ?";
	
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaEliminar);
		preSentencia.setString(1, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public void actualizarDescripcion (String descripcion, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Trabajos " + "SET descripcion = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, descripcion);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarNombreCliente (String nombreCliente, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Trabajos " + "SET nombreCliente = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, nombreCliente);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public String recuperarDescripcion (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT descripcion FROM Trabajos " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String descripcion = resultado.getString("descripcion");
		
		preSentencia.close();

		return descripcion;
	}
	public String recuperarNombreCliente (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT nombreCliente FROM Trabajos " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String nombreCliente = resultado.getString("nombreCliente");
		
		preSentencia.close();

		return nombreCliente;
	}

	public LinkedList<String> getCodigos () throws SQLException
	{
		LinkedList<String> codigos = new LinkedList<String>();
		String sentenciaGet = "SELECT codigo FROM Trabajos";

		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaGet);
		while (resultado.next())
		{
			codigos.add(resultado.getString("codigo"));
		}

		return codigos;
	}
}
