package com.example.module_4.repository;

import com.example.module_4.model.House;
import com.example.module_4.model.RentHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRentHouseRepository extends JpaRepository<RentHouse, Long> {
    List<RentHouse> findAllByHouse(House house);

    @Query(value = "select * from Rent_house where check_in = false and status = true and house_id = ?1", nativeQuery = true)
    List<RentHouse> findAllHouseCheck(Long id);
}
