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

    @Test
    public void validationTest() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

//        memberService.join(member1);
//        memberService.join(member2);
//
//        // 예외 터져야함
//        // 터짐 :) java.lang.IllegalStateException: 이미 존재하는 회원입니다.

        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }
        System.out.println("이거 나오면 곤란.");
    }


}