package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당 Entity클래스이름, PK타입>
// JpaRepository 상속시 어노테이션 필요X
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    //이메일을 조건으로 회원 조회
    /*
        메소드 리턴타입 : MemberEntity
        메소드 이름 : findByEmail ( select => find, 조건 => By, 조건 매개변수 => Email )
        메소드 매개변수 : String memberEmail
     */

    MemberEntity findByMemberEmail(String memberEmail);
}
