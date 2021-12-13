package com.sunny.maven;

import io.vertx.core.AbstractVerticle;

/**
 * @author SUNNY
 * @description: MyFirstVerticle
 * @create: 2021-09-23 23:47
 */
public class MyFirstVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().
                    putHeader("content-type", "text/plain").
                    end("Hello World");
        }).listen(8080);
    }
}
