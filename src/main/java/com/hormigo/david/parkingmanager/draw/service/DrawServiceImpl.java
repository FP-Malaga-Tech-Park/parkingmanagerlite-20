package com.hormigo.david.parkingmanager.draw.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.hormigo.david.parkingmanager.draw.domain.Draw;
import com.hormigo.david.parkingmanager.draw.domain.DrawDao;
import com.hormigo.david.parkingmanager.draw.domain.DrawRepository;
@Service
public class DrawServiceImpl implements DrawService{

    DrawRepository repository;
    public DrawServiceImpl(DrawRepository repository) {
        this.repository = repository;
    }
    @Override
    public Iterable<Draw> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Draw getLastDrawed() {
        return null;
    }

    @Override
    public void register(DrawDao drawDao) {
        Draw draw = new Draw();
        BeanUtils.copyProperties(drawDao, draw);
        this.repository.save(draw);
    }
    
}
