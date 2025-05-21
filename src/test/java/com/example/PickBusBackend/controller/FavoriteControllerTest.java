package com.example.PickBusBackend.controller;

import com.example.PickBusBackend.repository.FavoriteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ObjectMapper objectMapper;


}
