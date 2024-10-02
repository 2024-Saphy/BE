package saphy.saphy.pay.presentation;

import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.pay.dto.request.PayPrepareRequest;
import saphy.saphy.pay.dto.response.PayPrepareResponse;
import saphy.saphy.pay.service.PayService;
import saphy.saphy.pay.dto.request.PayCompleteRequest;
import saphy.saphy.pay.dto.response.PayCompleteResponse;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "PaymentController", description = "결제 관련 API")
@Slf4j
public class PayController {
    private final PayService payService;

    @PostMapping("/prepare")
    public ApiResponse<PayPrepareResponse> preparePayment(@RequestBody PayPrepareRequest request) {
        PayPrepareResponse response = payService.preparePay(request);
        return new ApiResponse<>(response);
    }

    @PostMapping("/complete")
    public ApiResponse<PayCompleteResponse> completePayment(@RequestBody PayCompleteRequest request)
        throws IamportResponseException, IOException {
        PayCompleteResponse response = payService.completePay(request);
        return new ApiResponse<>(response);
    }
}