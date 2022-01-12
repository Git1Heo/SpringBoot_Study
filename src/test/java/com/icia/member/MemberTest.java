package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Test
    @Transactional  // 테스트 시작할 때 새로운 트랙잭션 시작
    @Rollback       //테스트 종료 후 롤백 수행
    @DisplayName("회원조회 테스트")
    public  void memberDetailTest(){
    /*
        테스트 3단계
        given : 테스트 조건 설정
            1. 새로운 회원을 등록하고 해당회원의 번호(member_id)를 가져옴.
        when  : 테스트 수행
            2. 위에서 가져온 회원 번호를 가지고 조회 기능 수행
        then  : 테스트 결과 검증
            3. 1번에서 가입한 회원의 정보와 2번에서 조회한 회원의 정보가 일치하면 테스트 통과 일치하지 않으면 테스트 실패
     */
        MemberSaveDTO memberSaveDTO=new MemberSaveDTO("조회용회원이메일1","조회용회원비밀번호1","조회용회원이름1");
        Long memberId=ms.save(memberSaveDTO);

        MemberDetailDTO findMember = ms.findById(memberId);

        // memberSaveDTO의 이메일값과 findById의 이메일 값이 일치하는지 확인
        assertThat(memberSaveDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
    }


    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인 테스트")
    public void memberLogin(){
        // given
            // 1. 새로운 회원을 등록하고 해당회원의 이메일과 패스워드를 LoginDTO에 담는다.
        MemberSaveDTO memberSaveDTO=new MemberSaveDTO("이메일","비밀번호","이름");
        ms.save(memberSaveDTO);
        MemberLoginDTO memberLoginDTO=new MemberLoginDTO(memberSaveDTO.getMemberEmail(), memberSaveDTO.getMemberPassword());
        //MemberLoginDTO memberLoginDTO=new MemberLoginDTO("다른이메일","다른 비밀번호");

        //when
            // 2, 위에서 가져온 회원 아이디 패스워드 가지고 조회기능 수행
        boolean result=ms.login(memberLoginDTO);

        //then
       assertThat(result).isEqualTo(true);
    }


    /*
        MemberServiceImpl.save() 메소드가 잘 동작하는지를 테스트

        회원가입테스스
        save.html 에서 회원정보 입력 후 가입클릭
        -> DB 확인
     */

    @Test
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트회원 이메일1");
        memberSaveDTO.setMemberName("테스트회원 이름1");
        memberSaveDTO.setMemberPassword("테스트회원 비밀번호1");

        ms.save(memberSaveDTO);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원목록 테스트")
    public void memberListTest(){
        /*
            member_talbe에 아무 데이터가 없는 상태에서
            3명의 회원을 가입시킨 후 memberList사이즈를 조회하며 3이면 테스트 통과
         */
        MemberSaveDTO memberSaveDTO1=new MemberSaveDTO("이메일1","비밀번호1","이름1");
        ms.save(memberSaveDTO1);
        MemberSaveDTO memberSaveDTO2=new MemberSaveDTO("이메일2","비밀번호2","이름2");
        ms.save(memberSaveDTO2);
        MemberSaveDTO memberSaveDTO3=new MemberSaveDTO("이메일3","비밀번호3","이름3");
        ms.save(memberSaveDTO3);

        // IntStream 방식
        IntStream.rangeClosed(1,3).forEach(i ->{
                    ms.save(new MemberSaveDTO("가"+i, "가"+i,"가"+i));
                }
        );

        List<MemberDetailDTO> all = ms.findAll();
        assertThat(all.size()).isEqualTo(6);
    }

}
