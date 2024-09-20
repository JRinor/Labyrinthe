// Time for executions: ... s, Number of generator states: .. Path found ? : true length :13

package vues;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import models.Case;

public class VueAffichage extends JPanel implements Observer {

    public VueAffichage() {
        JLabel label = new JLabel("Contenu en bas");
        this.add(label);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Case) {
            Case updatedCase = (Case) arg;
            System.out.println("VueAffichage mise à jour avec: " + updatedCase.getStatut());
        }
    }
}