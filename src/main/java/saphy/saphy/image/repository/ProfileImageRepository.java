package saphy.saphy.image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import saphy.saphy.image.domain.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
	Optional<ProfileImage> findByImage_StoreName(String storeName);

	Optional<ProfileImage> findByMemberId(Long memberId);

	// @Query("SELECT p FROM ProfileImage p JOIN FETCH p.member WHERE p.member.id = :memberId")
	// Optional<ProfileImage> findByMemberIdJQL(Long memberId);
/*
	@EntityGraph(attributePaths = { "url" })
	Optional<String> fetchProfileImage();

 */
}
