/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author kid_d
 */
public class Dibujo extends Canvas {

    int l1x1 = 100;
    int l1x2 = 45;
    int l2x1 = 100;
    int l2x2 = 155;
    int n1 = 65;
    int n2 = 10;
    int n3 = 120;
    int t1 = 90;
    int t2 = 30;
    int t3 = 140;
    ArrayList<String> expresiones;
    String[][] exp;

    public Dibujo(ArrayList<String> e) {
        expresiones = e;
        if (!e.isEmpty()) {
            exp = new String[e.size()][3];
            for (int i = 0; i < e.size(); i++) {
                if (e.get(i).contains("+")) {
                    String t[] = e.get(i).split("\\+");
                    exp[i][0] = t[0];
                    exp[i][1] = "+";
                    exp[i][2] = t[1];
                } else if (e.get(i).contains("-")) {
                    String t[] = e.get(i).split("-");
                    exp[i][0] = t[0];
                    exp[i][1] = "-";
                    exp[i][2] = t[1];
                } else if (e.get(i).contains("*")) {
                    String t[] = e.get(i).split("\\*");
                    exp[i][0] = t[0];
                    exp[i][1] = "*";
                    exp[i][2] = t[1];
                } else {
                    String t[] = e.get(i).split("/");
                    exp[i][0] = t[0];
                    exp[i][1] = "/";
                    exp[i][2] = t[1];
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (!expresiones.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            for (int i = 0; i < expresiones.size(); i++) {
                g.setColor(Color.black);
                g.drawLine(l1x1, 65, l1x2, 175);
                g.drawLine(l2x1, 65, l2x2, 175);
                g.setColor(Color.cyan);
                g.fillOval(n1, 30, 70, 70);
                g.fillOval(n2, 140, 70, 70);
                g.fillOval(n3, 140, 70, 70);
                g.setColor(Color.black);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.drawString(exp[i][1], t1, 75);
                g.drawString(exp[i][0], t2, 185);
                g.drawString(exp[i][2], t3, 185);
                l1x1 += 200;
                l1x2 += 200;
                l2x1 += 200;
                l2x2 += 200;
                n1 += 200;
                n2 += 200;
                n3 += 200;
                t1 += 200;
                t2 += 200;
                t3 += 200;
            }
        }
    }
}
