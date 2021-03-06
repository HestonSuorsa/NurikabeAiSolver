public class Cell {
    private int row;
    private int col;
    private int islandSize;
    private boolean isOrigin, isWater;
    private int pheromone;

    public Cell(int row, int col, boolean isOrigin) {
        this.row = row;
        this.col = col;
        this.isOrigin = isOrigin;
        this.islandSize = 0;
        this.pheromone = 0;
    }

    public int getPheromone() { return pheromone; }

    public void setPheromone(int pheromone) { this.pheromone = pheromone; }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getIslandSize() {
        return islandSize;
    }

    public void setIslandSize(int islandSize) {
        this.islandSize = islandSize;
    }

    public boolean getIsOrigin() {
        return isOrigin;
    }

    public void setIsOrigin(boolean isOrigin) {
        this.isOrigin = isOrigin;
    }

    public void setIsLand() { this.isWater = false; }

    public boolean getIsLand() { return !isWater; }

    public boolean getIsWater() {
        return isWater;
    }

    public void setIsWater() {
        this.isWater = true;
    }

    @Override
    public String toString() {
        if (isOrigin) return "" + islandSize; // Island Size
        else if (isWater) return "W"; // Water
        else return "L"; // Land
    }

    public Cell clone() {
        Cell newC = new Cell(this.row, this.col, this.isOrigin);
        if(this.isWater) newC.setIsWater();
        else newC.setIsLand();
        if(this.isOrigin) {
            newC.setIslandSize(this.islandSize);
        }
        return newC;
    }

}
