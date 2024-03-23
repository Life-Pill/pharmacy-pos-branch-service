package com.lifePill.posbranchservice.service.Impl;

import com.lifePill.posbranchservice.dto.BranchDTO;
import com.lifePill.posbranchservice.dto.BranchUpdateDTO;
import com.lifePill.posbranchservice.entity.Branch;
import com.lifePill.posbranchservice.exception.EntityDuplicationException;
import com.lifePill.posbranchservice.exception.NotFoundException;
import com.lifePill.posbranchservice.helper.SaveImageHelper;
import com.lifePill.posbranchservice.repository.BranchRepository;
import com.lifePill.posbranchservice.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BranchServiceIMPL implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public void saveBranch(BranchDTO branchDTO, MultipartFile image) {
        if (branchRepository.existsById(branchDTO.getBranchId()) || branchRepository.existsByBranchEmail(branchDTO.getBranchEmail())) {
            throw new EntityDuplicationException("Branch already exists");
        } else {
            byte[] imageBytes = SaveImageHelper.saveImage(image);
            Branch branch = new Branch(
                    branchDTO.getBranchId(),
                    branchDTO.getBranchName(),
                    branchDTO.getBranchAddress(),
                    branchDTO.getBranchContact(),
                    branchDTO.getBranchFax(),
                    branchDTO.getBranchEmail(),
                    branchDTO.getBranchDescription(),
                    imageBytes,
                    branchDTO.isBranchStatus(),
                    branchDTO.getBranchLocation(),
                    branchDTO.getBranchCreatedOn(),
                    branchDTO.getBranchCreatedBy()
            );
            branchRepository.save(branch);
        }
    }


    @Override
    public byte[] getImageData(int branchId) {
        Optional<Branch> branchOptional = branchRepository.findById(branchId);
        return branchOptional.map(Branch::getBranchImage).orElse(null);
    }



    @Override
    public List<BranchDTO> getAllBranches() {
        List<Branch> getAllBranches = branchRepository.findAll();
        if (getAllBranches.size() > 0){
            List<BranchDTO> branchDTOList = new ArrayList<>();
            for (Branch branch: getAllBranches){
                BranchDTO cashierDTO = new BranchDTO(
                        branch.getBranchId(),
                        branch.getBranchName(),
                        branch.getBranchAddress(),
                        branch.getBranchContact(),
                        branch.getBranchFax(),
                        branch.getBranchEmail(),
                        branch.getBranchDescription(),
                        branch.getBranchImage(),
                        branch.isBranchStatus(),
                        branch.getBranchLocation(),
                        branch.getBranchCreatedOn(),
                        branch.getBranchCreatedBy()
                );
                branchDTOList.add(cashierDTO);
            }
            return branchDTOList;
        }else {
            throw new NotFoundException("No Branch Found");
        }
    }

    @Override
    public BranchDTO getBranchById(int branchId) {
        if (branchRepository.existsById(branchId)){
            Branch branch = branchRepository.getReferenceById(branchId);

            // can use mappers to easily below that task
            BranchDTO branchDTO  = new BranchDTO(
                   branch.getBranchId(),
                    branch.getBranchName(),
                    branch.getBranchAddress(),
                    branch.getBranchContact(),
                    branch.getBranchFax(),
                    branch.getBranchEmail(),
                    branch.getBranchDescription(),
                    branch.getBranchImage(),
                    branch.isBranchStatus(),
                    branch.getBranchLocation(),
                    branch.getBranchCreatedOn(),
                    branch.getBranchCreatedBy()
            );
            return branchDTO;
        }else {
            throw  new NotFoundException("No Branch found for that id");
        }

    }

    @Override
    public String deleteBranch(int branchId) {
        if (branchRepository.existsById(branchId)){
            branchRepository.deleteById(branchId);

            return "deleted succesfully : "+ branchId;
        }else {
            throw new NotFoundException("No Branch found for that id");
        }
    }

    public String updateBranch(int branchId, BranchUpdateDTO branchUpdateDTO, MultipartFile image) {
        if (!branchRepository.existsById(branchId)) {
            throw new EntityNotFoundException("Branch not found");
        }

        Optional<Branch> branchOptional = branchRepository.findById(branchId);
        if (branchOptional.isPresent()) {
            Branch branch = branchOptional.get();

            if (branchUpdateDTO.getBranchName() != null) {
                branch.setBranchName(branchUpdateDTO.getBranchName());
            }

            // Repeat the same pattern for other fields

            if (image != null && !image.isEmpty()) {
                byte[] imageBytes = SaveImageHelper.saveImage(image);
                branch.setBranchImage(imageBytes);
            }

            branchRepository.save(branch);
            return "updated";
        } else {
            throw new NotFoundException("No Branch found for that id");
        }
    }

}