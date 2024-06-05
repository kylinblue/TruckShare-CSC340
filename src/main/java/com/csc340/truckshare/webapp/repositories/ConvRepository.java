package com.csc340.truckshare.webapp.repositories;

import com.csc340.truckshare.webapp.models.Conv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConvRepository
        extends JpaRepository<Conv, Integer> {
    @Query(value = "select * from conversation where user_Id =?1", nativeQuery = true)
    List<Conv> queryByUserId(int userId);

}
