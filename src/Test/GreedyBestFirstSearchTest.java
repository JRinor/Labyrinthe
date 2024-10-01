package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import algorithms.GreedyBestFirstSearch;
import models.Labyrinthe;
import models.Case;
import vues.VueGrille;

import java.util.List;
import java.util.Map;

class GreedyBestFirstSearchTest {

    private GreedyBestFirstSearch gbfs;
    private Labyrinthe labyrinthe;
    private VueGrille vueGrille;

    @BeforeEach
    void setUp() {
        labyrinthe = new Labyrinthe(5, 5);
        vueGrille = new VueGrille(5, 5, labyrinthe);
        gbfs = new GreedyBestFirstSearch(vueGrille);
    }

    @Test
    void testPathFound() {
        Case start = labyrinthe.getCase(0, 0);
        Case goal = labyrinthe.getCase(4, 4);
        Map<String, Object> result = gbfs.search(labyrinthe, start, goal);

        assertNotNull(result.get("shortestPath"));
        assertTrue(((List<Case>)result.get("shortestPath")).size() > 0);
    }


    @Test
    void testStartEqualsGoal() {
        Case start = labyrinthe.getCase(0, 0);
        Map<String, Object> result = gbfs.search(labyrinthe, start, start);

        assertNotNull(result.get("shortestPath"));
        assertEquals(1, ((List<Case>)result.get("shortestPath")).size());
    }

    @Test
    void testNullStartOrGoal() {
        Case start = labyrinthe.getCase(0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            gbfs.search(labyrinthe, null, start);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gbfs.search(labyrinthe, start, null);
        });
    }
}
