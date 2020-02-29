package com.eror.server.service.impl;


import com.eror.server.model.Company;
import com.eror.server.repository.CompanyRepository;
import com.eror.server.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company update(Company entity) {
        return null;
    }

    @Override
    public void delete(Company entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteInBatch(List<Company> entities) {

    }

    @Override
    public Company find(Long id) {
        return companyRepository.findCompanyById(id);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
