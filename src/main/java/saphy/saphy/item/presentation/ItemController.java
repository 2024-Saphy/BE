package saphy.saphy.item.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.image.service.ImageService;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.request.LaptopCreateRequest;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.request.TabletCreateRequest;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.service.ItemService;

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
}
