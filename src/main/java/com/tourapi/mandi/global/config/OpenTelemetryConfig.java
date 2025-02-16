package com.tourapi.mandi.global.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"dev"})
@Configuration
public class OpenTelemetryConfig {

    @Value("${tracing.url:http://localhost:4317}")
    private String tracesEndpoint;

    @Bean
    public SpanExporter spanExporter() {
        return OtlpGrpcSpanExporter.builder().setEndpoint(tracesEndpoint).build();
    }
}