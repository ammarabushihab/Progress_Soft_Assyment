package com.progressoftassesment.progressoftassesment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progressoftassesment.progressoftassesment.error.RecordFoundException;
import com.progressoftassesment.progressoftassesment.error.RecordNotFoundException;
import com.progressoftassesment.progressoftassesment.model.FxDeals;
import com.progressoftassesment.progressoftassesment.service.FxDealsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import springfox.documentation.spring.web.json.Json;

import static org.mockito.BDDMockito.*;
import static  org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FxDealControllerTest {


    @Autowired
    private MockMvc mockMvc;







    @Test
    public void findAllFxDealsTest()throws Exception{
    mockMvc.perform(get("/api/v1/findalldeals")).andExpect(status().isOk());

    }


    @Test
    public void whenGetAllFxDeals_thenReturnJsonArray()throws Exception {
        Random random = new Random();
        long id1 = random.nextLong();
        long id2 = random.nextLong();

        FxDeals fxDeals1 = new FxDeals(id1, "EUR", "JOD", null, 99.39);
        FxDeals fxDeals2 = new FxDeals(id2, "USD", "EUR", null, 1533.22);

        ObjectMapper mapper = new ObjectMapper();
        List<FxDeals> fxDealsList = Arrays.asList(fxDeals1, fxDeals2);
        mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fxDeals1)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fxDeals2)))
                .andExpect(status().isOk());
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/v1/findalldeals").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn();

        fxDealsList = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<FxDeals>>(){});
        long count = fxDealsList.stream().filter(fxDeals -> fxDeals.getDealId().equals(id1) || fxDeals.getDealId().equals(id2)).count();
        assertEquals(2, count);
    }
        @Test
    public void whenPostFxDeal_thenCreateFxDeal()throws Exception{
        Random random=new Random();
        long id = random.nextLong();
        FxDeals fxDeals1=new FxDeals();
        fxDeals1.setDealId(id);
        fxDeals1.setFromCurrencyISOCode("USD");
        fxDeals1.setToCurrencyISOCode("JOD");
        fxDeals1.setDealTimeStamp(null);
        fxDeals1.setAmountOfDeal(999.78);

        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValueAsString(fxDeals1);

        mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fxDeals1)))
                        .andExpect(status().isOk());

       mockMvc.perform(get("/api/v1/findalldealsbyid?id="+id).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

    }

    @Test
    public void whenPostFxDealWithExistIdNumber_thenFxDealReject()throws Exception{

        FxDeals fxDeals1=new FxDeals();
        fxDeals1.setDealId(1l);
        fxDeals1.setFromCurrencyISOCode("USD");
        fxDeals1.setToCurrencyISOCode("JOD");
        fxDeals1.setDealTimeStamp(null);
        fxDeals1.setAmountOfDeal(999.78);

        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValueAsString(fxDeals1);

        mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(fxDeals1)))
                .andExpect(status().isFound());



    }



    @Test()
    public void whenPostFxDeal_thenCheckNotExistingDealId_ThenShouldBeReject()throws Exception{

        mockMvc.perform(get("/api/v1/findalldealsbyid?id=759284856").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


}
