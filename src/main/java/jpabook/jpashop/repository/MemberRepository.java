package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager entityManager;

    public void save(Member member) {
        entityManager.persist(member);
        // Member 트랜잭션 안에서 Member가 db에 저장된다.
    }

    public Member findOne(Long id) {
        return entityManager.find(Member.class, id);
        //type, pk 를 넣어주면 된다.
    }

    public List<Member> findAll() {
        return entityManager
                .createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
        //JPQL
        //SQL이랑 문법은 거의 똑같은데 FROM 에 대상이 테이블이 아니라 Entity가 되어야 한다.
    }

    public List<Member> findByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        //파라미터를 set하여 사용
    }

}
