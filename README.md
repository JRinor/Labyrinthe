# Algorithmes de Recherche de Chemin dans un Labyrinthe

## Description
Cette application implémente et visualise différents algorithmes de recherche de chemin dans un labyrinthe. Elle permet de comparer les performances et les caractéristiques de plusieurs algorithmes populaires de pathfinding.

## Algorithmes Implémentés
- A* (A-Star)
- Breadth-First Search (BFS)
- Depth-First Search (DFS)
- Dijkstra
- Greedy Best-First Search

## Fonctionnalités
- Génération de labyrinthes personnalisables
- Visualisation en temps réel du processus de recherche
- Comparaison des performances des différents algorithmes
- Interface utilisateur interactive pour la création et la modification de labyrinthes

## Pour les Utilisateurs

### Installation
1. Assurez-vous d'avoir Java (version 8 ou supérieure) installé sur votre système.
2. Téléchargez le fichier JAR de l'application depuis la section des releases.
3. Double-cliquez sur le fichier JAR ou exécutez-le via la commande :
```
java -jar nom_du_fichier.jar
```

### Utilisation
1. Lancez l'application.
2. Créez un labyrinthe en utilisant les outils de l'interface :
   - Cliquez pour placer des murs
   - Définissez le point de départ et d'arrivée
3. Choisissez un algorithme dans le menu déroulant.
4. Cliquez sur "Démarrer" pour lancer la recherche.
5. Observez le processus de recherche en temps réel.
6. Consultez les statistiques de performance après la fin de la recherche.

### Astuces
- Utilisez le bouton "Réinitialiser" pour effacer le chemin et recommencer.
- Expérimentez avec différentes configurations de labyrinthe pour voir comment les algorithmes se comportent.

## Pour les Développeurs

### Structure du Projet
```
project/
│
├── src/
│   ├── algorithms/
│   │   ├── AStar.java
│   │   ├── BreadthFirstSearch.java
│   │   ├── DepthFirstSearch.java
│   │   ├── Dijkstra.java
│   │   └── GreedyBestFirstSearch.java
│   │
│   ├── models/
│   │   ├── Case.java
│   │   └── Labyrinthe.java
│   │
│   ├── vues/
│   │   ├── VueAffichage.java
│   │   ├── VueBouttons.java
│   │   ├── VueFenetre.java
│   │   └── VueGrille.java
│   │
│   └── Main.java
│
└── test/
└── [fichiers de test]
```

### Configuration de l'Environnement de Développement
1. Clonez le dépôt :
```
git clone https://github.com/JRinor/Labyrinthe.git
```
2. Ouvrez le projet dans votre IDE préféré (Eclipse, IntelliJ IDEA, etc.).
3. Assurez-vous que le JDK (version 8 ou supérieure) est correctement configuré.

### Compilation
Utilisez votre IDE ou compilez via la ligne de commande :
```
javac -d bin src/**/*.java
```

### Exécution des Tests
Les tests unitaires sont situés dans le dossier `test/`. Exécutez-les via votre IDE ou en utilisant JUnit en ligne de commande.



