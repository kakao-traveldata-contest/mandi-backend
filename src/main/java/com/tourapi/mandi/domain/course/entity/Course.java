package com.tourapi.mandi.domain.course.entity;

import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "course_tb")
public class Course extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(length = 100, nullable = false)
    private String name;

    private BigDecimal distance;

    private String duration;

    private BigDecimal ratingAverage;

    @Column(length = 512, name = "route_url")
    private String routeUrl;

    @Column(length = 512, name = "img_url")
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private DifficultyLevel difficulty;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "mid_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "mid_longitude"))
    })
    private Coordinate midPoint;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "start_point_name")),
            @AttributeOverride(name = "address", column = @Column(name = "start_address")),
            @AttributeOverride(name = "coordinate.latitude", column = @Column(name = "start_latitude")),
            @AttributeOverride(name = "coordinate.longitude", column = @Column(name = "start_longitude"))
    })
    private Location startPoint;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "end_point_name")),
            @AttributeOverride(name = "address", column = @Column(name = "end_address")),
            @AttributeOverride(name = "coordinate.latitude", column = @Column(name = "end_latitude")),
            @AttributeOverride(name = "coordinate.longitude", column = @Column(name = "end_longitude"))
    })
    private Location endPoint;
}
