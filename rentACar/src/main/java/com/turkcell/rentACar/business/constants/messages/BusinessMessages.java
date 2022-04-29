package com.turkcell.rentACar.business.constants.messages;

public class BusinessMessages {
	
	public static final String TO = " to ";

    public class GlobalMessages{

        public static final String DATA_LISTED_SUCCESSFULLY = "Data Listed Successfully: ";
        public static final String DATA_GETTED_SUCCESSFULLY = "Data Getted from this id.";
        public static final String DATA_ADDED_SUCCESSFULLY = "Data Added Successfully: ";
        public static final String DATA_UPDATED_SUCCESSFULLY = "Data updated successfully: ";
        public static final String DATA_DELETED_SUCCESSFULLY = "Data deleted successfully: ";
        public static final String PAGED_SUCCESSFULLY = "Paged Successfully.";

    }

    public class AdditionalServiceMessages{

        public static final String ADDITIONAL_SERVICE_ALREADY_EXISTS = "Additional Service is already exists: ";
        public static final String ADDITIONAL_SERVICE_NOT_FOUND = "There is no Additional Service with this id";
        //public static final String ADDITIONAL_SERVICE_HAS_NO_CHANGES = "This Additional Service Update Has No Changes: ";
    }

    public class OrderedAdditionalServiceMessages{
    	public static final String ORDERED_ADDITIONAL_SERVICE_NOT_FOUND = "There is no Ordered Additional Service with this id";
    }
    
    public class BrandMessages{

        public static final String BRAND_ALREADY_EXISTS = "Brand is already exists: ";
        public static final String BRAND_NOT_FOUND = "There is no Brand with this id: ";
        //public static final String BRAND_HAS_NO_CHANGES = "This Brand Update Has No Changes: ";
    }

    public class CarDamageMessages{

        public static final String CAR_DAMAGE_ALREADY_EXISTS = "Car Damage is already exists: ";
        public static final String CAR_DAMAGE_NOT_FOUND = "There is no Car Damage with this id: ";
    }

    public class CarMaintenanceMessages{

        public static final String CAR_NOT_FOUND = "There is no Car with this id: ";
        public static final String CAR_RENT_NOT_FOUND = "There is no Rent for this Car's id: ";
        public static final String CAR_IS_RENTED = "Maintenance is not possible! This car is rented";
        public static final String CAR_MAINTENANCE_NOT_FOUND = "There is no car maintenance with this id: ";
        //public static final String NO_CHANGES_NO_NEED_TO_UPDATE = "Initial values are completely equal to update values, no need to update!";
        public static final String CAR_IS_ALREADY_AT_MAINTENANCE = "This car is already in maintenance";
    }

    public class CarMessages{

        public static final String CAR_NOT_FOUND = "There is no Car with this id: ";
        public static final String BRAND_NOT_FOUND_IN_CARS = "There is no Brand with this id in cars";
        public static final String COLOR_NOT_FOUND_IN_CARS = "There is no Color with this id in cars ";
        public static final String SORTED_CAR_LIST_SUCCESS = "Car list successfully sorted.";
        public static final String SORTED_CAR_LIST_LESS_THAN = "Car list successfully sorted less than desired price.";
        public static final String SORTED_CAR_LIST_GREATER_THAN = "Car list successfully sorted greater than desired price.";
        public static final String CAR_LIST_BETWEEN_PRICE = "Cars listed successfully between prices.";
        public static final String INVALID_PRICE = "Desired prices are wrong.";
        public static final String CAR_KM_UPDATED = "Cars kilometer updated.";
        public static final String INVALID_ORDER_OF_SORT = "Invalid order for sorting.";
        public static final String PAGE_NUMBERS_INCORRECT = "Page size and page number must be greater than zero";        
    }

    public class ColorMessages{

        public static final String COLOR_ALREADY_EXISTS = "Following Color is already exists: ";
        public static final String COLOR_NOT_FOUND = "There is no Color with this id: ";
        //public static final String COLOR_HAS_NO_CHANGES = "This Color Update Has No Changes: ";
    }

    public class CorporateCustomerMessages{

        public static final String CORPORATE_CUSTOMER_NOT_FOUND = "There is no Corporate Customer with this id: ";
        public static final String CORPORATE_CUSTOMER_INVALID_REQUEST = "Invalid request while creating Corporate Customer.";
        public static final String CORPORATE_CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
        public static final String CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS = "Following Tax Number is already exists: ";
    }

    public class CustomerMessages{

        public static final String CUSTOMER_NOT_FOUND = "There is no Customer with this id: ";
        public static final String CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
    }

    public class CityMessages{
        public static final String CITY_NOT_FOUND = "There is no City with this id:";
        public static final String CITY_IS_ALREADY_EXISTS = "This city is already exists.";
    }

    public class IndividualCustomerMessages{

        public static final String INDIVIDUAL_CUSTOMER_NOT_FOUND = "There is no Individual Customer with this id: ";
        public static final String INDIVIDUAL_CUSTOMER_INVALID_REQUEST = "Invalid request while creating Individual Customer.";
        public static final String INDIVIDUAL_CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
        public static final String INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS = "Following National Identity is already exists: ";
    }

    public class InvoiceMessages{

        //public static final String RENTAL_CAR_NOT_FOUND = "There is no Rental Car with this id: ";
        public static final String INVOICE_NOT_FOUND = "There is no Invoice with this id: ";
        public static final String INVOICES_BETWEEN_DATES_LISTED = "Invoices listed between dates.";
    }

    public class RentMessages{

        //public static final String CITY_NOT_FOUND = "There is no City with this id: ";
        public static final String CAR_NOT_FOUND = "There is no Car with following id: ";
        public static final String RENTAL_CAR_NOT_FOUND = "There is no Rental Car with this id: ";
        //public static final String NO_CHANGES_NO_NEED_TO_UPDATE = "Initial values are completely equal to update values, no need to update!";
        //public static final String CUSTOMER_NOT_FOUND = "There is no Customer with this id: ";
        public static final String CAR_IS_AT_MAINTENANCE = "This car is in maintenance";
        public static final String CAR_IS_ALREADY_RENTED = "This car is already rented";
        public static final String CAR_RENTAL_ENDED_SUCCESSFULLY = "Car Rental Ended Successfully";
        public static final String CAR_RENTAL_ENDED_WITH_PAYMENT_SUCCESSFULLY = "Car Rental Ended with Payment Successfully.";
        public static final String RENTAL_DATES_ARE_NOT_CORRECT = "Rental dates are not correct";
        public static final String INVALID_KM = "Invalid Kilometer, return kilometer must be greater than rented kilometer.";
        
    }

    public class UserMessages{

        public static final String USER_NOT_FOUND = "There is no Customer with this id: ";
        public static final String USER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
    }

    public class PaymentMessages{

        public static final String INVALID_PAYMENT = "Invalid payment operation!";
        public static final String PAYMENT_NOT_FOUND = "Payment Not Found";
        public static final String INSUFFICIENT_BALANCE = "Insufficient Balance";
    }
    public class CreditCardMessages{
    	public static final String INVALID_CREDIT_CARD = "Invalid Card";
    	public static final String INVALID_CARD_DATES = "Invalid Dates for Card";
    	public static final String CARD_ALREADY_EXISTS = "Card already exists";
        public static final String CREDIT_CARD_NOT_FOUND = "Credit Card Not Found";
        public static final String CUSTOMER_NOT_FOUND = "Customer Not Found";
    }
}
