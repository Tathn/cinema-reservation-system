package com.tathn.cinema.domain.room;

import com.tathn.cinema.infrastructure.model.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface RoomRepository extends BaseRepository<Room, Long> {
    Room findByName(String name);

}
