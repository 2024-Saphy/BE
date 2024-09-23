package saphy.saphy.pay.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saphy.saphy.global.response.ApiResponse;
import saphy.saphy.pay.service.PayService;
import saphy.saphy.pay.dto.request.PayRequest;
import saphy.saphy.pay.dto.response.PayResponse;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "PaymentController", description = "결제 관련 API")
public class PayController {
    private final PayService payService;

    @PostMapping
    public ApiResponse<PayResponse> processPayment(@RequestBody PayRequest request){
        PayResponse response = payService.processPayment(request);

        return new ApiResponse<>(response);
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<PayResponse> getPaymentDetails(@PathVariable Long paymentId) {
        PayResponse response = payService.getPaymentDetails(paymentId);

        return new ApiResponse<>(response);
    }
}