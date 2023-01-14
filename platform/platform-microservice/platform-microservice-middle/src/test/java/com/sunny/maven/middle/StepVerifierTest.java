package com.sunny.maven.middle;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author SUNNY
 * @description: StepVerifier
 * @create: 2022-11-01 10:10
 */
public class StepVerifierTest {

    Flux<String> helloWorld = Flux.just("Hello", "World");

    @Test
    public void testStepVerifier() {
        StepVerifier.create(helloWorld).
                expectNext("Hello").
                expectNext("World").
                expectComplete().
                verify();
    }

    @Test
    public void testStepVerifierWithError() {
        Flux<String> helloWorldWithException =
                helloWorld.concatWith(Mono.error(new IllegalArgumentException("exception")));
        StepVerifier.create(helloWorldWithException).
                expectNext("Hello").
                expectNext("World").expectErrorMessage("exception").
                verify();
    }
}
