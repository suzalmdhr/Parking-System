package com.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entities.floor;

public interface FloorRepo extends JpaRepository<floor , Long> {

}
