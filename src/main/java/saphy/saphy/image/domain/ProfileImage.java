package saphy.saphy.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_image_id")
	private Long id;

	@Embedded
	private Image image;

	@OneToOne(fetch = FetchType.LAZY)
	@Setter
	private Member member;

	/**
	 * 생성 메서드
	 */
	public static ProfileImage createProfileImage(
		String uploadName,
		String storeName,
		String url,
		Member member
	) {
		ProfileImage profileImage = new ProfileImage();

		profileImage.image = Image.of(uploadName, storeName, url);
		profileImage.member = member;

		return profileImage;
	}

	public void updateMember(Member member) {
		this.member = member;
	}
}