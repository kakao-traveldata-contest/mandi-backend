package com.tourapi.mandi.domain.trekking.repository;

import org.springframework.data.repository.CrudRepository;

import com.tourapi.mandi.domain.trekking.entity.TrekkingSession;

public interface TrekkingSessionRepository extends CrudRepository<TrekkingSession, String> {

}
