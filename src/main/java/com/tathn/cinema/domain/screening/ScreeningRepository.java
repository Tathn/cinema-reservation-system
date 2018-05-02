package com.tathn.cinema.domain.screening;

import java.util.Collection;
import java.util.Date;

import com.tathn.cinema.infrastructure.model.BaseRepository;
import org.springframework.stereotype.Repository;

import com.tathn.cinema.domain.room.Room;

/**
 * Created by Patryk on 2017-06-03.
 */
@Repository
public interface ScreeningRepository extends BaseRepository<Screening, Long> {
	Collection<Screening> findScreeningsByDate(Date date);
	Collection<Screening> findScreeningsByDateAndRoom(Date date, Room room);
}
