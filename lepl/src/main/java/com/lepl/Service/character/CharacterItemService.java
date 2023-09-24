package com.lepl.Service.character;

import com.lepl.Repository.character.CharacterItemRepository;
import com.lepl.domain.character.CharacterItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 모드
@RequiredArgsConstructor
public class CharacterItemService {

    @Autowired
    CharacterItemRepository characterItemRepository;

    /*
        save, findOne, findAll, remove
    */

    //아이템 저장
    @Transactional
    public void save(CharacterItem characterItem) {
        characterItemRepository.save(characterItem);
    }

    //아이템 1개 조회
    public CharacterItem findOnd(Long id) {
        return characterItemRepository.findOne(id);
    }

    //아이템 전체 조회
    public List<CharacterItem> findAll() {
        return characterItemRepository.findAll();
    }

    //소유 아이템 삭제
    @Transactional
    public void remove(CharacterItem characterItem) {
        characterItemRepository.remove(characterItem);
    }
}
