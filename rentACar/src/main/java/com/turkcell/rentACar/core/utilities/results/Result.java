package com.turkcell.rentACar.core.utilities.results;

public class Result {
	private boolean success;
	private String message;

	public Result(boolean success) {
		this.success=success;
	}

	public Result(boolean success, String message) {
		this(success);		//burada this.success=success diyebilirdik ama yukarıda dediğimizden ötürü tekrara düşmemek adına bu yönteme kullandık
		this.message=message;
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	public String getMessage() {
		return this.message;
	}

}