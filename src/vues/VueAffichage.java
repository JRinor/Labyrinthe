package vues;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import models.Case;
import algorithms.AlgorithmStats;

public class VueAffichage extends JPanel implements Observer {

    private JLabel executionTimeLabel;
    private JLabel statesGeneratedLabel;
    private JLabel successLabel;
    private JLabel pathLengthLabel;

    public VueAffichage() {
        setLayout(new GridLayout(4, 1));
        executionTimeLabel = new JLabel("Temps d'exécution : ");
        statesGeneratedLabel = new JLabel("États générés : ");
        successLabel = new JLabel("Réussite : ");
        pathLengthLabel = new JLabel("Longueur du chemin : ");

        add(executionTimeLabel);
        add(statesGeneratedLabel);
        add(successLabel);
        add(pathLengthLabel);
    }

    public void updateStats(AlgorithmStats stats) {
        SwingUtilities.invokeLater(() -> {
            executionTimeLabel.setText("Temps d'exécution : " + stats.getExecutionTime() + " ms");
            statesGeneratedLabel.setText("États générés : " + stats.getStatesGenerated());
            successLabel.setText("Réussite : " + (stats.isSuccess() ? "Oui" : "Non"));
            pathLengthLabel.setText("Longueur du chemin : " + stats.getPathLength());
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        // Cette méthode peut rester vide si vous n'avez plus besoin de l'update pour les Cases
    }
}
