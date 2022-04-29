package com.turkcell.rentACar.business.requests.delete;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.turkcell.rentACar.business.requests.update.UpdateCreditCardRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCreditCardRequest {
	@NotNull
	@NotBlank
	private int cardId;
}
