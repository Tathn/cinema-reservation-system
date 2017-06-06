package com.springframework.cinema.domain.room;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.springframework.cinema.infrastructure.model.BaseRepository;

/**
 * Created by Patryk on 2017-05-27.
 */
@Repository
public interface RoomSeatRepository extends BaseRepository<RoomSeat, Long> {
    Collection<RoomSeat> findRoomSeatsByRoomId(Long roomId);
    @Transactional
    void deleteByRoomId(Long roomId);

}
