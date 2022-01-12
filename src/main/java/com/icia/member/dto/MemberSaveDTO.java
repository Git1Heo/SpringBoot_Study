package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {
    @NotBlank(message = "이메일은 필수")
    private String memberEmail;
    @NotBlank
    @Length(min = 2, max = 8,message = "2~8자리의 비밀번호")
    private String memberPassword;
    private String memberName;
}
