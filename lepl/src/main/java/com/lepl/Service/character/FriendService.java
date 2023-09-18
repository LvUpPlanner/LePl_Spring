package com.lepl.Service.character;

import com.lepl.Repository.character.CharacterItemRepository;
import com.lepl.Repository.character.FriendRepository;
import com.lepl.domain.character.CharacterItem;
import com.lepl.domain.character.Friend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기모드
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    /**
     * save, findOne, findAll, remove
     */
    @Transactional // 쓰기모드
    public Long join(Friend friend) { friendRepository.save(friend); return friend.getId(); }

    public Friend findOne(Long friendId) { return friendRepository.findOne(friendId); }
    public List<Friend> findAll() {return friendRepository.findAll();}

    @Transactional
    public void remove(Friend friend) { friendRepository.remove(friend); }
}
