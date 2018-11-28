package bd;

import bd.core.*;
import bd.daos.*;

public class BDPostgreSQL {
    public static final MeuPreparedStatement COMANDO;

    static {
    	MeuPreparedStatement comando = null;
    	try {
            comando = new MeuPreparedStatement ("org.postgresql.Driver", "jdbc:postgresql://localhost/donation", "postgres", "");
        } catch (Exception error) {
        	System.out.println (error.getMessage());
            System.err.println ("Problemas de conexao com o BD");
            System.exit(0); // Aborts the program.
        }
        COMANDO = comando;
    }
}
