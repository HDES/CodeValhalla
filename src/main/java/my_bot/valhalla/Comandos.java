<<<<<<< HEAD
package my_bot.valhalla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my_bot.valhalla.MyListener.Estado;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Comandos{
	//Orden alfabético

	//Pre:
	public void ayuda(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		evento.getChannel().sendMessage(contenidoAyuda).queue();
	}

	//Pre:
	public void borrarAnclados(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) //Igual a 1 porque además de los parámetros tiene en mensajeDiv[0] el comando en si
		{
			if ( estado.borrarAnclados ) evento.getChannel().sendMessage("ON").queue();
			else evento.getChannel().sendMessage("OFF").queue();
		}
		else if ( mensajeDiv[1].equals("ON") ) estado.borrarAnclados = true;
		else if ( mensajeDiv[1].equals("OFF") ) estado.borrarAnclados = false;
		else /*LANZAR EXCEPCION*/
	}

	//Pre:
	public void limpiarPrefijo(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) { limpiarPrefijoAux(evento, prefijo, estado.numMensajes); }		//No recibe parámetros. Borra usando el prefijo del bot con numMensajes por defecto
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarPrefijoAux(evento, mensajeDiv[1], estado.numMensajes); 
		}	//Recibe un prefijo. Borra numMensajes mensajes por defecto que empiecen por el par1
		else if ( mensajeDiv.length == 3) 
		{ 
			try{
				limpiarPrefijoAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2])); 
			} catch(NumberFormatException e) {/*LANZAR EXCEPCION*/}
		}	//Recibe dos parámetros. Borra par2 mensajes que empiecen por par1
	}

	//Pre:
	public void limpiarBot(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) { limpiarBotAux(evento, botClientID, estado.numMensajes); }	//No recibe parámetros. Borra numMensajes por defecto de este bot
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarBotAux(evento, mensajeDiv[1], estado.numMensajes); 
		}	//Recibe un prefijo. Borra numMensajes mensajes por defecto del bot par1
		else if ( mensajeDiv.length == 3) 
		{ 
			try{
				limpiarBotAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2])); 
			} catch(NumberFormatException e) {/*LANZAR EXCEPCION*/}
		}	//Recibe dos parámetros. Borra par2 mensajes del bot par1
	}

	//Pre:
	public void limpiarTodo(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
	
		if ( mensajeDiv.length == 1) { limpiarTodoAux(evento, estado.numMensajes); }	//No recibe parámetros. Borra numMensajes mensajes
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarTodoAux(evento, mensajeDiv[1]); 
		}	//Recibe un número. Borra par1 mensajes
	}

	//Pre:
	public void limpiarUsuario(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) { limpiarUsuarioAux(evento, evento.getMessage().getAuthor().getId(), estado.numMensajes); }	//No recibe parámetros. Borra numMensajes por defecto de este usuario
		else if ( mensajeDiv.length == 2) 
		{ 
			limpiarUsuarioAux(evento, mensajeDiv[1], estado.numMensajes); 
		}	//Recibe un usuario. Borra numMensajes mensajes por defecto del usuario par1
		else if ( mensajeDiv.length == 3) 
		{ 
			try{
				limpiarUsuarioAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2])); 
			} catch(NumberFormatException e) {/*LANZAR EXCEPCION*/}
		}	//Recibe dos parámetros. Borra par2 mensajes del usuario par1
	}

	//Pre:
	public void numeroMensajes(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) evento.getChannel().sendMessage(Integer.toString(estado.numMensajes)).queue();
		else estado.numMensajes = Integer.parseInt(mensajeDiv[1]);
	}

	//Pre:
	public void opciones(MessageReceivedEvent evento)
	{
		String cadena = "";

		cadena.concat("Borrar mensajes anclados => ");
		estado.borrarAnclados ? cadena.concat("ON") : cadena.concat("OFF");
		cadena.concat("%n");

		cadena.concat("Idioma => ");
		estado.idioma ? cadena.concat("ENG") : cadena.concat("ESP");
		cadena.concat("%n");

		cadena.concat("Número de mensajes borrados => ");
		cadena.concat(Integer.toString(estado.numMensajes));
		cadena.concat("%n");
		
		cadena.concat("Modo silencioso => ");
		estado.silencioso ? cadena.concat("ON") : cadena.concat("OFF");
		cadena.concat("%n");
		
		evento.getChannel().sendMessage(cadena).queue();
	}

	//Pre:
	public void ping(MessageReceivedEvent evento)
	{
		evento.getChannel().sendMessage("Pong").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
	}

	//Pre:
	public void prefijo(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1) evento.getChannel().sendMessage(prefijo).queue();
		else prefijo = mensajeDiv[1];
	}

	//Pre:
	public void silencioso(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 1)
		{
			if ( estado.silencioso ) evento.getChannel().sendMessage("ON").queue();
			else evento.getChannel().sendMessage("OFF").queue();
		}
		else if ( mensajeDiv[1].equals("ON") ) estado.silencioso = true;
		else if ( mensajeDiv[1].equals("OFF") ) estado.silencioso = false;
		else /*LANZAR EXCEPCION*/
	}




	/*FUNCIONES AUXILIARES*/

	//Post: Devuelve true si 0 <= numeroParamentros <= maximo, false en caso contrario
	private boolean numeroParametros(String mensaje, int maximo)
	{
		String[] mensajeDiv = mensaje.trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		return (mensajeDiv.length >= 0 && mensajeDiv.length <= maximo);		//El número de parámetros introducidos debe estar en [0,maximo]
	}

	//Post: borra par2 mensajes que empiecen por par1
	private void limpiarPrefijoAux(MessageReceivedEvent evento, String par1, int par2) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getContentRaw().substring(0, par1.length()).equals(par1))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		
	}

	//Post: borra par2 mensajes del bot par1
	private void limpiarBotAux(MessageReceivedEvent evento, String par1, int par2) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getAuthor().isBot() && mensaje.getAuthor().getId().equals(par1))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		
	}

	//Post: borra par1 mensajes
	private void limpiarTodoAux(MessageReceivedEvent evento, int par1) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
		    listaMensajes.add(mensaje);
			if (par1-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();
	}

	//Post: borra par2 mensajes del usuario par1
	private void limpiarUsuarioAux(MessageReceivedEvent evento, String par1, int par2) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (!mensaje.getAuthor().isBot() && mensaje.getAuthor().getId().equals(par1))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (par2-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		
	}
}

