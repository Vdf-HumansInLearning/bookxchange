package com.bookxchange.service;

import com.bookxchange.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
   private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository=memberRepository;
    }
    @Transactional
    public void updatePointsToMemberByID(String memberID){
        memberRepository.updatePointsToMember(10, memberID);
    }
}
