package saphy.saphy.sales.presentation;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.image.service.ImageService;
import saphy.saphy.member.domain.Member;
import saphy.saphy.sales.domain.Sales;
import saphy.saphy.sales.dto.request.SalesCreateRequest;
import saphy.saphy.sales.dto.response.SalesResponse;
import saphy.saphy.sales.service.SalesService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class SalesController {
	private final SalesService salesService;
	private final ImageService imageService;

	@PostMapping("/sales")
	@Operation(summary = "판매 생성 API", description = "판매를 생성하는 API 입니다.")
	public ApiResponse<Void> save(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestPart("request") SalesCreateRequest request,
		@RequestPart("imageFiles") List<MultipartFile> imageFiles
	) {
		Member member = customUserDetails.getMember();
		Sales sales = salesService.save(member, request);
		imageService.saveSalesImages(imageFiles, sales.getId());

		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@GetMapping("/sales/{salesId}")
	@Operation(summary = "판매 단건 조회 API", description = "판매를 단건으로 조회하는 API 입니다.")
	public ApiResponse<SalesResponse> findSalesById(@PathVariable Long salesId) {
		return new ApiResponse<>(salesService.findById(salesId));
	}

	@GetMapping("/sales")
	@Operation(summary = "로그인한 사용자의 판매 목록 조회 API", description = "로그인한 사용자의 판매 목록을 조회하는 API 입니다.")
	public ApiResponse<SalesResponse> findSalesByMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		Member member = customUserDetails.getMember();

		return new ApiResponse<>(salesService.findByMember(member));
	}
}
