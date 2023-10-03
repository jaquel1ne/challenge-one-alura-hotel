package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import controllers.UsuarioController;
import db.ConnectionFactory;
import model.dao.UsuarioDAO;
import model.entities.Usuario;

public class RegistroUsuario extends JFrame {

    private JTextField txtNomeUsuario;
    private JPasswordField txtSenha;
    private JButton btnRegistrar;
    private JLabel lblStatus;
    private UsuarioController usuarioController;

    public RegistroUsuario(UsuarioController controller) {
        this.usuarioController = controller;

        setTitle("Registro de Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        txtNomeUsuario = new JTextField(20);
        txtSenha = new JPasswordField(20);
        btnRegistrar = new JButton("Registrar");
        lblStatus = new JLabel("");

        // Definições do GridBagConstraints
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Nome de Usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome de Usuário:"), gbc);
        
        gbc.gridx = 1;
        add(txtNomeUsuario, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        add(txtSenha, gbc);

        // Botão Registrar
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnRegistrar, gbc);

        // Label Status
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblStatus, gbc);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String nomeUsuario = txtNomeUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (nomeUsuario.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("Por favor, preencha todos os campos.");
            return;
        }

        if (senha.length() < 6) {
            lblStatus.setText("A senha deve ter pelo menos 6 caracteres.");
            return;
        }

        // Se chegou aqui, tenta registrar o usuário.
        Usuario novoUsuario = new Usuario(nomeUsuario, senha);
        usuarioController.salvar(novoUsuario);
        lblStatus.setText("Usuário registrado com sucesso!");
        txtNomeUsuario.setText("");
        txtSenha.setText("");
        
     // Feche a janela atual e abra a tela de login
        this.dispose(); 
        Login loginFrame = new Login(usuarioController);
        loginFrame.setVisible(true);
    }    
    

    // Main para testes
    public static void main(String[] args) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(ConnectionFactory.getConnection()); 
            UsuarioController controller = new UsuarioController(usuarioDAO);
            RegistroUsuario frame = new RegistroUsuario(controller);
            frame.setVisible(true);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
