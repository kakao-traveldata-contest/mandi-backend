package com.tourapi.mandi.domain.course.repository;

import static com.tourapi.mandi.domain.course.entity.QCourse.course;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.entity.Coordinate;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.domain.course.entity.CoursePreference;
import com.tourapi.mandi.domain.course.entity.DifficultyLevel;
import com.tourapi.mandi.domain.course.entity.DurationLevel;
import com.tourapi.mandi.domain.course.entity.QCoordinate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private static final String DESC = "DESC";

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Course> findCoursesInBound(final Coordinate ne, final Coordinate sw) {
        return jpaQueryFactory.selectFrom(course)
                .where(course.isNotNull().andAnyOf(
                                pointInBound(course.midPoint, ne, sw),
                                pointInBound(course.startPoint.coordinate, ne, sw),
                                pointInBound(course.endPoint.coordinate, ne, sw)
                        )
                )
                .fetch();
    }

    private Predicate pointInBound(final QCoordinate point, Coordinate ne, Coordinate sw) {
        return point.latitude.between(sw.getLatitude(), ne.getLatitude())
                .and(point.longitude.between(sw.getLongitude(), ne.getLongitude()));
    }

    @Override
    public Page<Course> findCoursesBySearch(CourseSearchDto courseSearch) {
        return new PageImpl<>(queryItems(courseSearch), courseSearch.getPageable(), getTotalCount(courseSearch));
    }

    private List<Course> queryItems(CourseSearchDto courseSearch) {
        return jpaQueryFactory.selectFrom(course)
                .where(applyFilters(courseSearch))
                .limit(courseSearch.size())
                .offset(courseSearch.getOffset())
                .orderBy(applySorting(courseSearch))
                .fetch();
    }

    private Long getTotalCount(CourseSearchDto courseSearch) {
        return jpaQueryFactory.select(course.count())
                .from(course)
                .where(applyFilters(courseSearch))
                .fetchOne();
    }

    private BooleanExpression applyFilters(CourseSearchDto courseSearch) {
        return course.isNotNull()
                .and(keywordEq(courseSearch.keyword()))
                .and(difficultyContains(courseSearch.difficulties()))
                .and(ratingGoe(courseSearch.rating()));
    }

    // 코스명 검색
    private BooleanExpression keywordEq(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return course.name.containsIgnoreCase(keyword);
        }
        return null;
    }

    // 난이도 필터링
    private BooleanExpression difficultyContains(final List<DifficultyLevel> difficulties) {
        if (difficulties != null && !difficulties.isEmpty()) {
            return course.difficulty.in(difficulties);
        }
        return null;
    }

    // 별점 필터링
    private BooleanExpression ratingGoe(final Integer rating) {
        if (rating != null) {
            return course.ratingAverage.goe(rating);
        }
        return null;
    }

    private OrderSpecifier<?> applySorting(CourseSearchDto courseSearch) {
        if (courseSearch.orderByDirection().equalsIgnoreCase(DESC)) {
            return course.distance.desc();
        }
        return course.distance.asc();
    }

    @Override
    public List<Course> findPreferredCourse(final CoursePreference preference) {
        return jpaQueryFactory.selectFrom(course)
                .where(course.isNotNull()
                        .and(difficultyContains(Collections.singletonList(preference.getDifficultyLevel())))
                        .and(durationBetween(preference.getDurationLevel())))
                .fetch();
    }

    // 거리 필터링
    private BooleanExpression durationBetween(final DurationLevel level) {
        if (level != null) {
            return course.duration
                    .goe(level.getStartInclusive())
                    .and(course.duration.lt(level.getEndInclusive()));
        }
        return null;
    }
}
