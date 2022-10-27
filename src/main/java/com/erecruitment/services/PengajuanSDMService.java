package com.erecruitment.services;

import com.erecruitment.dtos.requests.AddPengajuanSDMRequest;
import com.erecruitment.dtos.requests.UpdateStatusPengajuanSDMRequest;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.dtos.response.PengajuanSDMResponse;
import com.erecruitment.entities.*;
import com.erecruitment.exceptions.DataNotFoundException;
import com.erecruitment.exceptions.ValidationErrorException;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.PengajuanSDMSkillRepository;
import com.erecruitment.repositories.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PengajuanSDMService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PengajuanSDMRepository pengajuanSDMRepository;

    @Autowired
    private PengajuanSDMSkillRepository pengajuanSDMSkillRepository;

    @Autowired
    private SkillRepository skillRepository;


    public PengajuanSDMResponse saveData(AddPengajuanSDMRequest request, Long id) {
        validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<PengajuanSDMSkillEntity> dataList = new ArrayList<>();
        List<SkillEntity> listSkill = skillRepository.findBySkillIdIn(request.getListSkill());
        PengajuanSDMEntity pengajuanSDMEntity = convertToEntity(request);
        if (id > 0) {
            Optional<PengajuanSDMEntity> dt = pengajuanSDMRepository.findById(id);
            if (!dt.isPresent()) {
                throw new DataNotFoundException("Data with ID: " + id + " not found");
            }
            int status = dt.get().getStatus();
            if (status > 1) {
                throw new ValidationErrorException("data cannot be edit");
            }
            pengajuanSDMEntity.setIdPengajuan(id);
        }
        pengajuanSDMEntity.setStatus((short) 1);
        pengajuanSDMEntity.setUser(user);
        PengajuanSDMEntity pengajuanSDMEntity1 = pengajuanSDMRepository.save(pengajuanSDMEntity);
        if (!listSkill.isEmpty()) {
            for (SkillEntity skillEntity : listSkill) {
                PengajuanSDMSkillEntity pengajuanSDMSkillEntity = new PengajuanSDMSkillEntity();
                pengajuanSDMSkillEntity.setPengajuanSDMEntity(pengajuanSDMEntity1);
                pengajuanSDMSkillEntity.setSkillId(skillEntity.getSkillId());
                pengajuanSDMSkillEntity.setSkillName(skillEntity.getSkillName());
                dataList.add(pengajuanSDMSkillEntity);
            }
            pengajuanSDMSkillRepository.saveAll(dataList);
        }

        return convertToDto(pengajuanSDMEntity1);
    }

    public PageableResponse<PengajuanSDMResponse> getData(int page, int size, String keyword, String sortColumn,
                                                          String sortOrder, short status) {
        Sort sort = sortOrder.toLowerCase() == "desc" ? Sort.by(sortColumn).descending()
                : Sort.by(sortColumn).ascending();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        RoleName role = user.getRole();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<PengajuanSDMEntity> pengajuanSDMEntities;
        if (role == RoleName.USER) {
            if (keyword != null) {
                if (status > 0) {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByUserAndPosisiContainingIgnoreCaseAndStatus(user, keyword, Short.valueOf(status), paging);
                } else {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByUserAndPosisiContainingIgnoreCase(user, keyword, paging);
                }
            } else {
                if (status > 0) {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByUserAndStatus(user, Short.valueOf(status), paging);
                } else {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByUser(user, paging);
                }
            }
        } else {
            if (keyword != null) {
                if (status > 0) {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByPosisiContainingIgnoreCaseAndStatus(keyword, Short.valueOf(status), paging);
                } else {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByPosisiContainingIgnoreCase(keyword, paging);
                }
            } else {
                if (status > 0) {
                    pengajuanSDMEntities = pengajuanSDMRepository.findByStatus(Short.valueOf(status), paging);
                } else {
                    pengajuanSDMEntities = pengajuanSDMRepository.findAll(paging);
                }
            }
        }
        List<PengajuanSDMEntity> dataList = pengajuanSDMEntities.getContent();
        PageableResponse response = new PageableResponse();
        if (!dataList.isEmpty()) {
            response.setMessage("ok");
            List<PengajuanSDMResponse> dt = dataList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            response.setData(dt);
        } else {
            throw new DataNotFoundException("Data not found");
        }
        response.setTotalData(pengajuanSDMEntities.getTotalElements());
        response.setTotalPages(pengajuanSDMEntities.getTotalPages());
        response.setCurrentPage(pengajuanSDMEntities.getNumber() + 1);
        response.setNext(pengajuanSDMEntities.hasNext());
        response.setPrevious(pengajuanSDMEntities.hasPrevious());
        response.setPageSize(pengajuanSDMEntities.getSize());
        return response;
    }

    public void removeOne(Long id) {
        pengajuanSDMRepository.findById(id).orElseThrow(() -> new DataNotFoundException("data id : " + id + " not found"));
        pengajuanSDMRepository.deleteById(id);
    }

    public PengajuanSDMResponse updateStatus(UpdateStatusPengajuanSDMRequest request, Long id) {
        if (id == 0L) {
            throw new ValidationErrorException("id required");
        }
        if (request.getStatus() > 3 || request.getStatus() == 1) {
            throw new ValidationErrorException("status invalid");
        }
        if (request.getStatus() == 3 && StringUtils.isEmpty(request.getDeadline())) {
            throw new ValidationErrorException("deadline required");
        }
        Optional<PengajuanSDMEntity> dt = pengajuanSDMRepository.findById(id);
        if (!dt.isPresent()) {
            throw new DataNotFoundException("Data with ID: " + id + " not found");
        }
        PengajuanSDMEntity pengajuanSDMEntity = dt.get();
        if (pengajuanSDMEntity.getStatus() > 1) {
            throw new ValidationErrorException("status cannot be update");
        }
        pengajuanSDMEntity.setIdPengajuan(id);
        pengajuanSDMEntity.setStatus(request.getStatus());
        pengajuanSDMEntity.setRemarkHR(request.getRemarkHR());
        pengajuanSDMEntity.setDeadline(request.getDeadline());
        return convertToDto(pengajuanSDMRepository.save(pengajuanSDMEntity));
    }

    public PengajuanSDMResponse closeJobPosted(Long id) {
        if (id == 0L) {
            throw new ValidationErrorException("id required");
        }
        Optional<PengajuanSDMEntity> dt = pengajuanSDMRepository.findById(id);
        if (!dt.isPresent()) {
            throw new DataNotFoundException("Data with ID: " + id + " not found");
        }
        PengajuanSDMEntity pengajuanSDMEntity = dt.get();
        if (pengajuanSDMEntity.getStatus() == 2 || pengajuanSDMEntity.getStatus() == 4) {
            throw new ValidationErrorException("status cannot be update");
        }
        pengajuanSDMEntity.setIdPengajuan(id);
        pengajuanSDMEntity.setStatus((short) 4);
        return convertToDto(pengajuanSDMRepository.save(pengajuanSDMEntity));
    }

    public PengajuanSDMResponse getDetail(Long id) {
        if (id == 0L) {
            throw new ValidationErrorException("id required");
        }
        Optional<PengajuanSDMEntity> dt = pengajuanSDMRepository.findById(id);
        if (!dt.isPresent()) {
            throw new DataNotFoundException("Data with ID: " + id + " not found");
        }
        PengajuanSDMEntity pengajuanSDMEntity = dt.get();
        return convertDetailToDto(pengajuanSDMEntity);
    }

    public void closeAutoJobPosted() {
        List<PengajuanSDMEntity> dataList = new ArrayList<>();
        List<PengajuanSDMEntity> jobList = pengajuanSDMRepository.closeAutoJob();
        if (!jobList.isEmpty()) {
            for (PengajuanSDMEntity jl : jobList) {
                PengajuanSDMEntity pengajuanSDMEntity = modelMapper.map(jl, PengajuanSDMEntity.class);
                pengajuanSDMEntity.setIdPengajuan(jl.getIdPengajuan());
                pengajuanSDMEntity.setStatus((short) 4);
                dataList.add(pengajuanSDMEntity);
            }
            pengajuanSDMRepository.saveAll(dataList);
        }
    }

    private void validate(AddPengajuanSDMRequest request) {
        if (StringUtils.isEmpty(request.getPosisi())) {
            throw new ValidationErrorException("posisi cannot be empty");
        }
        if (StringUtils.isEmpty(request.getDescription())) {
            throw new ValidationErrorException("description cannot be empty");
        }
        if (request.getNumberRequired() == 0 || StringUtils.isEmpty(request.getNumberRequired())) {
            throw new ValidationErrorException("numberRequired required");
        }
        if (request.getListSkill().size() == 0) {
            throw new ValidationErrorException("listSkill required");
        }
    }

    private PengajuanSDMEntity convertToEntity(AddPengajuanSDMRequest request) {
        return modelMapper.map(request, PengajuanSDMEntity.class);
    }

    private PengajuanSDMResponse convertToDto(PengajuanSDMEntity pengajuanSDMEntity) {
        PengajuanSDMResponse response = modelMapper.map(pengajuanSDMEntity, PengajuanSDMResponse.class);
        User user = pengajuanSDMEntity.getUser();
        response.setRequestName(user.getName());
        return response;
    }

    private PengajuanSDMResponse convertDetailToDto(PengajuanSDMEntity pengajuanSDMEntity) {
        PengajuanSDMResponse response = modelMapper.map(pengajuanSDMEntity, PengajuanSDMResponse.class);

        User user = pengajuanSDMEntity.getUser();
        response.setRequestName(user.getName());

        Set<PengajuanSDMSkillEntity> listSkill = pengajuanSDMSkillRepository.findByPengajuanId(pengajuanSDMEntity.getIdPengajuan());
        response.setListSkill(listSkill);
        return response;
    }


}
