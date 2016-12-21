package eu.knoker.knodes.kweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * Created by eduardo on 20/12/2016.
 */
public class KWeb extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();
        Router router = Router.router(vertx);

        router.route("/eventbus/*")
                .handler(SockJSHandler.create(vertx).bridge(
                        new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddressRegex(".*")).addInboundPermitted(new PermittedOptions().setAddressRegex(".*"))
                        )
                );
        router.route().handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("html"));

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
