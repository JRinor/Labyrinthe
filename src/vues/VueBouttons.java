// src/vues/VueBouttons.java
package vues;

import controleurs.EcouteurDemarrer;
import controleurs.EcouteurQuitter;
import models.Case;
import models.Labyrinthe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class VueBouttons extends JPanel implements Observer {
    private Labyrinthe labyrinthe;
    private VueGrille vueGrille;
    private JButton buttonMur;
    private JButton buttonDepart;
    private JButton buttonArrivee;
    private JButton buttonVide;
    private JComboBox<String> comboBox;

    public VueBouttons(Labyrinthe labyrinthe, VueGrille vueGrille) {
        this.labyrinthe = labyrinthe;
        this.vueGrille = vueGrille;

        JLabel label = new JLabel("Composez votre labyrinthe");
        buttonMur = createButton("Mur", Case.Statut.MUR);
        buttonDepart = createButton("Depart", Case.Statut.DEPART);
        buttonArrivee = createButton("Arrivee", Case.Statut.ARRIVEE);
        buttonVide = createButton("Vide", Case.Statut.VIDE);
        buttonVide.setBackground(Color.WHITE); // Set default selected button color

        comboBox = new JComboBox<>(new String[]{"AStar", "BreadthFirstSearch", "DepthFirstSearch", "Dijkstra", "GreedyBestFirstSearch", "IDAStar"});
        JButton buttonDemarrer = new JButton("Demarrer");
        JButton buttonQuitter = new JButton("Quitter");

        buttonDemarrer.addActionListener(new EcouteurDemarrer(labyrinthe, vueGrille, comboBox));
        buttonQuitter.addActionListener(new EcouteurQuitter());

        this.add(label);
        this.add(buttonMur);
        this.add(buttonDepart);
        this.add(buttonArrivee);
        this.add(buttonVide);
        this.add(comboBox);
        this.add(buttonDemarrer);
        this.add(buttonQuitter);
    }

    private JButton createButton(String text, Case.Statut statut) {
        JButton button = new JButton(text);
        button.setActionCommand(statut.name());
        button.addActionListener(new ButtonActionListener());
        return button;
    }

    private void updateButtonColors(Case.Statut selectedStatut) {
        buttonMur.setBackground(selectedStatut == Case.Statut.MUR ? Color.BLACK : null);
        buttonMur.setForeground(selectedStatut == Case.Statut.MUR ? Color.WHITE : null);

        buttonDepart.setBackground(selectedStatut == Case.Statut.DEPART ? Color.GREEN : null);
        buttonDepart.setForeground(selectedStatut == Case.Statut.DEPART ? Color.WHITE : null);

        buttonArrivee.setBackground(selectedStatut == Case.Statut.ARRIVEE ? Color.RED : null);
        buttonArrivee.setForeground(selectedStatut == Case.Statut.ARRIVEE ? Color.WHITE : null);

        buttonVide.setBackground(selectedStatut == Case.Statut.VIDE ? Color.WHITE : null);
        buttonVide.setForeground(selectedStatut == Case.Statut.VIDE ? Color.BLACK : null);
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Case.Statut statut = Case.Statut.valueOf(e.getActionCommand());
            vueGrille.setCurrentStatut(statut);
            updateButtonColors(statut);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Case) {
            Case updatedCase = (Case) arg;
            System.out.println("VueBouttons mise à jour avec: " + updatedCase.getStatut());
        }
    }
}