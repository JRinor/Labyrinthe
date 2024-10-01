package algorithms;

public class AlgorithmStats {
    private long executionTime;
    private int statesGenerated;
    private boolean success;
    private int pathLength;

    public AlgorithmStats() {
        reset();
    }

    public void reset() {
        this.executionTime = 0;
        this.statesGenerated = 0;
        this.success = false;
        this.pathLength = 0;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public void incrementStatesGenerated() {
        this.statesGenerated++;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getStatesGenerated() {
        return statesGenerated;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPathLength() {
        return pathLength;
    }

    @Override
    public String toString() {
        return String.format(
                "Temps d'exécution : %d ms%n" +
                        "États générés : %d%n" +
                        "Réussite : %s%n" +
                        "Longueur du chemin : %d",
                executionTime, statesGenerated, (success ? "Oui" : "Non"), pathLength
        );
    }
}
