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
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.Lexer;
import java_cup.runtime.Symbol;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;




public class PrincipalCompi extends javax.swing.JFrame {
    DefaultTableModel modelo;
   
    private String dirNuevo="";
    private String nomNuevo="";
    
    private static String ci="";
    
    public PrincipalCompi() {
        initComponents();
        modelo = (DefaultTableModel)jTable1.getModel();
        jTable1.setModel(modelo);
        
        this.setLocationRelativeTo(null);
        this.setTitle("Compilador - WreckBall");
    }
    
    private void analizarLexico() throws IOException{
        String expr = (String) jtxtArea.getText();
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
                case Comillas:
                    modelo.addRow(new Object[] {"<Comillas>",lexer.lexeme});
                    break;
                case Cadena:
                    modelo.addRow(new Object[] {"<Tipo de dato>",lexer.lexeme});
                    break;
                case T_dato:
                    modelo.addRow(new Object[] {"<Tipo de dato>",lexer.lexeme});
                    break;
                case If:
                    modelo.addRow(new Object[] {"<Reservada if>",lexer.lexeme});
                    break;
                case Else:
                    modelo.addRow(new Object[] {"<Reservada else>",lexer.lexeme});
                    break;
                case Do:
                    modelo.addRow(new Object[] {"<Reservada do>",lexer.lexeme});
                    break;
                case While:
                    modelo.addRow(new Object[] {"<Reservada while>",lexer.lexeme});
                    break;
                case For:
                    modelo.addRow(new Object[] {"<Reservada for>",lexer.lexeme});
                    break;
                case StopLoop:
                    modelo.addRow(new Object[] {"<Reservada stopLoop>",lexer.lexeme});
                    break;
                case SpinCraneLeft:
                    modelo.addRow(new Object[] {"<Método spinCraneLeft>",lexer.lexeme});
                    break;
                case SpinCraneRight:
                    modelo.addRow(new Object[] {"<Método spinCraneRight>",lexer.lexeme});
                    break;
                case MoveFowardCrane:
                    modelo.addRow(new Object[] {"<Método moveFowardCrane>",lexer.lexeme});
                    break;
                case MoveBackCrane:
                    modelo.addRow(new Object[] {"<Método moveBackCrane>",lexer.lexeme});
                    break;
                case SpinBallLeft:
                    modelo.addRow(new Object[] {"<Método spinBallLeft>",lexer.lexeme});
                    break;
                case SpinBallRight:
                    modelo.addRow(new Object[] {"<Método spinBallRight>",lexer.lexeme});
                    break;
                case HitToTheLeft:
                    modelo.addRow(new Object[] {"<Método hitToTheLeft>",lexer.lexeme});
                    break;
                case HitToTheRight:
                    modelo.addRow(new Object[] {"<Método hitToTheRight>",lexer.lexeme});
                    break;
                case Igual:
                    modelo.addRow(new Object[] {"<Operador igual>",lexer.lexeme});
                    break;
                case Suma:
                    modelo.addRow(new Object[] {"<Operador suma>",lexer.lexeme});
                    break;
                case Resta:
                    modelo.addRow(new Object[] {"<Operador resta>",lexer.lexeme});
                    break;
                case Multiplicacion:
                    modelo.addRow(new Object[] {"<Operador multiplicación>",lexer.lexeme});
                    break;
                case Division:
                    modelo.addRow(new Object[] {"<Operador división>",lexer.lexeme});
                    break;
                case Op_logico:
                    modelo.addRow(new Object[] {"<Operador lógico>",lexer.lexeme});
                    break;
                case Op_incremento:
                    modelo.addRow(new Object[] {"<Operador incremento/decremento>",lexer.lexeme});
                    break;
                case Op_relacional:
                    modelo.addRow(new Object[] {"<Operador relacional>",lexer.lexeme});
                    break;
                case Op_atribucion:
                    modelo.addRow(new Object[] {"<Operador atribución>",lexer.lexeme});
                    break;
                case Op_booleano:
                    modelo.addRow(new Object[] {"<Operador booleano>",lexer.lexeme});
                    break;
                case Parentesis_a:
                    modelo.addRow(new Object[] {"<Paréntesis de apertura>",lexer.lexeme});
                    break;
                case Parentesis_c:
                    modelo.addRow(new Object[] {"<Paréntesis de cierre>",lexer.lexeme});
                    break;
                case Llave_a:
                    modelo.addRow(new Object[] {"<Llave de apertura>",lexer.lexeme});
                    break;
                case Llave_c:
                    modelo.addRow(new Object[] {"<Llave de cierre>",lexer.lexeme});
                    break;
                case Corchete_a:
                    modelo.addRow(new Object[] {"<Corchete de apertura>",lexer.lexeme});
                    break;
                case Corchete_c:
                    modelo.addRow(new Object[] {"<Corchete de cierre>",lexer.lexeme});
                    break;
                case Start:
                    modelo.addRow(new Object[] {"<Reservada start>",lexer.lexeme});
                    break;
                case P_coma:
                    modelo.addRow(new Object[] {"<Punto y coma>",lexer.lexeme});
                    break;
                case Identificador:
                    modelo.addRow(new Object[] {"<Identificador>",lexer.lexeme});
                    break;
                case Numero:
                    modelo.addRow(new Object[] {"<Número>",lexer.lexeme});
                    break;
                case ERROR:
                    modelo.addRow(new Object[] {"<Símbolo no definido>",lexer.lexeme});
                    break;
                default:
                    modelo.addRow(new Object[] {null,lexer.lexeme});
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

        jScrollPane2 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        lineCounter = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        output.setColumns(20);
        output.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        output.setForeground(new java.awt.Color(51, 204, 0));
        output.setRows(5);
        output.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        output.setEnabled(false);
        jScrollPane2.setViewportView(output);

        jLabel2.setText("Output:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lineCounter.setEditable(false);
        lineCounter.setColumns(2);
        lineCounter.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        lineCounter.setLineWrap(true);
        lineCounter.setRows(2);
        lineCounter.setTabSize(2);
        lineCounter.setText("1");
        lineCounter.setAutoscrolls(false);
        lineCounter.setCaretColor(new java.awt.Color(51, 51, 255));
        lineCounter.setFocusable(false);

        jtxtArea.setColumns(20);
        jtxtArea.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jtxtArea.setLineWrap(true);
        jtxtArea.setRows(5);
        jtxtArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtAreaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtAreaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtxtArea);

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
        jScrollPane4.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Tabla de Tokens");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lineCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(147, 147, 147))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lineCounter)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel1);

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

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Mostrar");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4.setText("Automata finito");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("Pila");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem6.setText("Arbol");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem7.setText("Tabla codigo int.");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        //Nuevo nv=new Nuevo(this, true,this);
        //nv.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public boolean Guardar(){
        FileOutputStream out;
        PrintStream p;

        try {
            out = new FileOutputStream(dirNuevo);
            p = new PrintStream(out);
            p.println(this.jtxtArea.getText());
            p.close();
            this.setTitle(this.getTitle().replace("*", ""));
            return true;
        } catch (Exception e) {
            //System.err.println("BarbonC - No se puede guardar el archivo");
            return false;
        }
    }
    
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String resultado;
        int n=jTable1.getRowCount();
        
