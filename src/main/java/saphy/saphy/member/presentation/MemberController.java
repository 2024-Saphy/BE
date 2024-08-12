package saphy.saphy.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.dto.request.JoinMemberDto;
import saphy.saphy.member.domain.dto.response.MemberInfoDto;
import saphy.saphy.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/join")
    public ApiResponse<Void> join(@Validated @RequestBody JoinMemberDto joinDto, Errors errors) {

        validateRequest(errors);
        memberService.join(joinDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @GetMapping("/info")
    public ApiResponse<MemberInfoDto> getInfo() {
        MemberInfoDto dto = memberService.getInfo();
        return new ApiResponse<>(dto);
    }

    private void validateRequest(Errors errors) {

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                throw SaphyException.from(ErrorCode.INVALID_REQUEST);
            });
        }
    }


}
