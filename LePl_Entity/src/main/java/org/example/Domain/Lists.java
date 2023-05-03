package org.example.Domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Lists {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISTS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private Date lists_date;

    @OneToMany(mappedBy = "lists")
    private List<Lists_Task> lists_tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLists_date() {
        return lists_date;
    }

    public void setLists_date(Date lists_date) {
        this.lists_date = lists_date;
    }

    public List<Lists_Task> getLists_tasks() {
        return lists_tasks;
    }

    public void setLists_tasks(List<Lists_Task> lists_tasks) {
        this.lists_tasks = lists_tasks;
    }
}
