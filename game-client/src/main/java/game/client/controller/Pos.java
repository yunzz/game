package game.client.controller;

/**
 * @author Yunzhe.Jin
 * 2020/4/21 10:56
 */
public class Pos {
    private int ix;
    private int iy;
    private double x;
    private double y;
    private int side;

    public Pos() {

    }

    public Pos(double x, double y, int ix, int iy) {
        this.ix = ix;
        this.iy = iy;
        this.x = x;
        this.y = y;
    }

    public int getIx() {
        return ix;
    }

    public int getIy() {
        return iy;
    }

    public boolean eq(Pos pos) {
        return pos.ix == ix && pos.iy == iy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "Pos{" +
                "ix=" + ix +
                ", iy=" + iy +
                ", x=" + x +
                ", y=" + y +
                ", side=" + side +
                '}';
    }
}
