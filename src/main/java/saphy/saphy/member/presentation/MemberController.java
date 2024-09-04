package saphy.saphy.member.presentation;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.auth.utils.AccessTokenUtils;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.dto.request.MemberJoinRequest;
import saphy.saphy.member.domain.dto.request.MemberInfoUpdateRequest;
import saphy.saphy.member.domain.dto.response.MemberDetailResponse;
import saphy.saphy.member.domain.dto.response.MemberInfoResponse;
import saphy.saphy.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/join")
    public ApiResponse<Void> join(@Validated @RequestBody MemberJoinRequest joinDto, Errors errors) {

        validateRequest(errors);
        memberService.join(joinDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    // 회원 정보 조회
    @GetMapping("/info")
    @Operation(summary = "회원 정보 조회 API", description = "배달, 구매, 판매와 관련된 회원 정보를 조회합니다.")
    public ApiResponse<MemberInfoResponse> getInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member loggedInMember = customUserDetails.getMember();
        MemberInfoResponse response = memberService.getInfo(loggedInMember);
        return new ApiResponse<>(response);
    }

    @GetMapping("/me")
    @Operation(summary = "단일 회원 상세 조회 API", description = "한명의 회원에 대한 필드 정보를 조회합니다.(관리자)")
    public ApiResponse<MemberDetailResponse> getMemberDetail() {

        String loginId = AccessTokenUtils.isPermission();
        MemberDetailResponse memberDetail = memberService.getMemberDetails(loginId);
        return new ApiResponse<>(memberDetail);
    }

    @GetMapping
    @Operation(summary = "전체 회원 상세 조회 API", description = "전체 회원에 대한 필드 정보를 조회합니다.(관리자)")
    public ApiResponse<MemberDetailResponse> getAllMemberDetails() {

        List<MemberDetailResponse> members = memberService.getAllMemberDetails();
        return new ApiResponse<>(members);
    }

    // 회원 정보 수정
    @PatchMapping("/info")
    public ApiResponse<Void> updateInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MemberInfoUpdateRequest updateRequest) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.updateMemberInfo(loggedInMember, updateRequest);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @GetMapping("/test")
    @Operation(summary = "로그인 유지 test", description = "member가 SecurityContext에 저장되었는지 확인하는 API 입니다.")
    public ApiResponse checkFirstLogin(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletResponse response) {
        memberService.checkLogin(userDetails.getMember(), response);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
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
