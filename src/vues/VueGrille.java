// src/vues/VueGrille.java
package vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import models.Case;
import models.Labyrinthe;

public class VueGrille extends JPanel implements Observer {
    private JButton[][] buttons;
    private Labyrinthe labyrinthe;
    private Case.Statut currentStatut = Case.Statut.VIDE; // Default to VIDE

    public VueGrille(int largeur, int hauteur, Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.setLayout(new GridLayout(largeur, hauteur));
        buttons = new JButton[largeur][hauteur];
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                buttons[i][j] = button;
                int x = i;
                int y = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        labyrinthe.setCaseStatut(x, y, currentStatut);
                    }
                });
                this.add(button);
            }
        }
    }

    public void setCurrentStatut(Case.Statut statut) {
        this.currentStatut = statut;
    }

    public JButton getButtonForCase(Case c) {
        return buttons[c.getX()][c.getY()];
    }

    public void updateButtonColor(JButton button, Color color) {
        if (button.getBackground() != Color.GREEN && button.getBackground() != Color.RED) {
            button.setBackground(color);
        }
    }

    private void updateButtonColor(JButton button, Case.Statut statut) {
        switch (statut) {
            case MUR:
                button.setBackground(Color.BLACK);
                break;
            case DEPART:
                button.setBackground(Color.GREEN);
                break;
            case ARRIVEE:
                button.setBackground(Color.RED);
                break;
            case VIDE:
                button.setBackground(Color.WHITE);
                break;
        }
    }

    public void resetPathColors() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton button = buttons[i][j];
                if (button.getBackground() == Color.YELLOW) {
                    button.setBackground(Color.WHITE);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Case) {
            Case updatedCase = (Case) arg;
            JButton button = getButtonForCase(updatedCase);
            if (button != null) {
                updateButtonColor(button, updatedCase.getStatut());
            }
            System.out.println("VueGrille mise à jour avec: " + updatedCase.getStatut());
        }
    }
}