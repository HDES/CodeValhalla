package my_bot.valhalla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MyListener extends ListenerAdapter {

	/*VERY IMPORTANTE*/
	//HAY QUE VER COMO LANZAR EXCEPCIONES
	//HAY QUE REVISAR SI SE PUEDE DIVIDIR CÓDIGO EN VARIOS ARCHIVOS
	
	final String botClientID = "525273189287198721";	//ID del Cliente del bot en Discord
	
	class Estado {
		//Orden alfabético
		public Boolean borrarAnclados; 		//True = ON | False = OFF
		public Boolean idioma; 				//True = ENG | False = ESP
		public int numMensajes; 			//Por defecto = 25
		public Boolean silencioso; 			//True = ON | False = OFF
	};

	public Estado estado;

	String prefijo = "!!";		// Prefijo usado antes de los comandos
	final Int tamPrefijo = prefijo.length(); // Longitud de la cadena del prefijo

	final String contenidoAyuda = "Comando								|	Descripción
									ping								|		Devuelve 'Pong'
									limpiarPrefijo [prefijo] [numero]	|		Borra numero mensajes que empizan por prefijo
									limpiarBot [nombre] [numero]		|		Borra numero mensajes del bot nombre
									limpiarTodo [numero]				|		Borra numero mensajes sin discriminar
									limpiarUsuario [nombre] [numero]	|		Borra numero mensajes del Usuario dado
									prefijo [prefijo]					|		Devuelve el prefijo actual. Si recibe un parámetro, cambia el prefijo al nuevo dado
									silencioso [ON|OFF]					|		Si se activa borra siempre el mensaje del comando utilizado y el que muestre el bot(si ese comando muestra algo)
									borrarAnclados [ON|OFF]				|		Si se activa, los comandos limpiar... no borrarán mensajes anclados.
									opciones							|		Muestra el estado actual de los comandos con variables
									ayuda								|		Devuelve la lista de los comandos disponibles";		//Contiene el mensaje que envia el comando Ayuda

	/*COMANDOS*/ 	
	Map<String, String> comandos = new HashMap<>();		//Para poder leer los comandos en español o inglés indistintamente
	Map<String, Int> comandosNum = new HashMap<>();		//Se asigna un número arbitrario a los comandos para poder ejecutarlos usando una interfaz
	Map<String, Int> comandosPar = new HashMap<>();		//Número máximo de parámetros que puede recibir un comando
/*
		//Orden alfabético
	final String comando1 = prefijo.concat("ping");				//ping		//un_clasico_meme.jpg
	final String comando2 = prefijo.concat("limpiarPrefijo");	//limpiarPrefijo [prefijo] [numero]		//Borra numero mensajes que empizan por prefijo
	final String comando3 = prefijo.concat("limpiarBot");		//limpiarBot [nombre] [numero]			//Borra numero mensajes del bot nombre
	final String comando4 = prefijo.concat("limpiarTodo");		//limpiarTodo [numero]					//Borra numero mensajes sin discriminar
	final String comando5 = prefijo.concat("limpiarUsuario");	//limpiarUsuario [nombre] [numero]		//Borra numero mensajes del Usuario dado
	final String comando6 = prefijo.concat("prefijo");			//prefijo [prefijo]			//Devuelve el prefijo actual. Si recibe un parámetro, cambia el prefijo al nuevo dado
	final String comando7 = prefijo.concat("silencioso");		//silencioso [ON|OFF]		//Si se activa borra siempre el mensaje del comando utilizado y el que muestre el bot(si ese comando muestra algo)
	final String comando8 = prefijo.concat("borrarAnclados");	//borrarAnclados [ON|OFF]	//Si se activa, los comandos limpiar... no borrarán mensajes anclados.
	final String comando9 = prefijo.concat("opciones");			//opciones	//Muestra el estado actual de los comandos con variables
	final String comando10 = prefijo.concat("ayuda");			//ayuda		//un_clasico_meme.jpg
*/	
	/*COMANDOS*/

	//Post: llama a las funciones para incializar los diccionarios
	@Override public void onReady(/*ReadyEvent event*/)	//Cuando el bot esté listo para funcionar lanza ReadyEvent, así que lo usamos para inicializar el map
	{
		String prefijo = "!!";
		inicializarComandos();
		inicializarComandosNum();
		inicializarEstado();
	}

	//Post: ejecuta los comandos recibidos
	@Override public void onMessageReceived(MessageReceivedEvent evento)
	{	
		if ( !controlEntrada(evento) ) return;
			
		String mensaje = evento.getMessage().getContentRaw();		//Recoge el mensaje que activa el evento y lo transforma a cadena, atomic getter¿?
		
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		opciones[comandosNum.get(mensajeDiv[0].substring(tamPrefijo))].IFuncion(evento);	//Para pasar el comando sin prefijo

	}

/*
	@Override public void onMessageReceived(MessageReceivedEvent evento)
	{	
		if ( !controlEntrada(evento) ) return;
			
		String mensaje = evento.getMessage().getContentRaw();		//Recoge el mensaje que activa el evento y lo transforma a cadena, atomic getter¿?
		
		if ( mensaje.equals(comando1) && numeroParametros(mensaje, 0) )	//ping
		{
			ping();
		} 
		else if (mensaje.equals(comando2) && numeroParametros(mensaje, 2) )	//limpiarPrefijo
		{
			limpiarPrefijo();
		} 
		else if (mensaje.equals(comando3) && numeroParametros(mensaje, 2) )	//limpiarBot
		{
			limpiarBot();
		} 
		else if (mensaje.equals(comando4) && numeroParametros(mensaje, 1) )	//limpiarTodo
		{
			limpiarTodo();
		}
		else if (mensaje.equals(comando5) && numeroParametros(mensaje, 1) )	//limpiarUsuario
		{
			limpiarUsuario();
		}
		else if (mensaje.equals(comando6) && numeroParametros(mensaje, 1) )	//prefijo
		{
			prefijo();
		}
		else if (mensaje.equals(comando7) && numeroParametros(mensaje, 1) )	//silencioso
		{
			silencioso();
		}
		else if (mensaje.equals(comando8) && numeroParametros(mensaje, 1) )	//borrarAnclados
		{
			borrarAnclados();
		}
		else if (mensaje.equals(comando9) && numeroParametros(mensaje, 1) )	//opciones
		{
			opciones();
		}
		else if (mensaje.equals(comando10) && numeroParametros(mensaje, 0) )	//ayuda
		{
			ayuda();
		}
	}
*/	
	//Post: Devuelve true si el comando no es de un bot, empieza por el prefijo actual y existe, false en caso contrario
	private boolean controlEntrada(MessageReceivedEvent evento)
	{
		boolean valida = true;	//Bandera para saber si la entrada es válida o no
<<<<<<< HEAD
		
		if ( evento.getAuthor().isBot() ) valida = false;		//No reacciona a mensajes de otros bots (incluido él mismo)
		else
		{
			String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
=======
		
		if ( evento.getAuthor().isBot() ) valida = false;		//No reacciona a mensajes de otros bots (incluido él mismo)
		else
		{
			String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
>>>>>>> 975a64ca18c6836dfd754ab4a3fcb2030bd62dd0
			if ( !mensajeDiv[0].equals(prefijo) ) valida = false;				//El mensaje no empieza por el prefijo actual
			else if ( comandos.get(mensajeDiv[1]) == null ) valida = false;		//El comando no existe
		}

		return valida;
	}
	
	//Post: inicializa el diccionario de comandos español-inglés
	private void inicializarComandos() {
		//Orden alfabético
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
<<<<<<< HEAD

	//Post: inicializa el diccionario de comandos palabra-número
	private void inicializarComandosNum() {
		//Orden alfabético
		comandosNum.put("ayuda"				, 0);
		comandosNum.put("borrarAnclados"	, 1);
		comandosNum.put("limpiarBot"		, 2);
		comandosNum.put("limpiarPrefijo"	, 3);
		comandosNum.put("limpiarTodo"		, 4);
		comandosNum.put("limpiarUsuario"	, 5);
		comandosNum.put("numeroMensajes"	, 6);
		comandosNum.put("opciones"			, 7);
		comandosNum.put("ping"				, 8);
		comandosNum.put("prefijo"			, 9);
		comandosNum.put("silencioso"		, 10);
	}

	//Post: inicializa el diccionario de comandos palabra-número
	private void inicializarComandosPar() {
		//Orden alfabético
		comandosPar.put("ayuda"				, 0);
		comandosPar.put("borrarAnclados"	, 1);
		comandosPar.put("limpiarBot"		, 2);
		comandosPar.put("limpiarPrefijo"	, 2);
		comandosPar.put("limpiarTodo"		, 1);
		comandosPar.put("limpiarUsuario"	, 2);
		comandosPar.put("numeroMensajes"	, 1);
		comandosPar.put("opciones"			, 0);
		comandosPar.put("ping"				, 0);
		comandosPar.put("prefijo"			, 1);
		comandosPar.put("silencioso"		, 1);
	}

	//Post: inicializa la clase Estado con las opciones predeterminadas del bot
	private void inicializarEstado() {
		//Orden alfabético
		estado.borrarAnclados = false;		//True = ON | False = OFF
		estado.idioma = false;				//True = ENG | False = ESP
		estado.numMensajes = 25;			//Por defecto = 25
		estado.silencioso = false;			//True = ON | False = OFF
	}

}
=======

	//Post: inicializa el diccionario de comandos palabra-número
	private void inicializarComandosNum() {
		//Orden alfabético
		comandosNum.put("ayuda"				, 0);
		comandosNum.put("borrarAnclados"	, 1);
		comandosNum.put("limpiarBot"		, 2);
		comandosNum.put("limpiarPrefijo"	, 3);
		comandosNum.put("limpiarTodo"		, 4);
		comandosNum.put("limpiarUsuario"	, 5);
		comandosNum.put("numeroMensajes"	, 6);
		comandosNum.put("opciones"			, 7);
		comandosNum.put("ping"				, 8);
		comandosNum.put("prefijo"			, 9);
		comandosNum.put("silencioso"		, 10);
	}

	//Post: inicializa el diccionario de comandos palabra-número
	private void inicializarComandosPar() {
		//Orden alfabético
		comandosPar.put("ayuda"				, 0);
		comandosPar.put("borrarAnclados"	, 1);
		comandosPar.put("limpiarBot"		, 2);
		comandosPar.put("limpiarPrefijo"	, 2);
		comandosPar.put("limpiarTodo"		, 1);
		comandosPar.put("limpiarUsuario"	, 2);
		comandosPar.put("numeroMensajes"	, 1);
		comandosPar.put("opciones"			, 0);
		comandosPar.put("ping"				, 0);
		comandosPar.put("prefijo"			, 1);
		comandosPar.put("silencioso"		, 1);
	}

	//Post: inicializa la clase Estado con las opciones predeterminadas del bot
	private void inicializarEstado() {
		//Orden alfabético
		estado.borrarAnclados = false;		//True = ON | False = OFF
		estado.idioma = false;				//True = ENG | False = ESP
		estado.numMensajes = 25;			//Por defecto = 25
		estado.silencioso = false;			//True = ON | False = OFF
	}

}
>>>>>>> 975a64ca18c6836dfd754ab4a3fcb2030bd62dd0
