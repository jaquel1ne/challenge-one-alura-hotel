package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.entities.Hospede;

public class HospedeDAO {
	
	private Connection connection;

	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void insert(Hospede hospede) {
		String sql = "INSERT INTO Hospedes (Nome, Sobrenome, DataNascimento, Nacionalidade, Telefone, IdReserva) VALUES (?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, hospede.getNome());
	        stmt.setString(2, hospede.getSobrenome());
	        stmt.setDate(3, new java.sql.Date(hospede.getDataNascimento().getTime()));
	        stmt.setString(4, hospede.getNacionalidade());
	        stmt.setString(5, hospede.getTelefone());
	        stmt.setLong(6, hospede.getIdReserva());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public List<Hospede> find(String nome) {
	    List<Hospede> hospedes = new ArrayList<>();
	    String sql = "SELECT * FROM Hospedes WHERE Nome LIKE ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, "%" + nome + "%");  
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Hospede hospede = new Hospede();
	                hospede.setId(rs.getLong("Id"));
	                hospede.setNome(rs.getString("Nome"));
	                hospede.setSobrenome(rs.getString("Sobrenome"));
	                hospede.setDataNascimento(rs.getDate("DataNascimento"));
	                hospede.setNacionalidade(rs.getString("Nacionalidade"));
	                hospede.setTelefone(rs.getString("Telefone"));
	                hospede.setIdReserva(rs.getLong("IdReserva"));
	                hospedes.add(hospede);
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return hospedes;
	}
	
	public List<Hospede> findByReservaId(Long reservaId) {
	    List<Hospede> hospedes = new ArrayList<>();
	    String sql = "SELECT * FROM Hospedes WHERE IdReserva = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setLong(1, reservaId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Hospede hospede = new Hospede();
	                hospede.setId(rs.getLong("Id"));
	                hospede.setNome(rs.getString("Nome"));
	                hospede.setSobrenome(rs.getString("Sobrenome"));
	                hospede.setDataNascimento(rs.getDate("DataNascimento"));
	                hospede.setNacionalidade(rs.getString("Nacionalidade"));
	                hospede.setTelefone(rs.getString("Telefone"));
	                hospede.setIdReserva(rs.getLong("IdReserva"));
	                hospedes.add(hospede);
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return hospedes;
	}

	
	public void update(Hospede hospede) {
	    if (!reservaExists(hospede.getIdReserva())) {
	        hospede.setIdReserva(null);
	    }
	    
	    String sql = "UPDATE Hospedes SET Nome = ?, Sobrenome = ?, DataNascimento = ?, Nacionalidade = ?, Telefone = ?, IdReserva = ? WHERE Id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, hospede.getNome());
	        stmt.setString(2, hospede.getSobrenome());
	        stmt.setDate(3, new java.sql.Date(hospede.getDataNascimento().getTime()));
	        stmt.setString(4, hospede.getNacionalidade());
	        stmt.setString(5, hospede.getTelefone());
	        
	        if (hospede.getIdReserva() != null) {
	            stmt.setLong(6, hospede.getIdReserva());
	        } else {
	            stmt.setNull(6, Types.BIGINT);  
	        }

	        stmt.setLong(7, hospede.getId());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	private boolean reservaExists(Long idReserva) {
	    if (idReserva == null) return false;

	    String sql = "SELECT COUNT(*) FROM reservas WHERE Id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setLong(1, idReserva);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return false;
	}


	     	
	public void delete(Hospede hospede) {
	    deleteById(hospede.getId());
	}
	
	
	public void deleteById(Long id) {
	    String sql = "DELETE FROM Hospedes WHERE Id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setLong(1, id);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public List<Hospede> buscarPorSobrenome(String sobrenome) {
	    List<Hospede> hospedes = new ArrayList<>();
	    String sql = "SELECT * FROM Hospedes WHERE Sobrenome = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, sobrenome);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Hospede hospede = new Hospede();
	                hospede.setId(rs.getLong("Id"));
	                hospede.setNome(rs.getString("Nome"));
	                hospede.setSobrenome(rs.getString("Sobrenome"));
	                hospede.setDataNascimento(rs.getDate("DataNascimento"));
	                hospede.setNacionalidade(rs.getString("Nacionalidade"));
	                hospede.setTelefone(rs.getString("Telefone"));
	                hospede.setIdReserva(rs.getLong("IdReserva"));
	                hospedes.add(hospede);
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return hospedes;
	}

}
