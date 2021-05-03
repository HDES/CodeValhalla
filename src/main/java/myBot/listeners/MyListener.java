package myBot.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class MyListener extends ListenerAdapter {

	/*VERY IMPORTANTE*/
	//HAY QUE VER COMO LANZAR EXCEPCIONES
	//HAY QUE REVISAR SI SE PUEDE DIVIDIR CÓDIGO EN VARIOS ARCHIVOS
	//NUEVO COMANDO REPETIR COMANDOS DE OTROS BOTS (O MENSAJES EN GENERAL) CADA X TIEMPO EN Y CANAL (CONFIGURABLE)
	
	//UTILIZAR COMANDOS CON OPCIONES POR EJEMPLO !!limpiarTodo -quiet SERIA SILENCIOSO AUNQUE estado.silencioso = false
	
	public static class Estado {
		Estado() {
			borrarAnclados = false;		//True = ON | False = OFF
			numMensajes = 25;			//Por defecto = 25
			silencioso = false;			//True = ON | False = OFF
		}
		//Orden alfabético
		public boolean borrarAnclados;
		public int numMensajes;
		public boolean silencioso;
	};

	public static Estado estado = new Estado();	//Se crea el estado actual del bot por defecto
	public static String prefijo = "!!";	//Prefijo usado antes de los comandos
	public final static int tamPrefijo = MyListener.prefijo.length();			//Longitud de la cadena del prefijo
	public final static String contenidoAyuda = inicializarContenidoAyuda();		//Contiene el mensaje que envia el comando Ayuda

	/*COMANDOS*/ 
	public static Map<String, String> comandos = new HashMap<String, String>();			//Para poder leer los comandos en español o inglés indistintamente
	public static Map<String, Integer> comandosNum = new HashMap<String, Integer>();		//Se asigna un número arbitrario a los comandos para poder ejecutarlos usando una interfaz
	public static Map<String, Integer> comandosPar = new HashMap<String, Integer>();		//Número máximo de parámetros que puede recibir un comando
	/*COMANDOS*/
	
	public static Logger log = LoggerFactory.getLogger(MyListener.class);

	//Post: llama a las funciones para incializar los diccionarios
	@Override public void onReady(ReadyEvent event)	//Cuando el bot esté listo para funcionar lanza ReadyEvent, así que lo usamos para inicializar el map
	{
		inicializarComandos();
		inicializarComandosNum();
		inicializarComandosPar();
	}
/*
	//Post: ejecuta los comandos recibidos
	@Override public void onMessageReceived(MessageReceivedEvent evento)
	{	
		if ( !controlEntrada(evento) ) return;
		
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		Comandoss.opciones[comandosNum.getOrDefault(mensajeDiv[0].substring(tamPrefijo), Comandoss.opciones.length - 1)].IFuncion(evento);	//Para pasar el comando sin prefijo
	}
*/
	//Post: ejecuta los comandos recibidos
	@Override public void onMessageReceived(MessageReceivedEvent evento)
	{	
		
		log.info("Entra en el listener");
		if ( !controlEntrada(evento) ) return;
		log.info("Entra en el listener");
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		
		try {
			log.info("Entra en el listener");
		Class<?> c= Class.forName(mensajeDiv[0].substring(tamPrefijo));
		//c.newInstance();	//assuming you aren't worried about constructor.
		// get the constructor with one parameter
        Constructor<?> constructor = c.getConstructor(new Class[] {String.class});
        log.info("Entra en el listener");
        // create an instance
        Object invoker = constructor.newInstance(new Object[]{comandosNum.get(mensajeDiv[0].substring(tamPrefijo)), 0, estado.silencioso, comandos.get(mensajeDiv[0].substring(tamPrefijo)), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))});
		} catch (ClassNotFoundException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Entra en el listener");
	}

	//Post: Devuelve true si el comando no es de un bot, empieza por el prefijo actual y existe, false en caso contrario
	private boolean controlEntrada(MessageReceivedEvent evento)
	{
		boolean valida = true;	//Bandera para saber si la entrada es válida o no
		
		if ( evento.getAuthor().isBot() ) valida = false;		//No reacciona a mensajes de otros bots (incluido él mismo)
		else
		{
			String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas

			if ( !mensajeDiv[0].substring(0, tamPrefijo).equals(prefijo) ) valida = false;		//El mensaje no empieza por el prefijo actual
			else if ( toBoolean(comandosNum.get(mensajeDiv[0].substring(0, tamPrefijo))) ) valida = false;		//El comando no existe
		}

		return valida;
	}

	//Post: inicializa la cadena que contiene el texto del comando "ayuda"
	private static String inicializarContenidoAyuda() {
		String contenidoAyudaAux = "```"
		+ "Comando								|		Descripción\n"
		+ "-------------------------------------|--------------------------------------\n"
		+ "ayuda								|		Devuelve la lista de los comandos disponibles\n"	
		+ "borrarAnclados [ON|OFF]				|		Si se activa, los comandos limpiar... no borrarán mensajes anclados.\n"
		+ "limpiarBot [nombre] [numero]			|		Borra numero mensajes del bot nombre\n"
		+ "limpiarPrefijo [prefijo] [numero]	|		Borra numero mensajes que empizan por prefijo\n"
		+ "limpiarTodo [numero]					|		Borra numero mensajes sin discriminar\n"
		+ "limpiarUsuario [nombre] [numero]		|		Borra numero mensajes del Usuario dado\n"
		+ "numeroMensajes [numero]				|		Muestra el número de mensajes borrados por defecto. Si se pasa un número, ese será la nueva cantidad por defecto\n"
		+ "opciones								|		Muestra el estado actual de los comandos con variables\n"
		+ "ping									|		Devuelve 'pong'\n"
		+ "prefijo [prefijo]					|		Devuelve el prefijo actual. Si recibe un parámetro, cambia el prefijo al nuevo dado\n"
		+ "silencioso [ON|OFF]					|		Si se activa borra siempre el mensaje del comando utilizado y el que muestre el bot(si ese comando muestra algo)\n"	;
		
		contenidoAyudaAux += "\nPREFIJO ACTUAL: " + prefijo;
		
		contenidoAyudaAux += "```";
		return contenidoAyudaAux;
	}

	//Post: inicializa el diccionario de comandos español-inglés
	private void inicializarComandos() {
		//Orden alfabético
		comandos.put("apagar"			, "shutdown"			);
		comandos.put("ayuda"			, "help"				);
		comandos.put("borrarAnclados"	, "lockPinned"			);
		comandos.put("limpiarBot"		, "cleanBot"			);
		comandos.put("limpiarPrefijo"	, "cleanPrefix"			);
		comandos.put("limpiarTodo"		, "cleanAll"			);
		comandos.put("limpiarUsuario"	, "cleanUser"			);
		comandos.put("numeroMensajes"	, "messageNumber"		);
		comandos.put("opciones"			, "options"				);
		comandos.put("ping"				, "ping"				);
		comandos.put("prefijo"			, "prefix"				);
		comandos.put("silencioso"		, "quiet"				);
	}

	//Post: inicializa el diccionario de comandos palabra-número
	private void inicializarComandosNum() {
		//Orden alfabético
		comandosNum.put("apagar"			, 0);
		comandosNum.put("ayuda"				, 1);
		comandosNum.put("borrarAnclados"	, 2);
		comandosNum.put("limpiarBot"		, 3);
		comandosNum.put("limpiarPrefijo"	, 4);
		comandosNum.put("limpiarTodo"		, 5);
		comandosNum.put("limpiarUsuario"	, 6);
		comandosNum.put("numeroMensajes"	, 7);
		comandosNum.put("opciones"			, 8);
		comandosNum.put("ping"				, 9);
		comandosNum.put("prefijo"			, 10);
		comandosNum.put("silencioso"		, 11);
	}

	//Post: inicializa el diccionario de comandos palabra-número
	private void inicializarComandosPar() {
		//Orden alfabético
		comandosPar.put("apagar"			, 0);
		comandosPar.put("ayuda"				, 0);
		comandosPar.put("borrarAnclados"	, 1);
		comandosPar.put("limpiarBot"		, 2);
		comandosPar.put("limpiarPrefijo"	, 3);
		comandosPar.put("limpiarTodo"		, 1);
		comandosPar.put("limpiarUsuario"	, 2);
		comandosPar.put("numeroMensajes"	, 1);
		comandosPar.put("opciones"			, 0);
		comandosPar.put("ping"				, 0);
		comandosPar.put("prefijo"			, 1);
		comandosPar.put("silencioso"		, 1);
	}

	//Post: recibe un objeto (que no sea int) y devuelve false
	private static Boolean toBoolean(Object i) { return false; }
	
	//Post: recibe un int y devuelve su equivalente Boolean
	@SuppressWarnings("unused")
	private static Boolean toBoolean(int i) { return i != 0 ? true : false; }
	
}




/* PASOS PARA AÑADIR UN COMANDO NUEVO
 * 
 * MANTENER ORDEN ALFABÉTICO EN TODO EL PROCESO
 * 
 * MyListener.java
 * 1.	Modificar inicializarcontenidoAyuda()
 * 2.	Modificar inicializarComandos()
 * 3.	Modificar inicializarComandosNum()
 * 4.	Modificar inicializarComandosPar()
 * 
 * Comandos.java
 * 5.	Modificar static IComandos[] opciones, añadiendo una nueva fila correspondiente al número dado en ComandosNum
 * 6.	Añadir la nueva función del comando, "nombrecomando()"
 * 
 * */





