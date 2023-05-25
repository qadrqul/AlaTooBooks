package com.example.academics.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



}
