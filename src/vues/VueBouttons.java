// VueBouttons.java
package vues;

import controleurs.*;
import models.Case;
import models.Labyrinthe;

import javax.swing.*;
import java.awt.*;
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
    private JButton buttonDemarrer;

    public VueBouttons(Labyrinthe labyrinthe, VueGrille vueGrille) {
        this.labyrinthe = labyrinthe;
        this.vueGrille = vueGrille;

        JLabel label = new JLabel("Composez votre labyrinthe");
        buttonMur = createButton("Mur", Case.Statut.MUR);
        buttonDepart = createButton("Depart", Case.Statut.DEPART);
        buttonArrivee = createButton("Arrivee", Case.Statut.ARRIVEE);
        buttonVide = createButton("Vide", Case.Statut.VIDE);
        buttonVide.setBackground(Color.WHITE);

        comboBox = new JComboBox<>(new String[]{"AStar", "BreadthFirstSearch", "DepthFirstSearch", "Dijkstra", "GreedyBestFirstSearch", "IDAStar"});
        buttonDemarrer = new JButton("Demarrer");
        JButton buttonQuitter = new JButton("Quitter");

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
        button.addActionListener(e -> {
            vueGrille.setCurrentStatut(statut);
            updateButtonColors(statut);
        });
        return button;
    }

    private void updateButtonColors(Case.Statut selectedStatut) {
        buttonMur.setBackground(selectedStatut == Case.Statut.MUR ? Color.BLACK : Color.LIGHT_GRAY);
        buttonMur.setForeground(selectedStatut == Case.Statut.MUR ? Color.WHITE : Color.BLACK);

        buttonDepart.setBackground(selectedStatut == Case.Statut.DEPART ? Color.GREEN : Color.LIGHT_GRAY);
        buttonDepart.setForeground(selectedStatut == Case.Statut.DEPART ? Color.WHITE : Color.BLACK);

        buttonArrivee.setBackground(selectedStatut == Case.Statut.ARRIVEE ? Color.RED : Color.LIGHT_GRAY);
        buttonArrivee.setForeground(selectedStatut == Case.Statut.ARRIVEE ? Color.WHITE : Color.BLACK);

        buttonVide.setBackground(selectedStatut == Case.Statut.VIDE ? Color.WHITE : Color.LIGHT_GRAY);
        buttonVide.setForeground(selectedStatut == Case.Statut.VIDE ? Color.BLACK : Color.BLACK);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Case) {
            Case updatedCase = (Case) arg;
            System.out.println("VueBouttons mise à jour avec: " + updatedCase.getStatut());
        }
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public void setEcouteurDemarrer(EcouteurDemarrer ecouteurDemarrer) {
        buttonDemarrer.addActionListener(ecouteurDemarrer);
    }
}
