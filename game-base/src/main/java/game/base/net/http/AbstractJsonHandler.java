package game.base.net.http;

import game.base.utils.JsonUtil;
import io.netty.handler.codec.http.HttpMethod;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiFunction;

/**
 * @author yzz
 * 2020/3/23 17:02
 */
public abstract class AbstractJsonHandler<R, Q> implements BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> {

    public abstract Q parse(R o);

    Class<R> request;

    public AbstractJsonHandler() {
        Type superClass = getClass().getGenericSuperclass();

        request = (Class<R>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    @Override
    public Publisher<Void> apply(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) {
        if (httpServerRequest.method() == HttpMethod.GET) {
            Object ret;

            if (request != Void.class) {
                String param = JsonUtil.toJsonString(httpServerRequest.params());
                ret = parse(JsonUtil.fromJsonString(param, request));
            } else {
                ret = parse(null);
            }

            return httpServerResponse.sendString(Mono.just(JsonUtil.toJsonString(ret)));
        } else if (httpServerRequest.method() == HttpMethod.POST) {
            return httpServerResponse.sendString(httpServerRequest.receive().asString().map(s -> {
                Object parse = parse(JsonUtil.fromJsonString(s, request));
                return JsonUtil.toJsonString(parse);
            }));
        }

        return httpServerResponse.sendString(Mono.just("Not support"));
    }
}
