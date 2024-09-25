package vues;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import models.Case;

public class VueAffichage extends JPanel implements Observer {

    private JLabel label;

    public VueAffichage() {
        label = new JLabel("Contenu en bas");
        this.add(label);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Case) {
            Case updatedCase = (Case) arg;
            SwingUtilities.invokeLater(() -> {
                System.out.println("VueAffichage mise à jour avec: " + updatedCase.getStatut());
                // Update the label or other UI components if needed
            });
        }
    }
}