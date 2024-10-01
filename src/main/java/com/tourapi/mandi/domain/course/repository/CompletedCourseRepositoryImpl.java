package com.tourapi.mandi.domain.course.repository;

import static com.tourapi.mandi.domain.course.entity.QCompletedCourse.completedCourse;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourapi.mandi.domain.course.dto.CourseReviewSearch;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.course.util.CourseReviewSortType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@RequiredArgsConstructor
public class CompletedCourseRepositoryImpl implements CompletedCourseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private static final int EXCELLENT = 5;
    private static final int VERY_GOOD = 4;
    private static final int AVERAGE = 3;
    private static final int POOR = 2;
    private static final int TERRIBLE = 1;

    @Override
    public Page<CompletedCourse> findReviewsBySearch(Long courseId, CourseReviewSearch courseReviewSearch) {
        return new PageImpl<>(queryItems(courseId, courseReviewSearch), courseReviewSearch.getPageable(),
                getTotalCount(courseId));
    }

    @Override
    public ReviewSummary getReviewSummary(Long courseId) {
        Tuple result = jpaQueryFactory
                .select(
                        completedCourse.count(),
                        completedCourse.reviewScore.avg(),
                        completedCourse.reviewScore.eq(EXCELLENT).count(),
                        completedCourse.reviewScore.eq(VERY_GOOD).count(),
                        completedCourse.reviewScore.eq(AVERAGE).count(),
                        completedCourse.reviewScore.eq(POOR).count(),
                        completedCourse.reviewScore.eq(TERRIBLE).count()
                )
                .from(completedCourse)
                .where(applyFilter(courseId))
                .fetchOne();

        return createReviewSummary(result);
    }

    private ReviewSummary createReviewSummary(Tuple result) {
        return Optional.ofNullable(result)
                .map(r -> ReviewSummary.builder()
                        .totalReviewCount(r.get(0, Long.class))
                        .averageReviewScore(r.get(1, Double.class))
                        .excellentCount(r.get(2, Long.class))
                        .veryGoodCount(r.get(3, Long.class))
                        .averageCount(r.get(4, Long.class))
                        .poorCount(r.get(5, Long.class))
                        .terribleCount(r.get(6, Long.class))
                        .build())
                .orElseGet(() -> ReviewSummary.builder()
                        .totalReviewCount(0L)
                        .averageReviewScore(0.0)
                        .excellentCount(0L)
                        .veryGoodCount(0L)
                        .averageCount(0L)
                        .poorCount(0L)
                        .terribleCount(0L)
                        .build());
    }

    private List<CompletedCourse> queryItems(Long courseId, CourseReviewSearch courseReviewSearch) {
        return jpaQueryFactory.selectFrom(completedCourse)
                .join(completedCourse.course).fetchJoin()
                .join(completedCourse.user).fetchJoin()
                .leftJoin(completedCourse.reviewImageList).fetchJoin()
                .where(applyFilter(courseId))
                .limit(courseReviewSearch.size())
                .offset(courseReviewSearch.getOffset())
                .orderBy(applySorting(courseReviewSearch.sortType()))
                .fetch();
    }

    private Long getTotalCount(Long courseId) {
        return jpaQueryFactory.select(completedCourse.count())
                .from(completedCourse)
                .where(applyFilter(courseId))
                .fetchOne();
    }



    private BooleanExpression applyFilter (Long courseId) {
        return completedCourse.isNotNull()
                .and(completedCourse.course.courseId.eq(courseId))
                .and(completedCourse.isReviewed.eq(true));
    }

    private OrderSpecifier<?> applySorting(CourseReviewSortType courseReviewSortType) {
        return switch (courseReviewSortType) {
            case HIGHEST_RATING -> completedCourse.reviewScore.desc();
            case LOWEST_RATING -> completedCourse.reviewScore.asc();
            default -> completedCourse.reviewedAt.desc();
        };
    }
}
