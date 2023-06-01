package com.lvpl.Service.task;


import com.lvpl.Repository.task.ListsRepository;
import com.lvpl.domain.task.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ListsService {
    private final ListsRepository listsRepository;

    /**
     * save, findOne, findByDate, findAll, remove, findByToday
     */

    @Transactional // 쓰기모드
    public void join(Lists lists) {
        listsRepository.save(lists);
    }

    public Lists findOne(Long id) {
        return listsRepository.findOne(id);
    }
    public List<Lists> findByDate(LocalDateTime start, LocalDateTime end) {
        return listsRepository.findByDate(start, end);
    }
    public List<Lists> findByCurrent(LocalDateTime curDate) {
        return listsRepository.findByCurrent(curDate);
    }
    public List<Lists> findAll() {
        return listsRepository.findAll();
    }
    public List<Lists> findAllWithTask() {
        return listsRepository.findAllWithTask();
    }
    public void remove(Lists lists) {
        listsRepository.remove(lists);
    }
}
