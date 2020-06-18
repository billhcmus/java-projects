package vn.sparrow.vertx.common.utils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.sparrow.vertx.config.VertxConfig;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

/** Created by thuyenpt Date: 2020-06-07 */
public class VertxUtils {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  private VertxUtils() {}

  public static Vertx getVertxInstance(VertxConfig vertxConfig) {
    MeterRegistry meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    meterRegistry.config().commonTags().commonTags("application", "zas-bank-mapping");

    MicrometerMetricsOptions metricsOptions =
        new MicrometerMetricsOptions()
            .setMicrometerRegistry(meterRegistry)
            .setJvmMetricsEnabled(true)
            .setPrometheusOptions(
                new VertxPrometheusOptions()
                    .setEnabled(true)
                    .setEmbeddedServerOptions(
                        new HttpServerOptions().setPort(vertxConfig.getPrometheusPort()))
                    .setEmbeddedServerEndpoint("/metrics")
                    .setPublishQuantiles(true));
    Vertx vertx =
        Vertx.vertx(
            new VertxOptions()
                .setPreferNativeTransport(true)
                .setWorkerPoolSize(vertxConfig.getWorkerPoolSize())
                .setEventLoopPoolSize(vertxConfig.getEventLoopPoolSize())
                .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
                .setMaxWorkerExecuteTimeUnit(TimeUnit.MILLISECONDS)
                .setMaxEventLoopExecuteTime(vertxConfig.getMaxEventLoopExecutionTimeMillis())
                .setMaxWorkerExecuteTime(vertxConfig.getMaxWorkerExecutionTimeMillis())
                .setMetricsOptions(metricsOptions));

    vertx.exceptionHandler(
        ex -> LOGGER.error("Un handle exception, {}", ExceptionUtils.getDetail(ex)));

    new UptimeMetrics().bindTo(meterRegistry);
    new ProcessorMetrics().bindTo(meterRegistry);

    return vertx;
  }
}
