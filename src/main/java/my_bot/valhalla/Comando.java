package my_bot.valhalla;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Comando {

	//Atributos comunes a todos los comandos
	public final Integer id_;			//Identificador arbitrario
	public final Boolean isQuiet_;		//TRUE si muestra algún mensaje por pantalla
	public final Integer level_;		//Nivel de acceso (
	public Set<String> names_ = new HashSet<String>();	//Posibles nombres para invocar al comando
	public final Integer parameters_;	//Número de parámetros máximo que admite

	//Constructor
	public Comando(Integer id, Integer level, Boolean quiet, /*Set<String> names,*/ String name, Integer param) 
	{
		id_ = id;
		isQuiet_ = quiet;
		level_ = level;
		names_.add(name);
		parameters_ = param;
	}

	public Comando(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param) 
	{
		id_ = id;
		isQuiet_ = quiet;
		level_ = level;
		names_ = names;
		parameters_ = param;
	}

	public Comando(Comando c) 
	{
		id_ = c.id();
		isQuiet_ = c.isQuiet();
		level_ = c.level();
		names_ = c.names();
		parameters_ = c.parameters();
	}

	//Métodos comunes a todos los comandos
	public Integer id() { return id_; }
	public Boolean isQuiet() { return isQuiet_; }
	public Integer level() { return level_; }
	public Integer parameters() { return parameters_; }

	public Set<String> names() { return names_; }
	public void addName(String name) { names_.add(name); }
	public void removeName(String name) { names_.remove(name); }

	//Ejecución del comando
	public abstract void run(MessageReceivedEvent evento);
	
	//Salida por pantalla estándar
	public abstract void print(MessageReceivedEvent evento);
	

	/*FUNCIONES AUXILIARES*/

	//Post: Devuelve true si 0 <= numeroParametros <= maximo, false en caso contrario
	@SuppressWarnings("unused")
	protected static boolean numeroParametros(String mensaje, Integer maximo)
	{	
		String[] mensajeDiv = mensaje.trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		return (mensajeDiv.length >= 0 && mensajeDiv.length <= (maximo + 1));		//El número de parámetros introducidos debe estar en [0, maximo + 1], añadimos uno a maximo porque se cuenta el propio comando también en la comparación
	}
}


///////////////////////////////////////////*COMANDOS*///////////////////////////////////////////
class Apagar extends Comando 
{
	//Constructor
	public Apagar(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet = Boolean.FALSE, name, param); }
	
	public Apagar(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		//HAY QUE AÑADIR UNA FORMA DE QUE SOLO PUEDA EJECUTARSE TENIENDO PERMISOS O METIENDO UNA "CONTRASEÑA" COMO PARÁMETRO
		print(evento);
		System.exit(0);
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Apagando...").queue();
	}
}

class Ayuda extends Comando 
{
	//Constructor
	public Ayuda(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public Ayuda(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;	//Se comprueba que el número de parámetros sea correcto

		print(evento);
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage(MyListener.contenidoAyuda).queue();
	}
	
}

class BorrarAnclados extends Comando 
{
	//Constructor
	public BorrarAnclados(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public BorrarAnclados(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) print(evento);
		else if ( mensajeDiv[1].equals("ON") ) MyListener.estado.borrarAnclados = true;
		else if ( mensajeDiv[1].equals("OFF") ) MyListener.estado.borrarAnclados = false;
		else return;/*LANZAR EXCEPCION*/
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso )
			if ( MyListener.estado.borrarAnclados ) evento.getChannel().sendMessage("Borrar mensajes anclados => ON").queue();
			else evento.getChannel().sendMessage("Borrar mensajes anclados => OFF").queue();
	}
}

