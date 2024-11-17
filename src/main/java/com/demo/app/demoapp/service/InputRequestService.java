package com.demo.app.demoapp.service;

import com.demo.app.demoapp.dto.InputRequestDto;

import java.util.Optional;

public interface InputRequestService {

    public InputRequestDto generateRequest(InputRequestDto inputRequestDto);

    public InputRequestDto getRequest(long id);
}
