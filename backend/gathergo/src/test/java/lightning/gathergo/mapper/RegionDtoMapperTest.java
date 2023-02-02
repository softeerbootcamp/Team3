package lightning.gathergo.mapper;

import lightning.gathergo.dto.RegionDto;
import lightning.gathergo.model.Region;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegionDtoMapperTest {

    private Region region;
    private ModelMapper modelMapper;
    private RegionDtoMapper regionDtoMapper;
    private RegionDto.Request regionRequest;
    private List<Region> regionList;


    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        regionDtoMapper = new RegionDtoMapper(modelMapper);
        region = new Region(1, "Test Region");
        regionRequest = new RegionDto.Request();
        regionRequest.setName("Test Region");

        regionList = new ArrayList<>();
        for(int i=0;i<10;i++){
            regionList.add(new Region("list region elem"));
        }
    }

    @Test
    void toRegionResponse() {
        RegionDto.Response  res = regionDtoMapper.toRegionResponse(region);
        Assertions.assertThat(res.getName()).isEqualTo("Test Region");
    }

    @Test
    void toRegionResponseList() {
        List<RegionDto.Response> responseList = regionDtoMapper.toRegionResponseList(regionList);
        Assertions.assertThat(responseList.size()).isEqualTo(10);
        Assertions.assertThat(responseList.get(0).getName()).isEqualTo("list region elem");
    }

    @Test
    void toRegion() {
        Region region = regionDtoMapper.toRegion(regionRequest);
        Assertions.assertThat(region.getName()).isEqualTo("Test Region");
    }
}
