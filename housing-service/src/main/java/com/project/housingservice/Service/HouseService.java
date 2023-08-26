package com.project.housingservice.Service;

import com.project.housingservice.DAO.FacilityRepository;
import com.project.housingservice.DAO.HouseRepository;
import com.project.housingservice.DAO.LandlordRepository;
import com.project.housingservice.Exception.NotFoundException;
import com.project.housingservice.Model.DTO.AvailableHouseDTO;
import com.project.housingservice.Model.DTO.HouseAndLandlordDTO;
import com.project.housingservice.Model.DTO.HouseDTO;
import com.project.housingservice.Model.Entity.House;
import com.project.housingservice.Model.Entity.Landlord;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    private final LandlordRepository landlordRepository;
    private final FacilityRepository facilityRepository;

    public HouseService(HouseRepository houseRepository, LandlordRepository landlordRepository, FacilityRepository facilityRepository) {
        this.houseRepository = houseRepository;
        this.landlordRepository = landlordRepository;
        this.facilityRepository = facilityRepository;
    }

    public void createHouse(HouseDTO houseDTO) throws NotFoundException {
        Long landlordId = houseDTO.getLandlordId();
        Optional<Landlord> landlordOptional = landlordRepository.findById(landlordId);
        if (landlordOptional.isEmpty()) {
            throw new NotFoundException(String.format("landlord not found with id: %s", landlordId));
        }
        House house = new House();
        house.setLandlord(landlordOptional.get());
        house.setAddress(houseDTO.getAddress());
        house.setMaxOccupant(houseDTO.getMaxOccupant());
        house.setNumOfResidents(houseDTO.getNumOfResidents());
        houseRepository.save(house);
    }

    public void editHouse(Long houseId, HouseDTO houseDTO) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("house not found with id: %s", houseId));
        }
        Long landlordId = houseDTO.getLandlordId();
        Optional<Landlord> landlordOptional = landlordRepository.findById(landlordId);
        if (landlordOptional.isEmpty()) {
            throw new NotFoundException(String.format("landlord not found with id: %s", landlordId));
        }
        House house = houseOptional.get();
        house.setLandlord(landlordOptional.get());
        house.setAddress(houseDTO.getAddress());
        house.setMaxOccupant(houseDTO.getMaxOccupant());
        house.setNumOfResidents(houseDTO.getNumOfResidents());
        houseRepository.save(house);
    }

    @Transactional(rollbackOn = {NotFoundException.class, RuntimeException.class, Error.class})
    public void deleteHouse(Long houseId) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("house not found with id: %s", houseId));
        }
        facilityRepository.deleteAllByHouseId(houseId);
        houseRepository.deleteById(houseId);
    }

    public List<AvailableHouseDTO> findAvaliableHouse() {
        List<House> houseList = houseRepository.findHousesWithSpace();
        List<AvailableHouseDTO> availableHouses = new ArrayList<>();
        for (House house : houseList) {
            AvailableHouseDTO availableHouseDTO = new AvailableHouseDTO();
            availableHouseDTO.setHouseId(house.getId());
            availableHouseDTO.setAvailableSpace(house.getMaxOccupant() - house.getNumOfResidents());
            availableHouses.add(availableHouseDTO);
        }
        return availableHouses;
    }

    public boolean checkHouseAvailablity(Long houseId) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House not found with id %s", houseId));
        }
        House house = houseOptional.get();
        if (house.getMaxOccupant() > house.getNumOfResidents()) return true;
        return false;

    }
    public String getHouseName(Long houseId) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House not found with id %s", houseId));
        }
        House house = houseOptional.get();
        return house.getAddress();

    }

    public HouseDTO findHouse(Long houseId) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House not found with id %s", houseId));
        }
        House house = houseOptional.get();
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setAddress(house.getAddress());
        houseDTO.setMaxOccupant(house.getMaxOccupant());
        houseDTO.setNumOfResidents(house.getNumOfResidents());
        houseDTO.setLandlordId(house.getLandlord().getId());
        return houseDTO;
    }

    public HouseAndLandlordDTO findHouseWithLandlord(Long houseId) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House not found with id %s", houseId));
        }

        House house = houseOptional.get();
        Landlord landlord = house.getLandlord();
        HouseAndLandlordDTO houseAndLandlordDTO = new HouseAndLandlordDTO();
        houseAndLandlordDTO.setLandlordEmail(landlord.getEmail());
        houseAndLandlordDTO.setLandlordPhone(landlord.getPhone());
        houseAndLandlordDTO.setAddress(house.getAddress());
        houseAndLandlordDTO.setLandlordFullName(String.format("%s %s",landlord.getFirstName(),landlord.getLastName()));

        return houseAndLandlordDTO;
    }
    public Integer getTotalNumOfResidents() {
        return houseRepository.findTotalNumOfResidents();
    }

    public void changeResidentsNum(Long houseId, Integer number, Boolean add) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House not found with id %s", houseId));
        }
        House house = houseOptional.get();
        int currNum = house.getNumOfResidents();
        if (add) {
            house.setNumOfResidents(currNum + number);
        } else {
            house.setNumOfResidents(currNum - number);
        }
        houseRepository.save(house);
    }
}
