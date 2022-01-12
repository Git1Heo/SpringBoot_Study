package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberLoginDTO {
    @NotBlank(message = "이메일은 필수입니다.")
    private String memberEmail;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Length(min = 2, max = 8,message = "2~8자리의 비밀번호")
    private String memberPassword;
}
