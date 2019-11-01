package my_bot.valhalla;

import java.util.ArrayList;
import java.util.List;

import my_bot.valhalla.Key;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Comandoss{
	//Orden alfabético

	//Pre:
	public static void apagar(MessageReceivedEvent evento)
	{
		//HAY QUE AÑADIR UNA FORMA DE QUE SOLO PUEDA EJECUTARSE TENIENDO PERMISOS O METIENDO UNA "CONTRASEÑA" COMO PARÁMETRO
		System.exit(0);
	}

	//Pre:
	public static void ayuda(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;	//Se comprueba que el número de parámetros sea correcto

		evento.getChannel().sendMessage(MyListener.contenidoAyuda).queue();
	}

	//Pre:
	public static void borrarAnclados(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) //Igual a 1 porque además de los parámetros tiene en mensajeDiv[0] el comando en si
		{
			if ( MyListener.estado.borrarAnclados ) evento.getChannel().sendMessage("ON").queue();
			else evento.getChannel().sendMessage("OFF").queue();
		}
		else if ( mensajeDiv[1].equals("ON") ) MyListener.estado.borrarAnclados = true;
		else if ( mensajeDiv[1].equals("OFF") ) MyListener.estado.borrarAnclados = false;
		else return;/*LANZAR EXCEPCION*/
	}

	//Pre:
	public static void limpiarBot(MessageReceivedEvent evento)
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

	//Pre:
	public static void limpiarPrefijo(MessageReceivedEvent evento)
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

	//Pre:
	public static void limpiarTodo(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
	
		if ( mensajeDiv.length == 1) { limpiarTodoAux(evento, MyListener.estado.numMensajes); }	//No recibe parámetros. Borra numMensajes mensajes
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarTodoAux(evento, Integer.parseInt(mensajeDiv[1]));	//Recibe un número. Borra par1 mensajes
		}
	}

	//Pre:
	public static void limpiarUsuario(MessageReceivedEvent evento)
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

	//Pre:
	public static void numeroMensajes(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) evento.getChannel().sendMessage(Integer.toString(MyListener.estado.numMensajes)).queue();
		else MyListener.estado.numMensajes = Integer.parseInt(mensajeDiv[1]);
	}

	//Pre:
	public static void opciones(MessageReceivedEvent evento)
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

	//Pre:
	public static void ping(MessageReceivedEvent evento)
	{
		evento.getChannel().sendMessage("pong").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
	}

	//Pre:
	public static void prefijo(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) evento.getChannel().sendMessage(MyListener.prefijo).queue();
		else MyListener.prefijo = mensajeDiv[1];
	}

	//Pre:
	public static void silencioso(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1)
		{
			if ( MyListener.estado.silencioso ) evento.getChannel().sendMessage("ON").queue();
			else evento.getChannel().sendMessage("OFF").queue();
		}
		else if ( mensajeDiv[1].equals("ON") ) MyListener.estado.silencioso = true;
		else if ( mensajeDiv[1].equals("OFF") ) MyListener.estado.silencioso = false;
		else return;/*LANZAR EXCEPCION*/
	}

	/*FUNCIONES AUXILIARES*/

	//Post: Devuelve true si 0 <= numeroParametros <= maximo, false en caso contrario
	private static boolean numeroParametros(String mensaje, Integer maximo)
	{	
		String[] mensajeDiv = mensaje.trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		return (mensajeDiv.length >= 0 && mensajeDiv.length <= (maximo + 1));		//El número de parámetros introducidos debe estar en [0, maximo + 1], añadimos uno a maximo porque se cuenta el propio comando también en la comparación
	}

	//Post: borra par2 mensajes del bot par1
	private static void limpiarBotAux(MessageReceivedEvent evento, String par1, Integer par2) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList<Message>();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getAuthor().isBot() && mensaje.getAuthor().getAsTag().equals(par1))		//Si cad1 == cad2, se borra
			{
		        listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();		
	}

	//Post: borra par2 mensajes que empiecen por par1
	private static void limpiarPrefijoAux(MessageReceivedEvent evento, String par1, Integer par2) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList<Message>();
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
	}

	//Post: borra par1 mensajes
	private static void limpiarTodoAux(MessageReceivedEvent evento, Integer par1) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList<Message>();
		for (Message mensaje : canal.getIterableHistory()) 
		{
		    listaMensajes.add(mensaje);
			if (par1-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();
	}

	//Post: borra par2 mensajes del usuario par1
	private static void limpiarUsuarioAux(MessageReceivedEvent evento, String par1, Integer par2) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList<Message>();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getAuthor().getAsTag().equals(par1))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		if( !listaMensajes.isEmpty() ) canal.deleteMessages(listaMensajes).queue();		
	}

	/*INTERFAZ*/
	//Se usa una clase interfaz para evitar el uso de switch() y if-else anidados
	//Equivale al uso de puntero a función en C/C++
	
	public interface IComandos
	{
		public void IFuncion(MessageReceivedEvent evento); 
	}
	   
	static IComandos[] opciones = {
		//Orden alfabético
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ apagar(evento); 			} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ ayuda(evento); 				} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ borrarAnclados(evento); 	} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarBot(evento);			} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarPrefijo(evento); 	} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarTodo(evento); 		} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarUsuario(evento); 	} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ numeroMensajes(evento); 	} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ opciones(evento); 			} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ ping(evento); 				} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ prefijo(evento); 			} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ silencioso(evento); 		} },
		new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ /*NO HACER NADA*/			} }		//El valor por defecto, si no existe el comando
	};
}

/* //RAW
   public interface Function{
	public void function(); 
   }
   
   
   Function[] options = {
	new Function(){ public void function(){ System.out.println("Option 0"); } },
	new Function(){ public void function(){ System.out.println("Option 1"); } },
	new Function(){ public void function(){ System.out.println("Option 2"); } },
	new Function(){ public void function(){ System.out.println("Option 3"); } }
   };
   
   options[2].function();
*/