package saphy.saphy.item.domain.repository.custom;

import static org.springframework.util.StringUtils.*;
import static saphy.saphy.image.domain.QItemDescriptionImage.*;
import static saphy.saphy.item.domain.QItem.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.enumeration.DeviceType;
import saphy.saphy.item.dto.request.SearchParam;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemQueryDslRepositoryImpl implements ItemQueryDslRepository {
	private final JPAQueryFactory queryFactory;

	/**
	 * 기종과 정렬 타입에 따라 상품 검색
	 */
	@Override
	public Slice<Item> findByDeviceTypeAndSortType(SearchParam searchParam, Pageable pageable) {
		int pageSize = pageable.getPageSize();

		JPAQuery<Item> basicQuery = queryFactory
			.selectFrom(item)
			.leftJoin(item.itemDescriptionImage, itemDescriptionImage).fetchJoin()
			.where(
				titleContains(searchParam.getQuery()),
				descriptionContains(searchParam.getQuery()),
				deviceTypeEquals(searchParam.getDeviceType())
			)
			.offset(pageable.getOffset())
			.limit(pageSize + 1);

		List<Item> content = addSortingQuery(basicQuery, searchParam.getSort());

		return new SliceImpl<>(content, pageable, hasNextPage(content, pageSize));
	}

	/*
	검색어를 포함한 전체 기종의 상품 조회 (+ 정렬 기능)
	*/
	@Override
	public Slice<Item> findBySortType(SearchParam searchParam, Pageable pageable) {
		int pageSize = pageable.getPageSize();

		JPAQuery<Item> basicQuery = queryFactory
			.selectFrom(item)
			.where(
				titleContains(searchParam.getQuery()),
				descriptionContains(searchParam.getQuery())
			)
			.offset(pageable.getOffset())
			.limit(pageSize + 1);

		List<Item> content = addSortingQuery(basicQuery, searchParam.getSort());

		return new SliceImpl<>(content, pageable, hasNextPage(content, pageSize));
	}

	/**
	 * 마지막 페이지 여부 확인 메소드
	 */
	private boolean hasNextPage(List<Item> content, int pageSize) {
		boolean hasNext = false;
		if (content.size() > pageSize) {
			hasNext = true;
			content.remove(pageSize);

		}
		return hasNext;
	}

	/**
	 * 제목, 내용, 카테고리 포함 여부 조사 메소드
	 */
	private BooleanExpression titleContains(String name) {
		return hasText(name) ? item.name.contains(name) : null;
	}

	private BooleanExpression descriptionContains(String description) {
		return hasText(description) ? item.description.contains(description) : null;
	}

	private BooleanExpression deviceTypeEquals(String deviceType) {
		if (!hasText(deviceType) || deviceType.equals("ALL")) {
			return null;
		}

		return item.deviceType.eq(DeviceType.valueOf(deviceType));
	}

	private BooleanExpression ltItemId(Long itemId) {
		return itemId == null ? null : item.id.lt(itemId);
	}

	/*
	  정렬 관련 쿼리를 추가하기 위한 메소드
	 */
	private List<Item> addSortingQuery(JPAQuery<Item> basicQuery, String sortType) {
		List<Item> content;

		switch (sortType) {
			case "id":
				content = basicQuery
					.orderBy(item.id.desc())
					.fetch();
				break;

			case "rid":
				content = basicQuery
					.orderBy(item.id.asc())
					.fetch();
				break;

			case "price":
				content = basicQuery
					.orderBy(item.price.desc())
					.fetch();
				break;

			case "rprice":
				content = basicQuery
					.orderBy(item.price.asc())
					.fetch();
				break;

			default:
				content = new ArrayList<>();
		}

		return content;
	}
}
