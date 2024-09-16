package saphy.saphy.image.dto.response;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.image.domain.Image;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ImageResponse {
	private String name;
	private String url;

	public static ImageResponse from(Image image) {
		ImageResponse imageResponse = new ImageResponse();

		imageResponse.name = image.getUploadName();
		imageResponse.url = image.getUrl();

		return imageResponse;
	}
}
