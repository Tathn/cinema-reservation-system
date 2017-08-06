package com.springframework.cinema.domain.screening;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-06-03.
 */
@Repository
public interface ScreeningRepository extends BaseRepository<Screening, Long> {
	Collection<Screening> findScreeningsByDate(Date date);
	Collection<Screening> findScreeningsByDateAndRoom(Date date, Room room);
}
