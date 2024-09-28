package com.tourapi.mandi.domain.trekking.util;

import com.tourapi.mandi.domain.course.entity.Coordinate;

public final class LocationUtil {

	// 지구 반경 (단위: km)
	private static final double EARTH_RADIUS = 6371.0;
	public static final double ONE_KILOMETER_TO_METER = 0.1;

	/**
	 * 두 지점 간의 거리를 Haversine 공식을 이용하여 계산합니다. (단위: km)
	 *
	 * @param lat1 첫 번째 지점의 위도
	 * @param lon1 첫 번째 지점의 경도
	 * @param lat2 두 번째 지점의 위도
	 * @param lon2 두 번째 지점의 경도
	 * @return 두 지점 간의 거리 (단위: km)
	 */
	public static double calculateDistance(
		double lat1, double lon1,
		double lat2, double lon2
	) {
		double deltaLat = Math.toRadians(Math.abs(lat1 - lat2));
		double deltaLng = Math.toRadians(Math.abs(lon1 - lon2));

		double sinDeltaLat = Math.sin(deltaLat / 2);
		double sinDeltaLng = Math.sin(deltaLng / 2);

		double squareRoot = Math.sqrt(sinDeltaLat * sinDeltaLat
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * sinDeltaLng * sinDeltaLng
		);
		return 2 * EARTH_RADIUS * Math.asin(squareRoot);
	}

	/**
	 * 특정 지점이 기준 지점에서 반경 100m 이내에 있는지 확인합니다.
	 *
	 * @param userLocation 사용자(현재 위치)의 [위도,경도]
	 * @param targetLocation 기준 지점의 [위도, 경도]
	 * @return 반경 100m 이내에 있으면 true, 그렇지 않으면 false
	 */
	public static boolean isWithin100Meters(Coordinate userLocation, Coordinate targetLocation) {
		// 거리가 0.1km 이하이면 true 반환
		return calculateDistance(
			userLocation.getLatitude(), userLocation.getLongitude(),
			targetLocation.getLatitude(), targetLocation.getLongitude()
		) <= ONE_KILOMETER_TO_METER;
	}
}
