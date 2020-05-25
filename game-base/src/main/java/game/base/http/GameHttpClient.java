package game.base.http;


import com.google.common.net.HttpHeaders;
import game.base.utils.JsonUtil;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author yzz
 * 2020/3/23 17:54
 */
public class GameHttpClient {

    private static HttpClient client = HttpClient.create()
            .wiretap(true)
            .headers(entries -> {
                entries.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
            }).keepAlive(true);

    public static <T> String post(T req, String uri) {

        Mono<String> response = client
                .post().uri(uri).send(
                        ByteBufFlux.fromString(Mono.just(JsonUtil.toJsonString(req)))
                ).responseContent().aggregate().asString();
        String block = response.block();
        System.out.println(block);
        return block;
    }

    public static <T, R> R post(T req, String uri, Class<R> clazz) {

        Mono<String> response = client
                .post().uri(uri).send(
                        ByteBufFlux.fromString(Mono.just(JsonUtil.toJsonString(req)))
                ).responseContent().aggregate().asString();
        String block = response.block();
        return JsonUtil.fromJsonString(block, clazz);
    }

    public static String get(Map<String, String> req, String uri) throws Exception {
        StringBuilder builder = new StringBuilder();

        if (req != null) {
            for (Map.Entry<String, String> e : req.entrySet()) {
                if (builder.length() > 0) {
                    builder.append("&");
                }
                builder.append(URLEncoder.encode(e.getKey(), "UTF-8") + "=" + URLEncoder.encode(e.getValue(), "UTF-8"));
            }
        }

        if (builder.length() > 0) {
            uri += "?" + builder.toString();
        }

        Mono<String> response = client
                .get()
                .uri(uri)
                .responseContent().aggregate().asString();
        String block = response.block();
        return block;
    }

    public static <T> T get(Map<String, String> req, String uri, Class<T> tClass) throws Exception {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> e : req.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(e.getKey(), "UTF-8") + "=" + URLEncoder.encode(e.getValue(), "UTF-8"));
        }

        if (builder.length() > 0) {
            uri += "?" + builder.toString();
        }

        Mono<String> response = client
                .get()
                .uri(uri)
                .responseContent().aggregate().asString();
        String block = response.block();
        return JsonUtil.fromJsonString(block, tClass);
    }

    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null) {
            return entity;
        }
        url = url.trim();
        if (url.equals("")) {
            return entity;
        }
        String[] urlParts = url.split("\\?");
        entity.baseUrl = urlParts[0];
        //没有参数
        if (urlParts.length == 1) {
            return entity;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        entity.params = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            entity.params.put(keyValue[0], keyValue[1]);
        }

        return entity;
    }

    public static class UrlEntity {
        /**
         * 基础url
         */
        public String baseUrl;
        /**
         * url参数
         */
        public Map<String, String> params;
    }

}
