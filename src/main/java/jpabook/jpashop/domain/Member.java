package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Adderess adderess;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
