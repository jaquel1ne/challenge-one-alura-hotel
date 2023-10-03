package controllers;

import java.util.List;

import model.dao.UsuarioDAO;
import model.entities.Usuario;
import utils.PasswordUtil;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void salvar(Usuario usuario) {
        usuarioDAO.insert(usuario);
    }

    public List<Usuario> find() {
        return usuarioDAO.find();
    }

    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioDAO.delete(usuario);
    }
    
    public boolean verificarCredenciais(String username, String password) {
        Usuario user = usuarioDAO.findByUsername(username);
        if (user != null) {
            return PasswordUtil.checkPassword(password, user.getSenha());
        }
        return false;
    }
}