package com.progressoftassesment.progressoftassesment;

import com.progressoftassesment.progressoftassesment.error.CurrencyNotFoundException;
import com.progressoftassesment.progressoftassesment.error.RecordFoundException;
import com.progressoftassesment.progressoftassesment.error.RecordNotFoundException;
import com.progressoftassesment.progressoftassesment.model.FxDeals;
import com.progressoftassesment.progressoftassesment.repository.FxDealRepository;
import com.progressoftassesment.progressoftassesment.service.FxDealsService;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.BDDMockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RunWith(SpringRunner.class)
public class FxDealServiceTest {

    @MockBean
    private FxDealRepository fxDealRepository;

    @Autowired
    private FxDealsService fxDealsService;

    @TestConfiguration
    static class FxDealsServiceContextConfiguration{
        @Bean
        public FxDealsService fxDealsService(){
            return new FxDealsService();
        }

    }




    @Test
    public  void    whenAddNewFxDeal_ThenWillCreateFxDeal(){
        Random random = new Random();
        Long id1 = random.nextLong();

        FxDeals fxDeals1=new FxDeals(id1,"JOD","JOD", null,99.39);
        given(fxDealRepository.existsById(id1)).willReturn(false);
        given(fxDealRepository.save(fxDeals1)).willReturn(fxDeals1);
        given(fxDealRepository.findById(id1)).willReturn(Optional.of(fxDeals1));
        fxDealsService.saveFxDeal(fxDeals1);
        FxDeals fxDealById = fxDealsService.getFxDealById(id1);
        Assert.assertEquals(id1,fxDealById.getDealId());

    }

    @Test(expected = RecordFoundException.class)
    public  void    whenAddNewFxDealWithExistDealId_ThenWillRejectFxDeal(){
        Random random = new Random();
        Long id1 = random.nextLong();

        FxDeals fxDeals1=new FxDeals(id1,"JOD","JOD", null,99.39);
        given(fxDealRepository.existsById(id1)).willReturn(true);
        fxDealsService.saveFxDeal(fxDeals1);
        FxDeals fxDealById = fxDealsService.getFxDealById(id1);
        Assert.assertEquals(id1,fxDealById.getDealId());

    }


    @Test(expected = CurrencyNotFoundException.class)
    public  void    whenAddNewFxDealWithExistCurrency_ThenWillRejectFxDeal(){
        Random random = new Random();
        Long id1 = random.nextLong();

        FxDeals fxDeals1=new FxDeals(id1,"fff","JOD", null,99.39);

        fxDealsService.saveFxDeal(fxDeals1);
        FxDeals fxDealById = fxDealsService.getFxDealById(id1);
        Assert.assertEquals(id1,fxDealById.getDealId());

    }

    @Test
    public void whenFindAll_ReturnAllFxDealsList(){

        FxDeals fxDeals1=new FxDeals(1524l,"JOD","JOD", null,99.39);
        FxDeals fxDeals2=new FxDeals(554l,"USD","JOD", null,1533.22);
        List<FxDeals> fxDealsList=Arrays.asList(fxDeals1,fxDeals2);

        given(fxDealRepository.findAll()).willReturn(fxDealsList);

        Assert.assertEquals(2, fxDealsService.getAllDeals().size());


    }

    @Test
    public void whenGetFxDealById_FxDealShouldBeFound(){
        FxDeals fxDeals1=new FxDeals(1524l,"JOD","JOD", null,99.39);
        List<FxDeals> fxDealsList=Arrays.asList(fxDeals1);
        given(fxDealRepository.findById(fxDeals1.getDealId())).willReturn(fxDealsList.stream().findAny());
        FxDeals result=fxDealsService.getFxDealById(1524l);
            Assert.assertEquals("JOD",result.getFromCurrencyISOCode());

    }

    @Test(expected = RecordNotFoundException.class)
    public void whenGetInvalidFxDealId_FxDealShouldNotBeFound(){
       given(fxDealRepository.findById(anyLong())).willReturn(Optional.empty());
       fxDealsService.getFxDealById(759284856);

    }




}
