package gestor.recursosdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;

public class TablaMateriales
{
	private Connection conexion;

	public TablaMateriales (Connection conexion)
	{
		this.conexion = conexion;
	}

	public void crearTabla () throws SQLException
	{
		String sentenciaCrear = "CREATE TABLE Materiales " + "(codigo CHAR(9) NOT NULL, " + 
					"tipo VARCHAR(15) NOT NULL, " + "proposito VARCHAR(30) NOT NULL, " +
					"estaDisponible BOOLEAN NOT NULL, " + "codigoTrabajo CHAR(9), " +
					"ultimaModificacion DATE NOT NULL, " + "PRIMARY KEY (codigo), " +
					"FOREIGN KEY (codigoTrabajo) REFERENCES Trabajos (codigo))";

		Statement sentencia = conexion.createStatement();

		sentencia.executeUpdate(sentenciaCrear);

		sentencia.close();
	}
	public void poblarTabla (String codigo, String tipo, String proposito, Date ultimaModificacion) throws SQLException
	{
		String sentenciaPoblar = "INSERT INTO Materiales " + "VALUES (?, ?, ?, true, NULL, ?)";

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
				sentenciaConsultar = "SELECT * FROM Materiales " + "WHERE codigo = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 2:
				sentenciaConsultar = "SELECT * FROM Materiales " + "WHERE tipo = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 3:
				sentenciaConsultar = "SELECT * FROM Materiales " + "WHERE proposito = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 4:
				bValor = Boolean.parseBoolean(valor);

				sentenciaConsultar = "SELECT * FROM Materiales " + "WHERE estaDisponible = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setBoolean(1, bValor);

				break;
			case 5:
				sentenciaConsultar = "SELECT * FROM Materiales " + "WHERE codigoTrabajo = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setString(1, valor);

				break;
			case 6:
				dValor = Date.valueOf(valor);

				sentenciaConsultar = "SELECT * FROM Materiales " + "WHERE ultimaModificacion = ?";

				preSentencia = conexion.prepareStatement(sentenciaConsultar);
				preSentencia.setDate(1, dValor);

				break;
		}

		ResultSet resultado = preSentencia.executeQuery();
		System.out.println("\t  Codigo  \t\t      Tipo      \t\t           Proposito           \t\tDisponible\t\t Trabajo \t\tModificada");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("tipo") + 
					   "\t\t" + resultado.getString("proposito") + 
					   "\t\t" + resultado.getBoolean("estaDisponible") + 
					   "\t\t" + resultado.getString("codigoTrabajo") + 
					   "\t\t" + resultado.getDate("ultimaModificacion"));
		}

		preSentencia.close();
	}
	public void verTabla () throws SQLException
	{
		String sentenciaVer = "SELECT * FROM Materiales";

		Statement sentencia = conexion.createStatement();

		ResultSet resultado = sentencia.executeQuery(sentenciaVer);
		System.out.println("\t Codigo  \t\t      Tipo      \t\t           Proposito           \t\tDisponible\t\t Trabajo \t\tModificada");
		while (resultado.next())
		{
			System.out.println("\t" + resultado.getString("codigo") + 
					   "\t\t" + resultado.getString("tipo") + 
					   "\t\t" + resultado.getString("proposito") + 
					   "\t\t" + resultado.getBoolean("estaDisponible") + 
					   "\t\t" + resultado.getString("codigoTrabajo") + 
					   "\t\t" + resultado.getDate("ultimaModificacion"));
		}

		sentencia.close();
	}

	public void eliminarMaterial (String codigo) throws SQLException
	{
		String sentenciaEliminar = "DELETE FROM Materiales " + "WHERE codigo = ?";
	
		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaEliminar);
		preSentencia.setString(1, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public void actualizarTipo (String tipo, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Materiales " + "SET tipo = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, tipo);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarProposito (String proposito, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Materiales " + "SET proposito = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, proposito);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarEstaDisponible (boolean estaDisponible, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Materiales " + "SET estaDisponible = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setBoolean(1, estaDisponible);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarCodigoTrabajo (String codigoTrabajo, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Materiales " + "SET codigoTrabajo = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setString(1, codigoTrabajo);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}
	public void actualizarUltimaModificacion (Date ultimaModificacion, String codigo) throws SQLException
	{
		String sentenciaActualizar = "UPDATE Materiales " + "SET ultimaModificacion = ? WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaActualizar);

		preSentencia.setDate(1, ultimaModificacion);
		preSentencia.setString(2, codigo);
		preSentencia.executeUpdate();

		preSentencia.close();
	}

	public String recuperarTipo (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT tipo FROM Materiales " + "WHERE codigo = ?";

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
		String sentenciaRecuperar = "SELECT proposito FROM Materiales " + "WHERE codigo = ?";

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
		String sentenciaRecuperar = "SELECT estaDisponible FROM Materiales " + "WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		boolean estaDisponible = resultado.getBoolean("estaDiponible");

		preSentencia.close();

		return estaDisponible;
	}
	public String recuperarCodigoTrabajo (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT codigoTrabajo FROM Materiales " + "WHERE codigo = ?";

		PreparedStatement preSentencia = conexion.prepareStatement(sentenciaRecuperar);

		preSentencia.setString(1, codigo);		
		ResultSet resultado = preSentencia.executeQuery();
		resultado.next();
		String codigoUsuario = resultado.getString("codigoTrabajo");

		preSentencia.close();

		return codigoUsuario;
	}
	public Date recuperarUltimaModificacion (String codigo) throws SQLException
	{
		String sentenciaRecuperar = "SELECT ultimaModificacion FROM Materiales " + "WHERE codigo = ?";

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
		String sentenciaGet = "SELECT codigo FROM Materiales";

		Statement sentencia = conexion.createStatement();
		
		ResultSet resultado = sentencia.executeQuery(sentenciaGet);
		while (resultado.next())
		{
			codigos.add(resultado.getString("codigo"));
		}

		return codigos;
	}
}
