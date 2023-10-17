package com.realfinal.toot.db.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
@Table(name = "word")
@Entity
public class Word extends BaseEntity {

    //    word    varchar(255) not null unique,
    @Column(name = "word", insertable = false, updatable = false, unique = true)
    @NotNull
    private String word;

    //    meaning text         null,
    @Column(name = "meaning")
    private String meaning;

    @Builder
    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
}
