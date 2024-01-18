package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {

		System.out.println("Validating authentication...");

		try {
			String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, modelLogin.getLogin());
			statement.setString(2, modelLogin.getSenha());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;/* autenticado */
			}

			return false; /* nao autenticado */

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao validar autenticação: " + e.getMessage());
			System.err.println("Estado da conexão: " + (connection == null ? "nula" : "não nula"));
			throw e;
		}

	}
}