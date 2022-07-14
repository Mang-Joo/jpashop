package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Member member) {
        entityManager.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return entityManager.find(Member.class, id);
    }

    /*
    * EntityManager를 SpringBoot가 다 해준다.
    * yml설정 파일을 가지고 다 만들어준다.
    * */

    /*
    * 커맨드와 쿼리를 분리하자.
    * return은 ID정도만 준다.
    * */
}