/*INTERFAZ*/
//Se usa una clase interfaz para evitar el uso de switch() y if-else anidados
//Equivale al uso de puntero a función en C/C++

public interface IComandos
{
	public void IFuncion(MessageReceivedEvent evento); 
}
   
IComandos[] opciones = {
	//Orden alfabético
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ ayuda(); 				} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ borrarAnclados(); 		} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarBot();			} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarPrefijo(); 		} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarTodo(); 			} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarUsuario(); 		} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ opciones(); 			} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ ping(); 				} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ prefijo(); 				} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ silencioso(); 			} }
};

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
=======
package my_bot.valhalla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my_bot.valhalla.MyListener.Estado;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Comandos{
	//Orden alfabético

	//Pre:
	public void ayuda(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		evento.getChannel().sendMessage(contenidoAyuda).queue();
	}

	//Pre:
	public void borrarAnclados(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 0) 
		{
			if ( estado.borrarAnclados ) evento.getChannel().sendMessage("ON").queue();
			else evento.getChannel().sendMessage("OFF").queue();
		}
		else if ( mensajeDiv[1].equals("ON") ) estado.borrarAnclados = true;
		else if ( mensajeDiv[1].equals("OFF") ) estado.borrarAnclados = false;
		else /*LANZAR EXCEPCION*/
	}

	//Pre:
	public void limpiarPrefijo(MessageReceivedEvent evento)
	{
		//hay que comprobar la validez de los parámetros de entrada
		limpiarGenericaCondicional(evento, mensaje.getContentRaw().substring(0, tamPrefijo), prefijo, estado.numMensajes);
	}

	//Pre:
	public void limpiarBot(MessageReceivedEvent evento)
	{
		//hay que comprobar la validez de los parámetros de entrada
		limpiarGenericaCondicional(evento, mensaje.getAuthor().getId(), botClientID, estado.numMensajes);
	}

	//Pre:
	public void limpiarTodo(MessageReceivedEvent evento)
	{
		//hay que comprobar la validez de los parámetros de entrada
		limpiarGenerica(evento, estado.numMensajes);
	}

	//Pre:
	public void limpiarUsuario(MessageReceivedEvent evento)
	{
		//hay que comprobar la validez de los parámetros de entrada
		limpiarGenericaCondicional(evento, /*mensaje.getAuthor().getId(), botClientID*/, estado.numMensajes);
	}

	//Pre:
	public void numeroMensajes(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 0) evento.getChannel().sendMessage(Integer.toString(estado.numMensajes)).queue();
		else estado.numMensajes = Integer.parseInt(mensajeDiv[1]);
	}

	//Pre:
	public void opciones(MessageReceivedEvent evento)
	{
		String cadena = "";

		cadena.concat("Borrar mensajes anclados => ");
		estado.borrarAnclados ? cadena.concat("ON") : cadena.concat("OFF");
		cadena.concat("%n");

		cadena.concat("Idioma => ");
		estado.idioma ? cadena.concat("ENG") : cadena.concat("ESP");
		cadena.concat("%n");

		cadena.concat("Número de mensajes borrados => ");
		cadena.concat(Integer.toString(estado.numMensajes));
		cadena.concat("%n");
		
		cadena.concat("Modo silencioso => ");
		estado.silencioso ? cadena.concat("ON") : cadena.concat("OFF");
		cadena.concat("%n");
		
		evento.getChannel().sendMessage(cadena).queue();
	}

	//Pre:
	public void ping(MessageReceivedEvent evento)
	{
		evento.getChannel().sendMessage("Pong").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
	}

	//Pre:
	public void prefijo(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 0) evento.getChannel().sendMessage(prefijo).queue();
		else prefijo = mensajeDiv[1];
	}

	//Pre:
	public void silencioso(MessageReceivedEvent evento)
	{
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		
		if ( !numeroParametros(evento.getMessage().getContentRaw(), comandosPar.get(mensajeDiv[0].substring(tamPrefijo))) ) /*LANZAR EXCEPCION*/ return;		//Se comprueba que el número de parámetros sea correcto
		
		if ( mensajeDiv.length == 0) 
		{
			if ( estado.silencioso ) evento.getChannel().sendMessage("ON").queue();
			else evento.getChannel().sendMessage("OFF").queue();
		}
		else if ( mensajeDiv[1].equals("ON") ) estado.silencioso = true;
		else if ( mensajeDiv[1].equals("OFF") ) estado.silencioso = false;
		else /*LANZAR EXCEPCION*/
	}




	/*FUNCIONES AUXILIARES*/

	//Post: Devuelve true si 0 <= numeroParamentros <= maximo, false en caso contrario
	private boolean numeroParametros(String mensaje, int maximo)
	{
		String[] mensajeDiv = mensaje.trim().split("\\s+");		//Se divide el mensaje por espacios en distintas subcadenas
		return (mensajeDiv.length >= 0 && mensajeDiv.length <= maximo);		//El número de parámetros introducidos debe estar en [0,maximo]
	}

	//Post: borra los mensajes cuyos cad1 sean iguales a cad2
	private void limpiarGenericaCondicional(MessageReceivedEvent evento, String cad1, String cad2, int numMensajes) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (cad1.equals(cad2))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (numMensajes-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		
	}

	//Post: borra numMensajes mensajes
	private void limpiarGenerica(MessageReceivedEvent evento, int numMensajes) 
	{
		TextChannel canal = evento.getTextChannel();
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
		    listaMensajes.add(mensaje);
			if (numMensajes-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();
	}

}

/*INTERFAZ*/
//Se usa una clase interfaz para evitar el uso de switch() y if-else anidados
//Equivale al uso de puntero a función en C/C++

public interface IComandos
{
	public void IFuncion(MessageReceivedEvent evento); 
}
   
IComandos[] opciones = {
	//Orden alfabético
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ ayuda(); 				} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ borrarAnclados(); 	} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarBot();			} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarPrefijo(); 		} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarTodo(); 			} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ limpiarUsuario(); 		} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ opciones(); 			} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ ping(); 				} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ prefijo(); 				} },
	new IComandos(){ public void IFuncion(MessageReceivedEvent evento){ silencioso(); 			} }
};

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
>>>>>>> 975a64ca18c6836dfd754ab4a3fcb2030bd62dd0
*/