package com.tourapi.mandi.domain.course.service;

import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.domain.course.dto.CourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNameResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNearbyListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseNearbyRequestDto;
import com.tourapi.mandi.domain.course.dto.CoursePreferredListResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseResponseDto;
import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.domain.course.repository.CourseRepository;
import com.tourapi.mandi.domain.course.util.CourseMapper;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.exception.Exception404;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    public CourseListResponseDto findBySearch(CourseSearchDto courseSearch) {
        return CourseMapper.toCourseListResponseDto(courseRepository.findCoursesBySearch(courseSearch));
    }

    public List<CourseNameResponseDto> getNames() {
        return CourseMapper.toCourseNameListResponseDto(courseRepository.findAll());
    }

    public CourseNearbyListResponseDto findCoursesInBound(CourseNearbyRequestDto request) {
        return CourseMapper.toCourseNearbyResponseDto(courseRepository.findCoursesInBound(request.ne(), request.sw()));
    }

    public CourseResponseDto getCourseById(Long courseId) {
        return CourseMapper.toCourseResponseDto(findCourseById(courseId));
    }

    public CoursePreferredListResponseDto findPreferredCourses(final User user) {
        User existingUser = userService.getExistingUser(user);
        return CourseMapper.toCoursePreferredListResponseDto(courseRepository.findPreferredCourse(existingUser.getCoursePreference()));
    }

    private Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new Exception404(CourseExceptionStatus.COURSE_NOT_FOUND));
    }
}
