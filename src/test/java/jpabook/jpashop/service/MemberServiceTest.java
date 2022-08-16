package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void signInTest() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);
        Member one = memberService.findOne(member.getId());

        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }


}