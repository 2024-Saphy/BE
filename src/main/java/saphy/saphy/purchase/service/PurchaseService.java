package saphy.saphy.purchase.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.member.domain.Member;
import saphy.saphy.pay.domain.PayMethod;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.purchase.domain.PurchaseStatus;
import saphy.saphy.purchase.domain.repository.PurchaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Purchase toEntity(BigDecimal amount, PayMethod payMethod, Member member, Item item){
		LocalDateTime now = LocalDateTime.now();

		return Purchase.builder()
			.orderDate(now)
			.amount(amount)
			.payMethod(payMethod)
			.member(member)
			.item(item)
            .build();
	}

}
