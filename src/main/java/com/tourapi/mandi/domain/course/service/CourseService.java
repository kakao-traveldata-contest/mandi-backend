package com.tourapi.mandi.domain.course.service;

import com.tourapi.mandi.domain.course.dto.CourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNameResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.repository.CourseRepository;
import com.tourapi.mandi.domain.course.util.CourseMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseListResponseDto findBySearch(CourseSearchDto courseSearch) {
        return CourseMapper.toCourseListResponseDto(courseRepository.findCoursesBySearch(courseSearch));
    }

    public List<CourseNameResponseDto> getNames() {
        return CourseMapper.toCourseNameListResponseDto(courseRepository.findAll());
    }
}
