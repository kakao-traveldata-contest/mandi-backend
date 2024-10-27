package com.tourapi.mandi.domain.course.repository;

import com.tourapi.mandi.domain.course.dto.CourseSearchDto;
import com.tourapi.mandi.domain.course.entity.Coordinate;
import com.tourapi.mandi.domain.course.entity.Course;
import com.tourapi.mandi.domain.course.entity.CoursePreference;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CourseRepositoryCustom {

    /**
     * 좌표 기반 영역 검색을 수행합니다.
     * <p>예를 들어 좌표 상에서 남서쪽(<code>sw</code>)과 북동쪽(<code>ne</code>) 지점이 주어졌을 때: </p>
     * <ul>
     * <li>검색대상 지점: (<code>lat</code>, <code>lng</code>)</li>
     * <li>남서쪽 지점: (<code>sw_lat</code>, <code>sw_lng</code>)</li>
     * <li>북동쪽 지점: (<code>ne_lat</code>, <code>ne_lng</code>)</li>
     * </ul>
     * <p>해당 영역 내에 포함되는지 확인하는 기본 원리는 다음과 같습니다.</p>
     * <blockquote><pre>
     * 위도와 경도 범위에 모두 포함되는 경우 참입니다.
     *
     * - 위도 범위: sw_lat <= lat <= ne_lat
     * - 경도 범위: sw_lng <= lng <= ne_lng
     * </pre></blockquote>
     *
     * @author LEE-sh1673
     */
    List<Course> findCoursesInBound(Coordinate ne, Coordinate sw);


    /**
     * 코스 선호도 기반 검색을 수행합니다.
     * @param preference 코스 선호도
     * @return 코스 선호도에 부합되는 코스 목록
     * @author LEE-sh1673
     */
    List<Course> findPreferredCourse(CoursePreference preference);

    Page<Course> findCoursesBySearch(CourseSearchDto courseSearch);
}
