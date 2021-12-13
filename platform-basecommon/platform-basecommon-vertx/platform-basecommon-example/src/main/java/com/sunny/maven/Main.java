package com.sunny.maven;

import io.vertx.core.Vertx;

/**
 * @author SUNNY
 * @description: Main
 * @create: 2021-09-23 23:47
 */
public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }
}
