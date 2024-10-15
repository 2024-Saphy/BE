package saphy.saphy.member.presentation;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.auth.utils.AccessTokenUtils;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.image.domain.ProfileImage;
import saphy.saphy.image.service.ImageService;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.dto.request.MemberAccountAddRequest;
import saphy.saphy.member.dto.request.MemberAccountUpdateRequest;
import saphy.saphy.member.dto.request.MemberAddressAddRequest;
import saphy.saphy.member.dto.request.MemberAddressUpdateRequest;
import saphy.saphy.member.dto.request.MemberJoinRequest;
import saphy.saphy.member.dto.request.MemberInfoUpdateRequest;
import saphy.saphy.member.dto.response.MemberAccountResponse;
import saphy.saphy.member.dto.response.MemberAddressResponse;
import saphy.saphy.member.dto.response.MemberDetailResponse;
import saphy.saphy.member.dto.response.MemberInfoResponse;
import saphy.saphy.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "MemberController", description = "회원 관련 API")
public class MemberController {
    private final MemberService memberService;
    private final ImageService imageService;

    @GetMapping("/test")
    @Operation(summary = "로그인 유지 test", description = "member가 SecurityContext에 저장되었는지 확인하는 API 입니다.")
    public ApiResponse checkFirstLogin(@AuthenticationPrincipal CustomUserDetails userDetails,
        HttpServletResponse response) {
        memberService.isCurrentMember(userDetails.getMember().getLoginId());
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입 API", description = "회원 정보를 등록합니다.")
    public ApiResponse<Void> join(@Validated @RequestBody MemberJoinRequest joinDto) {
        memberService.join(joinDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

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

    @PatchMapping("/info")
    @Operation(summary = "회원 정보 수정 API", description = "회원 정보를 수정합니다.")
    public ApiResponse<Void> updateInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody MemberInfoUpdateRequest updateRequest) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.updateMemberInfo(loggedInMember, updateRequest);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @PatchMapping("/profileImage")
    @Operation(summary = "회원 프로필 이미지 설정 API", description = "회원의 프로필 이미지를 설정합니다.")
    public ApiResponse<Void> updateProfileImage(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestPart("profileImage") MultipartFile profileImageFile
    ) {
        Member loggedInMember = customUserDetails.getMember();
        imageService.deleteProfileImage(loggedInMember.getId());
        ProfileImage profileImage = imageService.saveProfileImage(profileImageFile, loggedInMember.getId());
        memberService.updateProfileImage(loggedInMember, profileImage);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 서비스 탈퇴 API", description = "회원을 삭제합니다.")
    public ApiResponse<Void> deleteMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        memberService.validateMemberForDeletion(member);
        memberService.deleteMember(member);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    /**
     * 주소
     */
    @GetMapping("/info/address")
    @Operation(summary = "회원 주소 조회 API", description = "회원의 주소를 조회합니다.")
    public ApiResponse<MemberAddressResponse> getAddress(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member loggedInMember = customUserDetails.getMember();

        return new ApiResponse<>(memberService.findMemberAddress(loggedInMember));
    }

    @PostMapping("/info/address")
    @Operation(summary = "회원 주소 추가 API", description = "회원의 주소를 추가합니다.")
    public ApiResponse<Void> addAddress(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody MemberAddressAddRequest request
    ) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.addMemberAddress(loggedInMember, request);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @PatchMapping("/info/address")
    @Operation(summary = "회원 주소 수정 API", description = "회원의 주소를 수정합니다.")
    public ApiResponse<Void> updateAddress(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody MemberAddressUpdateRequest request
    ) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.updateMemberAddress(loggedInMember, request);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping("/info/address")
    @Operation(summary = "회원 주소 삭제 API", description = "회원의 주소를 삭제합니다.")
    public ApiResponse<Void> deleteAddress(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.deleteMemberAddress(loggedInMember);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    /**
     * 계좌
     */
    @GetMapping("/info/account")
    @Operation(summary = "회원 계좌 조회 API", description = "회원의 계좌를 조회합니다.")
    public ApiResponse<MemberAccountResponse> getAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member loggedInMember = customUserDetails.getMember();

        return new ApiResponse<>(memberService.findMemberAccount(loggedInMember));
    }

    @PostMapping("/info/account")
    @Operation(summary = "회원 계좌 추가 API", description = "회원의 계좌를 추가합니다.")
    public ApiResponse<Void> addAccount(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody MemberAccountAddRequest request
    ) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.addMemberAccount(loggedInMember, request);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @PatchMapping("/info/account")
    @Operation(summary = "회원 계좌 수정 API", description = "회원의 계좌를 수정합니다.")
    public ApiResponse<Void> updateAccount(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody MemberAccountUpdateRequest request
    ) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.updateMemberAccount(loggedInMember, request);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping("/info/account")
    @Operation(summary = "회원 계좌 삭제 API", description = "회원의 계좌를 삭제합니다.")
    public ApiResponse<Void> deleteAccount(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member loggedInMember = customUserDetails.getMember();
        memberService.deleteMemberAccount(loggedInMember);

        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
