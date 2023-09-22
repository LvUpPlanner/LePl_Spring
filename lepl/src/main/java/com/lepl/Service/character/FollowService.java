package com.lepl.Service.character;

import com.lepl.Repository.character.FollowRepository;
import com.lepl.domain.character.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기모드
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    /**
     * save, findOne, findAll, remove
     */
    @Transactional // 쓰기모드
    public Long join(Follow follow) { followRepository.save(follow); follow.setFollowerId(follow.getCharacter().getId()); return follow.getId(); }

    public Follow findOne(Long followId) { return followRepository.findOne(followId); }
    public List<Follow> findAll() {return followRepository.findAll();}

    @Transactional
    public void remove(Follow follow) { followRepository.remove(follow); }

    public List<Follow> findAllWithFollowing(Long characterId) { return followRepository.findAllWithFollowing(characterId); }
    public List<Follow> findAllWithFollower(Long characterId) { return followRepository.findAllWithFollower(characterId); }
}
