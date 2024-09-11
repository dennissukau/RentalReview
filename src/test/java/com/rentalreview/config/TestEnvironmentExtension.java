package com.rentalreview.config;

import org.junit.jupiter.api.extension.*;

public class TestEnvironmentExtension implements ParameterResolver, BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) {

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return Environment.class.equals(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var context = extensionContext.getRoot();
        var store = context.getStore(ExtensionContext.Namespace.GLOBAL);
        return store.getOrComputeIfAbsent(TestEnvironmentResource.class);
    }
}
