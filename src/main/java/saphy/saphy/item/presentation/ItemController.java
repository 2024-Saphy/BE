package saphy.saphy.item.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.image.service.ImageService;
import saphy.saphy.item.domain.Item;
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
	private final ImageService imageService;

	@PostMapping("/items/phones")
	@Operation(summary = "휴대폰 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
		@RequestPart("request") PhoneCreateRequest request,
		@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	){
		Item item = itemService.savePhone(request);
		imageService.saveItemImages(multipartFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/tablets")
	@Operation(summary = "태블릿 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
		@RequestPart("request") TabletCreateRequest request,
		@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Item item = itemService.saveTablet(request);
		imageService.saveItemImages(multipartFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@PostMapping("/items/laptops")
	@Operation(summary = "노트북 생성 API", description = "상품을 생성하는 API 입니다.")
	public ApiResponse<Void> save(
		@RequestPart("request") LaptopCreateRequest request,
		@RequestPart("imageFiles") List<MultipartFile> multipartFiles
	) {
		Item item = itemService.saveLaptop(request);
		imageService.saveItemImages(multipartFiles, item.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@GetMapping("/items/{itemId}")
	@Operation(summary = "상품 단건 조회 API", description = "상품을 단건으로 조회하는 API 입니다.")
	public ApiResponse<PhoneResponse> findPhoneById(@PathVariable Long itemId) {
		return new ApiResponse<>(itemService.findPhoneById(itemId));
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
}
