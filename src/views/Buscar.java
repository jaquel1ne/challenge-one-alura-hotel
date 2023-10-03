package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.HospedeController;
import controllers.ReservaController;
import db.ConnectionFactory;
import model.dao.ReservaDAO;
import model.entities.FormaPagamento;
import model.entities.Hospede;
import model.entities.Reserva;
import model.dao.HospedeDAO;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SuppressWarnings("serial")
public class Buscar extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHospedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHospedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ReservaController reservaController;
	private HospedeController hospedeController;
	private boolean isEditing = false;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Buscar() {
		try {
		    reservaController = new ReservaController(new ReservaDAO(ConnectionFactory.getConnection()));
		    hospedeController = new HospedeController(new HospedeDAO(ConnectionFactory.getConnection()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(Buscar.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblTitulo = new JLabel("SISTEMA DE BUSCA");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblTitulo.setBounds(331, 62, 280, 42);
		contentPane.add(lblTitulo);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
				
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Data Check In");
		modelo.addColumn("Data Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		
		tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("Numero de Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Numero de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHospedes);
		panel.addTab("Huéspedes", new ImageIcon(Buscar.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Buscar.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { // Quando o usuário passa o mouse sobre o botão, ele muda de cor
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Quando o usuário remove o mouse do botão, ele retornará ao estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        String criterioDeBusca = txtBuscar.getText().trim();

		        if (criterioDeBusca.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Digite um critério de busca.");
		            return;
		        }

		        try {
		            Long numeroReserva = Long.parseLong(criterioDeBusca);
		            
		            Reserva reserva = reservaController.buscarPorId(numeroReserva);
		            if(reserva != null) {
		            	preencherTabelaReservas(Arrays.asList(reserva));
		                List<Hospede> hospedesDaReserva = hospedeController.findByIdReserva(numeroReserva);
		                preencherTabelaHospedes(hospedesDaReserva);
		                tbReservas.revalidate();
		                tbReservas.repaint();

		            } else {
		                JOptionPane.showMessageDialog(null, "Nenhuma reserva encontrada com o número " + numeroReserva);
		            }

		        } catch (NumberFormatException ex) {
		            List<Hospede> hospedes = hospedeController.buscarPorSobrenome(criterioDeBusca);
		            if (!hospedes.isEmpty()) {
		                preencherTabelaHospedes(hospedes);
		            } else {
		                JOptionPane.showMessageDialog(null, "Nenhum hóspede encontrado com o sobrenome " + criterioDeBusca);
		            }
		        }
		    }
		});

		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (!isEditing) { 
		            tbReservas.setRowSelectionAllowed(true);
		            tbHospedes.setRowSelectionAllowed(true);
		            lblEditar.setText("SALVAR");
		            isEditing = true;
		        } else { 
		            isEditing = false;
		            tbReservas.setRowSelectionAllowed(false);
		            tbHospedes.setRowSelectionAllowed(false);
		            lblEditar.setText("EDITAR");
		            
		            int selectedRowReserva = tbReservas.getSelectedRow();
		            if (selectedRowReserva != -1) {
		            	Reserva reserva = new Reserva();
		                reserva.setId((Long) modelo.getValueAt(selectedRowReserva, 0));
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		             // Para Data de Entrada
		             Object dataEntradaObj = modelo.getValueAt(selectedRowReserva, 1);
		             java.util.Date dataEntrada;

		             if (dataEntradaObj instanceof String) {
		                 try {
		                     dataEntrada = sdf.parse((String) dataEntradaObj);
		                 } catch (ParseException e1) {
		                     dataEntrada = new java.util.Date(); 
		                     e1.printStackTrace();
		                 }
		             } else if (dataEntradaObj instanceof java.sql.Date) {
		                 dataEntrada = new java.util.Date(((java.sql.Date) dataEntradaObj).getTime());
		             } else {
		                 dataEntrada = new java.util.Date(); // default value
		             }

		             reserva.setDataEntrada(dataEntrada);

		             // Para Data de Saída 
		             Object dataSaidaObj = modelo.getValueAt(selectedRowReserva, 2);
		             java.util.Date dataSaida;

		             if (dataSaidaObj instanceof String) {
		                 try {
		                     dataSaida = sdf.parse((String) dataSaidaObj);
		                 } catch (ParseException e1) {
		                     dataSaida = new java.util.Date(); 
		                     e1.printStackTrace();
		                 }
		             } else if (dataSaidaObj instanceof java.sql.Date) {
		                 dataSaida = new java.util.Date(((java.sql.Date) dataSaidaObj).getTime());
		             } else {
		                 dataSaida = new java.util.Date(); // default value
		             }

		             reserva.setDataSaida(dataSaida);
		             Object valorObj = modelo.getValueAt(selectedRowReserva, 3);
		             BigDecimal valor;
		             if (valorObj instanceof String) {
		                    try {
		                        valor = new BigDecimal((String) valorObj);
		                    } catch (NumberFormatException e1) {
		                        valor = BigDecimal.ZERO; 
		                        e1.printStackTrace();
		                    }
		                } else if (valorObj instanceof BigDecimal) {
		                    valor = (BigDecimal) valorObj;
		                } else {
		                    valor = BigDecimal.ZERO; 
		                }

		                reserva.setValor(valor);

		                reserva.setFormaPagamento((FormaPagamento) modelo.getValueAt(selectedRowReserva, 4));
		                reservaController.update(reserva);
		            }
		            
		            int selectedRowHospede = tbHospedes.getSelectedRow();
		            if (selectedRowHospede != -1) {
		                Hospede hospede = new Hospede();
		                hospede.setId((Long) modeloHospedes.getValueAt(selectedRowHospede, 0));
		                hospede.setNome((String) modeloHospedes.getValueAt(selectedRowHospede, 1));
		                hospede.setSobrenome((String) modeloHospedes.getValueAt(selectedRowHospede, 2));
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		                Object dataNascimentoObj = modeloHospedes.getValueAt(selectedRowHospede, 3);
		                java.util.Date dataNascimento;

		                if (dataNascimentoObj instanceof String) {
		                    try {
		                        dataNascimento = sdf.parse((String) dataNascimentoObj);
		                    } catch (ParseException e1) {
		                        dataNascimento = new java.util.Date(); 
		                        e1.printStackTrace();
		                    }
		                } else if (dataNascimentoObj instanceof Date) {
		                    dataNascimento = (Date) dataNascimentoObj;
		                } else {
		                    dataNascimento = new java.util.Date(); // default value
		                }

		                hospede.setDataNascimento(dataNascimento);
		                hospede.setNacionalidade((String) modeloHospedes.getValueAt(selectedRowHospede, 4));
		                hospede.setTelefone((String) modeloHospedes.getValueAt(selectedRowHospede, 5));
		                hospede.setIdReserva((Long) modeloHospedes.getValueAt(selectedRowHospede, 6));
		                hospedeController.update(hospede);
		            }
		            
		            JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso!");
		        }
		    }
		});


		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnDeletar = new JPanel();
		btnDeletar.setLayout(null);
		btnDeletar.setBackground(new Color(12, 138, 199));
		btnDeletar.setBounds(767, 508, 122, 35);
		btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnDeletar);
		btnDeletar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int selectedRowHospede = tbHospedes.getSelectedRow();
		        int selectedRowReserva = tbReservas.getSelectedRow();
		        
		        if (selectedRowHospede != -1) {
		            Long idHospede = (Long) modeloHospedes.getValueAt(selectedRowHospede, 0);
		            Hospede hospedeToDelete = new Hospede();
		            hospedeToDelete.setId(idHospede);
		            hospedeController.delete(hospedeToDelete);
		            modeloHospedes.removeRow(selectedRowHospede);  
		            
		            JOptionPane.showMessageDialog(null, "Hóspede deletado com sucesso!");

		        } else if (selectedRowReserva != -1) {
		        	Long idReserva = (Long) modelo.getValueAt(selectedRowReserva, 0);
		            
		            List<Hospede> hospedesAssociated = hospedeController.findByIdReserva(idReserva);
		            for (Hospede h : hospedesAssociated) {
		                h.setIdReserva(null); 
		                hospedeController.update(h); 
		            }

		            Reserva reservaToDelete = new Reserva();
		            reservaToDelete.setId(idReserva);
		            reservaController.delete(reservaToDelete);
		            modelo.removeRow(selectedRowReserva); 

		            JOptionPane.showMessageDialog(null, "Reserva deletada com sucesso!");

		        } else {
		            JOptionPane.showMessageDialog(null, "Por favor, selecione um registro para deletar.");
		        }
		    }
		});

		
		JLabel lblExcluir = new JLabel("DELETAR");
		lblExcluir.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluir.setForeground(Color.WHITE);
		lblExcluir.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblExcluir.setBounds(0, 0, 122, 35);
		btnDeletar.add(lblExcluir);
		setResizable(false);
	}
	
	private void preencherTabelaReservas(List<Reserva> reservas) {
	    modelo.setRowCount(0);
	    for (Reserva reserva : reservas) {
	        modelo.addRow(new Object[] {
	            reserva.getId(),  
	            reserva.getDataEntrada(),  
	            reserva.getDataSaida(),  
	            reserva.getValor(),
	            reserva.getFormaPagamento()
	        });
	    }
	}

	private void preencherTabelaHospedes(List<Hospede> hospedes) {
	    modeloHospedes.setRowCount(0);
	    for (Hospede hospede : hospedes) {
	        modeloHospedes.addRow(new Object[] {
	            hospede.getId(),  
	            hospede.getNome(),
	            hospede.getSobrenome(),
	            hospede.getDataNascimento(),
	            hospede.getNacionalidade(),
	            hospede.getTelefone(),
	            hospede.getIdReserva()  
	        });
	    }
	}
	
	//Código que permite movimentar a janela pela tela seguindo a posição de "x" e "y"	
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
