package my_bot.valhalla;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Bot 
{
    public static void main( String[] args )
    {
    	try 
    	{  		
    	JDA api = new JDABuilder("NTI1MjczMTg5Mjg3MTk4NzIx.XQenDg.S9i-cUb3t65TApIIfcF2occW_Ls").addEventListener(new MyListener()).build().awaitReady();
    	//el método awaitReady hace "Blocking" garantizando que el JDA se cargará guay

    	} 
    	
    	catch (LoginException e) {
    		// Esto avisará de error en caso de que haya fallo en el proceso de autentificación
    		e.printStackTrace();
    	}
    	
    	catch (InterruptedException e) {
    		// Controla el método awaitReady()
    		e.printStackTrace();
    	}
    	
    }
    
}
