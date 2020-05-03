package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class VistaGrafica extends JFrame implements InterVISTA {
	private static final long serialVersionUID = 1L;
	private JTable tbljugadores;
	private InterCONTROLADOR controlador;
	private Component btnIniciarJuego;
	private JMenuItem mntmAgregarJugador;
	private JMenuItem mntmEliminarJugador;
	private DadoGrafico dado;
	private JLabel lblDado1;
	private JLabel lblDado2;
	private JLabel lblDado3;
	private JLabel lblDado4;
	private JLabel lblDado5;
	private boolean[] dadosTirables;
	private int apuesta;
	private String ganador;
	private JLabel lblPozoAcumulado;
	private JLabel lblEstadoDelJuego;
	private JLabel lblJugador;
	private JTextField textField;
	private Object textPane;
	private JTextField textField_1;
	private int nrodetiro;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaGrafica frame = new VistaGrafica();
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
	@SuppressWarnings("serial")
	public VistaGrafica() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 480);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnJuego = new JMenu("Juego");
		menuBar.add(mnJuego);
		
		dado = new DadoGrafico();
		dadosTirables = new boolean[5];
		dadosTirables[0] = true;
		dadosTirables[1] = true;
		dadosTirables[2] = true;
		dadosTirables[3] = true;
		dadosTirables[4] = true;
		
		JMenuItem mntmVerReglas = new JMenuItem("Ver reglas");
		mnJuego.add(mntmVerReglas);
		mntmVerReglas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				mostrarReglas();
			}
		});
		
		JMenuItem mntmCargarPartida = new JMenuItem("Cargar partida");
		mnJuego.add(mntmCargarPartida);
		mntmCargarPartida.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				cargarpartida();
			}
		});
		
		
		JMenuItem mntmGuardarPartida = new JMenuItem("Guardar partida");
		mnJuego.add(mntmGuardarPartida);
		mnJuego.add(mntmCargarPartida);
		mntmGuardarPartida.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				guardarpartida();
			}
		});
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnJuego.add(mntmSalir);
		mntmSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				salir();
			}
		});

		
		JMenu mnJugadores = new JMenu("Jugadores");
		menuBar.add(mnJugadores);
		
		JMenuItem mntmAgregarJugador = new JMenuItem("Agregar Jugador");
		mntmAgregarJugador.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				agregarJugador();
			}
			
		});
		mnJugadores.add(mntmAgregarJugador);

	
			
		
			
		
		JMenuItem mntmEliminarJugador = new JMenuItem("Eliminar Jugador");
		mnJugadores.add(mntmEliminarJugador);
		mntmEliminarJugador.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				eliminarJugador();
			}
		});
		mnJugadores.add(mntmEliminarJugador);
		
		
		JMenu mnPersistencia = new JMenu("Persistencia");
		menuBar.add(mnPersistencia);
		
		JMenuItem mntmGenerarFileRanking = new JMenuItem("Generar file ranking");
		mnPersistencia.add(mntmGenerarFileRanking);
		mntmGenerarFileRanking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				generarpersistenciaranking();
			}			
		});
		
		JMenuItem mntmGenerarFilePartida = new JMenuItem("Generar file partida");
		mnPersistencia.add(mntmGenerarFilePartida);
		mntmGenerarFilePartida.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				generarpersistenciapartida();
			}			
		});	
			
	
		
		
		JMenu mnRanking = new JMenu("Ranking");
		menuBar.add(mnRanking);
		
		JMenuItem mntmVerRanking = new JMenuItem("Ver ranking");
		mnRanking.add(mntmVerRanking);
		mntmVerRanking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				veranking();
			}			
		});	
		
		
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnEstablecerOrden = new JButton("ESTABLECER ORDEN");
		toolBar.add(btnEstablecerOrden);
		btnEstablecerOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				establecerorden();
			}
		});
		
		JButton btnIniciarJuego = new JButton("INICIAR JUEGO");
		toolBar.add(btnIniciarJuego);
		btnIniciarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarJuego();
			}
		});
		
		
		JButton btnHayPozo = new JButton("HAY POZO?");
		toolBar.add(btnHayPozo);
		btnHayPozo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				haypozo();
			}
		});
		
		JButton btnRealizarTiro = new JButton("REALIZAR TIRO");
		toolBar.add(btnRealizarTiro);
		btnRealizarTiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		try {
			tirar();
		} catch (Exception e1) {
			System.out.println("Error en el tirado del Dado");
			e1.printStackTrace();
		}
	}
});
		
		JButton btnFinalizarTurno = new JButton("FINALIZAR TURNO");
		toolBar.add(btnFinalizarTurno);
		btnFinalizarTurno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finalizarTurno();
			}
		});
		
		JButton btnTerminarPartida = new JButton("TERMINAR PARTIDA");
		toolBar.add(btnTerminarPartida);
		btnTerminarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finalizarpartida();
			}
		});
		
		
		JButton btnObtenerGanador = new JButton("OBTENER GANADOR");
		toolBar.add(btnObtenerGanador);
		btnObtenerGanador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ganador(ganador,apuesta);
			}
		});
		
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.EAST);
	    panel_1.setLayout(new MigLayout("", "[grow]", "[][grow]"));
	
	
	JLabel lblJugadores = new JLabel("Jugadores");
	panel_1.add(lblJugadores, "cell 0 0,grow");
	
	tbljugadores = new JTable();
	tbljugadores = new JTable();
	tbljugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tbljugadores.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
	tbljugadores.setModel(new DefaultTableModel(
		new Object[][] {
		},
		new String[] {
			"ID", "Sigla", "Puntos"
		}
	) {
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {
			Integer.class, String.class, Long.class
		};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
	});
	tbljugadores.getColumnModel().getColumn(0).setResizable(false);
	tbljugadores.getColumnModel().getColumn(0).setPreferredWidth(28);
	tbljugadores.getColumnModel().getColumn(0).setMinWidth(28);
	tbljugadores.getColumnModel().getColumn(0).setMaxWidth(28);
	tbljugadores.getColumnModel().getColumn(1).setResizable(false);
	tbljugadores.getColumnModel().getColumn(1).setMinWidth(75);
	tbljugadores.getColumnModel().getColumn(1).setMaxWidth(75);
	tbljugadores.getColumnModel().getColumn(2).setPreferredWidth(50);
	tbljugadores.getColumnModel().getColumn(2).setMinWidth(50);
	panel_1.add(tbljugadores, "cell 0 1,grow");
	
	
	JPanel Mesa = new JPanel();
	getContentPane().add(Mesa, BorderLayout.CENTER);
	Mesa.setLayout(new MigLayout("", "[86][86px,grow][86px][86px,grow][86px][86px,grow]", "[26px][26px][26px,grow][][][grow][26.00,grow][][][26px][][][grow][grow][][grow][][][][grow][grow][26px]"));//[26px][26px][26px][26px][26px][26px][26px][26px]"));
	JPanel panel_Dado1 = new JPanel();
	Mesa.add(panel_Dado1, "cell 1 1,alignx center,aligny center");
	panel_Dado1.setLayout(new GridLayout(0, 1, 0, 0));
	
	lblDado1 = new JLabel("");
	panel_Dado1.add(lblDado1);
	lblDado1.setIcon(dado.pngDadoR(1));
			
	JPanel panel_Dado2 = new JPanel();
	Mesa.add(panel_Dado2, "cell 3 1,alignx center,aligny center");
	panel_Dado2.setLayout(new GridLayout(0, 1, 0, 0));
	
	lblDado2 = new JLabel("");
	panel_Dado2.add(lblDado2);
	lblDado2.setIcon(dado.pngDadoR(2));
	
	JPanel panel_Dado3 = new JPanel();
	Mesa.add(panel_Dado3, "cell 1 4,alignx center,aligny center");
	panel_Dado3.setLayout(new GridLayout(0, 1, 0, 0));
	
	lblDado3 = new JLabel("");
	panel_Dado3.add(lblDado3);
	lblDado3.setIcon(dado.pngDadoR(3));
	
	JPanel panel_dado4 = new JPanel();
	Mesa.add(panel_dado4, "cell 3 4,alignx center,aligny center");
	panel_dado4.setLayout(new GridLayout(0, 1, 0, 0));
	
	lblDado4 = new JLabel("");
	panel_dado4.add(lblDado4);
	lblDado4.setIcon(dado.pngDadoR(4));
	
	lblPozoAcumulado = new JLabel("POZO ACUMULADO: ");
	Mesa.add(lblPozoAcumulado, "cell 5 4,growx");
	
	JPanel panel_dado5 = new JPanel();
	Mesa.add(panel_dado5, "flowx,cell 1 6,alignx center,aligny center");
	panel_dado5.setLayout(new GridLayout(0, 1, 0, 0));
	
	lblDado5 = new JLabel("");
	panel_dado5.add(lblDado5);
	lblDado5.setIcon(dado.pngDadoR(5));
	
	textField_1 = new JTextField();
	Mesa.add(textField_1, "cell 3 5 3 3,growx");
	textField_1.setColumns(10);
	

	JPanel panel_Jugador = new JPanel();
	Mesa.add(panel_Jugador, "cell 1 8,alignx center,aligny center");
	
	JPanel panel_estado = new JPanel();
	Mesa.add(panel_estado, "cell 3 8,alignx center,aligny center");
	panel_estado.setLayout(new GridLayout(0, 1, 0, 0));
	
	JPanel panel_Puntaje = new JPanel();
	Mesa.add(panel_Puntaje, "cell 1 10,alignx center,aligny center");
	panel_Puntaje.setLayout(new GridLayout(0, 1, 0, 0));
	
	lblJugador = new JLabel("Jugador actual: ninguno");
	Mesa.add(lblJugador, "cell 1 11");
	
	lblEstadoDelJuego = new JLabel("ESTADO DEL JUEGO: Detenido");
	Mesa.add(lblEstadoDelJuego, "cell 3 11");
	
	textField= new JTextField();
	Mesa.add(textField, "cell 1 16 3 1,growx");
	textField.setColumns(10);
	
	
	}
	

