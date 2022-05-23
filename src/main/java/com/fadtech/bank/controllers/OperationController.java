package com.fadtech.bank.controllers;

import com.fadtech.bank.exception.InvalidDataException;
import com.fadtech.bank.model.Amount;
import com.fadtech.bank.model.Operation;
import com.fadtech.bank.service.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.fadtech.bank.utils.ApiUrls.*;
import static com.fadtech.bank.utils.Message.INVALID_DATA_EXCEPTION_ERROR_MESSAGE;

@RestController
@AllArgsConstructor
@RequestMapping(OPERATION_BASE_URI)
/**
 * OperationController class use to handle all operation request
 */
public class OperationController {

    private final OperationService operationService;

    /**
     *  Get all operations of an account
     * @param accountId
     * @return List<Operation>
     */
    @GetMapping()
    public List<Operation> getOperationsByAccountId(@RequestParam("accountId") Long accountId) {
        return operationService.getOperationsByAccountId(accountId);
    }

    /**
     *  make a deposit
     * @param json
     * @return Operation
     */
    @PostMapping(DEPOSIT_URI)
    public Operation deposit(@RequestBody Map<String, String> json) {
        try {
            long accountId = Long.parseLong(json.get("accountId"));
            Amount amount = new Amount(new BigDecimal(json.get("amount")));
            return operationService.deposit(accountId, amount);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, INVALID_DATA_EXCEPTION_ERROR_MESSAGE, exception);
        }
    }

    /**
     *  make a withdrawal
     * @param json
     * @return Operation
     * @throws InvalidDataException
     */
    @PostMapping(WITHDRAW_URI)
    public Operation withdraw(@RequestBody Map<String, String> json) throws InvalidDataException {
        try {
            long accountId = Long.parseLong(json.get("accountId"));
            Amount amount = new Amount(new BigDecimal(json.get("amount")));
            return operationService.withdrawal(accountId, amount);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, INVALID_DATA_EXCEPTION_ERROR_MESSAGE, exception);
        }
    }

}