class LimpiarBot extends Comando 
{
	//Constructor
	public LimpiarBot(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public LimpiarBot(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) { limpiarBotAux(evento, Key.tag, MyListener.estado.numMensajes); }	//No recibe parámetros. Borra numMensajes por defecto de este bot
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarBotAux(evento, mensajeDiv[1], MyListener.estado.numMensajes); 
		}	//Recibe un prefijo. Borra numMensajes mensajes por defecto del bot par1
		else if ( mensajeDiv.length == 3) 
		{ 
			try{
				limpiarBotAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2])); 
			} catch(NumberFormatException e) {/*LANZAR EXCEPCION*/}
		}	//Recibe dos parámetros. Borra par2 mensajes del bot par1
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Mensajes eliminados: " + listaMensajes.size()).queue();
	}

	private List <Message> listaMensajes = new ArrayList<Message>();
	
	//Post: borra par2 mensajes del bot par1
	private void limpiarBotAux(MessageReceivedEvent evento, String par1, Integer par2) 
	{
		TextChannel canal = evento.getTextChannel();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getAuthor().isBot() && mensaje.getAuthor().getAsTag().equals(par1))		//Si cad1 == cad2, se borra
			{
		        listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();
		
		print(evento);
		listaMensajes.clear();	//Como listaMensajes es del ámbito de la clase habrá que vaciarla manualmente, ya que no se vaciará automáticamente al terminar la función limpiarBotAux
	}
}

class LimpiarPrefijo extends Comando 
{
	//Constructor
	public LimpiarPrefijo(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public LimpiarPrefijo(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) { limpiarPrefijoAux(evento, MyListener.prefijo, MyListener.estado.numMensajes); }		//No recibe parámetros. Borra usando el prefijo del bot con numMensajes por defecto
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarPrefijoAux(evento, mensajeDiv[1], MyListener.estado.numMensajes); 
		}	//Recibe un prefijo. Borra numMensajes mensajes por defecto que empiecen por el par1
		else if ( mensajeDiv.length == 3) 
		{ 
			try{
				limpiarPrefijoAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2])); 
			} catch(NumberFormatException e) {/*LANZAR EXCEPCION*/}
		}	//Recibe dos parámetros. Borra par2 mensajes que empiecen por par1
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Mensajes eliminados: " + listaMensajes.size()).queue();
	}
	
	List <Message> listaMensajes = new ArrayList<Message>();
	
	//Post: borra par2 mensajes que empiecen por par1
	private void limpiarPrefijoAux(MessageReceivedEvent evento, String par1, Integer par2) 
	{
		TextChannel canal = evento.getTextChannel();
		//for (MessagePaginationAction iter = canal.getIterableHistory(); par2-- < 0; iter = iter.) 
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getContentRaw().substring(0, par1.length()).equals(par1))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();	
		
		print(evento);
		listaMensajes.clear();	//Como listaMensajes es del ámbito de la clase habrá que vaciarla manualmente, ya que no se vaciará automáticamente al terminar la función limpiarBotAux	
	}
}

class LimpiarTodo extends Comando 
{
	//Constructor
	public LimpiarTodo(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public LimpiarTodo(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
	
		if ( mensajeDiv.length == 1) { limpiarTodoAux(evento, MyListener.estado.numMensajes); }	//No recibe parámetros. Borra numMensajes mensajes
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarTodoAux(evento, Integer.parseInt(mensajeDiv[1]));	//Recibe un número. Borra par1 mensajes
		}
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Mensajes eliminados: " + listaMensajes.size()).queue();
	}
	
	List <Message> listaMensajes = new ArrayList<Message>();
	
