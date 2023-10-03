package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entities.Usuario;
import utils.PasswordUtil;

public class UsuarioDAO {
	
	private Connection connection;

	public UsuarioDAO(Connection connection) {
		this.connection = connection;
	}
	
		
	public void insert(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (login, senha, isAdmin) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, PasswordUtil.hashPassword(usuario.getSenha()));
            stmt.setBoolean(3, usuario.isAdmin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public List<Usuario> find() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setAdmin(rs.getBoolean("isAdmin"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
	
	public Usuario findByUsername(String username) {
	    Usuario user = null;
	    String sql = "SELECT * FROM Usuarios WHERE login = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            user = new Usuario();
	            user.setId(rs.getLong("id"));
	            user.setLogin(rs.getString("login"));
	            user.setSenha(rs.getString("senha"));
	            user.setAdmin(rs.getBoolean("isAdmin"));
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return user;
	}

	public void update(Usuario usuario) {
        String sql = "UPDATE Usuarios SET login=?, senha=?, isAdmin=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, PasswordUtil.hashPassword(usuario.getSenha()));
            stmt.setBoolean(3, usuario.isAdmin());
            stmt.setLong(4, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void delete(Usuario usuario) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
}
