package com.bookxchange.service;

import com.bookxchange.exception.RegisterException;
import com.bookxchange.model.MemberEntity;
import com.bookxchange.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "userService")
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void updatePointsToSupplierByID(String memberID) {
        memberRepository.updatePointsToMember(10d, memberID);
    }

    public void updatePointsToClientById(Double bookPoints, String memberId) {
        memberRepository.updatePointsToMember(bookPoints, memberId);
    }

    public Double getPointsByMemberId(String memberId) {
        return memberRepository.getPointsByMemberId(memberId);
    }

    public MemberEntity getMemberEntity(String username) {
        return memberRepository.getMembersEntityByUsername(username);
    }

    public void saveMember(MemberEntity member) {

        try {
            memberRepository.save(member);
        } catch (Exception exception) {
            if (exception.getMessage().contains("constraint")) {
                throw new RegisterException(String.format("Username %s or e-mail %s already exists.", member.getUsername(), member.getEmailAddress()));
            } else {
                throw exception;
            }
        }
    }


    public MemberEntity findByUuid(String uuid) {
        return memberRepository.getMembersEntitiesByMemberUserUuid(uuid);
    }
}
