package com.iiht.StockMarket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.dto.InvalidCompanyExceptionResponse;
import com.iiht.StockMarket.exception.CompanyNotFoundException;
import com.iiht.StockMarket.exception.InvalidCompanyException;
import com.iiht.StockMarket.services.CompanyInfoService;

@RestController
@RequestMapping (value = "/company")
public class CompanyInfoController {

	@Autowired
	private CompanyInfoService companyInfoService;

	//-------------------------------------------------------------------------------------------------------------------------------
	// SERVICE OPERATIONS
	//-------------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping(value="/addCompany")																					// 3. WORKING
	public ResponseEntity<CompanyDetailsDTO> addCompanyDetails(@Valid @RequestBody CompanyDetailsDTO companyDetailsDTO, BindingResult bindingResult) throws InvalidCompanyException{
      CompanyDetailsDTO companyDTO=companyInfoService.saveCompanyDetails(companyDetailsDTO); 
      return new ResponseEntity<CompanyDetailsDTO>(companyDTO,new HttpHeaders(),HttpStatus.OK);
    }
      		
	@DeleteMapping(value = "/deleteCompany/{companyCode}")																
	public ResponseEntity<CompanyDetailsDTO> deleteCompanyDetails(@PathVariable("companyCode") Long companyCode) {
        CompanyDetailsDTO companyDTO=companyInfoService.deleteCompany(companyCode);
         return new ResponseEntity<CompanyDetailsDTO>(companyDTO,new HttpHeaders(),HttpStatus.OK);
    }

	
    @GetMapping(value = "/getCompanyInfoById/{companyCode}")																														
	public ResponseEntity<CompanyDetailsDTO> getCompanyDetailsById(@PathVariable("companyCode") Long companyCode) {
		CompanyDetailsDTO companyDetailsDTO = companyInfoService.getCompanyInfoById(companyCode);
		return new ResponseEntity<CompanyDetailsDTO>(companyDetailsDTO, new HttpHeaders(), HttpStatus.OK);
    }
	
	@GetMapping(value = "/getAllCompanies", produces = "application/json")												
	public ResponseEntity<List<CompanyDetailsDTO>> getAllCompanies() {	
        List<CompanyDetailsDTO> listAllCompanies=companyInfoService.getAllCompanies();
        return new ResponseEntity<List<CompanyDetailsDTO>>(listAllCompanies, new HttpHeaders(), HttpStatus.OK);
    }
	
	//================================================================================================
	//			UTITLITY EXCEPTION HANDLERS - 2
	//================================================================================================
	@ExceptionHandler(InvalidCompanyException.class)
	public ResponseEntity<InvalidCompanyExceptionResponse> companyHandler(InvalidCompanyException ex) {
		InvalidCompanyExceptionResponse resp = new InvalidCompanyExceptionResponse(ex.getMessage(),System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value());
		ResponseEntity<InvalidCompanyExceptionResponse> response =	new ResponseEntity<InvalidCompanyExceptionResponse>(resp, HttpStatus.BAD_REQUEST);
		return response;
	}
	//------------------------------------------------------------------------------------------------
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<InvalidCompanyExceptionResponse> companyHandler(CompanyNotFoundException ex){
		InvalidCompanyExceptionResponse resp = new InvalidCompanyExceptionResponse(ex.getMessage(),System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		ResponseEntity<InvalidCompanyExceptionResponse> response = new ResponseEntity<InvalidCompanyExceptionResponse>(resp, HttpStatus.NOT_FOUND);
		return response;
	}	
}