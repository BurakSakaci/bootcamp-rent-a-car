package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.getdto.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.listdto.InvoiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<InvoiceListDto>> getAll() {
		return this.invoiceService.getAll();
	}

	@GetMapping("/getbyid/{id}")
	public DataResult<GetInvoiceDto> getById(@RequestParam int id) throws BusinessException {
		return this.invoiceService.getById(id);
	}

	public DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(@PathVariable("from_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, 
			@PathVariable("to_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
		return this.invoiceService.getInvoicesBetweenDates(fromDate, toDate);
	}
	/*
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		return this.invoiceService.add(createInvoiceRequest);
	}
	*/
	@DeleteMapping("/delete")
	public Result delete(int id) throws BusinessException {
		return this.invoiceService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		return this.invoiceService.update(updateInvoiceRequest);
	}

}
