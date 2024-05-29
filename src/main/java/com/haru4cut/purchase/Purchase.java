package com.haru4cut.purchase;


import com.haru4cut.domain.user.Users;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Purchase {

    @Id
    @Column(name = "orderNum")
    private String orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    public Purchase(String orderNum, Users users) {
        this.orderNum = orderNum;
        this.users = users;
    }

}
