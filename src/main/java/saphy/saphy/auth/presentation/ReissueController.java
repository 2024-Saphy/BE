package saphy.saphy.auth.presentation;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saphy.saphy.auth.service.ReissueService;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reissue")
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping
    ApiResponse reissue(HttpServletRequest request, HttpServletResponse response){

        String newAccess = reissueService.createNewAccessToken(request,response);
        response.addHeader("Authorization", "Bearer " + newAccess);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
