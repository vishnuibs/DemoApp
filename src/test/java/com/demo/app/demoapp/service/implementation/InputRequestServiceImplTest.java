package com.demo.app.demoapp.service.implementation;

import com.demo.app.demoapp.clients.DemoClient;
import com.demo.app.demoapp.dto.BillingLineDto;
import com.demo.app.demoapp.dto.BillingLineInformationDto;
import com.demo.app.demoapp.dto.InputRequestDto;
import com.demo.app.demoapp.entity.BillingHeader;
import com.demo.app.demoapp.entity.BillingLineInformation;
import com.demo.app.demoapp.entity.InputRequest;
import com.demo.app.demoapp.exceptions.ResourceNotFoundException;
import com.demo.app.demoapp.repo.InputRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InputRequestServiceImplTest {

    @Mock
    private InputRequestRepository inputRequestRepository;

    @Spy
    private ModelMapper modelMapper; // using the real model mapper instead of mocking

    @Mock
    private DemoClient demoClient;

    @InjectMocks
    private InputRequestServiceImpl inputRequestService;


    private InputRequest mockInputRequest;
    private InputRequestDto  mockInputRequestDto;

    @BeforeEach
    void setup() {
        mockInputRequest =  InputRequest.builder()
                .id(1L)
                .billingHeader(createMockBillingHeader())
                .build();
        mockInputRequest.setBillingLines(createMockBillingLines(mockInputRequest));

        mockInputRequestDto = modelMapper.map(mockInputRequest,InputRequestDto.class);
    }

    @Test
    void testGenerateRequest_WhenInputRequestIsValid_ThenReturnSavedInputRequest() {

        when(demoClient.getResponse()).thenReturn("Demo Client Success Response");
        when(inputRequestRepository.save(any(InputRequest.class))).thenReturn(mockInputRequest);
        when(modelMapper.map(mockInputRequest, InputRequestDto.class)).thenReturn(mockInputRequestDto);

        InputRequestDto result = inputRequestService.generateRequest(mockInputRequestDto);

        verify(demoClient, times(1)).getResponse();
        verify(inputRequestRepository, times(1)).save(any(InputRequest.class));
        assertNotNull(result);
        assertEquals(mockInputRequestDto, result);
    }

    @Test
    void TestGetRequest_WhenRequestIdIsPresent_ThenReturnInputRequest() {
        long id  =1L;

        when(inputRequestRepository.findById(id))
                .thenReturn(Optional.of(mockInputRequest)); //stubbing

        InputRequestDto inputRequestDto = inputRequestService.getRequest(id);

        assertThat(inputRequestDto).isNotNull();

        assertThat(inputRequestDto.getBillingHeader()).isNotNull();
        assertThat(inputRequestDto.getBillingHeader().getBillingId())
                .isEqualTo(mockInputRequest.getBillingHeader().getBillingId());
        assertThat(inputRequestDto.getBillingHeader().getInvoiceDate())
                .isEqualTo(mockInputRequest.getBillingHeader().getInvoiceDate());
        assertThat(inputRequestDto.getBillingHeader().getTotalInvoiceAmount())
                .isEqualTo(mockInputRequest.getBillingHeader().getTotalInvoiceAmount());

        assertThat(inputRequestDto.getBillingLines()).hasSize(mockInputRequest.getBillingLines().size());

        for (int i = 0; i < inputRequestDto.getBillingLines().size(); i++) {
            BillingLineDto billingLineDto = inputRequestDto.getBillingLines().get(i);
            BillingLineInformationDto expectedBillingLine = modelMapper.map(mockInputRequest.getBillingLines().get(i),BillingLineInformationDto.class);

            assertThat(billingLineDto).isNotNull();

            assertThat(billingLineDto.getBillingLineInformation()).isNotNull();
            assertThat(billingLineDto.getBillingLineInformation().getProductType())
                    .isEqualTo(expectedBillingLine.getProductType());
            assertThat(billingLineDto.getBillingLineInformation().getLineType())
                    .isEqualTo(expectedBillingLine.getLineType());
        }
        verify(inputRequestRepository, only()).findById(id);
    }

    @Test
    void TestGetRequest_WhenInputRequestIsNotPresent_ThenThrowException() {
       when(inputRequestRepository.findById(anyLong())).thenReturn(Optional.empty());


       assertThatThrownBy(() -> inputRequestService.getRequest(1L))
               .isInstanceOf(ResourceNotFoundException.class)
               .hasMessage("Request not found with id: 1");

       verify(inputRequestRepository).findById(1L);
    }

    private static BillingHeader createMockBillingHeader() {
        return BillingHeader.builder()
                .id(1L) // Mock ID
                .billingId("BILL-12345")
                .invoiceDate("2024-11-25")
                .totalInvoiceAmount(1500.00)
                .build();
    }

    private static List<BillingLineInformation> createMockBillingLines(InputRequest inputRequest) {
        List<BillingLineInformation> lines = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            BillingLineInformation line = BillingLineInformation.builder()
                    .id((long) i) // Mock ID for each line
                    .inputRequest(inputRequest) // This will be set later
                    .productType("Product " + i)
                    .lineType("Type " + i)
                    .build();
            lines.add(line);
        }

        return lines;
    }

}