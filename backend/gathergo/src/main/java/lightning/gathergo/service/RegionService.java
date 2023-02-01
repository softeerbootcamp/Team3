package lightning.gathergo.service;

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

    public Optional<Region> getRegionById(String id){
        Integer identifier = Integer.parseInt(id);
        return regionRepository.findOneById(identifier);
    }

    public Optional<Region> getRegionByName(String name){
        return regionRepository.findOneByName(name);
    }

    public Region createRegion(Region region){
        return regionRepository.save(region);
    }

    public Region updateRegion(Region region){
        return regionRepository.save(region);
    }

    public void deleteRegionById(Integer id){
        regionRepository.deleteById(id);
    }

    public void deleteRegions(){
        regionRepository.deleteAll();
    }


}
