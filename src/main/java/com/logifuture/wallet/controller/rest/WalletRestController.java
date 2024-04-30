package com.logifuture.wallet.controller.rest;

import com.logifuture.wallet.dto.wallet.request.AddFundsToWalletRequest;
import com.logifuture.wallet.dto.wallet.request.CreateWalletRequest;
import com.logifuture.wallet.dto.wallet.request.GetWalletRequest;
import com.logifuture.wallet.dto.wallet.request.RemoveFundsFromWalletRequest;
import com.logifuture.wallet.dto.wallet.response.CreateWalletResponse;
import com.logifuture.wallet.dto.wallet.response.GetWalletResponse;
import com.logifuture.wallet.service.wallet.WalletCreationService;
import com.logifuture.wallet.service.wallet.WalletFundsManagementService;
import com.logifuture.wallet.service.wallet.WalletRetrievalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@Slf4j
public class WalletRestController {
    private final WalletCreationService walletCreationService;
    private final WalletFundsManagementService walletFundsManagementService;
    private final WalletRetrievalService walletRetrievalService;

    public WalletRestController(WalletCreationService walletCreationService, WalletFundsManagementService walletFundsManagementService, WalletRetrievalService walletRetrievalService) {
        this.walletCreationService = walletCreationService;
        this.walletFundsManagementService = walletFundsManagementService;
        this.walletRetrievalService = walletRetrievalService;
    }


    @PostMapping()
    public ResponseEntity<CreateWalletResponse> createWallet(@RequestBody @Valid CreateWalletRequest createWalletRequest) {
        log.info("User with id: {} asked for creation of wallet", createWalletRequest.userId());
        return ResponseEntity.ok(walletCreationService.createWallet(createWalletRequest));
    }

    @PostMapping("/{walletId}/add-funds")
    public ResponseEntity addFundsToWallet(@PathVariable Long walletId, @RequestBody AddFundsToWalletRequest addFundsToWalletRequest) {
        log.info("User with id:{} and wallet id: {} asked to add: {} in wallet", addFundsToWalletRequest.userId(), walletId, addFundsToWalletRequest.amount());
        walletFundsManagementService.addFunds(walletId, addFundsToWalletRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{walletId}/remove-funds")
    public ResponseEntity removeFundsFromWallet(@PathVariable Long walletId, @RequestBody RemoveFundsFromWalletRequest removeFundsFromWalletRequest) {
        log.info("User with id:{} and wallet id: {} asked to remove: {} from wallet", removeFundsFromWalletRequest.userId(), walletId, removeFundsFromWalletRequest.amount());
        walletFundsManagementService.removeFunds(walletId, removeFundsFromWalletRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<GetWalletResponse> getWallet(@PathVariable Long walletId, @RequestBody GetWalletRequest getWalletRequest) {
        GetWalletResponse outputDTO = walletRetrievalService.getWallet(walletId,getWalletRequest);
        return ResponseEntity.ok(outputDTO);
    }
}