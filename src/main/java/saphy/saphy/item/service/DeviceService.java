package saphy.saphy.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.domain.repository.DeviceRepository;
import saphy.saphy.item.dto.response.DeviceResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeviceService {
	private final DeviceRepository deviceRepository;

	public DeviceResponse findById(Long deviceId) {
		return deviceRepository.findById(deviceId)
			.map(DeviceResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기기입니다."));
	}

	public List<DeviceResponse> findAll() {
		return deviceRepository.findAll().stream()
			.map(DeviceResponse::from)
			.toList();
	}
}
