package Vista;

	import java.awt.BorderLayout;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;
	import java.awt.GridLayout;
	import net.miginfocom.swing.MigLayout;
	import javax.swing.JButton;
	import javax.swing.JLabel;
	import javax.swing.JTextArea;
	import java.awt.Font;
	import java.awt.Color;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;

	@SuppressWarnings("serial")
	public class VistaReglas extends JFrame {

		private JPanel contentPane;
		private String[] reglas;
		private int i;
		private JTextArea textArea;
		private JButton btnAnterior;
		private JButton btnSiguiente;
		
		

		/**
		 * Create the frame.
		 */
		public VistaReglas() {
			i = 0;
			reglas = new String[5];
			reglas[0] = "Bienvenidos a Yacht, conocido como 'Yahtzee'.\n";
			reglas[1] = "Los jugadores por turno tiran cinco dados,\n"
					+ "teniendo como máximo tres tiros disponibles.\n"
			        + "Puede jugarse por dinero con un pozo común.\n";
			reglas[2] = "Los puntos se calculan según las sig. categorías: \n"
					+ "YAHTZEE= 50ptos para cinco dados iguales\n"
					+ "ESCALERA MAYOR= 30ptos para cinco\n"
					+ "dados ascendentes.\n"
					+ "ESCALERA MENOR= 15ptos para cuatro\n"
					+ "dados ascendentes.\n"
					+ "CUATRO IGUALES= los ptos son proporcionales\n"
					+ "a los valores.\n"
					+ "FULL=tres iguales y un par.\n"
			        + "ELECCION= se considera el valor de los cinco dados.\n";
			reglas[3] = "Al igual que en la generala, la estrategia consiste en\n"
			           +"lograr puntajes altos haciendo los juegos mayores.\n"
					   +" \n"
			           +" \n"
					   +"MUCHA SUERTE Y A DISFRUTAR!!!\n";
			
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 554, 341);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_reglas = new JPanel();
			contentPane.add(panel_reglas, BorderLayout.CENTER);
			panel_reglas.setLayout(null);
			
			textArea = new JTextArea();
			textArea.setBackground(Color.WHITE);
			textArea.setForeground(Color.BLUE);
			textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			textArea.setBounds(6, 6, 454, 273);
			panel_reglas.add(textArea);
			textArea.setText(reglas[0]);
			textArea.setEditable(false);
			
			JPanel panel_adelanteAtras = new JPanel();
			contentPane.add(panel_adelanteAtras, BorderLayout.SOUTH);
			panel_adelanteAtras.setLayout(new GridLayout(1, 0, 0, 0));
			
			btnAnterior = new JButton("Anterior");
			btnAnterior.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					anterior();
				}
			});
			panel_adelanteAtras.add(btnAnterior);
			btnAnterior.setEnabled(false);
			
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					siguiente();
				}
			});
			panel_adelanteAtras.add(btnSiguiente);
			
			this.setResizable(false);
		}

		protected void anterior() {
			this.btnSiguiente.setEnabled(true);
			i--;
			this.textArea.setText(this.reglas[i]);
			if (i == 0){
				this.btnAnterior.setEnabled(false);
			}
		}

		protected void siguiente() {
			this.btnAnterior.setEnabled(true);
			i++;
			this.textArea.setText(this.reglas[i]);
			if (i == 4){
				this.btnSiguiente.setEnabled(false);
			}
		}

}
