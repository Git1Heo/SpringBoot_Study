package com.icia.member.entity;

import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_imcrement
    @Column(name = "member_id") //별도 멀럼이름 지정할 때
    private Long id;

    @Column(length = 50, unique = true, name="memberEmail") //카멜케이스로 이름 지정시 대문자 소문자로 바꾸고 언더바 추가
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    //Column 생략하면 default 크기 255로 지정됨
    private String memberName;

    /*
        DTO클래스 객체를 전달받아 Entity  클래스 필드랎으로 세팅하고
        Entity 객체를 리턴하는 메소드 선언
     */

    // static(정적)메소드 : 클래스메소드, 객체를 만들지 않고도 바로 호출 가능
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
    }

}
