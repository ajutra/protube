package com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryJpaEntity {
    @Id
    private String category_name;
}
