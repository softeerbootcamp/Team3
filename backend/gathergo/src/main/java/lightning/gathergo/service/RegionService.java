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

    public Region getRegionById(String id){
        Integer identifier = Integer.parseInt(id);
        return regionRepository.findOneById(identifier);
    }

    public Region getRegionByName(String name){
        return regionRepository.findOneByName(name);
    }


}