protected void veranking() {
		textField_1.setText("RANKING:" + " " + this.controlador.veranking());
		
	}

protected void guardarpartida() {
		this.controlador.guardarpartida();
		JOptionPane.showConfirmDialog(null,"LA PARTIDA ACTUAL HA SIDO GUARDADA","PARTIDA",JOptionPane.OK_OPTION);
		this.finalizarpartida();
		JOptionPane.showConfirmDialog(null,"EL JUEGO SE CERRARA","FIN DE JUEGO",JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}

protected void cargarpartida() {
		apuesta=this.controlador.cargarpartida();
		this.lblPozoAcumulado.setText("POZO ACUMULADO:" + " " + String.valueOf(apuesta));
		int[] puntajes=this.controlador.getPuntajes();
		ArrayList<String> jugadores = this.controlador.getJugadores();
		DefaultTableModel m = (DefaultTableModel) tbljugadores.getModel();
		m.setRowCount(0);
		for (int i = 0 ; i < jugadores.size(); i++) {
			int j = i + 1;
			m.addRow(new Object[] {j,jugadores.get(i),puntajes[i]});
		}
		this.controlador.iniciarJuego();
	}

protected void generarpersistenciapartida() {
	int op = JOptionPane.showConfirmDialog(null,"DESEA GENERAR EL ARCHIVO DE PARTIDA?","PARTIDA",JOptionPane.YES_NO_CANCEL_OPTION);
	 if (op==JOptionPane.YES_OPTION){
		 this.controlador.generarpersistenciapartida();
		 JOptionPane.showConfirmDialog(null,"EL ARCHIVO HA SIDO GENERADO EXITOSAMENTE","PARTIDA",JOptionPane.WARNING_MESSAGE);
	 }
		
	}

public void generarpersistenciaranking() {
		int op = JOptionPane.showConfirmDialog(null,"DESEA GENERAR EL ARCHIVO DE RANKING?","RANKING",JOptionPane.YES_NO_CANCEL_OPTION);
		 if (op==JOptionPane.YES_OPTION){
			 this.controlador.generarpersistenciaranking();
			 JOptionPane.showConfirmDialog(null,"EL ARCHIVO HA SIDO GENERADO EXITOSAMENTE","RANKING",JOptionPane.WARNING_MESSAGE);
		 }
	}

protected void establecerorden() {
	   this.controlador.establecerorden();
	   JOptionPane.showConfirmDialog(null,"EL ORDEN HA SIDO ESTABLECIDO CORRECTAMENTE","ORDEN",JOptionPane.WARNING_MESSAGE);
		
	}

protected void finalizarpartida() {
   this.lblEstadoDelJuego.setText("ESTADO DEL JUEGO:" + " " + "finalizado");		
}
		


	protected void tirar() {
		nrodetiro=nrodetiro+1;
		this.controlador.tirar();
		int op = JOptionPane.showConfirmDialog(null,"DESEA TIRAR NUEVAMENTE?","TIRAR?",JOptionPane.YES_NO_CANCEL_OPTION);
		 int op2 = 0;
		 if (op==JOptionPane.YES_OPTION){
			  op2= JOptionPane.showConfirmDialog(null,"DESEA TIRAR TODOS LOS DADOS?","TIRAR",JOptionPane.YES_NO_CANCEL_OPTION);
		 }
		if (op==JOptionPane.NO_OPTION){
			nrodetiro=0;
			this.finalizarTurno();
		}
		if (op2==JOptionPane.YES_OPTION){
			if (nrodetiro>2){
				JOptionPane.showConfirmDialog(null,"NO TIENE MAS TIROS EN ESTE TURNO","ATENCION!!!",JOptionPane.WARNING_MESSAGE);
				nrodetiro=0;
				this.finalizarTurno();
			}
			if (nrodetiro==1){
				JOptionPane.showConfirmDialog(null,"LE QUEDA UN SOLO TIRO DISPONIBLE","ATENCION!!!",JOptionPane.WARNING_MESSAGE);
			}
			nrodetiro=nrodetiro+1;
			this.controlador.tirar();
		}
		if (op2==JOptionPane.NO_OPTION){
			if (nrodetiro>2){
				JOptionPane.showConfirmDialog(null,"NO TIENE MAS TIROS EN ESTE TURNO","ATENCION!!!",JOptionPane.WARNING_MESSAGE);
				nrodetiro=0;
				this.finalizarTurno();
			}
			if (nrodetiro==1){
				JOptionPane.showConfirmDialog(null,"LE QUEDA UN SOLO TIRO DISPONIBLE","ATENCION!!!",JOptionPane.WARNING_MESSAGE);
			}
			String s = JOptionPane.showInputDialog("Ingrese los dados que quiere tirar(sin espacios y ascendentemente)");
			nrodetiro=nrodetiro+1;
			this.controlador.getRemanente(s);
		}
		
	}

	protected void iniciarJuego() {
		this.controlador.iniciarJuego();
		nrodetiro=0;
	}

	protected void eliminarJugador() {
		String s = JOptionPane.showInputDialog("Ingrese el nombre jugador a eliminar");
		if (s == null){
			
		}else if (s.equals("")){
			
		}else{
			this.controlador.eliminarJugador(s);
		}
		
	}

	protected void agregarJugador() {
			String s = JOptionPane.showInputDialog("Ingrese el nombre del nuevo jugador");
			     this.controlador.agregarJugador(s);
			
		}		
		
	
	@Override
	public void mostrarcategorias() { //no se usa en esta vista
		
		
	}
    
	public void setControlador(InterCONTROLADOR c) {
		this.controlador = c;	
	}

	@Override
	public void nuevoJugador(ArrayList<String> jugadores) {
		DefaultTableModel m = (DefaultTableModel) tbljugadores.getModel();
		m.setRowCount(0);
		for (int i = 0 ; i < jugadores.size(); i++) {
			int j = i + 1;
			m.addRow(new Object[] {j,jugadores.get(i),""});
		}
	}
		
	

	@Override
	public void cambioJugador(String n) {
		lblJugador.setText("Jugador actual: " + n);
	}

		
	

	@Override
	public void nuevoPuntajeActual(int p) {
	}
		
	

	@Override
	public void jugadorEliminado(ArrayList<String> j, String n) {
		
		
	}


	@Override
	public void dadosTirados(int[] d){
		lblDado1.setIcon(dado.pngDadoR((float) d[0]));
		lblDado2.setIcon(dado.pngDadoR((float) d[1]));
		lblDado3.setIcon(dado.pngDadoR((float) d[2]));
		lblDado4.setIcon(dado.pngDadoR((float) d[3]));
		lblDado5.setIcon(dado.pngDadoR((float) d[4]));
	}

		
	
	@Override
	public void nuevosPuntajes(int[] puntajes) {
		ArrayList<String> jugadores = this.controlador.getJugadores();
		DefaultTableModel m = (DefaultTableModel) tbljugadores.getModel();
		m.setRowCount(0);
		for (int i = 0 ; i < jugadores.size(); i++) {
			int j = i + 1;
			m.addRow(new Object[] {j,jugadores.get(i),puntajes[i]});
		}
		
	}

	@Override
	public void juegoIniciado() {
		this.lblEstadoDelJuego.setText("ESTADO DEL JUEGO" + "  " + "iniciado");
	 
	
	}

	@Override
	public void juegoNoIniciado() {
		// TODO Auto-generated method stub
		
	}

	
				
	
	
	protected void finalizarTurno() {
			int op = JOptionPane.showConfirmDialog(null,"DESEA FINALIZAR SU TURNO?","FIN DE TURNO", JOptionPane.YES_NO_OPTION);
			if (op == JOptionPane.YES_OPTION){
				String s = JOptionPane.showInputDialog("Ingrese la categoria de ptos");
				this.controlador.getCategoria(s);
				this.controlador.finalizarTurno(true);
				}
			if (op == JOptionPane.NO_OPTION){
				this.controlador.finalizarTurno(false);
			}
	}
	
	

	@Override
	public void dadosReiniciados(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jugadorYaExiste() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void puedeFinalizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avisoultimotiro() {    
		// TODO Auto-generated method stub
		
	}

	@Override
	public void haypozo() {
		ArrayList<String> cantjugadores;
		cantjugadores= this.controlador.getJugadores();
		int op = JOptionPane.showConfirmDialog(null, "¿Hay apuesta para el juego?", "Pozo", JOptionPane.YES_NO_CANCEL_OPTION);
		if(op == JOptionPane.YES_OPTION){
			this.controlador.determinarPozo(true);
			String s = JOptionPane.showInputDialog("Ingrese el valor apuesta mínima");
			apuesta = Integer.parseInt(s)*(cantjugadores.size());
			this.lblPozoAcumulado.setText("POZO ACUMULADO:" + " " + String.valueOf(apuesta));
		}
		if (op == JOptionPane.NO_OPTION){
		     this.controlador.determinarPozo(false);
		     this.lblPozoAcumulado.setText("POZO ACUMULADO:" + " " + "0");
		}
		
	}

	protected void salir() {
		JOptionPane.showConfirmDialog(null,"CREADO POR LUCIANO COURTADE","HASTA LUEGO",JOptionPane.WARNING_MESSAGE);
	    System.exit(0);
	}

	protected void mostrarReglas() {
		VistaReglas vr = new VistaReglas();
		vr.setVisible(true);
	}

	@Override
	public void ganador(String n, int t) {
			n=this.controlador.getGanadorP(ganador, apuesta);
		  textField.setText("GANADOR:" +" " + n);
		  			
	}


	
}
