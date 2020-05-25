package game.base.net.server;

import game.base.LifeCycleBox;
import game.base.config.AppDataConfig;
import game.base.core.Game;
import game.base.core.Handlers;
import game.base.core.ParseMessage;
import game.base.log.Logs;
import game.base.utils.JsonUtil;
import game.base.utils.ResourceUtils;
import game.base.utils.YamlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.Oneway;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 10:55
 */
public abstract class AbstractServer implements IServer {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractServer.class);

    /**
     * 需要生命周期都类都放这里统一处理
     */
    protected LifeCycleBox lifeCycleBox = new LifeCycleBox();
    protected static Handlers handler = new Handlers();
    protected static ParseMessage parseMessage = new ParseMessage();

    protected int port;
    protected AppDataConfig config;

    public Handlers getHandler() {
        return handler;
    }

    @Override
    public ParseMessage getParseMessage() {
        return parseMessage;
    }

    public LifeCycleBox getLifeCycleBox() {
        return lifeCycleBox;
    }

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        initConfig();
        // 初始化 handler
        lifeCycleBox.add(parseMessage);
        registerHandler();

        initGame();
        // 注册excel配置文件
        initExcel();
        // 加载配置
        lifeCycleBox.add(Game.excelManager);
        lifeCycleBox.add(Game.workManager);
        lifeCycleBox.add(Game.getTcpClientManager());

        beforeStart();
        lifeCycleBox.start();
        start();
        afterStart();
    }

    protected void initConfig() {
        Logs.common.info("开始加载配置文件>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        config = new AppDataConfig();
        config.setWorkBuffSize(2048);
        config.setWorkCount(Runtime.getRuntime().availableProcessors() * 2);

        try {
            config.load();
        } catch (Exception e) {
            Logs.common.error("", e);
            throw new RuntimeException(e);
        } finally {
            Logs.common.info("结束加载配置文件<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }

        String port = this.config.getConfig(AppDataConfig.APP_PORT);
        if (StringUtils.isNoneBlank(port)) {
            this.port = Integer.parseInt(port);
        }

    }

    public String serverName() {
        return config.getConfig(AppDataConfig.APP_NAME);
    }


    /**
     * 注册业务excel配置文件
     */
    protected abstract void initExcel();

    private void initGame() {
        Game.server = this;
        Game.init(config);
    }

    protected void beforeStart() {
    }

    protected void afterStart() {
    }
}
