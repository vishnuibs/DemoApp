package com.demo.app.demoapp.controller;


import com.demo.app.demoapp.dto.InputRequestDto;
import com.demo.app.demoapp.service.implementation.InputRequestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
public class InvoiceRequestController {

    private InputRequestServiceImpl inputRequestService;

    public InvoiceRequestController(
            InputRequestServiceImpl inputRequestService
            ) {
        this.inputRequestService = inputRequestService;
    }

    @PostMapping("/generate")
    public ResponseEntity<InputRequestDto> generateBilling(@RequestBody InputRequestDto inputRequestDto){
        InputRequestDto savedInputReqeustDto = inputRequestService.generateRequest(inputRequestDto);
        return new ResponseEntity<>(savedInputReqeustDto, HttpStatus.CREATED);
    }

    @GetMapping("request/{id}")
    public ResponseEntity<InputRequestDto> getBilling(@PathVariable long id){
        return ResponseEntity.ok(inputRequestService.getRequest(id));
    }

}