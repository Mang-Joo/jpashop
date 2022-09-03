package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Getter
    @Setter
    public static class MemberForm {

        @NotEmpty(message = "회원 이름은 필수 입니다.")
        private String name;

        private String city;
        private String street;

        @Size(min = 5, message = "우편번호는 5개입니다.")
        private String zipcode;

    }
}
