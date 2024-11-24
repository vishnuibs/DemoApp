package com.demo.app.demoapp.service.implementation;

import com.demo.app.demoapp.clients.DemoClient;
import com.demo.app.demoapp.exceptions.ResourceNotFoundException;
import com.demo.app.demoapp.dto.BillingHeaderDto;
import com.demo.app.demoapp.dto.BillingLineDto;
import com.demo.app.demoapp.dto.InputRequestDto;
import com.demo.app.demoapp.entity.BillingHeader;
import com.demo.app.demoapp.entity.BillingLineInformation;
import com.demo.app.demoapp.entity.InputRequest;
import com.demo.app.demoapp.repo.InputRequestRepository;
import com.demo.app.demoapp.service.InputRequestService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InputRequestServiceImpl implements InputRequestService {

    @Value("${demoClient.resiliency.check}")
    private Boolean RESILIENCY_CHECK;

    private ModelMapper modelMapper;
    private InputRequestRepository inputRequestRepository;
    private DemoClient demoClient;

    public InputRequestServiceImpl(
            ModelMapper modelMapper,
            InputRequestRepository inputRequestRepository,
            DemoClient demoClient) {
        this.modelMapper = modelMapper;
        this.inputRequestRepository = inputRequestRepository;
        this.demoClient = demoClient;
    }


    @Override
    public InputRequestDto generateRequest(InputRequestDto inputRequestDto) {
        if(RESILIENCY_CHECK) {
            log.info("Hitting Demo Client for resiliency check");
            var clientResponse  = demoClient.getResponse();
            log.info("Response: {}", clientResponse);
        }
        InputRequest inputRequest = new InputRequest();
        BillingHeaderDto billingHeaderDto = inputRequestDto.getBillingHeader();
        List<BillingLineDto> billingLinesDto = inputRequestDto.getBillingLines();

        inputRequest.setBillingHeader(BillingHeader
                .builder()
                .billingId(billingHeaderDto.getBillingId())
                .invoiceDate(billingHeaderDto.getInvoiceDate())
                .totalInvoiceAmount(billingHeaderDto.getTotalInvoiceAmount())
                .build()
        );
        inputRequest.setBillingLines(billingLinesDto.stream()
                .map(bl -> BillingLineInformation
                        .builder()
                        .inputRequest(inputRequest)
                        .productType(bl.getBillingLineInformation().getProductType())
                        .lineType(bl.getBillingLineInformation().getLineType())
                        .build())
                .toList());
        log.info("Saving Model: {}", inputRequest);
        return modelMapper.map(inputRequestRepository.save(inputRequest),InputRequestDto.class);
    }

    @Override
    public InputRequestDto getRequest(long id) {
        log.info("Fetching Model with id: {}",id);
        return inputRequestRepository.findById(id)
                .map(inputRequest -> modelMapper.map(inputRequest, InputRequestDto.class))
                .orElseThrow(() ->new ResourceNotFoundException("Request not found with id : " +id));

    }
}
