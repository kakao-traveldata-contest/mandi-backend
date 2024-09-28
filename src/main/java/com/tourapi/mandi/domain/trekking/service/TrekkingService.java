package com.tourapi.mandi.domain.trekking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.domain.course.entity.Coordinate;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.domain.course.repository.CourseRepository;
import com.tourapi.mandi.domain.trekking.dto.TrekkingStartResponseDto;
import com.tourapi.mandi.domain.trekking.entity.TrekkingSession;
import com.tourapi.mandi.domain.trekking.repository.TrekkingSessionRepository;
import com.tourapi.mandi.domain.trekking.util.LocationUtil;
import com.tourapi.mandi.domain.trekking.util.TrekkingMapper;
import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception404;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TrekkingService {

	private final UserJpaRepository userJpaRepository;
	private final TrekkingSessionRepository sessionRepository;
	private final CourseRepository courseRepository;

	public TrekkingStartResponseDto findTrekkingStatus(
		User user,
		Long courseId,
		Coordinate currentUserLocation
	) {
		if (!userJpaRepository.existsById(user.getUserId())) {
			throw new Exception404(UserExceptionStatus.USER_NOT_FOUND);
		}
		if (isUserWithin100Meters(user, getCourseById(courseId), currentUserLocation)) {
			return TrekkingMapper.mapToTrekkingStartSuccessResponseDto();
		}
		return TrekkingMapper.mapToTrekkingStartFailResponseDto();
	}

	private boolean isUserWithin100Meters(
		User user,
		Course course,
		Coordinate userLocation
	) {
		TrekkingSession.TrekkingSessionBuilder sessionBuilder = TrekkingSession.builder()
			.userId(user.getUserId())
			.courseId(course.getCourseId());

		// 코스 시작 지점에 인접해 있는 경우
		if (LocationUtil.isWithin100Meters(userLocation, course.getStartLocationCoordinate())) {
			sessionBuilder.startPoint(course.getStartLocationCoordinate());
			sessionRepository.save(sessionBuilder.build());
			return true;
		}

		// 코스 끝 지점에 인접해 있는 경우
		if (LocationUtil.isWithin100Meters(userLocation, course.getEndLocationCoordinate())) {
			sessionBuilder.startPoint(course.getEndLocationCoordinate());
			sessionRepository.save(sessionBuilder.build());
			return true;
		}
		return false;
	}

	private Course getCourseById(Long courseId) {
		return courseRepository.findById(courseId)
			.orElseThrow(() -> new Exception404(CourseExceptionStatus.COURSE_NOT_FOUND));
	}
}
