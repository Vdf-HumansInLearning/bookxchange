package com.bookxchange.repository;

import com.bookxchange.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, UUID> {
    @Modifying
    @Query(value = "UPDATE members SET points = points+?1  WHERE member_uuid = ?2 ", nativeQuery = true)
    void updatePointsToMember(Double points, String memberId);

    @Query(value = "SELECT points from members where member_uuid = ?1", nativeQuery = true)
    Double getPointsByMemberId(String memberId);

    MemberEntity getMembersEntityByUsername(String username);

    MemberEntity getMembersEntitiesByMemberUserUuid(String uuid);
}
