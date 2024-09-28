package com.tourapi.mandi.domain.trekking.entity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import com.tourapi.mandi.domain.course.entity.Coordinate;

import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash(value = "Trekking-session")
public class TrekkingSession {

	@Id
	private String sessionId;

	@Indexed
	private Long userId;

	@Indexed
	private Long courseId;

	private Coordinate startPoint;

	@TimeToLive(unit = TimeUnit.HOURS)
	private Long expirationHours = 12L;

	@Builder
	public TrekkingSession(Long userId,  Long courseId, Coordinate startPoint) {
		this.sessionId = "Trekking-session-" + userId + "-" + courseId;
		this.userId = userId;
		this.courseId = courseId;
		this.startPoint = startPoint;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		TrekkingSession other = (TrekkingSession) obj;
		return Objects.equals(getSessionId(), other.getSessionId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSessionId());
	}
}
