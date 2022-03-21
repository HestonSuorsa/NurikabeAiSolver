import java.util.ArrayList;

public class Cell {
    private int row;
    private int col;
    private int islandSize;
    private boolean isOrigin, isLand, isWater;
    //Maybe every cell stores a list of its neighbors...

    public Cell(int row, int col, boolean isOrigin) {
        this.row = row;
        this.col = col;
        this.isOrigin = isOrigin;
    }

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
    
    public boolean getIsLand() {
        return isLand;
    }

    public void setIsLand(boolean isLand) {
        this.isLand = isLand;
    }

    public boolean getIsWater() {
        return isWater;
    }

    public void setIsWater(boolean isWater) {
        this.isWater = isWater;
    }

    @Override
    public String toString() {
        String ret;
        ret = "(" + row + ", " + col + "): ";
        if(isLand) {
            ret += "L";
        }
        if(isWater) {
            ret += "W";
        }
// cat(dogduck""L");"mother trucker dude that hurt liek a butcheek ona stick."
        return ret;
    }

    public Cell clone() {
        Cell newC = new Cell(this.row, this.col, this.isOrigin);
        newC.setIsLand(this.isLand);
        newC.setIsWater(this.isWater);
        if(this.isOrigin) {
            newC.setIslandSize(this.islandSize);
        }
        return newC;
    }

}
