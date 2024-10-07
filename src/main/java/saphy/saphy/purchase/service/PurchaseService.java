package saphy.saphy.purchase.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import saphy.saphy.member.domain.Member;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.purchase.domain.PurchaseStatus;
import saphy.saphy.purchase.domain.repository.PurchaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saphy.saphy.purchase.dto.response.PurchaseResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;

	public Map<PurchaseStatus, Long> getPurchaseCounts(Long memberId) {
		Map<PurchaseStatus, Long> statusCounts = new EnumMap<>(PurchaseStatus.class);

		for (PurchaseStatus status : PurchaseStatus.values()) {
			long count = purchaseRepository.countByStatusAndMember_Id(status, memberId);
			statusCounts.put(status, count);
		}

		return statusCounts;
	}

	public List<PurchaseResponse> findAll(Member member) {
		return purchaseRepository.findByMember(member).stream()
				.map(PurchaseResponse::toDto)
				.toList();
	}

	public List<PurchaseResponse> findByStatus(Member member, String status) {
		PurchaseStatus purchaseStatus = PurchaseStatus.findByName(status); // 여기에 오류 발생시키긴 했는데, 뭔가 더 좋은 방법 있을거 같습니다..
		return purchaseRepository.findByStatusAndMember(purchaseStatus, member).stream()
				.map(PurchaseResponse::toDto)
				.toList();
	}

	public PurchaseResponse findById(Member member, Long purchaseId) {
		return PurchaseResponse.toDto(purchaseRepository.findByIdAndMember(purchaseId, member));
	}

	public List<PurchaseResponse> findDeliveries(Member member) {
		return purchaseRepository.findByStatusesAndMember(List.of(
					PurchaseStatus.START,
					PurchaseStatus.SHIPPED,
					PurchaseStatus.DELIVERED
				), member)
				.stream()
				.map(PurchaseResponse::toDto)
				.toList();
	}

}
