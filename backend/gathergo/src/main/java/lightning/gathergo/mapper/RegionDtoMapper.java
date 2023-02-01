package lightning.gathergo.mapper;

import lightning.gathergo.dto.RegionDto;
import lightning.gathergo.model.Region;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RegionDtoMapper {
    private ModelMapper modelMapper;
    //entity to dto
    public RegionDto.Response toRegionResponse(Region region){
        return modelMapper.map(region,RegionDto.Response.class);
    }
    public List<RegionDto.Response> toRegionResponseList(List<Region> regions){
        return regions
                .stream()
                .map(region -> modelMapper.map(region, RegionDto.Response.class))
                .collect(Collectors.toList());
    }
    //dto to entity
    public Region toRegion(RegionDto.Request regionRequest){
        return modelMapper.map(regionRequest,Region.class);
    }
}
