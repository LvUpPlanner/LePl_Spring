package com.lvpl.domain.character;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Exp {
    @Id @GeneratedValue
    @Column(name = "exp_id")
    private Long id;

    private Long expAll;
}
