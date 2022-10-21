package com.erecruitment.services;

import com.erecruitment.dtos.requests.SkillRequest;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.dtos.response.SkillResponse;
import com.erecruitment.entities.SkillEntity;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SkillRepository skillRepository;

    public SkillResponse saveData(SkillRequest request, Long id) {
        if (StringUtils.isEmpty(request.getSkillName())) {
            throw new ValidationErrorException("skill name cannot be empty");
        }
        SkillEntity skillEntity = convertToEntity(request);
        if (id > 0) {
            skillRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Data with ID: " + id + " not found"));
            skillEntity.setSkillId(id);
        }
        return convertToDto(skillRepository.save(skillEntity));
    }

    public PageableResponse<SkillResponse> getData(int page, int size, String keyword, String sortColumn, String sortOrder) {
        Sort sort = sortOrder.toLowerCase() == "desc" ? Sort.by(sortColumn).descending()
                : Sort.by(sortColumn).ascending();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<SkillEntity> skillEntities;
        if (keyword != null) {
            skillEntities = skillRepository.findBySkillNameContainingIgnoreCase(keyword, paging);
        } else {
            skillEntities = skillRepository.findAll(paging);
        }
        List<SkillEntity> dataList = skillEntities.getContent();
        PageableResponse response = new PageableResponse();
        if (!dataList.isEmpty()) {
            response.setMessage("ok");
            List<SkillResponse> dt = dataList.stream()
                    .map(barang -> modelMapper.map(barang, SkillResponse.class))
                    .collect(Collectors.toList());
            response.setData(dt);
        } else {
            throw new DataNotFoundException("Data not found");
        }
        response.setTotalData(skillEntities.getTotalElements());
        response.setTotalPages(skillEntities.getTotalPages());
        response.setCurrentPage(skillEntities.getNumber() + 1);
        response.setNext(skillEntities.hasNext());
        response.setPrevious(skillEntities.hasPrevious());
        response.setPageSize(skillEntities.getSize());
        return response;
    }

    public void removeOne(Long id) {
        skillRepository.findById(id).orElseThrow(() -> new DataNotFoundException("data id : " + id + " not found"));
        skillRepository.deleteById(id);
    }

    private SkillEntity convertToEntity(SkillRequest request) {
        return modelMapper.map(request, SkillEntity.class);
    }

    private SkillResponse convertToDto(SkillEntity skillEntity) {
        return modelMapper.map(skillEntity, SkillResponse.class);
    }
}
