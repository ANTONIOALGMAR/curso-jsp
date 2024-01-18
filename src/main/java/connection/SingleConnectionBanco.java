package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		System.out.println("Conexão estabelecida com sucesso!"); // testar conexao
		return connection;
	}
	
	static {
		conectar();
	}
	
	
	
	public SingleConnectionBanco() {  // QUANDO TIVER UMA INSTANCIA VAI CONECTAR
		conectar();
	}



	private static void conectar() {
		
		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver"); // CARREGA O DRIVE DE CONEXAO DO BANCO
				connection = DriverManager.getConnection(senha, user, banco);
				connection.setAutoCommit(false); //PARA NAO EFETUAR ALTERACOES SEM NOSSO COMANDO
			}
		
		}catch(Exception e) {
		e.printStackTrace(); // MOSTRA QUALQUER ERRO NO MOMENTO DE CONECTAR
		}
	}
	
}
