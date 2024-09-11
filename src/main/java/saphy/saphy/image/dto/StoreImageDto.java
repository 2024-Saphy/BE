package saphy.saphy.image.dto;

import saphy.saphy.image.domain.ItemImage;
import saphy.saphy.image.domain.ProfileImage;
import saphy.saphy.image.domain.ReviewImage;
import saphy.saphy.item.domain.Item;
import saphy.saphy.member.domain.Member;
import saphy.saphy.review.domain.Review;

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
}
