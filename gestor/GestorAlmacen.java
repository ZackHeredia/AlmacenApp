package gestor;

import java.sql.SQLException;

public class GestorAlmacen
{
	public static void main (String[] args)
	{
		try
		{
			Almacenista.mostrarMenuPrincipal();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
