package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.api.models.CardSaverEnum;
import com.turkcell.rentACar.api.models.CreatePaymentModel;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.getdto.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.listdto.PaymentListDto;
import com.turkcell.rentACar.business.requests.delete.DeletePaymentRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.exceptions.entityExceptions.PaymentException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	private PaymentService paymentService;
	
	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("/getAll")
	DataResult<List<PaymentListDto>> getAll(){
		return this.paymentService.getAll();
	}
	@GetMapping("/getById")
	DataResult<GetPaymentDto> getById(@RequestParam int paymentId) throws PaymentException{
		return this.paymentService.getById(paymentId);
	}
	@PostMapping("/addPaymentForIndividualCustomer/{cardSaver}")
	Result addPaymentForIndividualCustomer(@RequestBody @Valid CreatePaymentModel createPaymentModel,
			@RequestParam @PathVariable(value = "cardSaver") CardSaverEnum cardSaver) throws BusinessException{
		return this.paymentService.addPaymentForIndividualCustomer(createPaymentModel, cardSaver);
	}
	
	@PostMapping("/addPaymentForCorporateCustomer/{cardSaver}")
	Result addPaymentForCorporateCustomer(@RequestBody @Valid CreatePaymentModel createPaymentModel, 
			@RequestParam @PathVariable(value = "cardSaver") CardSaverEnum cardSaver) throws BusinessException{
		return this.paymentService.addPaymentForCorporateCustomer(createPaymentModel, cardSaver);
	}
	@DeleteMapping("/delete")
	Result delete(@RequestBody @Valid DeletePaymentRequest deletePaymentRequest) throws PaymentException{
		return this.paymentService.delete(deletePaymentRequest);
	}

}
