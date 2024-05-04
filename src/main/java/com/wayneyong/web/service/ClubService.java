package com.wayneyong.web.service;

import com.wayneyong.web.dto.ClubDto;
import com.wayneyong.web.models.Club;

import java.util.List;

public interface ClubService {

    List<ClubDto> findAllClubs();

    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);
}
