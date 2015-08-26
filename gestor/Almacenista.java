package gestor;

import gestor.recursosdb.*;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;
import java.util.LinkedList;
import java.text.DecimalFormat;

public abstract class Almacenista
{
	private static AlmacenDb almacenDb;

	public static void mostrarMenuPrincipal () throws SQLException
	{
		almacenDb = AlmacenDb.getInstancia();
		almacenDb.establecerConexion();
		Scanner lector = new Scanner(System.in);
		char opcion;
		boolean continuar = true;
 		
		do
		{
			mostrarCabecera("Menu Principal");

			System.out.println("\nQue desea hacer??");
			System.out.println("\n\t0 - Salir del Programa" + 
					   "\n\t1 - Menu de Empleados" +
					   "\n\t2 - Menu de Herramientas" + 
					   "\n\t3 - Menu de Trabajos" + 
					   "\n\t4 - Menu de Materiales");
			System.out.print("\nOpcion: ");
			opcion = lector.next().charAt(0);
			lector.nextLine();
			
			switch (opcion)
			{
				case '0':
					continuar = false;
					break;
				case '1':
					mostrarMenuEmpleados();
					break;
				case '2':
					mostrarMenuHerramientas();
					break;
				case '3':
					mostrarMenuTrabajos();
					break;
				case '4':
					mostrarMenuMateriales();
					break;
				default:
					System.out.println("\nOpcion invalida!!");
					System.out.println("Presiones ENTER para continuar...");
					lector.nextLine();

					break;
			}
		} while (continuar);
	}

	private static void mostrarMenuMateriales () throws SQLException
	{
		TablaMateriales tablaMateriales = almacenDb.getTablaMateriales();
		TablaTrabajos tablaTrabajos = almacenDb.getTablaTrabajos();
		Scanner lector = new Scanner(System.in);
		char opcion;
		boolean continuar = true;
 		
		do
		{
			mostrarCabecera("Menu Principal >> Menu de Materiales");

			System.out.println("\nQue desea hacer??");
			System.out.println("\n\t0 - Salir del menu" + 
					   "\n\t1 - Agregar Materiales" +
					   "\n\t2 - Eliminar Materiales" + 
					   "\n\t3 - Ver Materiales" + 
					   "\n\t4 - Consultar Materiales" +
					   "\n\t5 - Proveer Materiales" +
					   "\n\t6 - Guardar Materiales");
			System.out.print("\nOpcion: ");
			opcion = lector.next().charAt(0);
			lector.nextLine();
			
			
			switch (opcion)
			{
				case '0':
					continuar = false;
					break;
				case '1':
					agregarMateriales(tablaMateriales);
					break;
				case '2':
					eliminarMateriales(tablaMateriales);
					break;
				case '3':
					verMateriales(tablaMateriales);
					break;
				case '4':
					consultarMateriales(tablaMateriales);
					break;
				case '5':
					proveerMateriales(tablaMateriales, tablaTrabajos);
					break;
				case '6':
					guardarMateriales(tablaMateriales);
					break;
				default:
					System.out.println("\nOpcion invalida!!");
					System.out.println("Presiones ENTER para continuar...");
					lector.nextLine();

					break;
			}
		} while (continuar);
	}
	private static void guardarMateriales (TablaMateriales tablaMateriales)  throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigoTrabajo = null;
		String codigo;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Materiales >> Proveer Materiales");

