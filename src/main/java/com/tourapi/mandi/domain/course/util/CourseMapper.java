package com.tourapi.mandi.domain.course.util;

import static lombok.AccessLevel.PRIVATE;

import com.tourapi.mandi.domain.course.dto.CourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNameResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNearbyListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNearbyResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseListItemResponseDto;
import com.tourapi.mandi.domain.course.dto.CoursePreferredListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseResponseDto;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.global.dto.PageInfoDto;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = PRIVATE)
public final class CourseMapper {
    public static CourseListResponseDto toCourseListResponseDto(final Page<Course> page) {
        List<CourseListItemResponseDto> courses = page.stream()
                .map(CourseMapper::toCourseListItemDto)
                .toList();

        return CourseListResponseDto.builder()
                .pageInfo(new PageInfoDto(page))
                .courses(courses)
                .build();
    }

    public static CoursePreferredListResponseDto toCoursePreferredListResponseDto(final List<Course> courses) {
        List<CourseListItemResponseDto> coursesPreferred = courses.stream()
                .map(CourseMapper::toCourseListItemDto)
                .toList();

        return CoursePreferredListResponseDto.builder()
                .courses(coursesPreferred)
                .build();
    }

    private static CourseListItemResponseDto toCourseListItemDto(final Course course) {
        return CourseListItemResponseDto.builder()
                .id(course.getCourseId())
                .courseName(course.getName())
                .distance(course.getDistance())
                .startPointName(course.getStartPoint().getName())
                .endPointName(course.getEndPoint().getName())
                .difficulty(course.getDifficulty())
                .ratingAverage(course.getRatingAverage())
                .duration(DateTimeUtil.formatHourMinute(course.getDuration()))
                .imgUrl(course.getImgUrl())
                .build();
    }

    public static List<CourseNameResponseDto> toCourseNameListResponseDto(final List<Course> courses) {
        return courses.stream()
                .map(CourseNameResponseDto::new)
                .toList();
    }

    public static CourseNearbyListResponseDto toCourseNearbyResponseDto(final List<Course> nearByCourses) {
        List<CourseNearbyResponseDto> courses = nearByCourses.stream()
                .map(CourseMapper::toCourseNearbyItemDto)
                .toList();

        return CourseNearbyListResponseDto.builder()
                .courses(courses)
                .build();
    }

    private static CourseNearbyResponseDto toCourseNearbyItemDto(final Course course) {
        return CourseNearbyResponseDto.builder()
                .id(course.getCourseId())
                .courseName(course.getName())
                .distance(course.getDistance())
                .startPoint(course.getStartPoint())
                .midPoint(course.getMidPoint())
                .endPoint(course.getEndPoint())
                .difficulty(course.getDifficulty())
                .ratingAverage(course.getRatingAverage())
                .duration(DateTimeUtil.formatHourMinute(course.getDuration()))
                .gpxUrl(course.getRouteUrl())
                .build();
    }

    public static CourseResponseDto toCourseResponseDto(final Course course) {
        return CourseResponseDto.builder()
            .id(course.getCourseId())
            .courseName(course.getName())
            .distance(course.getDistance())
            .startPoint(course.getStartPoint())
            .endPoint(course.getEndPoint())
            .difficulty(course.getDifficulty())
            .ratingAverage(course.getRatingAverage())
            .duration(DateTimeUtil.formatHourMinute(course.getDuration()))
            .imgUrl(course.getImgUrl())
            .gpxUrl(course.getRouteUrl())
            .build();
    }
}
