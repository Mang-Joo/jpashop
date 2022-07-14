package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional /* 테스트가 끝나면 롤백을 한다. */
    @Rollback(false) /* 롤백을 안하고 커밋을 한다. */
    public void testMember() throws Exception {
        Member member = new Member();
        member.setUserName("memberA");

        Long saveID = memberRepository.save(member);
        Member findMember = memberRepository.find(saveID);

        Assertions.assertThat(findMember.getId()).isEqualTo(saveID);
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("(findMember==member) = " + (findMember == member));

    }

}