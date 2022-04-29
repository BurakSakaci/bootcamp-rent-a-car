package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.getdto.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.listdto.InvoiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concrates.Invoice;
import com.turkcell.rentACar.entities.concrates.Rent;

public interface InvoiceService {

	DataResult<List<InvoiceListDto>> getAll();
	DataResult<GetInvoiceDto> getById(int id) throws BusinessException ;
	DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(LocalDate fromDate, LocalDate toDate);
	Result delete(int id) throws BusinessException;
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;	
	DataResult<Invoice> invoiceCreator(Rent rent);
	DataResult<Invoice> invoiceCreatorForEndRent(Rent rent);
}
