package vues;

import models.Case;
import models.Labyrinthe;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VueGrille extends JPanel implements Observer {
    private JButton[][] buttons;
    private Labyrinthe labyrinthe;
    private Case.Statut currentStatut = Case.Statut.VIDE;

    public VueGrille(int largeur, int hauteur, Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.setLayout(new GridLayout(largeur, hauteur));
        buttons = new JButton[largeur][hauteur];
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                buttons[i][j] = button;
                final int x = i;
                final int y = j;
                button.addActionListener(e -> labyrinthe.setCaseStatut(x, y, currentStatut));
                this.add(button);
            }
        }
    }

    public void setCurrentStatut(Case.Statut statut) {
        this.currentStatut = statut;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Case) {
            Case updatedCase = (Case) arg;
            int x = updatedCase.getX();
            int y = updatedCase.getY();
            SwingUtilities.invokeLater(() -> updateButtonColor(buttons[x][y], updatedCase.getStatut()));
        }
    }

    private void updateButtonColor(JButton button, Case.Statut statut) {
        Color color;
        switch (statut) {
            case MUR:
                color = Color.BLACK;
                break;
            case DEPART:
                color = Color.GREEN;
                break;
            case ARRIVEE:
                color = Color.RED;
                break;
            case VIDE:
            default:
                color = Color.WHITE;
                break;
        }
        button.setBackground(color);
    }

    public void updateButtonColor(JButton button, Color color) {
        if (button.getBackground() != Color.GREEN && button.getBackground() != Color.RED) {
            button.setBackground(color);
        }
    }

    public JButton getButtonForCase(Case c) {
        return buttons[c.getX()][c.getY()];
    }

    public void resetPathColors() {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                if (button.getBackground() == Color.YELLOW || button.getBackground() == Color.CYAN) {
                    button.setBackground(Color.WHITE);
                }
            }
        }
    }
}