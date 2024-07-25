package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.repository.TrackRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import com.sparta.teamssc.domain.user.user.service.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    public TrackResponseDto createTrack(TrackRequestDto trackRequestDto) {

        Track track = Track.builder()
                .name(trackRequestDto.getName())
                .build();

        trackRepository.save(track);

        return TrackResponseDto.builder()
                .name(track.getName())
                .build();

    }

    @Transactional
    public TrackResponseDto updateTrack(Long trackId, TrackRequestDto trackRequestDto) {

        Track track = trackRepository.findById(trackId).orElseThrow(
                ()-> new NoSuchElementException("수정할 트랙을 찾지 못했습니다.")
        );

        if (track.getName().equals(trackRequestDto.getName())) {
            throw new IllegalArgumentException("트랙명을 같은 이름으로 변경할 수 없습니다.");
        }

        Track updatedTrack = track.setTrackName(trackRequestDto.getName());

        return TrackResponseDto.builder()
                .name(updatedTrack.getName())
                .build();
    }
}