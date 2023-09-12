package com.lvpl.domain.character;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter @Setter
public class Coin {
    @Id @GeneratedValue
    @Column(name = "coin_id")
    private Long id;

    private Long coinAll;
}
