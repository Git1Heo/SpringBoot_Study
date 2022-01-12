package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository mr;

    @Override
    public MemberDetailDTO findById(Long memberId) {
        /*
            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴
            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴.
         */
        MemberEntity member = mr.findById(memberId).get();
        MemberDetailDTO memberDetailDTO =MemberDetailDTO.toMemberDetailDTO(member);
        System.out.println("memberDetailDTO.toString() = " + memberDetailDTO.toString());
        return memberDetailDTO;
    }

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        /*
            1. MemberSaveDTO -> MemberEntity에 옮기기(MemberEntity의 saveMember메소드)
            2. MemberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달
         */
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        //테스트코드에서 사용하기 위해 id값을 리턴함
        //리파지토리에 save메소드 없는데 문제가 없다 JpaRepository에 save메소드가 있다.

        //사용자가 입력한 이메일 중복체크
        MemberEntity emailCheckResult = mr.findByMemberEmail(memberSaveDTO.getMemberEmail());
        // 이메일 중복체크 결과가 null이 아니라면 예외를 발생시킴
        // 예외종류 : IllegalStateException , 예외메시지 : 중복 이메일입니다!!
        // throw : 예외를 호출한곳에 떠넘김
        if(emailCheckResult!=null){
            throw new IllegalStateException("중복된 이메일입니다.!!");
        }
        else
            return mr.save(memberEntity).getId();


    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // 1. 사용자가 입력한 이메일을 조건으로 DB에서 조회(select * from member_table where member_email=?)
        // JPA는 where할때 PK를 기준으로 한다. 예를들어 findById는 PK인 id를 기준으로 select한다.
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        // 2. 비밀번호 일치여부 확인
        if(memberEntity != null) {
                if (memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                    return true;
                } else {
                    return false;
                }
        }
        else
            return false;
    }
    
    // 전체목록
    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        // List<MemberEntity> memberEntityList => List<MemberDetailDTO>
        List<MemberDetailDTO> memberDetailDTOList = new ArrayList<>();
/*        for(int i=0;i<memberEntityList.size();i++) {
            MemberDetailDTO memberDTO=MemberDetailDTO.toMemberDetailDTO(memberEntityList.get(i));
            memberDetailDTOList.add(memberDTO);
        }*/

        // foreach
        for (MemberEntity m: memberEntityList)
            memberDetailDTOList.add(MemberDetailDTO.toMemberDetailDTO(m));
        return memberDetailDTOList;
    }


}
