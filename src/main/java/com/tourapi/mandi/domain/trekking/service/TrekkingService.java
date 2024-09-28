package com.tourapi.mandi.domain.trekking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tourapi.mandi.domain.badge.entity.BadgeType;
import com.tourapi.mandi.domain.badge.service.BadgeService;
import com.tourapi.mandi.domain.course.CourseExceptionStatus;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.course.entity.Coordinate;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.domain.course.repository.CompletedCourseRepository;
import com.tourapi.mandi.domain.course.repository.CourseRepository;
import com.tourapi.mandi.domain.course.service.CompletedCourseService;
import com.tourapi.mandi.domain.trekking.TrekkingSessionExceptionStatus;
import com.tourapi.mandi.domain.trekking.dto.TrekkingFinishRequestDto;
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
	private final CourseRepository courseRepository;
	private final TrekkingSessionRepository trekkingSessionRepository;
	private final CompletedCourseRepository completedCourseRepository;

	private final CompletedCourseService completedCourseService;
	private final BadgeService badgeService;

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
			sessionBuilder.endPoint(course.getEndLocationCoordinate());
			trekkingSessionRepository.save(sessionBuilder.build());
			return true;
		}

		// 코스 끝 지점에 인접해 있는 경우
		if (LocationUtil.isWithin100Meters(userLocation, course.getEndLocationCoordinate())) {
			sessionBuilder.startPoint(course.getEndLocationCoordinate());
			sessionBuilder.endPoint(course.getStartLocationCoordinate());
			trekkingSessionRepository.save(sessionBuilder.build());
			return true;
		}
		return false;
	}

	public TrekkingStartResponseDto isTrekkingFinished(
		User user,
		Long courseId,
		TrekkingFinishRequestDto request
	) {
		if (!userJpaRepository.existsById(user.getUserId())) {
			throw new Exception404(UserExceptionStatus.USER_NOT_FOUND);
		}
		Course course = getCourseById(courseId);
		TrekkingSession trekkingSession = findSession(user, courseId);

		if (isTrekkingFinished(request, trekkingSession)) {
			// 완주 코스에 추가
			CompletedCourse completedCourse = CompletedCourse.notReviewedBuilder()
				.user(user)
				.course(course)
				.startedAt(trekkingSession.getStartedAt())
				.completedAt(request.completedAt())
				.build();

			// 뱃지 취득
			double prevDistance = completedCourseService.getDistanceTotal(user);
			double currDistance = course.getDistance().doubleValue();

			// 첫 번째 완주인 경우
			if (prevDistance == 0) {
				badgeService.saveBadge(BadgeType.BEGINNING_OF_COMPLETION, user);
			}
			// 8km 이상일 경우
			if (prevDistance + currDistance >= 8) {
				badgeService.saveBadge(BadgeType.WALKED_10000_STEPS, user);
			}

			// 트레킹 세션 종료
			completedCourseRepository.save(completedCourse);
			trekkingSessionRepository.delete(trekkingSession);
			return TrekkingMapper.mapToTrekkingStartSuccessResponseDto();
		}
		return TrekkingMapper.mapToTrekkingStartFailResponseDto();
	}

	private Course getCourseById(Long courseId) {
		return courseRepository.findById(courseId)
			.orElseThrow(() -> new Exception404(CourseExceptionStatus.COURSE_NOT_FOUND));
	}

	private TrekkingSession findSession(User user, Long courseId) {
		return trekkingSessionRepository.findById(getKey(user.getUserId(), courseId))
			.orElseThrow(() -> new Exception404(TrekkingSessionExceptionStatus.TREKKING_NOT_FOUND));
	}

	private String getKey(Long userId, Long courseId) {
		return "Trekking-session-" + userId + "-" + courseId;
	}

	private boolean isTrekkingFinished(TrekkingFinishRequestDto request, TrekkingSession session) {
		// 사용자의 현재 위치가 끝 지점과 100m 이하로 인접해 있는 경우 && 종료 시간이 시작 시간보다 선행되는 경우
		return LocationUtil.isWithin100Meters(request.userLocation(), session.getEndPoint())
			&& request.completedAt().isAfter(session.getStartedAt());
	}
}
