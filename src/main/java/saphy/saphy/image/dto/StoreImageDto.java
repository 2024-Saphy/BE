package saphy.saphy.image.dto;

import saphy.saphy.image.domain.ItemImage;
import saphy.saphy.image.domain.ProfileImage;
import saphy.saphy.image.domain.ReviewImage;
import saphy.saphy.image.domain.SalesImage;
import saphy.saphy.item.domain.Item;
import saphy.saphy.member.domain.Member;
import saphy.saphy.review.domain.Review;
import saphy.saphy.sales.domain.Sales;

public record StoreImageDto(String uploadName, String storeName) {

	public ProfileImage toProfileImageEntity(Member member, String url) {
		return ProfileImage.createProfileImage(uploadName, storeName, url, member);
	}

	public ItemImage toItemImageEntity(Item item, String url) {
		return ItemImage.createItemImage(uploadName, storeName, url, item);
	}

	public ReviewImage toReviewImageEntity(Review review, String url) {
		return ReviewImage.createReviewImage(uploadName, storeName, url, review);
	}

	public SalesImage toSalesImageEntity(Sales sales, String url) {
		return SalesImage.createSalesImage(uploadName, storeName, url, sales);
	}
}
