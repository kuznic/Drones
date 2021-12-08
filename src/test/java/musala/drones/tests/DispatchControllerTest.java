package musala.drones.tests;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import musala.drones.implementation.DroneServiceImpl;
import musala.drones.implementation.MedicationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DispatchControllerTest
{

    @MockBean
    DroneServiceImpl droneService;

    @MockBean
    MedicationServiceImpl medicationService;

    @Autowired
    private WebApplicationContext context;



    private MockMvc mockMvc;

    @Autowired
    Gson gson;



    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }




    @Test
    @WithMockUser(username = "user", password = "userpasssword", roles = "USER")
    public void registerDrone_success() throws Exception {
        Map<String,String> payload = new HashMap<>();

        payload.put("serialNumber", "433433");
        payload.put("droneModel","LIGHTWEIGHT");
        payload.put("batteryCapacity","100");
        payload.put("weightLimit", "400");

        mockMvc.perform(post("/api/v1/drones")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(payload)))
                .andExpect(ResultMatcher.matchAll(mvcResult -> {
            log.info(mvcResult.getResponse().getContentAsString());
        })).andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "userpasssword", roles = "USER")
    public void getAllDrones_success() throws Exception
    {
       mockMvc.perform(get("/api/v1/drones")
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(ResultMatcher.matchAll(mvcResult -> {
        log.info(mvcResult.getResponse().getContentAsString());
    })).andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "userpasssword", roles = "USER")
    public void getDrone_success() throws Exception {
        mockMvc.perform(get("/api/v1/drones/06364cbc-9468-4bfe-a917-1ab641bd49f1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(ResultMatcher.matchAll(mvcResult -> {
                    log.info(mvcResult.getResponse().getContentAsString());
                }))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "userpassword", roles = "USER")
    public void prepareDroneForLoading_success() throws Exception
    {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/api/v1/drones/06364cbc-9468-4bfe-a917-1ab641bd49f1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8");

        mockMvc.perform(builder)
                .andExpect(ResultMatcher.matchAll(mvcResult ->
                        log.info(mvcResult.getResponse().getContentAsString())))
                .andExpect(status().isOk());
    }



    @Test
    @WithMockUser(username = "user", password = "userpassword", roles = "USER")
    public void getAllAvailableDrones_success() throws Exception
    {
        mockMvc.perform(get("/api/v1/drones/available-for-loading")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(ResultMatcher.matchAll(mvcResult -> {
                    log.info(mvcResult.getResponse().getContentAsString());
                })).andExpect(status().isOk());
    }



//    @Test
//    @WithMockUser(username = "user", password = "userpasssword", roles = "USER")
//    public void addMedication_success() throws Exception {
//        Map<String,String> payload = new HashMap<>();
//
//        payload.put("name", "Propane");
//        payload.put("weight","40");
//        payload.put("code","CODE12");
//        payload.put("droneId", "06364cbc-9468-4bfe-a917-1ab641bd49f1");
//
//
//        MockMultipartFile file = new MockMultipartFile("file", "hello.jpg",
//                MediaType.IMAGE_JPEG_VALUE, "Drone Image 1!".getBytes());
//
//
//        mockMvc.perform(multipart("/api/v1/drones/add-medication")
//                .file(file)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(gson.toJson(payload)))
//                .andExpect(status().isOk());
//    }


    @Test
    @WithMockUser(username = "user", password = "userpasssword", roles = "USER")
    public void getAllDroneMedications_success() throws Exception
    {
        mockMvc.perform(get("/api/v1/drones/drone-medications/06364cbc-9468-4bfe-a917-1ab641bd49f1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(ResultMatcher.matchAll(mvcResult -> {
                    log.info(mvcResult.getResponse().getContentAsString());
                })).andExpect(status().isOk());
    }



    @Test
    @WithMockUser(username = "user", password = "userpasssword", roles = "USER")
    public void getDroneBatteryLevel_success() throws Exception {
        mockMvc.perform(get("/api/v1/drones/battery-level/06364cbc-9468-4bfe-a917-1ab641bd49f1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(ResultMatcher.matchAll(mvcResult -> {
                    log.info(mvcResult.getResponse().getContentAsString());
                }))
                .andExpect(status().isOk());
    }











}
