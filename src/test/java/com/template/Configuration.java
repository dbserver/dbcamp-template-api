package com.template;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.*;

@LoadPolicy(LoadType.MERGE)
@Sources({
        "system:properties",
        "classpath:api.properties"})
public interface Configuration extends Config {
    @Key("api.base.path")
    String basePath();

    @Key("api.base.uri")
    String baseURI();

    @Key("api.port")
    int port();
}
