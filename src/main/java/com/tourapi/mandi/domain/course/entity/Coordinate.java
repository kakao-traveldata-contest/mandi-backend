package com.tourapi.mandi.domain.course.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Coordinate {

    private double latitude;

    private double longitude;
}
