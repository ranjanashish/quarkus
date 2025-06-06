package io.quarkus.it.extension.testresources;

import static java.util.Objects.requireNonNull;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class SharedResource implements QuarkusTestResourceLifecycleManager {
    private String argument;

    @Override
    public void init(Map<String, String> initArgs) {
        this.argument = requireNonNull(initArgs.get("resource.arg"));
    }

    @Override
    public Map<String, String> start() {
        System.err.println(getClass().getSimpleName() + " start with arg '" + argument + "'");
        return Map.of();
    }

    @Override
    public void stop() {
        System.err.println(getClass().getSimpleName() + " stop");
    }

    @Override
    public void inject(TestInjector testInjector) {
        testInjector.injectIntoFields(argument,
                new TestInjector.AnnotatedAndMatchesType(SharedResourceAnnotation.class, String.class));
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SharedResourceAnnotation {
    }
}
