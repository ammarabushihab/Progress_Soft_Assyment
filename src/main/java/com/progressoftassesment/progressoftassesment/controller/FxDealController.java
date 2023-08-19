package com.progressoftassesment.progressoftassesment.controller;

import com.progressoftassesment.progressoftassesment.model.FxDeals;
import com.progressoftassesment.progressoftassesment.service.FxDealsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FxDealController {



    @Autowired
    private FxDealsService fxDealsService;

@PostMapping()
    public ResponseEntity<?> addEmployee(@RequestBody @Valid FxDeals fxDeals) {
    log.info(fxDeals.toString());
    fxDealsService.saveFxDeal(fxDeals);
        return ResponseEntity.ok(fxDeals);

}


@GetMapping("/findalldeals")
    public List<FxDeals> findAllDeals(){
    return fxDealsService.getAllDeals();
}

    @GetMapping("/findalldealsbyid")
    public FxDeals getFxDealID(@RequestParam long id) {
        return fxDealsService.getFxDealById(id);}


}
