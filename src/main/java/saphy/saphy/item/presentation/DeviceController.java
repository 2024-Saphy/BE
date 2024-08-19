package saphy.saphy.item.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.dto.response.DeviceResponse;
import saphy.saphy.item.service.DeviceService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceController {
	private final DeviceService deviceService;

	@GetMapping("/devices/{deviceId}")
	public DeviceResponse findById(@PathVariable Long deviceId) {
		return deviceService.findById(deviceId);
	}

	@GetMapping("/devices")
	public List<DeviceResponse> findAll() {
		return deviceService.findAll();
	}
}
