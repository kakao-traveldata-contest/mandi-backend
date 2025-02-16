package com.tourapi.mandi.domain.course.repository;

import static com.tourapi.mandi.domain.course.entity.QCompletedCourse.completedCourse;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourapi.mandi.domain.course.dto.CourseReviewSearch;
import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.course.util.CourseReviewSortType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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
        List<Integer> scores = jpaQueryFactory
                .select(completedCourse.reviewScore)
                .from(completedCourse)
                .where(applyFilter(courseId))
                .fetch();

        return createReviewSummary(scores);
    }

    private ReviewSummary createReviewSummary(List<Integer> scores) {
        if (scores.isEmpty()) {
            return ReviewSummary.builder()
                    .averageReviewScore(BigDecimal.ZERO)
                    .excellentCount(0L)
                    .veryGoodCount(0L)
                    .averageCount(0L)
                    .poorCount(0L)
                    .terribleCount(0L)
                    .build();
        }

        BigDecimal averageReviewScore = scores.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(scores.size()), 1, RoundingMode.HALF_UP);

        Long excellentCount = getCountFilterByReviewScore(scores, EXCELLENT);
        Long veryGoodCount = getCountFilterByReviewScore(scores, VERY_GOOD);
        Long averageCount = getCountFilterByReviewScore(scores, AVERAGE);
        Long poorCount = getCountFilterByReviewScore(scores, POOR);
        Long terribleCount = getCountFilterByReviewScore(scores, TERRIBLE);

        return ReviewSummary.builder()
                .averageReviewScore(averageReviewScore)
                .excellentCount(excellentCount)
                .veryGoodCount(veryGoodCount)
                .averageCount(averageCount)
                .poorCount(poorCount)
                .terribleCount(terribleCount)
                .build();
    }

    private Long getCountFilterByReviewScore(List<Integer> scores, int targetScore) {
        return scores.stream()
                .filter(s -> s == targetScore)
                .count();
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
