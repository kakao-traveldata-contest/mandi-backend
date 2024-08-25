package com.tourapi.mandi.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourapi.mandi.global.exception.Exception401;
import com.tourapi.mandi.global.exception.Exception403;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

public class FilterResponseUtils {

        private static final ObjectMapper om = new ObjectMapper();

        public static void unAuthorized(HttpServletResponse response, Exception401 exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(exception.status().value());
        response.getWriter().println(om.writeValueAsString(exception.body()));
    }

        public static void forbidden(HttpServletResponse response, Exception403 exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(exception.status().value());
        response.getWriter().println(om.writeValueAsString(exception.body()));
    }

}
