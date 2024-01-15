package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Repas;
import com.example.stockifybackend.Repositories.RepasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepasService {
    @Autowired
    private RepasRepository repasRepository;
    public List<Repas> listsDeRepas(Long stockId){
        return repasRepository.repas(stockId);
    }
}
