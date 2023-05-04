package com.lvpl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Coin {
    @Id @GeneratedValue
    @Column(name = "coin_id")
    private Long id;

    private Long coinAll;
}
