package saphy.saphy.image.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.image.domain.ItemImage;
import saphy.saphy.image.domain.ProfileImage;
import saphy.saphy.image.domain.ReviewImage;
import saphy.saphy.image.dto.StoreImageDto;
import saphy.saphy.image.repository.ImageStoreProcessor;
import saphy.saphy.image.repository.ItemImageRepository;
import saphy.saphy.image.repository.ProfileImageRepository;
import saphy.saphy.image.repository.ReviewImageRepository;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.repository.ItemRepository;
import saphy.saphy.member.domain.Member;
import saphy.saphy.member.domain.repository.MemberRepository;
import saphy.saphy.review.domain.Review;
import saphy.saphy.review.domain.repository.ReviewRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
	private final ItemImageRepository itemImageRepository;
	private final ProfileImageRepository profileImageRepository;
	private final ReviewImageRepository reviewImageRepository;
	private final ItemRepository<Item> itemRepository;
	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final ImageStoreProcessor imageStoreProcessor;
	private final AmazonS3 amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Transactional
	public void saveItemImages(List<MultipartFile> imageFiles, Long itemId) {
		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.ITEM_NOT_FOUND));

		List<StoreImageDto> storeImageDtos = imageStoreProcessor.storeImageFiles(imageFiles);

		for (int i = 0; i < imageFiles.size(); i++) {
			String imageUrl = uploadImageFile(imageFiles.get(i), storeImageDtos.get(i));

			ItemImage itemImage = storeImageDtos.get(i).toItemImageEntity(item, imageUrl);
			itemImageRepository.save(itemImage);
		}
	}

	@Transactional
	public void saveProfileImages(List<MultipartFile> imageFiles, Long memberId) {
		 Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.MEMBER_NOT_FOUND));

		List<StoreImageDto> storeImageDtos = imageStoreProcessor.storeImageFiles(imageFiles);

		for (int i = 0; i < imageFiles.size(); i++) {
			String imageUrl = uploadImageFile(imageFiles.get(i), storeImageDtos.get(i));

			ProfileImage profileImage = storeImageDtos.get(i).toProfileImageEntity(member, imageUrl);
			profileImageRepository.save(profileImage);
		}
	}

	@Transactional
	public void saveReviewImages(List<MultipartFile> imageFiles, Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.REVIEW_NOT_FOUND));

		List<StoreImageDto> storeImageDtos = imageStoreProcessor.storeImageFiles(imageFiles);

		for (int i = 0; i < imageFiles.size(); i++) {
			String imageUrl = uploadImageFile(imageFiles.get(i), storeImageDtos.get(i));

			ReviewImage reviewImage = storeImageDtos.get(i).toReviewImageEntity(review, imageUrl);
			reviewImageRepository.save(reviewImage);
		}
	}

	@Transactional
	public void deleteItemImages(Long itemId) {
		List<ItemImage> itemImages = itemImageRepository.findByItemId(itemId);

		itemImages.forEach(itemImage -> {
			itemImageRepository.delete(itemImage);

			String storeName = itemImage.getImage().getStoreName();
			amazonS3Client.deleteObject(bucket, storeName);
		});
	}

	@Transactional
	public void deleteProfileImage(Long memberId) {
		ProfileImage profileImage = profileImageRepository.findByMemberId(memberId)
			.orElseThrow(() -> SaphyException.from(ErrorCode.PROFILE_IMAGE_NOT_FOUND));

			profileImageRepository.delete(profileImage);

			String storeName = profileImage.getImage().getStoreName();
			amazonS3Client.deleteObject(bucket, storeName);
	}

	@Transactional
	public void deleteReviewImages(Long reviewId) {
		List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewId);

		reviewImages.forEach(reviewImage -> {
			reviewImageRepository.delete(reviewImage);

			String storeName = reviewImage.getImage().getStoreName();
			amazonS3Client.deleteObject(bucket, storeName);
		});
	}

	@Transactional
	public void deleteProfileImages(Long memberId) {
		Optional<ProfileImage> optionalProfileImage = profileImageRepository.findByMemberId(memberId);
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if (optionalProfileImage.isEmpty()) {
			throw SaphyException.from(ErrorCode.EMPTY_IMAGE);
		}
		if (optionalMember.isEmpty()) {
			throw SaphyException.from(ErrorCode.MEMBER_NOT_FOUND);
		}
    
		Member member = optionalMember.get();
		member.setProfileImage(null);
		memberRepository.save(member);

		ProfileImage profileImage = optionalProfileImage.get();
		profileImageRepository.delete(profileImage);
		String storeName = profileImage.getImage().getStoreName();
		amazonS3Client.deleteObject(bucket, storeName);
	}

	private String uploadImageFile(MultipartFile multipartFile, StoreImageDto storeImageDto) {
		String storeName = storeImageDto.storeName();
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(multipartFile.getContentType());
			objectMetadata.setContentLength(multipartFile.getSize());
			objectMetadata.setContentDisposition("inline");

			PutObjectRequest putObjectRequest = new PutObjectRequest(
				bucket,
				storeName,
				multipartFile.getInputStream(),
				objectMetadata
			);

			amazonS3Client.putObject(putObjectRequest);

			return amazonS3Client.getUrl(bucket, storeName).toString();
		} catch (IOException e) {
			throw SaphyException.from(ErrorCode.S3_UPLOAD_FAILURE);
		}
	}
}
