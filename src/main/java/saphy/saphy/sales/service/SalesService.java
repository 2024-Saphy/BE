package saphy.saphy.sales.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.repository.ItemRepository;
import saphy.saphy.member.domain.Member;
import saphy.saphy.sales.SalesStatus;
import saphy.saphy.sales.domain.Sales;
import saphy.saphy.sales.domain.repository.SalesRepository;
import saphy.saphy.sales.dto.request.SalesCreateRequest;
import saphy.saphy.sales.dto.response.SalesResponse;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesService {
	private final SalesRepository salesRepository;
	private final ItemRepository<Item> itemRepository;

	public Map<SalesStatus, Long> getPurchaseCounts(Long memberId) {
		Map<SalesStatus, Long> statusCounts = new EnumMap<>(SalesStatus.class);

		for (SalesStatus status : SalesStatus.values()) {
			long count = salesRepository.countByPurchaseStatusAndMemberId(status, memberId);
			statusCounts.put(status, count);
		}

		return statusCounts;
	}

	@Transactional
	public Sales save(Member member, SalesCreateRequest request) {
		Item item = itemRepository.findById(request.getItemId())
			.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));

		return salesRepository.save(request.toEntity(member, item));
	}

	public SalesResponse findById(Long salesId) {
		Sales sales = salesRepository.findById(salesId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.SALES_NOT_FOUND));

		return SalesResponse.toDto(sales);
	}

	public List<SalesResponse> findByMember(Member member) {
		return salesRepository.findByMemberId(member.getId()).stream()
			.map(SalesResponse::toDto)
			.toList();
	}

	@Transactional
	public void deleteSalesById(Long salesId) {
		salesRepository.deleteById(salesId);
	}
}
