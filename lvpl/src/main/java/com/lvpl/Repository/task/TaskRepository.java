package com.lvpl.Repository.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor // 생성자 주입 + 엔티티매니저 주입
public class TaskRepostiory {
    private final EntityManager em;

    public
}
