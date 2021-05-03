package myBot.comandos;

import java.util.HashSet;
import java.util.Set;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Comando {

	// Atributos comunes a todos los comandos
	protected final int id; // Identificador arbitrario
	protected final boolean isQuiet; // TRUE si muestra algún mensaje por pantalla
	protected final int level; // Nivel de acceso (
	protected Set<String> names = new HashSet<String>(); // Posibles nombres para invocar al comando
	protected final int parameters; // Número de parámetros máximo que admite

	// Constructor
	public Comando(int id, int level, boolean quiet, /* Set<String> names, */ String name, int param) {
		this.id = id;
		this.isQuiet = quiet;
		this.level = level;
		this.names.add(name);
		this.parameters = param;
	}

	public Comando(int id, int level, boolean quiet, Set<String> names, int param) {
		this.id = id;
		this.isQuiet = quiet;
		this.level = level;
		this.names = names;
		this.parameters = param;
	}

	public Comando(Comando c) {
		this.id = c.id();
		this.isQuiet = c.isQuiet();
		this.level = c.level();
		this.names = c.names();
		this.parameters = c.parameters();
	}

	// Métodos comunes a todos los comandos
	public Integer id() {
		return id;
	}

	public Boolean isQuiet() {
		return isQuiet;
	}

	public Integer level() {
		return level;
	}

	public Integer parameters() {
		return parameters;
	}

	public Set<String> names() {
		return names;
	}

	public void addName(String name) {
		names.add(name);
	}

	public void removeName(String name) {
		names.remove(name);
	}

	// Ejecución del comando
	public abstract void run(MessageReceivedEvent evento);

	// Salida por pantalla estándar
	public abstract void print(MessageReceivedEvent evento);

	/* FUNCIONES AUXILIARES */

	// Post: Devuelve true si 0 <= numeroParametros <= maximo, false en caso
	// contrario
	@SuppressWarnings("unused")
	protected static boolean numeroParametros(String mensaje, Integer maximo) {
		String[] mensajeDiv = mensaje.trim().split("\\s+"); // Se divide el mensaje por espacios en distintas subcadenas
		return (mensajeDiv.length >= 0 && mensajeDiv.length <= (maximo + 1)); // El número de parámetros introducidos
																				// debe estar en [0, maximo + 1],
																				// añadimos uno a maximo porque se
																				// cuenta el propio comando también en
																				// la comparación
	}
}