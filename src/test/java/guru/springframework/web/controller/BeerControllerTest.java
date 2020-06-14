package guru.springframework.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.bootstrap.BeerLoader;
import guru.springframework.respositories.BeerRepository;
import guru.springframework.web.model.BeerDto;
import guru.springframework.web.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import guru.springframework.services.BeerService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 443)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.mappers")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;

    @MockBean
    BeerService beerService;

    BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerDto = BeerDto.builder()
                .beerName("Dogfish Head")
                .beerStyle(BeerStyle.ALE)
                .upc(BeerLoader.BEER_1_UPC)
                .price(new BigDecimal(5))
                .build();
    }

    @Test
    void getBeerById() throws Exception {
        given(beerService.getById(any(), null)).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)
                .param("iscold", "yes")
        )
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestParameters(
                            parameterWithName("iscold").description("Is beer cold query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer").type(UUID.class),
                                fieldWithPath("version").description("Version number").type(String.class),
                                fieldWithPath("createdDate").description("Date Created").type(String.class),
                                fieldWithPath("lastModifiedDate").description("Date Updated").type(String.class),
                                fieldWithPath("beerName").description("Beer Name").type(String.class),
                                fieldWithPath("beerStyle").description("Beer Style").type(String.class),
                                fieldWithPath("upc").description("UPC of Beer").type(Long.class),
                                fieldWithPath("price").description("Price").type(BigDecimal.class),
                                fieldWithPath("quantityOnHand").description("Quantity on hand").type(Integer.class)
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson)
        )
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                            requestFields(
                                    fields.withPath("id").ignored(),
                                    fields.withPath("version").ignored(),
                                    fields.withPath("createdDate").ignored(),
                                    fields.withPath("lastModifiedDate").ignored(),
                                    fields.withPath("beerName").description("Name of the beer"),
                                    fields.withPath("beerStyle").description("Style of the beer"),
                                    fields.withPath("upc").description("Beer UPC"),
                                    fields.withPath("price").description("Beer price"),
                                    fields.withPath("quantityOnHand").ignored()
                            )
                        ));
    }

    @Test
    void updateBeerById() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson)
        )
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path)
                    .attributes(key("constraints").value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path),". ")));
        }
    }

}