	//Post: borra par1 mensajes
	private void limpiarTodoAux(MessageReceivedEvent evento, Integer par1) 
	{
		TextChannel canal = evento.getTextChannel();
		for (Message mensaje : canal.getIterableHistory()) 
		{
		    listaMensajes.add(mensaje);
			if (par1-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();
		
		print(evento);
		listaMensajes.clear();	//Como listaMensajes es del ámbito de la clase habrá que vaciarla manualmente, ya que no se vaciará automáticamente al terminar la función limpiarBotAux
	}
}

class LimpiarUsuario extends Comando 
{
	//Constructor
	public LimpiarUsuario(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public LimpiarUsuario(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) { limpiarUsuarioAux(evento, evento.getMessage().getAuthor().getId(), MyListener.estado.numMensajes); }	//No recibe parámetros. Borra numMensajes por defecto de este usuario
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarUsuarioAux(evento, mensajeDiv[1], MyListener.estado.numMensajes); 
		}	//Recibe un usuario. Borra numMensajes mensajes por defecto del usuario par1
		else if ( mensajeDiv.length == 3) 
		{ 
			try{
				limpiarUsuarioAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2])); 
			} catch(NumberFormatException e) {/*LANZAR EXCEPCION*/}
		}	//Recibe dos parámetros. Borra par2 mensajes del usuario par1
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Mensajes eliminados: " + listaMensajes.size()).queue();
	}
	
	List <Message> listaMensajes = new ArrayList<Message>();

	//Post: borra par2 mensajes del usuario par1
	private void limpiarUsuarioAux(MessageReceivedEvent evento, String par1, Integer par2) 
	{
		TextChannel canal = evento.getTextChannel();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getAuthor().getAsTag().equals(par1))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();
		
		print(evento);
		listaMensajes.clear();	//Como listaMensajes es del ámbito de la clase habrá que vaciarla manualmente, ya que no se vaciará automáticamente al terminar la función limpiarBotAux	
	}
}

class NumeroMensajes extends Comando 
{
	//Constructor
	public NumeroMensajes(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public NumeroMensajes(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) print(evento);
		else
		{
			MyListener.estado.numMensajes = Integer.valueOf(mensajeDiv[1]);
			print(evento);
		}
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Número de mensajes borrados => " + MyListener.estado.numMensajes).queue();
	}
}

class Opciones extends Comando 
{
	//Constructor
	public Opciones(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public Opciones(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		print(evento);
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) 
		{
			String cadena = "```";	//Formato Discord: texto estilo código

			cadena += "Borrar mensajes anclados => ";
			if(MyListener.estado.borrarAnclados) cadena += "ON"; else cadena += "OFF";
			cadena += "\n";

			cadena += "Número de mensajes borrados => ";
			cadena += Integer.toString(MyListener.estado.numMensajes);
			cadena += "\n";
	
			cadena += "Modo silencioso => ";
			if(MyListener.estado.silencioso) cadena += "ON"; else cadena += "OFF";
			cadena += "\n";
	
			cadena += "```";	//Formato Discord: texto estilo código
	
			evento.getChannel().sendMessage(cadena).queue();
		}
	}
}

class Ping extends Comando 
{
	//Constructor
	public Ping(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public Ping(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		print(evento);
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Apagando...").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
	}
}

class Prefijo extends Comando 
{
	//Constructor
	public Prefijo(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public Prefijo(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) print(evento);
		else 
		{
			MyListener.prefijo = mensajeDiv[1];
			print(evento);
		}
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) evento.getChannel().sendMessage("Prefijo actual: " + MyListener.prefijo).queue();
	}
}

class Silencioso extends Comando 
{
	//Constructor
	public Silencioso(Integer id, Integer level, Boolean quiet, String name, Integer param)  { super(id, level, quiet, name, param); }
	
	public Silencioso(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param)  { super(id, level, quiet, names, param); }

	public void run(MessageReceivedEvent evento) 
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) print(evento);
		else if ( mensajeDiv[1].equals("ON") ) MyListener.estado.silencioso = true;
		else if ( mensajeDiv[1].equals("OFF") ) MyListener.estado.silencioso = false;
		else return;/*LANZAR EXCEPCION*/
	}
	
	public void print(MessageReceivedEvent evento) 
	{
		if( !isQuiet() && !MyListener.estado.silencioso ) 
			if ( MyListener.estado.silencioso ) evento.getChannel().sendMessage("Modo silencioso => ON").queue();
			else evento.getChannel().sendMessage("Modo silencioso => OFF").queue();
	}
}
