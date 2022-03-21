import java.util.ArrayList;

public class Cell {
    private int index;
    private int islandSize;
    private boolean isOrigin, isLand, isWater;
    private ArrayList<Cell> neighbors;
    //Maybe every cell stores a list of its neighbors...

    public Cell(int index, boolean isOrigin) {
        this.index = index;
        this.isOrigin = isOrigin;
        neighbors = new ArrayList<Cell>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
        ret = "(" + index + "): ";
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
        Cell newC = new Cell(this.index, this.isOrigin);
        return newC;
    }

}
