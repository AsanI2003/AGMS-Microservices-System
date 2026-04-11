package lk.ijse.agms.crop_service.service.impl;

import lk.ijse.agms.crop_service.client.ZoneClient;
import lk.ijse.agms.crop_service.dto.CropDTO;
import lk.ijse.agms.crop_service.entity.Crop;
import lk.ijse.agms.crop_service.entity.enums.CropStatus;
import lk.ijse.agms.crop_service.repo.CropRepository;
import lk.ijse.agms.crop_service.service.CropService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ZoneClient zoneClient; // Bridge to Zone Service (OpenFeign)

    @Override
    public String saveCrop(CropDTO cropDTO) {
        try {
            ResponseEntity<Object> response = zoneClient.getZoneById(cropDTO.getZoneID());
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Zone ID: Zone not found for save.");
            }


        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Zone Service is unreachable.");
        }


        Crop crop = modelMapper.map(cropDTO, Crop.class);
        return cropRepository.save(crop).getCropID();
    }

    @Override
    public void updateCropStatus(String id, CropStatus status) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crop not found for status update: " + id));

        crop.setCropStatus(status);
        cropRepository.save(crop);
    }

    @Override
    public CropDTO getCrop(String id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crop not found: " + id));

        return modelMapper.map(crop, CropDTO.class);
    }
}