			System.out.print("\nEntre el codigo del material que desea guardar (9 carateres), " + 
					 "si desea ver los materiales registrados escriba \"ver materiales\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver materiales"))
			{
				tablaMateriales.verTabla();
				System.out.print("\nEntre el codigo del material que desea guardar: ");
				codigo = lector.nextLine();
			}

			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaMateriales.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe un material con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (tablaMateriales.recuperarCodigoTrabajo(codigo) != null && tablaMateriales.recuperarEstaDisponible(codigo) == true)
			{
				System.out.println("\nEl material ya esta guardado!!");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else 
			{
				System.out.println("Seguro que desea guardar el siguiente material? S/N: ");
				tablaMateriales.consultarTabla(1, codigo);
			
				if (resp == 's' || resp == 'S');
				{
					tablaMateriales.actualizarEstaDisponible(true, codigo);
					tablaMateriales.actualizarCodigoTrabajo(codigoTrabajo, codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea proveer otro material?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void proveerMateriales (TablaMateriales tablaMateriales, TablaTrabajos tablaTrabajos) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, codigoTrabajo;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Materiales >> Proveer Materiales");

			System.out.print("\nEntre el codigo del material que desea proveer (9 caracteres), " + 
					 "si desea ver los materiales registrados escriba \"ver materiales\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver materiales"))
			{
				tablaMateriales.verTabla();
				System.out.print("\nEntre el codigo del material que desea proveer: ");
				codigo = lector.nextLine();
			}

			System.out.print("\nEntre el codigo del trabajo en el que se usara el material (9 caracteres), " + 
					 "si desea ver los trabajos registrados escriba \"ver trabajos\", sin comillas: ");
			codigoTrabajo = lector.nextLine();

			if (codigoTrabajo.equals("ver trabajos"))
			{
				tablaTrabajos.verTabla();
				System.out.print("\nEntre el codigo del trabajo en el que se usara el material: ");
				codigoTrabajo = lector.nextLine();
			}

			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaMateriales.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe un material con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (codigoTrabajo.isEmpty() || codigoTrabajo.length() < 9 || codigoTrabajo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaTrabajos.getCodigos().contains(codigoTrabajo))
			{
				System.out.println("\nEntrada invalida!!! No existe un trabajo con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				System.out.println("Seguro que desea proveer el siguiente material");
				tablaMateriales.consultarTabla(1, codigo);
				System.out.println("Al siguiente trabajo? S/N");
				tablaTrabajos.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);

				if (resp == 's' || resp == 'S');
				{
					tablaMateriales.actualizarEstaDisponible(false, codigo);
					tablaMateriales.actualizarCodigoTrabajo(codigoTrabajo, codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea proveer otro material?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void consultarMateriales (TablaMateriales tablaMateriales) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, valor;
		char resp = 'n', cOpcion;
		int opcion;

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Materiales >> Consultar Materiales");

			System.out.println("\nSelecione el campo que se usara de criterio:");
			System.out.println("\n\t1.Codigo\t2.Tipo\t3.proposito\t4.Disponible" +
					   "\t5.Trabajo\t6.Modificada");
			System.out.print("\nOpcion: ");
			cOpcion = lector.next().charAt(0);
			lector.nextLine();
			System.out.print("\nEntre el valor a evaluar: ");
			valor = lector.nextLine();
			
			if (cOpcion != '1' && cOpcion != '2' && cOpcion != '3' && cOpcion != '4' && cOpcion != '5' && cOpcion != '6')
			{
				System.out.println("\nEntrada invalida!!!");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				opcion = Integer.parseInt("" + cOpcion);
				tablaMateriales.consultarTabla(opcion, valor);
				
				System.out.print("\nDesea realizar otra consulta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void verMateriales (TablaMateriales tablaMateriales) throws SQLException
	{
		Scanner lector = new Scanner(System.in);

		mostrarCabecera("Menu Principal >> Menu de Materiales >> Ver Materiales");
		
		System.out.println();
		tablaMateriales.verTabla();
				
		System.out.print("\nPresione ENTER para continuar...");
		lector.nextLine();
			
	}
	private static void eliminarMateriales (TablaMateriales tablaMateriales) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Materiales >> Eliminar Materiales");

			System.out.print("\nEntre el codigo del material que desea eliminar (9 caracteres), " + 
					 "si desea ver los materiales registrados escriba \"ver materiales\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver materiales"))
			{
				tablaMateriales.verTabla();
				System.out.print("\nEntre el codigo de la material que desea eliminar: ");
				codigo = lector.nextLine();
			}

			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaMateriales.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe una material con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				System.out.println("Seguro que desea eliminar el siguiente material? S/N");
				tablaMateriales.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);

				if (resp == 's' || resp == 'S');
				{
					tablaMateriales.eliminarMaterial(codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea eliminar otro material?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void agregarMateriales (TablaMateriales tablaMateriales) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, tipo, proposito;
		Date ultimaModificacion;
		boolean estaDisponible;
		char resp = 'n';
	
		do
		{	
			mostrarCabecera("Menu Principal >> Menu de Materiales >> Agregar Materiales");

			System.out.print("\nEntre el tipo del material (max. 15 caracteres): ");
			tipo = lector.nextLine();
			System.out.print("\nEntre el proposito del material (max. 30 caracteres): ");
			proposito = lector.nextLine();
			
			if (tipo.isEmpty() || tipo.length() > 15)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El tipo no debe exceder los 15 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (proposito.isEmpty() || proposito.length() > 30)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El proposito no debe exceder los 30 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				codigo = generarCodigoMaterial(tipo, tablaMateriales.getCodigos());
				ultimaModificacion = obtenerFechaActual();
				tablaMateriales.poblarTabla(codigo, tipo, proposito, ultimaModificacion);
				System.out.println("\nCodigo asignado: " + codigo);

				System.out.print("\nDesea entrar otro material?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			} 
		} while (resp == 's' || resp == 'S');
	}
	private static String generarCodigoMaterial (String tipo, LinkedList<String> codigosAnteriores)
	{
		tipo = tipo.toUpperCase();
		DecimalFormat parteEntera = new DecimalFormat("0000");
		StringBuffer codigo = new StringBuffer("M-");
		int entero = 0;

		if (tipo.contains(" "))
		{
			codigo.append(tipo.charAt(0));

			for (int i = 0; i < tipo.length(); i++)
			{
				if (tipo.charAt(i) == ' ')
				{
					codigo.append(tipo.charAt(i+1));
					codigo.append("-");
					i = tipo.length();
				}
			}
		}
		else
		{
			codigo.append(tipo.substring(0, 1));
			codigo.append("-");
		}
	
		for (int i = 0; i < codigosAnteriores.size(); i++)
		{
			if (codigosAnteriores.get(i).contains(codigo.toString()))
			{
				++entero;
			}
		}
	
		codigo.append(parteEntera.format(entero));

		return codigo.toString();
	}

	private static void mostrarMenuTrabajos () throws SQLException
	{
		TablaTrabajos tablaTrabajos = almacenDb.getTablaTrabajos();
		Scanner lector = new Scanner(System.in);
		char opcion;
		boolean continuar = true;
 		
		do
		{
			mostrarCabecera("Menu Principal >> Menu de Trabajos");

			System.out.println("\nQue desea hacer??");
			System.out.println("\n\t0 - Salir del menu" + 
					   "\n\t1 - Agregar Trabajos" +
					   "\n\t2 - Eliminar Trabajos" + 
					   "\n\t3 - Ver Trabajos" + 
					   "\n\t4 - Consultar Trabajos");
			System.out.print("\nOpcion: ");
			opcion = lector.next().charAt(0);
			lector.nextLine();
			
			switch (opcion)
			{
				case '0':
					continuar = false;
					break;
				case '1':
					agregarTrabajos(tablaTrabajos);
					break;
				case '2':
					eliminarTrabajos(tablaTrabajos);
					break;
				case '3':
					verTrabajos(tablaTrabajos);
					break;
				case '4':
					consultarTrabajos(tablaTrabajos);
					break;
				default:
					System.out.println("\nOpcion invalida!!");
					System.out.println("Presiones ENTER para continuar...");
					lector.nextLine();

					break;
			}
		} while (continuar);
	}
	private static void consultarTrabajos (TablaTrabajos tablaTrabajos) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, valor;
		char resp = 'n', cOpcion;
		int opcion;

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Trabajos >> Consultar Trabajos");

			System.out.println("\nSelecione el campo que se usara de criterio:");
			System.out.println("\n\t1.Codigo\t2.Descripcion\t3.Cliente");
			System.out.print("\nOpcion: ");
			cOpcion = lector.next().charAt(0);
			lector.nextLine();
			System.out.print("\nEntre el valor a evaluar: ");
			valor = lector.nextLine();
			
			if (cOpcion != '1' && cOpcion != '2' && cOpcion != '3')
			{
				System.out.println("\nEntrada invalida!!!");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				opcion = Integer.parseInt("" + cOpcion);
				tablaTrabajos.consultarTabla(opcion, valor);
				
				System.out.print("\nDesea realizar otra consulta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void verTrabajos (TablaTrabajos tablaTrabajos) throws SQLException
	{
		Scanner lector = new Scanner(System.in);

		mostrarCabecera("Menu Principal >> Menu de Trabajos >> Ver Trabajos");
		
		System.out.println();
		tablaTrabajos.verTabla();
				
		System.out.print("\nPresione ENTER para continuar...");
		lector.nextLine();	
	}
	private static void eliminarTrabajos (TablaTrabajos tablaTrabajos) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Trabajos >> Eliminar Trabajos");

			System.out.print("\nEntre el codigo del trabajo que desea eliminar (9 caracteres), " + 
					 "si desea ver los trabajos registrados escriba \"ver trabajos\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver trabajos"))
			{
				tablaTrabajos.verTabla();
				System.out.print("\nEntre el codigo del trabajo que desea eliminar: ");
				codigo = lector.nextLine();
			}

			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaTrabajos.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe un trabajo con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				System.out.println("Seguro que desea eliminar el siguiente trabajo? S/N");
				tablaTrabajos.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);
				lector.nextLine();

				if (resp == 's' || resp == 'S');
				{
					tablaTrabajos.eliminarTrabajo(codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea eliminar otro trabajo?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void agregarTrabajos (TablaTrabajos tablaTrabajos) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, descripcion, nombreCliente;
		char resp = 'n';
	
		do
		{	
			mostrarCabecera("Menu Principal >> Menu de Trabajos >> Agregar Trabajos");

			System.out.print("\nEntre el descripcion del trabajo (max. 40 caracteres): ");
			descripcion = lector.nextLine();
			System.out.print("\nEntre el nombre del cliente del trabajo (max. 20 caracteres): ");
			nombreCliente = lector.nextLine();

			if (descripcion.isEmpty() || descripcion.length() > 40)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El descripcion no debe exceder los 40 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (nombreCliente.isEmpty() || nombreCliente.length() > 20)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El nombre del cliente no debe exceder los 20 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				codigo = generarCodigoTrabajo(descripcion, tablaTrabajos.getCodigos());
				tablaTrabajos.poblarTabla(codigo, descripcion, nombreCliente);
				System.out.println("\nCodigo asignado: " + codigo);

				System.out.print("\nDesea entrar otro trabajo?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			} 
		} while (resp == 's' || resp == 'S');
	}
	private static String generarCodigoTrabajo (String descripcion, LinkedList<String> codigosAnteriores)
	{
		descripcion = descripcion.toUpperCase();
		DecimalFormat parteEntera = new DecimalFormat("0000");
		StringBuffer codigo = new StringBuffer("T-");
		int entero = 0;

		if (descripcion.contains(" "))
		{
			codigo.append(descripcion.charAt(0));

			for (int i = 0; i < descripcion.length(); i++)
			{
				if (descripcion.charAt(i) == ' ')
				{
					codigo.append(descripcion.charAt(i+1));
					codigo.append("-");
					i = descripcion.length();
				}
			}
		}
		else
		{
			codigo.append(descripcion.substring(0,1));
			codigo.append("-");
		}
	
		for (int i = 0; i < codigosAnteriores.size(); i++)
		{
			if (codigosAnteriores.get(i).contains(codigo.toString()))
			{
				++entero;
			}
		}
	
		codigo.append(parteEntera.format(entero));

		return codigo.toString();
	}

	private static void mostrarMenuHerramientas () throws SQLException
	{
		TablaHerramientas tablaHerramientas = almacenDb.getTablaHerramientas();
		TablaEmpleados tablaEmpleados = almacenDb.getTablaEmpleados();
		Scanner lector = new Scanner(System.in);
		char opcion;
		boolean continuar = true;
 		
		do
		{
			mostrarCabecera("Menu Principal >> Menu de Herramientas");

			System.out.println("\nQue desea hacer??");
			System.out.println("\n\t0 - Salir del menu" + 
					   "\n\t1 - Agregar Herramientas" +
					   "\n\t2 - Eliminar Herramientas" + 
					   "\n\t3 - Ver Herramientas" + 
					   "\n\t4 - Consultar Herramientas" +
					   "\n\t5 - Proveer Herramientas" +
					   "\n\t6 - Guardar Herramientas");
			System.out.print("\nOpcion: ");
			opcion = lector.next().charAt(0);
			lector.nextLine();
			
			switch (opcion)
			{
				case '0':
					continuar = false;
					break;
				case '1':
					agregarHerramientas(tablaHerramientas);
					break;
				case '2':
					eliminarHerramientas(tablaHerramientas);
					break;
				case '3':
					verHerramientas(tablaHerramientas);
					break;
				case '4':
					consultarHerramientas(tablaHerramientas);
					break;
				case '5':
					proveerHerramientas(tablaHerramientas, tablaEmpleados);
					break;
				case '6':
					guardarHerramientas(tablaHerramientas);
					break;
				default:
					System.out.println("\nOpcion invalida!!");
					System.out.println("Presiones ENTER para continuar...");
					lector.nextLine();

					break;
			}
		} while (continuar);
	}
	private static void guardarHerramientas (TablaHerramientas tablaHerramientas) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigoTrabajo = null;
		String codigo, codigoUsuario = null;
		char resp = 'n';
		

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Herramientas >> Proveer Herramientas");

			System.out.print("\nEntre el codigo de la herramienta que desea guardar (9 caracteres), " + 
					 "si desea ver las herramientas registradas escriba \"ver herramientas\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver herramientas"))
			{
				tablaHerramientas.verTabla();
				System.out.print("\nEntre el codigo de la herramienta que desea guardar: ");
				codigo = lector.nextLine();
			}
			
			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaHerramientas.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe una herramienta con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (tablaHerramientas.recuperarCodigoUsuario(codigo) != null && tablaHerramientas.recuperarEstaDisponible(codigo) == true)
			{
				System.out.println("\nLa herramienta ya esta guardada!!");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else 
			{
				System.out.println("Seguro que desea guardar la siguiente herramienta? S/N: ");
				tablaHerramientas.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);
			
				if (resp == 's' || resp == 'S');
				{
					tablaHerramientas.actualizarEstaDisponible(true, codigo);
					tablaHerramientas.actualizarCodigoUsuario(codigoUsuario, codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea proveer otra herramienta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void proveerHerramientas (TablaHerramientas tablaHerramientas, TablaEmpleados tablaEmpleados) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, codigoUsuario;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Herramientas >> Proveer Herramientas");

			System.out.print("\nEntre el codigo de la herramienta que desea proveer (9 caracteres), " + 
					 "si desea ver las herramientas registradas escriba \"ver herramientas\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver herramientas"))
			{
				tablaHerramientas.verTabla();
				System.out.print("\nEntre el codigo de la herramienta que desea proveer: ");
				codigo = lector.nextLine();
			}

			System.out.print("\nEntre el codigo del empleado al que le proveera la herramienta (9 caracteres), " + 
					 "si desea ver los empleados registrados escriba \"ver empleados\", sin comillas: ");
			codigoUsuario = lector.nextLine();

			if (codigoUsuario.equals("ver empleados"))
			{
				tablaEmpleados.verTabla();
				System.out.print("\nEntre el codigo del empleado al que le proveera la herramienta: ");
				codigoUsuario = lector.nextLine();
			}

			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaHerramientas.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe una herramienta con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (codigoUsuario.isEmpty() || codigoUsuario.length() < 9 || codigoUsuario.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaEmpleados.getCodigos().contains(codigoUsuario))
			{
				System.out.println("\nEntrada invalida!!! No existe un empleado con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{	
				System.out.println("Seguro que desea proveer la siguiente herramienta");
				tablaHerramientas.consultarTabla(1, codigo);
				System.out.println("Al siguiente empleado? S/N");
				tablaEmpleados.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);

				if (resp == 's' || resp == 'S');
				{
					tablaHerramientas.actualizarEstaDisponible(false, codigo);
					tablaHerramientas.actualizarCodigoUsuario(codigoUsuario, codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea proveer otra herramienta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void consultarHerramientas (TablaHerramientas tablaHerramientas) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, valor;
		char resp = 'n', cOpcion;
		int opcion;

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Herramientas >> Consultar Herramientas");

			System.out.println("\nSelecione el campo que se usara de criterio:");
			System.out.println("\n\t1.Codigo\t2.Tipo\t3.proposito\t4.Disponible" +
					   "\t5.Usuario\t6.Modificada");
			System.out.print("\nOpcion: ");
			cOpcion = lector.next().charAt(0);
			lector.nextLine();
			System.out.print("\nEntre el valor a evaluar: ");
			valor = lector.nextLine();
			
			if (cOpcion != '1' && cOpcion != '2' && cOpcion != '3' && cOpcion != '4' && cOpcion != '5' && cOpcion != '6')
			{
				System.out.println("\nEntrada invalida!!!");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				opcion = Integer.parseInt("" + cOpcion);
				tablaHerramientas.consultarTabla(opcion, valor);
				
				System.out.print("\nDesea realizar otra consulta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void verHerramientas (TablaHerramientas tablaHerramientas) throws SQLException
	{
		Scanner lector = new Scanner(System.in);

		mostrarCabecera("Menu Principal >> Menu de Herramientas >> Ver Herramientas");
		
		System.out.println();
		tablaHerramientas.verTabla();
				
		System.out.print("\nPresione ENTER para continuar...");
		lector.nextLine();
			
	}
	private static void eliminarHerramientas (TablaHerramientas tablaHerramientas) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Herramientas >> Eliminar Herramientas");

			System.out.print("\nEntre el codigo de la herramienta que desea eliminar (9 caracteres), " + 
					 "si desea ver las herramientas registrados escriba \"ver herramientas\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver herramientas"))
			{
				tablaHerramientas.verTabla();
				System.out.print("\nEntre el codigo de la herramienta que desea eliminar: ");
				codigo = lector.nextLine();
			}
			
			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaHerramientas.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe una herramienta con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				System.out.println("Seguro que desea eliminar la siguiente herramienta? S/N");
				tablaHerramientas.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);

				if (resp == 's' || resp == 'S');
				{
					tablaHerramientas.eliminarHerramienta(codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea eliminar otra herramienta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void agregarHerramientas (TablaHerramientas tablaHerramientas) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, tipo, proposito;
		Date ultimaModificacion;
		boolean estaDisponible;
		char resp = 'n';
	
		do
		{	
			mostrarCabecera("Menu Principal >> Menu de Herramientas >> Agregar Herramientas");

			System.out.print("\nEntre el tipo de la herramienta (max. 15 caracteres): ");
			tipo = lector.nextLine();
			System.out.print("\nEntre el proposito de la herramienta (max. 30 caracteres): ");
			proposito = lector.nextLine();
			
			if (tipo.isEmpty() || tipo.length() > 15)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El tipo no debe exceder los 15 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (proposito.isEmpty() || proposito.length() > 30)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El proposito no debe exceder los 30 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				codigo = generarCodigoHerramienta(tipo, tablaHerramientas.getCodigos());
				ultimaModificacion = obtenerFechaActual();
				tablaHerramientas.poblarTabla(codigo, tipo, proposito, ultimaModificacion);
				System.out.println("\nCodigo asignado: " + codigo);

				System.out.print("\nDesea entrar otra herramienta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			} 
		} while (resp == 's' || resp == 'S');
	}
	private static String generarCodigoHerramienta (String tipo, LinkedList<String> codigosAnteriores)
	{
		tipo = tipo.toUpperCase();
		DecimalFormat parteEntera = new DecimalFormat("0000");
		StringBuffer codigo = new StringBuffer( "H-");
		int entero = 0;

		if (tipo.contains(" "))
		{
			codigo.append(tipo.charAt(0));

			for (int i = 0; i < tipo.length(); i++)
			{
				if (tipo.charAt(i) == ' ')
				{
					codigo.append(tipo.charAt(i+1));
					codigo.append("-");
					i = tipo.length();
				}
			}
		}
		else
		{
			codigo.append(tipo.substring(0, 1));
			codigo.append("-");
		}
		
	
		for (int i = 0; i < codigosAnteriores.size(); i++)
		{
			if (codigosAnteriores.get(i).contains(codigo.toString()))
			{
				++entero;
			}
		}
	
		codigo.append(parteEntera.format(entero));

		return codigo.toString();
	}

	private static void mostrarMenuEmpleados () throws SQLException
	{
		TablaEmpleados tablaEmpleados = almacenDb.getTablaEmpleados();
		Scanner lector = new Scanner(System.in);
		char opcion;
		boolean continuar = true;
 		
		do
		{
			mostrarCabecera("Menu Principal >> Menu de Empleados");

			System.out.println("\nQue desea hacer??");
			System.out.println("\n\t0 - Salir del menu" + 
					   "\n\t1 - Agregar Empleados" +
					   "\n\t2 - Eliminar Empleados" + 
					   "\n\t3 - Ver Empleados" + 
					   "\n\t4 - Consultar Empleados");
			System.out.print("\nOpcion: ");
			opcion = lector.next().charAt(0);
			lector.nextLine();
			
			switch (opcion)
			{
				case '0':
					continuar = false;
					break;
				case '1':
					agregarEmpleados(tablaEmpleados);
					break;
				case '2':
					eliminarEmpleados(tablaEmpleados);
					break;
				case '3':
					verEmpleados(tablaEmpleados);
					break;
				case '4':
					consultarEmpleados(tablaEmpleados);
					break;
				default:
					System.out.println("\nOpcion invalida!!");
					System.out.println("Presiones ENTER para continuar...");
					lector.nextLine();

					break;
			}
		} while (continuar);
	}
	private static void consultarEmpleados (TablaEmpleados tablaEmpleados) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, valor;
		char resp = 'n', cOpcion;
		int opcion;

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Empleados >> Consultar Empleados");

			System.out.println("\nSelecione el campo que se usara de criterio:");
			System.out.println("\n\t1.Codigo\t2.Nombre\t3.Apellido\t4.Puesto");
			System.out.print("\nOpcion: ");
			cOpcion = lector.next().charAt(0);
			lector.nextLine();
			System.out.print("\nEntre el valor a evaluar: ");
			valor = lector.nextLine();
			
			if (cOpcion != '1' && cOpcion != '2' && cOpcion != '3' && cOpcion != '4')
			{
				System.out.println("\nEntrada invalida!!!");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				opcion = Integer.parseInt("" + cOpcion);
				tablaEmpleados.consultarTabla(opcion, valor);
				
				System.out.print("\nDesea realizar otra consulta?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void verEmpleados (TablaEmpleados tablaEmpleados) throws SQLException
	{
		Scanner lector = new Scanner(System.in);

		mostrarCabecera("Menu Principal >> Menu de Empleados >> Ver Empleados");
		
		System.out.println();
		tablaEmpleados.verTabla();
				
		System.out.print("\nPresione ENTER para continuar...");
		lector.nextLine();
			
	}
	private static void eliminarEmpleados (TablaEmpleados tablaEmpleados) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo;
		char resp = 'n';

		do
		{
			mostrarCabecera("Menu Principal >> Menu de Empleados >> Eliminar Empleados");

			System.out.print("\nEntre el codigo del empleado que desea eliminar (9 caracteres), " + 
					 "si desea ver los empleados registrados escriba \"ver empleados\", sin comillas: ");
			codigo = lector.nextLine();

			if (codigo.equals("ver empleados"))
			{
				tablaEmpleados.verTabla();
				System.out.print("\nEntre el codigo del empleado que desea eliminar: ");
				codigo = lector.nextLine();
			}

			if (codigo.isEmpty() || codigo.length() < 9 || codigo.length() > 9)
			{
				System.out.println("\nEntrada invalida!!! Formato de codigo incorrecto");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (!tablaEmpleados.getCodigos().contains(codigo))
			{
				System.out.println("\nEntrada invalida!!! No existe un empleado con ese codigo");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				System.out.println("Seguro que desea eliminar el siguiente empleado? S/N");
				tablaEmpleados.consultarTabla(1, codigo);
				resp = lector.next().charAt(0);

				if (resp == 's' || resp == 'S');
				{
					tablaEmpleados.eliminarEmpleado(codigo);
					resp = 'n';
				}
				
				System.out.print("\nDesea eliminar otro empleado?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			}
		} while (resp == 's' || resp == 'S');
	}
	private static void agregarEmpleados (TablaEmpleados tablaEmpleados) throws SQLException
	{
		Scanner lector = new Scanner(System.in);
		String codigo, nombre, apellido, puesto;
		char resp = 'n';
	
		do
		{	
			mostrarCabecera("Menu Principal >> Menu de Empleados >> Agregar Empleados");

			System.out.print("\nEntre el nombre del empleado (max. 10 caracteres): ");
			nombre = lector.nextLine();
			System.out.print("\nEntre el apellido del empleado (max. 10 caracteres): ");
			apellido = lector.nextLine();
			System.out.print("\nEntre el puesto del empleado (max. 10 caracteres): ");
			puesto = lector.nextLine();

			if (nombre.isEmpty() || nombre.length() > 10)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El nombre no debe exceder los 10 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (apellido.isEmpty() || apellido.length() > 10)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El apellido no debe exceder los 10 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else if (puesto.isEmpty() || puesto.length() > 10)
			{
				System.out.println("\nEntrada invalida!!! " + 
						   "El puesto no debe exceder los 10 caracteres y tampoco puede dejarse en blanco");
				resp = 's';
				System.out.println("Presione ENTER para continuar...");
				lector.nextLine();
			}
			else
			{
				codigo = generarCodigoEmpleado(nombre, apellido, tablaEmpleados.getCodigos());
				tablaEmpleados.poblarTabla(codigo, nombre, apellido, puesto);
				System.out.println("\nCodigo asignado: " + codigo);

				System.out.print("\nDesea entrar otro empleado?? S/N: ");
				resp = lector.next().charAt(0);
				lector.nextLine();
			} 
		} while (resp == 's' || resp == 'S');
	}
	private static String generarCodigoEmpleado (String nombre, String apellido, LinkedList<String> codigosAnteriores)
	{
		nombre = nombre.toUpperCase();
		apellido = apellido.toUpperCase();
		DecimalFormat parteEntera = new DecimalFormat("0000");
		StringBuffer codigo = new StringBuffer("E-");
		codigo.append(nombre.charAt(0));
		codigo.append(apellido.charAt(0));
		codigo.append("-");
		int entero = 0;
	
		for (int i = 0; i < codigosAnteriores.size(); i++)
		{
			if (codigosAnteriores.get(i).contains(codigo.toString()))
			{
				++entero;
			}
		}
	
		codigo.append(parteEntera.format(entero));

		return codigo.toString();
	}
	
	private static void mostrarCabecera (String ubicacion)
	{
		limpiarPantalla();

		System.out.println();
		System.out.println("\t\t\t\t---------------------------");
		System.out.println("\t\t\t\t|    Gestor de Almacen    |");
		System.out.println("\t\t\t\t---------------------------");
		System.out.println();

		System.out.println("\t\t\t\t\t\t\t\t     " + obtenerFechaActual());
		System.out.println(ubicacion);
	}
	private static Date obtenerFechaActual()
	{
		java.util.Date utilFecha = new java.util.Date();
		Date fechaActual = new java.sql.Date (utilFecha.getTime());

		return fechaActual;
	}	
	private static void limpiarPantalla ()
	{
		System.out.print("\033[2J");
		System.out.print("\033[50A");
	}
}
