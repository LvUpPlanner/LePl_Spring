package org.example.Domain;

import jakarta.persistence.*;

@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    private Long item_one_id;

    private Boolean wearing_status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getWearing_status() {
        return wearing_status;
    }

    public void setWearing_status(Boolean wearing_status) {
        this.wearing_status = wearing_status;
    }
}
