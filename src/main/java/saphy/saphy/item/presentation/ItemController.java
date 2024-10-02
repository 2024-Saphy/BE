package saphy.saphy.item.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.image.service.ImageService;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.request.*;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.item.dto.response.LaptopResponse;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.dto.response.TabletResponse;
import saphy.saphy.item.service.ItemService;
import saphy.saphy.member.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "ItemController", description = "모임 관련 API")
public class ItemController {
	private final ItemService itemService;
	private final ImageService imageService;

	@PostMapping("/items/phones")
	@Operation(summary = "휴대폰 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestPart("request") PhoneCreateRequest request,
			@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	){
		Member loggedInMember = customUserDetails.getMember();
		Item item = itemService.savePhone(loggedInMember, request);
		imageService.saveItemImages(multipartFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/tablets")
	@Operation(summary = "태블릿 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestPart("request") TabletCreateRequest request,
			@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		Item item = itemService.saveTablet(loggedInMember, request);
		imageService.saveItemImages(multipartFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/laptops")
	@Operation(summary = "노트북 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestPart("request") LaptopCreateRequest request,
			@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		Item item = itemService.saveLaptop(loggedInMember, request);
		imageService.saveItemImages(multipartFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@GetMapping("/items/{itemId}")
	@Operation(summary = "상품 단건 조회 API", description = "상품을 단건으로 조회하는 API 입니다.")
	public ApiResponse<? extends ItemResponse> findItemById(@PathVariable Long itemId) {
		return new ApiResponse<>(itemService.findItemById(itemId));
	}

	@GetMapping("/items/all")
	@Operation(summary = "기기 종류에 따른 상품 조회 API", description = "기기 종류에 따라 상품 목록을 조회하는 API 입니다.")
	public ApiResponse<ItemResponse> findAllItems(@RequestParam("type") String type) {
		if (type.equals("ALL")) {
			return new ApiResponse<>(itemService.findAllItems());
		}
		return new ApiResponse<>(itemService.findByDeviceType(type));
	}

	@DeleteMapping("/items/{itemId}")
	@Operation(summary = "상품 삭제 API", description = "상품을 삭제하는 API 입니다.")
	public ApiResponse<Void> delete(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@PathVariable Long itemId
	) {
		Member loggedInMember = customUserDetails.getMember();
		itemService.delete(loggedInMember, itemId);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PatchMapping("/items/phones/{itemId}")
	@Operation(summary = "핸드폰 수정 API", description = "상품을 수정하는 API 입니다.")
	public ApiResponse<Void> update(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@PathVariable Long itemId,
			@RequestPart("request") PhoneUpdateRequest request,
			@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		itemService.updatePhone(loggedInMember, itemId, request);

		imageService.deleteItemImages(itemId);
		imageService.saveItemImages(multipartFiles, itemId);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PatchMapping("/items/tablets/{itemId}")
	@Operation(summary = "태블릿 수정 API", description = "상품을 수정하는 API 입니다.")
	public ApiResponse<Void> update(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@PathVariable Long itemId,
			@RequestPart("request") TabletUpdateRequest request,
			@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		itemService.updateTablet(loggedInMember, itemId, request);

		imageService.deleteItemImages(itemId);
		imageService.saveItemImages(multipartFiles, itemId);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PatchMapping("/items/laptops/{itemId}")
	@Operation(summary = "노트북 수정 API", description = "상품을 수정하는 API 입니다.")
	public ApiResponse<Void> update(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@PathVariable Long itemId,
			@RequestPart("request") LaptopUpdateRequest request,
			@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		itemService.updateLaptop(loggedInMember, itemId, request);

		imageService.deleteItemImages(itemId);
		imageService.saveItemImages(multipartFiles, itemId);

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

}
