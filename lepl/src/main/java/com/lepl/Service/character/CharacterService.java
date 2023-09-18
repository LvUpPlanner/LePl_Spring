package com.lepl.Service.character;

import com.lepl.Repository.character.CharacterRepository;
import com.lepl.domain.character.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 읽기모드
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;

    /**
     * save, findOne, remove
     */
    @Transactional // 쓰기모드
    public Long join(Character character) { characterRepository.save(character); return character.getId(); }

    public Character findOne(Long characterId) { return characterRepository.findOne(characterId); }

    @Transactional
    public void remove(Character character) { characterRepository.remove(character); }
}
