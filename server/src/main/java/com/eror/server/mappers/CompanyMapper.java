package com.eror.server.mappers;

import com.eror.server.dto.CompanyDTO;
import com.eror.server.dto.RoleDTO;
import com.eror.server.model.Company;
import com.eror.server.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDTO companyToCompanyDTO(Company company);

    Company companyDTOToCompany(CompanyDTO companyDTO);

    List<Company> listCompanyDTOtoListCompanys(List<CompanyDTO> companyDTOList);

    List<CompanyDTO> listCompantToListCompanyDTOs(List<Company> companyList);
}
