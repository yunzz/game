package game.base.net.http;

import game.base.LifeCycle;
import game.base.LifeCycleBox;
import game.base.config.AppDataConfig;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.DisposableServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunzhe.Jin
 * 2020/4/11 14:50
 */
public abstract class HttpServer implements LifeCycle {

    protected final static Logger LOG = LoggerFactory.getLogger(HttpServer.class);
    protected List<ModuleHandler> moduleHandlerList = new ArrayList<>();
    protected LifeCycleBox lifeCycleBox = new LifeCycleBox();


    public HttpServer() {
    }


    protected void initHandler() throws NoSuchMethodException {
        throw new NotImplementedException("没有handler实现");
    }

    protected AppDataConfig appDataConfig() {
        throw new NotImplementedException("需实现配置表获取");
    }

    private void createServer() {
        DisposableServer server = reactor.netty.http.server.HttpServer.create()
                .wiretap(true)
                .port(appDataConfig().getHttpPort())
                .route(routes -> {
                            moduleHandlerList.forEach(moduleHandler -> {
                                if (moduleHandler.getMethod() == HttpMethod.GET) {
                                    routes.get(moduleHandler.getPath(), moduleHandler.getHandler());
                                } else if (moduleHandler.getMethod() == HttpMethod.POST) {
                                    routes.post(moduleHandler.getPath(), moduleHandler.getHandler());
                                } else {
                                    throw new IllegalStateException("Not support");
                                }
                            });
                        }
                ).bindNow();

        server.onDispose().block();

        System.out.println("End server");
    }


    public void run() throws Exception {
        appDataConfig().load();
        start();
    }


    @Override
    public void start() {
        try {
            lifeCycleBox.add(this);
            initHandler();
            createServer();

            lifeCycleBox.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        LOG.info("************************************** End http server");
        lifeCycleBox.stop();
    }
}
