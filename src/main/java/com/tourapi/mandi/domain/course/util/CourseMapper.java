package com.tourapi.mandi.domain.course.util;

import static lombok.AccessLevel.PRIVATE;

import com.tourapi.mandi.domain.course.dto.CourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseResponseDto;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.global.common.PageInfoDto;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = PRIVATE)
public final class CourseMapper {
    public static CourseListResponseDto toCourseListResponseDto(final Page<Course> page) {
        List<CourseResponseDto> courses = page.stream()
                .map(CourseResponseDto::new)
                .toList();

        return CourseListResponseDto.builder()
                .pageInfo(new PageInfoDto(page))
                .courses(courses)
                .build();
    }
}
