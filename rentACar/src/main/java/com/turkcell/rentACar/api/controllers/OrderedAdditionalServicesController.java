package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.getdto.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.listdto.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.create.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.delete.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.update.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/ordered-additional-services")
public class OrderedAdditionalServicesController {
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll(){
		return this.orderedAdditionalServiceService.getAll();
	}
	@GetMapping("/getById")
	public DataResult<GetOrderedAdditionalServiceDto> getById(@RequestParam int id) throws BusinessException{
		return this.orderedAdditionalServiceService.getById(id);
	}
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}
	@PutMapping("/update")
	public Result update (@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}
	
}
