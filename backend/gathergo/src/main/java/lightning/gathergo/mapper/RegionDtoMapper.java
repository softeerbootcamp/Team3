package lightning.gathergo.mapper;

import lightning.gathergo.dto.RegionDto;
import lightning.gathergo.model.Region;
import org.modelmapper.ModelMapper;


public class RegionDtoMapper {
    private ModelMapper modelMapper;
    //entity to dto
    public RegionDto.Response toRegionResponse(Region region){
        return modelMapper.map(region,RegionDto.Response.class);
    }
    //dto to entity
    public Region toRegionResponse(RegionDto.Request regionRequest){
        return modelMapper.map(regionRequest,Region.class);
    }
}
