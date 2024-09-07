package saphy.saphy.itemWish.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.itemWish.service.ItemWishService;
import saphy.saphy.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-wishes")
@Tag(name = "ItemWishController", description = "찜 목록 관련 API")
public class ItemWishController {
    private final ItemWishService itemWishService;

    @PostMapping
    @Operation(summary = "찜 목록 추가 API", description = "item을 찜 목록에 추가하는 API 입니다.")
    public ApiResponse<Void> save(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestParam Long itemId) {
        Member loggedInMember = customUserDetails.getMember();
        itemWishService.save(loggedInMember, itemId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping
    @Operation(summary = "찜 목록 삭제 API", description = "item을 찜 목록에서 삭제하는 API 입니다.")
    public ApiResponse<Void> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestParam Long itemId) {
        Member loggedInMember = customUserDetails.getMember();
        itemWishService.delete(loggedInMember, itemId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

}
