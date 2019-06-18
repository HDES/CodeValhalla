package my_bot.valhalla;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Bot 
{
    public static void main( String[] args ) throws Exception
    {
    	JDA api = new JDABuilder("NTI1MjczMTg5Mjg3MTk4NzIx.XQenDg.S9i-cUb3t65TApIIfcF2occW_Ls").addEventListener(new MyListener()).build().awaitReady();
    	
    }
    
}
