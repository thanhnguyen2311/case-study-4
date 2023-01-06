package com.example.module_4.controller;

import com.example.module_4.model.House;
import com.example.module_4.model.RentHouse;
import com.example.module_4.service.IHouseService;
import com.example.module_4.service.IRentHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rent")
public class RentHouseController {
    @Autowired
    private IRentHouseService rentHouseService;
    @Autowired
    private IHouseService houseService;

    @GetMapping("{id}")
    public ResponseEntity<List<RentHouse>> findRentHousesByHouse(@PathVariable Long id){
        List<RentHouse> rentHouses = rentHouseService.findAllByHouse(houseService.findById(id).get());
        if (rentHouses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rentHouses, HttpStatus.OK);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<House>> top5RentHouse(){
        List<House> houses = houseService.top5RentHouse();
        if (houses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody RentHouse rentHouse){
        if (rentHouseService.checkRentHouse(rentHouse)){
            rentHouseService.save(rentHouse);
            return new ResponseEntity<>("successful rental!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}