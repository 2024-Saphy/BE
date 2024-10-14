package saphy.saphy.item.presentation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.image.service.ImageService;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.request.LaptopCreateRequest;
import saphy.saphy.item.dto.request.LaptopUpdateRequest;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.request.PhoneUpdateRequest;
import saphy.saphy.item.dto.request.SearchParam;
import saphy.saphy.item.dto.request.TabletCreateRequest;
import saphy.saphy.item.dto.request.TabletUpdateRequest;
import saphy.saphy.item.dto.response.ItemResponse;
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
		@RequestPart("imageFiles") List<MultipartFile> imageFiles,
		@RequestPart("descriptionImageFiles") List<MultipartFile> descriptionImageFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		Item item = itemService.savePhone(loggedInMember, request);
		imageService.saveItemImages(imageFiles, item.getId());
		imageService.saveItemDescriptionImages(descriptionImageFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/tablets")
	@Operation(summary = "태블릿 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart("request") TabletCreateRequest request,
		@RequestPart("imageFiles") List<MultipartFile> multipartFiles,
		@RequestPart("descriptionImageFiles") List<MultipartFile> descriptionImageFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		Item item = itemService.saveTablet(loggedInMember, request);
		imageService.saveItemImages(multipartFiles, item.getId());
		imageService.saveItemDescriptionImages(descriptionImageFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/laptops")
	@Operation(summary = "노트북 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart("request") LaptopCreateRequest request,
		@RequestPart("imageFiles") List<MultipartFile> imageFiles,
		@RequestPart("descriptionImageFiles") List<MultipartFile> descriptionImageFiles
	) {
		Member loggedInMember = customUserDetails.getMember();
		Item item = itemService.saveLaptop(loggedInMember, request);
		imageService.saveItemImages(imageFiles, item.getId());
		imageService.saveItemDescriptionImages(descriptionImageFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@GetMapping("/items/{itemId}")
	@Operation(summary = "상품 단건 조회 API", description = "상품을 단건으로 조회하는 API 입니다.")
	public ApiResponse<? extends ItemResponse> findItemById(@PathVariable Long itemId) {
		return new ApiResponse<>(itemService.findById(itemId));
	}

	@GetMapping("/items")
	@Operation(summary = "상품 검색 API", description = "검색어 및 세부 필터링 조건에 따라 상품 목록을 조회하고 정렬하는 API 입니다.")
	public ApiResponse<ItemResponse> searchItems(
		@ModelAttribute SearchParam searchParam,
		@PageableDefault(size = 40, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		return new ApiResponse<>(itemService.searchByDeviceType(searchParam, pageable));
	}

	// 일단 사용할 곳이 없어 보이나, 혹시 몰라 주석 처리
	// @GetMapping("/items/main")
	// @Operation(summary = "기종에 따른 메인 페이지 상품 조회 API", description = "메인 페이지의 상품 목록을 기종에 따라 조회하는 API 입니다.")
	// public ApiResponse<ItemResponse> findMainItemsByDeviceType(
	// 	@ModelAttribute SearchParam searchParam,
	// 	@PageableDefault(size = 40, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	// ) {
	// 	return new ApiResponse<>(itemService.findMainPageItemsByDeviceType(searchParam, pageable));
	// }

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
