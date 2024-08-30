package saphy.saphy.item.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.dto.request.ItemCreateRequest;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.item.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {
	private final ItemService itemService;

	@PostMapping("/items")
	public void save(@RequestBody ItemCreateRequest request) {
		itemService.save(request);
	}

	@GetMapping("/items/{itemId}")
	public ItemResponse findPhoneById(@PathVariable Long itemId) {
		return itemService.findById(itemId);
	}

	@GetMapping("/items")
	public List<ItemResponse> findAll() {
		return itemService.findAll();
	}
}
