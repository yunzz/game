package game.base.net.http;

import game.base.utils.AnnotationUtils;
import io.netty.handler.codec.http.HttpMethod;
import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author yzz
 * 2020/3/17 3:17 下午
 */
public class ModuleHandler {
    private String path;

    private HttpMethod method;

    private BiFunction<? super HttpServerRequest, ? super HttpServerResponse, ? extends Publisher<Void>> handler;

    public ModuleHandler(BiFunction<? super HttpServerRequest, ? super HttpServerResponse, ? extends Publisher<Void>> handler) {

        HttpGet annotation = AnnotationUtils.findAnnotation(handler.getClass(), HttpGet.class);
        if (annotation != null) {

            path = annotation.value();
            method = HttpMethod.GET;
        } else {
            HttpPost post = AnnotationUtils.findAnnotation(handler.getClass(), HttpPost.class);
            path = post.value();
            method = HttpMethod.POST;
        }

        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public BiFunction<? super HttpServerRequest, ? super HttpServerResponse, ? extends Publisher<Void>> getHandler() {
        return handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleHandler that = (ModuleHandler) o;
        return path.equals(that.path) &&
                method.equals(that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
