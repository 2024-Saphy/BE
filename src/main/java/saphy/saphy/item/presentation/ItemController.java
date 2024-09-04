package saphy.saphy.item.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.item.dto.request.PhoneCreateRequest;
import saphy.saphy.item.dto.response.PhoneResponse;
import saphy.saphy.item.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {
	private final ItemService itemService;

	@PostMapping("/items")
	public ApiResponse<Void> save(@RequestBody PhoneCreateRequest request) {
		itemService.save(request);
		return new ApiResponse<>(ErrorCode.REQUEST_OK);
	}

	@GetMapping("/items/{itemId}")
	public ApiResponse<PhoneResponse> findPhoneById(@PathVariable Long itemId) {
		return new ApiResponse<>(itemService.findPhoneById(itemId));
	}

	@GetMapping("/items")
	public ApiResponse<PhoneResponse> findAllPhones() {
		return new ApiResponse<>(itemService.findAllPhones());
	}
}
