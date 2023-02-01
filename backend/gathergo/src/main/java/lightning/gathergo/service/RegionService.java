package lightning.gathergo.service;

import lightning.gathergo.model.Region;
import lightning.gathergo.repository.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Region getRegionById(Integer id){
        return regionRepository.findOneById(id);
    }

    public Region getRegionByName(String name){
        return regionRepository.findOneByName(name);
    }

    public Region createRegion(String name){
        return regionRepository.createRegion(name);
    }

    public Region modifyRegion(Integer id, String name){
        return regionRepository.updateRegion(id, name);
    }

    public void deleteRegion(Integer id){
        regionRepository.deleteOneById(id);
    }

    public void deleteRegions(){
        regionRepository.deleteAll();
    }


}
