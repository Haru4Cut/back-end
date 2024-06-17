package com.haru4cut.purchase;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/verify/{productId}/{userId}/{imp_uid}")
    public ResponseEntity<MessageResponse> verify(@PathVariable Long productId,
                                                  @PathVariable Long userId,
                                                  @PathVariable String imp_uid) throws IamportResponseException, IOException {
        MessageResponse messageResponse = purchaseService.PurchaseVerify(productId, userId, imp_uid);
        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
    }

}
