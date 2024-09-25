package com.tourapi.mandi.global.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"dev"})
@Configuration
public class OpenTelemetryConfig {

    @Value("${management.otlp.tracing.endpoint:http://localhost:4318}")
    private String tracesEndpoint;

    @Bean
    public SpanExporter spanExporter() {
        return OtlpHttpSpanExporter.builder().setEndpoint(tracesEndpoint).build();
    }
}