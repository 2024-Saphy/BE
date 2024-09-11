package saphy.saphy.item.presentation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.item.dto.request.LaptopCreateRequest;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.request.TabletCreateRequest;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.service.ItemService;
import saphy.saphy.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "ItemController", description = "모임 관련 API")
public class ItemController {
	private final ItemService itemService;

	@PostMapping("/items/phones")
	@Operation(summary = "휴대폰 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(@RequestBody PhoneCreateRequest request) {
		itemService.savePhone(request);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/tablets")
	@Operation(summary = "태블릿 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(@RequestBody TabletCreateRequest request) {
		itemService.saveTablet(request);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/laptops")
	@Operation(summary = "노트북 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(@RequestBody LaptopCreateRequest request) {
		itemService.saveLaptop(request);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@GetMapping("/items/{itemId}")
	@Operation(summary = "상품 단건 조회 API", description = "상품을 단건으로 조회하는 API 입니다.")
	public ApiResponse<PhoneResponse> findPhoneById(@PathVariable Long itemId) {
		return new ApiResponse<>(itemService.findPhoneById(itemId));
	}

	@GetMapping("/items/all")
	@Operation(summary = "상품 전체 조회 API", description = "전체 상품을 조회하는 API 입니다.")
	public ApiResponse<PhoneResponse> findAllPhones() {
		return new ApiResponse<>(itemService.findAllPhones());
	}

	@DeleteMapping("/items/{itemId}")
	@Operation(summary = "상품 삭제 API", description = "상품을 삭제하는 API 입니다.")
	public ApiResponse<Void> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long itemId) {
		Member loggedInMember = customUserDetails.getMember();
		itemService.delete(loggedInMember, itemId);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}
}
