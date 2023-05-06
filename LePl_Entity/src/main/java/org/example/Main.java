package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.Domain.*;
import org.example.Domain.Character;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LePl");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Member member = new Member();
            member.setNickname("yujin");
            em.persist(member);

            Character character = new Character();
            character.setLevel(1L);
            character.setMember(member);
            em.persist(character);

            Coin coin = new Coin();
            coin.setCharacter(character);
            coin.setCoin_all(100L);
            em.persist(coin);

            Exp exp = new Exp();
            exp.setCharacter(character);
            exp.setExp_all(200L);
            em.persist(exp);

            Item item = new Item();
            item.setCharacter(character);
            item.setItem_one_id(10L); //item 번호
            item.setWearingStatus(WearingStatus.OFF);
            em.persist(item);

            Profile profile = new Profile();
            profile.setMember(member);
            em.persist(profile);

            Lists lists = new Lists();
            lists.setLists_date(LocalDate.now().atStartOfDay());
            lists.setMember(member);
            em.persist(lists);

            Task task = new Task();
            task.setContent("Study JPA");
            task.setStart("17:00");
            task.setEnd("18:00");
            em.persist(task);

            Lists_Task lists_task = new Lists_Task();
            lists_task.setLists(lists);
            lists_task.setTask(task);
            lists_task.setCount(1);
            em.persist(lists_task);

            Task_Status task_status = new Task_Status();
            task_status.setStatus(true);
            task_status.setTimerOnOff(false);
            em.persist(task_status);

            task.setTask_status(task_status);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}