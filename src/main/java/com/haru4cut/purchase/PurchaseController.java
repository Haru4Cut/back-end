package com.haru4cut.purchase;

import com.amazonaws.Response;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@Log4j2
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/verify/{productId}/{userId}/{imp_uid}")
    public MessageResponse verify(@PathVariable Long productId,
                                  @PathVariable Long userId,
                                  @PathVariable String imp_uid) throws IamportResponseException, IOException {
        MessageResponse messageResponse = purchaseService.PurchaseVerify(productId, userId, imp_uid);
        return messageResponse;
    }

}
