package com.progressoftassesment.progressoftassesment.service;

import com.progressoftassesment.progressoftassesment.error.CurrencyNotFoundException;
import com.progressoftassesment.progressoftassesment.error.RecordFoundException;
import com.progressoftassesment.progressoftassesment.error.RecordNotFoundException;
import com.progressoftassesment.progressoftassesment.model.FxDeals;
import com.progressoftassesment.progressoftassesment.repository.FxDealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FxDealsService {

    private static Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();

    @Autowired
    private FxDealRepository fxDealRepository;

    public  void saveFxDeal(FxDeals fxDeals) {
        if (!validateCurrency(fxDeals.getFromCurrencyISOCode()) || !validateCurrency(fxDeals.getToCurrencyISOCode())) {

            throw new CurrencyNotFoundException("Invalid currency");
        }

        if (fxDealRepository.existsById(fxDeals.getDealId())) {
            throw new RecordFoundException("FX Deal ID Already Exist");
        }




        fxDealRepository.save(fxDeals);
        log.info(fxDeals.getDealId() + " Already Saved .");
    }
public List<FxDeals> getAllDeals(){

  return   fxDealRepository.findAll();
}
    public FxDeals getFxDealById(long id){

        return     fxDealRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("This FXDeal Transaction ID Not Exist :-"+ id));}

private boolean validateCurrency(String value){
    return availableCurrencies.stream()
            .anyMatch(currency -> currency.getCurrencyCode().equals(value));
}


}
