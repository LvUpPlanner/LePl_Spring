package com.lepl.Service.character;

import com.lepl.Repository.character.CharacterRepository;
import com.lepl.Repository.character.ExpRepository;
import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 읽기모드
@RequiredArgsConstructor
public class ExpService {
    private final ExpRepository expRepository;

    /**
     * save, findOne, remove
     */
    @Transactional // 쓰기모드
    public Long join(Exp exp) { expRepository.save(exp); return exp.getId(); }

    public Exp findOne(Long expId) { return expRepository.findOne(expId); }

    @Transactional
    public void remove(Exp exp) { expRepository.remove(exp); }
}
