import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import org.w3c.dom.Text;

import Module.*;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Editor extends JFrame {
    //Attribute für das Programm
	private File datei;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor frame = new Editor();
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
	public Editor() {
		setTitle("TextEditor");
		Methoden text = new Methoden();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JFileChooser öffner = new JFileChooser(); //Fenster um Datei zu öffnen
		
		öffner.addChoosableFileFilter(new FileFilter() { // Filter für txt Dateien
		    public String getDescription() {
		        return "Text Dokumente (*.txt)";
		    }
		 
		    public boolean accept(File f) {
		        if (f.isDirectory()) {
		            return true;
		        } else {
		            return f.getName().toLowerCase().endsWith(".txt");
		        }
		    }
		});
		
		öffner.setFileFilter( new FileFilter() { // Alternative zur Dateifilter
	          public boolean accept( File f ) {
	            return f.isDirectory() ||
	                   f.getName().toLowerCase().endsWith(".txt");
	          }
	          public String getDescription() {
	            return "nur Textdateien";
	          }
	        } );
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 719, 405);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JMenuBar menuBar = new JMenuBar(); // erstellt Menuleiste
		menuBar.setBounds(0, 0, 41, 34);
		contentPane.add(menuBar);
		
		JMenu file = new JMenu("Datei"); // erstellt Menu
		menuBar.add(file);               // fügt Menu zu Menuleiste
		
		JMenuItem open = new JMenuItem("Öffnen"); // erstellt Untermenu
		
		file.add(open);                          // fügt Untermenu zu Menu
		file.addSeparator();                    // trennt Untermenu mit Strichen
		JMenuItem save = new JMenuItem("Speichern");
		
		file.add(save);
		file.addSeparator();
		JMenuItem exit = new JMenuItem("Beenden");
		
		file.add(exit);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(41, 0, 80, 34);
		contentPane.add(menuBar_1);
		
		JMenu edit = new JMenu("Bearbeiten");
		menuBar_1.add(edit);
		
		JMenuItem info = new JMenuItem("Info");
		
		edit.add(info);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(192, 10, 203, 13);
		contentPane.add(lblNewLabel);
		
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Schließt das Fesnter wenn man auf "Beenden" klickt
			}
		});
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				öffner.setDialogTitle("Öffne Text in...");
				öffner.showOpenDialog(null); // öffnet Fesnter zum Datei auswählen
				if(öffner.getSelectedFile() != null) {
					datei = öffner.getSelectedFile(); // Typ datei wird mit Textdatei geladen
					lblNewLabel.setText(öffner.getSelectedFile().getName());
					try {                             // Überprüfen ob die Datei in Ordnung ist
						
					Scanner lesen = new Scanner(datei);  // Objekt lesen vom Typ Scanner 
					while (lesen.hasNext()) { // Überprüfung ob eine nächste Zeile vorhanden ist damit solange die WhileSchleife läuft
						textArea.append(lesen.nextLine()+"\n"); // Einlesen der Datei Zeile für Zeile mit Zeilenumbruch zwischendurch
					}
					} catch(FileNotFoundException e1) {   // Ausgabe Fehlermeldung
						lblNewLabel.setText("Datei konnte nicht geöffnet werden");
					} finally {
						
					}
					
					
				}
			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				öffner.setDialogTitle("Speichere Text in...");
				if(datei == null)  öffner.setSelectedFile(new File("neu.txt")); // Vordefinierter Dateiname mit Endung wenn neue Datei angelegt wird
				öffner.showSaveDialog(null); // öffnet Fesnter zum Speichern der Datei
				if(öffner.getSelectedFile() != null) { //Wenn vorher keine leere Datei geladen wurde
					datei = öffner.getSelectedFile(); // Typ datei wird mit Textdatei geladen
					
					try {                             // Überprüfen ob Speichern möglich ist
					PrintWriter schreiben = new PrintWriter(datei);	// Objekt schreiben vom PrintWriter
					schreiben.println(textArea.getText()); // Inhalt von Textfeld wird geladen
					schreiben.close();                     // Schließt das Speichern ab, Stream wird geschlossen
					} catch(FileNotFoundException e1) {   // Ausgabe Fehlermeldung
						lblNewLabel.setText("Datei konnte nicht gespeichert werden werden");
					} finally {
						
					}
					
					
				}
				öffner.setSelectedFile(new File("")); // Namensvorschlag wird entfernt wenn Speichervorgang beendet ist
			}                                         // Erstellt dennoch eine Datei "neu.txt" wenn nicht gespeichert wird, muss noch behoben werden
		});
		
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Sehr einfacher und billiger Texteditor ^-^\nFalls Sie tatsächlich dafür bezahlt haben sollten,\nwurden Sie gelinkt ^_~\nAutor: Dizman, Yasir","INFO", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
	}
}
