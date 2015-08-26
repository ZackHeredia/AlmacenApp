package gestor.recursosdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlmacenDb
{
	private Connection conexion;
	private static AlmacenDb almacenDb = new AlmacenDb();
	private TablaEmpleados tablaEmpleados;
	private TablaHerramientas tablaHerramientas;
	private TablaTrabajos tablaTrabajos;
	private TablaMateriales tablaMateriales;

	private AlmacenDb ()
	{
		conexion= null;
		tablaEmpleados= null;
		tablaHerramientas= null;
		tablaTrabajos= null;
		tablaMateriales= null;
	}

	public void establecerConexion () throws SQLException
	{
		String url = "jdbc:derby:AlmacenDb;create=true";

		conexion = DriverManager.getConnection(url);
	}

	public static AlmacenDb getInstancia() throws SQLException
	{
		return almacenDb;
	}
	public TablaEmpleados getTablaEmpleados() throws SQLException
	{
		DatabaseMetaData metaData = conexion.getMetaData();
		ResultSet resultado = metaData.getTables(null, "APP", "EMPLEADOS", null);

		if (tablaEmpleados == null)
		{
			tablaEmpleados = new TablaEmpleados(conexion);
			if (resultado.next())
				return tablaEmpleados;
			else
				tablaEmpleados.crearTabla();
		}
		
		return tablaEmpleados;
	}
	public TablaHerramientas getTablaHerramientas() throws SQLException
	{
		DatabaseMetaData metaData = conexion.getMetaData();
		ResultSet resultado = metaData.getTables(null, "APP", "HERRAMIENTAS", null);
	
		if (tablaHerramientas == null)
		{
			tablaHerramientas = new TablaHerramientas(conexion);
			if (resultado.next())
				return tablaHerramientas;
			else
				tablaHerramientas.crearTabla();
		}
		
		return tablaHerramientas;
	}
	public TablaTrabajos getTablaTrabajos() throws SQLException
	{
		DatabaseMetaData metaData = conexion.getMetaData();
		ResultSet resultado = metaData.getTables(null, "APP", "TRABAJOS", null);

		if (tablaTrabajos == null)
		{
			tablaTrabajos = new TablaTrabajos(conexion);
			if (resultado.next())
				return tablaTrabajos;
			else
				tablaTrabajos.crearTabla();
		}
		
		return tablaTrabajos;
	}
	public TablaMateriales getTablaMateriales() throws SQLException
	{
		DatabaseMetaData metaData = conexion.getMetaData();
		ResultSet resultado = metaData.getTables(null, "APP", "MATERIALES", null);

		if (tablaMateriales == null)
		{
			tablaMateriales = new TablaMateriales(conexion);
			if (resultado.next())
				return tablaMateriales;
			else
				tablaMateriales.crearTabla();
		}
		
		return tablaMateriales;
	}
}
