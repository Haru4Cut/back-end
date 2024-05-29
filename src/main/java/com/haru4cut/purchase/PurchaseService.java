package com.haru4cut.purchase;

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

    public MessageResponse PurchaseVerify(Long userId,Long goodsId, String imp_uid) throws IamportResponseException, IOException {
        BigDecimal dbPrice = calculateDBAmount(goodsId);

        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(imp_uid);
        BigDecimal paidAmount = iamportResponse.getResponse().getAmount();

        if(paidAmount.compareTo(dbPrice) != 0){ // 반환값 0 -> 두 객체 같음, 반환값 양수 -> 비교 대상인 객체 (compare 안) 가 비교되는 객체보다 큼 , 음수
            IamportResponse<Payment> response = iamportClient.paymentByImpUid(imp_uid);
            CancelData cancelData = createCancelData(response, 0); // 우린 전체 환불만 해주자
            iamportClient.cancelPaymentByImpUid(cancelData);

            return new MessageResponse("구매에 실패하였습니다");
        }
        Users users = userRepository.findUserById(userId);
        int pencils = users.getPencils();
        Goods goods = entityManager.find(Goods.class, goodsId);
        int plus = goods.getPencil();
        int total = pencils + plus;
        Users new_users = new Users(userId, total);
        userRepository.save(new_users); //안되면 setter 써야지 뭔

        return new MessageResponse("구매가 완료 되었습니다");
    }

    private BigDecimal calculateDBAmount(Long goodsId){ // goodId를 통해 가격 받아오기
        BigDecimal price = BigDecimal.ZERO; //값이 0인 BigDecimal 객체 생성
        Goods goods = entityManager.find(Goods.class, goodsId);
        if (goods != null){
            return goods.getPrice();
        } else {
            return price;
        }
    }

    private CancelData createCancelData(IamportResponse<Payment> response, int refundAmount){
        if (refundAmount == 0){ // 전액환불만
            return new CancelData(response.getResponse().getImpUid(), true);
        }
        //부분 환불일 경우 checksum을 입력해 준다. -> 있으니까 썼는데 솔직히 부분환불 왜 필요한지 모르겠음 + 테스트 환경에서는 부분환불 안됨
        return new CancelData(response.getResponse().getImpUid(), true, new BigDecimal(refundAmount));
    }
}
