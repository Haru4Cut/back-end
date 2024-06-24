package com.haru4cut.purchase;

import com.haru4cut.domain.security.UserRole;
import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PurchaseService {
    private IamportClient iamportClient;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    public MessageResponse PurchaseVerify(Long goodsId,Long userId, String imp_uid) throws IamportResponseException, IOException {
        BigDecimal dbPrice = calculateDBAmount(goodsId);
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(imp_uid);

        if (iamportResponse.getResponse() == null){
            return new MessageResponse("구매에 실패하였습니다: 존재하지 않는 결제 정보");
        }

        BigDecimal paidAmount = iamportResponse.getResponse().getAmount();
        String merchant_uid = iamportResponse.getResponse().getMerchantUid();

        if(purchaseRepository.existsById(merchant_uid)){
            return new MessageResponse("구매에 실패하였습니다: 중복된 주문 번호");
        }
        if(paidAmount.compareTo(dbPrice) != 0){ // 반환값 0 -> 두 객체 같음, 반환값 양수 -> 비교 대상인 객체 (compare 안) 가 비교되는 객체보다 큼 , 음수
            IamportResponse<Payment> response = iamportClient.paymentByImpUid(imp_uid);
            CancelData cancelData = createCancelData(response, 0); // 우린 전체 환불만 해주자
            iamportClient.cancelPaymentByImpUid(cancelData);

            return new MessageResponse("지불 금액 :" + paidAmount + " db 금액 : "+ dbPrice +"구매에 실패하였습니다: 금액 불일치");
        }
        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        Users users = userRepository.findUserById(userId);
        int pencils = users.getPencils();
        Goods goods = entityManager.find(Goods.class, goodsId);
        int plus = goods.getPencil();
        int total = pencils + plus;
        users.setPencils(total);
        userRepository.save(users);
        Purchase purchase = new Purchase(merchant_uid, users);
        purchaseRepository.save(purchase);
        users.editAuthority(UserRole.ROLE_SUBSCRIBER);
        return new MessageResponse("구매가 완료 되었습니다");
    }

    private BigDecimal calculateDBAmount(Long goodsId){
        BigDecimal price = BigDecimal.ZERO;
        Goods goods = entityManager.find(Goods.class, goodsId);
        if (goods != null){
            return goods.getPrice();
        } else {
            return price;
        }
    }

    private CancelData createCancelData(IamportResponse<Payment> response, int refundAmount){ // 전체 환불
        if (refundAmount == 0){
            return new CancelData(response.getResponse().getImpUid(), true);
        }
        return new CancelData(response.getResponse().getImpUid(), true, new BigDecimal(refundAmount));
    }
}
