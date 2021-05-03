package myBot.comandos.utiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ComandoLimpiarTodo extends Comando {
	// Constructor
	public ComandoLimpiarTodo(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoLimpiarTodo(int id, int level, boolean quiet, Set<String> names, int param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+"); // Se divide el mensaje por
																						// espacios en distintas
																						// subcadenas

		if (!numeroParametros(evento.getMessage().getContentRaw(),
				MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))))
			/* LANZAR EXCEPCION */ return; // Se comprueba que el número de parámetros sea correcto

		if (mensajeDiv.length == 1) {
			limpiarTodoAux(evento, MyListener.estado.numMensajes);
		} // No recibe parámetros. Borra numMensajes mensajes
		else if (mensajeDiv.length == 2) {
			limpiarTodoAux(evento, Integer.parseInt(mensajeDiv[1])); // Recibe un número. Borra par1 mensajes
		}
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			evento.getChannel().sendMessage("Mensajes eliminados: " + listaMensajes.size()).queue();
	}

	List<Message> listaMensajes = new ArrayList<Message>();

	// Post: borra par1 mensajes
	private void limpiarTodoAux(MessageReceivedEvent evento, Integer par1) {
		TextChannel canal = evento.getTextChannel();
		for (Message mensaje : canal.getIterableHistory()) {
			listaMensajes.add(mensaje);
			if (par1-- < 0)
				break; // Aberración contra natura
		}
		if (!listaMensajes.isEmpty())
			canal.deleteMessages(listaMensajes).queue();

		print(evento);
		listaMensajes.clear(); // Como listaMensajes es del ámbito de la clase habrá que vaciarla manualmente,
								// ya que no se vaciará automáticamente al terminar la función limpiarBotAux
	}

	{
	}
}
