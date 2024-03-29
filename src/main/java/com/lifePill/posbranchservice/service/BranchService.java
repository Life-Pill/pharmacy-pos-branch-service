package com.lifePill.posbranchservice.service;

import com.lifePill.posbranchservice.dto.BranchDTO;
import com.lifePill.posbranchservice.dto.BranchUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BranchService {

    public void saveBranch(BranchDTO branchDTO, MultipartFile image);

    byte[] getImageData(int branchId);

    List<BranchDTO> getAllBranches();

    public BranchDTO getBranchById(int branchId);

    String deleteBranch(int branchId);
    String updateBranch(int branchId, BranchUpdateDTO branchUpdateDTO, MultipartFile image);
}
