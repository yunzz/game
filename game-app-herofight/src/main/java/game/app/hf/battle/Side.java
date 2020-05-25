package game.app.hf.battle;

/**
 * @author Yunzhe.Jin
 * 2020/4/17 15:39
 */
public enum Side {

    FIRST {
        @Override
        public Side flip() {
            return SECOND;
        }
    }, SECOND {
        @Override
        public Side flip() {
            return FIRST;
        }
    };

    public abstract Side flip();
}
