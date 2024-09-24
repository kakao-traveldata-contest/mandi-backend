package com.tourapi.mandi.domain.course.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Profile("dev")
public class DurunubiRouteApiClient implements CourseRouteApiClient {

    private final String serviceKey;
    private final String endPointUri;
    private final RestTemplate restOperations;

    public DurunubiRouteApiClient(
            @Value("${openapi.durunubi.service-key}") final String serviceKey,
            @Value("${openapi.durunubi.end-point-url}") final String endPointUrl,
            final RestTemplate restOperations
    ) {
        this.serviceKey = serviceKey;
        this.endPointUri = buildRequestUri(endPointUrl);
        this.restOperations = restOperations;
    }

    private String buildRequestUri(String apiUri) {
        return UriComponentsBuilder.fromHttpUrl(apiUri)
                .queryParam("numOfRows", "500")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "mandi")
                .queryParam("serviceKey", serviceKey)
                .queryParam("brdDiv", "DNWW")
                .queryParam("_type", "json")
                .build()
                .toUriString();
    }

    @Override
    public RouteListResponse requestRoutes() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<RouteListResponse> response = fetchResponse(request);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new RuntimeException("Course request failed");
    }

    private ResponseEntity<RouteListResponse> fetchResponse(HttpEntity<?> request) {
        try {
            return restOperations.exchange(endPointUri, HttpMethod.GET, request, RouteListResponse.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("HttpClientErrorException: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("HttpServerErrorException: " + e.getMessage());
        }
    }
}
