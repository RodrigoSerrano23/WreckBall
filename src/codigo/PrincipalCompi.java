/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

/**
 *
 * @author Rodrigo
 */
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.Lexer;
import java_cup.runtime.Symbol;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class PrincipalCompi extends javax.swing.JFrame {

    JFileChooser seleccionado = new JFileChooser();
    File archivo;
    byte[] bytesImg;
    GestionA gestion = new GestionA();

    DefaultTableModel modelo;

    DefaultStyledDocument doc;
    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    final AttributeSet Black = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    final AttributeSet blue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.blue);
    final AttributeSet green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.green);
    final AttributeSet yellow = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.yellow);
    final AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.orange);

    private String dirNuevo = "";
    private String nomNuevo = "";

    public static String ci = "";
    public static String sentencia[] = new String[36];
    public static String declaracion;
    public static String ifs;
    public static String elses;
    public static String s_arit;
    public static String s_bool;
    public static String whiles;
    public static String dowhiles;
    public static String fors;
    public static String s_for;
    public static String d_for;
    public static Object value;
    public static Object valueb;

    public static int temp;
    public static int tempb;
    public static int status;
    public static int choice;
    public static int loop;

    public static ArrayList<Simbolo> tabla_simbolos = new ArrayList<>();

    public static String[] error;
    public static ArrayList<String> err = new ArrayList<>();
    public static ArrayList<String> expresiones = new ArrayList<>();
    public static String gramatica;

    public PrincipalCompi() {
        //tabla_simbolos
        //err.
        err.clear();
        Object t = new Object();

        doc = new DefaultStyledDocument() {

            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = jTextPane1.getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {

                        if (text.substring(wordL, wordR).matches("(\\W)*(true|false|start)")) {
                            setCharacterAttributes(wordL, wordR - wordL, orange, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(if|else|do|while|for|stopLoop)")) {
                            setCharacterAttributes(wordL, wordR - wordL, blue, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(int|bool)")) {
                            setCharacterAttributes(wordL, wordR - wordL, green, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(spinCraneLeft|spinCraneRight|moveFowardCrane|moveBackCrane|spinBallLeft|spinBallRight|hitToTheLeft|hitToTheRight|waitTime)")) {
                            setCharacterAttributes(wordL, wordR - wordL, red, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(\\Q) (\\W)* (\\E)")) {
                            setCharacterAttributes(wordL, wordR - wordL, yellow, false);
                        } else {
                            setCharacterAttributes(wordL, wordR - wordL, Black, false);
                        }

                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                String text = jTextPane1.getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(true|false|start)")) {
                    setCharacterAttributes(before, after - before, orange, false);
                } else if (text.substring(before, after).matches("(\\W)*(if|else|do|while|for|stopLoop)")) {
                    setCharacterAttributes(before, after - before, blue, false);
                } else if (text.substring(before, after).matches("(\\W)*(int|bool)")) {
                    setCharacterAttributes(before, after - before, green, false);
                } else if (text.substring(before, after).matches("(\\W)*(spinCraneLeft|spinCraneRight|moveFowardCrane|moveBackCrane|spinBallLeft|spinBallRight|hitToTheLeft|hitToTheRight|waitTime)")) {
                    setCharacterAttributes(before, after - before, red, false);
//                } else if (text.substring(before, after).matches("(\\W)*(>|<)")) {
//                    setCharacterAttributes(before, after - before, yellow, false);
                } else {
                    setCharacterAttributes(before, after - before, Black, false);
                }
            }
        };
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Compilador - WreckBall");
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    private void analizarLexico() throws IOException {
        String expr = (String) jTextPane1.getText();
        codigo.Lexer lexer = new codigo.Lexer(new StringReader(expr));

        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                //output.setText(resultado);
                return;
            }
            switch (token) {
                case Linea:
                    //cont++;
                    //resultado += "LINEA " + cont + "\n";
                    break;
                case Int:
                    modelo.addRow(new Object[]{"<Tipo de dato entero>", lexer.lexeme});
                    break;
                case Bool:
                    modelo.addRow(new Object[]{"<Tipo de dato booleano>", lexer.lexeme});
                    break;
                case If:
                    modelo.addRow(new Object[]{"<Reservada if>", lexer.lexeme});
                    break;
                case Else:
                    modelo.addRow(new Object[]{"<Reservada else>", lexer.lexeme});
                    break;
                case Do:
                    modelo.addRow(new Object[]{"<Reservada do>", lexer.lexeme});
                    break;
                case While:
                    modelo.addRow(new Object[]{"<Reservada while>", lexer.lexeme});
                    break;
                case For:
                    modelo.addRow(new Object[]{"<Reservada for>", lexer.lexeme});
                    break;
                case StopLoop:
                    modelo.addRow(new Object[]{"<Reservada stopLoop>", lexer.lexeme});
                    break;
                case SpinCraneLeft:
                    modelo.addRow(new Object[]{"<Método spinCraneLeft>", lexer.lexeme});
                    break;
                case SpinCraneRight:
                    modelo.addRow(new Object[]{"<Método spinCraneRight>", lexer.lexeme});
                    break;
                case MoveFowardCrane:
                    modelo.addRow(new Object[]{"<Método moveFowardCrane>", lexer.lexeme});
                    break;
                case MoveBackCrane:
                    modelo.addRow(new Object[]{"<Método moveBackCrane>", lexer.lexeme});
                    break;
                case SpinBallLeft:
                    modelo.addRow(new Object[]{"<Método spinBallLeft>", lexer.lexeme});
                    break;
                case SpinBallRight:
                    modelo.addRow(new Object[]{"<Método spinBallRight>", lexer.lexeme});
                    break;
                case HitToTheLeft:
                    modelo.addRow(new Object[]{"<Método hitToTheLeft>", lexer.lexeme});
                    break;
                case HitToTheRight:
                    modelo.addRow(new Object[]{"<Método hitToTheRight>", lexer.lexeme});
                    break;
                case WaitTime:
                    modelo.addRow(new Object[]{"<Método WaitTime>", lexer.lexeme});
                    break;
                case Igual:
                    modelo.addRow(new Object[]{"<Operador igual>", lexer.lexeme});
                    break;
                case Suma:
                    modelo.addRow(new Object[]{"<Operador suma>", lexer.lexeme});
                    break;
                case Resta:
                    modelo.addRow(new Object[]{"<Operador resta>", lexer.lexeme});
                    break;
                case Multiplicacion:
                    modelo.addRow(new Object[]{"<Operador multiplicación>", lexer.lexeme});
                    break;
                case Division:
                    modelo.addRow(new Object[]{"<Operador división>", lexer.lexeme});
                    break;
                case Op_logico:
                    modelo.addRow(new Object[]{"<Operador lógico>", lexer.lexeme});
                    break;
                case Op_incremento:
                    modelo.addRow(new Object[]{"<Operador incremento/decremento>", lexer.lexeme});
                    break;
                case Op_relacional:
                    modelo.addRow(new Object[]{"<Operador relacional>", lexer.lexeme});
                    break;
                case Op_atribucion:
                    modelo.addRow(new Object[]{"<Operador atribución>", lexer.lexeme});
                    break;
                case Op_booleano:
                    modelo.addRow(new Object[]{"<Operador booleano>", lexer.lexeme});
                    break;
                case Parentesis_a:
                    modelo.addRow(new Object[]{"<Paréntesis de apertura>", lexer.lexeme});
                    break;
                case Parentesis_c:
                    modelo.addRow(new Object[]{"<Paréntesis de cierre>", lexer.lexeme});
                    break;
                case Llave_a:
                    modelo.addRow(new Object[]{"<Llave de apertura>", lexer.lexeme});
                    break;
                case Llave_c:
                    modelo.addRow(new Object[]{"<Llave de cierre>", lexer.lexeme});
                    break;
                case Corchete_a:
                    modelo.addRow(new Object[]{"<Corchete de apertura>", lexer.lexeme});
                    break;
                case Corchete_c:
                    modelo.addRow(new Object[]{"<Corchete de cierre>", lexer.lexeme});
                    break;
                case Start:
                    modelo.addRow(new Object[]{"<Reservada start>", lexer.lexeme});
                    break;
                case P_coma:
                    modelo.addRow(new Object[]{"<Punto y coma>", lexer.lexeme});
                    break;
                case Identificador:
                    modelo.addRow(new Object[]{"<Identificador>", lexer.lexeme});
                    break;
                case Numero:
                    modelo.addRow(new Object[]{"<Número>", lexer.lexeme});
                    break;
                case ERROR:
                    String ST = jTextPane1.getText();
                    Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
                     {
                        try {
                            s.parse();
                        } catch (Exception ex) {
                            Logger.getLogger(PrincipalCompi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Symbol sym = s.getS();
                    modelo.addRow(new Object[]{"<Símbolo no definido>", sym.value});
                    err.add("Error léxico linea:" + (sym.right + 1) + "; el símbolo " + sym.value + " no ha sido definido en el lenguaje");
                    break;
                default:
                    modelo.addRow(new Object[]{null, lexer.lexeme});
                    break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane(doc);
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Output:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextPane1.setText("start(){\n\n}");
        TextLineNumber lineas=new TextLineNumber(jTextPane1);
        jScrollPane1.setRowHeaderView(lineas);
        jScrollPane1.setViewportView(jTextPane1);

        jTable1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componente Lexico", "Lexema"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        modelo = (DefaultTableModel)jTable1.getModel();
        jTable1.setModel(modelo);
        jScrollPane4.setViewportView(jTable1);

        jTabbedPane1.addTab("Tabla de Tokens", jScrollPane4);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jTabbedPane1.addTab("Código Interm.", jScrollPane2);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane5.setViewportView(jTextArea2);

        jTabbedPane1.addTab("Código Optimizado", jScrollPane5);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea3.setRows(5);
        jScrollPane7.setViewportView(jTextArea3);

        jTabbedPane1.addTab("Código Objeto", jScrollPane7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane3.setViewportView(jPanel1);

        jList2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {  };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(jList2);

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Guardar como ...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Accion");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem3.setText("Compilar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem5.setText("Obtener lms");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Mostrar");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4.setText("Gramática");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem6.setText("Arbol");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        //Nuevo nv=new Nuevo(this, true,this);
        //nv.show();
        if (seleccionado.showDialog(null, "Guardar") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionado.getSelectedFile();
            if (archivo.getName().endsWith("txt")) {
                String contenido = jTextPane1.getText();
                String respuesta = gestion.GuardarATexto(archivo, contenido);
                if (respuesta != null) {
                    JOptionPane.showMessageDialog(null, respuesta);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar codigo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El texto se debe guardar en un formato de texto.");
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public boolean Guardar() {
        FileOutputStream out;
        PrintStream p;

        try {
            out = new FileOutputStream(dirNuevo);
            p = new PrintStream(out);
            p.println(this.jTextPane1.getText());
            p.close();
            this.setTitle(this.getTitle().replace("*", ""));
            return true;
        } catch (Exception e) {
            //System.err.println("BarbonC - No se puede guardar el archivo");
            return false;
        }
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        tabla_simbolos.clear();
        err.clear();
        expresiones.clear();
        value = null;
        valueb = null;
        gramatica = "";

        for (int i = 0; i <= 35; i++) {
            sentencia[i] = "";
        }
        declaracion = "";
        ifs = "";
        elses = "";
        s_arit = "";
        s_bool = "";
        whiles = "";
        dowhiles = "";
        fors = "";
        s_for = "";
        d_for = "";
        status = 0;
        temp = 0;
        choice = 0;
        loop = 0;
        String resultado;
        int n = jTable1.getRowCount();
        while (n > 0) {
            modelo.removeRow(n - 1);
            n--;
        }
        try {
            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalCompi.class.getName()).log(Level.SEVERE, null, ex);
        }
        String ST = jTextPane1.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
        try {
            s.parse();
            resultado = "\nAnálisis realizado correctamente";
        } catch (Exception ex) {
            Symbol sym = s.getS();
            resultado = "\nError de sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"";
            //err.add(resultado);
            //err.add("Error sintáctico linea:"+(sym.right+1)+"; sentencia incomprensible. Imposible seguir analizando");
        }
        if (err.isEmpty()) {
            error = new String[]{"Análisis realizado correctamente"};
        } else {
            ci = "";
            error = new String[err.size()];
            for (int i = 0; i < err.size(); i++) {
                error[i] = err.get(i);
            }
        }
        gramatica = "terminal Linea, Int, Bool, If, Else, Do, While, For, StopLoop,\n"
                + "    SpinCraneLeft, SpinCraneRight, MoveFowardCrane, MoveBackCrane,\n"
                + "    SpinBallLeft, SpinBallRight, HitToTheLeft, HitToTheRight,\n"
                + "    Igual, Suma, Resta, Multiplicacion, Division, Op_logico, Op_relacional,\n"
                + "    Op_atribucion, Op_incremento, Op_booleano, Parentesis_a, Parentesis_c,\n"
                + "    Llave_a, Llave_c, Corchete_a, Corchete_c, Start, P_coma, Identificador,\n"
                + "    Numero, WaitTime, ERROR;\n\n"
                + "non terminal INICIO, SENTENCIA, DECLARACION, DECLARACION_FOR, IF, ELSE,\n"
                + "    WHILE, DO_WHILE, FOR, SENTENCIA_BOOLEANA, SENTENCIA_ARITMETICA, SENTENCIA_FOR;\n"
                + "\n"
                + "start with INICIO;\n\n" + gramatica;
        jList2.setListData(error);
        jTextArea1.setText(ci);
        jTextArea2.setText(new Optimizacion(ci).opt());
        System.out.println(tabla_simbolos.size());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /*public void contarFilas(){
        int totalrows=jTextPane1.getLineCount();
            lineCounter.setText("1\n");
            for(int i=2; i<=totalrows;i++){
                lineCounter.setText(lineCounter.getText()+i+"\n");
            }
    }*/
    public void llenarTabla(Vector v) {

        DefaultTableModel jTable1 = new DefaultTableModel();
        for (int i = 0; i < v.size(); i = i + 2) {

            modelo.addRow(new Object[]{v.elementAt(i + 1), v.elementAt(i)});

            System.err.println("" + v.elementAt(i));

        }
    }

    public void limpiarTablas() {
        int var = modelo.getRowCount() - 1;
        for (int i = 0; i <= var; i++) {
            modelo.removeRow(0);
        }
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        //Abrir a=new Abrir(this, true, this);
        //a.setVisible(true);
        if (seleccionado.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionado.getSelectedFile();
            if (archivo.canRead()) {
                if (archivo.getName().endsWith("txt")) {
                    String contenido = gestion.AbrirATexto(archivo);
                    jTextPane1.setText(contenido);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un archivo de texto");
                }
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            JFileChooser fiSa = new JFileChooser();
            fiSa.showSaveDialog(this);
            File f = fiSa.getSelectedFile();
            String path = f.getPath();
            String endpath = path.substring(path.length() - 4, path.length());
            if (!endpath.equals(".lms")) {
                f = new File(f.getPath() + ".lms");
            }
            if (f.exists()) {
                f.delete();

            }
            f.createNewFile();

            FileWriter fw = new FileWriter(f, true);
            CodigoLMS cod = new CodigoLMS(ci);
            String tx = cod.lms();
            fw.write(tx);
            fw.close();
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "No se pudo guardar el archivo.");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "El archivo debe ser .lms");
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        if (err.isEmpty()) {
            new VentanaArbol(expresiones).setVisible(true);
        } else {
            new VentanaArbol().setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Automata a = new Automata(this, true, this, gramatica);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    public void habilitarCampo(String dirnovo, String nomnovo) {
        this.setTitle("Compilador");
        this.nomNuevo = nomnovo;
        this.dirNuevo = dirnovo + nomNuevo + ".wb";
        this.setTitle(this.getTitle() + " - " + dirNuevo);
        jTextPane1.enable(true);
        //contarFilas();
    }

    public void habilitarCampo(String dirnovo) {
        this.setTitle("CompiladorEjemplo");
        this.dirNuevo = dirnovo;
        try {
            FileInputStream fstream = new FileInputStream(dirNuevo);
            DataInputStream in = new DataInputStream(fstream);
            this.jTextPane1.setText("");
            while (in.available() != 0) {
                this.jTextPane1.setText(this.jTextPane1.getText() + in.readLine() + "\n");
            }
            in.close();
            this.setTitle(this.getTitle() + " - " + dirNuevo);
            jTextPane1.enable(true);
            //contarFilas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "File input error");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        // SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.OfficeSilver2007Skin");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalCompi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
