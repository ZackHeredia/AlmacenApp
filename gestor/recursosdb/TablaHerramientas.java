package gestor.recursosdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;

public class TablaHerramientas
{
	private Connection conexion;
	
	public TablaHerramientas (Connection conexion)
	{
		this.conexion = conexion;
	}
	
	public void crearTabla () throws SQLException
	{
		String sentenciaCrear = "CREATE TABLE Herramientas " + "(codigo CHAR(9) NOT NULL, " + 
					"tipo VARCHAR(15) NOT NULL, " + "proposito VARCHAR(30) NOT NULL, " +
					"estaDisponible BOOLEAN NOT NULL, " + "codigoUsuario CHAR(9), " +
					"ultimaModificacion DATE NOT NULL, " + "PRIMARY KEY (codigo), " +
					"FOREIGN KEY (codigoUsuario) REFERENCES Empleados (codigo))";

		Statement sentencia = conexion.createStatement();
		
		sentencia.executeUpdate(sentenciaCrear);

		sentencia.close();
	}
	public void poblarTabla (String codigo, String tipo, String proposito, Date ultimaModificacion) throws SQLException
	{
		String sentenciaPoblar = "INSERT INTO Herramientas " + "VALUES (?, ?, ?, true, NULL, ?)";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaPoblar);
		
		preSentencia.setString(1, codigo);
		preSentencia.setString(2, tipo);
		preSentencia.setString(3, proposito);
		preSentencia.setDate(4, ultimaModificacion);
		preSentencia.executeUpdate();
	
		preSentencia.close();
	}
	public void consultarTabla (int opcion, String valor) throws SQLException
	{
		String sentenciaConsultar;
		boolean bValor;
		Date dValor;
		PreparedStatement preSentencia = null;

		switch (opcion)
		{
			case 1:
				sentenciaConsultar = "SELECT * FROM Herramientas " + "WHERE codigo = ?";
	
				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 2:
				sentenciaConsultar = "SELECT * FROM Herramientas " + "WHERE tipo = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 3:
				sentenciaConsultar = "SELECT * FROM Herramientas " + "WHERE proposito = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 4:
				bValor = Boolean.parseBoolean(valor);
				
				sentenciaConsultar = "SELECT * FROM Herramientas " + "WHERE estaDisponible = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setBoolean(1, bValor);

				break;
			case 5:
				sentenciaConsultar = "SELECT * FROM Herramientas " + "WHERE codigoUsuario = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 6:
				dValor = Date.valueOf(valor);
				sentenciaConsultar = "SELECT * FROM Herramientas " + "WHERE ultimaModificacion = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setDate(1, dValor);

				break;
		}
				
		ResultSet resultado = preSentencia.executeQuery();
		System.out.println("\t Codigo  \t\t      Tipo      \t\t           Proposito           \t\tDisponible\t\t Usuario \t\tModificada");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("tipo") + 
					   "\t\t" + resultado.getString("proposito") + 
					   "\t\t" + resultado.getBoolean("estaDisponible") + 
					   "\t\t" + resultado.getString("codigoUsuario") + 
					   "\t\t" + resultado.getDate("ultimaModificacion"));
		}
			
		preSentencia.close();
	}
	public void verTabla () throws SQLException
	{
		String sentenciaVer = "SELECT * FROM Herramientas";
		
		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaVer);
		System.out.println("\t  Codigo  \t\t      Tipo      \t\t           Proposito           \t\tDisponible\t\t Usuario \t\tModificada");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("tipo") + 
					   "\t\t" + resultado.getString("proposito") + 
					   "\t\t" + resultado.getBoolean("estaDisponible") + 
					   "\t\t" + resultado.getString("codigoUsuario") + 
					   "\t\t" + resultado.getDate("ultimaModificacion"));
		}
		
		sentencia.close();
	}
	
	public void eliminarHerramienta (String codigo) throws SQLException
	{
		String sentenciaEliminar = "DELETE FROM Herramientas " + "WHERE codigo = ?";
	
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaEliminar);
		preSentencia.setString(1, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public void actualizarTipo (String tipo, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Herramientas " + "SET tipo = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, tipo);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarProposito (String proposito, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Herramientas " + "SET proposito = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, proposito);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarEstaDisponible (boolean estaDisponible, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Herramientas " + "SET estaDisponible = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setBoolean(1, estaDisponible);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarCodigoUsuario (String codigoUsuario, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Herramientas " + "SET codigoUsuario = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, codigoUsuario);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarUltimaModificacion (Date ultimaModificacion, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Herramientas " + "SET ultimaModificacion = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setDate(1, ultimaModificacion);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public String recuperarTipo (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT tipo FROM Herramientas " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();		
		resultado.next();
		String tipo = resultado.getString("tipo");
		
		preSentencia.close();

		return tipo;
	}
	public String recuperarProposito (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT proposito FROM Herramientas " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String proposito = resultado.getString("proposito");
		
		preSentencia.close();

		return proposito;
	}
	public boolean recuperarEstaDisponible (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT estaDisponible FROM Herramientas " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		boolean estaDisponible = resultado.getBoolean("estaDiponible");
		
		preSentencia.close();

		return estaDisponible;
	}
	public String recuperarCodigoUsuario (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT codigoUsuario FROM Herramientas " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String codigoUsuario = resultado.getString("codigoUsuario");
		
		preSentencia.close();

		return codigoUsuario;
	}
	public Date recuperarUltimaModificacion (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT ultimaModificacion FROM Herramientas " + "WHERE codigo = ?";
		
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		Date ultimaModificacion = resultado.getDate("ultimaModificacion");
		
		preSentencia.close();

		return ultimaModificacion;
	}

	public LinkedList<String> getCodigos () throws SQLException
	{
		LinkedList<String> codigos = new LinkedList<String>();
		String sentenciaGet = "SELECT codigo FROM Herramientas";

		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaGet);
		while (resultado.next())
		{
			codigos.add(resultado.getString("codigo"));
		}

		return codigos;
	}
}
