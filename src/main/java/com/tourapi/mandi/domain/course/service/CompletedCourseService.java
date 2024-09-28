package com.tourapi.mandi.domain.course.service;

import static java.lang.Boolean.TRUE;

import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.domain.course.dto.CompletedCourseDto;
import com.tourapi.mandi.domain.course.dto.CompletedCourseListResponseDto;
import com.tourapi.mandi.domain.course.dto.CreateReviewRequestDto;
import com.tourapi.mandi.domain.course.dto.ReviewDto;
import com.tourapi.mandi.domain.course.dto.ReviewListResponseDto;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.course.entity.ReviewImage;
import com.tourapi.mandi.domain.course.repository.CompletedCourseRepository;
import com.tourapi.mandi.domain.course.util.CompletedCourseMapper;
import com.tourapi.mandi.domain.course.util.ReviewMapper;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.exception.Exception403;
import com.tourapi.mandi.global.exception.Exception404;
import com.tourapi.mandi.global.exception.Exception409;
import com.tourapi.mandi.global.util.S3ImageClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompletedCourseService  {
    private final CompletedCourseRepository completedCourseRepository;
    private final UserService userService;
    private final S3ImageClient s3ImageClient;

    @Transactional(readOnly = true)
    public CompletedCourseListResponseDto getCompletedCourses(User user) {
        User existingUser = userService.getExistingUser(user);

        List<CompletedCourse> completedCourses = completedCourseRepository.findByUser(existingUser);

        List<CompletedCourseDto> completedCourseDtos = new ArrayList<>();
        BigDecimal totalDistance = BigDecimal.ZERO;

        for (final CompletedCourse completedCourse : completedCourses) {
            completedCourseDtos.add(CompletedCourseMapper.toCompletedCourseDto(completedCourse));
            totalDistance = totalDistance.add(completedCourse.getDistance());
        }

       return  CompletedCourseMapper.toCompletedCourseListResponseDto(
                completedCourses.size(),
                totalDistance,
                completedCourseDtos
        );

    }

    @Transactional(readOnly = true)
    public ReviewListResponseDto getReviews(User user) {
        User existingUser = userService.getExistingUser(user);

        List<CompletedCourse> completedCourses = completedCourseRepository.findByUser(existingUser);

        List<ReviewDto> reviewedDtos = new ArrayList<>();
        List<CompletedCourseDto> completedCourseDtos = new ArrayList<>();

        for (final CompletedCourse completedCourse : completedCourses) {
            if (completedCourse.getIsReviewed().equals(TRUE)) {
                reviewedDtos.add(ReviewMapper.toReviewDto(completedCourse));
            } else completedCourseDtos.add(CompletedCourseMapper.toCompletedCourseDto(completedCourse));
        }

       return  ReviewMapper.toReviewListResponseDto(
                completedCourses.size(),
                reviewedDtos.size(),
                reviewedDtos,
                completedCourseDtos
        );
    }

    @Transactional
    public ReviewDto createReview(Long completedCourseId, User user, CreateReviewRequestDto createReviewRequestDto) {
        User existingUser = userService.getExistingUser(user);

        CompletedCourse completedCourse = completedCourseRepository.findById(completedCourseId)
                .orElseThrow(() -> new Exception404(CourseExceptionStatus.COMPLETED_COURSE_NOT_FOUND));

        if (!completedCourse.getUser().getUserId().equals(existingUser.getUserId())) {
            throw new Exception403(CourseExceptionStatus.USER_NOT_AUTHORIZED);
        }

        if (completedCourse.getIsReviewed().equals(TRUE)) {
            throw new Exception409(CourseExceptionStatus.REVIEW_ALREADY_EXISTS);
        }

        List<ReviewImage> reviewImages = new ArrayList<>();
        Optional.ofNullable(createReviewRequestDto.base64EncodedImageList())
                .ifPresent(images -> {
                    List<String> imageUrls = uploadReviewImages(images);

                    for (final String imageUrl : imageUrls) {
                        reviewImages.add(ReviewMapper.toReviewImage(imageUrl, completedCourse));
                    }
                });

        completedCourse.addReview(
                createReviewRequestDto.content(),
                createReviewRequestDto.score(),
                reviewImages
        );

        return ReviewMapper.toReviewDto(completedCourse);
    }

    private List<String> uploadReviewImages(List<String> base64EncodedImageList) {
        return base64EncodedImageList.stream()
                .map(s3ImageClient::base64ImageToS3)
                .toList();
    }

    @Transactional(readOnly = true)
    public double getDistanceTotal(User user) {
        return completedCourseRepository.findByUser(userService.getExistingUser(user))
            .stream()
            .mapToDouble(course -> course.getDistance().doubleValue())
            .sum();
    }
}
