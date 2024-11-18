package Ventanas;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ventana1 extends JFrame{
    private String palabraSeleccionada;
    private StringBuilder palabraOculta;
    private int intentosRestantes;
    private char[] letrasCorrectas;
    private JPanel panelDibujo;
    private JLabel labelPalabra, labelIntentos;
    private JTextField letraInput;
    private JButton btnJugarDeNuevo, btnSalir;
    
    private static final String[] PALABRAS = {"ALMOHADA", "CASTILLO", "ELEFANTE", "NUBE", "LABERINTO", "MANZANA", 
        "HELICOPTERO", "PALMERA", "FUEGO", "BIBLIOTECA", "ESPADA","VOLCAN", "AURORA", "TORMENTA", "RELIQUIA"};

    public Ventana1() {
        setTitle("El Ahorcado");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        labelPalabra = new JLabel("", JLabel.CENTER);
        labelPalabra.setFont(new Font("Calibri", Font.PLAIN, 24));
        panel1.add(labelPalabra);

        labelIntentos = new JLabel("Intentos restantes: 6", JLabel.CENTER);
        labelIntentos.setFont(new Font("Calibri", Font.PLAIN, 18));
        panel1.add(labelIntentos);

        add(panel1, BorderLayout.NORTH);

        panelDibujo = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarHorca(g);
                dibujarPersona(g);
            }
        };
        add(panelDibujo, BorderLayout.CENTER);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        letraInput = new JTextField(3);
        panel2.add(letraInput);

        JButton btnIngresarLetra = new JButton("Adivinar");
        btnIngresarLetra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adivinarLetra(letraInput.getText().toUpperCase().charAt(0));
                letraInput.setText("");
            }
        });
        panel2.add(btnIngresarLetra);

        btnJugarDeNuevo = new JButton("Jugar de nuevo");
        btnJugarDeNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        panel2.add(btnJugarDeNuevo);

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel2.add(btnSalir);
        add(panel2, BorderLayout.SOUTH);
        iniciarJuego();
    }
    private void iniciarJuego() {
        Random rand = new Random();
        palabraSeleccionada = PALABRAS[rand.nextInt(PALABRAS.length)];
        palabraOculta = new StringBuilder("_".repeat(palabraSeleccionada.length()));
        letrasCorrectas = new char[palabraSeleccionada.length()];
        intentosRestantes = 6;
        labelPalabra.setText(palabraOculta.toString());
        labelIntentos.setText("Intentos restantes: " + intentosRestantes);
        panelDibujo.repaint();
    }

    private void adivinarLetra(char letra) {
        boolean letraCorrecta = false;
        for (int i = 0; i < palabraSeleccionada.length(); i++) {
            if (palabraSeleccionada.charAt(i) == letra && palabraOculta.charAt(i) == '_') {
                palabraOculta.setCharAt(i, letra);
                letraCorrecta = true;
            }
        }
        if (letraCorrecta) {
            labelPalabra.setText(palabraOculta.toString());
            
            if(palabraOculta.toString().equals(palabraSeleccionada)) {
                JOptionPane.showMessageDialog(this, "¡Felicidades, ganaste!");
                iniciarJuego();
            }
        }else {
            intentosRestantes--;
            labelIntentos.setText("Intentos restantes: " + intentosRestantes);
            if (intentosRestantes == 0) {
                JOptionPane.showMessageDialog(this, "Perdiste el juego. La palabra era: " + palabraSeleccionada);
                iniciarJuego();
            }
        }
        panelDibujo.repaint();
    }
    private void dibujarHorca(Graphics g) {
        g.setColor(Color.BLACK);
        // Horca
        g.drawLine(50, 300, 150, 300); 
        g.drawLine(100, 300, 100, 50); 
        g.drawLine(100, 50, 200, 50); 
        g.drawLine(200, 50, 200, 80);
    }
    private void dibujarPersona(Graphics g) {
        if (intentosRestantes < 6) { g.drawOval(175, 80, 50, 50); } // Cabeza
        if (intentosRestantes < 5) { g.drawLine(200, 130, 200, 190); } // Cuerpo
        if (intentosRestantes < 4) { g.drawLine(200, 132, 165, 185); } // Brazo izQ
        if (intentosRestantes < 3) { g.drawLine(200, 132, 235, 185); } // Brazo der
        if (intentosRestantes < 2) { g.drawLine(200, 190, 180, 260); } // Pierna izQ
        if (intentosRestantes < 1) { g.drawLine(200, 190, 220, 260); } // Pierna der
    }
}
