package db;

import java.sql.Connection;

public class TestaConexao {

    public static void main(String[] args) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            System.out.println("Conexão estabelecida com sucesso!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar conectar com o banco de dados.");
        }
    }
}
