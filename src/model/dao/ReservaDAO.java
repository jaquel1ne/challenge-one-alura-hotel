package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entities.FormaPagamento;
import model.entities.Reserva;

public class ReservaDAO {
	
	private Connection connection;

	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	public Long insert(Reserva reserva) {
	    String sql = "INSERT INTO Reservas (DataEntrada, DataSaida, Valor, FormaPagamento) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        stmt.setDate(1, new java.sql.Date(reserva.getDataEntrada().getTime()));
	        stmt.setDate(2, new java.sql.Date(reserva.getDataSaida().getTime()));
	        stmt.setBigDecimal(3, reserva.getValor());
	        stmt.setString(4, reserva.getFormaPagamento().toString());
	        
	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Criando reserva falhou, nenhuma linha foi afetada.");
	        }

	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getLong(1);
	            } else {
	                throw new SQLException("Criando reserva falhou, nenhum ID obtido.");
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	
	public List<Reserva> find(String nome) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas WHERE Nome LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getLong("Id"));
                reserva.setDataEntrada(rs.getDate("DataEntrada"));
                reserva.setDataSaida(rs.getDate("DataSaida"));
                reserva.setValor(rs.getBigDecimal("Valor"));
                reserva.setFormaPagamento(FormaPagamento.valueOf(rs.getString("FormaPagamento")));
                
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservas;
    }
	
	
	public Reserva findById(Long id) {
	    Reserva reserva = null;
	    String sql = "SELECT * FROM Reservas WHERE Id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setLong(1, id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            reserva = new Reserva();
	            reserva.setId(rs.getLong("Id"));
	            reserva.setDataEntrada(rs.getDate("DataEntrada"));
	            reserva.setDataSaida(rs.getDate("DataSaida"));
	            reserva.setValor(rs.getBigDecimal("Valor"));

	            String formaPagamentoString = rs.getString("FormaPagamento");
	            if(formaPagamentoString != null) {
	            	 reserva.setFormaPagamento(FormaPagamento.getByDescricao(formaPagamentoString));
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return reserva;
	}
	
	public void update(Reserva reserva) {
	    String sql = "UPDATE Reservas SET DataEntrada=?, DataSaida=?, Valor=?, FormaPagamento=? WHERE Id=?";
	    FormaPagamento formaPagamento = reserva.getFormaPagamento(); 

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setDate(1, new java.sql.Date(reserva.getDataEntrada().getTime()));
	        stmt.setDate(2, new java.sql.Date(reserva.getDataSaida().getTime()));
	        stmt.setBigDecimal(3, reserva.getValor());
	        stmt.setString(4, (formaPagamento != null) ? formaPagamento.toString() : null); 
	        stmt.setLong(5, reserva.getId());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	
	public void delete(Reserva reserva) {
        deleteById(reserva.getId());
    }
	
	
	public void deleteById(Long id) {
        String sql = "DELETE FROM Reservas WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
}
