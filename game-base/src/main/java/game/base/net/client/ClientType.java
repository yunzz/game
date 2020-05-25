package game.base.net.client;

/**
 * @author Yunzhe.Jin
 * 2020/4/6 20:54
 */
public enum ClientType {
    GATEWAY {
        @Override
        public int type() {
            return 1;
        }
    },
    WORLD {
        @Override
        public int type() {
            return 2;
        }
    },
    APP {
        @Override
        public int type() {
            return 3;
        }
    };

    public abstract int type();
}
