package lightning.gathergo.service;

import lightning.gathergo.exception.CustomGlobalException;
import lightning.gathergo.exception.ErrorCode;
import lightning.gathergo.model.Region;
import lightning.gathergo.repository.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions(){
        return regionRepository.findAll();
    }

    public Optional<Region> getRegionById(Integer id){
        return Optional.ofNullable(regionRepository.findOneById(id)).orElseThrow(() -> new CustomGlobalException(ErrorCode.NO_RESOURCE));
    }

    public Optional<Region> getRegionByName(String name){
        return regionRepository.findOneByName(name);
    }

    public Optional<Region> createRegion(String name){
        if(getRegionByName(name).isPresent()) throw new CustomGlobalException(ErrorCode.NAME_DUPLICATED);
         regionRepository.save(name);
         return regionRepository.findOneByName(name);
    }

    public Optional<Region> updateRegion(Integer id, String name){
        if(getRegionByName(name).isPresent()) throw new CustomGlobalException(ErrorCode.NAME_DUPLICATED);
        regionRepository.update(id,name);
        return regionRepository.findOneById(id);
    }

    public void deleteRegionById(Integer id){
        regionRepository.deleteById(id);
    }

    public void deleteRegions(){
        regionRepository.deleteAll();
    }

}
