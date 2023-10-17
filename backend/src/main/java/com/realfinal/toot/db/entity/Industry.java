package com.realfinal.toot.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "industry")
@Entity
public class Industry extends BaseEntity {

    @Column(name = "industry_class", length = 50)
    @NotNull
    private String industryClass;

    @Column(name = "wics", length = 50)
    @NotNull
    private String wics;

}
