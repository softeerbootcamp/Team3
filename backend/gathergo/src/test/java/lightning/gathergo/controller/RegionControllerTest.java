package lightning.gathergo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lightning.gathergo.dto.RegionDto;
import lightning.gathergo.mapper.RegionDtoMapper;
import lightning.gathergo.model.Region;
import lightning.gathergo.service.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Mock
    private RegionService regionService;

    @Mock
    private RegionDtoMapper regionDtoMapper;

    @InjectMocks
    private RegionController regionController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();
    }

    @Test
    public void getRegions() throws Exception {
        Region region1 = new Region();
        region1.setId(1);
        region1.setName("Region 1");

        Region region2 = new Region();
        region2.setId(2);
        region2.setName("Region 2");


        when(regionService.getRegions()).thenReturn(Arrays.asList(
                region1,
                region2
        ));

        RegionDto.Response res1 = new RegionDto.Response();
        RegionDto.Response res2 = new RegionDto.Response();

        res1.setId(1); res1.setName("Region 1");
        res1.setId(2); res1.setName("Region 2");

        when(regionDtoMapper.toRegionResponseList(Arrays.asList(
                region1,
                region2
        )))
                .thenReturn(Arrays.asList(res1, res2));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/regions");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getRegionById() throws Exception {
        Region region1 = new Region();
        region1.setId(17);
        region1.setName("Region 1");

        when(regionService.getRegionById(17)).thenReturn(Optional.of(region1));

        RegionDto.Response res1 = new RegionDto.Response();
        res1.setId(1); res1.setName("Region 1");

        when(regionDtoMapper.toRegionResponse(region1)).thenReturn(res1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/regions/17");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", region1.getName()).exists())
                .andDo(print());

    }

    @Test
    void createRegion() throws Exception {

        Region region1 = new Region();
        //region1.setId(40);
        region1.setName("new region");

        when(regionService.createRegion("new region")).thenReturn(Optional.of(region1));

        RegionDto.Response res1 = new RegionDto.Response();
        res1.setId(1); res1.setName("Region 1");

        when(regionDtoMapper.toRegionResponse(region1)).thenReturn(res1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(region1));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", region1.getName()).exists())
                .andDo(print());
    }

    @Test
    void modifyRegion() throws Exception {

        Region modified = new Region();
        modified.setId(17);
        modified.setName("modified region");

        when(regionService.updateRegion(17,"modified region")).thenReturn(Optional.of(modified));

        RegionDto.Response res1 = new RegionDto.Response();
        res1.setId(17); res1.setName("modified region");

        when(regionDtoMapper.toRegionResponse(modified)).thenReturn(res1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(modified));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", modified.getName()).exists())
                .andDo(print());
    }

    @Test
    void deleteRegion() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/regions/17");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print());
    }

}
