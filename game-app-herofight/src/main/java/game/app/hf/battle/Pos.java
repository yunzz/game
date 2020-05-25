package game.app.hf.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (0,0),(0,1),(0,2)
 * (1,0),(1,1),(1,2)
 * (2,0),(2,1),(2,2)
 *
 * @author Yunzhe.Jin
 * 2020/4/17 14:57
 */
public class Pos {
    public final int x;
    public final int y;
    private static Pos[][] pos = new Pos[3][3];

    static {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pos[i][j] = new Pos(i, j);
            }
        }
    }


    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Pos> near() {
        List<Pos> pos = new ArrayList<>();
        pos.add(left());
        pos.add(right());
        pos.add(up());
        pos.add(down());
        return pos.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Pos left() {
        int lx = x - 1;
        if (lx < 0) {
            return null;
        }

        return pos[lx][y];
    }

    public Pos right() {
        int _x = x + 1;
        if (_x > 2) {
            return null;
        }

        return pos[_x][y];
    }

    public Pos up() {

        int _y = y - 1;
        if (_y < 0) {
            return null;
        }

        return pos[x][_y];
    }

    public Pos down() {
        int _y = y + 1;
        if (_y > 2) {
            return null;
        }
        return pos[x][_y];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x &&
                y == pos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