        while(n>0){
            modelo.removeRow(n-1);
            n--;
        }
        
        try {
            analizarLexico();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalCompi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String ST = jtxtArea.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
        
        try {
            s.parse();
            resultado="\nAnalisis realizado correctamente";
            output.setText(resultado);
        } catch (Exception ex) {
            Symbol sym = s.getS();
            resultado="\nError de sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"";
            output.setText(resultado);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    public void contarFilas(){
        int totalrows=jtxtArea.getLineCount();
            lineCounter.setText("1\n");
            for(int i=2; i<=totalrows;i++){
                lineCounter.setText(lineCounter.getText()+i+"\n");
            }
    }
    
    public void llenarTabla(Vector v) {
        
        DefaultTableModel jTable1 = new DefaultTableModel();
        for (int i = 0; i < v.size(); i = i + 2) {

            modelo.addRow(new Object[]{v.elementAt(i+1), v.elementAt(i)});

            System.err.println("" + v.elementAt(i));

        }
    }
    
    public void limpiarTablas(){
        int var = modelo.getRowCount()-1;
        for(int i = 0; i<=var ;i++){
            modelo.removeRow(0);
        }
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        //Abrir a=new Abrir(this, true, this);
        //a.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jtxtAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtAreaKeyPressed
        // TODO add your handling code here:
        //output.setText("");
    }//GEN-LAST:event_jtxtAreaKeyPressed

    private void jtxtAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtAreaKeyReleased
        // TODO add your handling code here:
        if(evt.isControlDown() || evt.getKeyCode()==10 || evt.getKeyCode()==8 || evt.getKeyCode()==127){
            contarFilas();
        }
    }//GEN-LAST:event_jtxtAreaKeyReleased

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

     public void habilitarCampo(String dirnovo, String nomnovo){
        this.setTitle("Compilador");
        this.nomNuevo=nomnovo;
        this.dirNuevo=dirnovo+nomNuevo+".wb";
        this.setTitle(this.getTitle()+" - "+dirNuevo);
        jtxtArea.enable(true);
        contarFilas();
    }
    
    public void habilitarCampo(String dirnovo){
        this.setTitle("CompiladorEjemplo");
        this.dirNuevo=dirnovo;
        try {
            FileInputStream fstream = new FileInputStream(dirNuevo);
            DataInputStream in = new DataInputStream(fstream);
            this.jtxtArea.setText("");
            while (in.available() != 0) {
                this.jtxtArea.setText(this.jtxtArea.getText() + in.readLine() + "\n");
            }
            in.close();
            this.setTitle(this.getTitle()+" - "+dirNuevo);
            jtxtArea.enable(true);
            contarFilas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"File input error");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private static javax.swing.JTextArea jtxtArea;
    private javax.swing.JTextArea lineCounter;
    private javax.swing.JTextArea output;
    // End of variables declaration//GEN-END:variables
}
