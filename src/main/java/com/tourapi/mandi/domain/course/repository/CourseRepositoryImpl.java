package com.tourapi.mandi.domain.course.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.domain.course.entity.QCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Course> findCoursesBySearch(final CourseSearchDto courseSearch) {
        List<Course> courses = queryItems(courseSearch);
        return new PageImpl<>(courses, courseSearch.getPageable(), courses.size());
    }

    private List<Course> queryItems(CourseSearchDto courseSearch) {
        return jpaQueryFactory.selectFrom(QCourse.course)
                .where(applyFilters(courseSearch))
                .limit(courseSearch.size())
                .offset(courseSearch.getOffset())
                .orderBy(applySorting(courseSearch))
                .fetch();
    }

    private BooleanExpression applyFilters(CourseSearchDto courseSearch) {
        QCourse course = QCourse.course;
        BooleanExpression predicate = course.isNotNull();

        // 코스명 검색
        if (courseSearch.keyword() != null && !courseSearch.keyword().isEmpty()) {
            predicate = predicate.and(course.name.containsIgnoreCase(courseSearch.keyword()));
        }
        // 난이도 필터링
        if (courseSearch.difficulties() != null && !courseSearch.difficulties().isEmpty()) {
            predicate = predicate.and(course.difficulty.in(courseSearch.getDifficultyKeywords()));
        }
        // 별점 필터링
        if (courseSearch.rating() != null) {
            predicate = predicate.and(course.ratingAverage.goe(courseSearch.rating()));
        }
        return predicate;
    }

    private OrderSpecifier<?> applySorting(CourseSearchDto courseSearch) {
        QCourse course = QCourse.course;

        if (courseSearch.orderByDirection().equalsIgnoreCase("DESC")) {
            return course.distance.desc();
        }
        return course.distance.asc();
    }
}
