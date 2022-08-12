package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    private LocalDateTime orderDate; //주문시간 (Hibernate가 지원해준다.)

    @Enumerated(EnumType.STRING) //ORDINAL 타입을 사용하면 중간에 값이 들어갈 경우 큰일 난다. String을 사용해야 한다.
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


}
