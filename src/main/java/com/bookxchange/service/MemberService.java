package com.bookxchange.service;

import com.bookxchange.model.MembersEntity;
import com.bookxchange.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service(value = "userService")
public class MemberService {
   private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository=memberRepository;
    }
    @Transactional
    public void updatePointsToSupplierByID(String memberID){
        memberRepository.updatePointsToMember(10d, memberID);
    }

    public void updatePointsToClientById(Double bookPoints,String memberId) {
        memberRepository.updatePointsToMember(bookPoints,memberId);
    }

    public Double getPointsByMemberId(String memberId) {return memberRepository.getPointsByMemberId(memberId);}

    public MembersEntity getMemberEntity (String username) {
        return memberRepository.getMembersEntityByUsername(username);
    }

    public void saveMember (MembersEntity member) {
         memberRepository.save(member);
    }


}
